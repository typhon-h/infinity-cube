#include "header/led.h"
#include <FastLED.h>
#include <FastFX.h>
#include <FFXCoreEffects.h>

/**
 * @brief Initializes the strips within FastLED with default settings.
 *
 */
void led_setup()
{
  // FastLED Setup
  FastLED.addLeds<LED_TYPE, LED_PIN>(leds, NUM_LEDS);

  FastLED.setBrightness(DEFAULT_INTENSITY);
  FastLED.setCorrection(TypicalLEDStrip);

  FastLED.setMaxPowerInVoltsAndMilliamps(LED_OPERATING_V, MAX_CURRENT_MA);

  FastLED.clear();

  // FastFX Setup
  fxctrlr.initialize(new FFXFastLEDPixelController(leds, NUM_LEDS));
  fxctrlr.getPrimarySegment()->setBrightness(DEFAULT_INTENSITY);

  segment_init();

  // TODO: Temporary chase effect to test segments and direction symmetry
  for (int vertex = 0; vertex < CUBE_VERTICES; vertex++)
  {
    for (int segment = vertex * SEGMENT_PER_VERTEX; segment < ((vertex * SEGMENT_PER_VERTEX) + SEGMENT_PER_VERTEX); segment++)
    {
      FFXSegment *seg = segments[segment].segment;
      seg->setFX(new ChaseFX(seg->getLength()));                                    // Change the FX class here for cool things :)
      seg->getFX()->getFXColor().setPalette(NamedPalettes::getInstance()["multi"]); // Change the Palette here for more cool things :)

      seg->setOpacity(DEFAULT_INTENSITY);

      // Alternate direction of even/odd vertices
      // Reverse the fx direction of strips with flipped directions from vertex
      if ((vertex % 2 == 0 && segments[segment].direction == IN) || (vertex % 2 != 0 && segments[segment].direction == OUT))
      {
        seg->getFX()->setMovement(FFXBase::MovementType::MVT_BACKWARD);
      }
    }
  }
}

void segment_init()
{
  // TODO: possibly extract indices to ENUM or similar

  // BRB
  segments[0] = {fxctrlr.addSegment("0X", SEGMENT_OUT_VERTEX_START_INDEX(0), SEGMENT_OUT_VERTEX_END_INDEX(0)), X, OUT};
  segments[1] = {fxctrlr.addSegment("0Y", SEGMENT_OUT_VERTEX_START_INDEX(7), SEGMENT_OUT_VERTEX_END_INDEX(7)), Y, OUT};
  segments[2] = {fxctrlr.addSegment("0Z", SEGMENT_IN_VERTEX_START_INDEX(6), SEGMENT_IN_VERTEX_END_INDEX(6)), Z, IN};

  // BLB
  segments[3] = {fxctrlr.addSegment("1X", SEGMENT_IN_VERTEX_START_INDEX(0), SEGMENT_IN_VERTEX_END_INDEX(0)), X, IN};
  segments[4] = {fxctrlr.addSegment("1Y", SEGMENT_OUT_VERTEX_START_INDEX(1), SEGMENT_OUT_VERTEX_END_INDEX(1)), Y, OUT};
  segments[5] = {fxctrlr.addSegment("1Z", SEGMENT_OUT_VERTEX_START_INDEX(2), SEGMENT_OUT_VERTEX_END_INDEX(2)), Z, OUT};

  // BLF
  segments[6] = {fxctrlr.addSegment("2X", SEGMENT_OUT_VERTEX_START_INDEX(4), SEGMENT_OUT_VERTEX_END_INDEX(4)), X, OUT};
  segments[7] = {fxctrlr.addSegment("2Y", SEGMENT_OUT_VERTEX_START_INDEX(3), SEGMENT_OUT_VERTEX_END_INDEX(3)), Y, OUT};
  segments[8] = {fxctrlr.addSegment("2Z", SEGMENT_IN_VERTEX_START_INDEX(2), SEGMENT_IN_VERTEX_END_INDEX(2)), Z, IN};

  // BRF
  segments[9] = {fxctrlr.addSegment("3X", SEGMENT_IN_VERTEX_START_INDEX(4), SEGMENT_IN_VERTEX_END_INDEX(4)), X, IN};
  segments[10] = {fxctrlr.addSegment("3Y", SEGMENT_OUT_VERTEX_START_INDEX(5), SEGMENT_OUT_VERTEX_END_INDEX(5)), Y, OUT};
  segments[11] = {fxctrlr.addSegment("3Z", SEGMENT_OUT_VERTEX_START_INDEX(6), SEGMENT_OUT_VERTEX_END_INDEX(6)), Z, OUT};

  // TLB
  segments[12] = {fxctrlr.addSegment("4X", SEGMENT_IN_VERTEX_START_INDEX(8), SEGMENT_IN_VERTEX_END_INDEX(8)), X, IN};
  segments[13] = {fxctrlr.addSegment("4Y", SEGMENT_IN_VERTEX_START_INDEX(1), SEGMENT_IN_VERTEX_END_INDEX(1)), Y, IN};
  segments[14] = {fxctrlr.addSegment("4Z", SEGMENT_OUT_VERTEX_START_INDEX(9), SEGMENT_OUT_VERTEX_END_INDEX(9)), Z, OUT};

  // TLF
  segments[15] = {fxctrlr.addSegment("5X", SEGMENT_OUT_VERTEX_START_INDEX(10), SEGMENT_OUT_VERTEX_END_INDEX(10)), X, OUT};
  segments[16] = {fxctrlr.addSegment("5Y", SEGMENT_IN_VERTEX_START_INDEX(3), SEGMENT_IN_VERTEX_END_INDEX(3)), Y, IN};
  segments[17] = {fxctrlr.addSegment("5Z", SEGMENT_IN_VERTEX_START_INDEX(9), SEGMENT_IN_VERTEX_END_INDEX(9)), Z, IN};

  // TRF
  segments[18] = {fxctrlr.addSegment("6X", SEGMENT_IN_VERTEX_START_INDEX(10), SEGMENT_IN_VERTEX_END_INDEX(10)), X, IN};
  segments[19] = {fxctrlr.addSegment("6Y", SEGMENT_IN_VERTEX_START_INDEX(5), SEGMENT_IN_VERTEX_END_INDEX(5)), Y, IN};
  segments[20] = {fxctrlr.addSegment("6Z", SEGMENT_OUT_VERTEX_START_INDEX(11), SEGMENT_OUT_VERTEX_END_INDEX(11)), Z, OUT};

  // TRB
  segments[21] = {fxctrlr.addSegment("7X", SEGMENT_OUT_VERTEX_START_INDEX(8), SEGMENT_OUT_VERTEX_END_INDEX(8)), X, OUT};
  segments[22] = {fxctrlr.addSegment("7Y", SEGMENT_IN_VERTEX_START_INDEX(7), SEGMENT_IN_VERTEX_END_INDEX(7)), Y, IN};
  segments[23] = {fxctrlr.addSegment("7Z", SEGMENT_IN_VERTEX_START_INDEX(11), SEGMENT_IN_VERTEX_END_INDEX(11)), Z, IN};
}