This is an experiment for driving a robot and an excuse to play with Clojure.

# ENV and Pins for Raspberry PI 3

[Gpio (BCM) pins](https://pinout.xyz/pinout/pin1_3v) are read from ENVs. These will be set in the Dockerfile 
or when you start the container. You can also choose to use the `envs` file by running `eval $(cat envs)`.
