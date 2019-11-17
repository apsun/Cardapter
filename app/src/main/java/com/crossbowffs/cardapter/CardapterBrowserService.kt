package com.crossbowffs.cardapter

import android.content.ComponentName
import android.media.browse.MediaBrowser
import android.media.session.MediaSession
import android.os.Bundle
import android.service.media.MediaBrowserService

class CardapterBrowserService : MediaBrowserService() {
    private lateinit var session: MediaSession
    private lateinit var browser: MediaBrowser

    override fun onCreate() {
        super.onCreate()
        session = MediaSession(this, "Cardapter")
        browser = MediaBrowser(
            this,
            ComponentName(
                "com.soundcloud.android",
                "com.soundcloud.android.playback.players.MediaService"
            ),
            object : MediaBrowser.ConnectionCallback() {
                override fun onConnected() {
                    // The important part! Exports the real SoundCloud session token to
                    // the system so we can use it with Android Auto.
                    setSessionToken(browser.getSessionToken())
                }
            },
            null
        )
        browser.connect()
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