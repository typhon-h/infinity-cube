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

    // Set up the scheduler
    schedule.init();
}

void loop()
{
    schedule.execute(); // Enable task scheduler
}
