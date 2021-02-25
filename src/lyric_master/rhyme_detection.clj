(ns lyric-master.rhyme-detection
  (:require [lyric-master.api.datamuse-api :as datamuse]
            [lyric-master.api.rhymebrain-api :as rhymebrain]))

(defn api-rhyme? [w1 w2 api-get]
  (let [get-rhymes #(->> %
                         api-get
                         (map :word))
        w1-rhymes (get-rhymes w1)
        w2-rhymes (get-rhymes w2)]
    (boolean
      (or
        ((set w1-rhymes) w2)
        ((set w2-rhymes) w1)))))

(defn datamuse-rhyme? [w1 w2]
  (api-rhyme? w1 w2 datamuse/get-rhymes))

(defn rhymebrain-rhyme? [w1 w2]
  (api-rhyme? w1 w2 rhymebrain/get-rhymes))

(defn custom-rhyme-detected? [w1 w2]
  false)

(defn words-rhyme? [w1 w2]
  (and
    (not= w1 w2)
    (or
      (datamuse-rhyme? w1 w2)
      (rhymebrain-rhyme? w1 w2)
      (custom-rhyme-detected? w1 w2))))

(defn- has-rhyme-in-coll? [word compared-words]
  (boolean (seq
             (filter #(words-rhyme? word %) compared-words))))

(defn filter-words-that-rhyme [distinct-words]
  (filter #(has-rhyme-in-coll? % distinct-words)
          distinct-words))
