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
    server->onNotFound(notFound);

    // Set Wifi credentials
    server->on((String(BASE_ROUTE) + "/connect").c_str(), HTTP_POST, authorizeWifi);
}