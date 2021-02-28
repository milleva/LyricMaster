(ns lyric-backend.rhyme-detection.api-rhyme-detection.datamuse-api
  (:require [lyric-backend.http-common :refer :all]
            [lyric-backend.rhyme-detection.api-rhyme-detection.api-rhyme-detection :refer [api-rhyme?]]))

(def ^:private client-name :datamuse)
(def ^:private base-url "https://api.datamuse.com/")

(defn get-rhymes [word]
  (let [url (str base-url "words?sl=" word)]
    (get-cached word url :rhymes client-name)))

(defn rhyme-detected? [w1 w2]
  (api-rhyme? w1 w2 get-rhymes))
