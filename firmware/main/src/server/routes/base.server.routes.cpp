#include <ESPAsyncWebServer.h>
#include "../server.h"
#include "base.server.h"
#include "../alexa.h"

/**
 * @brief Definition of routes for base endpoints
 *
 * @param server the web server object to assign to
 */
void base_routes(AsyncWebServer *server)
{
    // Fallback route if not available
    server->onNotFound([](AsyncWebServerRequest *request)
                       {
        String body = (request->hasParam("body", true)) ? request->getParam("body", true)->value() : String();
        if (fauxmo.process(request->client(), request->method() == HTTP_GET, request->url(), body)) return;
        notFound(request); });

    // Set Wifi credentials
    server->on((String(BASE_ROUTE) + "/connect").c_str(), HTTP_POST, authorizeWifi);

    server->onRequestBody([](AsyncWebServerRequest *request, uint8_t *data, size_t len, size_t index, size_t total)
                          {
        if (fauxmo.process(request->client(), request->method() == HTTP_GET, request->url(), String((char *)data)))
            return; });
}