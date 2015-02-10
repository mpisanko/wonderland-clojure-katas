(ns card-game-war.game)

;; feel free to use these cards or use your own data structure
(def suits [:spade :club :diamond :heart])
(def ranks [2 3 4 5 6 7 8 9 10 :jack :queen :king :ace])
(def cards
  (for [suit suits
        rank ranks]
    [suit rank]))

(def suit-values (zipmap suits (iterate inc 0)))
(def rank-values (zipmap ranks (iterate inc 0)))

(defn compare-suits
  [card-1 card-2]
  (let [sub (- (suit-values (first card-1)) (suit-values (first card-2)))]
    (cond 
      (pos? sub) [[card-1 card-2] []]
      (neg? sub) [[] [card-1 card-2]]
      :else [[card-1] [card-2]])))

(defn compare-ranks
  [card-1 card-2]
  (let [sub (- (rank-values (second card-1)) (rank-values (second card-2)))]
    (cond 
      (zero? sub) (compare-suits card-1 card-2)
      (pos? sub) [[card-1 card-2] []]
      :else [[] [card-1 card-2]])))

(defn- deal
  []
  (reduce 
    (fn 
      [[deck-1 deck-2] [card-1 card-2]]
      (vector (conj deck-1 card-1) (conj deck-2 card-2)))
    (vector (clojure.lang.PersistentQueue/EMPTY) (clojure.lang.PersistentQueue/EMPTY))
    (partition 2 (shuffle cards))))

(defn play-round [player1-card player2-card])

(defn play-game [player1-cards player2-cards])
