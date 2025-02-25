package com.gildedrose

class GildedRose(var items: List<Item>) {

    fun updateQuality() {

        items = items
            .map { item ->
                val namedItem = when {
                    item.name == "Aged Brie" -> AgedBrie(item.sellIn, item.quality)
                    item.name == "Backstage passes to a TAFKAL80ETC concert" -> Backstage(item.sellIn, item.quality)
                    item.name == "Sulfuras, Hand of Ragnaros" -> Sulfuras(item.sellIn, item.quality)
                    item.name.startsWith("Conjured") -> ConjuredItem(item.name, item.sellIn, item.quality)
                    else -> NamedItem(item.name, item.sellIn, item.quality)
                }
                namedItem.updated()
            }
            .toList()
    }
}
