(ns lyrics-frontend.core
    (:require
              [reagent.core :as reagent :refer [atom]]
              [reagent.dom :as rd]))

(enable-console-print!)

(println "This text is printed from src/lyrics-frontend/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce rap-text-box-contents (atom ""))

(defn rap-text-box []
      (let [write-to-box (fn [str] (reset! rap-text-box-contents str))]
           [:textarea {:on-change #(reset! rap-text-box-contents (-> % .-target .-value))}]))

(defn hello-world []
  [:div
   [:h1 (str "Lyrics: " @rap-text-box-contents)]
   [:h3 "Paste lyrics into this checkbox to analyze them"]
   [rap-text-box]])

(rd/render [hello-world]
           (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
