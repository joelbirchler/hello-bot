This is an experiment for driving a robot and an excuse to play with Clojure.


# TODO

- [x] Mock GPIO interface prints
- [x] LEDs cycle
- [x] Trap exit and close GPIO
- [x] Sketch out programs
- [ ] Refactor to functions that return desired state, and state change handler
- [ ] Motors/car
- [ ] LED cycle sketch works
- [ ] Square sketch works
- [ ] Bumpy sketch works
- [ ] PI 3: Wifi, GPIO wiring
- [ ] https://github.com/peterschwarz/clj-gpio
- [ ] Break into client/server, where server handles the queueing and robot
      commands and the client sends the program

# Raspberry PI Setup

I'm using a Raspberry PI 3 with the [Hypriot](http://blog.hypriot.com/) image. Hypriot
is a Debian-based linux with Docker.

1. Change the password with `passwd`.

2. Setting up the wifi is harder than it should be. You can find a mess of tutorials
and Stack Overflow answers like [this](https://www.raspberrypi.org/documentation/configuration/wireless/wireless-cli.md) or [this](http://raspberrypi.stackexchange.com/questions/37920/how-do-i-set-up-networking-wifi-static-ip)
or [this](http://raspberrypi.stackexchange.com/questions/5308/cant-get-an-ip-for-wlan0).

I ended up modifying two files and then rebooting.

Run `sudo nano /etc/network/interfaces` and make it look like this:

```
source-directory /etc/network/interfaces.d

auto lo
iface lo inet loopback

iface eth0 inet manual

auto wlan0
iface wlan0 inet static
    address 192.168.86.209
    netmask 255.255.255.0
wpa-conf /etc/wpa_supplicant/wpa_supplicant.conf
```

Then run `sudo nano /etc/wpa_supplicant/wpa_supplicant.conf` and make it look like this:

```
ctrl_interface=DIR=/var/run/wpa_supplicant GROUP=netdev
update_config=1

network={
ssid="[YOUR SSID HERE]"
psk="[YOUR PASSWORD HERE]"
}
```

FIXME: This configuration isn't letting me egress via wifi.

3. Install make: `sudo apt-get update && sudo apt-get install make`

# Pins

Note: This needs to be updated for the PI 3 (see: https://pinout.xyz/pinout/pin1_3v3_power)

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
