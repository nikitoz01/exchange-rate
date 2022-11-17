
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.3")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id ("com.android.application") version "7.3.1" apply false
    id ("com.android.library")version "7.3.1" apply false
    id ("org.jetbrains.kotlin.android") version "1.7.10" apply false

    id("com.google.dagger.hilt.android") version "2.44" apply false

    id("com.google.devtools.ksp") version "1.7.10-1.0.6"
}