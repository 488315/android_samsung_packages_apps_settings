package com.samsung.android.settings.asbase.audio;

import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.provider.Settings;
import android.util.Log;
import android.widget.SeekBar;

import com.android.internal.jank.InteractionJankMonitor;

import com.samsung.android.settings.asbase.rune.AudioRune;
import com.samsung.android.settings.asbase.utils.SeekBarUtil;
import com.samsung.android.settings.asbase.widget.SecVolumeSeekBarPreference;
import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecSeekBarVolumizerDTMF implements SeekBar.OnSeekBarChangeListener {
    public final AudioManager mAudioManager;
    public final SecVolumeSeekBarPreference.AnonymousClass3 mCallback;
    public final Context mContext;
    public SeekBar mSeekBar;
    public ToneGenerator mToneGenerator;

    public SecSeekBarVolumizerDTMF(
            Context context, SecVolumeSeekBarPreference.AnonymousClass3 anonymousClass3) {
        this.mContext = context;
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        this.mCallback = anonymousClass3;
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        SecVolumeSeekBarPreference.AnonymousClass3 anonymousClass3;
        if (z && (anonymousClass3 = this.mCallback) != null) {
            if (SecVolumeSeekBarPreference.m1123$$Nest$mrequestAudioFocus(
                    SecVolumeSeekBarPreference.this)) {
                Log.d(
                        "SecVolumeSeekBarPreference",
                        "SecSeekBarVolumizerDTMF requestAudioFocus() return true : request focus.");
            } else {
                Log.d(
                        "SecVolumeSeekBarPreference",
                        "SecSeekBarVolumizerDTMF : can't request focus.");
            }
            SecVolumeSeekBarPreference.AnonymousClass3 anonymousClass32 = this.mCallback;
            anonymousClass32.getClass();
            Log.d("SecVolumeSeekBarPreference", "onProgressChanged changed");
            SecVolumeSeekBarPreference secVolumeSeekBarPreference = SecVolumeSeekBarPreference.this;
            SecVolumeSeekBarPreference.Callback callback = secVolumeSeekBarPreference.mCallback;
            if (callback != null) {
                callback.onStreamValueChanged(secVolumeSeekBarPreference.mStream, i);
            }
            SeekBarUtil.vibrateIfNeeded(seekBar, secVolumeSeekBarPreference.mStream, i);
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onStartTrackingTouch(SeekBar seekBar) {
        seekBar.setProgress(Math.round(seekBar.getProgress() / 1000.0f) * 1000, true);
        SecVolumeSeekBarPreference.AnonymousClass3 anonymousClass3 = this.mCallback;
        if (anonymousClass3 != null) {
            SecVolumeSeekBarPreference secVolumeSeekBarPreference = SecVolumeSeekBarPreference.this;
            secVolumeSeekBarPreference.mJankMonitor.begin(
                    InteractionJankMonitor.Configuration.Builder.withView(
                                    53, secVolumeSeekBarPreference.mSeekBar)
                            .setTag(secVolumeSeekBarPreference.getKey()));
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onStopTrackingTouch(SeekBar seekBar) {
        seekBar.setProgress(Math.round(seekBar.getProgress() / 1000.0f) * 1000, true);
        if (this.mToneGenerator == null) {
            this.mToneGenerator = new ToneGenerator(8, 0);
        }
        this.mToneGenerator.setVolume(
                this.mAudioManager.semGetSituationVolume(15, 0)
                        * ((float)
                                Math.pow(
                                        2.0d,
                                        (seekBar.getProgress() / 1000)
                                                - (AudioRune.SUPPORT_SITUATION_EXTENSION
                                                        ? 4
                                                        : 2))));
        this.mToneGenerator.semSetSituationType("stv_call_waiting");
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "volume_waiting_tone",
                seekBar.getProgress() / 1000);
        this.mToneGenerator.startTone(22, 300);
        SecVolumeSeekBarPreference.AnonymousClass3 anonymousClass3 = this.mCallback;
        if (anonymousClass3 != null) {
            SecVolumeSeekBarPreference.this.mJankMonitor.end(53);
        }
    }

    public final void stop() {
        ToneGenerator toneGenerator = this.mToneGenerator;
        if (toneGenerator != null) {
            toneGenerator.setVolume(0.0f);
            this.mToneGenerator.stopTone();
        }
        ToneGenerator toneGenerator2 = this.mToneGenerator;
        if (toneGenerator2 != null) {
            toneGenerator2.release();
            this.mToneGenerator = null;
        }
        this.mSeekBar.setOnSeekBarChangeListener(null);
    }
}
