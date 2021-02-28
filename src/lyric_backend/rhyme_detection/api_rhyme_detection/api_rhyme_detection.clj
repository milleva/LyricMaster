(ns lyric-backend.rhyme-detection.api-rhyme-detection.api-rhyme-detection)

(defn api-rhyme? [w1 w2 api-get-fn]
  (let [get-rhymes #(->> %
                         api-get-fn
                         (map :word))
        _ (println "lol3" w1)
        w1-rhymes (get-rhymes w1)
        w2-rhymes (get-rhymes w2)]
    (boolean
      (or
        ((set w1-rhymes) w2)
        ((set w2-rhymes) w1)))))
