package com.furkanhrmnc.filmscape.common

object FilmException {
    const val EXCEPTION_400 = "Gönderilen istek hatalı. Lütfen bilgilerinizi kontrol edin ve tekrar deneyin."
    const val EXCEPTION_401 = "Giriş yapmanız gerekiyor. Lütfen kullanıcı adınızı ve şifrenizi girerek tekrar deneyin."
    const val EXCEPTION_403 = "Bu içeriğe erişiminiz yok."
    const val EXCEPTION_404 = "İstediğiniz kaynak bulunamadı."
    const val EXCEPTION_500 = "Sunucu hatası. Lütfen daha sonra tekrar deneyin."
    const val EXCEPTION_503 = "Sunucu şu anda kullanılamıyor. Lütfen daha sonra tekrar deneyin."

    const val EXCEPTION_IO = "Ağ hatası. Lütfen internet bağlantınızı kontrol edin ve tekrar deneyin."
}