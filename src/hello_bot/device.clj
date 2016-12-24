(ns hello-bot.device
  (:require [hello-bot.gpio-mock :as gpio]))

(defn open! [port]
  (-> port
    (gpio/open-port)
    (gpio/set-direction! :out)))

(defn turn-on! [port]
  (gpio/write-value! port :high))

(defn turn-off! [port]
  (gpio/write-value! port :low))

(defn set-state! [portmap statemap]
  (doseq [[key value] statemap]
    (gpio/write-value! (key portmap) value)))

(defn close! [port]
  (turn-off! port)
  (gpio/close! port))
