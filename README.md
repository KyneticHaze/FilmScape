<img src="https://github.com/KyneticHaze/FilmScape/blob/master/assets/filmscape_logo.png" alt="FilmScape Logo" height="600" width="800">

# 🎬 FilmScape - Movie Discovery App

FilmScape, kullanıcıların en yeni, popüler, en çok puan alan ve yaklaşan filmleri keşfetmelerine olanak tanıyan modern bir film keşif uygulamasıdır. TMDb (The Movie Database) API'sini kullanarak zengin bir film veri tabanı sunar. Uygulama, Android Jetpack Compose ile tamamen modern ve kullanıcı dostu bir tasarıma sahiptir.

## 📱 Özellikler

- **TMDb API Entegrasyonu**: TMDb API kullanılarak gerçek zamanlı film verileri.
- **Kullanıcı Girişi ve Kayıt**: Firebase Authentication ile güvenli kullanıcı kimlik doğrulama sistemi.
- **Favori ve İzleme Listeleri**: Kullanıcılar, favori ve izleme listeleri oluşturabilir ve bu listeleri Firebase Firestore ile yönetebilir.
- **Infinite Scrolling**: Jetpack Paging ile sayfa yükleme ve kullanıcı deneyimini artırma.
- **Tema Değiştirme**: Jetpack DataStore ile cihaz temasına göre karanlık veya aydınlık mod.
- **Yerleşik Video Oynatıcı**: YoutubePlayerView ile fragman izleme imkanı.
- **Gelişmiş Arama**: Kullanıcıların filmleri türe, yıla ve popülerliğe göre filtreleyebilmesi.
- **Detaylı Film Bilgisi**: Film oyuncuları, yönetmen, süresi, puanı, yorumlar vb.
- **Splash Screen**: Android SplashScreen API ile akıcı bir uygulama başlangıcı.
  
## 🛠️ Kullanılan Teknolojiler ve Kütüphaneler

- **Jetpack Compose**: Modern UI geliştirme.
- **Coil**: Görsellerin hızlı ve verimli yüklenmesi.
- **Ktor**: Ağ istekleri ve veri çekme.
- **Koin**: Dependency Injection (DI) yönetimi.
- **Firebase Authentication**: Kullanıcı kimlik doğrulama.
- **Firebase Firestore**: Gerçek zamanlı veri tabanı.
- **Jetpack Paging**: Sonsuz kaydırma için veri yükleme.
- **Jetpack DataStore**: Kalıcı veri saklama ve tema ayarları.
- **Jetpack Navigation**: Ekranlar arası kolay geçiş.
- **YoutubePlayerView**: Film fragmanlarını oynatma.
- **Jetpack Icons Extended**: UI için kapsamlı ikon seti.
- **Kotlinx Datetime**: Zaman ve tarih işlemleri.
- **SplashScreen API**: Uygulama başlatma ekranı.
- **KSP Compiler**: Kode generasyon işlemleri.

## 🎨 Ekran Görüntüleri

* ?

## 🚀 Başlangıç

### Gereksinimler

- Minimum SDK: 24
- Target SDK: 34
- API Anahtarı: TMDb API anahtarı gereklidir. Bu anahtarı almak için [TMDb](https://www.themoviedb.org/) web sitesine kaydolun.



## 📁 Proje Yapısı
- data: Veri modelleri, veri kaynakları ve repository katmanı.
- di: Koin bağımlılık injeksiyonu yapılandırması.
- domain: İş mantığı, use case'ler ve model tanımları.
- navigation: Uygulama içinde ekranlar arası geçişlerin yönetimi.
- ui: Uygulama ekranları ve Jetpack Compose bileşenleri.
- util: Yardımcı fonksiyonlar ve sabitler.



### Kurulum

1. Bu depoyu klonlayın:
   ```bash
   git clone https://github.com/kullaniciadi/FilmScape.git


## ☕ Katkıda Bulunma
Katkıda bulunmak isterseniz lütfen bir `Pull Request` gönderin veya bir `Issue` açın. Her türlü katkıdan memnuniyet duyarız!

## 📄 Lisans
Bu proje MIT Lisansı ile lisanslanmıştır. Daha fazla bilgi için LICENSE dosyasına göz atın.
