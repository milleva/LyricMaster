(ns lyric-backend.http-common
  (:require [clojure.data.json :as json]
            [clojure.walk :as w]
            [clj-http.client :as client]
            [lyric-backend.cache :refer [fetch-from-cache save-to-cache!]]))

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
        cached (fetch-from-cache {:client-name client-name
                                  :type cache-type
                                  :key cache-key})]
    (if (some? cached)
      cached
      (let [result (get-req url)]
        (when (some? result)
          (save-to-cache! {:client-name client-name
                           :type cache-type
                           :key cache-key}
                          result))))))
