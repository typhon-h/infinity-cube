#include <ESPAsyncWebServer.h>
#include "status_code.h"
#include "../../../preferences.h"
#include "../routes/base.server.h"
#include "../alexa.h"

/**
 * @brief Set wifi credentials then reboot board to try to connect
 *
 * @param request the Web request object
 */
void authorizeWifi(AsyncWebServerRequest *request)
{
    if (request->hasParam(SSID_PREF) && request->hasParam(PASSWORD_PREF))
    {
        setWifiCredentials(request->getParam(SSID_PREF)->value(), request->getParam(PASSWORD_PREF)->value());
        request->send(STATUS_OK, "text/plain", "Wifi credentials updated successfully");
        delay(1000); // Buffer to allow response to send
        ESP.restart();
    }
    else
    {
        request->send(STATUS_BAD_REQUEST, "text/plain", "Missing SSID or PASSWORD");
    }
}

/**
 * @brief General fallback response for unknown endpoint
 *
 * @param request the Web request object
 */
void notFound(AsyncWebServerRequest *request)
{
    String body = (request->hasParam("body", true)) ? request->getParam("body", true)->value() : String();
    if (fauxmo.process(request->client(), request->method() == HTTP_GET, request->url(), body))
        return;
    request->send(STATUS_NOT_FOUND, "text/plain", "Doctor, are you there?");
}