package com.example.mowtv.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mowtv.R
import com.example.mowtv.convert.DateConvert
import com.example.mowtv.model.Report
import com.example.mowtv.viewModel.MainViewModel

class RecyclerViewAdapter():
    RecyclerView.Adapter<RecyclerViewAdapter.ReportViewHolder>() {
    private var items = emptyList<Report>()

    internal fun setListData(data:List<Report>) {
        this.items = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReportViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_report, parent, false)
        return ReportViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
            holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        if(items.isEmpty())
            Log.d("ADAPTWR_GET_ITEM_COUNT","List is empty+${items.size}")
        return items.size
    }
    class ReportViewHolder(var view: View) : RecyclerView.ViewHolder(view){
        val name = view.findViewById<TextView>(R.id.name_textview)
        val time = view.findViewById<TextView>(R.id.date_textview)

        fun bind(data:Report){
            name?.text = data.video_name
            time?.text = DateConvert().dateToTimestamp(data.start_time)
        }

    }

}