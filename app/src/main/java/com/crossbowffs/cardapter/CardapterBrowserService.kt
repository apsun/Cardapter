package com.crossbowffs.cardapter

import android.content.ComponentName
import android.media.browse.MediaBrowser
import android.os.Bundle
import android.service.media.MediaBrowserService
import android.util.Log

class CardapterBrowserService : MediaBrowserService() {
    companion object {
        private const val TAG = "Cardapter"
    }

    private lateinit var browser: MediaBrowser

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate()")

        browser = MediaBrowser(
            this,
            ComponentName(
                "com.soundcloud.android",
                "com.soundcloud.android.playback.players.MediaService"
            ),
            object : MediaBrowser.ConnectionCallback() {
                override fun onConnected() {
                    Log.i(TAG, "onConnected()")

                    // The important part! Exports the real SoundCloud session token to
                    // the system so we can use it with Android Auto.
                    val token = browser.getSessionToken()
                    Log.i(TAG, "sessionToken = " + token.hashCode())
                    setSessionToken(token)
                }

                override fun onConnectionFailed() {
                    Log.e(TAG, "onConnectionFailed()")
                }

                override fun onConnectionSuspended() {
                    Log.e(TAG, "onConnectionSuspended()")

                    // setSessionToken can only be called once so we can't change our token
                    // if the connection is re-established, so we kill ourselves in the hope
                    // that someone will restart us and restart the connection.
                    stopSelf()
                }
            },
            null
        )
        browser.connect()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy()")

        browser.disconnect()
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowser.MediaItem>>
    ) {
        // The real MediaService doesn't seem to properly work anyways, so don't bother
        // trying to load any meaningful results here. An empty list is good enough.
        result.sendResult(mutableListOf())
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        return BrowserRoot("/", null)
    }
}