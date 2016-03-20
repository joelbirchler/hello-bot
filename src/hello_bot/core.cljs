(ns hello-bot.core
  (:require [cljs.nodejs :as node]))

(node/enable-util-print!)
(def express (node/require "express"))
(def app (express))

(def onoff (.-Gpio (node/require "onoff")))
(defn gpio [bcm-pin direction]
  (println (str bcm-pin " set to " (name direction)))  
  (onoff. bcm-pin (name direction)))

; TODO: clean should (.unexport led)
; TODO: move gpio to other module
; TODO: trap sigint

(def bcm-pins [4 22 23 24 25])
(def pins 
  (zipmap
    bcm-pins
    (map #(gpio % :out) bcm-pins)))

(defn set-pin [bcm-pin high-or-low]
  (let [pin (get pins bcm-pin)
        value (if (= :high high-or-low) 1 0)]
    (.writeSync pin value)))

;;(.on node/process 'SIGINT' 
;;  (fn [& _]
;;    (println "Exiting...")
;;    (close)
;;    (.exit node/process)))

(.get app "/gpio/:pin/set/:value"
  (fn [req res]
    (set-pin 
      (int (.. req -params -pin)) 
      (keyword (.. req -params -value)))
    (.send res "ok")))

(.get app "/" 
  (fn [req res]
    (.send res "Howdy.")))

(defn -main [& args]
  (.listen app 3000
    (fn [] (println "Listening on port 3000!"))))

(set! *main-cli-fn* -main)
