(ns lyric-master.file-reader)

(defn file->str [filename]
  (-> (.getFile (clojure.java.io/resource filename))
      slurp))
