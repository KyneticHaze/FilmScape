# Genel Ayarlar

# Tüm logları kaldır
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** e(...);
    public static *** i(...);
    public static *** w(...);
}

# Genel olarak kullanılan kütüphaneleri koru
-keep class androidx.** { *; }
-keep class com.google.** { *; }
-keep class kotlin.** { *; }
-keep class kotlinx.** { *; }

# Ktor için ağ isteklerini koru
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**

# DataStore için ayarlar
-keep class androidx.datastore.** { *; }
-dontwarn androidx.datastore.**

# Kotlinx Datetime için ayarlar
-keep class kotlinx.datetime.** { *; }
-dontwarn kotlinx.datetime.**

# SplashScreen API için ayarlar
-keep class androidx.core.splashscreen.** { *; }
-dontwarn androidx.core.splashscreen.**

# Koin için ayarlar
-keep class org.koin.** { *; }
-dontwarn org.koin.**

# Firebase SDK için gerekli kurallar
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**

# Kullanılmayan kodlar için ayarlar
-dontnote **.R
-dontwarn **.R
-ignorewarnings

-keepclassmembers class com.furkanhrmnc.filmscape.domain.model.Media {
    public <init>(...);
}