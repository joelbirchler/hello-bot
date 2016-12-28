(ns hello-bot.programs.square
  (:require [hello-bot.car :refer [forward reverse turn-right turn-left]]
            [clojure.core.async :as async :refer [go-loop]]))

(defn test [>> {car :car}]
  (>> (forward car) 1000)
  (>> (turn-right car) 200))

(defn play [>> {car :car}]
  (go-loop []
    (>> (forward car) 1000)
    (>> (turn-right car) 200)
    (recur)))
