(ns hello-bot.driver
  (:require [gpio.core :as gpio]
            [environ.core :refer [env]]))

;; We don't want to expose the internals of gpio to the rest of the app, so 
;; we use a portmap here (:device-key gpioport) to track all of the gpio port
;; objects. The driver abstraction allows for interactions using the device
;; key (eg. :yellow-led) and blissful ignorance of the backing gpio library.
(def open-devices (atom #{}))

(defn- pin [device-key]
  "Returns the pin (number) for a given device-key (eg. :yellow-led)"
  (Integer/parseInt (env device-key)))

(defn- open-output-port! [device-key]
  "Opens a gpio pin for output"
  (-> (pin device-key)
    (gpio/open-pin)
    (gpio/set-direction :out)))

(defn- init-device! [device-key]
  "Opens the gpio pin and sets a hook for closing it on shutdown"
  (open-output-port! device-key)
  (swap! open-devices conj device-key)
  (.addShutdownHook (Runtime/getRuntime) (Thread. 
                                          #(gpio/close-pin (pin device-key)))))

(defn- gpio-value [state]
  "Translates :high/:low states to the internal gpio value"
  (= state :high))

(defn- write-to-device-key! [device-key state]
  "Writes :high/:low to a single device-key (eg. :yellow-led)"
  (println device-key "->" state)
  (when-not (contains? @open-devices device-key)
    (init-device! device-key))
  (gpio/write-value (pin device-key) (gpio-value state)))

(defn write! [statemap]
  "Write to gpio a map of device-keys (eg. :yellow-led) and states (eg. :high, :low)"
  (doseq [[device-key state] statemap]
    (write-to-device-key! device-key state)))
