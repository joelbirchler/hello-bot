(ns hello-bot.car
  (:require [hello-bot.motor :as motor]))

(defrecord Car [left-motor right-motor])

(defn keys [{:keys [left-motor right-motor]}]
  (concat
    (vals left-motor)
    (vals right-motor)))

(defn- with-both-motors [fun {:keys [left-motor right-motor]}]
  (merge
    (fun left-motor)
    (fun right-motor)))

(defn forward [my-car]
  (with-both-motors motor/forward my-car))

(defn reverse [my-car]
  (with-both-motors motor/reverse my-car))

(defn stop [my-car]
  (with-both-motors motor/stop my-car))

(defn turn-right [{:keys [left-motor right-motor]}]
  (merge
    (motor/forward left-motor)
    (motor/reverse right-motor)))

(defn turn-left [{:keys [left-motor right-motor]}]
  (merge
    (motor/reverse left-motor)
    (motor/forward right-motor)))
