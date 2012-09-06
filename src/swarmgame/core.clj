(ns swarmgame.core)

(def board (atom (vec (concat [20 20 20 20 1 -1 5 4 5 6] (repeat 44 3)) )))

(def pc (atom 0))

(defn incpc [] (swap! pc inc))

(def organism1 [2 4 6 6 2 4])

(defn calc-addr [reladdr]
  (-> (+ @pc reladdr)
      (mod (count @board))))

(defn get-addr [addr]
  (get @board (calc-addr addr)))

(defn board-set
  ([org pos]
     (println "Will copy org " org " to" pos)
     (reset! board (concat (take (dec pos) (deref board))
                        org
                        (drop (+ pos (count org)) (deref board) ))))
  ([] (let [origin      (incpc)
            destination (-> (incpc) calc-addr)]
        (board-set (map get-addr (range origin (+ origin 10)))
                     destination)
        (incpc))))
[0 1 45 2 34]

(defn nop []
  (incpc))


(defn set-zero []
  (let [addr (incpc)]
    (swap! board assoc (calc-addr addr) 0)
    (incpc)))

;;modulo board
;;program counter with arguments after instructions
;; allt som inte 'r med blir nop
(defn hello-world []
  (incpc)
  (println "hello world"))

(defn nop-debug []
  (incpc)
  (println "felfel"))

(def ins-set {0 'nop
              1 'board-set
              2 'set-zero
              3 'hello-world})


(defn step ([](let [ins-num (nth @board @pc)
      ins (get ins-set ins-num 'nop-debug)]
  (println "running" ins-num  " that is " (name ins)  )
  (eval (list ins))
  (println "pc" @pc)))
  ([n]
     (dotimes [i 5]
       (step)
       (println (apply str @board)))))



;; organism  0 2 3 5 3 5 7
;; 

