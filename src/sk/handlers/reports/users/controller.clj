(ns sk.handlers.reports.users.controller
  (:require
   [sk.handlers.reports.users.model :refer [get-users]]
   [sk.handlers.reports.users.view :refer [users-view]]
   [sk.layout :refer [application]]
   [sk.models.util :refer [get-session-id]]))

(defn users [_]
  (let [title "Users Report"
        ok (get-session-id)
        js nil
        rows (get-users)
        content (users-view title rows)]
    (application title ok js content)))

