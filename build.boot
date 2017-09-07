(def project 'lumo-cljfmt)
(def version "1.1")

(set-env! :source-paths #{"src"}
          :dependencies '[[adzerk/boot-cljs-repl "0.3.3"]   ;; latest release
                          [adzerk/boot-cljs "2.1.3" :scope "test"]
                          [com.cemerick/piggieback "0.2.1" :scope "test"]
                          [weasel "0.7.0" :scope "test"]
                          [org.clojure/tools.nrepl "0.2.12" :scope "test"]
                          [pandeiro/boot-http "0.8.3" :scope "test"]

                          [org.clojure/clojure "1.8.0"]
                          [org.clojure/clojurescript "1.9.908"]
                          [org.clojure/tools.reader "1.0.1"]
                          [cljsjs/nodejs-externs "1.0.4-1"]
                          [cljfmt "0.5.7"]
                          [org.clojure/tools.cli "0.3.5"]])

(require
  '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
  '[adzerk.boot-cljs :refer [cljs]]
  '[pandeiro.boot-http :refer [serve]])

(task-options!
  pom {:project     project
       :version     version
       :description "FIXME: write description"
       :url         "http://example/FIXME"
       :scm         {:url "https://github.com/yourname/boot-tasks"}
       :license     {"Eclipse Public License"
                     "http://www.eclipse.org/legal/epl-v10.html"}}
  cljs {:compiler-options {:target :nodejs}})


(deftask dev
         []
         (comp
           (watch)
           (cljs-repl)
           (cljs)))

(defmacro nodejs-repl
  []
  `(do (require '[cljs.repl.node])
       (cemerick.piggieback/cljs-repl (cljs.repl.node/repl-env))))

(deftask prod-build
         []
         (comp
           (cljs :optimizations :advanced)
           (sift :include #{#"\.out"} :invert true)
           (sift :move {#"main\.js" "cljfmt"})))