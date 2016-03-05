(ns hello-bot.core
  (:require [cljs.nodejs :as nodejs]))

(nodejs/enable-util-print!)
(defonce express (nodejs/require "express"))
(def app (express))

(.get app "/" 
  (fn [req res]
    (.send res "Howdy.")))

(defn -main [& args]
  (.listen app 3000
    (fn [] (println "Listening on port 3000!"))))

(set! *main-cli-fn* -main)
