#!/usr/bin/env python3

with open("input") as file:
  lines = file.readlines()
  key = lines[0].rstrip()
  input_image = []
  for line in lines[2:]:
    input_image.append(line.rstrip())


def enhance(i, j, m, foo):
  Y_MAX = len(m) - 1
  X_MAX = len(m[0]) - 1
  value = []
  for k in range(i - 1, i + 2):
    for l in range(j - 1, j + 2):
      if k < 0 or k > X_MAX or l < 0 or l > Y_MAX:
        value.append(foo)
      else:
        value.append(m[k][l])

  binarray = [0 if x == '.' else 1 for x in value]
  index = int("".join(str(x) for x in binarray), 2)
  return key[index]


result_image = []
for i in range(-1, len(input_image) + 1):
  result_row = []
  for j in range(-1, len(input_image) + 1):
    pixel = enhance(i, j, input_image, '.')
    result_row.append(pixel)
  result_image.append(result_row)

count = 0
for i in range(-1, len(result_image) + 1):
  for j in range(-1, len(result_image) + 1):
    pixel = enhance(i, j, result_image, '#')
    if pixel == "#":
      count += 1

print(count)
# print(result_image)