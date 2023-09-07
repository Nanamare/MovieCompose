plugins {
    id("movie.android.library")
}

android {
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
    namespace = "com.nanamare.test_shared"
}

dependencies {
    api(libs.bundles.unittest)
    api(libs.bundles.android.unittest)
    api(libs.bundles.retrofit)
}

tasks.withType<Test> {
    useJUnitPlatform()
}