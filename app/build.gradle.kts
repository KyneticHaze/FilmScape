plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlin)
    alias(libs.plugins.kspCompiler)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization") version "2.0.21"
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

        buildConfigField("String", "API_KEY", "\"3c70d93bbd9bad4986f886136e58dca7\"")
        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/\"")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    composeCompiler {
        reportsDestination = layout.buildDirectory.dir("compose-compiler")
    }

    buildFeatures {
        compose = true
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
    implementation(libs.koin.core)
    // Koin Android
    implementation(libs.insert.koin.koin.android)
    // Koin Jetpack Compose
    implementation(libs.koin.androidx.compose)

    // Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.logging)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.serialization.kotlinx.json)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.paging)

    // Navigation Compose
    implementation(libs.navigation.compose)

    // Coil
    implementation(libs.coil.compose)

    // Media3
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)

    // Extended Icon
    implementation(libs.androidx.material.icons.extended)

    // kotlinx.datetime
    implementation(libs.kotlinx.datetime)

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
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}