(ns fantasy-app.core
(:require [midje.sweet :refer :all])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  
  (println "Hello, World!"))

(def player
  {:id 101 :position 4 :xg 0.15 :xa 0.08 :xgc 0.90 
  :chance-of-playing 95 :clean-sheet-probability 0.0 :bps-rank 10 
  :form 5.4 
  :minutes-per-game 85 :ict-index 45.0 :expected-bonus 3})


(defn calculate-player-predicted-points
"A function that calculates player's predicted points based in next gameweek"
[player]
(let [{:keys [xg xa expected-bonus]} player
      goal-points (* 4 xg)
      assist-points (* 3 xa)
      bonus-points (* 1 expected-bonus)]
      (+ goal-points assist-points bonus-points)))

(fact "Calculates player's points"
(calculate-player-predicted-points nil) =not=> nil)
