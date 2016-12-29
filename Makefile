HARDWARE := $(shell uname -m)

all: build verify run

build:
ifeq ($(HARDWARE),$(filter $(HARDWARE),armv6l armv7l aarch64))
	docker build -t rpi-lein:latest -f Dockerfile-rpi-lein .
else
	@echo "ARM not detected. Skipping rpi-lein build."
endif
	./Dockerfile-gen.sh > Dockerfile-generated
	docker build -t hello-bot -f Dockerfile-generated .

run:
	docker run --rm hello-bot

repl:
	docker run --rm -it hello-bot lein repl

verify:
	docker run --rm hello-bot lein test
