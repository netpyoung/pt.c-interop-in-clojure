(def project 'clj-hello)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"src" "../java-native"}
          :dependencies   '[[org.clojure/clojure "RELEASE"]])

(task-options!
  aot {:namespace #{'clj-hello.core}}
  pom {:project     project
       :version     version
       :description "FIXME: write description"
       :url         "http://example/FIXME"
       :scm         {:url "https://github.com/yourname/clj-hello"}
       :license     {"Eclipse Public License"
                     "http://www.eclipse.org/legal/epl-v10.html"}}
  repl {:init-ns 'clj-hello.core}
  jar {:main 'clj-hello.core
       :file (str "clj-hello-" version "-standalone.jar")})

(deftask build
  "Build the project locally as a JAR."
  [d dir PATH #{str} "the set of directories to write to (target)."]
  (let [dir (if (seq dir) dir #{"target"})]
    (comp (javac) (aot) (pom) (uber) (jar) (target :dir dir))))
