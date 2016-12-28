(ns hello-bot.device
  (:require [hello-bot.gpio-mock :as gpio]
            [clojure.core.async :as async :refer [<! go go-loop timeout]]))

(defn open! [port]
  (-> port
    (gpio/open-port)
    (gpio/set-direction! :out)))

(defn turn-on! [port]
  (gpio/write-value! port :high))

(defn turn-off! [port]
  (gpio/write-value! port :low))

(defn close! [port]
  (turn-off! port)
  (gpio/close! port))

(defn set-state! [portmap statemap]
  (doseq [[key value] statemap]
    (gpio/write-value! (key portmap) value)))

(defn >> [portmap gpio-plan wait-milliseconds]
  (set-state! portmap gpio-plan)
  (println portmap)
  (println "playa gonna play play play")) ; TODO: add pause
