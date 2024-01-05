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
        currentDirection = (FFXBase::MovementType)newDirection;
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

bool setPalette(String colors)
{
    // CRGB values[4];
    // char colors_char[colors.length() + 1];
    // colors.toCharArray(colors_char, sizeof(colors_char));

    // char *token = strtok(colors_char, ",");

    size_t i = 1; // 0
    // while (i < 4 && token != NULL) // TODO: handling for invalid colors instead of black
    // {
    //     values[i] = string_to_crgb(token);

    //     token = strtok(NULL, ",");
    //     i++;
    // }

    CRGBPalette16 palette;
    switch (i)
    {
    case 1:
        palette = CRGBPalette16(CRGB::Red); // values[0]
        break;
    // case 2:
    //     palette = CRGBPalette16(values[0], values[1]);
    //     break;
    // case 3:
    //     palette = CRGBPalette16(values[0], values[1], values[2]);
    //     break;
    // case 4:
    //     palette = CRGBPalette16(values[0], values[1], values[2], values[3]);
    //     break;
    default:
        return false;
    }
    currentPalette = palette;
    return true;
}