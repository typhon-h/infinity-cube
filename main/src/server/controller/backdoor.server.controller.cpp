#include <ESPAsyncWebServer.h>
#include "status_code.h"
#include "../routes/backdoor.server.h"

/**
 * @brief Clears stored preferences and reboots the board
 *
 * @param request the Web request object
 */
void resetBoard(AsyncWebServerRequest *request)
{
    if(clearPreferences()) {
        request->send(STATUS_OK, "text/plain", "Preferences Cleared.");
        delay(1000); // Buffer to allow response to send
        ESP.restart();
    } else {
        request->send(STATUS_INTERNAL_SERVER_ERROR, "text/plain", "Could not clear preferences.");
    }
}