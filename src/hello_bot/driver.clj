(ns hello-bot.driver
  (:require [gpio.core :as gpio]
            [environ.core :refer [env]]))

;; We don't want to expose the internals of gpio to the rest of the app, so 
;; we use a portmap here (:device-key gpioport) to track all of the gpio port
;; objects. The driver abstraction allows for interactions using the device
;; key (eg. :yellow-led) and blissful ignorance of the backing gpio library.
(def portmap (atom {}))

;; TODO: We could make this deluxe by lazy-handling the opening
;; If you try to write to a device-key that's not in the portmap, open
;; it and add the shutdown hook


(defn open! [device-key]
  "Opens a port for a given device key"
  (swap! portmap assoc device-key (open-output-port! device-key))
  device-key)

(defn close! [device-key]
  (turn-off! device-key)
  (gpio/close! (device-key @portmap))
  device-key)

(defn open-all! [statemap]
  (do-keys #(open! %) statemap))

(defn close-all! [statemap]
  (do-keys #(close! %) statemap))

(defn write-state! [statemap]
  (do-kv write! statemap))

(defn- open-output-port! [device-key]
  (let [pin (env device-key)]
    (gpio/unexport! pin)
    (gpio/open-port pin :direction :out :digital-result-format :keyword)))

(defn- write! [device-key state]
  (println device-key "->" state)
  (gpio/write-value! (device-key @portmap) state)
  device-key)

(defn- turn-on! [device-key]
  (write! device-key :high))

(defn- turn-off! [device-key]
  (write! device-key :low))

(defn- do-kv [func statemap]
  (doseq [[k v] statemap] (func k v)))

(defn- do-keys [func statemap]
  (doseq [[k _v] statemap] (func k)))


