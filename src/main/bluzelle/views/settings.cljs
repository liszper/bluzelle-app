(ns bluzelle.views.settings
  (:require
   [bluzelle.styles :refer [styles]]
   [bluzelle.modules :refer [Text View]]))

(defn SettingsScreen []
  (fn []
    [View {:style (:cflex styles)}
     [Text "Settings"]]))
