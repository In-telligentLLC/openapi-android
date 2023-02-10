package com.intelligent.openapidemo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.intelligent.openapidemo.R
import com.sca.in_telligent.openapi.data.network.model.Community

class SearchCommunityAdapter : RecyclerView.Adapter<CommunityListAdapter.CommunityHolder>() {

    private val communities: java.util.ArrayList<Community> = ArrayList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommunityListAdapter.CommunityHolder {


        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.communitylist_item, parent, false)



        return CommunityListAdapter.CommunityHolder(itemView)

    }

    class CommunityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var communityTextView: TextView = itemView.findViewById(R.id.community_list_item)


    }

    override fun onBindViewHolder(holder: CommunityListAdapter.CommunityHolder, position: Int) {

        val community = communities[position]
        holder.communityTextView.text = community.name

    }

    override fun getItemCount(): Int {
        return communities.size

    }

    fun getCommunitiesList(communitiesList: List<Community>) {

        communities.clear()
        communities.addAll(communitiesList)
        notifyDataSetChanged()


    }


}