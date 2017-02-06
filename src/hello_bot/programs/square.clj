(ns hello-bot.programs.square
  (:refer-clojure :exclude [reverse])
  (:require [hello-bot.car :refer [forward reverse turn-right turn-left]]))

(defn play [>> {car :car}]
  (>>
    (forward car)
    [:sleep 2]
    (turn-right car)
    [:sleep 0.5])
  (recur >> {:car car}))
