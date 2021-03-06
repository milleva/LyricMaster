(ns lyric-backend.song-parser
  (:require [clojure.string :as str]))

(def new-song-regex #"NEW SONG")
(def round-bracket-regex #"\(.*\)")
(def square-bracket-regex #"\[.*\]")
(def quoted-text-regex #"\".*\"")
(def plain-text-with-spaces-regex #"[a-z|A-Z|\s]+")
(def whitespace-regex #"\s")
(def removed-special-chars-regex #"[,|?|!|\"]")

(def parsed-row-names [:title :artist])

(defn- ->parsed-row-regex [row-name]
  (let [row-name-str (-> row-name name str/upper-case)]
    (re-pattern (str row-name-str ".*[\n]"))))

(def parsed-row-regexes (map ->parsed-row-regex parsed-row-names))

(defn- remove-from-str [str regex]
  (str/replace str regex ""))

(defn- get-row-value [row song]
  (let [found-row (re-find (->parsed-row-regex row) song)]
    (when (some? found-row)
      (->> found-row
           (re-find quoted-text-regex)
           (re-find plain-text-with-spaces-regex)))))

(def remove-parsed-rows
  (apply comp
         (map
           (fn [regex] #(remove-from-str % regex))
           parsed-row-regexes)))


(defn- remove-non-lyrics [s]
  (-> s
      str/trim
      remove-parsed-rows
      (remove-from-str round-bracket-regex)
      (remove-from-str square-bracket-regex)
      str/trim))

(defn parse-song [s]
  (let [lyrics (remove-non-lyrics s)]
    {:title (get-row-value :title s)
     :artist (get-row-value :artist s)
     :song {:lyrics lyrics
            :bars (str/split-lines lyrics)
            :words (remove str/blank?
                           (-> lyrics
                               (remove-from-str removed-special-chars-regex)
                               str/lower-case
                               (str/split whitespace-regex)))}}))

(defn- extract-song-strings [song-file-str]
  (let [raw-songs (str/split song-file-str new-song-regex)
        non-empty-songs (remove str/blank? raw-songs)]
    non-empty-songs))

(defn parse-song-dsl-string [song-file-str]
  (->> song-file-str
      extract-song-strings
      (map parse-song)))

