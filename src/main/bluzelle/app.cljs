(ns bluzelle.app
  (:require
   ["expo" :as ex]
   ["react" :as react]
   ["bluzelle" :as blz]
   ["uuid" :as uuid]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [shadow.expo :as expo]
   [bluzelle.events]
   [bluzelle.subs]
   [bluzelle.view :refer [root]]))

(defonce splash-img (js/require "../assets/shadow-cljs.png"))

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
  (expo/render-root (r/as-element [root])))

(defn init []
  (rf/dispatch-sync [:initialize-db])
  (start))
