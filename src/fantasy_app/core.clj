(ns fantasy-app.core
(:require [midje.sweet :refer :all])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  
  (println "Hello, World!"))


(def all-players
  [{:id 101 :now-cost 81 :xg 0.15 :xa 0.08 :expected-bonus 3}
   {:id 202 :now-cost 65 :xg 0.30 :xa 0.12 :expected-bonus 5}
   {:id 303 :now-cost 70 :xg 0.10 :xa 0.20 :expected-bonus 2}
   {:id 404 :now-cost 90 :xg 0.40 :xa 0.05 :expected-bonus 4}
   {:id 505 :now-cost 75 :xg 0.25 :xa 0.15 :expected-bonus 3}
   {:id 606 :now-cost 60 :xg 0.20 :xa 0.10 :expected-bonus 1}
   {:id 707 :now-cost 85 :xg 0.35 :xa 0.18 :expected-bonus 5}
   {:id 808 :now-cost 55 :xg 0.12 :xa 0.25 :expected-bonus 2}])

(defn calculate-player-predicted-points
"A function that calculates player's predicted points based in next gameweek"
[player]
(let [{:keys [xg xa expected-bonus]} player
      goal-points (* 4 xg)
      assist-points (* 3 xa)
      bonus-points (* 1 expected-bonus)]
      (+ goal-points assist-points bonus-points)))

(fact "Check if there is a return value"
(calculate-player-predicted-points nil) =not=> nil)

(defn rank-players
  "A function that sorts players in decrasing order based on predicted points"
  [players]
  (sort-by (fn [player]
             (- (calculate-player-predicted-points player))) players))

(defn suggest-best-transfer
"A function that returns best replacement for selected players based on predicted points"
[players budget-in-bank & player-id]
  (let [ranked-players (filter #(not (some #{(:id %)} player-id)) 
                               (rank-players players))
        transfered-out-players (filter #(some #{(:id %)}player-id)players)
        available-budget (+ budget-in-bank (reduce + (map :now-cost transfered-out-players)))]
    (loop [remaining ranked-players
           selected-players []
           total-price 0]
      (if (or (empty? remaining)
              (== (count selected-players) (count player-id)))
        selected-players
        (let [new-price (+ total-price (:now-cost (first remaining)))]
          (if (<= new-price available-budget)
            (recur (rest remaining) (conj selected-players (first remaining)) new-price)
            (recur (rest remaining) selected-players total-price)))))))

(fact "Check if there is a return value"
(suggest-best-transfer all-players 303 =not=> nil))

(fact "Number of players returned should match input player IDs"
      (let [result (suggest-best-transfer all-players 2 303 404 101)]
        (count result) => 3))

(defn suggest-best-captain
  "A function that selects the best captain based on predicted points"
  [team]
  (first (rank-players team)))

(fact "Check if there is a return value"
      (suggest-best-captain all-players) =not=> nil)

(fact "Should return nil when the team is empty"
      (suggest-best-captain []) => nil)

(fact "The predicted points of the suggested captain should be the highest"
        (calculate-player-predicted-points (suggest-best-captain all-players))
        => (apply max (map calculate-player-predicted-points all-players)))