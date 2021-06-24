package com.ariesvelasquez.selectionprototype

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private var itemList: MutableList<ItemModel> = mutableListOf()

    private lateinit var itemListRecyclerViewAdapter: GenericRecyclerViewAdapter<ItemModel>
    private lateinit var recyclerViewList: RecyclerView

    private lateinit var deleteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initViews
        initRecyclerView()
        initAddButton()
        initDeleteButton()

        generateDummyItem()
    }

    private fun initRecyclerView() {
        recyclerViewList = findViewById(R.id.recyclerViewList)
        recyclerViewList.layoutManager = GridLayoutManager(this, 2)

        itemListRecyclerViewAdapter =
            GenericRecyclerViewAdapter(itemList, R.layout.item_selector) { v, position, item ->
                updateListUI(position)
            }

        recyclerViewList.adapter = itemListRecyclerViewAdapter

    }

    private fun updateListUI(position: Int) {
        // Handle Item View Events.
        itemList[position].isSelected = !itemList[position].isSelected
        itemListRecyclerViewAdapter.notifyDataSetChanged()

        // Check if need to display deleteButton
        when (itemList.all { !it.isSelected }) {
            true -> deleteButton.visibility = View.GONE
            else -> deleteButton.visibility = View.VISIBLE
        }
    }

    private fun generateDummyItem() {

        for (i in 1..5) {
            itemList.add(
                ItemModel().apply {
                    this.name = i.toString()
                }
            )
        }

        itemListRecyclerViewAdapter.notifyDataSetChanged()
    }

    private fun initAddButton() {
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        buttonAdd.setOnClickListener { addDummyItem() }
    }

    private fun initDeleteButton() {
        deleteButton = findViewById<Button>(R.id.buttonDeleteItems)
        deleteButton.setOnClickListener { deleteSelectedItems() }
    }

    private fun addDummyItem() {
        itemList.add(ItemModel().apply {
            name = "New Added"
        })
        itemListRecyclerViewAdapter.notifyDataSetChanged()
        Log.d("HEY", "addDummyItemPerClick: Clicked " + itemList.count())
    }

    private fun deleteSelectedItems() {
        itemList.removeAll { it.isSelected }
        itemListRecyclerViewAdapter.notifyDataSetChanged()
        deleteButton.visibility = View.GONE
    }
}