(ns hello-bot.core
  (:require [hello-bot.driver :as driver]
            [hello-bot.car :as car])
  (:gen-class))

(def state (atom car/stop))

(defn on-state-change [_watch-key _ref _old-state new-state]
  (driver/write! new-state))

(defn init! []
  (println "Hello!")
  (driver/write! @state)
  (add-watch state :state-watch on-state-change))

(defn go! [new-state]
  (swap! state merge new-state))

;; This won't work for multi-tasking/event receiving
(defn go-sleep! [new-state s]
  (go! new-state)
  (Thread/sleep (* s 1000)))

(defn drive-in-a-square! []
  (go-sleep! car/stop 1)
  (go-sleep! car/forward 2)
  (go-sleep! car/right 0.3)
  (go-sleep! car/forward 2)
  (go-sleep! car/right 0.3)
  (go-sleep! car/forward 2)
  (go-sleep! car/right 0.3)
  (go-sleep! car/forward 2)
  (go-sleep! car/right 0.3)
  (go-sleep! car/forward 2)
  (go-sleep! car/right 5)
  (go! car/stop))

(defn -main [& args]
  (init!)
  (drive-in-a-square!)
  :wat)
