#ifndef _SERVER_H_
#define _SERVER_H_

#include <WiFi.h>
#include <AsyncTCP.h>
#include <ESPAsyncWebServer.h>

boolean connectWifi(void);
void assign_routes(AsyncWebServer *server);
void server_setup(void);

AsyncWebServer server(80);

#endif // _SERVER_H_