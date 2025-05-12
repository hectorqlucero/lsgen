(ns sk.handlers.admin.users.view
  (:require
   [sk.models.form :refer [build-field build-hidden-field build-modal-buttons
                           build-radio build-select form]]
   [sk.models.grid :refer [build-grid build-modal modal-script]]))

(defn users-view
  [title rows]
  (let [table-id "users_table"
        labels ["lastname" "name" "username" "DOB" "Cell Phone" "Level" "status"]
        db-fields [:lastname :firstname :username :dob_formatted :cell :level_formatted :active_formatted]
        fields (apply array-map (interleave db-fields labels))
        args {:new true :edit true :delete true}
        href "/admin/users"]
    (build-grid title rows table-id fields href args)))

;; Start users-form
(defn build-users-fields
  [row]
  (list
   (build-hidden-field {:id "id"
                        :name "id"
                        :value (:id row)})
   (build-field {:label "Lastname:"
                 :type "text"
                 :id "lastname"
                 :name "lastname"
                 :required true
                 :placeholder "Lastname here..."
                 :value (:lastname row)})
   (build-field {:label "Name:"
                 :type "text"
                 :id "firstname"
                 :name "firstname"
                 :required true
                 :placeholder "Name here..."
                 :value (:firstname row)})
   (build-field {:label "Username:"
                 :type "email"
                 :id "username"
                 :name "username"
                 :required true
                 :placeholder "Users email here..."
                 :value (:username row)})
   (build-field {:label "Date of Birth:"
                 :type "date"
                 :id "dob"
                 :name "dob"
                 :required false
                 :value (:dob row)})
   (build-field {:label "Cell Phone:"
                 :type "text"
                 :id "cell"
                 :name "cell"
                 :required false
                 :value (:cell row)})
   (build-select {:label "User Level"
                  :id "level"
                  :name "level"
                  :required true
                  :error "The user level is a required field..."
                  :value (:level row)
                  :options [{:value ""
                             :label "Select User Level"}
                            {:value "U"
                             :label "User"}
                            {:value "A"
                             :label "Administrator"}
                            {:value "S"
                             :label "System"}]})
   (build-radio {:label "Status:"
                 :name "active"
                 :value (:active row)
                 :options [{:id "activeT"
                            :label "Active"
                            :value "T"}
                           {:id "activeF"
                            :label "Inactive"
                            :value "F"}]})))

(defn build-users-form
  [_ row]
  (let [fields (build-users-fields row)
        href "/admin/users/save"
        buttons (build-modal-buttons)]
    (form href fields buttons)))
;; End users-form

(defn build-users-modal
  [title row]
  (build-modal title row (build-users-form title row)))

(defn users-edit-view
  [title row rows]
  (list
   (users-view "Users" rows)
   (build-users-modal title row)))

(defn users-add-view
  [title row rows]
  (list
   (users-view "Users" rows)
   (build-users-modal title row)))

(defn users-modal-script
  []
  (modal-script))
