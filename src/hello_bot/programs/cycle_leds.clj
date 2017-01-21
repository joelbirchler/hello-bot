(ns hello-bot.programs.cycle-leds)

(defn blink
  ([count]
   (blink count [:high :low]))
  ([count pattern]
   (take count (cycle pattern))))

(defn sequence->device-play [sequence delay led-key]
  (->> sequence
    (map #(hash-map led-key %))
    (interpose [:sleep delay])))

(defn play [>> {{yellow-led :yellow green-led :green} :leds}]
  (apply >> (sequence->device-play (blink 6) 0.5 yellow-led)))


;; TODO: green and led blinking
;;
;(defn play [{:keys [green-led yellow-led]} _motor _bumper]
;  (blink! green-led)
;  (blink! yellow-led (take 7 (cycle [:off :on]))))
