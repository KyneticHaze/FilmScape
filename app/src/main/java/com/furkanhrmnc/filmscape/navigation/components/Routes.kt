package com.furkanhrmnc.filmscape.navigation.components

/**
 * Ui gezinmelerinin rotasını oluşturan numaralandırma sınıfı buradadır.
 *
 * @param route ile rota yolu belirtilir.
 *
 * @author Furkan Harmancı
 */
enum class Routes(val route: String) {
    MAIN("main_screen"),
    MOVIES("movies_screen"),
    DETAILS("details_screen"),
    SIMILAR("similar_screen"),
    ACTORS("actors_screen"),
    TV("tv_screen"),
    ACCOUNT("account_screen"),
    SEARCH("search_screen")
}