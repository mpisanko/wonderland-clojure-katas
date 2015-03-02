(ns doublets.solver
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(def words (-> "words.edn"
               (io/resource)
               (slurp)
               (read-string)))

(defn equal-length-words [words word]
  (let [word-len (count word)]
    (filter #(= (count %) word-len) words)))

(defn word-distance [word1 word2]
  (let [ws (map vector word1 word2)]
    (reduce
     #(let [[c1 c2] %2]
        (if (= c1 c2)
          %1
          (inc %1)))
     0 ws)))

(defn find-candidates [words word exclude]
  (filter #(and (not-any? #{%} exclude) (>= 1 (word-distance word %))) words))

(defn exact? [words word]
  (some #{word} words))

(defn next-words [work word target current]
  (let [candidates (find-candidates work word current)]
    (if (exact? candidates target)
      (conj current target)
      (map #(next-words work % target (conj current %)) candidates))))

(defn doublets [word1 word2]
  (let [work (equal-length-words words word1)
        results (next-words work word1 word2 [word1])]
    (into [] (flatten results))))
