#include <ESPAsyncWebServer.h>
#include "../server.h"
#include "base.server.h"

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
    server->on(strcat(BASE_ROUTE, "/connect"), HTTP_POST, authorizeWifi);
}