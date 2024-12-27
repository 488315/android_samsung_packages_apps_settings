package com.samsung.android.settings.theftprotection.timer;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.samsung.android.settings.theftprotection.TheftProtectionConstants;
import com.samsung.android.settings.theftprotection.logging.Log;

import java.lang.ref.WeakReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ProtectionTimerManager {
    public static NoLeakCountDownTimer sCountDownTimer;
    public static ProtectionTimerManager sInstance;
    public static final Object sLock = new Object();
    public boolean mIsPause;
    public ProtectionTimerFragment mTimer;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NoLeakCountDownTimer extends CountDownTimer {
        public final WeakReference mManager;

        public NoLeakCountDownTimer(
                ProtectionTimerManager protectionTimerManager, long j, long j2) {
            super(j, j2);
            this.mManager = new WeakReference(protectionTimerManager);
        }

        @Override // android.os.CountDownTimer
        public final void onFinish() {
            Log.d("ProtectionTimerManager", "CountDownTimer onFinish()");
            ProtectionTimerManager protectionTimerManager =
                    (ProtectionTimerManager) this.mManager.get();
            if (protectionTimerManager != null) {
                ProtectionTimerData.sRemainMillis = 0L;
                ProtectionTimerManager.m1279$$Nest$mupdateTime(protectionTimerManager);
                ProtectionTimerFragment protectionTimerFragment = protectionTimerManager.mTimer;
                if (protectionTimerFragment != null) {
                    protectionTimerFragment.finish();
                }
            }
        }

        @Override // android.os.CountDownTimer
        public final void onTick(long j) {
            ProtectionTimerManager protectionTimerManager =
                    (ProtectionTimerManager) this.mManager.get();
            if (protectionTimerManager != null) {
                ProtectionTimerData.sRemainMillis = j;
                ProtectionTimerManager.m1279$$Nest$mupdateTime(protectionTimerManager);
            }
        }
    }

    /* renamed from: -$$Nest$mupdateTime, reason: not valid java name */
    public static void m1279$$Nest$mupdateTime(ProtectionTimerManager protectionTimerManager) {
        ProtectionTimerTimeView protectionTimerTimeView;
        int i;
        ProtectionTimerFragment protectionTimerFragment = protectionTimerManager.mTimer;
        if (protectionTimerFragment == null || protectionTimerManager.mIsPause) {
            return;
        }
        long j = ProtectionTimerData.sRemainMillis;
        long j2 = TheftProtectionConstants.MINUTE_MILLIS;
        int i2 = (int) (j / j2);
        long j3 = TheftProtectionConstants.SECOND_MILLIS;
        int i3 = (int) ((j % j2) / j3);
        if (((int) (j % j3)) <= 200
                && (protectionTimerTimeView = protectionTimerFragment.mTimeView) != null) {
            TextView textView = protectionTimerTimeView.mSecondPostfix;
            if (textView != null && (i = i3 % 10) != protectionTimerTimeView.mSecond % 10) {
                if (i == 9) {
                    protectionTimerTimeView.setNumber(9, textView);
                } else {
                    protectionTimerTimeView.setNumber(i, textView);
                }
            }
            int i4 = i3 / 10;
            if (i4 == protectionTimerTimeView.mSecond / 10) {
                protectionTimerTimeView.setNumber(i4, protectionTimerTimeView.mSecondPrefix);
            } else if (i4 == 5) {
                protectionTimerTimeView.setNumber(5, protectionTimerTimeView.mSecondPrefix);
            } else {
                protectionTimerTimeView.setNumber(i4, protectionTimerTimeView.mSecondPrefix);
            }
            int i5 = i2 % 10;
            if (i5 == protectionTimerTimeView.mMinute % 10) {
                protectionTimerTimeView.setNumber(i5, protectionTimerTimeView.mMinutePostfix);
            } else if (i5 == 9) {
                protectionTimerTimeView.setNumber(9, protectionTimerTimeView.mMinutePostfix);
            } else {
                protectionTimerTimeView.setNumber(i5, protectionTimerTimeView.mMinutePostfix);
            }
            int i6 = i2 / 10;
            if (i6 == protectionTimerTimeView.mMinute / 10) {
                protectionTimerTimeView.setNumber(i6, protectionTimerTimeView.mMinutePrefix);
            } else if (i6 == 5) {
                protectionTimerTimeView.setNumber(5, protectionTimerTimeView.mMinutePrefix);
            } else {
                protectionTimerTimeView.setNumber(i6, protectionTimerTimeView.mMinutePrefix);
            }
            protectionTimerTimeView.mSecond = i3;
            protectionTimerTimeView.mMinute = i2;
            protectionTimerTimeView.setTimeViewContentDescription();
        }
        if (protectionTimerFragment.mCircleView != null) {
            ProtectionTimerCircleView.updateTime(ProtectionTimerData.sInputMillis, j);
        }
    }

    public static ProtectionTimerManager getInstance() {
        if (sInstance == null) {
            synchronized (sLock) {
                ProtectionTimerManager protectionTimerManager = new ProtectionTimerManager();
                protectionTimerManager.mTimer = null;
                sInstance = protectionTimerManager;
            }
        } else {
            Log.d("ProtectionTimerManager", "ProtectionTimerManager instance already exist");
        }
        return sInstance;
    }
}
