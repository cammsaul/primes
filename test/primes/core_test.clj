(ns primes.core-test
  (:require [expectations :refer :all]
            [primes.core :refer :all]))

;;; TESTS FOR N-PRIMES

(expect []
  (n-primes 0))

(expect [2]
  (n-primes 1))

(expect [2]
  (n-primes 1))

(expect [2 3]
  (n-primes 2))

(expect [2 3 5]
  (n-primes 3))

(expect [2 3 5 7 11 13 17 19 23 29]
  (n-primes 10))

;; Check that n-primes calculates results lazily (i.e. doesn't stack overflow)
;; This takes like 21 seconds on my box but ... I guess it's proof it works <3
(expect [104743]
  (drop 10000 (n-primes 10001)))


;;; TESTS FOR NUMS->MULTIPLICATION-TABLE

(expect [[1 2 3]
         [1 1 2 3]
         [2 2 4 6]
         [3 3 6 9]]
  (nums->multipliction-table (range 1 4)))

(expect [[2 3 5 7 11 13 17 19 23 29]
         [2 4 6 10 14 22 26 34 38 46 58]
         [3 6 9 15 21 33 39 51 57 69 87]
         [5 10 15 25 35 55 65 85 95 115 145]
         [7 14 21 35 49 77 91 119 133 161 203]
         [11 22 33 55 77 121 143 187 209 253 319]
         [13 26 39 65 91 143 169 221 247 299 377]
         [17 34 51 85 119 187 221 289 323 391 493]
         [19 38 57 95 133 209 247 323 361 437 551]
         [23 46 69 115 161 253 299 391 437 529 667]
         [29 58 87 145 203 319 377 493 551 667 841]]
  (nums->multipliction-table [2 3 5 7 11 13 17 19 23 29]))


;;; TESTS FOR TABLE->STR & RELATED FNS

(expect (str "   | %2d %2d %2d \n"
             "---+----------\n"
             "%2d | %2d %2d %2d \n"
             "%2d | %2d %2d %2d \n"
             "%2d | %2d %2d %2d ")
  (format-table 2 3))

(expect (str "    | %3d %3d %3d %3d \n"
             "----+-----------------\n"
             "%3d | %3d %3d %3d %3d \n"
             "%3d | %3d %3d %3d %3d \n"
             "%3d | %3d %3d %3d %3d \n"
             "%3d | %3d %3d %3d %3d ")
  (format-table 3 4))

(expect (str "  | 1 2 3 \n"
             "--+-------\n"
             "1 | 1 2 3 \n"
             "2 | 2 4 6 \n"
             "3 | 3 6 9 ")
  (table->str [[1 2 3]
               [1 1 2 3]
               [2 2 4 6]
               [3 3 6 9]]))

(expect (str "     |   10  100 1000 \n"
             "-----+----------------\n"
             "   1 |   10  100 1000 \n"
             "   1 |   10  100 1000 \n"
             "   1 |   10  100 1000 ")
    (table->str [[10 100 1000]
                 [1 10 100 1000]
                 [1 10 100 1000]
                 [1 10 100 1000]]))


;;; TESTS FOR PRIMES-TABLE

(expect (str "  | 2 3 \n"
             "--+-----\n"
             "2 | 4 6 \n"
             "3 | 6 9 ")
  (primes-table 2))

(expect (str "    |   2   3   5   7  11  13  17  19  23  29 \n"
             "----+-----------------------------------------\n"
             "  2 |   4   6  10  14  22  26  34  38  46  58 \n"
             "  3 |   6   9  15  21  33  39  51  57  69  87 \n"
             "  5 |  10  15  25  35  55  65  85  95 115 145 \n"
             "  7 |  14  21  35  49  77  91 119 133 161 203 \n"
             " 11 |  22  33  55  77 121 143 187 209 253 319 \n"
             " 13 |  26  39  65  91 143 169 221 247 299 377 \n"
             " 17 |  34  51  85 119 187 221 289 323 391 493 \n"
             " 19 |  38  57  95 133 209 247 323 361 437 551 \n"
             " 23 |  46  69 115 161 253 299 391 437 529 667 \n"
             " 29 |  58  87 145 203 319 377 493 551 667 841 ")
  (primes-table 10))
