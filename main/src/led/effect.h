#ifndef _EFFECT_H_
#define _EFFECT_H_

#include <stdint.h>
#include "symmetry.h"

#include <FastFX.h>
#include <FFXCoreEffects.h>

typedef enum
{ // Available effects
    CHASE,
    CYCLE,
    CYLON,
    JUGGLE,
    MOTION,
    PACIFICA,
    PALETTE,
    RAINBOW,
    SOLID,
    TWINKLE
} EFFECT_T;

// Chase Parameters
extern uint8_t dotWidth;
extern uint8_t dotSpacing;
extern uint8_t dotBlur;

// Motion Parameters
extern uint8_t motionRange;

extern EFFECT_T currentEffect;

extern FFXBase::MovementType currentDirection;
extern CRGBPalette16 currentPalette;
extern SYMMETRY_T currentSymmetry;
extern uint8_t currentSpeed;

FFXBase *getEffect(uint16_t length);
FFXBase::MovementType getInverseDirection(FFXBase::MovementType dir);
std::string effectName(EFFECT_T effect);

#endif // _EFFECT_H_