package com.anagramsoftware.kito.ui.common.viewholders

import android.content.Context
import android.net.Uri
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.anagramsoftware.kito.R
import com.anagramsoftware.kito.data.model.Result
import com.anagramsoftware.kito.interfaces.OnItemClickListener
import com.bumptech.glide.Glide

/**
* Created by udesh on 2/11/18.
*/

class ResultViewHolder(private val context: Context, private val listener: OnItemClickListener?, itemView: View):
        RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

    var imageView: ImageView = itemView.findViewById<View>(R.id.image) as ImageView
    var titleView: TextView = itemView.findViewById<View>(R.id.title) as TextView
    var subtitleView: TextView = itemView.findViewById<View>(R.id.subtitle) as TextView

    init {
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    fun bindData(currentItem: Result) {
        Glide.with(context).load(Uri.parse(currentItem.imageUri)).into(imageView)
        titleView.text = currentItem.containDengue.toString()
        subtitleView.text = currentItem.confidence.toString()
    }

    override fun onClick(view: View) {
        val position = adapterPosition
        if (position != RecyclerView.NO_POSITION) {
            listener?.onItemClick(position)
        }
    }

    override fun onLongClick(view: View): Boolean {
        val position = adapterPosition
        if (position != RecyclerView.NO_POSITION) {
            listener?.onItemLongClick(position)
        }
        return true
    }

}