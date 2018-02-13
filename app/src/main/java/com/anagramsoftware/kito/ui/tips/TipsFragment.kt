package com.anagramsoftware.kito.ui.tips

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import com.anagramsoftware.kito.R
import com.anagramsoftware.kito.extentions.navigateToPredictionDetail
import com.anagramsoftware.kito.interfaces.OnItemClickListener
import com.anagramsoftware.kito.ui.common.adapters.ResultAdapter
import org.koin.android.ext.android.inject

/**
* Created by udesh on 2/11/18.
*/

class TipsFragment: Fragment() {

    private val viewModel: TipsViewModel by inject()

    private lateinit var tipsAdapter: ResultAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tips, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val resultsRV = view.findViewById<RecyclerView>(R.id.results_rv)
        tipsAdapter = ResultAdapter()
        tipsAdapter.listener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                navigateToPredictionDetail(predictionId = tipsAdapter.getItem(position).id)
            }

            override fun onItemLongClick(position: Int) {

            }

        }
        resultsRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        resultsRV.adapter = tipsAdapter

        viewModel.apply {
            start()
            results.observe(this@TipsFragment, Observer {
                tipsAdapter.accept(it)
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.stop()
    }

    companion object {
        fun create() = TipsFragment()
    }

}