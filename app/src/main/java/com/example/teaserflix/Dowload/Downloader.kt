package com.example.teaserflix.Dowload

interface Downloader {
    fun downloadfile(url:String) : Long
}