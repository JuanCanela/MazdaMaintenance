package com.example.mazdamaintenance.ui.records

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.mazdamaintenance.*
import com.example.mazdamaintenance.Adapters.ClientAdapter
import com.example.mazdamaintenance.Entities.Record
import com.example.mazdamaintenance.Entities.Users
import kotlinx.android.synthetic.main.fragment_recordcallview.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class RecordFragment : Fragment() {

    private lateinit var viewModel: RecordViewModel
    private lateinit var database: DatabaseMazda

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      val root =   inflater.inflate(R.layout.fragment_recordcallview, container, false)
        database = Room.databaseBuilder(
            requireContext(), DatabaseMazda::class.java, "DatabaseMazda"
        ).build()


        doAsync {
            DataObject.listdata = database.record().getRcall() as MutableList<Record>
            uiThread {
                setRecycler()
            }
        }

      return  root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecordViewModel::class.java)

    }

    fun setRecycler(){
        recycler_view.adapter = ClientAdapter(DataObject.getAllData1())
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
    }

}