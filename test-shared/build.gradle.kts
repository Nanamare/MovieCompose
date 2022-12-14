plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
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

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    api(libs.bundles.unittest)
    api(libs.bundles.android.unittest)
    api(libs.bundles.retrofit)
}

tasks.withType<Test> {
    useJUnitPlatform()
}