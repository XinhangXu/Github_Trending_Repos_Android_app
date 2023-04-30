package com.xinhangxu.githubtrending.Adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.xinhangxu.githubtrending.DetailsActivity
import com.xinhangxu.githubtrending.MainActivity
import com.xinhangxu.githubtrending.Models.ReposItemsModel
import com.xinhangxu.githubtrending.R


class ReposItemsAdapter(var activity: Activity, var mList: ArrayList<ReposItemsModel>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<ReposItemsAdapter.ViewHolder>() {

    private var view: View? = null
    private var viewHolder: ViewHolder? = null
    private var mClickListener: ItemClickListener? = null


    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.customrow_repos_item, parent, false)
        viewHolder = ViewHolder(view!!)

        return viewHolder!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(!mList[position].owner!!.avatar_url.isNullOrEmpty()){
            Picasso.get()
                .load(mList[position].owner!!.avatar_url)
                .into(holder.imgview_owner)
        }

        holder.title_txtView.text = "" + mList[position].name + " | " + mList[position].owner!!.login
        if(!mList[position].created_at.isNullOrEmpty()){
            holder.time_txtView.text = "Updated: " + mList[position].updated_at!!.dropLast(4).replace("T", " ")
        }else{
            holder.time_txtView.visibility = View.GONE
        }
        if(!mList[position].description.isNullOrEmpty()){
            holder.description_txtView.text = "" + mList[position].description!!
        }else{
            holder.description_txtView.visibility = View.GONE
        }
        if(!mList[position].language.isNullOrEmpty()){
            holder.language_txtView.text = " " + mList[position].language!!
        }else{
            holder.language_txtView.visibility = View.GONE
        }

        holder.whole_card_view.setOnClickListener{
            MainActivity.selectedRepos = mList[position]
            val intent = Intent(activity, DetailsActivity::class.java)
            activity.startActivity(intent)
            //activity.finish()
            //activity.overridePendingTransition(androidx.appcompat.R.anim.abc_slide_in_top, androidx.appcompat.R.anim.abc_slide_in_top)
        }


    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(view1: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(view1),
        View.OnClickListener {

        var whole_card_view: RelativeLayout
        var imgview_owner: ImageView
        var title_txtView: TextView
        var time_txtView: TextView
        var description_txtView: TextView
        var language_txtView: TextView

        init {

            //whole_card_view = view1!!.findViewById(R.id.acsrt_whole_card_view)
            whole_card_view = view1!!.findViewById(R.id.whole_row_repos_items)
            imgview_owner = view1!!.findViewById(R.id.imgview_owner_img)
            title_txtView = view1!!.findViewById(R.id.txtview_owner_title)
            time_txtView = view1!!.findViewById(R.id.txtview_owner_time)
            description_txtView = view1!!.findViewById(R.id.txtview_owner_description)
            language_txtView = view1!!.findViewById(R.id.txtview_owner_language)

        }

        override fun onClick(v: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

}