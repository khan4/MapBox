package com.example.mapbox.recyclerview

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mapbox.R
import com.example.mapbox.database.UserEntity
import kotlinx.android.synthetic.main.item_view.view.*

class FeedRecyclerAdapter(val userEntity: List<UserEntity>, val onFeedListener: FeedViewHolder.OnFeedClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


     val TAG ="RecyclerAdapter"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FeedViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false),
            onFeedListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is FeedViewHolder ->{
                 holder.populate(userEntity.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d(TAG,"Size of feed lis is ")
        return userEntity.size
    }


    class FeedViewHolder : RecyclerView.ViewHolder,View.OnClickListener{
        val onFeedListener : OnFeedClickListener


        constructor(itemView: View , onFeedListener:OnFeedClickListener) : super(itemView) {
            this.onFeedListener = onFeedListener
            itemView.setOnClickListener(this)
        }


        val tvId  = itemView.tvId
        val tvName = itemView.tvName

        fun populate(userEntity: UserEntity){
            tvId.setText((userEntity.userId).toString())
            tvName.setText(userEntity.userName)



        }

        override fun onClick(position: View?) {
            Log.d(TAG,"This view is called")
            onFeedListener.OnFeedClick(adapterPosition)
        }


         interface OnFeedClickListener{
             fun OnFeedClick(position: Int)
         }

    }
}