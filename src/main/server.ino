#include <WiFi.h>
#include <AsyncTCP.h>
#include <ESPAsyncWebServer.h>
#include "header/server.h"
#include "header/preferences.h"

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

  Serial.println("");
  if (state)
  {
    Serial.print("Connected to ");
    Serial.println(WiFi.SSID());
    Serial.print("IP address: ");
    Serial.println(WiFi.localIP());
  }
  else
  {
    Serial.println("Connection Failed");
    Serial.println("");
    WiFi.disconnect();

    WiFi.mode(WIFI_AP); // Set WiFi mode to Access Point
    if (WiFi.softAP(DEVICE_NAME))
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

  server->on("/reconnect", HTTP_POST, [](AsyncWebServerRequest *request)
             {
              if (request->hasParam(SSID_PREF) && request->hasParam(PASSWORD_PREF)) {
                setWifiCredentials(request->getParam(SSID_PREF)->value(), request->getParam(PASSWORD_PREF)->value());
                request->send(200, "text/plain", "Updated. Rebooting...");
                ESP.restart();
              } else {
                request->send(400, "text/plain", "Bad Request");
              } });

  server->onNotFound([](AsyncWebServerRequest *request)
                     { request->send(404, "text/plain", "Not found"); });
}

void setWifiCredentials(String ssid, String password)
{
  preferences.begin(DEVICE_NAME, READ_WRITE);

  preferences.remove(SSID_PREF);
  preferences.remove(PASSWORD_PREF);

  preferences.putString(SSID_PREF, ssid);
  preferences.putString(PASSWORD_PREF, password);
  
  preferences.end();
}
