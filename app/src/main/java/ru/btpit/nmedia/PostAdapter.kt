package ru.btpit.nmedia

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.btpit.nmedia.databinding.PostCardBinding
import com.google.android.material.snackbar.Snackbar


class PostDiffCallback : DiffUtil.ItemCallback<Post>(){
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return  oldItem == newItem
    }

}
class PostViewHolder(private val binding: PostCardBinding)
    :RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post,listener: PostAdapter.Listener) {
        binding.apply {

            textViewHeader.text = post.header
            editTextHeader.setText(post.header)

            textViewDateTime.text = post.dataTime.toString().split("GMT")[0]

            textViewContent.text = post.content
            editTextContent.setText(post.content)

            textViewLike.text = convertToString(post.amountlike)
            textViewRepost.text = convertToString(post.amountrepost)
            textViewGlaza.text = convertToString(post.amountglaza)

            imagebutnlike.setImageResource(if (post.isLike) R.drawable.redlikes else R.drawable.likes)



            imagebutnlike.setOnClickListener {
                listener.onClickLike(post)
            }
            imagebutnRepost.setOnClickListener {
                listener.onClickShare(post)
            }
            imageViewmenu.setOnClickListener { //Устанавливаю слушатель на три точки
                listener.onClickMore(post,it,binding) //Вызываю функцию из MainActivity
            }
            buttonSave.setOnClickListener {
                listener.saveEditPost(post,binding)
            }

            if (post.id==0) listener.editModeOn(binding, "" )
        }
    }
}

@Suppress("SpellCheckingInspection")
fun getURLImageVideo(url:String):String{
    return  "https://img.youtube.com/vi/${if(url.split("v=").lastIndex == 1) url.split("v=")[1]
    else url.split("?si")[0].split("/").last()}/sddefault.jpg"

}
class PostAdapter(
    private val listener: Listener,
):ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }
    override fun onBindViewHolder(holder: PostViewHolder, position:Int){
        val post = getItem(position)
        holder.bind(post, listener)
    }

    interface Listener{
        fun onClickLike(post: Post)
        fun onClickShare(post: Post)
        fun onClickMore(post:Post, view: View,binding: PostCardBinding)
        fun cancelEditPost(post:Post,binding: PostCardBinding)
        fun saveEditPost(post:Post, binding: PostCardBinding)
        fun editModeOn(binding: PostCardBinding,content:String)
    }
}
private fun convertToString(count:Int):String{
    return when(count){
        in 0..<1_000 -> count.toString()
        in 1000..<1_100-> "1K"
        in 1_100..<10_000 -> ((count/100).toFloat()/10).toString() + "K"
        in 10_000..<1_000_000 -> (count/1_000).toString() + "K"
        in 1_000_000..<1_100_000 -> "1M"
        in 1_100_000..<10_000_000 -> ((count/100_000).toFloat()/10).toString() + "M"
        in 10_000_000..<1_000_000_000 -> (count/1_000_000).toString() + "M"
        else -> "ꚙ"
    }
}