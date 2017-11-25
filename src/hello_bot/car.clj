(ns hello-bot.car)

(def forward {
    :yellow-led          :low
    :green-led           :low
    :left-forward-motor  :high
    :left-reverse-motor  :low
    :right-forward-motor :high
    :right-reverse-motor :low
  })

(def stop {
    :yellow-led          :high
    :green-led           :high
    :left-forward-motor  :low
    :left-reverse-motor  :low
    :right-forward-motor :low
    :right-reverse-motor :low
  })

(def left {
    :yellow-led          :high
    :green-led           :low
    :left-forward-motor  :low
    :left-reverse-motor  :high
    :right-forward-motor :high
    :right-reverse-motor :low
  })

(def right {
    :yellow-led          :low
    :green-led           :high
    :left-forward-motor  :high
    :left-reverse-motor  :low
    :right-forward-motor :low
    :right-reverse-motor :high
  })
