(ns hello-bot.core
  (:require [hello-bot.driver :as driver]
            [hello-bot.car :as car]
            [clojure.core.async :as async :refer [>! <! >!! <!! go go-loop chan]])
  (:gen-class))

(def state (atom car/stop))
(def bumper-chan (driver/read-channel :bumper))

(defn on-state-change [_watch-key _ref _old-state new-state]
  (driver/write! new-state))

(defn init! []
  (println "Hello!")
  (driver/write! @state)
  (add-watch state :state-watch on-state-change))

(defn mut! 
  ([new-state] 
    (swap! state merge new-state))
  ([new-state seconds] 
    (mut! new-state)
    (Thread/sleep (* seconds 1000))))

(defn bumped! []
  (println "Bump!")
  (mut! car/backward 0.2)
  (mut! car/right 0.2)
  (mut! car/forward))

(defn drive! []
  (mut! car/forward)
  (go-loop []
    (if (<! bumper-chan) (bumped!))
    (recur)))

(defn -main [& args]
  (init!)
  (drive!)
  :ok)
