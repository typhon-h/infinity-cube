#include "tasks.h"
#include "led.h"

void pattern_task()
{
    led_patterns[0]();

    FastLED.show();
}