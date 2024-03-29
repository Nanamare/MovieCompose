plugins {
    id("movie.android.application")
    id("movie.android.application.compose")
    id("movie.android.hilt")
}

android {
    namespace = "com.nanamare.movie"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":base"))
    implementation(project(":test-shared"))
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.androidx.constraintLayout)
    implementation(libs.bundles.retrofit)
    implementation(libs.timber)
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicator)
    implementation(libs.startup)
}
