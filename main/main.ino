#include "preferences.h"
#include "src/led/led.h"
#include "src/tasks/tasks.h"
#include "src/server/server.h"
#include "src/server/alexa.h"

Preferences preferences;

void setup()
{
  // Safety to allow re-programming
  delay(3000);

  // Initialise Serial for the simulator
  Serial.begin(115200);

  // Initialize the LEDs
  led_setup();

  // Initialise the server for REST API interaction
  server_setup();

  // Register Alexa commands
  alexa_setup();

  // Set up the schedulers
  scheduler_setup();
}

void loop()
{
  // Unused - Handled by FreeRTOS on independent cores
}
