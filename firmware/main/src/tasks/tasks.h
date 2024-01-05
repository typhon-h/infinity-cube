#ifndef _TASKS_H_
#define _TASKS_H_

#include <Arduino.h>
#include <stdint.h>

#define DEFAULT_STACK_SIZE 10000 // TODO: Revisit this might need to define this per task
#define CORE0 0
#define CORE1 1

#define FREQ_TO_PERIOD(X) (1000 / X)

#define TASK_ONCE 0
#define LED_UPDATE_TASK_FREQUENCY 60
#define ALEXA_POLL_TASK_FREQUENCY 1

typedef enum // Arrange enum by priority - lowest to highest
{
  BASE_PRIORITY = 1,
  SERVER_PRIORITY,
  LED_PRIORITY,
} TASK_PRIORITY_T;

typedef struct
{
  const char *name;
  void (*callback)(void);
  float frequency;
  TASK_PRIORITY_T priority;
  BaseType_t core;
} TASK_T;

void ledUpdateCallback(void);

extern TASK_T system_tasks[];

void scheduler_setup(void);
void taskCallback(void *);

#endif // _TASKS_H_