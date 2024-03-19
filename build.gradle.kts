buildscript {
    dependencies {
        classpath(libs.google.services)
    }
}
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlin) apply false
    alias(libs.plugins.kspCompiler) apply false
    alias(libs.plugins.hiltAndroid) apply false
}