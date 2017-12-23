(ns hello-bot.output-device
  (:require [hello-bot.driver :as driver]))

(defn- init-once! [device-key]
  "Inits device for output"
  (when-not (driver/registered? device-key)
    (driver/register! device-key)
    (driver/set-direction device-key :out)))  

(defn- write-to-device-key! [device-key state]
  "Writes :high/:low to a single device-key (eg. :yellow-led)"
  (println device-key "->" state)
  (init-once! device-key)
  (driver/write-value device-key state))

(defn write! [statemap]
  "Write to gpio a map of device-keys (eg. :yellow-led) and states (eg. :high, :low)"
  (doseq [[device-key state] statemap]
    (write-to-device-key! device-key state)))

