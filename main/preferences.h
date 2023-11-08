#ifndef _PREFS_H_
#define _PREFS_H_

#include <Preferences.h>
#include <stdbool.h>

#define DEVICE_NAME "Infinity Cube"
#define SSID_PREF "SSID"
#define PASSWORD_PREF "PASSWORD"

extern Preferences preferences;

#define READ_ONLY true
#define READ_WRITE false

#endif _PREFS_H_