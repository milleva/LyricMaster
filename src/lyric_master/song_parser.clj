(ns lyric-master.song-parser
  (require [clojure.string :as str]))

(def new-song-regex #"NEW SONG")
(def title-line-regex #"TITLE.*[\n]")
(def round-bracket-regex #"\(.*\)")
(def square-bracket-regex #"\[.*\]")
(def quoted-text-regex #"\".*\"")
(def plain-text-with-spaces-regex #"[a-z|A-Z|\s]+")

(defn- remove-from-str [regex str]
  (str/replace str regex ""))

(defn- find-artist [song]
  (->> song
       (re-find square-bracket-regex)
       (re-find plain-text-with-spaces-regex)))

(defn- find-title [song]
  (->> song
       (re-find title-line-regex)
       (re-find quoted-text-regex)
       (re-find plain-text-with-spaces-regex)))

(defn parse-song [s]
  (let [remove-round-brackets (partial remove-from-str round-bracket-regex)
        remove-square-brackets (partial remove-from-str square-bracket-regex)
        remove-title-line (partial remove-from-str title-line-regex)

        title (find-title s)
        artist (find-artist s)
        lyrics (-> s
                   str/trim
                   remove-round-brackets
                   remove-square-brackets
                   remove-title-line
                   str/trim)]
    {:title title
     :artist artist
     :lyrics lyrics}))

(defn- extract-song-strings [song-file-str]
  (let [raw-songs (str/split song-file-str new-song-regex)
        ham-songs (remove str/blank? raw-songs)]
    ham-songs))

(defn parse-song-file-str [song-file-str]
  (->> song-file-str
      extract-song-strings
      (map parse-song)))

