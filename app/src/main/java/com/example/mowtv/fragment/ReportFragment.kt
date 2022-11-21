package com.example.mowtv.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mowtv.R
import com.example.mowtv.adapter.RecyclerViewAdapter
import com.example.mowtv.databinding.FragmentReportBinding
import com.example.mowtv.model.Report
import com.example.mowtv.viewModel.MainViewModel
import kotlinx.coroutines.*

class ReportFragment : Fragment() {
    
    private lateinit var viewModel: MainViewModel
    lateinit var binding: FragmentReportBinding
    var recyclerView: RecyclerView? = null
    private lateinit var adapter: RecyclerViewAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProvider(this)[MainViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }
    
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentReportBinding.inflate(inflater, container, false)
        adapter=RecyclerViewAdapter()

        viewModel.getAllReportsObservers().observe(requireActivity(), Observer<List<Report>> {reports ->
            if (reports != null) {
                adapter.setListData(reports)
            }
        })
        binding.viewData = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        recyclerView=binding.recyclerView
        recyclerView!!.adapter = adapter

        val linearLayoutManager = LinearLayoutManager(
            requireContext(), RecyclerView.VERTICAL,false)
        recyclerView!!.layoutManager = linearLayoutManager

        return binding.root
    }
}
