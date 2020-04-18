(ns bluzelle.db
  (:require [clojure.spec.alpha :as s]))

;; spec of app-db
(s/def ::counter number?)

(s/def ::messages vector?)

(s/def ::app-db
  (s/keys
   :req-un
   [::counter
    ::messages]))

;; initial state of app-db
(defonce app-db
  {:counter 0
   :messages
   [{:_id 0
     :text "Hello Developer!"
     :createdAt (js/Date)
     :user {:_id 2 :name "React Native"}}
    {:_id 1
     :text "This is our chat app.."
     :createdAt (js/Date)
     :user {:_id 2 :name "React Native"}}]})
