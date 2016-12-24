(ns hello-bot.core
  (:require [hello-bot.led :as led]
            [hello-bot.car :as car]
            [hello-bot.motor :as motor]
            [hello-bot.device :as device]
            [environ.core :refer [env]])
  (:gen-class))

(def bot-car
  (car/->Car
    (motor/->Motor :left-forward-motor :left-reverse-motor)
    (motor/->Motor :right-forward-motor :right-reverse-motor)))

(def leds         [:green-led :yellow-led])
(def devices      (concat leds (car/keys bot-car)))
(def device-ports (map env devices))

(defn init []
  (println "Hello!")
  (doseq [port device-ports]
    (device/open! port)))

(defn shutdown []
  (println "Goodbye!")
  (doseq [port device-ports]
    (device/close! port)))

(defn -main [& args]
  (.addShutdownHook (Runtime/getRuntime) (Thread. shutdown))
  (init)
  (loop [] (recur)))
