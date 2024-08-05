package com.furkanhrmnc.filmscape.util

/**
 * Veri çekmek için gereken verileri toplayan sınıfımız.
 *
 * [API_KEY] - Veri çekerken backend kısmının bizden istediği kimlik
 *
 * [BASE_URL] - İstek atacağımız api ve path'i
 *
 * [IMAGE_URL] - Çekilen verilerin image kısımları bu url'in path'i olarak yazılmış. Bu yüzden image verilerini kullanıcıya gösterirken bu değişkeni önde, image verisini arkada şeklinde yazacağız.
 *
 * @author Furkan Harmancı
 */
object ApiConfig {
    const val API_KEY = "d1c154d38388b545b29257de290d6484"
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
}