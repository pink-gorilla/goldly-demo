(defproject demo/goldly-custom-bundel "0.0.1-SNAPSHOT"
  :description "goldly custom bundel"
  :license {:name "MIT"}
  :min-lein-version "2.9.3"
  :min-java-version "1.11"

  :source-paths ["src"]
  :resource-paths ["target/webly"] ; js bundle
  :target-path  "target/jar"
  :main  goldly-server.app 

  :dependencies [[org.pinkgorilla/goldly "0.2.82"]
                 ; bundel dependencies
                 [org.pinkgorilla/gorilla-ui "0.3.31" :exclusions [org.clojure/clojurescript]]
                 [org.pinkgorilla/gorilla-plot "1.2.8" :exclusions [org.clojure/clojurescript]]
                 [org.pinkgorilla/ui-binaryclock "0.0.4"]
                 [org.pinkgorilla/ui-quil "0.1.5"]]

  :aliases {"npm-install"
            ["run" "-m" "goldly-server.app" "goldly-bundel.edn" "npm-install"]

            "build-release"
            ["run" "-m" "goldly-server.app" "goldly-bundel.edn" "release"]

            "demo"
            ["run" "-m" "goldly-server.app" "goldly-bundel.edn" "watch"]})

