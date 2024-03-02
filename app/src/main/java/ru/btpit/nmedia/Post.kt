package ru.btpit.nmedia

import java.util.Date

data class Post (
    var id:Int,
    val header:String,
    val content: String,
    val dataTime:Date,
    var amountlike:Int,
    var amountrepost:Int,
    var isLike:Boolean,
    var amountglaza: Int,
    var isRepos:Boolean
)


