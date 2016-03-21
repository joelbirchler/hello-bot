all: build run

build:
	java -cp cljs.jar:src clojure.main node_build.clj

run:
	node main.js

repl:
	rlwrap java -cp cljs.jar:src clojure.main node_repl.clj
