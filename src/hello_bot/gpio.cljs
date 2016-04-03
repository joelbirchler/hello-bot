(ns hello-bot.gpio
  (:require [cljs.nodejs :as node]))

(enable-console-print!)

(def onoff (.-Gpio (node/require "onoff")))

(defn gpio [bcm-pin direction]
  (println (str bcm-pin " set to " direction))
  (onoff. bcm-pin (name direction)))

(def pins
  {:yellow-led          (gpio 22 :out)
   :green-led           (gpio 23 :out)
   :left-forward-motor  (gpio 25 :out)
   :left-reverse-motor  (gpio 4  :out)
   :right-forward-motor (gpio 17 :out)
   :right-reverse-motor (gpio 18 :out)})

(defn close []
  (doseq [[_ pin] pins]
    (.writeSync pin 0)
    (.unexport pin)))

(defn set-pin [pin-name high-or-low]
  (let [pin (pin-name pins)
        value (if (= :high high-or-low) 1 0)]
    (println (str "Writing " value " to " pin " @ " pin-name))
    (.writeSync pin value)))
