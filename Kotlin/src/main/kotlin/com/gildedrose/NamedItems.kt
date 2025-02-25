package com.gildedrose


interface IItem {
    val name: String
    val sellIn: SellIn
    val quality: Quality

    fun updated(): IItem
    fun toItem(): Item {
        return Item(name, sellIn.value, quality.value)
    }
}

class NamedItem(
    override val name: String,
    override val sellIn: SellIn.Mutable,
    override val quality: Quality.Mutable,
) : IItem {

    override fun updated(): IItem {
        val updatedSellIn = sellIn.dec()
        val updatedQuality = if (sellIn.isExpired()) {
            quality.add(-2)
        } else {
            quality.add(-1)
        }

        return NamedItem(name, updatedSellIn, updatedQuality)
    }
}

class AgedBrie(override val sellIn: SellIn.Mutable, override val quality: Quality.Mutable) : IItem {

    override val name: String = "Aged Brie"

    override fun updated(): IItem {
        val updatedSellIn = sellIn.dec()
        val updatedQuality = quality.add(1)

        return AgedBrie(updatedSellIn, updatedQuality)
    }
}

class Backstage(override val sellIn: SellIn.Mutable, override val quality: Quality.Mutable) : IItem {

    override val name: String = "Backstage passes to a TAFKAL80ETC concert"

    override fun updated(): IItem {
        val updatedSellIn = sellIn.dec()
        val updatedQuality = when (updatedSellIn.value) {
            in 11..50 -> quality.add(1)
            in 6..10 -> quality.add(2)
            in 0..5 -> quality.add(3)
            else -> Quality.Mutable.ZERO
        }

        return Backstage(updatedSellIn, updatedQuality)
    }
}

class Sulfuras(override val sellIn: SellIn.Immutable, override val quality: Quality.Immutable) : IItem {
    override val name: String = "Sulfuras, Hand of Ragnaros"

    override fun updated(): IItem {
        return this
    }
}

class ConjuredItem(
    override val name: String,
    override val sellIn: SellIn.Mutable,
    override val quality: Quality.Mutable,
) : IItem {

    init {
        require(name.startsWith("Conjured")) { "Conjured item name must start with 'Conjured'" }
    }

    override fun updated(): IItem {
        val updatedSellIn = sellIn.dec()
        val updatedQuality = if (sellIn.isExpired()) {
            quality.add(-2 * 2)
        } else {
            quality.add(-1 * 2)
        }
        return ConjuredItem(name, updatedSellIn, updatedQuality)
    }
}

sealed class SellIn {

    abstract val value: Int

    data class Mutable(override val value: Int) : SellIn() {
        fun dec(): Mutable {
            return Mutable(value - 1)
        }

        fun isExpired(): Boolean {
            return value < 0
        }
    }

    data class Immutable(override val value: Int) : SellIn()
}

sealed class Quality {

    abstract val value: Int

    data class Mutable(private val _value: Int) : Quality() {

        override val value: Int = _value.coerceIn(0, 50)

        init {
            require(value in 0..50) { "Quality must be between 0 and 50" }
        }

        companion object {
            val ZERO = Mutable(0)
        }

        fun add(amount: Int): Mutable {
            return Mutable(value + amount)
        }
    }

    data class Immutable(override val value: Int) : Quality()
}
