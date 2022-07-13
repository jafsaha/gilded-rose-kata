(ns gilded-rose.core)


(defn is-an-item-that-decrement-twice-as-fast [item]
  (or (= "+5 Dexterity Vest" (:name item)) (= "Elixir of the Mongoose" (:name item))))

(defn is-backstage-passes [item]
  (= "Backstage passes to a TAFKAL80ETC concert" (:name item)))


(defn update-quality [items]
  (map
    (fn[item] (cond
      (and (< (:sell-in item) 0) (is-backstage-passes item))
        (merge item {:quality 0})
      (or (= (:name item) "Aged Brie") (is-backstage-passes item))
        (if (and (is-backstage-passes item) (>= (:sell-in item) 5) (< (:sell-in item) 10))
          (merge item {:quality (inc (inc (:quality item)))})
          (if (and (is-backstage-passes item) (>= (:sell-in item) 0) (< (:sell-in item) 5))
            (merge item {:quality (inc (inc (inc (:quality item))))})
            (if (< (:quality item) 50)
              (merge item {:quality (inc (:quality item))})
              item)))
      (< (:sell-in item) 0)
        (if (is-backstage-passes item)
          (merge item {:quality 0})
          (if (and (> (:quality item) 1) (is-an-item-that-decrement-twice-as-fast item))
            (merge item {:quality (- (:quality item) 2)})
            item))
      (and (= (:quality item) 1) (is-an-item-that-decrement-twice-as-fast item))
        (merge item {:quality (dec (:quality item))})
      :else item))
  (map (fn [item]
      (if (not= "Sulfuras, Hand of Ragnaros" (:name item))
        (merge item {:sell-in (dec (:sell-in item))})
        item))
  items)))

(defn item [item-name, sell-in, quality]
  {:name item-name, :sell-in sell-in, :quality quality})

(defn update-current-inventory[]
  (let [inventory 
    [
      (item "+5 Dexterity Vest" 10 20)
      (item "Aged Brie" 2 0)
      (item "Elixir of the Mongoose" 5 7)
      (item "Sulfuras, Hand Of Ragnaros" 0 80)
      (item "Backstage passes to a TAFKAL80ETC concert" 15 20)
    ]]
    (update-quality inventory)
    ))
