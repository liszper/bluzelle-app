(ns bluzelle.events
  (:require
   [re-frame.core :refer [reg-event-db after]]
   [clojure.spec.alpha :as s]
   [bluzelle.db :as db :refer [app-db]]))

;; -- Handlers --------------------------------------------------------------

(reg-event-db
 :initialize-db
 (fn [_ _]
   app-db))

(reg-event-db
 :add-message
 (fn [db [_ message]]
   (update db :messages conj message)))
