(ns lyric-backend.file-reader)

(defn file->str [filename]
  (-> (.getFile (clojure.java.io/resource filename))
      slurp))
