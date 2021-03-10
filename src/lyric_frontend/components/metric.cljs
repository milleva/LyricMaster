(ns lyric-frontend.components.metric
  (:require [stylefy.core :refer [use-style]]))


(def metric-style {:padding   "10px 0px 10px 0px"
                   :font-size         "50px"
                   :border-bottom "1px solid black"
                   :padding-bottom "10px"})

(def metric-label-style
  {:font-weight "Bold"})

(defn metric [label value]
  [:div
   (use-style metric-style)
   [:div
    (use-style metric-label-style)
    (str label ":")]
   [:div
    (str (or value 100 "unknown"))]])
