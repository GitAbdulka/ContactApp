package com.example.mycontactapp2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mycontactapp2.databinding.ItemContactBinding
import com.example.mycontactapp2.room.Contact
import com.example.mycontactapp2.room.ContactDao


class ContactAdapter() : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private lateinit var dao: ContactDao

    var checkedContactId = mutableListOf<Int>()

    var contacts = mutableListOf<Contact>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    inner class ContactViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Contact) {
            binding.apply {
                tvContactName.text = item.contactName
                tvContactPhone.text = item.phoneNumber
                imgPhone.setOnClickListener {
                    deleteBtnClicked.invoke(item)
                }
                root.setOnLongClickListener {
                    imgCheck.isVisible = true
                    checkedContactId.add(it.id)
                    observer.invoke(Unit)
                    true
                }

                root.setOnClickListener {
                    checkedContactId.forEach {
                        if (item.id == it) {
                            checkedContactId.remove(item.id)
                            imgCheck.isVisible = false
                            observer.invoke(Unit)
                        }
                    }
                }

                btnEdit.setOnClickListener {
                    editBtnClicked.invoke(item)
                }
            }
        }
    }

    var deleteBtnClicked: (contact: Contact) -> Unit = {}

    fun deleteBtnClicked(block: (Contact) -> Unit) {
        deleteBtnClicked = block
    }

    var observer: (Unit) -> Unit = {}

    fun observer(block: (Unit) -> Unit) {
        observer = block
    }

    var editBtnClicked: (contact: Contact) -> Unit = {}
    fun editBtnClicked(block: (Contact) -> Unit) {
        editBtnClicked = block
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            ItemContactBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }
}