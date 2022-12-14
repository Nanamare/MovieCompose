plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    compileSdk = libs.findVersion("androidCompileSdkVersion").get().toString().toInt()

    defaultConfig {
        applicationId = "com.nanamare.movie"
        minSdk = libs.findVersion("androidMinSdkVersion").get().toString().toInt()
        targetSdk = libs.findVersion("androidTargetSdkVersions").get().toString().toInt()
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        // Treat all Kotlin warnings as errors (disabled by default)
        // Override by setting warningsAsErrors=true in your ~/.gradle/gradle.properties
        val warningsAsErrors: String? by project
        allWarningsAsErrors = warningsAsErrors.toBoolean()

        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=kotlin.RequiresOptIn",
            // Enable experimental coroutines APIs, including Flow
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlinx.coroutines.FlowPreview",
            "-opt-in=kotlin.Experimental",
        )

        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    kapt {
        correctErrorTypes = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        kotlinCompilerExtensionVersion = libs.findVersion("androidxCompose").get().toString()
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
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)
    kaptAndroidTest(libs.hilt.compiler)
    implementation(libs.bundles.compose)
    implementation(libs.timber)
    implementation(libs.coil)
    implementation(libs.lottie.compose)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.bundles.room)
    kapt(libs.room.compiler)

}