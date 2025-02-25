package com.gildedrose

class GildedRose(var items: List<Item>) {

    fun updateQuality() {

        items = items
            .map { item ->
                val mutableSellIn = SellIn.Mutable(item.sellIn)
                val immutableSellIn = SellIn.Immutable(item.sellIn)
                when {
                    item.name == "Aged Brie" -> AgedBrie(mutableSellIn, Quality.Mutable(item.quality))
                    item.name == "Backstage passes to a TAFKAL80ETC concert" -> Backstage(
                        mutableSellIn,
                        Quality.Mutable(item.quality)
                    )

                    item.name == "Sulfuras, Hand of Ragnaros" -> Sulfuras(
                        immutableSellIn,
                        Quality.Immutable(item.quality)
                    )

                    item.name.startsWith("Conjured") -> ConjuredItem(
                        item.name,
                        mutableSellIn,
                        Quality.Mutable(item.quality)
                    )

                    else -> NamedItem(item.name, mutableSellIn, Quality.Mutable(item.quality))
                }.updated()
            }
            .map { it.toItem() }
            .toList()
    }
}
