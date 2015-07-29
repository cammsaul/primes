(ns primes.core)

;; This can be broken down into the following tasks:
;;
;; *  Calculate N primes
;; *  Create multiplication table
;; *  Convert multiplication table to str
;; *  Get N from the command line & put all the steps together, print result

;;; # Calculate N Primes

(defn- next-prime [primes]
  (loop [[n & more-ns] (range (last primes) Integer/MAX_VALUE)]
    (or (loop [[prime & more-primes] primes]
          (cond
            (not prime)         n
            (= 0 (mod n prime)) false
            :else               (recur more-primes)))
        (recur more-ns))))

(defn n-primes [n]
  {:pre [(>= n 0)]}
  (if (zero? n) []
      (loop [i n, acc [2]]
        (cond
          (= i 1) acc
          :else   (recur (dec i) (conj acc (next-prime acc)))))))


;;; # Create Multiplacation Table

(defn nums->multipliction-table [nums]
  {:pre [(sequential? nums)]}
  (conj (for [i nums]
          (conj (for [j nums]
                  (* i j)) i))
        nums))


;;; # Table->str

(defn- format-num [col-width]
  (str "%" col-width "d "))

(defn- format-header [col-width n]
  (apply str
         (apply str (repeat col-width " "))
         " | "
         (repeat n (format-num col-width))))

(defn- format-divider [col-width n]
  (apply str
         (apply str (repeat col-width "-"))
         "-+-"
         (repeat (* (inc col-width) n) "-")))

(defn- format-row [col-width n]
  (apply str
         (format-num col-width)
         "| "
         (repeat n (format-num col-width))))

(defn format-table [col-width n]
  (apply str
         (format-header col-width n) "\n"
         (format-divider col-width n) "\n"
         (->> (repeat n (format-row col-width n))
              (interpose "\n"))))

(defn table->str
  ([[first-row :as table]]
   {:pre [(every? sequential? table)]}
   (let [max-val   (last (last table))
         col-width (count (str max-val))
         n         (count first-row)]
     (table->str table col-width n)))
  ([table col-width n]
   (let [nums (flatten table)]
     (apply format (format-table col-width n) nums))))


;;; #  Get N from the command line & put all the steps together

(defn primes-table [n]
  (->> (n-primes n)
       nums->multipliction-table
       table->str))

(defn -main [n]
  (println (or (when-let [n (try (Integer/parseInt n)
                                 (catch Throwable _))]
                 (when (>= n 0)
                   (primes-table n)))
               "Usage: lein run 10")))
