(ns bluzelle.app
  (:require
   ["react-native-gesture-handler" :as gh]
   ["expo" :as ex]
   ["react" :as react]
   ["react-native" :as rn]
   ["react-native-gifted-chat" :as gc]
   ["@react-navigation/native" :as nav]
   ["bluzelle" :as blz]
   ["uuid" :as uuid]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [shadow.expo :as expo]
   [bluzelle.events]
   [bluzelle.subs]))

;; must use defonce and must refresh full app so metro can fill these in
;; at live-reload time `require` does not exist and will cause errors
;; must use path relative to :output-dir

(defonce splash-img (js/require "../assets/shadow-cljs.png"))

(def styles
  ^js (-> {:container
           {:flex 1
            :backgroundColor "#fff"
            :alignItems "center"
            :justifyContent "center"}
           :title
           {:fontWeight "bold"
            :fontSize 24
            :color "blue"}
           :button
           {:fontWeight "bold"
            :fontSize 18
            :padding 6
            :backgroundColor "blue"
            :borderRadius 10}
           :buttonText
           {:paddingLeft 12
            :paddingRight 12
            :fontWeight "bold"
            :fontSize 18
            :color "white"}
           :label
           {:fontWeight "normal"
            :fontSize 15
            :color "blue"}}
          (clj->js)
          (rn/StyleSheet.create)))

(defn list-element [item]
  (let []
    (fn []
      [:> rn/Text
       (:title item)]
      )))

(def api
  (blz/bluzelle
    (clj->js
      {
       :address  ""
       :mnemonic ""
       :endpoint "http://localhost:1317"
       ;:endpoint "http://testnet.public.bluzelle.com:1317"
       ;:endpoint "http://95.216.205.139:1317"
       :uid (uuid/v4)
       :chain_id "bluzelle"
       }
      )))

(.then 
  api  
  #(do
     (js/console.log "Bluzelle initialized..")
     (.version api)
     ))

(defn root []
  (let [
        counter (rf/subscribe [:get-counter])
        messages (rf/subscribe [:get-messages])
        ]
    (fn []
      [:> rn/View {:style {:height "100%"}}
     
      [:> nav/NavigationContainer
       {:style {:flex 1 :justifyContent "center" :alignItems "center"}}
       ;[:> rn/Text {:style (.-title styles)} "Clicked: " @counter]
       [:> rn/Text {:style (.-title styles)} "bluzelle chat"]
       
       ] 
       [:> rn/View {:style {:padding 20 :height "calc(100% - 150px)"}}

       [:> gc/GiftedChat
        {:messages (or @messages [])
         :onSend (fn [messages]
                   (let [{:keys [user] :as message} (first (js->clj messages :keywordize-keys true))
                         message (assoc message :user (user))
                         ]
                     (rf/dispatch [:add-message message])
                     )
                   )
         :user (fn [] {:_id 1 :name "Test User"})
         :inverted (not= rn/Platform.OS "web")
         :scrollToBottom true
         }]]
      
       [:> rn/View ;{:style {:height 150 :flex 1 :flexDirection "row"
                   ;         :padding-left 20 :padding-right 20 :padding-bottom 20}}
      [:> rn/View {:style (.-container styles)}
      
       ;[:> rn/SafeAreaView
       ;[:> rn/FlatList
       ; {
       ;  :data messages 
       ;  :renderItem (fn [item]
       ;                (r/as-element
       ;                  [list-element (:item (js->clj item :keywordize-keys true))]))
       ;  :keyExtractor (fn [item] (:id (:item (js->clj item :keywordize-keys true))))
;
;         }]]

       ;[:> rn/Image {:source splash-img :style {:width 200 :height 200}}]
       ;[:> rn/Text {:style (.-label styles)} "Using: shadow-cljs+expo+reagent+re-frame"]
       ]
       [:> rn/TouchableOpacity {:style (.-button styles)
                                :on-press 
                                #(do
                                   (js/console.log api)
                                   (rf/dispatch [:inc-counter]))}
        [:> rn/Text {:style (.-buttonText styles)} "Increment counter in Navigation"]]
        ]
       ]
      )))

(defn start
  {:dev/after-load true}
  []
  (expo/render-root (r/as-element [root])))

(defn init []
  (rf/dispatch-sync [:initialize-db])
  (start))

