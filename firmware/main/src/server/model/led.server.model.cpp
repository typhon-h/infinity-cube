#include "../routes/led.server.h"
#include "../../led/effect.h"
#include "../../led/led.h"

bool setName(String name)
{
    uint8_t newEffect = name.toInt();
    if (newEffect < NUM_EFFECTS)
    {
        currentEffect = (EFFECT_T)newEffect;
        return true;
    }

    return false;
}

bool setSpeed(String speed)
{
    uint8_t newSpeed = speed.toInt();
    if (newSpeed <= MAX_SPEED)
    {
        currentSpeed = newSpeed;
        return true;
    }

    return false;
}

bool setSymmetry(String symmetry)
{
    uint8_t newSymmetry = symmetry.toInt();
    if (newSymmetry < NUM_SYMMETRIES)
    {
        currentSymmetry = (SYMMETRY_T)newSymmetry;
        return true;
    }

    return false;
}

bool setDirection(String direction)
{
    uint8_t newDirection = direction.toInt();
    if (newDirection < NUM_DIRECTIONS)
    {
        // MovementType is 1-indexed for the ordinal mappings
        currentDirection = (FFXBase::MovementType)(newDirection + 1);
        return true;
    }

    return false;
}

bool setDotWidth(String width)
{
    uint8_t newDotWidth = width.toInt();
    if (newDotWidth <= MAX_DOT_WIDTH)
    {
        dotWidth = newDotWidth;
        return true;
    }

    return false;
}

bool setDotSpacing(String spacing)
{
    uint8_t newDotSpacing = spacing.toInt();
    if (newDotSpacing <= MAX_DOT_SPACING)
    {
        dotSpacing = newDotSpacing;
        return true;
    }

    return false;
}

bool setDotBlur(String blur)
{
    uint8_t newDotBlur = blur.toInt();
    if (newDotBlur <= MAX_DOT_BLUR)
    {
        dotBlur = newDotBlur;
        return true;
    }

    return false;
}

bool setMotionRange(String motion)
{
    uint8_t newMotionRange = motion.toInt();
    if (newMotionRange <= MAX_MOTION_RANGE)
    {
        motionRange = newMotionRange;
        return true;
    }

    return false;
}

bool setPalette(AsyncWebServerRequest *request, bool has_colors[4])
{
    CRGB gradient_colors[4] = {
        CRGB::Black,
        CRGB::Black,
        CRGB::Black,
        CRGB::Black};

    for (int i = 0; i < 4; i++)
    {
        if (has_colors[i])
        {
            gradient_colors[i] = string_to_crgb(request->arg("color" + String(i + 1)));
        }
    }

    currentPalette = CRGBPalette16(gradient_colors[0], gradient_colors[1], gradient_colors[2], gradient_colors[3]);
    return true;
}