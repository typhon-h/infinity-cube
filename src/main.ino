#include "led.h"
#include "tasks.h"

// Task Scheduler
#include <TaskScheduler.h>
#include <TaskSchedulerDeclarations.h>

Scheduler schedule;

void setup()
{
    // Initialise Serial for the simulator
    Serial.begin(115200);
    while (!Serial)
    {
    }

    // Initialize the LEDs
    led_setup();

    // Set up the scheduler
    schedule.init();

    // Register tasks
    schedule.addTask(pattern_t);

    // Enable tasks
    pattern_t.enable();
}

void loop()
{
    schedule.execute(); // Enable task scheduler
}
