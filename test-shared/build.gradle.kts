plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
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

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    Deps.UnitTest.unit_test_dependencies.forEach(::api)
    Deps.AndroidTest.android_test_dependencies.forEach(::api)
    Deps.Retrofit.retrofit_dependencies.forEach(::api)
}

tasks.withType<Test> {
    useJUnitPlatform()
}