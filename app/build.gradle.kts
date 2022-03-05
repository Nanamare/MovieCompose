plugins {
    id("kotlin-kapt")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.nanamare.movie"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org\"")
        buildConfigField("String", "TMDB_IMAGE_URL", "\"https://image.tmdb.org/t/p/w300\"")
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

    kapt {
        correctErrorTypes = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.compose_version
    }

}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":base"))

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    Deps.Retrofit.retrofit_dependencies.forEach(::implementation)

    Deps.Hilt.hilt_dependencies.forEach(::implementation)
    kapt(Deps.Hilt.dagger_hilt_compiler)

    Deps.Compose.compose_dependencies.forEach(::implementation)

    implementation(Deps.timber)
    implementation(Deps.coil_compose)
    implementation(Deps.lottie_compose)
    implementation(Deps.swipe_refresh_compose)

    Deps.Room.room_dependencies.forEach(::implementation)
    kapt(Deps.Room.room_compiler)
}