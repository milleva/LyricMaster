(ns lyric-master.core
  (:require [lyric-master.file-reader :as io]
            [lyric-master.song-parser :as parser]
            [lyric-master.song-analyzer :as a]
            [lyric-master.api.datamuse-api :as datamuse]
            [lyric-master.api.rhymebrain-api :as rhymebrain]))

(def eminem-lyric-file-name "eminem-song-lyrics.txt")
(def evan-lyric-file-name "evan-song-lyrics.txt")

(defn start [& args]
  (let [evan-song-str (io/file->str eminem-lyric-file-name)
        parsed-songs (parser/parse-song-dsl-string evan-song-str)
        song-1 (-> parsed-songs
                    first
                   :song)]
    (a/analyze-song-lyrics song-1)))
