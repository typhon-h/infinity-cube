#include "../routes/backdoor.server.h"
#include "../../../userpreferences.h"
#include <stdbool.h>

/**
 * @brief Clears the stored preferences
 *
 * @return true if preferences were cleared
 * @return false if preferences were not cleared
 */
bool clearPreferences()
{
    preferences.begin(DEVICE_NAME, READ_WRITE);

    bool wasSuccess = preferences.clear();

    preferences.end();

    return wasSuccess;
}