(ns primes.core)

;; This can be broken down into the following tasks:
;;
;; *  Calculate N primes
;; *  Create multiplication table
;; *  Convert multiplication table to str
;; *  Get N from the command line & put all the steps together, print result

;;; # Calculate N Primes

(defn- next-prime
  "Given a vector of sequential primes numbers, return the next prime.

    (next-prime [2])   -> 3
    (next-prime [2 3]) -> 5"
  [primes]
  {:pre [(vector? primes)]}
  ;; Loop over every integer greater than the previous largest prime.
  ;; Then check whether it is evenly divisible by any of the previous primes; if not, it's prime;
  ;; otherwise recur and try again with the next integer.
  (loop [[n & more-ns] (range (inc (last primes)) Integer/MAX_VALUE)]
    (or (loop [[prime & more-primes] primes]
          (cond
            (not prime)         n
            (= 0 (mod n prime)) false
            :else               (recur more-primes)))
        (recur more-ns))))

(defn n-primes
  "Return a vector of the first `n` prime numbers.

     (n-primes 4) -> [2 3 5 7]"
  [n]
  {:pre  [(>= n 0)]
   :post [(= (count %) n)]}
  (if (zero? n) []
      ;; Each subsequent number is calculated as a result of the last, i.e. (n-primes (dec n)).
      ;; In order to let this function handle truly massive values of n we need to call n-primes recursively
      ;; in a way that doesn't push a new stack frame. So keep previous values in an accumulator and recurse
      ;; until we reach the desired number of primes.
      (loop [i n, acc [2]]
        (cond
          (= i 1) acc
          :else   (recur (dec i) (conj acc (next-prime acc)))))))


;;; # Create Multiplacation Table

(defn nums->multipliction-table
  "Calculate a multiplication table for a given sequence of numbers.
   The first row will have one less value than the rest; this is to represent the 'empty' left corner of the table.

     (nums->multipliction-table (range 1 3)) ->
       (  (1 2)
        (1 1 2)
        (2 2 4))"
  [nums]
  {:pre [(sequential? nums)]}
  (conj (for [i nums]
          (conj (for [j nums]
                  (* i j)) i))
        nums))


;;; # table->str

;; These functions convert a table to a string by generating a the appropriate format string
;; and calling format with the flattened table as arguments

(defn- format-num
  "Create an appropriate format string for an integer in a table of `col-width`.

    (format-num 4) -> \"%4d \""
  [col-width]
  (str "%" col-width "d "))

(defn- format-header
  "Create the format string for the header of the table.

    (format-header 3 5) -> \"    | %3d %3d %3d %3d %3d \""
  [col-width n]
  (apply str
         (apply str (repeat col-width " "))
         " | "
         (repeat n (format-num col-width))))

(defn- format-divider
  "Create the divider for the table.

     (format-divider 3 5) -> \"----+--------------------\""
  [col-width n]
  (apply str
         (apply str (repeat col-width "-"))
         "-+-"
         (repeat (dec (* (inc col-width) n)) "-")))

(defn- format-row
  "Create a format string for a row of the table.

    (format-row 3 5) ->  \"%3d | %3d %3d %3d %3d %3d \""
  [col-width n]
  (apply str
         (format-num col-width)
         "| "
         (repeat n (format-num col-width))))

(defn format-table
  "Create the combined format string for a table.

    (format-table 3 2) ->
      \"    | %3d %3d
        ----+--------
        %3d | %3d %3d
        %3d | %3d %3d"
  [col-width n]
  (apply str
         (format-header col-width n) "\n"
         (format-divider col-width n) "\n"
         (->> (repeat n (format-row col-width n))
              (interpose "\n"))))

(defn table->str
  "Convert a table to a string by applying `format` to the string
   generated by `format-table`."
  ([[first-row :as table]]
   {:pre [(every? sequential? table)]}
   (let [max-val   (last (last table))
         col-width (count (str max-val))
         n         (count first-row)]
     (table->str table col-width n)))

  ([table col-width n]
   (apply format (format-table col-width n) (flatten table))))


;;; #  Get N from the command line & put all the steps together

(defn primes-table
  "Calculate a multiplication table of the first `n` prime numbers and
   convert it to a string."
  [n]
  (->> (n-primes n)
       nums->multipliction-table
       table->str))

(defn -main [n]
  ;; print the string result of primes-table if input is valid
  ;; otherwise print usage message
  (println (or (when-let [n (try (Integer/parseInt n)
                                 (catch Throwable _))]
                 (when (>= n 0)
                   (primes-table n)))
               "Usage: lein run 10")))
