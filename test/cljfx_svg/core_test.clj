(ns cljfx-svg.core-test
  (:require [clojure.test :refer [deftest testing is use-fixtures]]
            [cljfx.api :as fx]
            [cljfx.lifecycle :as lifecycle]
            [cljfx-svg.core :as svg])
  (:import [javafx.scene Group]
           [javafx.application Platform]
           [org.girod.javafx.svgimage SVGImage]))

;; Initialize JavaFX toolkit for tests
(defn init-javafx [f]
  ;; Start JavaFX platform if not already started
  (try
    (Platform/startup (fn []))
    (catch IllegalStateException _
      ;; Already started, that's fine
      nil))
  (f))

(use-fixtures :once init-javafx)
