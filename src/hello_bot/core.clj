(ns hello-bot.core
  (:require [hello-bot.led :as led]
            [environ.core :refer [env]])
  (:gen-class))

(.addShutdownHook (Runtime/getRuntime)
  (Thread. #(println "shutdown")))

(defn -main [& args]
  (println "Hello!")
  (let [green-led (led/init (env :green-led))]
    (led/turn-on! green-led)))
