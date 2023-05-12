plugins {
    id("movie.android.library")
    id("movie.android.library.compose")
}

dependencies {
    implementation(project(":test-shared"))
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.bundles.compose)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)
    debugImplementation(libs.bundles.debugging)
}