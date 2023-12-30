#ifndef _SYMMETRY_H_
#define _SYMMETRY_H_

#include <FastFX.h>
#include <FFXCoreEffects.h>

typedef enum
{
    NONE,
    VERTEX,
    MIRROR,
    CYCLIC,
    NUM_SYMMETRIES
} SYMMETRY_T;

std::string symmetryName(SYMMETRY_T symmetry);

void update_symmetry(void);

// Symmetry functions
void symmetry_none(void);
void symmetry_vertex(void);
void symmetry_mirror(void);
void symmetry_cyclic(void);

#endif // _SYMMETRY_H_