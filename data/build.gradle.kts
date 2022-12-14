plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("kotlin-kapt")
}

android {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    compileSdk = libs.findVersion("androidCompileSdkVersion").get().toString().toInt()

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
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":test-shared"))
    implementation(libs.bundles.retrofit)
    implementation(libs.coroutine)
    implementation(libs.bundles.room)
    kapt(libs.room.compiler)
}