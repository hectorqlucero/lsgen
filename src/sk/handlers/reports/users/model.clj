(ns sk.handlers.reports.users.model
  (:require
   [sk.models.crud :refer [db Query]]))

(def get-users-sql
  (str
   "
SELECT *
FROM users_view
"))

(defn get-users
  []
  (Query db get-users-sql))

