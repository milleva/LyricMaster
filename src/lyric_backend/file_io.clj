(ns lyric-backend.file-io)

(defn file->str [filename]
  (-> (.getFile (clojure.java.io/resource filename))
      slurp))
