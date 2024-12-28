package com.furkanhrmnc.filmscape.navigation

/**
 * Ui gezinmelerinin rotasını oluşturan numaralandırma sınıfı buradadır.
 *
 * @param route ile rota yolu belirtilir.
 *
 * @author Furkan Harmancı
 */
enum class Destinations(val route: String) {
    MAIN("main_screen"),
    SETTINGS("settings_screen"),
    TRENDING("popular_screen"),
    HOME("home_screen"),
    MOVIES("movies_screen"),
    TV("tv_screen"),

    AUTH("auth_screen"),
    REGISTER("register_screen"),
    LOGIN("login_screen"),
    

    DETAILS("details_screen"),
    SIMILAR("similar_screen"),
    WATCH_VIDEO("watch_video_screen"),

    FAVORITE("favorite_screen"),
    SEARCH("search_screen"),
    PERSON("person_screen"),
    ACCOUNT("account_screen"),

    PERSON_DETAILS("person_details_screen"),
}