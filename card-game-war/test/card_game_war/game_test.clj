(ns card-game-war.game-test
  (:require [clojure.test :refer :all]
            [card-game-war.game :refer :all]))

(defmethod print-method clojure.lang.PersistentQueue
[q, w]
(print-method '<- w)
(print-method (seq q) w)
(print-method '-< w))

;; fill in  tests for your game
(deftest test-play-round
  (testing "the highest rank wins the cards in the round"
    (testing "queens are higer rank than jacks"
      (is (= [[["_" :queen] ["_" :jack]][]]) (compare-ranks ["_" :queen] ["_" :jack])))
    (testing "kings are higer rank than queens"
      (is (= [[["_" :king] ["_" :queen]][]]) (compare-ranks ["_" :king] ["_" :queen])))
    (testing "aces are higer rank than kings"
      (is (= [[["_" :ace] ["_" :king]][]]) (compare-ranks ["_" :ace] ["_" :king])))
    (testing "if the ranks are equal, clubs beat spades"
      (is (= [[[:club :ace] [:spade :ace]][]]) (compare-ranks [:club :ace] [:spade :ace])))
    (testing "if the ranks are equal, diamonds beat clubs"
      (is (= [[[:diamond 5] [:club 5]][]]) (compare-ranks [:diamond 5] [:club 5])))
    (testing "if the ranks are equal, hearts beat diamonds"
      (is (= [[[:heart 10] [:diamond 10]][]]) (compare-ranks [:heart 10] [:diamond 10])))))

(deftest test-play-round
  (testing "play round gives both cards to the side which had higher card"
    (testing "player-1 has upper hand, gets both the cards"
      (is (= [(conj clojure.lang.PersistentQueue/EMPTY [:heart :queen] [:heart :jack]) 
                clojure.lang.PersistentQueue/EMPTY] 
              (play-round 
                (conj clojure.lang.PersistentQueue/EMPTY [:heart :queen]) 
                (conj clojure.lang.PersistentQueue/EMPTY [:heart :jack])))))
    (testing "player-2 has upper hand, get both cards"
      (is (=  [clojure.lang.PersistentQueue/EMPTY 
                (conj clojure.lang.PersistentQueue/EMPTY [:diamond 2] [:club 3])] 
              (play-round 
                (conj clojure.lang.PersistentQueue/EMPTY [:diamond 2])
                (conj clojure.lang.PersistentQueue/EMPTY [:club 3]))))))) 


(deftest test-play-game
  (testing "the player loses when they run out of cards"))

(deftest test-deal
  (testing "deal create two decks of cards")
    (is (= 2 (count (deal cards)))))

