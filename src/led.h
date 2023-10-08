#ifndef _LED_H_
#define _LED_H_

#include <FastLED.h>
#include <stdint.h>
#include <stdbool.h>

#define LED_PIN 3
#define MAX_CURRENT_MA 2000
#define LED_OPERATING_V 5
#define DEFAULT_INTENSITY 255
#define DEFAULT_SATURATION 255

#define NUM_LEDS 108

#define CUBE_EDGES 12
#define LED_PER_EDGE (NUM_LEDS / CUBE_EDGES)
#define EDGE(X) (X * LED_PER_EDGE)

#define CUBE_VERTICES 8
#define EDGES_PER_VERTEX 3
// Not sure if these will be used yet but allows a consistent way to 'group' edges on the same plane
#define VERTEX_X_EDGE 0
#define VERTEX_Y_EDGE 1
#define VERTEX_Z_EDGE 2

// Edge object to make vertices easier to handle
typedef struct
{
    // Edge number (0-11) from edge ordering diagram
    uint8_t edge_num;

    // True if the edge data goes 'into the vertex' instead of 'out of the vertex'
    bool reversed;
} Edge_t;

typedef struct
{
    Edge_t edges[EDGES_PER_VERTEX];
} Vertex_t;

// Define vertices - this is bad practice since it assumes CUBE_VERTICES = 8
// but is a safe assumption unless cubes stop being cubes ¯\_(ツ)_/¯
Vertex_t vertices[CUBE_VERTICES];

CRGB leds[NUM_LEDS];

void led_setup(void);
void vertex_chase(void);

void (*led_patterns[])(void) = {vertex_chase}; // Add more patterns here

#endif // _LED_H_