(ns lyric-frontend.core
  (:require
    [reagent.core :as reagent :refer [atom]]
    [reagent.dom :as rd]
    [lyric-frontend.api :as api]))

(enable-console-print!)

(println "This text is printed from src/lyrics-frontend/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce rap-text-box-contents (atom ""))
(defonce server-analysis (atom ""))

(defn rap-text-box []
  (let []
    [:textarea {:on-change #(reset! rap-text-box-contents (-> % .-target .-value))
                :style {:background-color "grey"
                        :corner-radius "5px"
                        :width "80vw"
                        :height "30em"}}]))

(defn app []
  (let [rap-text @rap-text-box-contents
        analysis @server-analysis]
    [:div
     [:h1 (str "Lyrics: " (apply str (concat
                                       (take 13 rap-text)
                                       [(if (> (count rap-text) 0) "..." "")])))]
     [:h3 "Paste lyrics into this checkbox"]
     [rap-text-box]
     [:h3 "Click to analyze"]
     [:button [:input {:type     "button" :value "Click me!"
                       :on-click #(reset! server-analysis (api/post-song-for-analysis rap-text))}]]
     [:h3 (str "word count:" (js/parseInt analysis))]]))

(rd/render [app]
           (. js/document (getElementById "lyric-frontend")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )

