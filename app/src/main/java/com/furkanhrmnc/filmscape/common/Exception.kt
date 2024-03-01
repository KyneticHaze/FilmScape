package com.furkanhrmnc.filmscape.common

object Exception {
    const val exception400 = "Gönderilen istek hatalı. Lütfen bilgilerinizi kontrol edin ve tekrar deneyin."
    const val exception401 = "Giriş yapmanız gerekiyor. Lütfen kullanıcı adınızı ve şifrenizi girerek tekrar deneyin."
    const val exception403 = "Bu içeriğe erişiminiz yok."
    const val exception404 = "İstediğiniz kaynak bulunamadı."
    const val exception500 = "Sunucu hatası. Lütfen daha sonra tekrar deneyin."
    const val exception503 = "Sunucu şu anda kullanılamıyor. Lütfen daha sonra tekrar deneyin."

    const val exceptionIO = "Ağ hatası. Lütfen internet bağlantınızı kontrol edin ve tekrar deneyin."
}