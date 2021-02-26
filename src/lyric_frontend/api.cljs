(ns lyric-frontend.api
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(def ^:private base-url "http://localhost:3000")

(defn post-song-for-analysis [rap-str]
  (go (let [url      (str base-url "/analysis")
            response (<! (http/post url
                                    {:json-params {:rap rap-str}}))]
        (js/console.log (:status response))
        (js/console.log (:body response)))))

