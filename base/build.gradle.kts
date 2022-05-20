plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    compileSdk = Versions.compile_version

    defaultConfig {
        testInstrumentationRunner = "com.nanamare.test_shared.MovieTestRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    android {
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = Versions.Compose.compose_version
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    Deps.Compose.compose_dependencies.forEach(::implementation)
    implementation(Deps.lifecycle_runtime_ktx)
    Deps.Accompanist.accompanist_dependencies.forEach(::implementation)

    Deps.Hilt.hilt_dependencies.forEach(::implementation)
    kapt(Deps.Hilt.dagger_hilt_compiler)

}