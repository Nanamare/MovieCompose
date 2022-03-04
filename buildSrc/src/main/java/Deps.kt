object Deps {

    const val gradle = "com.android.tools.build:gradle:${Versions.gradle_version}"
    const val kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_version}"
    const val hilt_plugin =
        "com.google.dagger:hilt-android-gradle-plugin:${Versions.Hilt.dagger_hilt_version}"

    object Retrofit {
        private const val retrofit =
            "com.squareup.retrofit2:retrofit:${Versions.Retrofit.retrofit_version}"
        private const val kotlinx_serialization_json =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2"
        private const val kotlinx_serialization_retrofit_converter =
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
        private const val kotlinx_serialization_core =
            "org.jetbrains.kotlinx:kotlinx-serialization-core:1.3.0"
        private const val okhttp =
            "com.squareup.okhttp3:okhttp:${Versions.Retrofit.ok_http_version}"
        private const val okhttp_logging_interceptor =
            "com.squareup.okhttp3:logging-interceptor:${Versions.Retrofit.ok_http_version}"

        val retrofit_dependencies = listOf(
            retrofit,
            kotlinx_serialization_retrofit_converter,
            kotlinx_serialization_json,
            kotlinx_serialization_core,
            okhttp,
            okhttp_logging_interceptor
        )
    }

    object Hilt {
        private const val dagger_hilt_android =
            "com.google.dagger:hilt-android:${Versions.Hilt.dagger_hilt_version}"
        private const val dagger_hilt_lifecycle_viewmodel =
            "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.Hilt.hilt_lifecycle_viewmodel_version}"

        const val dagger_hilt_compiler =
            "com.google.dagger:hilt-compiler:${Versions.Hilt.dagger_hilt_version}"
        private const val dagger_hilt_navigation_compose =
            "androidx.hilt:hilt-navigation-compose:${Versions.Hilt.hilt_navigation_compose}"

        val hilt_dependencies =
            listOf(
                dagger_hilt_android,
                dagger_hilt_lifecycle_viewmodel,
                dagger_hilt_navigation_compose
            )
    }

    const val timber = "com.jakewharton.timber:timber:5.0.1"

    object Compose {
        private const val compose_activity_compose =
            "androidx.activity:activity-compose:${Versions.Compose.compose_version}}"
        private const val compose_runtime =
            "androidx.compose.runtime:runtime:${Versions.Compose.compose_version}"
        private const val compose_runtime_livedata =
            "androidx.compose.runtime:runtime-livedata:${Versions.Compose.compose_version}"
        private const val compose_ui = "androidx.compose.ui:ui:${Versions.Compose.compose_version}"
        private const val compose_ui_tooling =
            "androidx.compose.ui:ui-tooling:${Versions.Compose.compose_version}"
        private const val compose_ui_tooling_preview =
            "androidx.compose.ui:ui-tooling-preview:${Versions.Compose.compose_version}"
        private const val compose_foundation_layaout =
            "androidx.compose.foundation:foundation-layout:${Versions.Compose.compose_version}"
        private const val compose_foundation =
            "androidx.compose.foundation:foundation:${Versions.Compose.compose_version}"
        private const val compose_animation =
            "androidx.compose.animation:animation:${Versions.Compose.compose_version}"
        private const val compose_material =
            "androidx.compose.material:material:${Versions.Compose.compose_version}"
        private const val compose_viewbinding =
            "androidx.compose.ui:ui-viewbinding:${Versions.Compose.compose_version}"
        private const val compose_contraint =
            "androidx.constraintlayout:constraintlayout-compose:${Versions.Compose.compose_constraint_version}"
        private const val compose_paging =
            "androidx.paging:paging-compose:${Versions.Compose.compose_paging_version}"
        const val compose_viewmodel =
            "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.Compose.compose_viewmodel_version}"
        const val compose_navigation =
            "androidx.navigation:navigation-compose:${Versions.Compose.compose_navigation_version}"

        val compose_dependencies = listOf(
            compose_runtime,
            compose_runtime_livedata,
            compose_ui,
            compose_ui_tooling,
            compose_ui_tooling_preview,
            compose_foundation_layaout,
            compose_foundation,
            compose_animation,
            compose_material,
            compose_activity_compose,
            compose_viewbinding,
            compose_contraint,
            compose_paging,
            compose_viewmodel,
            compose_navigation
        )
    }

    object Accompanist {
        private const val system_ui_controller =
            "com.google.accompanist:accompanist-systemuicontroller:${Versions.Accompanist.systemuicontroller_version}"

        val accompanist_dependencies = listOf(system_ui_controller)
    }

    const val dagger = "com.google.dagger:dagger:${Versions.Hilt.dagger_hilt_version}"

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.gradle_version}"
    const val kotlin_coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinx_coroutines_version}"

    const val coil_compose = "io.coil-kt:coil-compose:${Versions.coil_version}"

    const val lottie_compose = "com.airbnb.android:lottie-compose:${Versions.lottie_version}"

    const val swipe_refresh_compose =
        "com.google.accompanist:accompanist-swiperefresh:${Versions.swipe_refresh_version}"

    const val lifecycle_runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-alpha01"

    object Room {
        private const val room = "androidx.room:room-ktx:${Versions.room_version}"
        private const val room_runtime = "androidx.room:room-runtime:${Versions.room_version}"
        private const val room_paging = "androidx.room:room-paging:${Versions.room_version}"

        const val room_compiler = "androidx.room:room-compiler:${Versions.room_version}"

        val room_dependencies = listOf(room, room_runtime, room_paging)
    }
}