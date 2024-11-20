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

;;First we have to go through all players and calculate predicted points for each one, and than based on that we need to return player/s
;;with highest predicted points whose number is equal to players we want to transfer out.

(defn suggest-best-transfer
"A function that returns best replacement for selected players based on predicted points"
[players & player-id]
  (let [ranked-players (sort-by (fn[player] (- (calculate-player-predicted-points player))) players)]
    (take (count player-id) ranked-players)))

(fact "Check if there is a return value"
(suggest-best-transfer all-players 303 =not=> nil))
