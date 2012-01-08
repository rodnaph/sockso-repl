
(ns sockso-repl.server)

(def server (atom {
    :host "127.0.0.1:4444"
}))

(defn prop-set
    "Set a server parameter"
    [key value]
    (swap! server assoc (keyword key) value))

(defn prop-get
    "Fetch a server parameter"
    [key]
    ((keyword key) @server))

(defn url
    "Fetch a url using the specified formatted resource"
    [resource & args]
    (format "http://%s%s" (prop-get :host)
                          (apply (partial format resource) args)))

