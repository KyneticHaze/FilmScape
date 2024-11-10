![FilmScape Logo](https://github.com/KyneticHaze/FilmScape/blob/master/assets/filmscape_logo.png)

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
<h3>App Giriş Ekranı</h3>
<div style="display: flex; gap: 10px;">
  <img src="https://github.com/KyneticHaze/FilmScape/blob/master/assets/screenshots/splash.png" alt="Anasayfa Ekranı" width="300">
</div>

<h3>Giriş/Kayıt Ekranı</h3>
<div style="display: flex; gap: 10px;">
  <img src="https://github.com/KyneticHaze/FilmScape/blob/master/assets/screenshots/login.png" alt="Anasayfa Ekranı" width="300">
<img src="https://github.com/KyneticHaze/FilmScape/blob/master/assets/screenshots/loginnig.png" alt="Anasayfa Ekranı" width="300">
<img src="https://github.com/KyneticHaze/FilmScape/blob/master/assets/screenshots/register.png" alt="Anasayfa Ekranı" width="300">
</div>

<h3>Anasayfa ve Film/Diziler Ekranı</h3>
<div style="display: flex; gap: 10px;">
  <img src="https://github.com/KyneticHaze/FilmScape/blob/master/assets/screenshots/main.png" alt="Anasayfa Ekranı" width="220">
<img src="https://github.com/KyneticHaze/FilmScape/blob/master/assets/screenshots/trending.png" alt="Anasayfa Ekranı" width="220">
<img src="https://github.com/KyneticHaze/FilmScape/blob/master/assets/screenshots/movies.png" alt="Anasayfa Ekranı" width="220">
<img src="https://github.com/KyneticHaze/FilmScape/blob/master/assets/screenshots/tv_series.png" alt="Anasayfa Ekranı" width="220">
</div>

<h3>Detay/Video/Önerilenler Ekranı</h3>
<div style="display: flex; gap: 10px;">
  <img src="https://github.com/KyneticHaze/FilmScape/blob/master/assets/screenshots/detail.png" alt="Anasayfa Ekranı" width="300">
<img src="https://github.com/KyneticHaze/FilmScape/blob/master/assets/screenshots/video.png" alt="Anasayfa Ekranı" width="300">
<img src="https://github.com/KyneticHaze/FilmScape/blob/master/assets/screenshots/recommendation.png" alt="Anasayfa Ekranı" width="300">
</div>

<h3>Search ve Aktör/Detay Ekranı</h3>
<div style="display: flex; gap: 10px;">
  <img src="https://github.com/KyneticHaze/FilmScape/blob/master/assets/screenshots/search.png" alt="Anasayfa Ekranı" width="300">
<img src="https://github.com/KyneticHaze/FilmScape/blob/master/assets/screenshots/actors.png" alt="Anasayfa Ekranı" width="300">
<img src="https://github.com/KyneticHaze/FilmScape/blob/master/assets/screenshots/actor_details.png" alt="Anasayfa Ekranı" width="300">
</div>

<h3>Favori Ekranı</h3>
<div style="display: flex; gap: 10px;">
  <img src="https://github.com/KyneticHaze/FilmScape/blob/master/assets/screenshots/favorites.png" alt="Anasayfa Ekranı" width="300">
</div>

<h3>Hesap Ekranı</h3>
<div style="display: flex; gap: 10px;">
  <img src="https://github.com/KyneticHaze/FilmScape/blob/master/assets/screenshots/account.png" alt="Anasayfa Ekranı" width="300">
<img src="https://github.com/KyneticHaze/FilmScape/blob/master/assets/screenshots/account_two.png" alt="Anasayfa Ekranı" width="300">
</div>

<h3>Ayarlar Ekranı</h3>
<div style="display: flex; gap: 10px;">
  <img src="https://github.com/KyneticHaze/FilmScape/blob/master/assets/screenshots/settings.png" alt="Anasayfa Ekranı" width="300">
</div>

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
