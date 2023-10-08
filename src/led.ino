#include "led.h"
#include <FastLED.h>

/**
 * @brief Initializes the strips within FastLED with default settings.
 *
 */
void led_setup()
{
    init_vertices();

    // FastLED Setup
    FastLED.addLeds<NEOPIXEL, LED_PIN>(leds, NUM_LEDS);

    FastLED.setBrightness(DEFAULT_INTENSITY);
    FastLED.setCorrection(TypicalLEDStrip);

    FastLED.setMaxPowerInVoltsAndMilliamps(LED_OPERATING_V, MAX_CURRENT_MA);

    FastLED.clear();
    FastLED.show();
}

void init_vertices()
{
    // T/B: Top/Bottom | L/R: Left/Right | F/B: Front/Back
    // BRB
    vertices[0].edges[VERTEX_X_EDGE] = (Edge_t){0, false};
    vertices[0].edges[VERTEX_Y_EDGE] = (Edge_t){7, false};
    vertices[0].edges[VERTEX_Z_EDGE] = (Edge_t){6, true};

    // BLB
    vertices[1].edges[VERTEX_X_EDGE] = (Edge_t){0, true};
    vertices[1].edges[VERTEX_Y_EDGE] = (Edge_t){1, false};
    vertices[1].edges[VERTEX_Z_EDGE] = (Edge_t){2, false};

    // BLF
    vertices[2].edges[VERTEX_X_EDGE] = (Edge_t){4, false};
    vertices[2].edges[VERTEX_Y_EDGE] = (Edge_t){3, false};
    vertices[2].edges[VERTEX_Z_EDGE] = (Edge_t){2, true};

    // BRF
    vertices[3].edges[VERTEX_X_EDGE] = (Edge_t){4, true};
    vertices[3].edges[VERTEX_Y_EDGE] = (Edge_t){5, false};
    vertices[3].edges[VERTEX_Z_EDGE] = (Edge_t){6, false};

    // TRB
    vertices[7].edges[VERTEX_X_EDGE] = (Edge_t){8, false};
    vertices[7].edges[VERTEX_Y_EDGE] = (Edge_t){7, true};
    vertices[7].edges[VERTEX_Z_EDGE] = (Edge_t){11, true};

    // TLB
    vertices[4].edges[VERTEX_X_EDGE] = (Edge_t){8, true};
    vertices[4].edges[VERTEX_Y_EDGE] = (Edge_t){1, true};
    vertices[4].edges[VERTEX_Z_EDGE] = (Edge_t){9, false};

    // TLF
    vertices[5].edges[VERTEX_X_EDGE] = (Edge_t){10, false};
    vertices[5].edges[VERTEX_Y_EDGE] = (Edge_t){3, true};
    vertices[5].edges[VERTEX_Z_EDGE] = (Edge_t){9, true};

    // TRF
    vertices[6].edges[VERTEX_X_EDGE] = (Edge_t){10, true};
    vertices[6].edges[VERTEX_Y_EDGE] = (Edge_t){5, true};
    vertices[6].edges[VERTEX_Z_EDGE] = (Edge_t){11, false};
}

// Example pattern just to see if things are working
void vertex_chase()
{
    Serial.println("Test");
}