#include "symmetry.h"
#include "led.h"
#include "effect.h"

#include <FastFX.h>
#include <FFXCoreEffects.h>

#include <string>

/**
 * @brief Update the symmetry pattern based on the current value
 *
 */
void update_symmetry()
{
    switch (currentSymmetry)
    {
    case VERTEX:
        symmetry_vertex();
        break;
    case MIRROR:
        symmetry_mirror();
        break;
    case CYCLIC:
        symmetry_cyclic();
        break;
    case NONE: // fallthrough
    default:
        symmetry_none();
        break;
    }
}

/**
 * @brief Symmetry pattern for no symmetry (linear strip)
 *
 */
void symmetry_none()
{
    for (int segment = 0; segment < NUM_SEGMENTS; segment++)
    {
        FFXSegment *seg = segments[segment].segment;
        seg->setFX(getEffect(seg->getLength()));
    }
}

/**
 * @brief Symmetry pattern for vertex (out/in alternating)
 *
 */
void symmetry_vertex()
{
    for (int vertex = 0; vertex < CUBE_VERTICES; vertex++)
    {
        for (int segment = vertex * SEGMENT_PER_VERTEX; segment < ((vertex * SEGMENT_PER_VERTEX) + SEGMENT_PER_VERTEX); segment++)
        {
            FFXSegment *seg = segments[segment].segment;
            seg->setFX(getEffect(seg->getLength()));

            // Alternate direction of even/odd vertices
            // Reverse the fx direction of strips with flipped directions from vertex
            if ((vertex % 2 == 0 && segments[segment].direction == IN) || (vertex % 2 != 0 && segments[segment].direction == OUT))
            {
                seg->getFX()->setMovement(getInverseDirection(currentDirection));
            }
        }
    }
}

/**
 * @brief Symmetry pattern for mirror (same plane goes same direction)
 *
 */
void symmetry_mirror()
{
    for (int segment = 0; segment < NUM_SEGMENTS; segment++)
    {
        FFXSegment *seg = segments[segment].segment;
        seg->setFX(getEffect(seg->getLength()));
    }

    FFXSegment *toInvert[] = {
        fxctrlr.findSegment("1Z"),
        fxctrlr.findSegment("2Z"),

        fxctrlr.findSegment("4Z"),
        fxctrlr.findSegment("5Z"),

        fxctrlr.findSegment("2X"),
        fxctrlr.findSegment("3X"),

        fxctrlr.findSegment("5X"),
        fxctrlr.findSegment("6X")};

    for (int i = 0; i < sizeof(toInvert) / sizeof(toInvert[0]); i++)
    {
        toInvert[i]->getFX()->setMovement(getInverseDirection(currentDirection));
    }
}

/**
 * @brief Symmetry pattern for cyclic (circles but sides do a diamond thing)
 *
 */
void symmetry_cyclic()
{
    for (int segment = 0; segment < NUM_SEGMENTS; segment++)
    {
        FFXSegment *seg = segments[segment].segment;
        seg->setFX(getEffect(seg->getLength()));
    }

    FFXSegment *toInvert[] = {
        fxctrlr.findSegment("4X"),
        fxctrlr.findSegment("7X"),

        fxctrlr.findSegment("2X"),
        fxctrlr.findSegment("3X"),
    };

    for (int i = 0; i < sizeof(toInvert) / sizeof(toInvert[0]); i++)
    {
        toInvert[i]->getFX()->setMovement(getInverseDirection(currentDirection));
    }
}

/**
 * @brief Get the string name of a symmetry
 * 
 * @param symmetry symmetry to retrieve name of
 * @return std::string string name of the symmetry
 */
std::string symmetryName(SYMMETRY_T symmetry)
{
    switch (symmetry)
    {
    case NONE:
        return "none";
    case VERTEX:
        return "vertex";
    case MIRROR:
        return "mirror";
    case CYCLIC:
        return "cyclic";
    default:
        return "unknown";
    }
}