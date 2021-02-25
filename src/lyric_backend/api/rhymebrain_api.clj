(ns lyric-backend.api.rhymebrain-api
  (:require [lyric-master.api.http-common :refer :all]))

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
