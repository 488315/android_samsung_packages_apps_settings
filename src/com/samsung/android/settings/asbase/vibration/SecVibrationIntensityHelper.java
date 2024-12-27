package com.samsung.android.settings.asbase.vibration;

import android.content.Context;
import android.media.Ringtone;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecVibrationIntensityHelper {
    public VibrationEffect mEffect;
    public SecVibrationIntensityMediaController mMediaController;
    public SecVibrationIntensityNotificationController mNotificationController;
    public Ringtone mRingtone;
    public SecVibrationIntensityIncomingCallController mRingtoneController;
    public SecVibrationIntensityTouchVibrationController mTouchController;
    public final Vibrator mVibrator;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final Handler mIconHandler = new Handler(Looper.getMainLooper(), new IconCallback());
    public final AnonymousClass1 mVibSeekBarTouchListener = new AnonymousClass1();
    public boolean mStopFlag = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.vibration.SecVibrationIntensityHelper$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class IconCallback implements Handler.Callback {
        public IconCallback() {}

        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            SecVibrationIntensityIncomingCallController secVibrationIntensityIncomingCallController;
            int i = message.what;
            if (i == 6) {
                SecVibrationIntensityMediaController secVibrationIntensityMediaController =
                        SecVibrationIntensityHelper.this.mMediaController;
                if (secVibrationIntensityMediaController == null) {
                    return true;
                }
                secVibrationIntensityMediaController.updatePreferenceIcon(message.arg1);
                return true;
            }
            if (i == 7) {
                SecVibrationIntensityTouchVibrationController
                        secVibrationIntensityTouchVibrationController =
                                SecVibrationIntensityHelper.this.mTouchController;
                if (secVibrationIntensityTouchVibrationController == null) {
                    return true;
                }
                secVibrationIntensityTouchVibrationController.updatePreferenceIcon(message.arg1);
                return true;
            }
            if (i != 8) {
                if (i != 10
                        || (secVibrationIntensityIncomingCallController =
                                        SecVibrationIntensityHelper.this.mRingtoneController)
                                == null) {
                    return true;
                }
                secVibrationIntensityIncomingCallController.updatePreferenceIcon(message.arg1);
                return true;
            }
            SecVibrationIntensityNotificationController
                    secVibrationIntensityNotificationController =
                            SecVibrationIntensityHelper.this.mNotificationController;
            if (secVibrationIntensityNotificationController == null) {
                return true;
            }
            secVibrationIntensityNotificationController.updatePreferenceIcon(message.arg1);
            return true;
        }
    }

    public SecVibrationIntensityHelper(Context context) {
        this.mVibrator =
                ((VibratorManager) context.getSystemService("vibrator_manager"))
                        .getDefaultVibrator();
    }

    public final void stopVibration() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        Vibrator vibrator = this.mVibrator;
        if (vibrator != null) {
            vibrator.cancel();
        }
        Ringtone ringtone = this.mRingtone;
        if (ringtone != null) {
            ringtone.stop();
        }
    }
}
