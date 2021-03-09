(ns lyric-backend.file-io)

(def eminem-lyric-file-name "eminem-song-lyrics.txt")
(def evan-lyric-file-name "evan-song-lyrics.txt")

(defn file->str [filename]
  (-> (.getFile (clojure.java.io/resource filename))
      slurp))

(defn str->file [str filename]
  (-> (.getFile (clojure.java.io/resource filename))
      (spit str)))
