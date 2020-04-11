(ns bluzelle.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
 :get-counter
 (fn [db _]
   (:counter db)))

(reg-sub
 :get-messages
 (fn [db _]
   (:messages db)))
