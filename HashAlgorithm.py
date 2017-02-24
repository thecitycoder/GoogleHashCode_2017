
# with open('kittens.in', 'r') as f:

    # read_Data = f.read()


# f.close

# print(read_Data)


# f = open('kittens.in', 'r')
    # firstLine = f.readline()
    # videoSizes = f.readline()

# print(firstLine)

from random import randint
array = [randint(1,100) for i in range(10)]

for number in array:
    if number%2 == 1:
        print(number)
