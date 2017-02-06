(defproject hello-bot "0.2.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
    [org.clojure/clojure "1.8.0"]
    [org.clojure/core.async "0.2.395"]
    [org.clojure/core.match "0.3.0-alpha4"]
    [clj-gpio "0.2.0"]
    [environ "1.1.0"]]
  :main ^:skip-aot hello-bot.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
