package com.example.submission1intermediate.background.response


data class StoryResponse(
    val listStory:ArrayList<ListStory>,
    val error: Boolean,
    val message: String
)
