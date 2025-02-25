package com.gildedrose


open class NamedItem(
    name: String,
    sellIn: Int,
    quality: Int,
) : Item(name, sellIn, quality) {

    open fun updated(): Item {
        val updatedSellIn = sellIn - 1
        var updatedQuality = quality - 1

        if (updatedSellIn < 0 && updatedQuality > 0) {
            updatedQuality -= 1
        }

        return toItem(name, updatedSellIn, updatedQuality)
    }

    fun toItem(name: String, sellIn: Int, quality: Int): Item {
        return Item(name, sellIn, quality.coerceIn(0, 50))
    }
}

class AgedBrie(sellIn: Int, quality: Int) : NamedItem(
    name = "Aged Brie",
    sellIn = sellIn,
    quality = quality
) {

    override fun updated(): Item {

        val updatedSellIn = sellIn - 1
        var updatedQuality = quality

        if (updatedQuality < 50) {
            updatedQuality += 1
        }

        return toItem(name, updatedSellIn, updatedQuality)
    }
}

class Backstage(sellIn: Int, quality: Int) : NamedItem(
    name = "Backstage passes to a TAFKAL80ETC concert",
    sellIn = sellIn,
    quality = quality
) {
    override fun updated(): Item {
        val updatedSellIn = sellIn - 1
        var updatedQuality = quality

        if (quality < 50) {
            updatedQuality +=
                when (sellIn) {
                    in 1..5 -> 3
                    in 6..10 -> 2
                    else -> 1
                }
        }

        if (updatedSellIn < 0) {
            updatedQuality = 0
        }

        return toItem(name, updatedSellIn, updatedQuality)
    }
}

class Sulfuras(sellIn: Int, quality: Int) : NamedItem(
    name = "Sulfuras, Hand of Ragnaros",
    sellIn = sellIn,
    quality = quality
) {
    override fun updated(): Item {
        return Item(name, sellIn, quality)
    }
}

class ConjuredItem(name: String, sellIn: Int, quality: Int) : NamedItem(
    name = name,
    sellIn = sellIn,
    quality = quality
) {

    init {
        require(name.startsWith("Conjured")) { "Conjured item name must start with 'Conjured'" }
    }

    override fun updated(): Item {
        val updatedSellIn = sellIn - 1
        var updatedQuality = quality - 2

        if (updatedSellIn < 0 && updatedQuality > 0) {
            updatedQuality -= 2
        }

        return toItem(name, updatedSellIn, updatedQuality)
    }
}
