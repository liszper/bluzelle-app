(ns bluzelle.views.chat
  (:require
   [re-frame.core :as rf]
   [bluzelle.events]
   [bluzelle.subs]
   [bluzelle.styles :refer [styles]]
   [bluzelle.modules :refer [Text View Image platform NavigationContainer GiftedChat]]))

(defn Chat []
  (let [messages (rf/subscribe [:get-messages])]
    (fn []
      [View {:style {:height "100%"}}
       [View {:style [(:cflex styles) {:backgroundColor "blue" :maxHeight "50px"}]}
        [Text {:style (:title styles)} "bluzelle chat"]]
       [GiftedChat
        {:messages (or @messages [])
         :onSend (fn [messages]
                   (let [{:keys [user] :as message} (first (js->clj messages :keywordize-keys true))
                         message (assoc message :user (user))]
                     (rf/dispatch [:add-message message])))
         :user (fn [] {:_id 1 :name "Test User"})
         :inverted (not= "web" platform)
         :scrollToBottom true}]])))
