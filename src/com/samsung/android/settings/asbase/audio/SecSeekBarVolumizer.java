package com.samsung.android.settings.asbase.audio;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.media.AudioAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.provider.Settings;
import android.service.notification.ZenModeConfig;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.SeekBar;

import com.android.internal.jank.InteractionJankMonitor;

import com.samsung.android.settings.asbase.utils.SeekBarUtil;
import com.samsung.android.settings.asbase.utils.SoundUtils;
import com.samsung.android.settings.asbase.widget.SecVolumeSeekBarPreference;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecSeekBarVolumizer
        implements SeekBar.OnSeekBarChangeListener, Handler.Callback {
    public static final long DURATION_TO_START_DELAYING;
    public static final long SET_STREAM_VOLUME_DELAY_MS;
    public static final long START_SAMPLE_DELAY_MS;
    public static long sStopVolumeTime;
    public boolean mAllowAlarms;
    public boolean mAllowMedia;
    public boolean mAllowRinger;
    public boolean mAllowSystem;
    public final AudioManager mAudioManager;
    public final SecVolumeSeekBarPreference.AnonymousClass3 mCallback;
    public final Context mContext;
    public final int mCurrentSimSlot;
    public Uri mDefaultUri;
    public Handler mHandler;
    public final int mLastWaitingToneVolume;
    public final int mMaxStreamVolume;
    public boolean mMuted;
    public final NotificationManager mNotificationManager;
    public final boolean mNotificationOrRing;
    public NotificationManager.Policy mNotificationPolicy;
    public final boolean mPlaySample;
    public Ringtone mRingtone;
    public SeekBar mSeekBar;
    public final int mStreamType;
    public boolean mSystemSampleStarted;
    public final TelecomManager mTelecomManager;
    public final Vibrator mVibrator;
    public final boolean mVoiceCapable;
    public Observer mVolumeObserver;
    public int mZenMode;
    public final H mUiHandler = new H();
    public final Receiver mReceiver = new Receiver();
    public int mLastProgress = -1;
    public ToneGenerator mToneGenerator = null;
    public boolean isRingerUpdatedToAudio = true;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class H extends Handler {
        public H() {}

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what == 1) {
                SecSeekBarVolumizer secSeekBarVolumizer = SecSeekBarVolumizer.this;
                if (secSeekBarVolumizer.mSeekBar != null) {
                    secSeekBarVolumizer.mLastProgress = message.arg1;
                    boolean booleanValue = ((Boolean) message.obj).booleanValue();
                    if (booleanValue != secSeekBarVolumizer.mMuted) {
                        secSeekBarVolumizer.mMuted = booleanValue;
                        SecVolumeSeekBarPreference.AnonymousClass3 anonymousClass3 =
                                secSeekBarVolumizer.mCallback;
                        if (anonymousClass3 != null) {
                            boolean isZenMuted = secSeekBarVolumizer.isZenMuted();
                            SecVolumeSeekBarPreference secVolumeSeekBarPreference =
                                    SecVolumeSeekBarPreference.this;
                            if (secVolumeSeekBarPreference.mMuted != booleanValue
                                    || secVolumeSeekBarPreference.mZenMuted != isZenMuted) {
                                secVolumeSeekBarPreference.mMuted = booleanValue;
                                secVolumeSeekBarPreference.mZenMuted = isZenMuted;
                            }
                        }
                    }
                    secSeekBarVolumizer.updateSeekBar();
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Observer extends ContentObserver {
        public Observer(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            super.onChange(z);
            SecSeekBarVolumizer secSeekBarVolumizer = SecSeekBarVolumizer.this;
            if (secSeekBarVolumizer.mStreamType != 8) {
                secSeekBarVolumizer.updateSlider();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Receiver extends BroadcastReceiver {
        public boolean mListening;

        public Receiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.media.VOLUME_CHANGED_ACTION".equals(action)) {
                int intExtra = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                SecSeekBarVolumizer secSeekBarVolumizer = SecSeekBarVolumizer.this;
                if (secSeekBarVolumizer.mStreamType == intExtra) {
                    secSeekBarVolumizer.updateSlider();
                    return;
                }
                return;
            }
            if ("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION".equals(action)) {
                SecSeekBarVolumizer.this.mAudioManager.getRingerModeInternal();
                SecSeekBarVolumizer secSeekBarVolumizer2 = SecSeekBarVolumizer.this;
                if (secSeekBarVolumizer2.mStreamType != 8) {
                    secSeekBarVolumizer2.updateSlider();
                    return;
                }
                return;
            }
            if ("android.media.STREAM_DEVICES_CHANGED_ACTION".equals(action)) {
                int intExtra2 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                SecSeekBarVolumizer secSeekBarVolumizer3 = SecSeekBarVolumizer.this;
                if (secSeekBarVolumizer3.mStreamType == intExtra2
                        && secSeekBarVolumizer3.isRingerUpdatedToAudio) {
                    secSeekBarVolumizer3.updateSlider();
                    return;
                }
                return;
            }
            if ("com.samsung.intent.action.DEFAULT_CS_SIM_CHANGED".equals(action)) {
                SecSeekBarVolumizer secSeekBarVolumizer4 = SecSeekBarVolumizer.this;
                int i = secSeekBarVolumizer4.mStreamType;
                if (i == 2) {
                    secSeekBarVolumizer4.mDefaultUri =
                            secSeekBarVolumizer4.mCurrentSimSlot == 1
                                    ? Settings.System.DEFAULT_RINGTONE_URI_2
                                    : Settings.System.DEFAULT_RINGTONE_URI;
                } else if (i == 5) {
                    secSeekBarVolumizer4.mDefaultUri =
                            secSeekBarVolumizer4.mCurrentSimSlot == 1
                                    ? Settings.System.DEFAULT_NOTIFICATION_URI_2
                                    : Settings.System.DEFAULT_NOTIFICATION_URI;
                }
                try {
                    Ringtone ringtone = secSeekBarVolumizer4.mRingtone;
                    if (ringtone != null) {
                        ringtone.setUri(secSeekBarVolumizer4.mDefaultUri);
                        SecSeekBarVolumizer.this.mRingtone.setAudioAttributes(
                                new AudioAttributes.Builder()
                                        .setInternalLegacyStreamType(
                                                SecSeekBarVolumizer.this.mStreamType)
                                        .build());
                        return;
                    }
                    return;
                } catch (Exception e) {
                    Log.d(
                            "SecSeekBarVolumizer",
                            "Exception happens in SIM_CHANGED_ACTION : " + e.toString());
                    return;
                }
            }
            if ("android.app.action.INTERRUPTION_FILTER_CHANGED".equals(action)) {
                SecSeekBarVolumizer secSeekBarVolumizer5 = SecSeekBarVolumizer.this;
                secSeekBarVolumizer5.mZenMode =
                        secSeekBarVolumizer5.mNotificationManager.getZenMode();
                SecSeekBarVolumizer.this.updateSlider();
            } else if ("android.app.action.NOTIFICATION_POLICY_CHANGED".equals(action)) {
                SecSeekBarVolumizer secSeekBarVolumizer6 = SecSeekBarVolumizer.this;
                secSeekBarVolumizer6.mNotificationPolicy =
                        secSeekBarVolumizer6.mNotificationManager.getNotificationPolicy();
                SecSeekBarVolumizer secSeekBarVolumizer7 = SecSeekBarVolumizer.this;
                int i2 = secSeekBarVolumizer7.mNotificationPolicy.priorityCategories;
                secSeekBarVolumizer7.mAllowAlarms = (i2 & 32) != 0;
                secSeekBarVolumizer7.mAllowMedia = (i2 & 64) != 0;
                secSeekBarVolumizer7.mAllowRinger =
                        !ZenModeConfig.areAllPriorityOnlyRingerSoundsMuted(r6);
                SecSeekBarVolumizer secSeekBarVolumizer8 = SecSeekBarVolumizer.this;
                secSeekBarVolumizer8.mAllowSystem =
                        (secSeekBarVolumizer8.mNotificationPolicy.priorityCategories & 128) != 0;
                secSeekBarVolumizer8.updateSlider();
            }
        }

        public final void setListening(boolean z) {
            if (this.mListening == z) {
                return;
            }
            this.mListening = z;
            if (!z) {
                SecSeekBarVolumizer.this.mContext.unregisterReceiver(this);
                return;
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION");
            intentFilter.addAction("android.app.action.INTERRUPTION_FILTER_CHANGED");
            intentFilter.addAction("android.app.action.NOTIFICATION_POLICY_CHANGED");
            intentFilter.addAction("android.media.STREAM_DEVICES_CHANGED_ACTION");
            intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
            intentFilter.addAction("com.samsung.intent.action.DEFAULT_CS_SIM_CHANGED");
            SecSeekBarVolumizer.this.mContext.registerReceiver(this, intentFilter, 2);
        }
    }

    static {
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        SET_STREAM_VOLUME_DELAY_MS = timeUnit.toMillis(500L);
        START_SAMPLE_DELAY_MS = timeUnit.toMillis(500L);
        DURATION_TO_START_DELAYING = timeUnit.toMillis(2000L);
    }

    public SecSeekBarVolumizer(
            Context context,
            int i,
            Uri uri,
            SecVolumeSeekBarPreference.AnonymousClass3 anonymousClass3) {
        Uri uri2;
        this.mLastWaitingToneVolume = -1;
        this.mContext = context;
        AudioManager audioManager = (AudioManager) context.getSystemService(AudioManager.class);
        this.mAudioManager = audioManager;
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NotificationManager.class);
        this.mNotificationManager = notificationManager;
        this.mVibrator = (Vibrator) context.getSystemService("vibrator");
        boolean shouldShowRingtoneVolume = audioManager.shouldShowRingtoneVolume();
        this.mVoiceCapable = shouldShowRingtoneVolume;
        NotificationManager.Policy consolidatedNotificationPolicy =
                notificationManager.getConsolidatedNotificationPolicy();
        this.mNotificationPolicy = consolidatedNotificationPolicy;
        int i2 = consolidatedNotificationPolicy.priorityCategories;
        boolean z = false;
        this.mAllowAlarms = (i2 & 32) != 0;
        this.mAllowMedia = (i2 & 64) != 0;
        this.mAllowRinger =
                !ZenModeConfig.areAllPriorityOnlyRingerSoundsMuted(consolidatedNotificationPolicy);
        this.mAllowSystem = (this.mNotificationPolicy.priorityCategories & 128) != 0;
        this.mStreamType = i;
        this.mTelecomManager = (TelecomManager) context.getSystemService("telecom");
        if (i == 3) {
            this.mMaxStreamVolume = 1500;
        } else {
            this.mMaxStreamVolume = audioManager.getStreamMaxVolume(i) * 100;
        }
        if (!shouldShowRingtoneVolume ? i == 5 : i == 2) {
            z = true;
        }
        this.mNotificationOrRing = z;
        if (z) {
            audioManager.getRingerModeInternal();
        }
        this.mZenMode = notificationManager.getZenMode();
        this.mCallback = anonymousClass3;
        this.mLastWaitingToneVolume =
                Settings.System.getInt(context.getContentResolver(), "volume_waiting_tone", 2);
        boolean isStreamMute = audioManager.isStreamMute(i);
        this.mMuted = isStreamMute;
        this.mPlaySample = true;
        boolean isZenMuted = isZenMuted();
        SecVolumeSeekBarPreference secVolumeSeekBarPreference = SecVolumeSeekBarPreference.this;
        if (secVolumeSeekBarPreference.mMuted != isStreamMute
                || secVolumeSeekBarPreference.mZenMuted != isZenMuted) {
            secVolumeSeekBarPreference.mMuted = isStreamMute;
            secVolumeSeekBarPreference.mZenMuted = isZenMuted;
        }
        int slotIndex =
                SubscriptionManager.getSlotIndex(SubscriptionManager.getDefaultSubscriptionId());
        this.mCurrentSimSlot = slotIndex;
        if (uri == null) {
            if (i == 2) {
                uri2 =
                        slotIndex == 1
                                ? Settings.System.DEFAULT_RINGTONE_URI_2
                                : Settings.System.DEFAULT_RINGTONE_URI;
            } else if (i == 5) {
                uri2 =
                        slotIndex == 1
                                ? Settings.System.DEFAULT_NOTIFICATION_URI_2
                                : Settings.System.DEFAULT_NOTIFICATION_URI;
            } else {
                uri =
                        i == 4
                                ? RingtoneManager.getActualDefaultRingtoneUri(context, 4)
                                : Settings.System.DEFAULT_ALARM_ALERT_URI;
            }
            uri = uri2;
        }
        this.mDefaultUri = uri;
    }

    public static boolean isDelay() {
        long currentTimeMillis = System.currentTimeMillis() - sStopVolumeTime;
        return currentTimeMillis >= 0 && currentTimeMillis < DURATION_TO_START_DELAYING;
    }

    public final int getImpliedLevel(int i) {
        int i2 = this.mMaxStreamVolume;
        int i3 = (i2 / 100) - 1;
        if (i == 0) {
            return 0;
        }
        return i == i2 ? i2 / 100 : ((int) ((i / i2) * i3)) + 1;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        int i = message.what;
        if (i == 0) {
            boolean z = this.mMuted;
            if (z && this.mLastProgress > 0) {
                this.mAudioManager.adjustStreamVolume(this.mStreamType, 100, 0);
            } else if (!z && this.mLastProgress == 0) {
                this.mAudioManager.adjustStreamVolume(this.mStreamType, -100, 0);
            }
            this.mAudioManager.setStreamVolume(this.mStreamType, this.mLastProgress, 1024);
            this.isRingerUpdatedToAudio = true;
            if (this.mLastProgress == 0 && !this.mVibrator.hasVibrator()) {
                int i2 = this.mStreamType;
                if (i2 == 2) {
                    this.mAudioManager.setRingerMode(0);
                } else if (!this.mVoiceCapable && i2 == 5) {
                    this.mAudioManager.setRingerMode(0);
                }
            }
        } else if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        Log.e(
                                "SecSeekBarVolumizer",
                                "invalid SeekBarVolumizer message: " + message.what);
                    } else if (isDelay()) {
                        Handler handler = this.mHandler;
                        if (handler != null) {
                            handler.removeMessages(4);
                            Handler handler2 = this.mHandler;
                            handler2.sendMessageDelayed(handler2.obtainMessage(4), 500L);
                        }
                    } else {
                        updateSlider();
                    }
                } else if (this.mPlaySample) {
                    synchronized (this) {
                        try {
                            Ringtone ringtone =
                                    RingtoneManager.getRingtone(this.mContext, this.mDefaultUri);
                            this.mRingtone = ringtone;
                            if (ringtone != null) {
                                ringtone.turnOffFadeIn();
                                if (this.mStreamType != 11) {
                                    this.mRingtone.setAudioAttributes(
                                            new AudioAttributes.Builder()
                                                    .setInternalLegacyStreamType(this.mStreamType)
                                                    .build());
                                } else {
                                    this.mRingtone.setAudioAttributes(
                                            new AudioAttributes.Builder()
                                                    .semAddAudioTag("BIXBY")
                                                    .setInternalLegacyStreamType(this.mStreamType)
                                                    .build());
                                }
                            }
                        } catch (Exception e) {
                            Log.d(
                                    "SecSeekBarVolumizer",
                                    "Exception happens in onInitSample() : " + e.toString());
                        } finally {
                        }
                    }
                }
            } else if (this.mPlaySample) {
                synchronized (this) {
                    try {
                        Ringtone ringtone2 = this.mRingtone;
                        if (ringtone2 != null) {
                            ringtone2.stop();
                        }
                    } finally {
                    }
                }
            }
        } else if (this.mPlaySample && !isSamplePlaying()) {
            SecVolumeSeekBarPreference.AnonymousClass3 anonymousClass3 = this.mCallback;
            if (anonymousClass3 != null) {
                SecVolumeSeekBarPreference secVolumeSeekBarPreference =
                        SecVolumeSeekBarPreference.this;
                if (secVolumeSeekBarPreference.mCallback != null
                        && SecVolumeSeekBarPreference.m1123$$Nest$mrequestAudioFocus(
                                secVolumeSeekBarPreference)) {
                    secVolumeSeekBarPreference.mCallback.onSampleStarting(this);
                }
            }
            synchronized (this) {
                if (this.mRingtone != null) {
                    try {
                        TelecomManager telecomManager = this.mTelecomManager;
                        if (telecomManager == null
                                || (!telecomManager.isRinging()
                                        && !this.mTelecomManager.isInCall())) {
                            int i3 = this.mStreamType;
                            if (i3 != 1
                                    && i3 != 8
                                    && i3 != 0
                                    && !this.mAudioManager.semIsFmRadioActive()) {
                                Log.d("SecSeekBarVolumizer", "sample : mRingtone.play()");
                                this.mRingtone.setAudioAttributes(
                                        new AudioAttributes.Builder(
                                                        this.mRingtone.getAudioAttributes())
                                                .setFlags(128)
                                                .build());
                                this.mRingtone.play();
                            }
                        }
                        Log.d(
                                "SecSeekBarVolumizer",
                                "don't play ringtone in volumepreference: return called");
                    } catch (Throwable th) {
                        Log.w(
                                "SecSeekBarVolumizer",
                                "Error playing ringtone, stream " + this.mStreamType,
                                th);
                    }
                }
            }
        }
        return true;
    }

    public final boolean isSamplePlaying() {
        boolean z;
        synchronized (this) {
            try {
                Ringtone ringtone = this.mRingtone;
                z = ringtone != null && ringtone.isPlaying();
            } finally {
            }
        }
        return z;
    }

    public final boolean isZenMuted() {
        int i;
        int i2;
        if ((this.mNotificationOrRing && this.mZenMode == 3) || (i = this.mZenMode) == 2) {
            return true;
        }
        if (i == 1) {
            if (!this.mAllowAlarms && this.mStreamType == 4) {
                return true;
            }
            if (!this.mAllowMedia && ((i2 = this.mStreamType) == 3 || i2 == 11)) {
                return true;
            }
            if (!this.mAllowRinger
                    && this.mStreamType == 5
                    && this.mAudioManager.shouldShowRingtoneVolume()) {
                return true;
            }
            if (!this.mAllowSystem && this.mStreamType == 1) {
                return true;
            }
        }
        return false;
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (z) {
            if (this.mStreamType != 3) {
                i = getImpliedLevel(i);
            } else if (i != 0 && (i = i / 10) == 0) {
                i = 1;
            }
            Log.d(
                    "SecSeekBarVolumizer",
                    "onProgressChanged : stream = " + this.mStreamType + ", progress = " + i);
            ToneGenerator toneGenerator = this.mToneGenerator;
            if (toneGenerator != null) {
                toneGenerator.setVolume(0.0f);
                this.mToneGenerator.stopTone();
            }
            int i2 = this.mStreamType;
            if (i2 == 2) {
                postSetVolume(i);
                this.isRingerUpdatedToAudio = false;
            } else if (i2 == 5) {
                if (i == 0) {
                    postStopSample();
                }
                this.mAudioManager.setStreamVolume(5, i, 0);
                if (i == 0 && !this.mVibrator.hasVibrator() && !this.mVoiceCapable) {
                    this.mAudioManager.setRingerMode(0);
                }
                this.mLastProgress = i;
            } else if (i2 == 1) {
                Log.d("SecSeekBarVolumizer", "******onProgressChanged*****");
                SecVolumeSeekBarPreference.AnonymousClass3 anonymousClass3 = this.mCallback;
                if (anonymousClass3 != null) {
                    SecVolumeSeekBarPreference secVolumeSeekBarPreference =
                            SecVolumeSeekBarPreference.this;
                    if (secVolumeSeekBarPreference.mCallback != null
                            && SecVolumeSeekBarPreference.m1123$$Nest$mrequestAudioFocus(
                                    secVolumeSeekBarPreference)) {
                        secVolumeSeekBarPreference.mCallback.onSampleStarting(this);
                    }
                }
                this.mAudioManager.setStreamVolume(1, i, 0);
                this.mContext.getOpPackageName();
                this.mContext.getAttributionTag();
                TelecomManager telecomManager = this.mTelecomManager;
                if (telecomManager == null
                        || !(telecomManager.isRinging() || this.mTelecomManager.isInCall())) {
                    if (!this.mAudioManager.semIsFmRadioActive() && this.mLastProgress != i) {
                        this.mAudioManager.playSoundEffect(100, i);
                        this.mSystemSampleStarted = true;
                        Log.d("SecSeekBarVolumizer", "sample : playSoundEffect()");
                    } else if (this.mLastProgress == i) {
                        this.mSystemSampleStarted = false;
                    }
                    this.mLastProgress = i;
                } else {
                    Log.d("SecSeekBarVolumizer", "don't play STREAM_SYSTEM in volumepreference.");
                }
                Log.d(
                        "SecSeekBarVolumizer",
                        "onProgressChanged : AudioManager.STREAM_SYSTEM["
                                + this.mAudioManager.getStreamVolume(1)
                                + "]");
            } else if (i2 == 8) {
                Settings.System.putInt(
                        this.mContext.getContentResolver(), "volume_waiting_tone", i);
                if (this.mToneGenerator == null) {
                    this.mToneGenerator = new ToneGenerator(8, 0);
                }
                this.mToneGenerator.setVolume(this.mAudioManager.semGetSituationVolume(15, 0));
                this.mToneGenerator.startTone(22, 300);
                this.mLastProgress = i;
            } else if (i2 == 3) {
                this.mAudioManager.semSetFineVolume(3, i, 0);
                this.mLastProgress = i;
            } else {
                postSetVolume(i);
            }
            int i3 = this.mStreamType;
            if (i3 != 1
                    && i3 != 8
                    && i3 != 0
                    && !isSamplePlaying()
                    && (!this.mAudioManager.isMusicActive() || this.mStreamType != 3)) {
                TelecomManager telecomManager2 = this.mTelecomManager;
                if (telecomManager2 != null
                        && (telecomManager2.isRinging() || this.mTelecomManager.isInCall())) {
                    Log.d(
                            "SecSeekBarVolumizer",
                            "don't play ringtone in volumepreference: return called");
                } else if (this.mHandler != null) {
                    boolean z2 =
                            ((TelephonyManager) this.mContext.getSystemService("phone"))
                                            .getCallState()
                                    != 0;
                    int mode = this.mAudioManager.getMode();
                    boolean z3 = mode == 3 || mode == 2;
                    if (!z2 && !z3 && (!isSamplePlaying() || this.mStreamType != 11)) {
                        this.mHandler.removeMessages(1);
                        Handler handler = this.mHandler;
                        handler.sendMessageDelayed(
                                handler.obtainMessage(1),
                                isSamplePlaying() ? 1000L : isDelay() ? START_SAMPLE_DELAY_MS : 0L);
                    }
                }
            }
        }
        SecVolumeSeekBarPreference.AnonymousClass3 anonymousClass32 = this.mCallback;
        if (anonymousClass32 != null) {
            SecVolumeSeekBarPreference secVolumeSeekBarPreference2 =
                    SecVolumeSeekBarPreference.this;
            SecVolumeSeekBarPreference.Callback callback = secVolumeSeekBarPreference2.mCallback;
            if (callback != null) {
                callback.onStreamValueChanged(secVolumeSeekBarPreference2.mStream, i);
            }
            if (z) {
                int i4 = secVolumeSeekBarPreference2.mStream;
                SeekBarUtil.vibrateIfNeeded(seekBar, i4, (i4 == 3 ? 10 : 100) * i);
            }
            secVolumeSeekBarPreference2.setContentDescriptionForAutomationTest$1(i);
            int i5 = secVolumeSeekBarPreference2.mStream;
            if (i5 == 2) {
                LoggingHelper.insertEventLogging(4006, 4007);
                return;
            }
            if (i5 == 5) {
                LoggingHelper.insertEventLogging(4006, 4009);
                return;
            }
            if (i5 == 3) {
                LoggingHelper.insertEventLogging(4006, 4008);
            } else if (i5 == 1) {
                LoggingHelper.insertEventLogging(4006, 4010);
            } else if (i5 == 11) {
                LoggingHelper.insertEventLogging(4006, 7254);
            }
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onStartTrackingTouch(SeekBar seekBar) {
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
        SecVolumeSeekBarPreference.AnonymousClass3 anonymousClass3 = this.mCallback;
        if (anonymousClass3 != null) {
            SecVolumeSeekBarPreference.this.mJankMonitor.end(53);
        }
        if (this.mStreamType == 1 && !this.mSystemSampleStarted) {
            int impliedLevel = getImpliedLevel(seekBar.getProgress());
            TelecomManager telecomManager = this.mTelecomManager;
            if (telecomManager != null
                    && (telecomManager.isRinging() || this.mTelecomManager.isInCall())) {
                Log.d(
                        "SecSeekBarVolumizer",
                        "[onStopTrackingTouch]don't play STREAM_SYSTEM in volumepreference.");
            } else if (!this.mAudioManager.semIsFmRadioActive()) {
                this.mAudioManager.playSoundEffect(100, impliedLevel);
                Log.d(
                        "SecSeekBarVolumizer",
                        "[onStopTrackingTouch]sample : playSoundEffect() : " + impliedLevel);
            }
        }
        int progress = seekBar.getProgress();
        if (this.mStreamType != 3) {
            seekBar.setProgress(getImpliedLevel(progress) * 100, seekBar.hasWindowFocus());
            return;
        }
        if (progress != 0) {
            int i = progress / 10;
            if (i == 0) {
                i = 1;
            }
            int i2 = i * 10;
            if (Settings.System.getInt(this.mContext.getContentResolver(), "volumelimit_on", 0)
                    == 1) {
                new Handler(Looper.getMainLooper())
                        .postDelayed(
                                new Runnable() { // from class:
                                                 // com.samsung.android.settings.asbase.audio.SecSeekBarVolumizer.1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        SecSeekBarVolumizer.this.updateSeekBar();
                                    }
                                },
                                500L);
            } else {
                seekBar.setProgress(i2);
            }
        }
    }

    public final void postSetVolume(int i) {
        Handler handler = this.mHandler;
        if (handler == null) {
            return;
        }
        this.mLastProgress = i;
        handler.removeMessages(0);
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(4);
        Handler handler2 = this.mHandler;
        handler2.sendMessageDelayed(
                handler2.obtainMessage(0), isDelay() ? SET_STREAM_VOLUME_DELAY_MS : 0L);
    }

    public final void postStopSample() {
        if (this.mHandler == null) {
            return;
        }
        int i = this.mStreamType;
        if (i == 0 || i == 2 || i == 5 || i == 4) {
            sStopVolumeTime = System.currentTimeMillis();
        }
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(2));
    }

    public final void stop() {
        if (this.mHandler == null) {
            return;
        }
        postStopSample();
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
        this.mContext.getContentResolver().unregisterContentObserver(this.mVolumeObserver);
        this.mReceiver.setListening(false);
        this.mSeekBar.setOnSeekBarChangeListener(null);
        this.mHandler.getLooper().quitSafely();
        this.mHandler = null;
        this.mVolumeObserver = null;
    }

    public final void updateSeekBar() {
        int i;
        int i2;
        int i3;
        this.mMuted = this.mAudioManager.isStreamMute(this.mStreamType);
        int ringerModeInternal = this.mAudioManager.getRingerModeInternal();
        boolean z = this.mNotificationOrRing;
        if (z && ringerModeInternal != 2) {
            this.mSeekBar.setProgress(0);
            return;
        }
        if (this.mMuted
                && !z
                && (i3 = this.mStreamType) != 3
                && i3 != 11
                && ringerModeInternal != 2) {
            this.mSeekBar.setEnabled(false);
            this.mSeekBar.setProgress(0);
            return;
        }
        if (this.mStreamType == 3) {
            int semGetFineVolume = this.mAudioManager.semGetFineVolume(3);
            int progress = this.mSeekBar.getProgress();
            this.mLastProgress = progress;
            if (progress != 0) {
                int i4 = progress / 10;
                if (i4 == 0) {
                    i4 = 1;
                }
                this.mLastProgress = i4;
            }
            if (this.mLastProgress != semGetFineVolume) {
                this.mSeekBar.setProgress(semGetFineVolume * 10);
                this.mLastProgress = semGetFineVolume;
            }
        } else {
            int impliedLevel = getImpliedLevel(this.mSeekBar.getProgress());
            this.mLastProgress = impliedLevel;
            if (impliedLevel != this.mAudioManager.getStreamVolume(this.mStreamType)) {
                this.mSeekBar.setProgress(
                        this.mAudioManager.getStreamVolume(this.mStreamType) * 100);
                this.mLastProgress = getImpliedLevel(this.mSeekBar.getProgress());
            }
        }
        if (!isZenMuted()
                && Settings.System.getInt(this.mContext.getContentResolver(), "all_sound_off", 0)
                        == 0) {
            boolean z2 = false;
            boolean z3 = false;
            for (AudioDeviceInfo audioDeviceInfo : this.mAudioManager.getDevices(2)) {
                if (audioDeviceInfo.getType() == 26) {
                    z3 = true;
                } else if (audioDeviceInfo.getType() == 30) {
                    z2 = true;
                }
            }
            char c = z2 ? z3 ? (char) 1 : (char) 2 : (char) 0;
            if (c != 2
                    ? c != 1 || ((i = this.mStreamType) != 5 && i != 1)
                    : (i2 = this.mStreamType) != 3 && i2 != 5 && i2 != 1 && i2 != 11) {
                if (this.mStreamType != 4 || SoundUtils.isUseGlobalAlarmVolume(this.mContext)) {
                    if (this.mSeekBar.isEnabled()) {
                        return;
                    }
                    this.mSeekBar.setEnabled(true);
                    return;
                }
            }
        }
        if (this.mSeekBar.isEnabled()) {
            this.mSeekBar.setEnabled(false);
        }
    }

    public final void updateSlider() {
        AudioManager audioManager;
        if (this.mSeekBar == null || (audioManager = this.mAudioManager) == null) {
            return;
        }
        this.mUiHandler
                .obtainMessage(
                        1,
                        audioManager.getStreamVolume(this.mStreamType),
                        this.mAudioManager.getLastAudibleStreamVolume(this.mStreamType),
                        Boolean.valueOf(this.mAudioManager.isStreamMute(this.mStreamType)))
                .sendToTarget();
    }
}
