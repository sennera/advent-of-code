#!/usr/bin/env python3

with open("input") as file:
  lines = file.readlines()
  key = lines[0].rstrip()
  input_image = []
  for line in lines[2:]:
    input_image.append(line.rstrip())


def enhance_pixel(i, j, m, foo):
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


def enhance_image(step, m):
  result = []
  for i in range(-1, len(m) + 1):
    result_row = []
    for j in range(-1, len(m) + 1):
      pixel = enhance_pixel(i, j, m, '.' if step % 2 == 0 else '#')
      result_row.append(pixel)
    result.append(result_row)
  return result


result_image = input_image
for step in range(50):
  result_image = enhance_image(step, result_image)

count = 0
for i in range(len(result_image)):
  for j in range(len(result_image)):
    if result_image[i][j] == "#":
      count += 1

print(count)
# print(result_image)