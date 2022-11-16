package com.eastail.yellina.Database

// Location Data
data class Location(
    val id: Int,
    val title: String,
    val image: Int,
    val isLocated: Boolean,
    val isTested: Boolean,
    val imageBig: Int
)

// Store Data
data class Store(
    val id: Int,
    val title: String,
    val description: String,
    val cost: Int,
    val image: Int,
    val imageColor: Int,
    val isBought: Int,
    val isAble: Boolean,
    val idleUp: Int
)

data class Helper(
    val id: Int,
    val music: Boolean,
    val sound: Boolean,
    val cheats: Boolean,
    val coins: Int
)
