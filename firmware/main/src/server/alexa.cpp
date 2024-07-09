
#include "alexa.h"
#include "server.h"
#include "../led/led.h"
#include "../led/effect.h"
#include "../../userpreferences.h"

Espalexa espalexa;
EspalexaDevice *alexa_device;

void alexa_callback()
{
    espalexa.loop();
}

void device_handler(EspalexaDevice *device)
{
    if (device == nullptr)
        return;

    currentIntensity = device->getLastValue();
    led_state = device->getState();

    // only update if the color has been changed to prevent custom palettes being overwritten by state/intensity changes
    CRGB color = ColorFromPalette(currentPalette, 0);
    if (device->getR() != color.r || device->getG() != color.g || device->getB() != color.b)
        currentPalette = CRGBPalette16(CRGB(device->getR(), device->getG(), device->getB()));

    sync_led();
}

void sync_alexa()
{
    alexa_device->setState(led_state);
    alexa_device->setValue(currentIntensity);

    CRGB color = ColorFromPalette(currentPalette, 0);
    alexa_device->setColor(color.r, color.g, color.b);

    sync_led();
}

void alexa_setup()
{
    alexa_device = new EspalexaDevice(DEVICE_NAME, device_handler, EspalexaDeviceType::color);

    espalexa.addDevice(alexa_device);
    espalexa.begin(&server);

    sync_alexa();
}