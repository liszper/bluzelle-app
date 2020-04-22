(ns bluzelle.modules
  (:require
   [reagent.core :as r :refer [adapt-react-class]]
   ["react-native" :as react-native]
   ["react-native-gifted-chat" :as gifted-chat]
   ["react-native-gesture-handler" :as gesture-handler]
   ["@react-navigation/native" :as react-nav-native]
   ["@react-navigation/bottom-tabs" :as rntabs]
   ["react-native-vector-icons/Ionicons" :as ion-icons]))

(def platform (. (.-Platform react-native) -OS))

(def Text (adapt-react-class (.-Text react-native)))
(def View (adapt-react-class (.-View react-native)))
(def Button (adapt-react-class (.-Button react-native)))
(def Image (adapt-react-class (.-Image react-native)))
(def touchable-highlight (adapt-react-class (.-TouchableHighlight react-native)))
(def input (adapt-react-class (.-TextInput react-native)))

(def Icon (adapt-react-class (.-default ion-icons)))

(def NavigationContainer (adapt-react-class (.-NavigationContainer react-nav-native)))

(def Tab (rntabs/createBottomTabNavigator))
(def Navigator (adapt-react-class (.-Navigator Tab)))
(def Screen (adapt-react-class (.-Screen Tab)))

(def GiftedChat (adapt-react-class (.-GiftedChat gifted-chat)))
