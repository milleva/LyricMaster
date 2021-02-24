(ns lyric-master.core
  (:require [lyric-master.file-reader :as io]
           [lyric-master.song-parser :as parser]
           [lyric-master.song-analyzer :as a]))

(def eminem-lyric-file-name "eminem-song-lyrics.txt")

(defn start [& args]
  (let [song-str (io/file->str eminem-lyric-file-name)
        parsed-songs (parser/parse-song-dsl-string song-str)
        song-1 (-> parsed-songs
                    first
                    :song)]
    (a/analyze-song-lyrics song-1)))
