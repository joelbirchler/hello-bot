(ns hello-bot.programs.square
  (:require [hello-bot.car :refer [forward reverse turn-right turn-left]]
            [clojure.core.async :as async :refer [go-loop]]))

(defn play [>> {car :car}]
  (>>
    (forward car)
    [:sleep 2]
    (turn-right car)
    [:sleep 0.5])
  (recur >> {:car car}))
