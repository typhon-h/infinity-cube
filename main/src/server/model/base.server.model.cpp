#include "../routes/base.server.h"
#include "../../../preferences.h"

#include <stdbool.h>

/**
 * @brief Sets the stored wifi credentials
 *
 * @param ssid name of the network to join
 * @param password password of the network to join
 * @return true if the credentials were updated successfully
 * @return false if the credentials were not updated
 */
bool setWifiCredentials(String ssid, String password)
{
    preferences.begin(DEVICE_NAME, READ_WRITE);

    preferences.remove(SSID_PREF);
    preferences.remove(PASSWORD_PREF);

    preferences.putString(SSID_PREF, ssid);
    preferences.putString(PASSWORD_PREF, password);

    bool wasSuccess = preferences.getString(SSID_PREF, "") == ssid && preferences.getString(PASSWORD_PREF, "") == password;

    preferences.end();

    return wasSuccess;
}