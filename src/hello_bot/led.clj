(ns hello-bot.led
  (:require [hello-bot.gpio-mock :as gpio]
            [clojure.core.async :as async :refer [<! go go-loop timeout]]))

(defn init [port]
  (-> port
    (gpio/open-port)
    (gpio/set-direction! :out)))

(defn turn-on! [port]
  (gpio/write-value! port :high))

(defn turn-off! [port]
  (gpio/write-value! port :low))

(defn- each-delayed [fun delay pattern]
  (go-loop [pattern pattern]
    (when (seq pattern)
      (recur (do
        (fun (first pattern))
        (<! (timeout delay))
        (rest pattern))))))

(defn blink!
  ([port]
    (blink! port (take 6 (cycle [:on :off]))))
  ([port pattern]
    (each-delayed #(gpio/write-value! port %) 800 pattern)))

(defn close! [port]
  (turn-off! port)
  (gpio/close! port))
