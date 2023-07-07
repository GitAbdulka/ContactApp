package com.example.mycontactapp2

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mycontactapp2.databinding.ScreenMainBinding
import com.example.mycontactapp2.room.Contact
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

        updateToolbarState()

        binding.btnAdd.setOnClickListener {
            val action = MainScreenDirections.actionMainScreenToAddScreen(null)
            findNavController().navigate(action)
        }

        binding.ivClose.setOnClickListener {
            dao.getMyAllContacts().forEach { contact ->
                if (contact.state == 1) {
                    dao.updateContact(
                        Contact(
                            contact.id,
                            contactName = contact.contactName,
                            surname = contact.surname,
                            phoneNumber = contact.phoneNumber,
                            state = 0
                        )
                    )
                }
            }
            binding.ivClose.isVisible = false
            binding.title.text = "Contacts"
            setData()
            adapter.notifyDataSetChanged()
        }

        binding.editQuery.doAfterTextChanged {
            adapter.contacts = dao.findContactWithName("$it%").toMutableList()
        }

        binding.deleteAll.setOnClickListener {
            var isDeleteAll = true
            dao.getMyAllContacts().forEachIndexed { index, contact ->
                if (contact.state == 1) {
                    isDeleteAll = false
                    dao.deleteContact(contact.id)
                }
                if (index == dao.getMyAllContacts().size - 1 && isDeleteAll) {
                    dao.deleteAll()
                }
            }
            updateToolbarState()
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

        adapter.observerAdd { contact ->
            dao.updateContact(
                Contact(
                    contact.id,
                    contactName = contact.contactName,
                    surname = contact.surname,
                    phoneNumber = contact.phoneNumber,
                    state = 1
                )
            )
            var count = 0
            dao.getMyAllContacts().forEach { contact ->
                if (contact.state == 1) {
                    count++
                }
            }
            if (count != 0) {
                binding.ivClose.isVisible = true
                binding.title.text = count.toString()
            } else {
                binding.ivClose.isVisible = false
                binding.title.text = "Contacts"
            }
        }

        adapter.observerRemove { contact ->
            dao.updateContact(
                Contact(
                    contact.id,
                    contactName = contact.contactName,
                    surname = contact.surname,
                    phoneNumber = contact.phoneNumber,
                    state = 0
                )
            )
            var count = 0
            dao.getMyAllContacts().forEach { contact ->
                if (contact.state == 1) {
                    count++
                }
            }
            if (count != 0) {
                binding.ivClose.isVisible = true
                binding.title.text = count.toString()
            } else {
                binding.ivClose.isVisible = false
                binding.title.text = "Contacts"
            }
        }

        setData()
    }
    fun updateToolbarState() {
        var count = 0
        dao.getMyAllContacts().forEach { contact ->
            if (contact.state == 1) count++
        }
        if (count != 0) {
            binding.ivClose.isVisible = true
            binding.title.text = count.toString()
        } else {
            binding.ivClose.isVisible = false
            binding.title.text = "Contacts"
        }
    }
    private fun setData() {
        adapter.contacts = dao.getMyAllContacts().toMutableList()
    }
}