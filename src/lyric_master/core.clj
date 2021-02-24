(ns lyric-master.core
  (require [lyric-master.file-reader :as io]
           [lyric-master.song-parser :as parser]))

(def eminem-lyric-file-name "eminem-song-lyrics.txt")

(defn start [& args]
  (let [song-str (io/file->str eminem-lyric-file-name)
        parsed-songs (parser/parse-song-file-str song-str)]
    parsed-songs))
