package com.furkanhrmnc.filmscape.navigation.components

/**
 * Ui gezinmelerinin rotasını oluşturan numaralandırma sınıfı buradadır.
 *
 * @param route ile rota yolu belirtilir.
 */
enum class Routes(val route: String) {
    MAIN("main_screen"),
    MOVIES("movies_screen"),
    DETAILS("details_screen"),
    ACTORS("actors_screen"),
    TV("tv_screen"),
    SEARCH("search_screen")
}