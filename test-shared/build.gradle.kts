plugins {
    id("movie.android.library")
}

android {
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