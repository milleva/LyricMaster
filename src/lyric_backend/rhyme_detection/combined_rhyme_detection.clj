(ns lyric-backend.rhyme-detection.combined-rhyme-detection
  (:require [lyric-backend.rhyme-detection.api-rhyme-detection.datamuse-api :as datamuse]
            [lyric-backend.rhyme-detection.api-rhyme-detection.rhymebrain-api :as rhymebrain]
            [lyric-backend.rhyme-detection.simple-rhyme-detection :as simple-detection]))


(defn- api-rhyme-detected? [w1 w2]
  (or (datamuse/rhyme-detected? w1 w2)
      (rhymebrain/rhyme-detected? w1 w2)))

(defn- words-rhyme? [w1 w2 is-using-api]
  (and
    (not= w1 w2)
    (or
      (simple-detection/rhyme-detected? w1 w2)
      (when is-using-api
        (api-rhyme-detected? w1 w2)))))

(defn- has-rhyme-in-coll? [word compared-words is-using-api]
  (boolean (seq
             (filter #(words-rhyme? % word is-using-api) compared-words))))

(defn filter-words-that-rhyme [distinct-words is-using-api]
  (filter #(has-rhyme-in-coll? % distinct-words is-using-api)
          distinct-words))
