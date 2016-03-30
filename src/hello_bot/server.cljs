(ns hello-bot.server
  (:require [cljs.nodejs :as node]
            [hello-bot.gpio :as gpio]))

(defonce express (node/require "express"))
(defonce app (express))

(defn GET [path action-fn]
  (.get app path
    (fn [req res]
      (.send res (action-fn (.. req -params))))))

(defn a-tag [text url]
  (str "<a href='" url "'>" text "</a>"))

(defn high-low-html [pin]
  (str
    pin ": "
    (a-tag "high" (str "/gpio/" pin "/set/high"))
    " / "
    (a-tag "low" (str "/gpio/" pin "/set/low"))
    "<br/>"))

(GET "/gpio/:pin/set/:value"
  (fn [params]
    (gpio/set-pin
      (keyword (.-pin params))
      (keyword (.-value params)))
    (a-tag "ok" "/")))

(GET "/"
  #(apply str 
    (map high-low-html ["yellow-led" "green-led" "left-forward-motor" "left-reverse-motor"])))

(defn init []
  (.listen app 3000
    (fn [] (println "Listening on port 3000!"))))
