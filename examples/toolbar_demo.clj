(ns toolbar-demo
  (:require [cljfx.api :as fx]
            [cljfx-svg.core :as svg]))

(def *state (atom {:theme :light
                   :title "SVG Icon Demo"}))

(defn event-handler [event]
  (prn :event event)
  (case (:event/type event)
    :save (do (println "Save clicked") {})
    :open (do (println "Open clicked") {})
    {}))

(defn toolbar-button [{:keys [icon-src label on-action]}]
  {:fx/type :button
   :graphic {:fx/type svg/icon
             :src icon-src
             :size 16
             :color :white}
   :text label
   :on-action on-action})

(defn toolbar [{:keys [fx/context]}]
  (let [theme (fx/sub-val context :theme)]
    {:fx/type :h-box
     :spacing 8
     :padding 8
     :style {:-fx-background-color (if (= theme :dark) "#333" "#eee")}
     :children [{:fx/type toolbar-button
                 :icon-src "icons/save.svg"
                 :label "Save"
                 :on-action {:event/type :save}}
                {:fx/type toolbar-button
                 :icon-src "icons/folder-open.svg"
                 :label "Open"
                 :on-action {:event/type :open}}]}))

(defn root-view [{:keys [fx/context]}]
  (let [title (fx/sub-val context :title)]
    {:fx/type :stage
     :showing true
     :title title
     :scene {:fx/type :scene
             :root {:fx/type :v-box
                    :children [{:fx/type toolbar}]}}}))

(def app
  (fx/create-app
   *state
   :event-handler event-handler
   :desc-fn (fn [_] {:fx/type root-view})))
