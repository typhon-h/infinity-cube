#ifndef _LED_H_
#define _LED_H_

#define FASTLED_INTERNAL
#define FASTLED_ALLOW_INTERRUPTS 0
#define FASTLED_INTERRUPT_RETRY_COUNT 0

#include <FastLED.h>
#include <FastFX.h>
#include <stdint.h>
#include <stdbool.h>

#define LED_PIN 13 // TODO: Set pin
#define LED_TYPE NEOPIXEL
#define MAX_CURRENT_MA 2000 // TODO: Determine max draw - probably 2500ish
#define LED_OPERATING_V 5
#define DEFAULT_INTENSITY 254
#define DEFAULT_SATURATION 255
#define SEGMENT_OPACITY 255

#define NUM_LEDS 120

#define CUBE_EDGES 12
#define CUBE_VERTICES 8
#define LED_PER_EDGE (NUM_LEDS / CUBE_EDGES)
#define INDEX_FROM_EDGE(X) (X * LED_PER_EDGE)

#define NUM_SEGMENTS (CUBE_EDGES * 2)

// Equations to determine start/end of a segment based on the data direction
//  and position in the strip. Broken into two catergories - DATA-OUT of a vertex | DATA-IN to a vertex
#define SEGMENT_OUT_VERTEX_START_INDEX(EDGE_NUM) (INDEX_FROM_EDGE(EDGE_NUM))
#define SEGMENT_OUT_VERTEX_END_INDEX(EDGE_NUM) (INDEX_FROM_EDGE(EDGE_NUM) + ((LED_PER_EDGE - 1) / 2))
#define SEGMENT_IN_VERTEX_START_INDEX(EDGE_NUM) (INDEX_FROM_EDGE(EDGE_NUM) + ((LED_PER_EDGE) / 2))
#define SEGMENT_IN_VERTEX_END_INDEX(EDGE_NUM) (INDEX_FROM_EDGE(EDGE_NUM) + LED_PER_EDGE - 1)
#define SEGMENT_PER_VERTEX 3

extern CRGB leds[NUM_LEDS];   // Global LED state array
extern FFXController fxctrlr; // Global controller for effects

// TODO: Not sure if these will be used yet but allows a consistent way to 'group' edges on the same plane
typedef enum
{
  X = 'X',
  Y = 'Y',
  Z = 'Z'
} EDGE_PLANE;

// Enum to describe if the data of a segment is going into/out of a vertex
typedef enum
{
  OUT,
  IN
} STRIP_DIRECTION;

typedef struct
{
  FFXSegment *segment;
  EDGE_PLANE plane;
  STRIP_DIRECTION direction;
} Section_t;

// Define segments to be re-arranged into different symmetry patterns
extern Section_t segments[NUM_SEGMENTS];

extern uint8_t currentIntensity;

void led_setup(void);
void sync_led(void);
void disable_led(void);

CRGB string_to_crgb(String color);

extern bool led_state;

#endif // _LED_H_