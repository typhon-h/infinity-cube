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
    fxctrlr.initialize( new FFXFastLEDPixelController( leds, NUM_LEDS ) );
    fxctrlr.getPrimarySegment()->setBrightness( DEFAULT_INTENSITY );

    segment_init();

    // TODO: Temporary chase effect to test segments and direction symmetry
    for(int vertex = 0; vertex < 8; vertex++) { 
      for(int segment = vertex * 3; segment < ((vertex * 3) + 3); segment++) {
        FFXSegment* seg = segments[segment];
        seg->setFX(new ChaseFX(seg->getLength())); // Change the FX class here for cool things :)
        seg->getFX()->getFXColor().setPalette(NamedPalettes::getInstance()["multi"]); // Change the Palette here for more cool things :)
        
        seg->setOpacity(255);
        
        // TODO: Janky POC for directionality - definitely needs to be a struct value
        if ((vertex % 2 == 0 && seg->getTag()[2] == '1') 
              || (vertex % 2 != 0 && seg->getTag()[2] == '0')) 
        {
          seg->getFX()->setMovement(FFXBase::MovementType::MVT_BACKWARD);
        }
      }
    }
}

void segment_init() {
    // TODO: possibly extract indices to ENUM or similar

    // BRB
    segments[0] = fxctrlr.addSegment("0X0", SEGMENT_OUT_VERTEX_START_INDEX(0), SEGMENT_OUT_VERTEX_END_INDEX(0));
    segments[1] = fxctrlr.addSegment("0Y0", SEGMENT_OUT_VERTEX_START_INDEX(7), SEGMENT_OUT_VERTEX_END_INDEX(7));
    segments[2] = fxctrlr.addSegment("0Z1", SEGMENT_IN_VERTEX_START_INDEX(6), SEGMENT_IN_VERTEX_END_INDEX(6));

    // BLB
    segments[3] = fxctrlr.addSegment("1X1", SEGMENT_IN_VERTEX_START_INDEX(0), SEGMENT_IN_VERTEX_END_INDEX(0));
    segments[4] = fxctrlr.addSegment("1Y0", SEGMENT_OUT_VERTEX_START_INDEX(1), SEGMENT_OUT_VERTEX_END_INDEX(1));
    segments[5] = fxctrlr.addSegment("1Z0", SEGMENT_OUT_VERTEX_START_INDEX(2), SEGMENT_OUT_VERTEX_END_INDEX(2));

    // BLF
    segments[6] = fxctrlr.addSegment("2X0", SEGMENT_OUT_VERTEX_START_INDEX(4), SEGMENT_OUT_VERTEX_END_INDEX(4));
    segments[7] = fxctrlr.addSegment("2Y0", SEGMENT_OUT_VERTEX_START_INDEX(3), SEGMENT_OUT_VERTEX_END_INDEX(3));
    segments[8] = fxctrlr.addSegment("2Z1", SEGMENT_IN_VERTEX_START_INDEX(2), SEGMENT_IN_VERTEX_END_INDEX(2));

    // BRF
    segments[9] = fxctrlr.addSegment("3X1", SEGMENT_IN_VERTEX_START_INDEX(4), SEGMENT_IN_VERTEX_END_INDEX(4));
    segments[10] = fxctrlr.addSegment("3Y0", SEGMENT_OUT_VERTEX_START_INDEX(5), SEGMENT_OUT_VERTEX_END_INDEX(5));
    segments[11] = fxctrlr.addSegment("3Z0", SEGMENT_OUT_VERTEX_START_INDEX(6), SEGMENT_OUT_VERTEX_END_INDEX(6));

    // TLB
    segments[12] = fxctrlr.addSegment("4X1", SEGMENT_IN_VERTEX_START_INDEX(8), SEGMENT_IN_VERTEX_END_INDEX(8));
    segments[13] = fxctrlr.addSegment("4Y1", SEGMENT_IN_VERTEX_START_INDEX(1), SEGMENT_IN_VERTEX_END_INDEX(1));
    segments[14] = fxctrlr.addSegment("4Z0", SEGMENT_OUT_VERTEX_START_INDEX(9), SEGMENT_OUT_VERTEX_END_INDEX(9));

    // TLF
    segments[15] = fxctrlr.addSegment("5X0", SEGMENT_OUT_VERTEX_START_INDEX(10), SEGMENT_OUT_VERTEX_END_INDEX(10));
    segments[16] = fxctrlr.addSegment("5Y1", SEGMENT_IN_VERTEX_START_INDEX(3), SEGMENT_IN_VERTEX_END_INDEX(3));
    segments[17] = fxctrlr.addSegment("5Z1", SEGMENT_IN_VERTEX_START_INDEX(9), SEGMENT_IN_VERTEX_END_INDEX(9));

    // TRF
    segments[18] = fxctrlr.addSegment("6X1", SEGMENT_IN_VERTEX_START_INDEX(10), SEGMENT_IN_VERTEX_END_INDEX(10));
    segments[19] = fxctrlr.addSegment("6Y1", SEGMENT_IN_VERTEX_START_INDEX(5), SEGMENT_IN_VERTEX_END_INDEX(5));
    segments[20] = fxctrlr.addSegment("6Z0", SEGMENT_OUT_VERTEX_START_INDEX(11), SEGMENT_OUT_VERTEX_END_INDEX(11));

    // TRB
    segments[21] = fxctrlr.addSegment("7X0", SEGMENT_OUT_VERTEX_START_INDEX(8), SEGMENT_OUT_VERTEX_END_INDEX(8));
    segments[22] = fxctrlr.addSegment("7Y1", SEGMENT_IN_VERTEX_START_INDEX(7), SEGMENT_IN_VERTEX_END_INDEX(7));
    segments[23] = fxctrlr.addSegment("7Z1", SEGMENT_IN_VERTEX_START_INDEX(11), SEGMENT_IN_VERTEX_END_INDEX(11));
}