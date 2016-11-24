(ns hello-bot.led
  (:require [hello-bot.gpio-mock :as gpio]))

(defn init [port]
  (-> port
    (gpio/open-port)
    (gpio/set-direction! :out))
  port)

(defn turn-on! [port]
  (gpio/write-value! port :high))

(defn turn-off! [port]
  (gpio/write-value! port :low))

(defn blink! [port seconds]
  (println "blink!"))

(defn close [port]
  (-> port
    (turn-off!)
    (gpio/close!)))
