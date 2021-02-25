(ns lyric-master.rhyme-detection
  (:require [lyric-master.api.datamuse-api :as datamuse]
            [lyric-master.api.rhymebrain-api :as rhymebrain]))

(defn api-rhyme? [w1 w2 api-get]
  (let [get-rhyming-words #(->> %
                                api-get
                                (map :word))
        w1-rhymes (get-rhyming-words w1)
        w2-rhymes (get-rhyming-words w2)]
    (boolean
      (or
        ((set w1-rhymes) w2)
        ((set w2-rhymes) w1)))))

(defn datamuse-rhyme? [w1 w2]
  (api-rhyme? w1 w2 datamuse/get-rhymes))

(defn rhymebrain-rhyme? [w1 w2]
  (api-rhyme? w1 w2 rhymebrain/get-rhymes))
