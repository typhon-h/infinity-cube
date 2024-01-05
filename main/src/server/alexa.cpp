
#include "alexa.h"
#include "server.h"

fauxmoESP fauxmo;

void alexa_callback()
{
    fauxmo.handle();
}

void alexa_setup()
{
    fauxmo.createServer(false);
    fauxmo.setPort(80); // This is required for gen3 devices
    fauxmo.enable(true);
    fauxmo.addDevice(DEVICE_NAME);

    fauxmo.onSetState([](unsigned char device_id, const char *device_name, bool state, unsigned char value, unsigned int hue, unsigned int saturation, unsigned int ct)
                      {
                          // Callback when a command from Alexa is received.
                          // You can use device_id or device_name to choose the element to perform an action onto (relay, LED,...)
                          // State is a boolean (ON/OFF) and value a number from 0 to 255 (if you say "set kitchen light to 50%" you will receive a 128 here).
                          // Just remember not to delay too much here, this is a callback, exit as soon as possible.
                          // If you have to do something more involved here set a flag and process it in your main loop.

                          // if (0 == device_id) digitalWrite(RELAY1_PIN, state);
                          // if (1 == device_id) digitalWrite(RELAY2_PIN, state);
                          // if (2 == device_id) analogWrite(LED1_PIN, value);

                          Serial.printf("[MAIN] Device #%d (%s) state: %s value: %d\n", device_id, device_name, state ? "ON" : "OFF", value);
                      });
}