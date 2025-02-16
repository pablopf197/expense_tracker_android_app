package com.example.expensetrackproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.expensetrackproject.R
import com.example.expensetrackproject.data.model.Category

class CategoryAdapter(context: Context, categories: List<Category>) :
    ArrayAdapter<Category>(context, 0, categories) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)

        val category = getItem(position)
        val iconImageView: ImageView = view.findViewById(R.id.iconImageView)
        val categoryTextView: TextView = view.findViewById(R.id.categoryTextView)

        iconImageView.setImageResource(category?.icon ?: R.drawable.default_icon)
        categoryTextView.text = category?.name

        return view
    }
}