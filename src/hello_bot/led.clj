(ns hello-bot.led)
;; TODO: consider renaming this module

(defrecord Leds [green yellow])

;; TODO: refactor car/all-keys and led/all-keys into single function
(defn all-keys [leds]
  (vals leds))





