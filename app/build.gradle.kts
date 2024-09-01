plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlin)
    alias(libs.plugins.kspCompiler)
    id("kotlin-parcelize")
}

android {
    namespace = "com.furkanhrmnc.filmscape"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.furkanhrmnc.filmscape"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Paging
    implementation(libs.androidx.paging.compose)

    // Koin Core
    implementation (libs.koin.core)

    // Koin Android
    implementation (libs.insert.koin.koin.android)

    // Koin Jetpack Compose
    implementation(libs.koin.androidx.compose)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Interceptor
    implementation(libs.logging.interceptor)

    // Navigation Compose
    implementation(libs.hilt.navigation.compose)
    implementation(libs.navigation.compose)

    // Coil
    implementation(libs.coil.compose)

    // Extended Icon
    implementation(libs.androidx.material.icons.extended)

    // kotlinx.datetime
    implementation(libs.kotlinx.datetime)

    // YoutubePlayer
    implementation(libs.youtube.player.view)

    // Jetpack Media3
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.ui)

    // Lifecycle Compose
    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.material.android)
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    //noinspection UseTomlInstead
    implementation("androidx.compose.material3:material3")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}