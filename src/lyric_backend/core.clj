(ns lyric-backend.core
  (:require [lyric-backend.file-io :as io]
            [lyric-backend.song-parser :as parser]
            [lyric-backend.song-analyzer :as a]
            [lyric-backend.api.datamuse-api :as datamuse]
            [lyric-backend.api.rhymebrain-api :as rhymebrain]))

(def eminem-lyric-file-name "eminem-song-lyrics.txt")
(def evan-lyric-file-name "evan-song-lyrics.txt")

(defn start [& args]
  (let [evan-song-str (io/file->str eminem-lyric-file-name)
        parsed-songs (parser/parse-song-dsl-string evan-song-str)
        song-1 (-> parsed-songs
                    first
                   :song)]
    (a/analyze-song-lyrics song-1)))
