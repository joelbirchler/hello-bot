(ns hello-bot.programs.cycle-leds)

(defn blink
  ([key count]
   (blink key count [:high :low]))
  ([key count pattern]
   (map 
     #(hash-map key %) 
     (take count (cycle pattern)))))

(defn plan [yellow-led green-led]
  (concat
    (mapcat vector 
      (blink yellow-led 6)
      (blink green-led  6 [:low :high])
      (repeat [:sleep 0.5]))
    [{yellow-led :low}
     {green-led  :low}]))

(defn play [>> {{yellow-led :yellow green-led :green} :leds}]
  (apply >> (plan yellow-led green-led)))
  

