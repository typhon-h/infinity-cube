#ifndef _ALEXA_H_
#define _ALEXA_H_

#include <Arduino.h>
#include "fauxmoESP.h"

#include "server.h"

extern fauxmoESP fauxmo;

void alexa_callback(void);
void alexa_setup(void);
void sync_alexa(void);

#endif // _ALEXA_H_