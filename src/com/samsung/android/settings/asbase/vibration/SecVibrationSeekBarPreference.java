package com.samsung.android.settings.asbase.vibration;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.TypedArray;
import android.media.AudioManager;
import android.media.Ringtone;
import android.os.Handler;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.SeslSeekBar;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SeekBarPreference;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.settings.asbase.rune.VibRune;
import com.samsung.android.settings.asbase.widget.SecVibrationIcon;
import com.samsung.android.settings.asbase.widget.SecVibrationIconMotion;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.util.SemLog;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecVibrationSeekBarPreference extends SeekBarPreference {
    public final Context mContext;
    public int mMaxIntensity;
    public final ContentResolver mResolver;
    public Ringtone mRingtone;
    public int mSALogEventId;
    public SeslSeekBar mSeekBar;
    public int mStream;
    public String mSyncDbName;
    public String mSystemDBName;
    public String mTitle;
    public SecVibrationIntensityHelper.AnonymousClass1 mTouchSeekBarCallBack;
    public VibrationAttributes mVibrationAttributes;
    public VibrationEffect mVibrationEffect;
    public SecVibrationIcon mVibrationIcon;
    public final AnonymousClass1 onSeekBarChangeListener;

    /* JADX WARN: Type inference failed for: r2v1, types: [com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreference$1] */
    public SecVibrationSeekBarPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.onSeekBarChangeListener =
                new SeslSeekBar
                        .OnSeekBarChangeListener() { // from class:
                                                     // com.samsung.android.settings.asbase.vibration.SecVibrationSeekBarPreference.1
                    @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
                    public final void onProgressChanged(
                            SeslSeekBar seslSeekBar, int i3, boolean z) {
                        Handler handler;
                        Handler handler2;
                        if (z) {
                            SecVibrationSeekBarPreference secVibrationSeekBarPreference =
                                    SecVibrationSeekBarPreference.this;
                            Settings.System.putInt(
                                    secVibrationSeekBarPreference.mResolver,
                                    secVibrationSeekBarPreference.mSystemDBName,
                                    i3);
                            Log.v(
                                    "SecVibrationSeekBarPreference",
                                    "setVibrationIntensity : "
                                            + secVibrationSeekBarPreference.mSystemDBName
                                            + "["
                                            + i3
                                            + "]");
                            SecVibrationIntensityHelper.AnonymousClass1 anonymousClass1 =
                                    secVibrationSeekBarPreference.mTouchSeekBarCallBack;
                            int i4 = secVibrationSeekBarPreference.mStream;
                            SecVibrationIntensityHelper secVibrationIntensityHelper =
                                    SecVibrationIntensityHelper.this;
                            if (i4 == 2) {
                                secVibrationIntensityHelper.mIconHandler.removeMessages(10);
                                secVibrationIntensityHelper
                                        .mIconHandler
                                        .obtainMessage(10, i3, 0)
                                        .sendToTarget();
                            } else if (i4 == 5) {
                                secVibrationIntensityHelper.mIconHandler.removeMessages(8);
                                secVibrationIntensityHelper
                                        .mIconHandler
                                        .obtainMessage(8, i3, 0)
                                        .sendToTarget();
                            } else if (i4 == 1 || i4 == 0) {
                                secVibrationIntensityHelper.mIconHandler.removeMessages(7);
                                secVibrationIntensityHelper
                                        .mIconHandler
                                        .obtainMessage(7, i3, 0)
                                        .sendToTarget();
                            } else if (i4 == 3) {
                                secVibrationIntensityHelper.mIconHandler.removeMessages(6);
                                secVibrationIntensityHelper
                                        .mIconHandler
                                        .obtainMessage(6, i3, 0)
                                        .sendToTarget();
                            }
                            if (VibRune.SUPPORT_SYNC_WITH_HAPTIC
                                    && secVibrationSeekBarPreference.mRingtone != null
                                    && Settings.System.getInt(
                                                    secVibrationSeekBarPreference.mResolver,
                                                    secVibrationSeekBarPreference.mSyncDbName,
                                                    0)
                                            == 1
                                    && AudioManager.isHapticPlaybackSupported()) {
                                final SecVibrationIntensityHelper.AnonymousClass1 anonymousClass12 =
                                        secVibrationSeekBarPreference.mTouchSeekBarCallBack;
                                Ringtone ringtone = secVibrationSeekBarPreference.mRingtone;
                                SecVibrationIntensityHelper secVibrationIntensityHelper2 =
                                        SecVibrationIntensityHelper.this;
                                secVibrationIntensityHelper2.stopVibration();
                                secVibrationIntensityHelper2.mRingtone = ringtone;
                                if (ringtone != null
                                        && (handler2 = secVibrationIntensityHelper2.mHandler)
                                                != null) {
                                    handler2.postDelayed(
                                            new Runnable() { // from class:
                                                             // com.samsung.android.settings.asbase.vibration.SecVibrationIntensityHelper.1.2
                                                public AnonymousClass2() {}

                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    SecVibrationIntensityHelper
                                                            secVibrationIntensityHelper3 =
                                                                    SecVibrationIntensityHelper
                                                                            .this;
                                                    if (secVibrationIntensityHelper3.mStopFlag) {
                                                        SemLog.i(
                                                                "SecVibrationIntensityHelper",
                                                                "ACH is canceled by stop flag");
                                                    } else {
                                                        secVibrationIntensityHelper3.mRingtone
                                                                .play();
                                                    }
                                                }
                                            },
                                            80L);
                                }
                            } else {
                                final SecVibrationIntensityHelper.AnonymousClass1 anonymousClass13 =
                                        secVibrationSeekBarPreference.mTouchSeekBarCallBack;
                                VibrationEffect vibrationEffect =
                                        secVibrationSeekBarPreference.mVibrationEffect;
                                final VibrationAttributes vibrationAttributes =
                                        secVibrationSeekBarPreference.mVibrationAttributes;
                                SecVibrationIntensityHelper secVibrationIntensityHelper3 =
                                        SecVibrationIntensityHelper.this;
                                secVibrationIntensityHelper3.stopVibration();
                                secVibrationIntensityHelper3.mEffect = vibrationEffect;
                                if (secVibrationIntensityHelper3.mVibrator != null
                                        && vibrationEffect != null
                                        && (handler = secVibrationIntensityHelper3.mHandler)
                                                != null) {
                                    handler.postDelayed(
                                            new Runnable() { // from class:
                                                             // com.samsung.android.settings.asbase.vibration.SecVibrationIntensityHelper.1.1
                                                public final /* synthetic */ VibrationAttributes
                                                        val$attr;

                                                public RunnableC00551(
                                                        final VibrationAttributes
                                                                vibrationAttributes2) {
                                                    r2 = vibrationAttributes2;
                                                }

                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    SecVibrationIntensityHelper
                                                            secVibrationIntensityHelper4 =
                                                                    SecVibrationIntensityHelper
                                                                            .this;
                                                    if (secVibrationIntensityHelper4.mStopFlag) {
                                                        SemLog.i(
                                                                "SecVibrationIntensityHelper",
                                                                "SEP is canceled by stop flag");
                                                    } else {
                                                        secVibrationIntensityHelper4.mVibrator
                                                                .vibrate(
                                                                        secVibrationIntensityHelper4
                                                                                .mEffect,
                                                                        r2);
                                                    }
                                                }
                                            },
                                            10L);
                                }
                            }
                            LoggingHelper.insertEventLogging(
                                    VolteConstants.ErrorCode.MDMN_PUSHCALL_TO_PRIMARY,
                                    secVibrationSeekBarPreference.mSALogEventId,
                                    i3);
                        }
                    }

                    @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
                    public final void onStartTrackingTouch(SeslSeekBar seslSeekBar) {}

                    @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
                    public final void onStopTrackingTouch(SeslSeekBar seslSeekBar) {}
                };
        this.mContext = context;
        this.mResolver = context.getContentResolver();
        setLayoutResource(R.layout.sec_vibration_intensity_v2);
    }

    @Override // androidx.preference.SeekBarPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setClickable(false);
        SeslSeekBar seslSeekBar =
                (SeslSeekBar) preferenceViewHolder.findViewById(R.id.vib_intensity_seekbar);
        this.mSeekBar = seslSeekBar;
        seslSeekBar.setSoundEffectsEnabled(true);
        this.mSeekBar.setSeamless(false);
        this.mSeekBar.setMax(this.mMaxIntensity);
        setCurrentProgress();
        this.mSeekBar.setOnSeekBarChangeListener(this.onSeekBarChangeListener);
        this.mSeekBar.setMode(5);
        if (this.mContext.getTheme() != null) {
            TypedArray obtainStyledAttributes =
                    this.mContext
                            .getTheme()
                            .obtainStyledAttributes(
                                    this.mContext.getThemeResId(), new int[] {R.attr.colorPrimary});
            this.mSeekBar.setProgressTintList(obtainStyledAttributes.getColorStateList(0));
            this.mSeekBar.setThumbTintList(obtainStyledAttributes.getColorStateList(0));
        }
        this.mVibrationIcon =
                (SecVibrationIcon) preferenceViewHolder.findViewById(R.id.basic_vibration_icon);
        SecVibrationIconMotion secVibrationIconMotion = new SecVibrationIconMotion(this.mContext);
        SecVibrationIcon secVibrationIcon = this.mVibrationIcon;
        if (secVibrationIcon == null || this.mSeekBar == null) {
            return;
        }
        String str = this.mSystemDBName;
        int i = this.mStream;
        secVibrationIcon.mSecVibrationIconMotion = secVibrationIconMotion;
        secVibrationIcon.mSystemDbName = str;
        secVibrationIcon.mStream = i;
        secVibrationIcon.mIconType = secVibrationIcon.getIconType();
        if (secVibrationIcon.mContext.getTheme() != null) {
            secVibrationIcon.mIconActiveColor =
                    secVibrationIcon
                            .mContext
                            .getTheme()
                            .obtainStyledAttributes(
                                    secVibrationIcon.mContext.getThemeResId(),
                                    new int[] {R.attr.colorPrimary})
                            .getColorStateList(0);
        }
        secVibrationIcon.updateLayout(true);
    }

    public final void setCurrentProgress() {
        int i = Settings.System.getInt(this.mResolver, this.mSystemDBName, this.mMaxIntensity);
        SeslSeekBar seslSeekBar = this.mSeekBar;
        if (seslSeekBar != null) {
            seslSeekBar.setProgress(i);
            this.mSeekBar.setContentDescription(this.mTitle);
        }
        if (this.mSeekBar != null && !Utils.isTalkBackEnabled(this.mContext)) {
            this.mSeekBar.setContentDescription(Integer.toString(i));
        }
        Log.w(
                "SecVibrationSeekBarPreference",
                "setCurrentProgress() " + this.mSystemDBName + " : " + i);
    }

    public SecVibrationSeekBarPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SecVibrationSeekBarPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SecVibrationSeekBarPreference(Context context) {
        this(context, null);
    }
}
