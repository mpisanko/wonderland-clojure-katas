(ns alphabet-cipher.coder)

(def alphabet (into [] "abcdefghijklmnopqrstuvwxyz"))

(def dict (zipmap alphabet (iterate inc 0)))

(defn code 
  [f [k m]]
  (alphabet 
    (mod (f (dict k) (dict m)) (count alphabet))))

(defn key-msg
  [keyword message]
  (into [] (map vector keyword message)))

(defn perform-coding
  [summation-fn msg-1 msg-2]
  (apply
    str
    (map
      (partial code summation-fn)
      (key-msg msg-1 msg-2))))

(defn encode 
  [keyword message]
  (perform-coding + (cycle keyword) message))

(defn decode 
  [keyword message]
  (perform-coding - message (cycle keyword)))