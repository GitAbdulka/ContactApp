package com.example.mycontactapp2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mycontactapp2.databinding.ScreenAddBinding
import com.example.mycontactapp2.room.Contact
import com.example.mycontactapp2.room.ContactDao
import com.example.mycontactapp2.room.ContactDatabase


class AddScreen : Fragment(R.layout.screen_add){

    private lateinit var binding: ScreenAddBinding
    private lateinit var dao: ContactDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ScreenAddBinding.bind(view)
        dao = ContactDatabase.getDatabase(requireContext()).contactDao()
        binding.btnDone.setOnClickListener {
            val name = binding.etName.text.toString()
            val surname = binding.etSurname.text.toString()
            val phone = binding.etPhone.text.toString()

            dao.addContact(Contact(0,name, surname, phone))

            findNavController().popBackStack()
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}