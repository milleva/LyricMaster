(defproject lyric-master "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/data.json "1.0.0"]
                 [clj-http "3.12.0"]
                 [metosin/reitit "0.5.12"]
                 [javax.servlet/servlet-api "2.5"]
                 [ring "1.6.3"]])

(comment
  "If you are using the ring/ring-core namespace on its own, you may run into errors when executing tests or running alternative adapters. To resolve this, include the following dependency in your dev profile:\n\n

  ")
