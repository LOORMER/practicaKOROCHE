package ru.btpit.nmedia

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.Calendar
import kotlin.random.Random

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id:Int)
    fun shareById(id: Int)
    fun removeById(id: Int)
    fun addPost(post: Post,string: String)
    fun editById(id: Int, header: String, content: String, url:String)
}

class PostRepositoryInMemoryImpl(context: Context) : PostRepository {


    private val prefs =  context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private val key = "posts"
    private var nxtId = 1
    private var posts = getPosts()
    private val data = MutableLiveData(posts)



    override fun getAll(): LiveData<List<Post>> = data
    override fun likeById(id:Int) {
        posts = posts.map{
            if(it.id != id) it else{
                if (it.isLike)
                    it.amountlike--
                else
                    it.amountlike++
                it.copy(isLike = !it.isLike)
            }
        }
        data.value = posts

    }
    override fun shareById(id:Int) {
        posts = posts.map {
            if(it.id != id)
                it
            else
                it.copy(amountrepost = it.amountrepost+ 1)
        }
        data.value = posts

    }
    override fun removeById(id: Int) {
        posts = posts.filter { it.id != id }
        data.value = posts

    }
    override fun addPost(post: Post,string: String) { // Функция добавоения (должна быть объявлена в Post
        posts = listOf(
            post.copy(
                id = 0,
                dataTime = Calendar.getInstance().time,
                content = string,
                amountlike = randomNumb(),
                amountrepost = randomNumb(),
                amountglaza = randomNumb(),
                isLike = false
            )
        ) + posts
        data.value = posts

    }
    override fun editById(id: Int, header: String, content: String, url: String) {
        posts = posts.map {
            if(it.id != id)
                it
            else {
                if (it.id == 0 ) it.id = nextId(posts)
                it.copy(header = header, content = content)
            }
        }
        data.value = posts

    }


}


class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryInMemoryImpl(application)
    val data = repository.getAll()
    private val edited = MutableLiveData(getEmptyPost())
    fun addPost(string: String){
        edited.value?.let {
            repository.addPost(it,string)
        }
        edited.value = getEmptyPost()
    }
    fun editById(id: Int,header:String,content:String,url:String){
        repository.editById(id,header,content,url)
    }
    fun likeById(id:Int) = repository.likeById(id)
    fun shareById(id:Int) = repository.shareById(id)
    fun removeById(id:Int) = repository.removeById(id)
}
fun nextId(posts:List<Post>):Int{
    var id = 1
    posts.forEach{ _ ->
        posts.forEach{
            if (it.id==id) id=it.id+1
        }
    }

    return id
}

fun randomNumb():Int
{
    return when (Random.nextInt(1,3))
    {
        1 -> Random.nextInt(0,1_000)
        2 -> Random.nextInt(1_000,1_000_000)
        3 -> Random.nextInt(1_000_000,1_000_000_000)
        else -> Random.nextInt(0, Int.MAX_VALUE)
    }
}