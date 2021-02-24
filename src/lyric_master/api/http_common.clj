(ns lyric-master.api.http-common
  (:require [clojure.data.json :as json]
            [clojure.walk :as w]
            [clj-http.client :as client]))

(def initial-cache (atom {:rhymes {}
                          :infos {}}))
(def cache initial-cache)

(defn- save-to-cache! [cache-type key val]
  (swap! cache assoc-in (map keyword [cache-type key]) val)
  val)

(defn status-ok? [status]
  (<= 200 status 299))

(defn get-req [url]
  (let [{:keys [status body] :as response} (client/get url)]
    (if (status-ok? status)
      (-> body
          json/read-str
          w/keywordize-keys)
      (println (str "Request to " url "failed with response:\n" response)))))

(defn get-cached [word url cache-type]
  (let [cache-key (keyword word)
        cached (-> @cache cache-type cache-key)]
    (if (some? cached)
      cached
      (let [result (get-req url)]
        (when (some? result)
          (save-to-cache! cache-type cache-key result))))))
