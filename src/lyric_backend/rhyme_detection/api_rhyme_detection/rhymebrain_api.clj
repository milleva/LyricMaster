(ns lyric-backend.rhyme-detection.api-rhyme-detection.rhymebrain-api
  (:require [lyric-backend.http-common :refer :all]
            [lyric-backend.rhyme-detection.api-rhyme-detection.api-rhyme-detection :refer [api-rhyme?]]))

;//{:rhymes [{:word {res-object}}...] :infos [{:word {res-object}}]}
(def cache-key :rhymebrain)
(def base-url "https://rhymebrain.com/talk")

(defn get-rhymes [word]
  (let [max-results 500
        query-str (str "?function=getRhymes&maxResults=" max-results "&word=" word)
        url (str base-url query-str)]
    (get-cached word url :rhymes cache-key)))

(defn get-word-info [word]
  (let [url (str base-url "?function=getWordInfo&word=" word)]
    (get-cached word url :infos cache-key)))

(defn rhyme-detected? [w1 w2]
  (api-rhyme? w1 w2 get-rhymes))
