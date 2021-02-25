(ns lyric-backend.song-analyzer
  (:require [lyric-master.rhyme-detection :as rhyme]))

(defn word-used-times [words word]
  (->> words
       (filter #(= % word))
       count))

(defn analyze-song-lyrics [{:keys [words bars]}]
  (let [sorted-words (sort words)
        distinct-words (distinct sorted-words)
        rhymes (rhyme/filter-words-that-rhyme words)
        rhyme-count (count rhymes)
        distinct-rhymes (distinct rhymes)
        distinct-rhyme-count (count distinct-rhymes)
        bar-count (count bars)
        word-count (count words)]
    {:bar-count bar-count
     :word-count word-count
     :rhyme-count (count rhymes)
     :distinct-word-count (count distinct-words)
     :distinct-rhyme-count (count distinct-rhymes)
     :distinct-rhyming-words distinct-rhymes
     :rhymes-per-bar (/ distinct-rhyme-count (float bar-count))
     :rhymes-per-word (/ distinct-rhyme-count (float word-count))
     :word-usage-amounts (into (sorted-map)
                               (group-by (partial word-used-times sorted-words)
                                         distinct-words))}))         ;make this unique
