#!/usr/bin/env python3

# I collaborated with aaronlindsey on this

discovered_points = set()
duplicate_points = set()

with open("input") as file:
    for line in file:
        endpoints = line.rstrip().split(sep='->')
        start = endpoints[0].rstrip().split(sep=',')
        end = endpoints[1].rstrip().split(sep=',')

        x1, y1 = int(start[0]), int(start[1])
        x2, y2 = int(end[0]), int(end[1])

        if x1 == x2 or y1 == y2:
            # line is horizontal or vertical
            # make sure the points are ordered least to greatest
            if x2 < x1 or y2 < y1:
                x1, x2 = x2, x1
                y1, y2 = y2, y1
            for i in range(x1, x2+1):
                for j in range(y1, y2+1):
                    point = (i, j)
                    if point in discovered_points:
                        duplicate_points.add(point)
                    discovered_points.add(point)
        elif x1 + y1 == x2 + y2:
            # line is diagonal like this /
            # make sure the points are ordered with the x values increasing
            if x2 < x1:
                x1, x2 = x2, x1
                y1, y2 = y2, y1
            line_length = x2 - x1 + 1
            for i in range(line_length):
                x = x1 + i
                y = y1 - i
                point = (x, y)
                if point in discovered_points:
                    duplicate_points.add(point)
                discovered_points.add(point)
        else:
            # line is diagonal like this \
            # make sure the points are ordered with the x values increasing
            if x2 < x1:
                x1, x2 = x2, x1
                y1, y2 = y2, y1
            line_length = x2 - x1 + 1
            for i in range(line_length):
                x = x1 + i
                y = y1 + i
                point = (x, y)
                if point in discovered_points:
                    duplicate_points.add(point)
                discovered_points.add(point)

print(len(duplicate_points))
