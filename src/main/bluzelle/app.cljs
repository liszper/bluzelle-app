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
      {:screenOptions (fn [route]
                        (clj->js {:tabBarIcon (fn [focused color size]
                                                (let [icon (r/atom "ios-settings-outline")]
                                                  (if (= "Home" (.-name route))
                                                    (if focused
                                                      (swap! icon assoc "ios-chatbubbles")
                                                      (swap! icon assoc "ios-chatbubbles-outline")))
                                                  (if (= "Settings" (.-name route))
                                                    (if focused
                                                      (swap! icon assoc "ios-settings")
                                                      (swap! icon assoc "ios-settings-outline")))
                                                  (as-element [Icon {:name "chatbubbles" :size 25 :color color}])))}))
       :tabBarOptions #js {:activeTintColor "tomato" :inactiveTintColor "gray"}}
      [Screen {:name "Home" :component (fn [] (as-element [HomeScreen]))}]
      [Screen {:name "Settings" :component (fn [] (as-element [SettingsScreen]))}]
      [Screen {:name "Chat" :component (fn [] (as-element [Chat]))}]]]))

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
