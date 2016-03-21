(ns hello-bot.server
  (:require [cljs.nodejs :as node]))

(defonce express (node/require "express"))
(defonce app (express))

(defn GET [path action-fn]
  (.get app path
    (fn [req res]
      (.send res (action-fn (.. req -params))))))


;(.get app "/gpio/:pin/set/:value"
;  (fn [req res]
;    (gpio/set-pin
;      (int (.. req -params -pin))
;      (keyword (.. req -params -value)))
;    (.send res "ok")))


(GET "/taco/:wat"
  #(str "Taco " (.-wat %)))

(GET "/"
  #(str "Hello!"))

(defn init []
  (.listen app 3000
    (fn [] (println "Listening on port 3000!"))))
