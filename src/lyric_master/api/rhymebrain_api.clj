(ns lyric-master.api.rhymebrain-api
  (:require [lyric-master.api.http-common :refer :all]))


;//{:rhymes [{:word {res-object}}...] :infos [{:word {res-object}}]}

(def base-url "https://rhymebrain.com/talk")

(defn get-rhymes [word]
  (let [url (str base-url "?function=getRhymes&maxResults=1000&word=" word)]
    (get-cached word url :rhymes)))

(defn get-word-info [word]
  (let [url (str base-url "?function=getWordInfo&word=" word)]
    (get-cached word url :infos)))
