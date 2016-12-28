(ns hello-bot.led
  (:require [hello-bot.gpio-mock :as gpio]
            [clojure.core.async :as async :refer [<! go go-loop timeout]]))

(defn- each-delayed [fun delay pattern]
  (go-loop [pattern pattern]
    (when (seq pattern)
      (recur (do
        (fun (first pattern))
        (<! (timeout delay))
        (rest pattern))))))

;; TODO: consider moving to hello-bot.device
(defn blink!
  ([port]
    (blink! port (take 6 (cycle [:on :off]))))
  ([port pattern]
    (each-delayed #(gpio/write-value! port %) 800 pattern)))
