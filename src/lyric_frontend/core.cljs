(ns lyric-frontend.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<!]]
            [reagent.core :as r :refer [atom]]
            [reagent.dom :as rd]
            [lyric-frontend.api :as api]))

(enable-console-print!)

(println "This text is printed from src/lyrics-frontend/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce rap-text-box-contents (atom ""))
(defonce server-analysis-word-count (atom nil))
(defonce server-analysis-distinct-word-count (atom nil))

(defn rap-text-box []
  (let []
    [:textarea {:on-change #(reset! rap-text-box-contents (-> % .-target .-value))
                :style {:background-color "grey"
                        :corner-radius "5px"
                        :width "80vw"
                        :height "30em"}}]))

(defn- update-server-analysis-results!
  [{:keys [word-count
           distinct-word-count]}]
  (reset! server-analysis-word-count word-count)
  (reset! server-analysis-distinct-word-count distinct-word-count))

(defn- create-analysis-button-handler [song-str]
  (fn [_]
    (go
      (let [server-analysis-results (<! (api/post-song-for-analysis song-str))]
        (update-server-analysis-results! server-analysis-results)))))

(defn app []
  (let [song-str @rap-text-box-contents
        word-count @server-analysis-word-count
        distinct-word-count @server-analysis-distinct-word-count]
    [:div
     [:h1 (str "Lyrics: " (apply str (concat
                                       (take 13 song-str)
                                       [(if (> (count song-str) 0) "..." "")])))]
     [:h3 "Paste lyrics into this box"]
     [rap-text-box]
     [:h3 "Click to analyze"]
     [:button [:input {:type     "button" :value "Click me!"
                       :on-click (create-analysis-button-handler song-str)}]]
     [:h3 (str "word count: " word-count)]
     [:h3 (str "distinct word count: " distinct-word-count)]]))

(rd/render [app]
           (. js/document (getElementById "lyric-frontend")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )

