(ns lyric-frontend.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<!]]
            [reagent.core :as r :refer [atom]]
            [reagent.dom :as rd]
            [lyric-frontend.api :as api]
            [clojure.string :as str]))

(enable-console-print!)

(println "This text is printed from src/lyrics-frontend/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce rap-text-box-contents (atom ""))

(defonce server-analysis (atom nil))

(defn rap-text-box []
  (let []
    [:textarea {:on-change #(reset! rap-text-box-contents (-> % .-target .-value))
                :style {:background-color "grey"
                        :corner-radius "5px"
                        :width "80vw"
                        :height "30em"}}]))

(defn- update-server-analysis-results! [song-analysis-result]
  (reset! server-analysis song-analysis-result))

(defn- create-analysis-button-handler [song-str]
  (fn [_]
    (when (seq song-str)
      (go
        (let [server-analysis-results (<! (api/post-song-for-analysis song-str))]
          (update-server-analysis-results! server-analysis-results))))))

(defn song-analysis-display
  [{:keys [bar-count
           distinct-rhyme-count
           distinct-rhyming-words
           distinct-word-count
           rhyme-count
           rhymes-per-bar
           rhymes-per-word
           word-count
           word-usage-amounts]}]
  [:div
   [:h5 (str "Bar count: " bar-count)]
   [:h5 (str "Word count: " word-count)]
   [:h5 (str "Distinct word count: " distinct-word-count)]
   [:h5 (str "Rhyme count: " rhyme-count)]
   [:h5 (str "Distinct rhyme count: " distinct-rhyme-count)]
   [:h5 (str "Rhymes per bar: " rhymes-per-bar)]
   [:h5 (str "Rhymes per word: " rhymes-per-word)]])

(defn app []
  (let [song-str @rap-text-box-contents
        analysis @server-analysis]
    [:div
     [:h1 (str "Lyrics: " (apply str (concat
                                       (take 13 song-str)
                                       [(if (> (count song-str) 0) "..." "")])))]
     [:h3 "Paste lyrics into this box"]
     [rap-text-box]
     [:h3 "Click to analyze"]
     [:button [:input {:type     "button" :value "Click me!"
                       :on-click (create-analysis-button-handler song-str)}]]
     [song-analysis-display analysis]]))

(rd/render [app]
           (. js/document (getElementById "lyric-frontend")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )

