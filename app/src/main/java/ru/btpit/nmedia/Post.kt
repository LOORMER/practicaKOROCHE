package ru.btpit.nmedia

data class Post (
    val id:Long,
    val header:String,
    val content: String,
    val dataTime:String,
    var amountlike:Int,
    var amountrepost:Int,
    val amountviews: Int,
    var isLike:Boolean,
    var isRepos:Boolean
)


