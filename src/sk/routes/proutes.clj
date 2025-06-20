(ns sk.routes.proutes
  (:require
   [sk.handlers.reports.users.controller :as users-report]
   [compojure.core :refer [defroutes GET POST]]
   [sk.handlers.admin.users.controller :as users-controller]
   [sk.handlers.users.controller :as users-dashboard]))

(defroutes proutes
  (GET "/reports/users" params [] (users-report/users params))
  (GET "/admin/users" params [] (users-controller/users params))
  (GET "/admin/users/edit/:id" [id] (users-controller/users-edit id))
  (POST "/admin/users/save" params [] (users-controller/users-save params))
  (GET "/admin/users/add" params [] (users-controller/users-add params))
  (GET "/admin/users/delete/:id" [id] (users-controller/users-delete id))
  (GET "/users" params [] (users-dashboard/users params)))