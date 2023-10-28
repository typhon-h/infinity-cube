#include "header/led.h"
#include "header/tasks.h"

// Task Scheduler
#include <TaskScheduler.h>
#include <TaskSchedulerDeclarations.h>

Scheduler schedule;

void setup()
{
    // Safety to allow re-programming
    delay(3000);

    // Initialise Serial for the simulator
    Serial.begin(115200);

    // Initialize the LEDs
    led_setup();

    // Set up the scheduler
    schedule.init();

    // Register tasks
    schedule.addTask(ledUpdateTask);

    // Enable tasks
    ledUpdateTask.enable();
}

void loop()
{
    schedule.execute(); // Enable task scheduler
}
