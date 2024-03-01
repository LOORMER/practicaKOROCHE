package ru.btpit.nmedia

import android.content.Intent
import android.provider.Settings.Global.getString
import androidx.core.content.contentValuesOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.Calendar
import kotlin.random.Random

interface PostRepository {
    fun get(): LiveData<List<Post>>
    fun likeById(id:Long)

    fun removeById(id: Long)
    fun save(post: Post)
    fun edit(post: Post)
}

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextId = 1L
    private var posts = listOf(
        Post(
            id = nextId++,
            header = "ГПБОУ ВО БТПИТ",
            content = "15 февраля на базе 1 и 3 корпусов ГБПОУ ВО «БТПИТ» прошли торжественные митинги, посвященные 35-й годовщине со дня вывода советских войск из Республики Афганистан с поднятием государственного флага и возложением цветов к «Деревьям Памяти».\\nКаштаны были посажены на территории учебного корпуса № 1 и 3 в честь воинов-интернационалистов, которые учились в нашем техникуме.\\nСтуденты почтили память участников войн и конфликтов минутой молчания.",
            dataTime = "21 февраля в 19:12",
            isLike = false,
            amountlike = 999,
            amountrepost = 15,
            amountviews = 500,
            isRepos = false
        ),
        Post(
            id = nextId++,
            header = "ГПБОУ ВО БТПИТ",
            content = "Преподаватель Борисоглебского техникума промышленных и информационных технологий Гребенникова Лариса Владимировна одно из занятий по дисциплине «Краеведение» со студентами 1 курсов специальностей «Дошкольное образование» и «Коррекционная педагогика в начальном образовании» провела в МБУК БГО Борисоглебском историко-художественном музее.",
            dataTime = "27 Февраля в 12:56",
            isLike = false,
            amountlike = 0,
            amountrepost = 0,
            amountviews = 0,
            isRepos = false
        ),

        )

    private val data = MutableLiveData(posts)
    private val edited = MutableLiveData(empty)
    override fun get(): LiveData<List<Post>> = data
    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else {
                if (it.isLike)
                    it.amountlike--
                else
                    it.amountlike++
                it.copy(isLike = !it.isLike)
            }
        }
        data.value = posts
    }


    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id}
        data.value = posts
    }
    override fun save(post: Post) {
        if (post.id == 0L) {
            // TODO: remove hardcoded author & published
            posts = listOf(
                post.copy(
                    id = nextId++,
                    header = "Me",
                    isLike = false,
                    isRepos = false,
                    dataTime = "now",
                    amountviews = 0
                )
            ) + posts
            data.value = posts
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
    }
    override fun edit(post: Post) {
        edited.value = post
    }
}
private val empty = Post(
    id = 0,
    content = "",
    amountviews = 0,
    amountlike = 0,
    amountrepost = 0,
    dataTime = "",
    header = "",
    isLike = false,
    isRepos = false
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    val edited = MutableLiveData(empty)
    fun edit(post: Post) {
        edited.value = post
    }
    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }
    fun changeContent(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (it.content == text) {
                return
            }
            edited.value = it.copy(content = text)
        }
    }
    fun likeById(id: Long) = repository.likeById(id)

    fun removeById(id : Long) = repository.removeById(id)
}