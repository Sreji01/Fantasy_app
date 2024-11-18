(ns fantasy-app.core
(:require [midje.sweet :refer :all])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  
  (println "Hello, World!"))

(defn calculate-player-predicted-points
"A function that calculates player's predicted points based in next gameweek"
[player])

(fact "Calculates player's points"
(calculate-player-predicted-points nil) =not=> nil)