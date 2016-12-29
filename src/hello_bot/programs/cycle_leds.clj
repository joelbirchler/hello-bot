(ns hello-bot.programs.cycle-leds)

(defn play [>> bot]
  (>> [:sleep 0.5] [:sleep 0.4] [:sleep 0.3]))

; TODO: blink should return a sequence with :sleeps

;(defn play [{:keys [green-led yellow-led]} _motor _bumper]
;  (blink! green-led)
;  (blink! yellow-led (take 7 (cycle [:off :on]))))
