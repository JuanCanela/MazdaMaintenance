package com.example.mazdamaintenance

import android.os.Bundle
import android.view.*
import android.view.Menu
import androidx.fragment.app.Fragment
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.mazdamaintenance.Adapters.ClientAdapter
import com.example.mazdamaintenance.Adapters.ContactsAdapter
import com.example.mazdamaintenance.Entities.Users
import com.example.mazdamaintenance.databinding.FragmentsContacsViewBinding
import com.example.mazdamaintenance.ui.ContacsViewModel
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.fragment_recordcallview.*
import kotlinx.android.synthetic.main.fragment_recordcallview.recycler_view
import kotlinx.android.synthetic.main.fragments_contacs_view.*
import kotlinx.android.synthetic.main.fragments_contacs_view.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.Locale.filter

class ContactsFragment : Fragment(){
    private lateinit var binding : FragmentsContacsViewBinding
    private lateinit var database: DatabaseMazda
    private val mainViewModel: ContacsViewModel by viewModels()
    //private val myAdapter:ContactsAdapter()
    private val myAdapter: ContactsAdapter by lazy { ContactsAdapter(DataObject.getAllData()) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
       val root = inflater.inflate(R.layout.fragment_contacts, container, false)
        database = Room.databaseBuilder(
            requireContext(), DatabaseMazda::class.java, "DatabaseMazda"
        ).build()

       // val texto = requireArguments().getString("text")
        /*
        if (texto == null){
            findNavController().navigate(R.id.action_nav_Contacts_to_nav_call)
        }
         */

        doAsync {
            DataObject.listdata1 = database.users().getUser() as MutableList<Users>


            uiThread {
                setRecycler()


            }
        }

       return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        btnTestContacts.setOnClickListener{
            findNavController().navigate(R.id.action_nav_Contacts_to_nav_call)
        }
         */
    }

    fun setRecycler(){
        recycler_view.adapter = ContactsAdapter(DataObject.getAllData())
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
    }

    fun refresh(){
        mainViewModel.readData.observe(requireActivity(), {
            myAdapter.setData1(it)
        })
    }



}