#include "led.h"
#include <FastLED.h>

/**
 * @brief Initializes the strips within FastLED with default settings.
 *
 */
void led_setup()
{
    FastLED.addLeds<NEOPIXEL, LED_PIN>(leds, NUM_LEDS);

    FastLED.setBrightness(DEFAULT_INTENSITY);
    FastLED.setCorrection(TypicalLEDStrip);

    FastLED.setMaxPowerInVoltsAndMilliamps(LED_OPERATING_V, MAX_CURRENT_MA);

    FastLED.clear();
    FastLED.show();
}
