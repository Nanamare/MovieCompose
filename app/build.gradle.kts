plugins {
    id("movie.android.application")
    id("movie.android.hilt")
    id("movie.android.application.compose")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    defaultConfig {
        applicationId = "com.nanamare.movie"
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "com.nanamare.test_shared.MovieTestRunner"
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

    kapt {
        correctErrorTypes = true
    }

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
    kapt(libs.room.compiler)
}