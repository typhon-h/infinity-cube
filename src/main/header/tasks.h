#ifndef _TASKS_H_
#define _TASKS_H_

#include <TaskScheduler.h>
#include <TaskSchedulerDeclarations.h>
#include "led.h"

#define FREQ_TO_PERIOD(X) (1000 / X)

#define LED_UPDATE_TASK_FREQUENCY 60

void ledUpdateCallback(void);

Task ledUpdateTask(FREQ_TO_PERIOD(LED_UPDATE_TASK_FREQUENCY), TASK_FOREVER, &ledUpdateCallback);

#endif // _TASKS_H_