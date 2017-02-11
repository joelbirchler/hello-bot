This is an experiment for driving a robot and an excuse to play with Clojure.


# TODO

- [x] Mock GPIO interface prints
- [x] LEDs cycle
- [x] Trap exit and close GPIO
- [x] Sketch out programs
- [x] Refactor to functions that return desired state, and state change handler
- [x] Square sketch works
- [x] LED cycle sketch works
- [ ] Remove dynamic program loading and revisit abstraction levels a bit
- [ ] Tests
- [ ] Bumpy sketch works
- [x] PI 3: Wifi, GPIO wiring
- [x] https://github.com/peterschwarz/clj-gpio


# ENV and Pins for Raspberry PI 3

[Gpio (BCM) pins](https://pinout.xyz/pinout/pin1_3v) are read from ENVs. These will be set in the Dockerfile 
or when you start the container. You can also choose to use the `envs` file by running `eval $(cat envs)`.

We currently have ground on pin 39 going to a line on the breadboard. Pins 35 (BCM 19) and 37 (BCM 26) each 
go to a resistor (1k-- just randomly grabbed out of a box) and then to the positive side of the led. The 
negative side goes into the ground line on the breadboard.

