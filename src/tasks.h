#ifndef _TASKS_H_
#define _TASKS_H_

#include <TaskScheduler.h>
#include <TaskSchedulerDeclarations.h>

#define FREQ_TO_PERIOD(X) (1000 / X)

#define PATTERN_TASK_FREQUENCY 10

void pattern_task(void);

Task pattern_t(FREQ_TO_PERIOD(PATTERN_TASK_FREQUENCY), TASK_FOREVER, &pattern_task);

#endif // _TASKS_H_