#include <ESPAsyncWebServer.h>
#include "status_code.h"
#include "../routes/led.server.h"
#include "../../led/effect.h"
#include "../../led/led.h"
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

void setActiveEffect(AsyncWebServerRequest *request)
{
    bool isValid = true;

    EFFECT_T oldName = currentEffect;
    FFXBase::MovementType oldDirection = currentDirection;
    SYMMETRY_T oldSymmetry = currentSymmetry;
    CRGBPalette16 oldPalette = currentPalette;
    uint8_t oldSpeed = currentSpeed;
    uint8_t oldDotWidth = dotWidth;
    uint8_t oldDotSpacing = dotSpacing;
    uint8_t oldDotBlur = dotBlur;
    uint8_t oldMotionRange = motionRange;

    if (request->hasParam("name"))
    {
        isValid = setName(request->arg("name"));
    }

    if (isValid && request->hasParam("speed"))
    {
        isValid = setSpeed(request->arg("speed"));
    }

    if (isValid && request->hasParam("symmetry"))
    {
        isValid = setSymmetry(request->arg("symmetry"));
    }

    if (isValid && request->hasParam("direction"))
    {
        isValid = setDirection(request->arg("direction"));
    }

    if (isValid && request->hasParam("dotWidth"))
    {
        isValid = setDotWidth(request->arg("dotWidth"));
    }

    if (isValid && request->hasParam("dotSpacing"))
    {
        isValid = setDotSpacing(request->arg("dotSpacing"));
    }

    if (isValid && request->hasParam("dotBlur"))
    {
        isValid = setDotBlur(request->arg("dotBlur"));
    }

    if (isValid && request->hasParam("motionRange"))
    {
        isValid = setMotionRange(request->arg("motionRange"));
    }

    if (isValid && request->hasParam("color"))
    {
        isValid = setPalette(request->arg("color"));
    }

    if (isValid)
    {
        sync_led();
        request->send(STATUS_OK, "text/plain", "Effect updated successfully.");
    }
    else
    {
        currentEffect = oldName;
        currentDirection = oldDirection;
        currentSymmetry = oldSymmetry;
        currentSpeed = oldSpeed;
        dotWidth = oldDotWidth;
        dotSpacing = oldDotSpacing;
        dotBlur = oldDotBlur;
        motionRange = oldMotionRange;
        currentPalette = oldPalette;
        //  TODO: update this to be more specific about what went wrong
        request->send(STATUS_BAD_REQUEST, "text/plain", "Effect update failed. One or more fields are malformed.");
    }
}

void ledState(AsyncWebServerRequest *request)
{
    StaticJsonDocument<255> body;

    body["power"] = led_state;
    body["intensity"] = currentIntensity;

    String response;
    serializeJson(body, response);

    request->send(STATUS_OK, "application/json", response);
}

void setLedState(AsyncWebServerRequest *request)
{
    bool isValid = true;

    uint8_t oldIntensity = currentIntensity;

    if (request->hasParam("intensity"))
    {
        uint8_t intensity = request->arg("intensity").toInt();
        currentIntensity = intensity;
        sync_led();
    }

    if (request->hasParam("power"))
    {
        bool power = request->arg("power").toInt();
        led_state = power;
    }

    // TODO: update to notify per setting
    request->send(STATUS_OK, "text/plain", "LED status updated successfully");
}