(ns hello-bot.core
  (:require [hello-bot.input-device :as input]
            [hello-bot.output-device :as output]
            [hello-bot.car :as car]
            [clojure.core.async :as async :refer [>! <! >!! <!! go go-loop chan]])
  (:gen-class))

(def state (atom car/stop))
(def bumper-chan (input/read-channel :bumper))

(defn on-state-change [_watch-key _ref _old-state new-state]
  (output/write! new-state))

(defn init! []
  (println "Hello!")
  (output/write! @state)
  (add-watch state :state-watch on-state-change))

(defn mut! 
  ([new-state] 
    (swap! state merge new-state))
  ([new-state seconds] 
    (mut! new-state)
    (Thread/sleep (* seconds 1000))))

(defn bumped! []
  (println "Bump!")
  (mut! car/backward 0.3)
  (mut! car/left 0.2)
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
