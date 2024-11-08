package com.example.contactmanagerapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactmanagerapp.databinding.ActivityMainBinding
import com.example.contactmanagerapp.repository.ContactRepository
import com.example.contactmanagerapp.room.Contact
import com.example.contactmanagerapp.room.ContactDatabase
import com.example.contactmanagerapp.view.RecyclerViewAdapter
import com.example.contactmanagerapp.viewmodel.ContactViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var contactViewModel: ContactViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        //ROOM DATABASE
        val dao = ContactDatabase.getInstance(applicationContext).contactDAO
        val repository = ContactRepository(dao)

        // VIEW MODEL
        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        binding.contactViewModel = contactViewModel
        binding.lifecycleOwner  = this

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        DisplayUsersList()
    }

    private fun DisplayUsersList() {
        contactViewModel.contacts.observe(this,Observer{
            binding.recyclerView.adapter = RecyclerViewAdapter(
                it,{selectedItem: Contact -> listItemClicked(selectedItem) }
            )
        })
    }

    private fun listItemClicked(selectedItem: Contact){
        Toast.makeText(this,"selected name is ${selectedItem.contact_name}", Toast.LENGTH_LONG).show()

        contactViewModel.initUpdateAndDelete(selectedItem)
    }


}