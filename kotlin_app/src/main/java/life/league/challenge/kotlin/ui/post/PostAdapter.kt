package life.league.challenge.kotlin.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import life.league.challenge.kotlin.databinding.ItemPostBinding
import life.league.challenge.kotlin.ui.model.User

class PostAdapter : ListAdapter<User, PostAdapter.ListViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
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
        fun bind(user: User) {
            itemBinding.run {
                userThumbnail.load(user.thumbnail) {
                    transformations(CircleCropTransformation())
                }
                userName.text = user.name
            }
        }
    }

}