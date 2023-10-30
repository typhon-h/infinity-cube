#include "header/preferences.h"
#include "header/led.h"
#include "header/tasks.h"
#include "header/server.h"

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

  // Set up the schedulers
  scheduler_setup();
}

void loop()
{
  // Unused - Handled by FreeRTOS on independent cores
}
