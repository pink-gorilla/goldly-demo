(ns systems.vega-charts
  (:require
   [goldly.runner :refer [system-start!]]
   [goldly.system :as goldly :refer [def-ui]]))

(def-ui bar
  {:$schema "https://vega.github.io/schema/vega-lite/v4.json"
   :description "A simple bar chart with embedded data."
   :width 200 ; "container"
   :height 200 ; "container"
   :data {:values [{:a "A" :b 28} {:a "B" :b 55} {:a "C" :b 43} {:a "D" :b 91}
                   {:a "E" :b 81} {:a "F" :b 53} {:a "G" :b 19} {:a "H" :b 87}
                   {:a "I" :b 52} {:a "J" :b 127}]}
   :mark "bar"
   :encoding {:x {:field "a" :type "ordinal"}
              :y {:field "b" :type "quantitative"}}})

(def-ui multi-plot-zoom
  {:$schema
   "https://vega.github.io/schema/vega-lite/v4.json"
   :data {:url "/r/data/sp500.csv"}
   :vconcat
   [{:width 480
     :mark "area"
     :encoding {:x {:field "date"
                    :type "temporal"
                    :scale {:domain {:selection "brush"}}
                    :axis {:title ""}}
                :y {:field "price", :type "quantitative"}}}
    {:width 480
     :height 60
     :mark "area"
     :selection
     {:brush {:type "interval", :encodings ["x"]}}
     :encoding {:x {:field "date", :type "temporal"}
                :y
                {:field "price"
                 :type "quantitative"
                 :axis {:tickCount 3, :grid false}}}}]})

(def-ui multi-line
  {:$schema "https://vega.github.io/schema/vega-lite/v4.json"
   :description "Stock prices of 5 Tech Companies over Time."
   :data {:url "/r/data/stocks.csv"}
   :transform [;{:filter "datum.symbol==='GOOG'"},
               {:filter {:field "date", :timeUnit "year", :range [2007, 2010]}}]
   :width 1200
   :height 600
   :mark "line"
   :selection   {:brush {:type "interval"}} ; :encodings ["x"]
   :encoding  {;:row {:field "symbol" :type "nominal"}
    ;:x {:field "date", :type "temporal"   :scale {:domain {:selection "brush"}}}
               :x {:field "date" :type "temporal"
                   :axis {:tickCount 8
                          :labelAlign "left"
                          :labelExpr "[timeFormat(datum.value, '%b'), timeFormat(datum.value, '%m') == '01' ? timeFormat(datum.value, '%Y') : '']"
                          :labelOffset 4
                          :labelPadding -24
                          :tickSize 30
                          :gridDash {:condition {:test {:field "value" :timeUnit "month", :equal 1}, :value []}
                                     :value [2,2]}
                          :tickDash {:condition {:test {:field "value", :timeUnit "month", :equal 1}, :value []}
                                     :value [2,2]}}}
               :y {:field "price", :type "quantitative"}
               :color {:field "symbol", :type "nominal"}}})

(system-start!
 (goldly/system
  {:name "vega charts"
   :route "/vega-charts"
   :state {}
   :html [:<>
          [:h1 "Vega charts"]
          [:h4 "generated by manually writing spec"]
          [:div.flex.flex-row.content-between
           [:div.flex.flex-col.justify-start
            [:div.bar.h-64.w-full
             [:p/vega bar]]
            [:div.multi
             [:p/vega multi-plot-zoom]]]
           [:p/vega multi-line]]]
   :fns {;:incr (fn [_ s] (inc s))
         }}
  {:fns {;:getdestination [(fn [place] (place places)) [:map]]
         }}))




