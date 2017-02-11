(ns hello-bot.core
  (:require [hello-bot.device.display :as display]
            [hello-bot.device.car :as car]
            [hello-bot.device.motor :as motor]
            [hello-bot.driver :as driver]
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

(defn init [pin-map]
  (println "Hello!")
  (map-kv #(driver/open! %) pin-map))

(defn shutdown [pin-map]
  (println "Goodbye!")
  (map-kv #(driver/close! %) pin-map))

(defn play-program [port-map program-name]
  (let [ns-name (str "hello-bot.programs." program-name)
        ns-symbol (symbol ns-name)]
    (require ns-symbol)
    (let [play-fn (ns-resolve (find-ns ns-symbol) 'play)]
      (play-fn
         (driver/player port-map)
         {:car bot-car :leds leds}))))

(defn -main [& args]
  (let [device-keys (concat (deep-vals leds) (deep-vals bot-car))
        pin-map     (select-keys env device-keys)
        port-map    (init pin-map)]
    (.addShutdownHook (Runtime/getRuntime) (Thread. #(shutdown pin-map)))
    (play-program port-map "cycle-leds")
    (loop [] (recur))))
