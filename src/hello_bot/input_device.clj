(ns hello-bot.input-device
  (:require [hello-bot.driver :as driver]
            [clojure.core.async :as async :refer [>! <! >!! <!! go go-loop chan]]))

(defn- init-once! [device-key]
  "Inits device for input"
  (when-not (driver/registered? device-key)
    (driver/register! device-key)
    (driver/set-direction device-key :in)))

(defn read-channel [device-key]
  "Returns a channel receives messages from an input port"
  (init-once! device-key)
  (let [input-chan (chan)]
    (go-loop []
      (>! input-chan (driver/wait-read-value device-key))
      (recur))
    input-chan))
