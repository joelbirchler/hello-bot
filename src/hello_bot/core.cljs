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

(defn -main [& args]
  (println "Hello world!")
  (server/init))

(set! *main-cli-fn* -main)
