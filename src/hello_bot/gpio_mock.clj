(ns hello-bot.gpio-mock)

;; This is based off the docs, not the source. Very likely to be wrong.

(defn open-port [port]
  (println "open-port" port)
  port)

(defn set-direction! [port in-or-out]
  (println "set-direction!" port in-or-out))

(defn write-value! [port high-or-low]
  (println "write-value!" port high-or-low))

(defn close! [port]
  (println "close!" port))
