(ns bluzelle.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
 :get-messages
 (fn [db _]
   (:messages db)))
