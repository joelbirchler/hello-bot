(ns hello-bot.driver
  (:require [gpio.core :as gpio]
            [environ.core :refer [env]]))

;; We don't want to expose the internals of gpio to the rest of the app, so 
;; we use a portmap here (:device-key gpioport) to track all of the gpio port
;; objects. The driver abstraction allows for interactions using the device
;; key (eg. :yellow-led) and blissful ignorance of the backing gpio library.
(def portmap (atom {}))

(defn- open-output-port! [device-key]
  (let [pin (env device-key)]
    (gpio/unexport! pin)
    (gpio/open-port pin :direction :out :digital-result-format :keyword)))

(defn- close-port! [device-key]
  (write-to-device-key! device-key :low)
  (gpio/close! (device-key @portmap))
  device-key)

(defn- init-port! [device-key]
  (let [port (open-output-port! device-key)]
    (swap! portmap assoc device-key port)
    (.addShutdownHook (Runtime/getRuntime) (Thread. #(close-port! device-key)))
    port))

(defn- get-port [device-key]
  (if (contains? @portmap device-key)
    (device-key @portmap)
    (init-port! device-key)))

(defn- write-to-device-key! [device-key state]
  (println device-key "->" state)
  (gpio/write-value! (get-port device-key) state))

(defn write! [statemap]
  "Write to gpio a map of device-keys (eg. :yellow-led) and states (eg. :high, :low)"
  (doseq [[device-key state] statemap]
    (write-to-device-key! device-key state)))
