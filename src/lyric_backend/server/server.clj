(ns lyric-backend.server.server
  (:require [ring.adapter.jetty :as jetty]
            [reitit.ring :as ring]))

(def ^:private service-port 3000)

(defn handler-1 [request]
  {:status 201
   :headers {"Content-Type" "text/plain"}
   :body "Hello 1"})

(defn handler-2 [request]
  {:status 201
   :headers {"Content-Type" "text/plain"}
   :body "ok"})

(def main-handler
  (ring/ring-handler
    (ring/router [["/" {:get handler-1}]
                  ["/analysis" {:get handler-2
                                :post handler-2}]])
    (ring/create-default-handler)))

(defn start-server []
  (jetty/run-jetty main-handler
                   {:port service-port
                    :host "localhost"
                    :join? false}))
