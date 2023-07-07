package com.example.mycontactapp2

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mycontactapp2.databinding.ItemContactBinding
import com.example.mycontactapp2.room.Contact


class ContactAdapter() : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    var contacts = mutableListOf<Contact>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    inner class ContactViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(item: Contact) {
            binding.apply {
                tvContactName.text = item.contactName
                tvContactPhone.text = item.phoneNumber
                imgCheck.isVisible = item.state == 1
                root.setOnLongClickListener {
                    imgCheck.isVisible = true
                    observerAdd.invoke(item)
                    true
                }

                root.setOnClickListener {
                    imgCheck.isVisible = false
                    observerRemove.invoke(item)
                }

                btnEdit.setOnClickListener {
                    editBtnClicked.invoke(item)
                }

                btnCall.setOnClickListener {
                    val phoneNumber = item.phoneNumber
                    val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                    itemView.context.startActivity(callIntent)
                }
            }
        }
    }

    var observerAdd: (Contact) -> Unit = {}
    fun observerAdd(block: (Contact) -> Unit) {
        observerAdd = block
    }

    var observerRemove: (Contact) -> Unit = {}
    fun observerRemove(block: (Contact) -> Unit) {
        observerRemove = block
    }

    var deleteBtnClicked: (contact: Contact) -> Unit = {}
    fun deleteBtnClicked(block: (Contact) -> Unit) {
        deleteBtnClicked = block
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