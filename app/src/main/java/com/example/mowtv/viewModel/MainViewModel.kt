package com.example.mowtv.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.mowtv.data.MowTVDB
import com.example.mowtv.model.Report
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*


class MainViewModel(application: Application) : AndroidViewModel(application) {
    var allUsers : MutableLiveData<List<Report>> = MutableLiveData()
    var cats : List<String> = listOf()
    init{
        GlobalScope.launch {
        getAllReports()
        }
    }

    fun getAllReportsObservers(): MutableLiveData<List<Report>> {
        return allUsers
    }

    suspend fun getAllReports() {
        val reportsDao = MowTVDB.getDatabase(getApplication())?.reportsDao()
        val list = reportsDao?.getAllReportsData()
        allUsers.postValue(list!!)
    }

    fun insertReportInfo(entity: Report){
        val reportsDao = MowTVDB.getDatabase(getApplication())?.reportsDao()
        reportsDao?.insert(entity)
        GlobalScope.launch {
            getAllReports()
        }
    }
}
