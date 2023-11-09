package com.example.mazdamaintenance.ui.records

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.mazdamaintenance.Adapters.ClientAdapter
import com.example.mazdamaintenance.DataObject
import com.example.mazdamaintenance.DatabaseMazda
import com.example.mazdamaintenance.Entities.Record
import com.example.mazdamaintenance.R
import kotlinx.android.synthetic.main.fragment_recordcallview.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class RecordEmail : Fragment() {

    private lateinit var database: DatabaseMazda
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_record_email, container, false)
        database = Room.databaseBuilder(
            requireContext(), DatabaseMazda::class.java, "DatabaseMazda"
        ).build()

        doAsync {
            DataObject.listdata = database.record().getRemail() as MutableList<Record>
            uiThread {
                setRecycler()
            }
        }

        return root
    }

    fun setRecycler(){
        recycler_view.adapter = ClientAdapter(DataObject.getAllData1())
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
    }

}