(ns lyric-frontend.views.editor
  (:require [reagent.core :as r]))

(defonce rap-text-box-contents (r/atom ""))

(defn lyric-editor []
  (let []
    [:textarea {:on-change #(reset! rap-text-box-contents (-> % .-target .-value))
                :style {:background-color "grey"
                        :corner-radius "5px"
                        :width "80vw"
                        :height "30em"}}]))
