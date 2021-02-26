(ns lyric-frontend.api
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(def ^:private base-url "http://localhost:3000")

(defn post-song-for-analysis [song-str]
  (go (let [url      (str base-url "/analysis")
            {:keys [status body]} (<! (http/post url
                                    {:json-params {:song song-str}}))
            parsed-body (js->clj (.parse js/JSON body))]
        (when (< 199 status 300)
          (println parsed-body)
          (println (type parsed-body))
          (println (-> parsed-body (get "word-count")))
          parsed-body))))

