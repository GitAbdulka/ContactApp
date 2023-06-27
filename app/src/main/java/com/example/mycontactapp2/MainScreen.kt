package com.example.mycontactapp2

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mycontactapp2.databinding.ScreenMainBinding
import com.example.mycontactapp2.room.ContactDao
import com.example.mycontactapp2.room.ContactDatabase


class MainScreen : Fragment(R.layout.screen_main) {

    private lateinit var binding: ScreenMainBinding
    private val adapter = ContactAdapter()
    private lateinit var dao: ContactDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ScreenMainBinding.bind(view)
        binding.rvContacts.adapter = adapter
        dao = ContactDatabase.getDatabase(requireContext()).contactDao()


        binding.btnAdd.setOnClickListener {
            val action = MainScreenDirections.actionMainScreenToAddScreen(null)
            findNavController().navigate(action)
        }

        binding.editQuery.doAfterTextChanged {
            adapter.contacts = dao.findContactWithName("$it%").toMutableList()
        }

        binding.more.setOnClickListener {
            dao.deleteAll()
            setData()
        }

        adapter.deleteBtnClicked {
            dao.deleteContact(it.id)
            setData()
//            val number = Uri.parse("tel:${it.phoneNumber}")
//            val callIntent = Intent(Intent.ACTION_DIAL, number)
            //startActivity(callIntent)
        }

        adapter.editBtnClicked {
            val action = MainScreenDirections.actionMainScreenToAddScreen(it)
            findNavController().navigate(action)
        }

        adapter.observer {
            if(adapter.checkedContactId.isNotEmpty()){
                binding.ivClose.isVisible = true
                binding.title.text = adapter.checkedContactId.count().toString()
            }
        }

        setData()
    }

    private fun setData() {
        adapter.contacts = dao.getMyAllContacts().toMutableList()
    }
}