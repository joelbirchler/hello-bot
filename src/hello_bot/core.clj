(ns hello-bot.core
  (:require [hello-bot.driver :as driver]
            [hello-bot.car :as car]
            [clojure.core.async :as async :refer [>! <! >!! <!! go go-loop chan]])
  (:gen-class))

(def state (atom car/stop))
(def bumper-chan (chan))

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

(defn bump! []
  (>!! bumper-chan :bump))

(defn bumped! []
  (mut! car/backward 0.1)
  (mut! car/right 0.3)
  (mut! car/forward))

(defn drive! []
  (mut! car/forward)
  (go-loop []
    (<! bumper-chan)
    (bumped!)
    (recur)))

(defn -main [& args]
  (init!)
  (drive!)
  :ok)
