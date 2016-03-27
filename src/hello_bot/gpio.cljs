(ns hello-bot.gpio
  (:require [cljs.nodejs :as node]))

(enable-console-print!)

(def onoff (.-Gpio (node/require "onoff")))

(defn gpio [bcm-pin direction]
  (println (str bcm-pin " set to " (name direction)))
  (onoff. bcm-pin (name direction)))

(def bcm-pins [4 22 23 24 25])

(def pins
  (zipmap
    bcm-pins
    (map #(gpio % :out) bcm-pins)))

(defn set-pin [bcm-pin high-or-low]
  (let [pin (get pins bcm-pin)
        value (if (= :high high-or-low) 1 0)]
    (.writeSync pin value)))
