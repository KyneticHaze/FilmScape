package com.furkanhrmnc.filmscape.navigation.components

/**
 * Ui gezinmelerinin rotasını oluşturan numaralandırma sınıfı buradadır.
 *
 * @param route ile rota yolu belirtilir.
 *
 * @author Furkan Harmancı
 */
enum class Destinations(val route: String) {
    MAIN("main_screen"),
    DETAILS("details_screen"),
    POPULAR("popular_screen"),
    SIMILAR("similar_screen"),
    FAVORITE("favorite_screen"),
    SEARCH("search_screen")
}