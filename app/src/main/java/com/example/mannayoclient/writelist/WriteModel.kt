package com.example.mannayoclient.writelist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


data class Item(val contents: String)


class WriteModel : ViewModel() {
    val itemsLiveData = MutableLiveData<ArrayList<Item>>()

    private val items = ArrayList<Item>()

    fun getItem(pos: Int) = items[pos]
    val itemsSize
        get() = items.size

    fun addItem(item: Item) {
        items.add(item)
        itemsLiveData.value = items
    }



}