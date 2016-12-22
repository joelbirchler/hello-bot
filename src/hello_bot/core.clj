(ns hello-bot.core
  (:require [hello-bot.led :as led]
            [hello-bot.motor :as motor]
            [environ.core :refer [env]])
  (:gen-class))


(defn key->portmap [key]
  [key (key env)])

;; FIXME: Blergh... some polymorphism would make this less messy. Not sure the best
;; clojure way to handle it though. Don't want to accidentally fall into OO.

(def led-keys    [:green-led :yellow-led])
(def motor-keys  [:left-forward-motor :left-reverse-motor :right-forward-motor :right-reverse-motor])
(def led-ports   (map key->portmap led-keys))
(def motor-ports (map key->portmap motor-keys))

(defn call-with-ports [fun ports]
  (doseq [[_ port] ports] (fun port)))

(defn init []
  (println "Hello!")
  (call-with-ports led/init led-ports)
  (call-with-ports motor/init motor-ports))

(defn shutdown []
  (println "Goodbye!")
  (call-with-ports led/close! led-ports)
  (call-with-ports motor/close! motor-ports))

(defn -main [& args]
  (.addShutdownHook (Runtime/getRuntime) (Thread. shutdown))
  (init)
  (loop [] (recur)))
