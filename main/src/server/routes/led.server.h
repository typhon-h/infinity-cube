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

// ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––

// Model
// Define model functions here...

#endif // LED_SERVER_H_