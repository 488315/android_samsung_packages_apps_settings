package com.samsung.android.settings.asbase.widget;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.preference.PreferenceViewHolder;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.widget.SeekBarPreference;
import com.samsung.android.settings.asbase.audio.SecSeekBarVolumizer;
import com.samsung.android.settings.asbase.audio.SecSeekBarVolumizerDTMF;
import com.sec.ims.presence.ServiceTuple;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecVolumeSeekBarPreference extends SeekBarPreference {
    public final AnonymousClass2 mAudioFocusListener;
    public final AudioManager mAudioManager;
    public Callback mCallback;
    public boolean mFirstBind;
    public AudioFocusRequest mFocusRequest;
    public final InteractionJankMonitor mJankMonitor;
    public int mMuteIconResId;
    public boolean mMuted;
    public SeekBar mSeekBar;
    public boolean mStopped;
    public int mStream;
    public String mSuppressionText;
    public TextView mSuppressionTextView;
    public final UserManager mUserManager;
    public SecVolumeIcon mVolumeIcon;
    public SecSeekBarVolumizer mVolumizer;
    public SecSeekBarVolumizerDTMF mVolumizerDTMF;
    public boolean mZenMuted;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.widget.SecVolumeSeekBarPreference$3, reason: invalid class name */
    public final class AnonymousClass3 {
        public /* synthetic */ AnonymousClass3() {
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Callback {
        void onSampleStarting(SecSeekBarVolumizer secSeekBarVolumizer);

        void onStreamValueChanged(int i, int i2);
    }

    /* renamed from: -$$Nest$mrequestAudioFocus, reason: not valid java name */
    public static boolean m1123$$Nest$mrequestAudioFocus(SecVolumeSeekBarPreference secVolumeSeekBarPreference) {
        secVolumeSeekBarPreference.getClass();
        int i = 0;
        for (int i2 = 0; i2 < 5 && (i = secVolumeSeekBarPreference.mAudioManager.requestAudioFocus(secVolumeSeekBarPreference.mFocusRequest)) != 1; i2++) {
        }
        return i != 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v3, types: [android.media.AudioManager$OnAudioFocusChangeListener, com.samsung.android.settings.asbase.widget.SecVolumeSeekBarPreference$2] */
    public SecVolumeSeekBarPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mJankMonitor = InteractionJankMonitor.getInstance();
        this.mFirstBind = false;
        ?? r3 = new AudioManager.OnAudioFocusChangeListener() { // from class: com.samsung.android.settings.asbase.widget.SecVolumeSeekBarPreference.2
            @Override // android.media.AudioManager.OnAudioFocusChangeListener
            public final void onAudioFocusChange(int i3) {
                SecSeekBarVolumizer secSeekBarVolumizer;
                if ((i3 == -3 || i3 == -2 || i3 == -1) && (secSeekBarVolumizer = SecVolumeSeekBarPreference.this.mVolumizer) != null) {
                    secSeekBarVolumizer.postStopSample();
                }
            }
        };
        this.mAudioFocusListener = r3;
        this.mFocusRequest = new AudioFocusRequest.Builder(2).setOnAudioFocusChangeListener(r3).setAudioAttributes(new AudioAttributes.Builder().setUsage(1).setContentType(2).build()).setWillPauseWhenDucked(true).build();
        setLayoutResource(R.layout.sec_preference_volume_slider);
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        this.mUserManager = (UserManager) context.getSystemService("user");
    }

    @Override // com.android.settings.widget.SeekBarPreference, com.android.settingslib.RestrictedPreference, com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mSeekBar = (SeekBar) preferenceViewHolder.findViewById(android.R.id.textClassifier);
        this.mSuppressionTextView = (TextView) preferenceViewHolder.findViewById(R.id.suppression_text);
        this.mVolumeIcon = (SecVolumeIcon) preferenceViewHolder.findViewById(R.id.basic_volume_icon);
        SecVolumeIconMotion secVolumeIconMotion = new SecVolumeIconMotion(getContext());
        SecVolumeIcon secVolumeIcon = this.mVolumeIcon;
        if (secVolumeIcon != null && this.mSeekBar != null) {
            secVolumeIcon.initialize(secVolumeIconMotion, this.mStream);
        }
        this.mSeekBar.semSetMode(5);
        int i = this.mStream;
        if (i == 10 || i == 4) {
            this.mSeekBar.semSetMin(100);
        }
        setupCustomInit();
    }

    public final void setContentDescriptionForAutomationTest$1(int i) {
        if (this.mSeekBar == null || Utils.isTalkBackEnabled(getContext())) {
            return;
        }
        this.mSeekBar.setContentDescription(Integer.toString(i));
    }

    public final void setupCustomInit() {
        Uri uri;
        if (this.mSeekBar == null) {
            return;
        }
        if (this.mFocusRequest == null) {
            this.mFocusRequest = new AudioFocusRequest.Builder(2).setOnAudioFocusChangeListener(this.mAudioFocusListener).setAudioAttributes(new AudioAttributes.Builder().setUsage(1).setContentType(2).build()).setWillPauseWhenDucked(true).build();
        }
        int i = this.mStream;
        if (i == 8) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3();
            if (this.mVolumizerDTMF == null) {
                this.mVolumizerDTMF = new SecSeekBarVolumizerDTMF(getContext(), anonymousClass3);
            }
        } else {
            AnonymousClass3 anonymousClass32 = new AnonymousClass3();
            if (i == 3) {
                uri = Uri.parse("file:///system/media/audio/ui/Media_preview_Over_the_horizon.ogg");
                Log.w("SecVolumeSeekBarPreference", "getMediaVolumeUri: " + uri.toString());
            } else {
                uri = null;
            }
            if (i == 11) {
                uri = Uri.parse("file:///system/media/audio/ui/Bixby_BOS.ogg");
                Log.w("SecVolumeSeekBarPreference", "getBixbyVolumeUri: " + uri.toString());
            }
            if (this.mVolumizer == null) {
                this.mVolumizer = new SecSeekBarVolumizer(getContext(), this.mStream, uri, anonymousClass32);
            }
        }
        if (this.mVolumizerDTMF == null && this.mStream == 8) {
            this.mVolumizerDTMF = new SecSeekBarVolumizerDTMF(getContext(), null);
        }
        SecSeekBarVolumizerDTMF secSeekBarVolumizerDTMF = this.mVolumizerDTMF;
        if (secSeekBarVolumizerDTMF != null) {
            secSeekBarVolumizerDTMF.mSeekBar = this.mSeekBar;
            int i2 = Settings.System.getInt(secSeekBarVolumizerDTMF.mContext.getContentResolver(), "volume_waiting_tone", 2) * 1000;
            secSeekBarVolumizerDTMF.mSeekBar.setMax(ImsProfile.DEFAULT_DEREG_TIMEOUT);
            secSeekBarVolumizerDTMF.mSeekBar.setProgress(i2);
            secSeekBarVolumizerDTMF.mSeekBar.setOnSeekBarChangeListener(secSeekBarVolumizerDTMF);
        }
        if (this.mVolumizer != null) {
            if (this.mUserManager.hasUserRestriction("no_adjust_volume")) {
                this.mSeekBar.setEnabled(false);
            } else {
                SecSeekBarVolumizer secSeekBarVolumizer = this.mVolumizer;
                if (secSeekBarVolumizer.mHandler == null) {
                    HandlerThread handlerThread = new HandlerThread("SecSeekBarVolumizer.CallbackHandler");
                    handlerThread.start();
                    Handler handler = new Handler(handlerThread.getLooper(), secSeekBarVolumizer);
                    secSeekBarVolumizer.mHandler = handler;
                    handler.sendEmptyMessage(3);
                    secSeekBarVolumizer.mVolumeObserver = new SecSeekBarVolumizer.Observer(secSeekBarVolumizer, secSeekBarVolumizer.mHandler);
                    secSeekBarVolumizer.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(Settings.System.VOLUME_SETTINGS_INT[secSeekBarVolumizer.mStreamType]), false, secSeekBarVolumizer.mVolumeObserver);
                    secSeekBarVolumizer.mReceiver.setListening(true);
                }
            }
            SecSeekBarVolumizer secSeekBarVolumizer2 = this.mVolumizer;
            SeekBar seekBar = this.mSeekBar;
            SeekBar seekBar2 = secSeekBarVolumizer2.mSeekBar;
            if (seekBar2 != null) {
                seekBar2.setOnSeekBarChangeListener(null);
            }
            secSeekBarVolumizer2.mSeekBar = seekBar;
            seekBar.setOnSeekBarChangeListener(null);
            if (secSeekBarVolumizer2.mStreamType == 8) {
                seekBar.setMax(4);
                SeekBar seekBar3 = secSeekBarVolumizer2.mSeekBar;
                int i3 = secSeekBarVolumizer2.mLastProgress;
                if (i3 <= -1) {
                    i3 = secSeekBarVolumizer2.mLastWaitingToneVolume;
                }
                seekBar3.setProgress(i3);
            } else {
                secSeekBarVolumizer2.mSeekBar.setMax(secSeekBarVolumizer2.mMaxStreamVolume);
                secSeekBarVolumizer2.updateSeekBar();
            }
            secSeekBarVolumizer2.mSeekBar.setOnSeekBarChangeListener(secSeekBarVolumizer2);
        }
        updateSuppressionText();
        if (this.mFirstBind) {
            new Handler().postDelayed(new SecVolumeSeekBarPreference$$ExternalSyntheticLambda0(this), 50L);
            this.mFirstBind = false;
        }
        if (!isEnabled()) {
            this.mSeekBar.setEnabled(false);
            SecSeekBarVolumizer secSeekBarVolumizer3 = this.mVolumizer;
            if (secSeekBarVolumizer3 != null) {
                secSeekBarVolumizer3.stop();
            }
            SecSeekBarVolumizerDTMF secSeekBarVolumizerDTMF2 = this.mVolumizerDTMF;
            if (secSeekBarVolumizerDTMF2 != null) {
                secSeekBarVolumizerDTMF2.stop();
            }
        }
        if (this.mStream == 3) {
            setContentDescriptionForAutomationTest$1(this.mSeekBar.getProgress() / 10);
        } else {
            setContentDescriptionForAutomationTest$1(this.mSeekBar.getProgress() / 100);
        }
    }

    public final void showIcon(int i) {
        SecVolumeIcon secVolumeIcon = this.mVolumeIcon;
        if (secVolumeIcon != null) {
            secVolumeIcon.updateLayout(false);
        }
    }

    public final void updateOverlapPointForDualColor(int i) {
        SeekBar seekBar = this.mSeekBar;
        if (seekBar != null && seekBar.isEnabled()) {
            this.mSeekBar.semSetOverlapPointForDualColor(i);
            this.mSeekBar.invalidate();
        }
        SecVolumeIcon secVolumeIcon = this.mVolumeIcon;
        if (secVolumeIcon != null) {
            secVolumeIcon.updateLayout(false);
        }
    }

    public final void updateSuppressionText() {
        TextView textView = this.mSuppressionTextView;
        if (textView == null || this.mSeekBar == null) {
            return;
        }
        textView.setText(this.mSuppressionText);
        this.mSuppressionTextView.setVisibility(TextUtils.isEmpty(this.mSuppressionText) ^ true ? 0 : 8);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v3, types: [android.media.AudioManager$OnAudioFocusChangeListener, com.samsung.android.settings.asbase.widget.SecVolumeSeekBarPreference$2] */
    public SecVolumeSeekBarPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mJankMonitor = InteractionJankMonitor.getInstance();
        this.mFirstBind = false;
        ?? r4 = new AudioManager.OnAudioFocusChangeListener() { // from class: com.samsung.android.settings.asbase.widget.SecVolumeSeekBarPreference.2
            @Override // android.media.AudioManager.OnAudioFocusChangeListener
            public final void onAudioFocusChange(int i3) {
                SecSeekBarVolumizer secSeekBarVolumizer;
                if ((i3 == -3 || i3 == -2 || i3 == -1) && (secSeekBarVolumizer = SecVolumeSeekBarPreference.this.mVolumizer) != null) {
                    secSeekBarVolumizer.postStopSample();
                }
            }
        };
        this.mAudioFocusListener = r4;
        this.mFocusRequest = new AudioFocusRequest.Builder(2).setOnAudioFocusChangeListener(r4).setAudioAttributes(new AudioAttributes.Builder().setUsage(1).setContentType(2).build()).setWillPauseWhenDucked(true).build();
        setLayoutResource(R.layout.sec_preference_volume_slider);
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        this.mUserManager = (UserManager) context.getSystemService("user");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v3, types: [android.media.AudioManager$OnAudioFocusChangeListener, com.samsung.android.settings.asbase.widget.SecVolumeSeekBarPreference$2] */
    public SecVolumeSeekBarPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mJankMonitor = InteractionJankMonitor.getInstance();
        this.mFirstBind = false;
        ?? r5 = new AudioManager.OnAudioFocusChangeListener() { // from class: com.samsung.android.settings.asbase.widget.SecVolumeSeekBarPreference.2
            @Override // android.media.AudioManager.OnAudioFocusChangeListener
            public final void onAudioFocusChange(int i3) {
                SecSeekBarVolumizer secSeekBarVolumizer;
                if ((i3 == -3 || i3 == -2 || i3 == -1) && (secSeekBarVolumizer = SecVolumeSeekBarPreference.this.mVolumizer) != null) {
                    secSeekBarVolumizer.postStopSample();
                }
            }
        };
        this.mAudioFocusListener = r5;
        this.mFocusRequest = new AudioFocusRequest.Builder(2).setOnAudioFocusChangeListener(r5).setAudioAttributes(new AudioAttributes.Builder().setUsage(1).setContentType(2).build()).setWillPauseWhenDucked(true).build();
        setLayoutResource(R.layout.sec_preference_volume_slider);
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        this.mUserManager = (UserManager) context.getSystemService("user");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.media.AudioManager$OnAudioFocusChangeListener, com.samsung.android.settings.asbase.widget.SecVolumeSeekBarPreference$2] */
    public SecVolumeSeekBarPreference(Context context) {
        super(context);
        this.mJankMonitor = InteractionJankMonitor.getInstance();
        this.mFirstBind = false;
        ?? r0 = new AudioManager.OnAudioFocusChangeListener() { // from class: com.samsung.android.settings.asbase.widget.SecVolumeSeekBarPreference.2
            @Override // android.media.AudioManager.OnAudioFocusChangeListener
            public final void onAudioFocusChange(int i3) {
                SecSeekBarVolumizer secSeekBarVolumizer;
                if ((i3 == -3 || i3 == -2 || i3 == -1) && (secSeekBarVolumizer = SecVolumeSeekBarPreference.this.mVolumizer) != null) {
                    secSeekBarVolumizer.postStopSample();
                }
            }
        };
        this.mAudioFocusListener = r0;
        this.mFocusRequest = new AudioFocusRequest.Builder(2).setOnAudioFocusChangeListener(r0).setAudioAttributes(new AudioAttributes.Builder().setUsage(1).setContentType(2).build()).setWillPauseWhenDucked(true).build();
        setLayoutResource(R.layout.sec_preference_volume_slider);
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        this.mUserManager = (UserManager) context.getSystemService("user");
    }
}
