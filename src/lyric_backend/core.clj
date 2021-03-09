(ns lyric-backend.core
  (:require [lyric-backend.server :as server]))

(defn start
  [& args]
  (server/start-server))
