(ns lyric-master.songparser
  (require [clojure.string :as str]))

(defn- extract-bars [song])

(defn- extract-words [song])

(defn parse-song-text [s]
  (let [withot-brackets (str/replace s #"(/d+)" )]))

(defn analyze-song [song-raw]
  (let [song (parse-song-text song-raw)
        bars (extract-bars song)
        words (extract-words song)]))
