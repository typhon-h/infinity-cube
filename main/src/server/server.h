#ifndef _SERVER_H_
#define _SERVER_H_

#include <WiFi.h>
#include <AsyncTCP.h>
#include <ESPAsyncWebServer.h>

extern AsyncWebServer server;

void server_setup(void);
boolean connectWifi(void);

#endif // _SERVER_H_