(ns hello-bot.driver
  (:require [gpio.core :as gpio]
            [environ.core :refer [env]]))

(defn- pin [device-key]
  "Returns the env defined pin value for a given device key (eg. :yellow-led)"
  (env device-key))

(defn open! [device-key]
  "Opens (and returns) a port for a given device key"
  (gpio/open-port
    (pin device-key)
    :direction :out
    :digital-result-format :keyword))

(defn turn-on! [device-key]
  (gpio/write-value! (pin device-key) :high))

(defn turn-off! [device-key]
  (gpio/write-value! (pin device-key) :low))

(defn close! [device-key]
  (turn-off! device-key)
  (gpio/close! (pin device-key)))

(defn- do-kv [func statemap]
  (doseq [[k v] statemap] (func k v)))

(defn- do-keys [func statemap]
  (doseq [[k _v] statemap] (func k)))

(defn- statemap->pin-states [statemap]
  (map (fn [[k v]] [(pin k) v]) statemap))

(defn- statemap->pinmap [statemap]
  (into {} (statemap->pin-states statemap)))

(defn watwat [pin val]
  (println pin "-" val)) ; gpio/write-value!

(defn set-state! [statemap]
  (do-kv watwat (statemap->pinmap statemap) ))

(defn open-all! [statemap]
  (do-keys #(open! %) statemap))

(defn close-all! [statemap]
  (do-keys #(close! %) statemap))
