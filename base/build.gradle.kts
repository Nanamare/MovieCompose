plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    compileSdk = libs.findVersion("androidCompileSdkVersion").get().toString().toInt()

    defaultConfig {
        testInstrumentationRunner = "com.nanamare.test_shared.MovieTestRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    android {
        buildFeatures {
            compose = true
        }
        composeOptions {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            kotlinCompilerExtensionVersion = libs.findVersion("androidxCompose").get().toString()
        }
    }
}

dependencies {

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")

    implementation(libs.bundles.compose)
    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)

}