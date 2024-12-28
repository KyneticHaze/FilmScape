plugins {
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.jetbrainsKotlin).apply(false)
    alias(libs.plugins.kspCompiler).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
    alias(libs.plugins.googleServices).apply(false)
    id("org.jlleitschuh.gradle.ktlint") version "11.6.0"
}

ktlint {
    debug.set(false)
    android.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    ignoreFailures.set(false)
}
