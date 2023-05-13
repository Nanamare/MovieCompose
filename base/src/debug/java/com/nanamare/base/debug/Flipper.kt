package com.nanamare.base.debug

import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.navigation.NavigationFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import okhttp3.OkHttpClient

object Flipper {

    private val flipper: FlipperOkhttpInterceptor? by lazy {
        AndroidFlipperClient.getInstanceIfInitialized()
            ?.getPlugin<NetworkFlipperPlugin>(NetworkFlipperPlugin.ID)
            ?.let { plugin ->
                FlipperOkhttpInterceptor(plugin, true)
            }
    }

    fun init(context: Context) {
        if (FlipperUtils.shouldEnableFlipper(context)) {
            SoLoader.init(context, false)
            AndroidFlipperClient.getInstance(context).apply {
                addPlugin(InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()))
                addPlugin(NavigationFlipperPlugin.getInstance())
                addPlugin(NetworkFlipperPlugin())
                addPlugin(DatabasesFlipperPlugin(context))
                addPlugin(
                    SharedPreferencesFlipperPlugin(
                        context,
                        listOf(
                            SharedPreferencesFlipperPlugin.SharedPreferencesDescriptor(
                                "preferences.movie",
                                Context.MODE_PRIVATE
                            )
                        )
                    )
                )
                addPlugin(CrashReporterPlugin.getInstance())
            }.start()
        }
    }


    fun initOkHttpClient(okHttpClientBuilder: OkHttpClient.Builder) {
        flipper?.let(okHttpClientBuilder::addNetworkInterceptor)
    }

}
