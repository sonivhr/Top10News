package com.newsapp.userexperience.listing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R
import com.newsapp.databinding.ListItemBinding
import com.newsapp.rest.response.StoryDetails

class StoryViewAdapter(stories: List<StoryDetails>,
                       private val onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<StoryViewAdapter.ContentViewHolder>() {

    private val listStories = mutableListOf<StoryDetails>()

    init {
        listStories.addAll(stories)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ContentViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val listItemBinding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, viewGroup, false)
        return ContentViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(contentViewHolder: ContentViewHolder, position: Int) =
        contentViewHolder.bindViews(listStories[position], position, onItemClickListener)

    override fun getItemCount() = listStories.size

    fun addStories(newStories: List<StoryDetails>) {
        val itemCount = itemCount
        listStories.addAll(newStories)
        notifyItemInserted(itemCount)
    }

    class ContentViewHolder(private val listItemBinding: ListItemBinding):
        RecyclerView.ViewHolder(listItemBinding.root) {

        private val cvStory: CardView = listItemBinding.root.findViewById(R.id.cvStory)

        fun bindViews(storyDetails: StoryDetails, position: Int, onItemClickListener: OnItemClickListener) {
            listItemBinding.storyDetails = storyDetails
            cvStory.setOnClickListener {
                onItemClickListener.onItemClick(position, storyDetails)
            }
        }
    }
}