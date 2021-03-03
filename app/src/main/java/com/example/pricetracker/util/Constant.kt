package com.example.pricetracker.util

import com.example.pricetracker.ui.detail.model.Image

object Constant {

    const val DATABASE_NAME = "tracker-database"

    const val BASE_URL = "http://192.168.0.107:8000/"

    val IMAGE_STORAGE: HashMap<Int, String> = hashMapOf(
        0 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/0-0.jpg",
        1 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/1-0.jpg",
        2 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/2-0.jpg",
        3 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/3-0.jpg",
        4 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/4-0.jpg",
        5 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/5-0.jpg",
        6 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/6-0.jpg",
        7 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/7-0.jpg",
        8 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/8-0.jpg",
        9 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/9-0.jpg",
        10 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/10-0.jpg",
        11 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/11-0.jpg",
        12 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/12-0.jpg",
        13 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/13-0.jpg",
        14 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/14-0.jpg",
        15 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/15-0.jpg",
        16 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/16-0.jpg",
        17 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/17-0.jpg",
        18 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/18-0.jpg",
        19 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/19-0.jpg",
        20 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/20-0.jpg",
        21 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/21-0.jpg",
        22 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/22-0.jpg",
        23 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/23-0.jpg",
        24 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/24-0.jpg",
        25 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/25-0.jpg",
        26 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/26-0.jpg",
        27 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/27-0.jpg",
        28 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/28-0.jpg",
        29 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/29-0.jpg",
        30 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/30-0.jpg",
        31 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/31-0.jpg",
        32 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/32-0.jpg",
        33 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/33-0.jpg",
        34 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/34-0.jpg",
        35 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/35-0.jpg",
        36 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/36-0.jpg",
        37 to "https://storage.googleapis.com/material-vignettes.appspot.com/image/37-0.jpg"
    )

    const val SULPAK = "Sulpak"
    const val TECHNODOM = "TechnoDom"
    const val MECHTA = "Mechta"
    const val BELYIVETER = "BelyiVeter"

    val URLS_OF_SHOPS = hashMapOf<String, String>(
        SULPAK to "https://www.sulpak.kz/",
        TECHNODOM to "https://www.technodom.kz/",
        MECHTA to "https://www.mechta.kz/",
        BELYIVETER to "https://shop.kz/"
    )

    val TYPES_FINISH = listOf("Graphite", "Glass", "Matte")

    fun getImages(): List<Image> {
        val imageList = mutableListOf<Image>()
        for (i in 0 until 3) {
            val randomInd = (0 until IMAGE_STORAGE.size).random()
            val imageUrl = IMAGE_STORAGE[randomInd]
            imageList.add(Image(i, imageUrl!!))
        }
        return imageList
    }

}