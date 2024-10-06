package com.example.teaserflix.Models

import com.example.teaserflix.Models.Category
import com.example.teaserflix.Models.Language

data class Videos(val id: Int,
                  val title: String,
                  val description: String,
                  val banner: String,
                  val trailer: String,
                  val category: Category,
                  val language: Language
)