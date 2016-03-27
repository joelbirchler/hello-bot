This is an experiment for driving a robot with an HTTP api and excuse to play with ClojureScript.

# TODO

- [x] Simple writing interface
- [x] Cleanup gpio on close
- [ ] Motors
- [ ] Bumper


# Pins

Note: Raspberry PI GPIO pins are confusingly numbered. It appears that the onoff library uses the internal BCM 
GPIO number.

PIN | GPIO | BCM GPIO
--- | ---- | --------
1   |  8   |   0/2
3   |  9   |   1/3
7   |  7   |   4
8   |  15  |   14
10  |  16  |   15
13  |  2   |   21/27
15  |  3   |   22
16  |  4   |   23
18  |  5   |   24
19  |  12  |   10
21  |  13  |   9
22  |  6   |   25
23  |  14  |   11
26  |  11  |   7


