(ns hello-bot.driver
  (:require [gpio.core :as gpio]
            [environ.core :refer [env]]))

;; We don't want to expose the internals of gpio to the rest of the app, so 
;; we use a portmap here (:device-key gpioport) to track all of the gpio port
;; objects. The driver abstraction allows for interactions using the device
;; key (eg. :yellow-led) and blissful ignorance of the backing gpio library.
(def open-devices (atom #{}))

(defn pin [device-key]
  "Returns the pin (number) for a given device-key (eg. :yellow-led)"
  (Integer/parseInt (env device-key)))

(defn registered? [device-key]
  (contains? @open-devices device-key))

(defn register! [device-key]
  "Opens device and sets a hook for closing it on shutdown"
  (gpio/open-pin (pin device-key))
  (swap! open-devices conj device-key)
  (.addShutdownHook 
   (Runtime/getRuntime) (Thread. #(gpio/close-pin (pin device-key)))))

(defn set-direction [device-key direction]
  "Sets direction as :in or :out"
  (gpio/set-direction (pin device-key) direction)
  (when (= :in direction)
    (gpio/active-low (pin device-key) true)))

(defn gpio-value [state]
  "Translates :high/:low states to the internal gpio value"
  (= state :high))

(defn write-value [device-key state]
  "Writes :high/:low to a device key"
  (gpio/write-value (pin device-key) (gpio-value state)))

(defn wait-read-value [device-key]
  "Waits for input and then returns the value"
  (-> (pin device-key)
    (gpio/wait-for-input)
    (gpio/read-value)))
