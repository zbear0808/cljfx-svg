(require '[cljfx-svg.core :as svg])
(require '[cljfx.api :as fx])
(import '[javafx.application Platform]
        '[org.girod.javafx.svgimage SVGImage])

;; Initialize JavaFX
(try
  (Platform/startup (fn []))
  (catch IllegalStateException _
    (println "JavaFX already initialized")))

;; Test with different sizes
(println "\n=== Testing SVG Scaling ===\n")

(doseq [size [16 24 32 64 128]]
  (println (str "Testing size: " size))
  (let [icon-desc (svg/icon {:src "test-icons/gear.svg" :size size})
        node ((:create icon-desc))]
    (println "  Node class:" (class node))
    (println "  Node type:" (type node))
    (let [bounds (.getBoundsInLocal node)]
      (println "  Width:" (.getWidth bounds))
      (println "  Height:" (.getHeight bounds))
      (println "  ScaleX:" (.getScaleX node))
      (println "  ScaleY:" (.getScaleY node)))
    (println)))

;; Test the scaleTo method directly
(println "\n=== Testing SVGImage.scaleTo directly ===\n")
(let [^SVGImage img (svg/icon {:src "test-icons/gear.svg"})
      node ((:create img))]
  (println "Initial bounds:" (.getBoundsInLocal node))
  (.scaleTo node 64.0 false)
  (println "After scaleTo(64, false):" (.getBoundsInLocal node))
  (println "ScaleX:" (.getScaleX node) "ScaleY:" (.getScaleY node)))
