package com.android.settings.widget;

import android.media.MediaPlayer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaAnimationController$$ExternalSyntheticLambda1
        implements MediaPlayer.OnPreparedListener {
    @Override // android.media.MediaPlayer.OnPreparedListener
    public final void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.setLooping(true);
    }
}
