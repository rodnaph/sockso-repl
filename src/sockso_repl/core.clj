
(ns sockso-repl.core
    (:gen-class)
    (:use [sockso-repl.urls :as urls])
    (:use [clojure.string :as string]))

(macroexpand-1 '(defcommand :connect [host] (println host)))

(defmacro defcommand [name args & body]
    `(defmethod command ~name
        [{:keys [name# [{:keys [~@args]}]]}]
            (do ~body)))

(defmulti command :name)

(command (parse-command-str "connect host=foo"))

(defcommand :connect
    [host]
    (println (format "Server changed to %s" host)))

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

