#include "effect.h"
#include "led.h"
#include "symmetry.h"

#include <FastFX.h>
#include <FFXCoreEffects.h>

#include <stdint.h>

// Default values ——————————————————————————————————

EFFECT_T currentEffect = CHASE;
FFXBase::MovementType currentDirection = FFXBase::MovementType::MVT_FORWARD;
CRGBPalette16 currentPalette = NamedPalettes::getInstance()["multi"];
SYMMETRY_T currentSymmetry = VERTEX;
uint8_t currentSpeed = 220;

// Effect Specific Defaults ——————————————————————————

// Chase Parameters
uint8_t dotWidth = 1;
uint8_t dotSpacing = 0;
uint8_t dotBlur = 0;

// Motion Parameters
uint8_t motionRange = LED_PER_EDGE; // TODO: figure out values

// ————————————————————————————————————————————————————

/**
 * @brief Get the Effect object based on current parameters
 *
 * @param length length of the segment to receive the effect
 * @return FFXBase* pointer to the effect
 */
FFXBase *getEffect(uint16_t length)
{
    FFXBase *effect = nullptr;

    switch (currentEffect)
    {
    case CHASE:
    {
        ChaseFX *chase = new ChaseFX(length);
        chase->setDotSpacing(dotWidth);
        chase->setBlurAmount(dotBlur);
        chase->setDotWidth(dotWidth);
        effect = chase;
    }
    break;
    case CYCLE:
        effect = new CycleFX(length);
        break;
    case CYLON:
        effect = new CylonFX(length);
        break;
    case JUGGLE:
        effect = new JuggleFX(length);
        break;
    case MOTION:
    {
        MotionFX *motion = new MotionFX(length);
        motion->setNormalizationRange(motionRange);
        effect = motion;
    }
    break;
    case PACIFICA:
        effect = new PacificaFX(length);
        break;
    case PALETTE:
        effect = new PaletteFX(length);
        break;
    case RAINBOW:
        effect = new RainbowFX(length);
        break;
    case TWINKLE:
        effect = new TwinkleFX(length);
        break;
    case SOLID: // Fallthrough / default case
    default:
        effect = new SolidFX(length);
        break;
    }

    effect->getFXColor().setPalette(currentPalette);
    effect->setSpeed(currentSpeed);
    return effect;
}

/**
 * @brief Get the inverse of the given direction
 *
 * @param dir direction to invert
 * @return FFXBase::MovementType inverted direction
 */
FFXBase::MovementType getInverseDirection(FFXBase::MovementType dir)
{
    FFXBase::MovementType inverse;
    switch (dir)
    {
    case FFXBase::MovementType::MVT_FORWARD:
        inverse = FFXBase::MovementType::MVT_BACKWARD;
        break;
    case FFXBase::MovementType::MVT_BACKWARD:
        inverse = FFXBase::MovementType::MVT_FORWARD;
        break;
    case FFXBase::MovementType::MVT_BACKFORTH:
        inverse = FFXBase::MovementType::MVT_BACKFORTH;
        break;
    case FFXBase::MovementType::MVT_RANDOM:
        inverse = FFXBase::MovementType::MVT_RANDOM;
        break;
    case FFXBase::MovementType::MVT_STILL:
        inverse = FFXBase::MovementType::MVT_STILL;
        break;
    default:
        inverse = FFXBase::MovementType::MVT_FORWARD;
    }

    return inverse;
}

/**
 * @brief Get the string name of an effect
 * 
 * @param effect effect to retrieve name of
 * @return std::string string name of the effect
 */
std::string effectName(EFFECT_T effect)
{
    switch (effect)
    {
    case CHASE:
        return "chase";
    case CYCLE:
        return "cycle";
    case CYLON:
        return "cylon";
    case JUGGLE:
        return "juggle";
    case MOTION:
        return "motion";
    case PACIFICA:
        return "pacifica";
    case PALETTE:
        return "palette";
    case RAINBOW:
        return "rainbow";
    case TWINKLE:
        return "twinkle";
    case SOLID:
        return "solid";
    default:
        return "unknown";
    }
}
