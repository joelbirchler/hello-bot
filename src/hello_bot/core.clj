(ns hello-bot.core
  (:require [hello-bot.led :as led]
            [environ.core :refer [env]])
  (:gen-class))

(def leds
  {:green  (env :green-led)
   :yellow (env :yellow-led)})

(defn init-leds []
  (doseq [[_ port] leds] (led/init port)))

(defn cycle-leds []
  (led/blink! (:green leds))
  (led/blink! (:yellow leds) (take 7 (cycle [:off :on]))))

(defn shutdown []
  (println "Goodbye!")
  (doseq [[_ port] leds] (led/close! port)))

(defn -main [& args]
  (println "Hello!")
  (.addShutdownHook (Runtime/getRuntime) (Thread. shutdown))
  (init-leds)
  (cycle-leds)
  (loop [] (recur)))
