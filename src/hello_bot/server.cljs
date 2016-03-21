(ns hello-bot.server
  (:require [cljs.nodejs :as node]))

(def express (node/require "express"))
(def app (express))

;(.get app "/gpio/:pin/set/:value"
;  (fn [req res]
;    (gpio/set-pin
;      (int (.. req -params -pin))
;      (keyword (.. req -params -value)))
;    (.send res "ok")))

(.get app "/"
  (fn [req res]
    (.send res "Howdy.")))

(defn init []
  (.listen app 3000
    (fn [] (println "Listening on port 3000!"))))
