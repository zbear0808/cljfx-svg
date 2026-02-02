(require '[cljfx-svg.core :as svg])
(require '[cljfx.api :as fx])
(import '[javafx.application Platform]
        '[javafx.stage Stage]
        '[javafx.scene Scene]
        '[javafx.scene.layout HBox VBox]
        '[javafx.scene.control Label])

;; Initialize JavaFX
(try
  (Platform/startup (fn []))
  (catch IllegalStateException _
    (println "JavaFX already initialized")))

(println "\n=== Testing SVG Scaling Fix ===\n")

;; Test with different sizes
(doseq [size [16 24 32 48 64]]
  (println (str "Creating icon with size: " size))
  (try
    (let [icon-desc (svg/icon {:src "test-icons/gear.svg" :size size})
          node ((:create icon-desc))]
      (println "  Node class:" (class node))
      (let [bounds (.getBoundsInLocal node)]
        (println "  Original bounds - Width:" (.getWidth bounds) "Height:" (.getHeight bounds)))
      (println "  ScaleX:" (.getScaleX node) "ScaleY:" (.getScaleY node))
      (let [scaled-bounds (.getBoundsInParent node)]
        (println "  Scaled bounds - Width:" (.getWidth scaled-bounds) "Height:" (.getHeight scaled-bounds))))
    (println)
    (catch Exception e
      (println "  ERROR:" (.getMessage e))
      (println))))

(println "=== Test Complete ===")
(System/exit 0)
