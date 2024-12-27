package com.android.settings.notification;

import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.preference.SeekBarVolumizer;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;

import com.android.internal.jank.InteractionJankMonitor;
import com.android.settings.R;
import com.android.settings.widget.SeekBarPreference;

import com.sec.ims.presence.ServiceTuple;

import java.text.NumberFormat;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class VolumeSeekBarPreference extends SeekBarPreference {
    AudioManager mAudioManager;
    public Callback mCallback;
    public int mIconResId;
    public ImageView mIconView;
    public final InteractionJankMonitor mJankMonitor;
    public Listener mListener;
    public Locale mLocale;
    public int mMuteIconResId;
    public boolean mMuted;
    public NumberFormat mNumberFormat;
    public SeekBar mSeekBar;
    SeekBarVolumizerFactory mSeekBarVolumizerFactory;
    public boolean mStopped;
    public int mStream;
    public String mSuppressionText;
    public TextView mSuppressionTextView;
    public TextView mTitle;
    public SeekBarVolumizer mVolumizer;
    public boolean mZenMuted;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Callback {
        void onSampleStarting();

        void onStartTrackingTouch(SeekBarVolumizer seekBarVolumizer);

        void onStreamValueChanged();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Listener {
        void onUpdateMuteState();
    }

    public VolumeSeekBarPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mJankMonitor = InteractionJankMonitor.getInstance();
        setLayoutResource(R.layout.preference_volume_slider);
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        this.mSeekBarVolumizerFactory = new SeekBarVolumizerFactory(context);
    }

    public CharSequence formatStateDescription(int i) {
        Locale locale = getContext().getResources().getConfiguration().getLocales().get(0);
        Locale locale2 = this.mLocale;
        if (locale2 == null || !locale2.equals(locale)) {
            this.mLocale = locale;
            this.mNumberFormat = NumberFormat.getPercentInstance(locale);
        }
        return this.mNumberFormat.format(getPercent(i));
    }

    public double getPercent(float f) {
        if (this.mMax - this.mMin <= 0.0f) {
            return 0.0d;
        }
        return Math.floor(Math.max(0.0f, Math.min(1.0f, (f - r3) / r0)) * 100.0f) / 100.0d;
    }

    public void init() {
        Uri uri;
        if (this.mSeekBar == null) {
            return;
        }
        if (!isEnabled()) {
            this.mSeekBar.setEnabled(false);
            return;
        }
        SeekBarVolumizer.Callback callback =
                new SeekBarVolumizer
                        .Callback() { // from class:
                                      // com.android.settings.notification.VolumeSeekBarPreference.1
                    public final void onMuted(boolean z, boolean z2) {
                        VolumeSeekBarPreference volumeSeekBarPreference =
                                VolumeSeekBarPreference.this;
                        if (volumeSeekBarPreference.mMuted == z
                                && volumeSeekBarPreference.mZenMuted == z2) {
                            return;
                        }
                        volumeSeekBarPreference.mMuted = z;
                        volumeSeekBarPreference.mZenMuted = z2;
                        volumeSeekBarPreference.updateIconView();
                        Listener listener = VolumeSeekBarPreference.this.mListener;
                        if (listener != null) {
                            listener.onUpdateMuteState();
                        }
                    }

                    public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                        Callback callback2 = VolumeSeekBarPreference.this.mCallback;
                        if (callback2 != null) {
                            callback2.onStreamValueChanged();
                        }
                        VolumeSeekBarPreference volumeSeekBarPreference =
                                VolumeSeekBarPreference.this;
                        volumeSeekBarPreference.mOverrideSeekBarStateDescription =
                                volumeSeekBarPreference.formatStateDescription(i);
                    }

                    public final void onSampleStarting(SeekBarVolumizer seekBarVolumizer) {
                        Callback callback2 = VolumeSeekBarPreference.this.mCallback;
                        if (callback2 != null) {
                            callback2.onSampleStarting();
                        }
                    }

                    public final void onStartTrackingTouch(SeekBarVolumizer seekBarVolumizer) {
                        Callback callback2 = VolumeSeekBarPreference.this.mCallback;
                        if (callback2 != null) {
                            callback2.onStartTrackingTouch(seekBarVolumizer);
                        }
                        VolumeSeekBarPreference volumeSeekBarPreference =
                                VolumeSeekBarPreference.this;
                        volumeSeekBarPreference.mJankMonitor.begin(
                                InteractionJankMonitor.Configuration.Builder.withView(
                                                53, volumeSeekBarPreference.mSeekBar)
                                        .setTag(VolumeSeekBarPreference.this.getKey()));
                    }

                    public final void onStopTrackingTouch(SeekBarVolumizer seekBarVolumizer) {
                        VolumeSeekBarPreference.this.mJankMonitor.end(53);
                    }
                };
        if (this.mStream == 3) {
            uri = Uri.parse("android.resource://" + getContext().getPackageName() + "/2131951686");
        } else {
            uri = null;
        }
        if (this.mVolumizer == null) {
            SeekBarVolumizerFactory seekBarVolumizerFactory = this.mSeekBarVolumizerFactory;
            int i = this.mStream;
            seekBarVolumizerFactory.getClass();
            this.mVolumizer =
                    new SeekBarVolumizer(seekBarVolumizerFactory.mContext, i, uri, callback);
        }
        this.mVolumizer.start();
        this.mVolumizer.setSeekBar(this.mSeekBar);
        updateIconView();
        updateSuppressionText();
        Listener listener = this.mListener;
        if (listener != null) {
            listener.onUpdateMuteState();
        }
    }

    @Override // com.android.settings.widget.SeekBarPreference,
              // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mSeekBar = (SeekBar) preferenceViewHolder.findViewById(android.R.id.textClassifier);
        this.mIconView = (ImageView) preferenceViewHolder.findViewById(android.R.id.icon);
        this.mSuppressionTextView =
                (TextView) preferenceViewHolder.findViewById(R.id.suppression_text);
        this.mTitle = (TextView) preferenceViewHolder.findViewById(android.R.id.title);
        init();
    }

    public void setStream(int i) {
        this.mStream = i;
        setMax(this.mAudioManager.getStreamMaxVolume(i));
        setMin(this.mAudioManager.getStreamMinVolumeInt(this.mStream));
        setProgress(this.mAudioManager.getStreamVolume(this.mStream), true);
    }

    public final void updateContentDescription(CharSequence charSequence) {
        TextView textView = this.mTitle;
        if (textView == null) {
            return;
        }
        textView.setContentDescription(charSequence);
    }

    public final void updateIconView() {
        ImageView imageView = this.mIconView;
        if (imageView == null) {
            return;
        }
        int i = this.mIconResId;
        if (i != 0) {
            imageView.setImageResource(i);
            return;
        }
        int i2 = this.mMuteIconResId;
        if (i2 == 0 || !this.mMuted || this.mZenMuted) {
            imageView.setImageDrawable(getIcon());
        } else {
            imageView.setImageResource(i2);
        }
    }

    public final void updateSuppressionText() {
        TextView textView = this.mSuppressionTextView;
        if (textView == null || this.mSeekBar == null) {
            return;
        }
        textView.setText(this.mSuppressionText);
        this.mSuppressionTextView.setVisibility(
                TextUtils.isEmpty(this.mSuppressionText) ^ true ? 0 : 8);
    }

    public VolumeSeekBarPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mJankMonitor = InteractionJankMonitor.getInstance();
        setLayoutResource(R.layout.preference_volume_slider);
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        this.mSeekBarVolumizerFactory = new SeekBarVolumizerFactory(context);
    }

    public VolumeSeekBarPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mJankMonitor = InteractionJankMonitor.getInstance();
        setLayoutResource(R.layout.preference_volume_slider);
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        this.mSeekBarVolumizerFactory = new SeekBarVolumizerFactory(context);
    }

    public VolumeSeekBarPreference(Context context) {
        super(context);
        this.mJankMonitor = InteractionJankMonitor.getInstance();
        setLayoutResource(R.layout.preference_volume_slider);
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        this.mSeekBarVolumizerFactory = new SeekBarVolumizerFactory(context);
    }
}
