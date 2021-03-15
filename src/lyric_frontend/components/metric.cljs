(ns lyric-frontend.components.metric
  (:require [stylefy.core :refer [use-style]]))


(def metric-style {:margin           "10px 0px 30px 0px"
                   :padding          "10px"
                   :font-size        "28px"
                   :font-family      "Comic Sans MS"
                   :background-color "lightblue"})

(def metric-label-style
  {})

(def metric-value-style
  {:color "blue"})

(defn metric [label value]
  [:div
   (use-style metric-style)
   [:span
    (use-style metric-label-style)
    (str label)]
   ": "
   [:span
    (use-style metric-value-style)
    (str (or value "-"))]])
