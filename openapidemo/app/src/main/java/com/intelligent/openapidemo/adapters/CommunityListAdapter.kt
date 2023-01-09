package com.intelligent.openapidemo.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.intelligent.openapidemo.R
import com.sca.in_telligent.openapi.data.network.model.Community


class CommunityListAdapter(private val listener: ItemClick) :
    RecyclerView.Adapter<CommunityListAdapter.CommunityHolder>() {

    private val communities: java.util.ArrayList<Community> = ArrayList()


    class CommunityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var communityTextView: TextView = itemView.findViewById(R.id.community_list_item)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.communitylist_item, parent, false)



        return CommunityHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommunityHolder, position: Int) {

        val community = communities[position]
        holder.communityTextView.text = community.name

        holder.itemView.setOnClickListener {
            community.id.let { it1 ->
                listener.buildingSelected(
                    buildingId = it1
                )
            }
        }


        val item = communities[position]
        holder.communityTextView.text = item.name

    }

    override fun getItemCount(): Int {
        return communities.size
    }


    fun getCommunitiesList(communitiesList: List<Community>) {

        communities.clear()
        communities.addAll(communitiesList)
        notifyDataSetChanged()


    }


    interface ItemClick {
        fun buildingSelected(buildingId: Int)
    }
}






