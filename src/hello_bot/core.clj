(ns hello-bot.core
  (:require [hello-bot.driver :as driver])
  (:gen-class))

(def state
  (atom {
    :yellow-led :low
    :green-led :low
    :left-forward-motor :low
    :left-reverse-motor :low
    :right-forward-motor :low
    :right-reverse-motor :low
  }))

(def left-motor {
  :forward :left-forward-motor
  :reverse :left-reverse-motor
  })

(def right-motor {
  :forward :right-forward-motor
  :reverse :right-reverse-motor
  })

(defn on-state-change [_watch-key _ref _old-state new-state]
  (println "Updated to:" new-state))

(defn shutdown! []
  (println "Goodbye!")
  (driver/close-all! @state))

(defn init! []
  (println "Hello!")
  (driver/open-all! @state)
  
  (add-watch state :state-watch on-state-change))

(defn -main [& args]
  (init!)
  :wat)
