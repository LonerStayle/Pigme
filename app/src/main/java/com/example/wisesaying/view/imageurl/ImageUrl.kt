package com.example.wisesaying.view.imageurl

 fun imageUrl(image: String): String {
    var uriStringValue = "android.resource://com.example.wisesaying/$image"
    if (image.length > 20) {
        uriStringValue = image
    }
    return uriStringValue

}