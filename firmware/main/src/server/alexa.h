#ifndef _ALEXA_H_
#define _ALEXA_H_

#include <Arduino.h>
#define ESPALEXA_ASYNC
#define ESPALEXA_NO_SUBPAGE
#define ESPALEXA_MAXDEVICES 1
#include <Espalexa.h>

#include "server.h"

extern Espalexa espalexa;
extern EspalexaDevice *alexa_device;

void alexa_callback(void);
void alexa_setup(void);
void sync_alexa(void);

#endif // _ALEXA_H_