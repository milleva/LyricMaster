(ns lyric-master.song-analyzer)

(defn word-used-times [words word]
  (->> words
       (filter #(= % word))
       count))

(defn analyze-song-lyrics [{:keys [words bars]}]
  (let [words-sorted (sort words)
        words-distinct (distinct words-sorted)]
    {:bar-count (count bars)
     :word-count (count words)
     :distinct-word-count (count words-distinct)
     :word-usage-amounts (into (sorted-map)
                               (group-by (partial word-used-times words-sorted)
                                         words-distinct))}))         ;make this unique
