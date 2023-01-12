package com.intelligent.openapidemo.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.intelligent.openapidemo.R
import com.sca.in_telligent.openapi.data.network.model.Notification

class AlertListAdapter(private val listener: AlertListAdapter.ItemClick) :
    RecyclerView.Adapter<AlertListAdapter.AlertListHolder>() {
    private val communityAlertList: java.util.ArrayList<Notification> = ArrayList()


    class AlertListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var titleTextView: TextView = itemView.findViewById(R.id.title_textview)
        var typeTextView: TextView = itemView.findViewById(R.id.type_textview)
        var dateTextView: TextView = itemView.findViewById(R.id.date_textview)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertListHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.alert_list_item, parent, false)
        return AlertListHolder(itemView)
    }

    override fun onBindViewHolder(holder: AlertListHolder, position: Int) {
        val item = communityAlertList[position]
        holder.titleTextView.text = item.title
        holder.typeTextView.text = item.type
        holder.dateTextView.text = item.date.toString()
        holder.itemView.setOnClickListener {
            item.id.let { it1 ->
                listener.openAlertDetail(
                    notificationId = it1
                )
            }
        }


    }

    override fun getItemCount(): Int {
        return communityAlertList.size

    }

    fun getCommunitiesAlertList(communitiesList: java.util.ArrayList<Notification>) {
        communityAlertList.clear()
        communityAlertList.addAll(communitiesList)
        notifyDataSetChanged()


    }

    interface ItemClick {
        fun openAlertDetail(notificationId: Int)
    }


}