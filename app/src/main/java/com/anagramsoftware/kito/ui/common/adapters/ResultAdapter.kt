package com.anagramsoftware.kito.ui.common.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.anagramsoftware.kito.R
import com.anagramsoftware.kito.data.model.Result
import com.anagramsoftware.kito.interfaces.OnItemClickListener
import com.anagramsoftware.kito.ui.common.viewholders.ResultViewHolder
import io.reactivex.functions.Consumer

/**
* Created by udesh on 2/11/18.
*/

class ResultAdapter: RecyclerView.Adapter<ResultViewHolder>(), Consumer<List<Result>> {

    private val TAG = javaClass.simpleName

    private var context: Context? = null
    private var dataSet: ArrayList<Result> = ArrayList()

    var listener: OnItemClickListener? = null

    override fun accept(items: List<Result>?) {
        Log.d(TAG, "accept ${items?.size}")
        dataSet = items as ArrayList<Result>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        context = parent.context

        val inflater = LayoutInflater.from(context)

        val itemView = inflater.inflate(R.layout.item_result, parent, false)
        return ResultViewHolder(context!!, listener, itemView)
    }

    override fun onBindViewHolder(holder: ResultViewHolder?, position: Int) {
        holder?.bindData(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    fun getItem(position: Int) = dataSet[position]

}