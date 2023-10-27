"""
Quick PyGame + OpenGL emulator to read LED values from a Serial port and display
them on a rotatable cube.

This is primarily ChatGPT generated - with the exception of the serial integrations
Honestly pretty impressed at how well it was able to build it :D
"""

import sys
import random
import pygame
import serial
from pygame.locals import *
from OpenGL.GL import *
from OpenGL.GLUT import *
from OpenGL.GLU import *


SERIAL_MODE = True  # Toggle this to disable serial
TOTAL_LEDS = 108
CUBE_EDGES = 12

if SERIAL_MODE:
    PORT = sys.argv[1]
    arduino = serial.Serial(port=PORT, baudrate=115200, timeout=0.1, dsrdtr=False, rtscts=False)

# Starting bottom right going anti-clockwise.
# Arranged in even/odd pattern
vertices = (
    (1, -1, -1),
    (-1, -1, -1),
    (-1, -1, 1),
    (1, -1, 1),
    (-1, 1, -1),
    (-1, 1, 1),
    (1, 1, 1),
    (1, 1, -1),
)

edges = (  # Order of edges as defined in wiring diagram
    (0, 7),
    (4, 7),
    (4, 5),
    (5, 6),
    (4, 1),
    (1, 2),
    (1, 0),
    (0, 3),
    (3, 6),
    (7, 6),
    (5, 2),
    (2, 3),
)


def generate_edges(start_vertex, end_vertex):
    """
    Splits edges between vertex A and B into number of individually addressable LEDs
    """
    segments = []
    num_segments = int(TOTAL_LEDS / CUBE_EDGES)
    for i in range(num_segments):
        t1 = i / num_segments
        t2 = (i + 1) / num_segments
        x1 = start_vertex[0] + t1 * (end_vertex[0] - start_vertex[0])
        y1 = start_vertex[1] + t1 * (end_vertex[1] - start_vertex[1])
        z1 = start_vertex[2] + t1 * (end_vertex[2] - start_vertex[2])
        x2 = start_vertex[0] + t2 * (end_vertex[0] - start_vertex[0])
        y2 = start_vertex[1] + t2 * (end_vertex[1] - start_vertex[1])
        z2 = start_vertex[2] + t2 * (end_vertex[2] - start_vertex[2])
        color = (0.1, 0.1, 0.1)  # Initialise as arbitrary grey
        segments.append(((x1, y1, z1), (x2, y2, z2), color))
    return segments


def draw_cube(led_values, segments):
    """
    Updates the cube with LED values from the serial port
    data in the form of a list
    R0,G0,B0,R1,G1,B1...
    """
    glEnable(GL_LINE_SMOOTH)

    glLineWidth(11.0)  # Slightly thicker lines for the outline

    # Draw edges with a contrasting color for the outline
    glBegin(GL_LINES)
    for segment in segments:
        glColor3f(0.2, 0.2, 0.2)
        glVertex3fv(segment[0])
        glVertex3fv(segment[1])
    glEnd()

    glLineWidth(10.0)

    # Draw edge interior with the desired color
    glBegin(GL_LINES)

    i = 0
    for segment in segments:
        glColor3f(
            int(led_values[i]) / 255,
            int(led_values[i + 1]) / 255,
            int(led_values[i + 2]) / 255,
        )
        i += 3
        glVertex3fv(segment[0])
        glVertex3fv(segment[1])
    glEnd()

    glDisable(GL_LINE_SMOOTH)


def draw_vertex_numbers():
    """
    Displays the number of each visual vertex to keep track of rotation easier
    """
    font = GLUT_BITMAP_TIMES_ROMAN_24
    glColor3f(1.0, 1.0, 1.0)  # Set font color to white
    offset = 0.1  # Offset from the vertices
    for i, vertex in enumerate(vertices):
        x, y, z = vertex
        glRasterPos3f(x + offset, y + offset, z + offset)  # Adjust position
        for char in str(i):
            glutBitmapCharacter(font, ord(char))


def main():
    pygame.init()
    display = (800, 600)
    pygame.display.set_mode(display, DOUBLEBUF | OPENGL)
    gluPerspective(45, (display[0] / display[1]), 0.1, 50.0)
    glTranslatef(0.0, 0.0, -5)

    clock = pygame.time.Clock()

    segments = []  # Edges split into led strips

    # Generate segments and colors
    for edge in edges:
        start_vertex = vertices[edge[0]]
        end_vertex = vertices[edge[1]]
        leds = generate_edges(start_vertex, end_vertex)
        segments.extend(leds)

    while True:
        # Poll events to rotate the cube
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                quit()

            if event.type == pygame.MOUSEBUTTONDOWN:
                if event.button == 4:
                    glTranslatef(0.0, 0.0, 1.0)
                if event.button == 5:
                    glTranslatef(0.0, 0.0, -1.0)

            if event.type == pygame.MOUSEMOTION:
                if event.buttons[0] == 1:
                    x, y = event.rel
                    glRotatef(x, 0, 1, 0)
                    glRotatef(y, 1, 0, 0)

        # Read serial data
        if SERIAL_MODE:
            data = arduino.readline()
            if len(data) == 0:
                print("Serial Empty")
                continue
            try:
                led_values = data.decode("utf-8").split(",")[:-1]
            except UnicodeDecodeError:
                print("Invalid Data")
                continue
        else:
            # Fallback
            led_values = [
                str(random.choice(range(255))) for _ in range(108 * 3)
            ]  # num_leds * leds_per_channel

        # Clear screen and redraw
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
        try:
            draw_cube(led_values, segments)
        except (IndexError, GLError, ValueError):
            print("Missing Data")
        draw_vertex_numbers()  # Add this line to draw vertex numbers

        pygame.display.flip()
        clock.tick(60)


if __name__ == "__main__":
    main()
