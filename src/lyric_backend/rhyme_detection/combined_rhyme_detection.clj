(ns lyric-backend.rhyme-detection.combined-rhyme-detection
  (:require [lyric-backend.rhyme-detection.api-rhyme-detection.datamuse-api :as datamuse]
            [lyric-backend.rhyme-detection.api-rhyme-detection.rhymebrain-api :as rhymebrain]))

(defn- matching-tail-n? [w1 w2 n]
  (let [c1 (count w1)
        c2 (count w2)]
    (when (and (>= c1 n)
               (>= c2 n))
      (= (subs w1 (- c1 n) c1)
         (subs w2 (- c2 n) c2)))))

(defn- matching-tail-4? [w1 w2]
  (matching-tail-n? w1 w2 4))

(defn- custom-rhyme-detected? [w1 w2]
  (matching-tail-4? w1 w2))

(defn- api-rhyme-detected? [w1 w2]
  (or (datamuse/rhyme-detected? w1 w2)
      (rhymebrain/rhyme-detected? w1 w2)))

(defn- words-rhyme? [w1 w2 is-using-api]
  (and
    (not= w1 w2)
    (or
      (custom-rhyme-detected? w1 w2)
      (when is-using-api
        (api-rhyme-detected? w1 w2)))))

(defn- has-rhyme-in-coll? [word compared-words is-using-api]
  (boolean (seq
             (filter #(words-rhyme? % word is-using-api) compared-words))))

(defn filter-words-that-rhyme [distinct-words is-using-api]
  (filter #(has-rhyme-in-coll? % distinct-words is-using-api)
          distinct-words))
