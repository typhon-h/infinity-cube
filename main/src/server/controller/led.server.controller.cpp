#include <ESPAsyncWebServer.h>
#include "status_code.h"
#include "../routes/led.server.h"
#include "../../led/effect.h"
#include <FastFX.h>
#include <ArduinoJson.h>

void activeEffect(AsyncWebServerRequest *request)
{
    StaticJsonDocument<255> body;

    body["name"] = effectName(currentEffect);
    body["speed"] = currentSpeed;
    body["symmetry"] = symmetryName(currentSymmetry);
    body["direction"] = FFXBase::movementTypeStr(currentDirection);
    body["dot_width"] = dotWidth;
    body["dot_spacing"] = dotSpacing;
    body["dotBlur"] = dotBlur;
    body["motion_range"] = motionRange;

    String response;
    serializeJson(body, response);

    request->send(STATUS_OK, "application/json", response);
}