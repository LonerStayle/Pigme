package kr.loner.pigme.view.imageurl

 fun imageUrl(image: String): String {
    var uriStringValue = "android.resource://kr.loner.pigme/$image"
    if (image.length > 20) {
        uriStringValue = image
    }
    return uriStringValue

}