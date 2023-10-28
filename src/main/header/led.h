#ifndef _LED_H_
#define _LED_H_

#define FASTLED_INTERNAL

#include <FastLED.h>
#include <FastFX.h>
#include <stdint.h>
#include <stdbool.h>

#define LED_PIN 3 // TODO: Set pin
#define LED_TYPE NEOPIXEL
#define MAX_CURRENT_MA 2000 // TODO: Determine max draw - probably 2500ish
#define LED_OPERATING_V 5
#define DEFAULT_INTENSITY 255
#define DEFAULT_SATURATION 255

#define NUM_LEDS 120 // TODO: Now using full strip so set 120 and 10/edge

#define CUBE_EDGES 12
#define LED_PER_EDGE (NUM_LEDS / CUBE_EDGES)
#define INDEX_FROM_EDGE(X) (X * LED_PER_EDGE)

// Equations to determine start/end of a segment based on the data direction
//  and position in the strip. Broken into two catergories - DATA-OUT of a vertex | DATA-IN to a vertex
#define SEGMENT_OUT_VERTEX_START_INDEX(EDGE_NUM) (INDEX_FROM_EDGE(EDGE_NUM))
#define SEGMENT_OUT_VERTEX_END_INDEX(EDGE_NUM) (INDEX_FROM_EDGE(EDGE_NUM) + ((LED_PER_EDGE - 1) / 2))
#define SEGMENT_IN_VERTEX_START_INDEX(EDGE_NUM) (INDEX_FROM_EDGE(EDGE_NUM) + ((LED_PER_EDGE) / 2))
#define SEGMENT_IN_VERTEX_END_INDEX(EDGE_NUM) (INDEX_FROM_EDGE(EDGE_NUM) + LED_PER_EDGE - 1)

CRGB leds[NUM_LEDS];                     // Global LED state array
FFXController fxctrlr = FFXController(); // Global controller for effects

// TODO: Not sure if these will be used yet but allows a consistent way to 'group' edges on the same plane
typedef enum
{
  X = 'X',
  Y = 'Y',
  Z = 'Z'
} EDGE_PLANE;

// Define segments to be re-arranged into different symmetry patterns
FFXSegment *segments[CUBE_EDGES * 2];

void led_setup(void);

#endif // _LED_H_