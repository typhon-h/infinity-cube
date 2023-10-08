#ifndef _LED_H_
#define _LED_H_

#include <FastLED.h>
#include <stdint.h>
#include <stdbool.h>

#define LED_PIN 3
#define MAX_CURRENT_MA 2000
#define LED_OPERATING_V 5

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
    Edge_t[EDGES_PER_VERTEX] edges;
} Vertex_t;

CRGB leds[NUM_LEDS];

// Define vertices - this is bad practice since it assumes CUBE_VERTICES = 8
// but is a safe assumption unless cubes stop being cubes ¯\_(ツ)_/¯
Vertex_t vertices[CUBE_VERTICES];

// T/B: Top/Bottom | L/R: Left/Right | F/B: Front/Back
// BRB
vertices[0].edges[VERTEX_X_EDGE] = {0, false};
vertices[0].edges[VERTEX_Y_EDGE] = {7, false};
vertices[0].edges[VERTEX_Z_EDGE] = {6, true};

// BLB
vertices[1].edges[VERTEX_X_EDGE] = {0, true};
vertices[1].edges[VERTEX_Y_EDGE] = {1, false};
vertices[1].edges[VERTEX_Z_EDGE] = {2, false};

// BLF
vertices[2].edges[VERTEX_X_EDGE] = {4, false};
vertices[2].edges[VERTEX_Y_EDGE] = {3, false};
vertices[2].edges[VERTEX_Z_EDGE] = {2, true};

// BRF
vertices[3].edges[VERTEX_X_EDGE] = {4, true};
vertices[3].edges[VERTEX_Y_EDGE] = {5, false};
vertices[3].edges[VERTEX_Z_EDGE] = {6, false};

// TRB
vertices[4].edges[VERTEX_X_EDGE] = {8, false};
vertices[4].edges[VERTEX_Y_EDGE] = {7, true};
vertices[4].edges[VERTEX_Z_EDGE] = {11, true};

// TLB
vertices[5].edges[VERTEX_X_EDGE] = {8, true};
vertices[5].edges[VERTEX_Y_EDGE] = {1, true};
vertices[5].edges[VERTEX_Z_EDGE] = {9, false};

// TLF
vertices[6].edges[VERTEX_X_EDGE] = {10, false};
vertices[6].edges[VERTEX_Y_EDGE] = {3, true};
vertices[6].edges[VERTEX_Z_EDGE] = {9, true};

// TRF
vertices[7].edges[VERTEX_X_EDGE] = {10, true};
vertices[7].edges[VERTEX_Y_EDGE] = {5, true};
vertices[7].edges[VERTEX_Z_EDGE] = {11, false};

#endif // _LED_H_