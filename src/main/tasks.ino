#include "header/tasks.h"
#include "header/led.h"

void ledUpdateCallback() {
  fxctrlr.update();
}