(ns cljfx-svg.core
  "SVG icon component for cljfx applications.
   
   Provides a function component for rendering SVG files as JavaFX nodes
   using the fxsvgimage library.
   
   Usage:
   
      (require '[cljfx.api :as fx])
      (require '[cljfx-svg.core :as svg])
      
      ;; Use as a function component (no registration needed)
      {:fx/type svg/icon
       :src \"icons/gear.svg\"
       :size 24
       :color :blue}
   
   Props:
     :src         - Path to SVG file (classpath resource or file path) [required]
     :size        - Target width in pixels (maintains aspect ratio)
     :color       - Fill color override (keyword like :red, string like \"#ff0000\", or Paint)
     
   Note: The icon is implemented using fx/ext-instance-factory, which means
   the node is recreated when props change. For most use cases this is fine."
  (:require [clojure.java.io :as io]
            [cljfx.api :as fx])
  (:import [org.girod.javafx.svgimage SVGLoader SVGImage]
           [javafx.scene Group Node]
           [javafx.scene.shape Shape]
           [javafx.scene.paint Color Paint]))

(defn- resolve-source
  "Resolve SVG source to a URL for SVGLoader.
   Supports classpath resources and file paths."
  [src]
  (or (io/resource src)
      (let [f (io/file src)]
        (when (.exists f)
          (.toURL f)))))

(defn- load-svg-image
  "Load an SVG file and return a JavaFX SVGImage."
  [src]
  (let [source (resolve-source src)]
    (when-not source
      (throw (ex-info "SVG source not found" {:src src})))
    (SVGLoader/load source)))

(defn- create-svg-region
  "Create an SVG node from source path.
   Returns an SVGImage instance (which extends Group)."
  [src]
  (let [^SVGImage svg-image (load-svg-image src)]
    ;; SVGImage extends Group, so it can be used directly as a Node
    ;; Store reference for scaling/color operations
    (.setUserData svg-image {:svg-image svg-image})
    svg-image))

(defn- set-svg-size!
  "Set the size of an SVG by scaling the SVGImage."
  [^SVGImage svg-image size]
  (let [bounds (.getBoundsInLocal svg-image)
        original-width (abs (.getWidth bounds))
        scale-factor (/ (double size) original-width)]
    (doto svg-image
      (.setScaleX scale-factor)
      (.setScaleY scale-factor))))

(defn- ->color
  "Convert various color representations to JavaFX Color.
   Accepts: keyword (:red), string (\"#ff0000\", \"red\"), or Color instance."
  [c]
  (cond
    (nil? c) nil
    (instance? Paint c) c
    (keyword? c) (Color/valueOf (name c))
    (string? c) (Color/valueOf c)
    :else (throw (ex-info "Invalid color" {:color c}))))

(defn- apply-color-to-node!
  "Recursively apply a fill color to all Shape children in a Node."
  [^Node node color]
  (let [paint (->color color)]
    (cond
      (instance? Shape node)
      (.setFill ^Shape node paint)

      (instance? Group node)
      (doseq [child (.getChildren ^Group node)]
        (apply-color-to-node! child color)))
    node))


(defn icon
  "Function component for SVG icons. Use directly as :fx/type without registration.
   
   Props:
     :src    - Path to SVG file (classpath resource or file path) [required]
     :size   - Target width in pixels (maintains aspect ratio)
     :color  - Fill color override (keyword, string, or Paint)
   
   Usage:
     {:fx/type svg/icon
      :src \"icons/gear.svg\"
      :size 24
      :color :blue}
   
   Note: Uses fx/ext-instance-factory, so the node is recreated when any prop changes.
   This is efficient for typical icon use cases."
  [{:keys [src size color] :as props}]
  (when-not src
    (throw (ex-info "svg/icon requires :src prop" {:props props})))
  {:fx/type fx/ext-instance-factory
   :create (fn []
             (cond-> (create-svg-region src)
               size (set-svg-size! size)
               color (apply-color-to-node! color)))})
