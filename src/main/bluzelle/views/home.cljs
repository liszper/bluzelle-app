(ns bluzelle.views.home
  (:require
   [bluzelle.styles :refer [styles]]
   [bluzelle.modules :refer [Text View Icon]]))

(println Icon Text)

(defn HomeScreen []
  (fn []
    [View {:style [(:cflex styles) {:backgroundColor "blue" :maxHeight "50px"}]}
     [Text {:style (:title styles)} "bluzelle chat"]]))
