(ns hello-bot.core
  (:require [hello-bot.device.display :as display]
            [hello-bot.device.car :as car]
            [hello-bot.device.motor :as motor]
            [hello-bot.driver :as driver]
            [hello-bot.program.cycle-leds :as cycle-leds]
            [environ.core :refer [env]])
  (:gen-class))

(def bot-car
  (car/->Car
    (motor/->Motor :left-forward-motor :left-reverse-motor)
    (motor/->Motor :right-forward-motor :right-reverse-motor)))

(def leds 
  (display/->Leds :green-led :yellow-led))

(defn deep-vals [m]
  (mapcat
    #(if (map? %) (deep-vals %) [%])
    (vals m)))

(defn map-kv [func some-hash-map]
  "Maps over values in a hash-map"
  (reduce-kv (fn [m k v] (assoc m k (func v))) {} some-hash-map))

(defn open-ports [pin-map]
  (map-kv #(driver/open! %) pin-map))

(defn shutdown [pin-map]
  (println "Goodbye!")
  (map-kv #(driver/close! %) pin-map))

(defn init! []
  (println "Hello!")
  (let [device-keys (concat (deep-vals leds) (deep-vals bot-car))
        pin-map     (select-keys env device-keys)
        port-map    (open-ports pin-map)]
      (.addShutdownHook (Runtime/getRuntime) (Thread. #(shutdown pin-map)))
      port-map))

(defn -main [& args]
  (let [port-map (init!)]  
    (cycle-leds/play
      (driver/player port-map)
      {:car bot-car :leds leds}))
  (loop [] (recur)))
