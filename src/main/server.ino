#include <WiFi.h>
#include <AsyncTCP.h>
#include <ESPAsyncWebServer.h>
#include "header/server.h"
#include "header/env.h" // Temporary ENV for wifi credentials

void server_setup()
{
  connectWifi();

  assign_routes(&server);
  server.begin(); // Async so determines best core to use based on load
}

// TODO: This is stolen - refactor to be something actually useful
//  implement version of this but defaults to a softAP if no connect

// connect to wifi – returns true if successful or false if not and softap was booted
boolean connectWifi()
{
  boolean state = true;
  int i = 0;

  WiFi.mode(WIFI_STA);
  WiFi.begin(SSID, PASSWORD);
  Serial.println("");
  Serial.println("Connecting to WiFi");

  // Wait for connection
  Serial.print("Connecting...");
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
  Serial.println("");
  if (state)
  {
    Serial.print("Connected to ");
    Serial.println(SSID);
    Serial.print("IP address: ");
    Serial.println(WiFi.localIP());
  }
  else
  {
    Serial.println("Connection Failed");
    Serial.println("");
    WiFi.disconnect();

    WiFi.mode(WIFI_AP); // Set WiFi mode to Access Point
    if (WiFi.softAP("Infinity Cube"))
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
  }
  delay(100);
  return state;
}

// Sets up the server endpoints
void assign_routes(AsyncWebServer *server)
{
  server->on("/", HTTP_GET, [](AsyncWebServerRequest *request)
             { request->send(200, "text/plain", "This is an example index page your server may send."); });

  server->on("/test", HTTP_GET, [](AsyncWebServerRequest *request)
             { request->send(200, "text/plain", "This is a second subpage you may have."); });

  server->onNotFound([](AsyncWebServerRequest *request)
                     { request->send(404, "text/plain", "Not found"); });
}
