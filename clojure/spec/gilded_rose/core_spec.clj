(ns gilded-rose.core-spec
(:require [clojure.test :refer :all]
          [gilded-rose.core :refer [update-quality item]]))

(defn perform-update-quality
  "Execute the function and return only the sell-in and quantity"
  [name sell-in quality]
  (-> (first (update-quality [(item  name sell-in quality)]))
      ((fn [item] (vector (:sell-in item) (:quality item))))))

#_(deftest gilded-rose-test
  (is (= "fixme" (:name (perform-update-quality "foo" 0 0)))))

(deftest conjured-item-quality-decrease-twice-as-fast-test
  (is (= [9 8] (perform-update-quality "Conjured" 10 10))))

(deftest aged-brie-increase-its-quality
  (is (= [9 1] (perform-update-quality "Aged Brie" 10 0))))

(deftest quality-never-is-negative
  (is (= [9 0] (perform-update-quality "+5 Dexterity Vest" 10 0))))

(deftest once-the-sell-by-date-has-passed-quality-degrades-twice-as-fast
  (is (= [-2 8] (perform-update-quality "+5 Dexterity Vest" -1 10)))
  (is (= [-2 11] (perform-update-quality "Aged Brie" -1 10)))
  (is (= [-2 8] (perform-update-quality "Elixir of the Mongoose" -1 10))))

(deftest the-quality-of-an-item-is-never-more-than-50
  (is (= [-2 50] (perform-update-quality "Aged Brie" -1 50))))

(deftest legendary-item-never-decrease-its-quality
  (is (= [-2 10] (perform-update-quality "Sulfuras, Hand Of Ragnaros" -1 10))))

(deftest backstage-tests-by-sell
  (is (= [9 12] (perform-update-quality "Backstage passes to a TAFKAL80ETC concert" 10 10)))
  (is (= [4 13] (perform-update-quality "Backstage passes to a TAFKAL80ETC concert" 5 10)))
  (is (= [-1 0]  (perform-update-quality "Backstage passes to a TAFKAL80ETC concert" 0 10))))
