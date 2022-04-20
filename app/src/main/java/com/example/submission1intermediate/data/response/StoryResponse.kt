package com.example.submission1intermediate.data.response


data class StoryResponse(
    val listStory:ArrayList<ListStory>,
    val error: Boolean,
    val message: String
)
