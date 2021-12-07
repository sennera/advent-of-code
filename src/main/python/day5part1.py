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

        # make sure the points are ordered least to greatest
        if x2 < x1 or y2 < y1:
            x1, x2 = x2, x1
            y1, y2 = y2, y1

        if x1 == x2 or y1 == y2:
            # line is horizontal or vertical
            for i in range(x1, x2+1):
                for j in range(y1, y2+1):
                    point = (i, j)
                    if point in discovered_points:
                        duplicate_points.add(point)
                    discovered_points.add(point)

print(len(duplicate_points))
