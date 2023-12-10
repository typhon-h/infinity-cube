#include <WiFi.h>
#include <AsyncTCP.h>
#include <ESPAsyncWebServer.h>
#include "server.h"

#include "routes/backdoor.server.h"
#include "routes/base.server.h"
#include "routes/led.server.h"

#include "../../preferences.h"

AsyncWebServer server(80); // Reference to web server

/**
 * @brief Connect to network and assign routes
 *
 */
void server_setup()
{
  if (connectWifi())
  {
    server.begin(); // Async so determines best core to run on based on load

    // Define routes
    backdoor_routes(&server);
    base_routes(&server);
    led_routes(&server);
  }
}

/**
 * @brief Boot a SoftAP server
 *
 * @return true if successful, false otherwise
 */
boolean connectSoftAP()
{
  WiFi.mode(WIFI_AP); // Set WiFi mode to Access Point

  bool state = WiFi.softAP(DEVICE_NAME);

  if (state)
  {
    Serial.println("Access Point Enabled");
    Serial.print("IP address: ");
    Serial.println(WiFi.softAPIP());
  }
  else
  {
    Serial.println("Something went wrong...");
    Serial.println("Could not initialise Access Point");
  }

  return state;
}

/**
 * @brief Establish WIFI connection - Boots SoftAP if credentials invalid
 *
 * @return true if successful, false otherwise
 */
boolean connectWifi()
{
  boolean state = true;

  WiFi.mode(WIFI_STA);

  preferences.begin(DEVICE_NAME, READ_ONLY);
  WiFi.begin(preferences.getString(SSID_PREF, ""), preferences.getString(PASSWORD_PREF, ""));
  preferences.end();

  Serial.println("");
  Serial.println("Connecting to WiFi");

  // Wait for connection
  Serial.print("Connecting...");
  int i = 0;
  while (WiFi.status() != WL_CONNECTED)
  {
    delay(500);
    Serial.print(".");
    if (i > 20)
    {
      state = false;
      break;
    }
    i++;
  }

  if (state)
  {
    Serial.print("\nConnected to ");
    Serial.println(WiFi.SSID());
    Serial.print("IP address: ");
    Serial.println(WiFi.localIP());
  }
  else
  {
    Serial.println("Connection Failed\n");
    WiFi.disconnect();

    state = connectSoftAP();
  }
  delay(100);
  return state;
}