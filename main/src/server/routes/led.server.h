#ifndef LED_SERVER_H_
#define LED_SERVER_H_

#include <stdbool.h>

#include <ESPAsyncWebServer.h>
#include "../server.h"
#include "../../led/effect.h"

#define LED_ROUTE "/led"

void led_routes(AsyncWebServer *server);

// Controller
void activeEffect(AsyncWebServerRequest *request);
void setActiveEffect(AsyncWebServerRequest *request);
// ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––

// Model
bool setName(String name);
bool setSpeed(String speed);
bool setSymmetry(String symmetry);
bool setDirection(String direction);
bool setDotWidth(String width);
bool setDotSpacing(String spacing);
bool setDotBlur(String blur);
bool setMotionRange(String range);

#endif // LED_SERVER_H_