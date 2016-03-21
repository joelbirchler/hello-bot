(ns hello-bot.core
  (:require [hello-bot.server :as server]
            [cljs.nodejs :as node]))

(enable-console-print!)

(.on node/process "SIGINT"
  (fn [& _]
    (println "Exiting...")
    (.exit node/process)))

(defn -main [& args]
  (println "Hello world!")
  (server/init))

(set! *main-cli-fn* -main)


; TODO: on sigint, gpio/close should (.unexport led)
