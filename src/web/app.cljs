(ns web.app
  (:require ["@material-ui/core/AppBar" :default AppBar]
            ["@material-ui/core/Button" :default Button]
            ["@material-ui/core/Card" :default Card]
            ["@material-ui/core/CardActions" :default CardActions]
            ["@material-ui/core/CardContent" :default CardContent]
            ["@material-ui/core/CardMedia" :default CardMedia]
            ["@material-ui/core/Container" :default Container]
            ["@material-ui/core/CssBaseline" :default CssBaseline]
            ["@material-ui/core/Grid" :default Grid]
            ["@material-ui/core/Link" :default Link]
            ["@material-ui/core/ToolBar" :default ToolBar]
            ["@material-ui/core/Typography" :default Typography]
            ["@material-ui/core/colors" :as color]
            ["@material-ui/core/styles" :refer [withStyles]]
            ["@material-ui/icons/PhotoCamera" :default CameraIcon]
            [goog.object :as g]
            [reagent.core :as r]
            [reagent.dom :as d]))

;; -------------------------
;; App

(def cards [1 2 3 4 5 6])

(defn styles [^js/Mui.Theme theme]
  #js {:content #js {:flexGrow 1}
       :media #js {:paddingTop "56.25%"}
       :icon #js {:margin (.spacing theme)}
       :buttons #js {:marginTop (.spacing theme 8)}
       :card #js {:height "100%" :display :flex :flexDirection :column}
       :grid #js {:paddingTop (.spacing theme 8) :paddingBottom (.spacing theme 8)}
       :footer #js {:backgroundColor (g/get (.-grey color) 100) :padding (.spacing theme 6)}
       :container #js {:backgroundColor (g/get (.-grey color) 100) :padding (.spacing theme 8 0 6)}})

(defn nav-bar [classes]
  [:> AppBar {:position :relative}
   [:> ToolBar
    [:> CameraIcon {:className classes.icon}]
    [:> Typography {:variant :h6 :color :inherit :noWrap true} "Album layout"]]])

(defn hero [classes]
  [:div {:className classes.container}
   [:> Container {:maxWidth :sm}
    [:> Typography {:component :h1 :variant :h2 :align :center :color :textPrimary :gutterBottom true} "Album layout"]
    [:> Typography {:variant :h5 :align :center :color :textSecondary :paragraph true}
     "Something short and leading about the collection below-its contents, the creator, etc. 
       Make it short and sweet, but not too short so folks don&apos;t simply skip over it entirely."]
    [:div {:className classes.buttons}
     [:> Grid {:container true :spacing 2 :justify :center}
      [:> Grid {:item true}
       [:> Button {:variant :contained :color :primary} "Main call to action"]]
      [:> Grid {:item true}
       [:> Button {:variant :outlined :color :primary} "Seconary action"]]]]]])

(defn card [classes id]
  [:> Grid {:item true :key id :xs 12 :sm 6 :md 4}
   [:> Card {:className classes.card}
    [:> CardMedia {:className classes.media :image "https://source.unsplash.com/random" :title "Image title"}]
    [:> CardContent {:className classes.content}
     [:> Typography {:gutterBottom true :variant :h5 :component :h2} "Heading Card " id]
     [:> Typography
      "This is a media card. You can use this section to describe the content."]]
    [:> CardActions
     [:> Button {:size :small :color :primary} "View"]
     [:> Button {:size :small :color :primary} "Edit"]]]])

(defn content [classes]
  [:main
   (hero classes)
   [:> Container {:className classes.grid :maxWidth :md}
    [:> Grid {:container true :spacing 2}
     [:<> (map #(card classes %) cards)]]]])

(def copy-right [:> Typography {:color :textSecondary :align :center :variant :body2} "Copyright © "
                 (.getFullYear (js/Date.)) [:<> " | "]
                 [:> Link {:color :inherit :href "https://material-ui.com"} "Your Website "]])

(defn footer [classes]
  [:footer {:className classes.footer}
   [:> Typography {:variant :h6 :align :center :gutterBottom true} "Footer"]
   [:> Typography {:variant :subtitle1 :align :center :color :textSecondary :component :p}
    "Something here to give the footer a purpose!"]
   [:<> copy-right]])

(defn album [{:keys [classes] :as _}]
  [:<>
   [:> CssBaseline]
   (nav-bar classes)
   (content classes)
   (footer classes)])

(def with-styles (withStyles styles))

(defn app []
  [:<>
   [:> (with-styles (r/reactify-component album))]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [app] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
