;; -*- comment-column: 80; -*-

(defproject primes "primes-1.0"
  :description      "Prime Number Multiplacation Table Printer"
  :min-lein-version "2.5.0"
  :dependencies     [[org.clojure/clojure "1.7.0"]]                             ; marginalia chokes on clojure 1.7 :/
  :main             primes.core
  :profiles         {:dev {:dependencies [[org.clojure/tools.nrepl "0.2.10"]    ; needed by cider-nrepl
                                          [expectations "2.1.2"]                ; unit tests
                                          [marginalia "0.8.0"]]                 ; documentation
                           :plugins      [[cider/cider-nrepl "0.9.1"]           ; for Emacs development, included here for sake of convenience
                                          [lein-expectations "0.0.8"]           ; unit tests
                                          [lein-marginalia "0.8.0"]             ; documentation
                                          [refactor-nrepl "1.1.0"]]}})          ; extensions to cider-nrepl.
