#ifndef _TASKS_H_
#define _TASKS_H_

#include "led.h" // Not used in this file but doesn't seem to run without it ¯\_(ツ)_/¯ 

#define DEFAULT_STACK_SIZE 10000 // TODO: Revisit this might need to define this per task
#define CORE0 0
#define CORE1 1

#define FREQ_TO_PERIOD(X) (1000 / X)

#define LED_UPDATE_TASK_FREQUENCY 60

typedef enum // Arrange enum by priority - lowest to highest
{
  BASE_PRIORITY = 1,
} TASK_PRIORITY_T;

typedef struct
{
  const char* name;
  void (*callback)(void);
  uint16_t frequency;
  TASK_PRIORITY_T priority;
  BaseType_t core;
} TASK_T;

void ledUpdateCallback(void);

TASK_T system_tasks[] = {
  {"LED Update", &ledUpdateCallback, LED_UPDATE_TASK_FREQUENCY, BASE_PRIORITY, CORE1},
};

void scheduler_setup(void);
void taskCallback(void*);

#endif // _TASKS_H_