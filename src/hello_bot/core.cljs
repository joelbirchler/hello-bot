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


; TODO: onoff doesn't work with the latest version of nodejs
; TODO: on sigint, gpio/close should (.unexport led)
; TODO: (defn route) where response will (.send res _)
