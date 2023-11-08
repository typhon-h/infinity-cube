#ifndef _BACKDOOR_SERVER_H_
#define _BACKDOOR_SERVER_H_

#include <stdbool.h>

#include <ESPAsyncWebServer.h>
#include "../server.h"

#define BACKDOOR_ROUTE "/backdoor"

void backdoor_routes(AsyncWebServer *server);

// Controller
void resetBoard(AsyncWebServerRequest *request);

// ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––

// Model
bool clearPreferences(void);

#endif // _BACKDOOR_SERVER_H_