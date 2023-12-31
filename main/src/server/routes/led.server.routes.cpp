#include <ESPAsyncWebServer.h>
#include "../server.h"
#include "led.server.h"

/**
 * @brief Definition of routes for backdoor endpoints
 *
 * @param server the web server object to assign to
 */
void led_routes(AsyncWebServer *server)
{
    server->on((String(LED_ROUTE)).c_str(), HTTP_GET, ledState);
    server->on((String(LED_ROUTE)).c_str(), HTTP_POST, setLedState);

    server->on((String(LED_ROUTE) + "/effect").c_str(), HTTP_GET, activeEffect);
    server->on((String(LED_ROUTE) + "/effect").c_str(), HTTP_POST, setActiveEffect);
}