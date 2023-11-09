#include <ESPAsyncWebServer.h>
#include "../server.h"
#include "backdoor.server.h"

/**
 * @brief Definition of routes for backdoor endpoints
 *
 * @param server the web server object to assign to
 */
void backdoor_routes(AsyncWebServer *server)
{
    server->on((String(BACKDOOR_ROUTE) + "/reset").c_str(), HTTP_GET, resetBoard);
}