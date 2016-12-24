(ns hello-bot.car
  (:require [hello-bot.motor :as motor]))

(defrecord Car [left-motor right-motor])

(defn keys [{:keys [left-motor right-motor]}]
  (concat
    (vals left-motor)
    (vals right-motor)))

(defn -merge-with [fun left right]
  (merge
    (fun left) (fun right)))

(defn forward [{:keys [left-motor right-motor]}]
  (merge-with motor/forward left-motor right-motor))

(defn reverse [{:keys [left-motor right-motor]}]
  (merge-with motor/reverse left-motor right-motor))

(defn stop [{:keys [left-motor right-motor]}]
  (merge-with motor/stop left-motor right-motor))

(defn turn-right [{:keys [left-motor right-motor]}]
  (merge
    (motor/forward left-motor)
    (motor/reverse right-motor)))

(defn turn-left [{:keys [left-motor right-motor]}]
  (merge
    (motor/reverse left-motor)
    (motor/forward right-motor)))
