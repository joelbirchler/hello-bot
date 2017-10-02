(defproject hello-bot "0.3.0-SNAPSHOT"
  :description "A small robot experiment"
  :url "http://www.joelbirchler.com"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [
    [org.clojure/clojure "1.8.0"]
    [org.clojure/core.async "0.2.395"]
    [org.clojure/core.match "0.3.0-alpha4"]
    [gpio "0.2.1"]
    [environ "1.1.0"]]
  :main ^:skip-aot hello-bot.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
