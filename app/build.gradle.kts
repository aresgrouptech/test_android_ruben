plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    kotlin("kapt")
}

android {
    namespace = "com.ridery.here"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ridery.here"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    dataBinding {
        enable = true
    }
}

dependencies {
    // Basic
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0") // Material
    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // ConstraintLayout
    implementation("androidx.drawerlayout:drawerlayout:1.2.0") //DrawerLayout
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01") // Swipe Refresh

    // Kotlin & Coroutines
    implementation("androidx.activity:activity-ktx:1.8.2")  // Kotlin Activity
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.2") // Coroutines
    implementation("androidx.databinding:databinding-runtime:8.2.0")

    //Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2") // ViewModel
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2") // LiveData

    //Room
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1") // Kotlin plugins and coroutines for Room

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // GSON Converter

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")

    implementation("org.osmdroid:osmdroid-android:6.1.17")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}