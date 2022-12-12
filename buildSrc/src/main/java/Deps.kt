object Deps {

    object Hilt {
        private const val dagger_hilt_android =
            "com.google.dagger:hilt-android:${Versions.Hilt.dagger_hilt_version}"

        const val dagger_hilt_compiler =
            "com.google.dagger:hilt-compiler:${Versions.Hilt.dagger_hilt_version}"
        private const val dagger_hilt_navigation_compose =
            "androidx.hilt:hilt-navigation-compose:${Versions.Hilt.hilt_navigation_compose}"

        val hilt_dependencies = listOf(dagger_hilt_android, dagger_hilt_navigation_compose)
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

    object UnitTest {
        private const val junit = "junit:junit:${Versions.UnitTest.junit_version}"

        private const val kotlinx_coroutines_test =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinx_coroutines_version}"
        private const val truth = "com.google.truth:truth:${Versions.UnitTest.truth_version}"
        private const val okhttp_mock_web_server =
            "com.squareup.okhttp3:mockwebserver:${Versions.UnitTest.okhttp_mock_server_version}"
        private const val androidx_core_testing = "androidx.arch.core:core-testing:2.1.0"

        private const val mockito_core = "org.mockito:mockito-core:4.0.0"
        private const val mockito_inline = "org.mockito:mockito-inline:3.2.4"

        val unit_test_dependencies = listOf(
            junit,
            kotlinx_coroutines_test,
            truth,
            okhttp_mock_web_server,
            androidx_core_testing,
            mockito_core,
            mockito_inline
        )
    }

    object AndroidTest {
        private const val androidx_test_core =
            "androidx.test:core:${Versions.AndroidTest.androidx_test_version}"
        private const val androidx_test_runner =
            "androidx.test:runner:${Versions.AndroidTest.androidx_test_version}"
        private const val androidx_test_espresso =
            "androidx.test.espresso:espresso-core:${Versions.AndroidTest.androidx_test_espresso_version}"

        private const val dagger_test =
            "com.google.dagger:hilt-android-testing:${Versions.Hilt.dagger_hilt_version}"

        private const val compose_ui_test =
            "androidx.compose.ui:ui-test-junit4:${Versions.Compose.compose_version}"
        private const val compose_ui_test_manifest =
            "androidx.compose.ui:ui-test-manifest:${Versions.Compose.compose_version}"

        val android_test_dependencies = listOf(
            androidx_test_core,
            androidx_test_runner,
            androidx_test_espresso,
            dagger_test,
            compose_ui_test,
            compose_ui_test_manifest
        )
    }
}