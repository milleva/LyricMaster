(ns lyric-backend.rhyme-detection.combined-rhyme-detection
  (:require [lyric-backend.rhyme-detection.api-rhyme-detection.datamuse-api :as datamuse]
            [lyric-backend.rhyme-detection.api-rhyme-detection.rhymebrain-api :as rhymebrain]))

(defn custom-rhyme-detected? [w1 w2]
  false)

(defn any-api-found-rhyme? [w1 w2]
  (or (datamuse/rhyme-detected? w1 w2)
      (rhymebrain/rhyme-detected? w1 w2)))

(defn- words-rhyme? [w1 w2]
  (and
    (not= w1 w2)
    (or
      (custom-rhyme-detected? w1 w2)
      (any-api-found-rhyme? w1 w2))))

(defn- has-rhyme-in-coll? [word compared-words]
  (boolean (seq
             (filter #(words-rhyme? word %) compared-words))))

(defn filter-words-that-rhyme [distinct-words]
  (filter #(has-rhyme-in-coll? % distinct-words)
          distinct-words))
