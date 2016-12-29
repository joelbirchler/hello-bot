(ns hello-bot.programs.square
  (:require [hello-bot.car :refer [forward reverse turn-right turn-left]]
            [clojure.core.async :as async :refer [go-loop]]))

;;; ideally...
;(>> (forward car) [:sleep 200] (turn-right car) [:sleep 1000])
;where >> is a function that takes [& args]

(defn play [>> {car :car}]
  (>> (forward car))
  (>> [:sleep 2000])
  (>> (turn-right car))
  (>> [:sleep 500])
  (recur >> {:car car}))
