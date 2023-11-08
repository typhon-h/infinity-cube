#ifndef BASE_SERVER_H_
#define BASE_SERVER_H_

#include <stdbool.h>

#include <ESPAsyncWebServer.h>
#include "../server.h"

#define BASE_ROUTE "/"

void base_routes(AsyncWebServer *server);

// Controller
void authorizeWifi(AsyncWebServerRequest *request);
void notFound(AsyncWebServerRequest *request);

// ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––

// Model
bool setWifiCredentials(String ssid, String password);

#endif // BASE_SERVER_H_