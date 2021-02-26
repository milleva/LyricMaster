(ns lyric-backend.server.server
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [reitit.ring :as ring]
            [jumblerg.middleware.cors :refer [wrap-cors]]
            [ring.util.request :refer [body-string]]
            [clojure.data.json :as json]
            [lyric-backend.song-parser :as song-parser]
            [lyric-backend.song-analyzer :as song-analyzer]))

(def ^:private service-port 3000)

(defn ping-handler [_]
  {:status 200
   :body "Hello ping"})

(defn analysis-handler [request]
  (let [req-body (:body request)
        song-str (:song req-body)
        parsed-song (-> song-str
                        song-parser/parse-song-dsl-string
                        first
                        :song)
        analyzed-song (song-analyzer/analyze-song-lyrics parsed-song)]
    {:status 201
     :headLers {"Content-Type" "text/plain"}
     :body (json/write-str analyzed-song)}))

(def main-handler
  (ring/ring-handler
    (ring/router [["/ping" {:get ping-handler}]
                  ["/analysis" {:post analysis-handler}]])
    (ring/create-default-handler)))

(def with-middlewares
  (-> main-handler
      (wrap-cors identity)                                  ;TODO remove or fix
      wrap-json-response
      (wrap-json-body {:keywords? true :bigdecimals? true})))

(defn start-server []
  (jetty/run-jetty with-middlewares
                   {:port service-port
                    :host "localhost"
                    :join? false}))
