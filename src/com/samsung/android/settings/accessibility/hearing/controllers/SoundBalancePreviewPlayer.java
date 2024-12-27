package com.samsung.android.settings.accessibility.hearing.controllers;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SoundBalancePreviewPlayer implements MediaPlayer.OnPreparedListener {
    public final AudioFocusRequest audioFocusRequest;
    public final AudioManager audioManager;
    public final Context context;
    public MediaPlayer mediaPlayer = null;
    public boolean isPrepared = false;

    public SoundBalancePreviewPlayer(Context context) {
        this.context = context;
        this.audioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        AudioFocusRequest.Builder builder = new AudioFocusRequest.Builder(1);
        builder.setOnAudioFocusChangeListener(
                new AudioManager
                        .OnAudioFocusChangeListener() { // from class:
                                                        // com.samsung.android.settings.accessibility.hearing.controllers.SoundBalancePreviewPlayer$$ExternalSyntheticLambda0
                    @Override // android.media.AudioManager.OnAudioFocusChangeListener
                    public final void onAudioFocusChange(int i) {
                        SoundBalancePreviewPlayer soundBalancePreviewPlayer =
                                SoundBalancePreviewPlayer.this;
                        soundBalancePreviewPlayer.getClass();
                        if (i != -3 && i != -2 && i != -1) {
                            if (i != 1) {
                                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                                        i,
                                        "Unknown audio focus change code,",
                                        "SoundBalancePreviewPlayer");
                                return;
                            } else {
                                Log.d("SoundBalancePreviewPlayer", "AUDIOFOCUS_GAIN");
                                return;
                            }
                        }
                        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                                i, "AudioFocus state = ", "SoundBalancePreviewPlayer");
                        MediaPlayer mediaPlayer = soundBalancePreviewPlayer.mediaPlayer;
                        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
                            return;
                        }
                        soundBalancePreviewPlayer.mediaPlayer.pause();
                    }
                });
        AudioAttributes.Builder builder2 = new AudioAttributes.Builder();
        builder2.setContentType(2);
        builder.setAudioAttributes(builder2.build());
        this.audioFocusRequest = builder.build();
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public final void onPrepared(MediaPlayer mediaPlayer) {
        this.isPrepared = true;
    }

    public final void pauseMediaPlayer() {
        Log.d("SoundBalancePreviewPlayer", "pauseMediaPlayer() mMediaPlayer : " + this.mediaPlayer);
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
            return;
        }
        this.mediaPlayer.pause();
    }
}
