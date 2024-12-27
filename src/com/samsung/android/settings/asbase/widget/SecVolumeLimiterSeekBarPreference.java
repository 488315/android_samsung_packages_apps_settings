package com.samsung.android.settings.asbase.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SeekBarPreference;

import com.android.settings.R;

import com.samsung.android.settings.logging.LoggingHelper;
import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecVolumeLimiterSeekBarPreference extends SeekBarPreference
        implements View.OnKeyListener {
    public final AudioManager mAudioManager;
    public final Context mContext;
    public SeekBar mSeekBar;
    public final AnonymousClass1 onSeekBarChangeListener;
    public final AnonymousClass2 onSeekBarTouchListener;

    /* JADX WARN: Type inference failed for: r2v1, types: [com.samsung.android.settings.asbase.widget.SecVolumeLimiterSeekBarPreference$1] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.samsung.android.settings.asbase.widget.SecVolumeLimiterSeekBarPreference$2] */
    public SecVolumeLimiterSeekBarPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.onSeekBarChangeListener =
                new SeekBar
                        .OnSeekBarChangeListener() { // from class:
                                                     // com.samsung.android.settings.asbase.widget.SecVolumeLimiterSeekBarPreference.1
                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onProgressChanged(SeekBar seekBar, int i3, boolean z) {
                        if (z) {
                            SecVolumeLimiterSeekBarPreference.this.updateVolumeLimiter(i3);
                            SecVolumeLimiterSeekBarPreference secVolumeLimiterSeekBarPreference =
                                    SecVolumeLimiterSeekBarPreference.this;
                            secVolumeLimiterSeekBarPreference.updateSeekBarDrawable(
                                    secVolumeLimiterSeekBarPreference.isEnabled());
                            seekBar.performHapticFeedback(
                                    HapticFeedbackConstants.semGetVibrationIndex(41));
                            LoggingHelper.insertEventLogging(4116, 4118, i3);
                        }
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStopTrackingTouch(SeekBar seekBar) {}
                };
        this.onSeekBarTouchListener =
                new View
                        .OnTouchListener() { // from class:
                                             // com.samsung.android.settings.asbase.widget.SecVolumeLimiterSeekBarPreference.2
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == 2 || motionEvent.getAction() == 0) {
                            SecVolumeLimiterSeekBarPreference.this.mSeekBar.setPressed(true);
                        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                            SecVolumeLimiterSeekBarPreference.this.mSeekBar.setPressed(false);
                        }
                        return false;
                    }
                };
        setLayoutResource(R.layout.sec_preference_volume_limiter_slider);
        this.mContext = context;
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
    }

    @Override // androidx.preference.SeekBarPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View view = preferenceViewHolder.itemView;
        view.setClickable(false);
        view.setOnKeyListener(this);
        SeekBar seekBar = (SeekBar) preferenceViewHolder.findViewById(R.id.volume_limiter_seekbar);
        this.mSeekBar = seekBar;
        if (seekBar == null) {
            return;
        }
        seekBar.setMax(5);
        int i =
                Settings.System.getInt(
                                this.mContext.getContentResolver(), "volume_limiter_value", 9)
                        - 9;
        this.mSeekBar.setProgress(i);
        this.mSeekBar.setOnSeekBarChangeListener(this.onSeekBarChangeListener);
        this.mSeekBar.setOnTouchListener(this.onSeekBarTouchListener);
        updateSeekBarDrawable(isEnabled());
        updateVolumeLimiter(i);
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        SeekBar seekBar;
        if (keyEvent.getAction() != 1 && keyEvent.getAction() != 0) {
            return false;
        }
        if ((i == 21 || i == 22) && (seekBar = this.mSeekBar) != null) {
            return seekBar.onKeyDown(i, keyEvent);
        }
        return false;
    }

    public final void updateSeekBarDrawable(boolean z) {
        Drawable drawable;
        SeekBar seekBar = this.mSeekBar;
        Drawable drawable2 =
                this.mContext
                        .getResources()
                        .getDrawable(R.drawable.sec_volume_limit_seekbar_tickmark_drawable, null);
        drawable2.setAlpha(z ? 255 : 153);
        seekBar.setTickMark(drawable2);
        SeekBar seekBar2 = this.mSeekBar;
        if (z) {
            drawable =
                    this.mContext
                            .getResources()
                            .getDrawable(
                                    this.mSeekBar.getProgress() > 0
                                            ? R.drawable
                                                    .sec_volume_limit_seekbar_thumb_ear_shock_drawable
                                            : R.drawable.sec_volume_limit_seekbar_thumb_drawable,
                                    null);
        } else {
            drawable =
                    this.mContext
                            .getResources()
                            .getDrawable(
                                    R.drawable.sec_volume_limit_seekbar_thumb_drawable_dim, null);
        }
        seekBar2.setThumb(drawable);
    }

    public final void updateVolumeLimiter(int i) {
        int i2 = i + 9;
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i2, "volume_limiter_value : ", "SecVolumeLimiterSeekBarPreference");
        Settings.System.putInt(this.mContext.getContentResolver(), "volume_limiter_value", i2);
        this.mAudioManager.setVolumeLimiterValue(i2);
    }

    public SecVolumeLimiterSeekBarPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SecVolumeLimiterSeekBarPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SecVolumeLimiterSeekBarPreference(Context context) {
        this(context, null);
    }
}
