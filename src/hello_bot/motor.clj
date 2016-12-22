(ns hello-bot.motor
  (:require [hello-bot.gpio-mock :as gpio]))

;; TODO: we need some sync motor functions, and then a method for queuing commands
;; TODO: we also need to be able to stop doing what we're doing (eg. on bump, turn)
;;       and clear the queue
;; TODO: We then need a "car" or something for turning that controls both motors
;; NOTE: This is going to be different than the LED... we want to return channels
;;       really... so yeah...

(defn forward! [forward-port reverse-port]
  (gpio/write-value! forward-port :high)
  (gpio/write-value! reverse-port :low))

(defn reverse! [forward-port reverse-port]
  (gpio/write-value! forward-port :low)
  (gpio/write-value! reverse-port :high))

(defn stop! [& ports]
  (doseq [port ports]
    (gpio/write-value! port :low)))
