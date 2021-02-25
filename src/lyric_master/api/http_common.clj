(ns lyric-master.api.http-common
  (:require [clojure.data.json :as json]
            [clojure.walk :as w]
            [clj-http.client :as client]))

(def ^:private initial-cache
  {:rhymebrain {:rhymes {}
                :infos  {}}
   :datamuse   {:rhymes {}}})
(def ^:private cache (atom initial-cache))

(defn- save-to-cache! [{:keys [client-name
                               type
                               key]} val]
  (swap! cache assoc-in (map keyword [client-name type key]) val)
  val)

(defn- status-ok? [status]
  (<= 200 status 299))

(defn- get-req [url]
  (try
    (let [{:keys [status body] :as response} (client/get url)]
      (println (str "http GET - " url))
      (if (status-ok? status)
        (-> body
            json/read-str
            w/keywordize-keys)
        (do
          (println (str "Request to " url "received bad status code:\n" response))
          {})))
    (catch Exception e
      (println (str "Request to " url "failed: " e))
      {})))

(defn get-cached [word url cache-type client-name]
  (let [cache-key (keyword word)
        cached (-> @cache client-name cache-type cache-key)]
    (if (some? cached)
      cached
      (let [result (get-req url)]
        (when (some? result)
          (save-to-cache! {:client-name client-name
                           :type cache-type
                           :key cache-key}
                          result))))))
