(ns lyric-backend.cache)

;E.g.
;client-name = :rhymebrain
;type = :rhymes
;key = :shizzle

(def ^:private initial-cache
  {:rhymebrain {:rhymes {}
                :infos  {}}
   :datamuse   {:rhymes {}}})
(def ^:private cache (atom initial-cache))

(defn save-to-cache! [{:keys [client-name
                               type
                               key]} val]
  (swap! cache assoc-in (map keyword [client-name type key]) val)
  val)

(defn fetch-from-cache [{:keys [client-name
                                type
                                key]}]
  (let [client-name-keyword (keyword client-name)
        type-keyword (keyword type)
        key-keyword (keyword key)]
    (-> @cache
        client-name-keyword
        type-keyword
        key-keyword)))
