(ns hello-bot.core
  (:require [hello-bot.server :as server]))

(enable-console-print!)

(defn -main [& args]
  (println "Hello world!")
  (server/init))

(set! *main-cli-fn* -main)


; TODO: onoff doesn't work with the latest version of nodejs
; TODO: clean should (.unexport led)
; TODO: (defn route) where response will (.send res _)
; TODO: trap sigint


;;(.on node/process 'SIGINT'
;;  (fn [& _]
;;    (println "Exiting...")
;;    (close)
;;    (.exit node/process)))
