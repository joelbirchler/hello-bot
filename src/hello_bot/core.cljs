(ns hello-bot.core
  (:require [hello-bot.server :as server]
            [hello-bot.gpio :as gpio]
            [cljs.nodejs :as node]))

(enable-console-print!)

(.on node/process "SIGINT"
  (fn [& _]
    (println "Exiting...")
    (gpio/close)
    (.exit node/process)))

(defn cycle-leds []
  (dotimes [n 8]
    (let [yellow-state (if (even? n) :high :low)
          green-state (if (= :high yellow-state) :low :high)
          delay (* n 150)] 
      (js/setTimeout 
        (fn []
          (gpio/set-pin :yellow-led yellow-state)
          (gpio/set-pin :green-led green-state))
        delay))))

(defn -main [& args]
  (println "Hi!")
  (cycle-leds)
  (server/init))

(set! *main-cli-fn* -main)
