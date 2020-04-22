(ns bluzelle.app
  (:require
   ["expo" :as ex]
   ["react" :as react]
   ["bluzelle" :as blz]
   ["uuid" :as uuid]
   [reagent.core :as r :refer [as-element]]
   [re-frame.core :as rf]
   [shadow.expo :as expo]
   [bluzelle.events]
   [bluzelle.subs]
   [bluzelle.modules :refer [Text View Icon NavigationContainer Navigator Screen]]
   [bluzelle.views.chat :refer [Chat]]
   [bluzelle.views.settings :refer [SettingsScreen]]
   [bluzelle.views.home :refer [HomeScreen]]))

(defonce splash-img (js/require "../assets/shadow-cljs.png"))

(defn Root []
  (fn []
    [NavigationContainer
     [Navigator
      {:screenOptions 
       (fn [route]
         (clj->js 
           {:tabBarIcon 
            (fn [params]
              (as-element
                [Icon 
                 {:name (case (-> route .-route .-name)
                          "Network" "md-globe"
                          "Chat" "md-contacts"
                          "Settings" "md-contact"
                          "md-add")
                  :size (.-size params)
                  :color (.-color params)}]))}))

       :tabBarOptions #js {:activeTintColor "tomato" :inactiveTintColor "gray"}}
      [Screen {:name "Network" :component (fn [] (as-element [HomeScreen]))}]
      [Screen {:name "Chat" :component (fn [] (as-element [Chat]))}]
      [Screen {:name "Settings" :component (fn [] (as-element [SettingsScreen]))}]
      ]]))

(def api
  (blz/bluzelle
   (clj->js
    {:address  ""
     :mnemonic ""
     :endpoint "http://localhost:1317"
       ;:endpoint "http://testnet.public.bluzelle.com:1317"
       ;:endpoint "http://95.216.205.139:1317"
     :uid (uuid/v4)
     :chain_id "bluzelle"})))

(.then api #(do
              (js/console.log "Bluzelle initialized..")
              (.version api)))

(defn start
  {:dev/after-load true}
  []
  (expo/render-root (r/as-element [Root])))

(defn init []
  (rf/dispatch-sync [:initialize-db])
  (start))
