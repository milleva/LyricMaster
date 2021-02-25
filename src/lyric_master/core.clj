(ns lyric-master.core
  (:require [lyric-master.file-reader :as io]
            [lyric-master.song-parser :as parser]
            [lyric-master.song-analyzer :as a]
            [lyric-master.api.datamuse-api :as datamuse]
            [lyric-master.api.rhymebrain-api :as rhymebrain]))

(def eminem-lyric-file-name "eminem-song-lyrics.txt")

(defn get-all-rhyming-words-from-apis [w]
  (map :word (concat
               (rhymebrain/get-rhymes w)
               (datamuse/get-rhymes w))))

(defn start [& args]
  (let [song-str (io/file->str eminem-lyric-file-name)
        parsed-songs (parser/parse-song-dsl-string song-str)
        song-1 (-> parsed-songs
                    first)]
    song-1))
