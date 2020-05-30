package com.mvvmproject.listing

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mvvmproject.R
import com.mvvmproject.rest.response.StoryDetails
import com.mvvmproject.util.covertToHumanReadableTime

class StoryViewAdapter(context: Context,
                       private val stories: List<StoryDetails>,
                       private val onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<StoryViewAdapter.ContentViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ContentViewHolder {
        val view = inflater.inflate(R.layout.list_item, viewGroup, false)
        return ContentViewHolder(view)
    }

    override fun onBindViewHolder(contentViewHolder: ContentViewHolder, position: Int) =
        contentViewHolder.bindViews(stories[position], position, onItemClickListener)

    override fun getItemCount() = stories.size

    class ContentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val cvStory: CardView = itemView.findViewById(R.id.cvStory)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvTitleBy: TextView = itemView.findViewById(R.id.tvTitleBy)
        private val titleTime: TextView = itemView.findViewById(R.id.titleTime)

        fun bindViews(storyDetails: StoryDetails, position: Int, onItemClickListener: OnItemClickListener) {
            tvTitle.text = storyDetails.title
            tvTitleBy.append(storyDetails.by)
            titleTime.text = storyDetails.time.covertToHumanReadableTime()
            cvStory.setOnClickListener {
                onItemClickListener.onItemClick(position, storyDetails)
            }
        }
    }
}