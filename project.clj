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
                 [ring "1.9.0"]
                 [ring/ring-json "0.5.0"]
                 [reagent "1.0.0"]
                 [jumblerg/ring-cors "2.0.0"]

                 [org.clojure/clojurescript "1.10.312"]
                 [cljs-http "0.1.46"]
                 [stylefy "2.2.1"]]

  :plugins [[lein-cljsbuild "1.1.4"
             :exclusions [org.clojure/clojure]]
            [lein-figwheel "0.5.20"]]

  :clean-targets ^{:protect false} ["resources/public/js/out"
                                    "resources/public/js/lyric_frontend.js"
                                    :target-path]

  :source-paths ["src"]
  :main "lyric-backend.core/start"

  :cljsbuild {
              :builds [{:id "lyric-frontend"
                        :source-paths ["src"]
                        :figwheel true
                        :compiler {
                                   :main lyric-frontend.core
                                   :asset-path "js/out"
                                   :output-to "resources/public/js/lyric_frontend.js"
                                   :output-dir "resources/public/js/out"
                                   :source-map-timestamp true}}]}

  :figwheel { :css-dirs ["resources/public/css"]
             :open-file-command "emacsclient"
             })

