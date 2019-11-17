# Cardapter: SoundCloud for Android Auto

Lets you somewhat control SoundCloud using Android Auto. Does not support
playlist switching or browsing, pretty much all it does is hook up the
previous/next buttons in your car to the SoundCloud app.

There is NO WARRANTY provided with this app. If using it causes Android Auto
to break and navigate you off a cliff into the ocean, don't blame me. If you
use this to blast [anime](https://soundcloud.com/dada-qada/feat-ranasol)
[music](https://soundcloud.com/djnoriken/k4n0-s73_lum) out your car and
everyone finds out that you're a weeb, AGAIN, NO WARRANTY. That said,
I've been using this for a few months now with no issues (well, except for
the anime part), but still - NO WARRANTY. NONE. NIL. ZERO.

## Usage

First, enable developer settings in the Android Auto app (please figure this
out on your own, I'm not going to help you shoot yourself in the foot). Once
that's done, enable unknown sources in the developer settings menu.
Cardapter should now be visible in Android Auto.

Unfortunately, SoundCloud does not (to my knowledge) export information
about your playlists, so if you want to switch between e.g. liked/stream,
you have to use the actual SoundCloud app to start playback. Once playback
is started you can seek between tracks using the normal Android Auto
controls.

Note that for some reason Android Auto doesn't like to jump directly to the
now playing view; you may have to manually press the blue circle to pull
that up. This is probably fixable, but I haven't found the time to
investigate why this happens. Please submit a pull request if you can
figure this out.

## How the hell?

SoundCloud has a neat little component that exports the playing state, which
I presume it uses for the media notification. Funnily enough, all it would
take to get the exact behavior as this app in the real SoundCloud app would
be to add a 4-line XML file:

```
<?xml version="1.0" encoding="utf-8"?>
<automotiveApp>
    <uses name="media" />
</automotiveApp>
```

This app effectively acts as a wrapper for the real app that adds this XML
file.
