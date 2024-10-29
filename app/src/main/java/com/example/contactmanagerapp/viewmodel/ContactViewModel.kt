package com.example.contactmanagerapp.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactmanagerapp.repository.ContactRepository
import com.example.contactmanagerapp.room.Contact
import kotlinx.coroutines.launch

class ContactViewModel(private val repository: ContactRepository): ViewModel(),Observable {

    val contacts = repository.contacts
    private var isUpdateOrDelete = false
    private lateinit var contactToUpdateOrDelete:Contact

    @Bindable
    val inputName = MutableLiveData<String?>()
    @Bindable
    val inputEmail = MutableLiveData<String?>()
    @Bindable
    val saveOrUpdateBtnText = MutableLiveData<String>()
    @Bindable
    val clearAllOrDeleteBtnText =  MutableLiveData<String>()

    init {
        saveOrUpdateBtnText.value = "Save"
        clearAllOrDeleteBtnText.value = "Clear ALl"
    }
    fun insert(contact: Contact)= viewModelScope.launch {
        repository.insert(contact)
    }
    fun delete(contact: Contact) = viewModelScope.launch {
        repository.delete(contact)

        //RESETTING THE BUTTON AND FIELDS
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateBtnText.value = "Save"
        clearAllOrDeleteBtnText.value = "Clear ALl"
    }
    fun update(contact: Contact)= viewModelScope.launch {
        repository.update(contact)
        //RESETTING THE BUTTON AND FIELDS
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateBtnText.value = "Save"
        clearAllOrDeleteBtnText.value = "Clear ALl"
    }
    fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
    }
    fun saveOrUpdate(){
        if (isUpdateOrDelete){
            //make an update
            contactToUpdateOrDelete.contact_name = inputName.value!!
            contactToUpdateOrDelete.contact_email = inputEmail.value!!
            update(contactToUpdateOrDelete)
        }else{
            //Insert a new contact
            val name = inputName.value!!
            val email = inputEmail.value!!

            insert(Contact(0,name,email))
            //reset the name and email
            inputName.value = null
            inputEmail.value  = null

        }
    }
    fun clearAllOrDelete(){
        if (isUpdateOrDelete){
            delete(contactToUpdateOrDelete)
        }else{
            clearAll()
        }
    }
    fun initUpdateAndDelete(contact: Contact){
        inputName.value = contact.contact_name
        inputEmail.value = contact.contact_email
        isUpdateOrDelete = true
        contactToUpdateOrDelete = contact
        saveOrUpdateBtnText.value = "Update"
        clearAllOrDeleteBtnText.value= "Delete"

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }


}