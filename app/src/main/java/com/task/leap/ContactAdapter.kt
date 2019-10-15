package com.task.leap

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.contact_list_item.view.*

class ContactAdapter(val contactList:ArrayList<ContactData>,val context:Context):RecyclerView.Adapter<ContactAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.contact_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder?.tvMobileNo?.text = contactList.get(position).mobileNo
        holder?.txtName?.text = contactList.get(position).name
        holder?.imgContact?.text = contactList.get(position).name.substring(0 ,1)
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view)  {

        val tvMobileNo = view.txtMobileNo
        val txtName = view.txtName
        val imgContact = view.imgContact

    }

}

