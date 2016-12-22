(ns hello-bot.core
  (:require [hello-bot.led :as led]
            [hello-bot.motor :as motor]
            [hello-bot.device :as device]
            [environ.core :refer [env]])
  (:gen-class))


(def led-keys     [:green-led :yellow-led])
(def motor-keys   [:left-forward-motor :left-reverse-motor :right-forward-motor :right-reverse-motor])
(def device-keys  (concat led-keys motor-keys))
(def device-ports (map env device-keys))

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
