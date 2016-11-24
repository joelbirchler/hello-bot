all: build verify run

build:
	docker build -t hello-bot .

run:
	docker run --rm hello-bot

repl:
	docker run --rm -it hello-bot lein repl

verify:
	docker run --rm hello-bot lein test
