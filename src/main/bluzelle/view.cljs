(ns bluzelle.view
  (:require
   ["react-native-gesture-handler" :as gh]
   ["react-native" :as rn :refer [View Text TouchableOpacity]]
   ["react-native-gifted-chat" :as gc]
   ["@react-navigation/native" :as nav]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [bluzelle.events]
   [bluzelle.subs]))

(def styles
  ^js (-> {:container
           {:flex 1
            :alignItems "center"
            :justifyContent "center"}

           :title
           {:fontWeight "bold"
            :fontSize 24
            :color "white"

            :margin "auto"
            :alignItems "center"}

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

(defn root []
  (let [counter (rf/subscribe [:get-counter])
        messages (rf/subscribe [:get-messages])]
    (fn []
      [:> View {:style {:height "100%"}}

       [:> nav/NavigationContainer {:style {:flex 1 :justifyContent "center" :alignItems "center"}}
        [:> View {:style {:backgroundColor "blue" :flex 1 :alignItems "center" :maxHeight "50px"}}
         [:> Text {:style (.-title styles)} "bluzelle chat"]]
        [:> gc/GiftedChat
         {:messages (or @messages [])
          :onSend (fn [messages]
                    (let [{:keys [user] :as message} (first (js->clj messages :keywordize-keys true))
                          message (assoc message :user (user))]
                      (rf/dispatch [:add-message message])))
          :user (fn [] {:_id 1 :name "Test User"})
          :inverted (not= rn/Platform.OS "web")
          :scrollToBottom true}]]])))
