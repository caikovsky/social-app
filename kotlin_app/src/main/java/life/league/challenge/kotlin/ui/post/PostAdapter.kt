package life.league.challenge.kotlin.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import life.league.challenge.kotlin.databinding.ItemPostBinding
import life.league.challenge.kotlin.ui.model.Post

class PostAdapter : ListAdapter<Post, PostAdapter.ListViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.postId == newItem.postId
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemBinding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ListViewHolder(
        private val itemBinding: ItemPostBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(post: Post) {
            itemBinding.run {
                userThumbnail.load(post.user.thumbnail) { transformations(CircleCropTransformation()) }
                userName.text = post.user.name
                postTitle.text = post.title
                postBody.text = post.body
            }
        }
    }

}