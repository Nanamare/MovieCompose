plugins {
    id("movie.android.library")
    id("kotlinx-serialization")
}

android {
    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org\"")
        buildConfigField("String", "TMDB_IMAGE_URL", "\"https://image.tmdb.org/t/p/w300\"")
        buildConfigField("String", "API_KEY", "\"73f6ff111acbb24ea793d4aa0e1271a1\"")
        buildConfigField("String", "LANGUAGE", "\"en-US\"")
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