(ns hello-bot.device
  (:require [hello-bot.gpio-mock :as gpio]
            [clojure.core.match :refer [match]]
            [clojure.core.async :as async :refer [<! >!! go go-loop timeout chan]]))

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

(defn- player-sender [ch]
  (fn [& messages]
    (doseq [message messages]
      (>!! ch message))))

(defn player [portmap]
  (let [ch (chan)]
    (go-loop [message (<! ch)]
      (println "message:" message)
      (match message
        [:sleep seconds] (<! (timeout (* 1000 seconds)))
        :else (set-state! portmap message))
    (recur (<! ch)))
    (player-sender ch)))
