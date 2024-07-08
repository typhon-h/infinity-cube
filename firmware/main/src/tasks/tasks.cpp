#include "tasks.h"
#include "../led/led.h"
#include "../server/alexa.h"

// Set of task definition for the RTOS scheduler to execute
TASK_T system_tasks[] = {
    {.name = "LED Update",
     .callback = &ledUpdateCallback,
     .frequency = LED_UPDATE_TASK_FREQUENCY,
     .priority = LED_PRIORITY,
     .core = CORE1},
    {.name = "Alexa",
     .callback = &alexa_callback,
     .frequency = ALEXA_POLL_TASK_FREQUENCY,
     .priority = SERVER_PRIORITY,
     .core = CORE1},
};

/**
 * @brief Registers each task to the dedicated core and enables execution
 *
 */
void scheduler_setup()
{
  // Register all tasks on their assigned cores
  for (int i = 0; i < sizeof(system_tasks) / sizeof(system_tasks[0]); i++)
  {
    xTaskCreatePinnedToCore(
        taskCallback,               // Callback function
        system_tasks[i].name,       // Task name
        system_tasks[i].stack_size, // Stack size
        (void *)(system_tasks + i), // Callback parameter (TASK_T object)
        system_tasks[i].priority,   // Execution priority
        NULL,                       // Handler
        system_tasks[i].core        // Core
    );
  }
}

/**
 * @brief Generic callback function for tasks. Allows greater flexibility for one-shot tasks
 *
 * @param parameter argument to pass to the specific task callback
 */
void taskCallback(void *parameter)
{
  TASK_T *task = (TASK_T *)parameter;

  if (task->frequency == TASK_ONCE)
  { // Frequency TASK_ONCE = single execution task
    (task->callback)();
    vTaskDelete(NULL);
    return;
  }

  while (1)
  {
    (task->callback)();                                               // Execute the task
    vTaskDelay(FREQ_TO_PERIOD(task->frequency) / portTICK_PERIOD_MS); // Block task for period
  }
}

/**
 * @brief LED update task callback
 *
 */
void ledUpdateCallback(void)
{
  static bool enabled = true;
  if (led_state)
  {
    enabled = true;
    fxctrlr.update(); // Refresh strip peripheral
  }
  else if (enabled)
  {
    enabled = false;
    disable_led();
  }
}
