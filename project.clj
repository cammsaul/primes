;; -*- comment-column: 70; -*-

(defproject primes "primes-1.0"
  :description      "Prime Number Multiplacation Table Printer"
  :min-lein-version "2.5.0"
  :dependencies     [[org.clojure/clojure "1.7.0"]]
  :main             primes.core
  :profiles         {:dev {:dependencies [[org.clojure/tools.nrepl "0.2.10"]
                                          [expectations "2.1.2"]]
                           :plugins      [[cider/cider-nrepl "0.9.1"]
                                          [lein-expectations "0.0.8"]
                                          [refactor-nrepl "1.1.0"]]}})
