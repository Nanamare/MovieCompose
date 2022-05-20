plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("kotlin-kapt")
}

android {
    compileSdk = Versions.compile_version

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org\"")
        buildConfigField("String", "TMDB_IMAGE_URL", "\"https://image.tmdb.org/t/p/w300\"")
        buildConfigField("String", "API_KEY", "\"73f6ff111acbb24ea793d4aa0e1271a1\"")
        buildConfigField("String", "LANGUAGE", "\"en-US\"")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":domain"))

    Deps.Retrofit.retrofit_dependencies.forEach(::implementation)

    implementation(Deps.dagger)
    implementation(Deps.kotlin_coroutines)

    Deps.Room.room_dependencies.forEach(::implementation)
    kapt(Deps.Room.room_compiler)
}