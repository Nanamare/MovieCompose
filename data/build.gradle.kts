import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("kotlin-kapt")
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "API_KEY", "\"73f6ff111acbb24ea793d4aa0e1271a1\"")
        buildConfigField("String", "LANGUAGE", "\"en-US\"")
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

    testImplementation("junit:junit:4.13.2")
}