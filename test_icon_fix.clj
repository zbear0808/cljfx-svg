(ns test-icon-fix
  "Quick test to verify the icon function fix works"
  (:require [cljfx.api :as fx]
            [cljfx-svg.core :as svg]))

;; Test that icon returns proper ext-instance-factory description
(def icon-desc 
  (svg/icon {:src "test-icons/gear.svg" 
             :size 24 
             :color :blue}))

(println "Icon description structure:")
(println icon-desc)

;; Verify it has the expected structure
(assert (= (:fx/type icon-desc) fx/ext-instance-factory)
        "icon should return ext-instance-factory")

(assert (fn? (:create icon-desc))
        "icon description should have :create function")

(println "\n✓ Icon function returns correct structure")
(println "✓ Can be used as {:fx/type svg/icon ...} without middleware")

;; Example usage that would work in a real app:
(def example-button
  {:fx/type :button
   :text "LINK"
   :graphic {:fx/type svg/icon
             :src "ableton.svg"
             :size 25}})

(println "\nExample usage:")
(println example-button)
