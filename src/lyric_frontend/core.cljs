(ns lyric-frontend.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<!]]
            [reagent.core :as r]
            [reagent.dom :as rd]
            [lyric-frontend.api :as api]
            [lyric-frontend.components.metric :refer [metric]]
            [stylefy.core :refer [use-style]]))
(enable-console-print!)
(println "reloading frontend")

(defonce rap-text-box-contents (r/atom ""))
(defonce server-analysis (r/atom nil))

(defn lyric-editor []
  (let []
    [:textarea {:on-change #(reset! rap-text-box-contents (-> % .-target .-value))
                :style     {:background-color "lightblue"
                            :border-radius    "5px"
                            :width            "80vw"
                            :height           "30em"
                            :font-size        "30px"
                            :font-family "Calibri"}}]))

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
   {:style {:overflow-y "scroll"
            :margin-top "20px"
            :margin-left "15px"}}
   (metric "Bar count" bar-count)
   (metric "Word count" word-count)
   (metric "Distinct word count" distinct-word-count)
   (metric "Rhyme count" rhyme-count)
   (metric "Distinct rhyme count" distinct-rhyme-count)
   (metric "Rhymes per bar" rhymes-per-bar)
   (metric "Rhymes per word" rhymes-per-word)
   (metric "Rhyming words detected" distinct-rhyming-words)
   (metric "Words frequencies" word-usage-amounts)])

(defn analysis-button [song-str]
  [:button [:input {:type     "button"
                    :value    "Analyze"
                    :on-click (create-analysis-button-handler song-str)
                    :style    {:font-weight   "bold"
                               :font-size     "20px"
                               :border        "3px solid"
                               :border-radius "5px"
                               :padding       "15px"
                               :background-color "white"
                               :margin "5px 0px 5px 0px"}}]])

(defn app []
  (let [song-str @rap-text-box-contents
        analysis @server-analysis]
    [:div
     {:style {:display "flex"
              :flex-direction "row"}}
     [:div
      [:h1 "Type or paste lyrics into this box"]
      (lyric-editor)
      [:div
       (analysis-button song-str)]]
     [:div
      [song-analysis-display analysis]]]))

(rd/render [app]
           (. js/document (getElementById "lyric-frontend")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )

