plugins {
    id("movie.android.library")
    id("movie.android.library.compose")
}

android {
    namespace = "com.nanamare.base"
}

dependencies {
    implementation(project(":test-shared"))
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.bundles.compose)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.bundles.hilt)
    implementation(libs.timber)
    kapt(libs.hilt.compiler)
    debugImplementation(libs.bundles.debugging)
}
