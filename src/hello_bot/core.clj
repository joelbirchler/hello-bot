(ns hello-bot.core
  (:require [hello-bot.led :as led]
            [environ.core :refer [env]])
  (:gen-class))

(.addShutdownHook (Runtime/getRuntime)
  (Thread. #(println "shutdown")))

(defn -main [& args]
  (println "Hello!")
  (let [green-led (led/init (env :green-led))
        yellow-led (led/init (env :yellow-led))]
    (led/blink! green-led  (take 6 (cycle [:on :off])))
    (led/blink! yellow-led (take 7 (cycle [:off :on])))
    (loop [] (recur))))
