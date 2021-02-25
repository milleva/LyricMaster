(ns lyric-backend.api.datamuse-api
  (:require [lyric-backend.api.http-common :refer :all]))

(def ^:private cache-key :datamuse)
(def ^:private base-url "https://api.datamuse.com/")

(defn get-rhymes [word]
  (let [url (str base-url "words?sl=" word)]
    (get-cached word url :rhymes cache-key)))
