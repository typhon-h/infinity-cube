#include "header/tasks.h"
#include "header/led.h"

void scheduler_setup()
{
  // Register all tasks on their assigned cores
  for (int i = 0; i < sizeof(system_tasks) / sizeof(system_tasks[0]); i++)
  {
    xTaskCreatePinnedToCore(
        taskCallback,               // Callback function
        system_tasks[i].name,       // Task name
        DEFAULT_STACK_SIZE,         // Stack size
        (void *)(system_tasks + i), // Callback parameter (TASK_T object)
        system_tasks[i].priority,   // Execution priority
        NULL,                       // Handler
        system_tasks[i].core        // Core
    );
  }
}

// Generic task callback
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

void ledUpdateCallback(void)
{
  fxctrlr.update(); // Refresh strip peripheral
}