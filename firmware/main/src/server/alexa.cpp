
#include "alexa.h"
#include "server.h"
#include "../led/led.h"
#include "../led/effect.h"
#include "../../userpreferences.h"

fauxmoESP fauxmo;

void alexa_callback()
{
    fauxmo.handle();
}

void device_handler(unsigned char device_id, const char *device_name, bool state, unsigned char value, unsigned int hue, unsigned int saturation, unsigned int ct)
{
    currentIntensity = value;
    // TODO: currently have to go custom -> other(alexa) -> white instead of custom -> white???
    if (saturation != 1) // use 1 as custom color sat - unlikely to occur in actual color
    {
        currentPalette = CRGBPalette16(CRGB(fauxmo.getRed(device_id), fauxmo.getGreen(device_id), fauxmo.getBlue(device_id)));
    }

    led_state = state;

    sync_led();
}

void sync_alexa()
{
    fauxmo.setState(static_cast<unsigned char>(0), static_cast<bool>(led_state), static_cast<unsigned char>(currentIntensity));

    sync_led();
}

void alexa_setup()
{
    fauxmo.createServer(false);
    fauxmo.setPort(PORT); // This is required for gen3 devices
    fauxmo.enable(true);
    fauxmo.addDevice(DEVICE_NAME);

    fauxmo.onSetState(device_handler);

    sync_alexa();
}