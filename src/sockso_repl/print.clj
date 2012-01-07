
(ns sockso-repl.print
    (:use [clojure.string :as string]))

(defn error
    "Print an error message"
    [message]
    (println (format "ERROR: %s" message)))

(defn prompt
    "Prints the shell prompt"
    []
    (print "=> ")
    (flush))

(defn welcome
    "Prints the welcome message"
    []
    (println "Welcome to the Sockso REPL")
    (println "Type 'help' for commands")
    (println ""))

(defn format-search-result
    "Format a search result"
    [res]
    (format "#%s %s" 
        (get res "id")
        (get res "name")))

(defn search-results
    "Print a vector of search results"
    [results]
    (println (string/join "\n" (map format-search-result results))))

