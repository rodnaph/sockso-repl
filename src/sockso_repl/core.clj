
(ns sockso-repl.core
    (:use [sockso-repl.urls :as urls])
    (:use [clojure.string :as string]))

(defmulti command :name)

(defmethod command :connect 
    [{:keys [name args]}]
    (println (format "Server changed to %s" (:host args))))

(defmethod command :default
    [{:keys [name args]}]
    (println (format "ERROR: Unknown command %s" name)))

(defn parse-command-arg
    "Parse a command arg pair into a map with :name and :value keys"
    [pair]
    (let [[name value] (string/split pair #"=")]
        (hash-map (keyword name) value)))

(defn parse-command-args
    "Parses name/value argument pairs into a map" 
    [lst]
    (reduce #(merge %1 (parse-command-arg %2)) {} lst))

(defn parse-command-str
    "Parse command string into map of name and arguments"
    [cmd]
    (let [[name & args] (string/split cmd #"\s+")]
        {:name (keyword name)
         :args (parse-command-args args)}))

(defn -main[]
    (println "Hello!"))

