(ns build
  (:require [clojure.tools.build.api :as b]
            [deps-deploy.deps-deploy :as dd]))

(def lib 'org.clojars.zubad/cljfx-svg)
(def version (format "0.1.%s" (b/git-count-revs nil)))
(def class-dir "target/classes")
(def basis (b/create-basis {:project "deps.edn"}))
(def jar-file (format "target/%s-%s.jar" (name lib) version))

(def pom-data
  [[:description "Clojure library for rendering SVG icons in cljfx/JavaFX applications"]
   [:url "https://github.com/zbear0808/cljfx-svg"]
   [:licenses
    [:license
     [:name "MIT"]
     [:url "https://opensource.org/licenses/MIT"]]]])

(defn clean [_]
  (b/delete {:path "target"}))

(defn jar [_]
  (clean nil)
  (b/write-pom {:class-dir class-dir
                :lib lib
                :version version
                :basis basis
                :src-dirs ["src"]
                :resource-dirs ["resources"]
                :pom-data pom-data
                :scm {:url "https://github.com/zbear0808/cljfx-svg"
                      :connection "scm:git:git://github.com/zbear0808/cljfx-svg.git"
                      :developerConnection "scm:git:ssh://git@github.com/zbear0808/cljfx-svg.git"
                      :tag (str "v" version)}})
  (b/copy-dir {:src-dirs ["src" "resources"]
               :target-dir class-dir})
  (b/jar {:class-dir class-dir
          :jar-file jar-file})
  (println "Built JAR:" jar-file))

(defn deploy [_]
  (jar nil)
  (dd/deploy {:installer :remote
              :artifact jar-file
              :pom-file (b/pom-path {:lib lib :class-dir class-dir})}))
