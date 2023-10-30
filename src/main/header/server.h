#ifndef _SERVER_H_
#define _SERVER_H_

#include <WiFi.h>
#include <AsyncTCP.h>
#include <ESPAsyncWebServer.h>

#define STATUS_OK 200
#define STATUS_BAD_REQUEST 400
#define STATUS_NOT_FOUND 404

boolean connectWifi(void);
void assign_routes(AsyncWebServer *server);
void server_setup(void);

AsyncWebServer server(80);

#endif // _SERVER_H_