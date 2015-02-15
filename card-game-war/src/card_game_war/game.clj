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


(defn compare-cards
  [[suit-1 rank-1 :as card-1] [suit-2 rank-2 :as card-2]]
  (let [rank-comp (compare (rank-values rank-1) (rank-values rank-2))
        suit-comp (compare (suit-values suit-1) (suit-values suit-2))]
    (if (zero? rank-comp) 
      suit-comp
      rank-comp)))

(defn compare-and-distribute
  [card-1 card-2]
  (if (pos? (compare-cards card-1 card-2))
    [[card-1 card-2] []]
    [[] [card-1 card-2]]))

(defn deal
  [cards]
  (reduce 
    (fn 
      [[deck-1 deck-2] [card-1 card-2]]
      (vector (conj deck-1 card-1) (conj deck-2 card-2)))
    (vector (clojure.lang.PersistentQueue/EMPTY) (clojure.lang.PersistentQueue/EMPTY))
    (partition 2 (shuffle cards))))

(defn- append-unless-empty
  [coll elems]
  (if (seq elems)
    (apply conj coll (seq elems))
    coll))

(defn play-round [player1-cards player2-cards]
  (let [player1-card (peek player1-cards)
        player2-card (peek player2-cards)
        [player1-round player2-round] (compare-and-distribute player1-card player2-card)]
    [(append-unless-empty (pop player1-cards) player1-round) 
     (append-unless-empty (pop player2-cards) player2-round)]))

(defn play-game [player1-cards player2-cards])
