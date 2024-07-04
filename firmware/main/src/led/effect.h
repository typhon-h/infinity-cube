#ifndef _EFFECT_H_
#define _EFFECT_H_

#include <stdint.h>
#include <string>

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
    TWINKLE,
    NUM_EFFECTS
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

const int MAX_SPEED = 255;
const int MAX_DOT_WIDTH = 255;
const int MAX_DOT_SPACING = 255;
const int MAX_DOT_BLUR = 255;
const int MAX_MOTION_RANGE = 255;
const int NUM_DIRECTIONS = 5; // not ideal but will do for now as enum is part of FFX

FFXBase *getEffect(uint16_t length);
FFXBase::MovementType getInverseDirection(FFXBase::MovementType dir);
std::string effectName(EFFECT_T effect);

#endif // _EFFECT_H_