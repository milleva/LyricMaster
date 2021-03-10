(ns lyric-frontend.api
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(def ^:private base-url "http://localhost:3000")

(defn post-song-for-analysis [song-str is-full-analysis]
  (go (let [url      (str base-url "/analysis")
            {:keys [status body]} (<! (http/post url
                                    {:json-params {:is-full-analysis is-full-analysis
                                                   :song song-str}}))
            parsed-body (.parse js/JSON body)
            parsed-clj-body (js->clj parsed-body :keywordize-keys true)]
        (when (< 199 status 300)
          parsed-clj-body))))

