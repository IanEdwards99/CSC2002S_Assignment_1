import sys
def main():
    myFile = sys.argv[2]
    f = open(myFile, "r+")
    i = 0
    count = 0
    min = 1000
    max = 0
    sum = 0

    while True:
        line = f.readline()
        if not line:
            break
        i = int(line)
        sum += i
        if i > max:
            max = i
        if i < min:
            min = i
        count += 1
    f.close()
    avg = sum/count

    f = open(myFile, "a+")
    f.write("Average: \n" + str(avg) + "\nMax: " + str(max) + "\nMin: " + str(min))
    f.close()

if __name__ == "__main__":
    main()

