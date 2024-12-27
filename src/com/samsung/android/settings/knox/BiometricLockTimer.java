package com.samsung.android.settings.knox;

import android.app.AlertDialog;
import android.os.Handler;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BiometricLockTimer {
    public long mCount;
    public Handler mHandler;
    public OnTickListener mOnTickListener;
    public AnonymousClass1 mTask;
    public Timer mTimer;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.knox.BiometricLockTimer$1, reason: invalid class name */
    public final class AnonymousClass1 extends TimerTask {
        public AnonymousClass1() {}

        @Override // java.util.TimerTask, java.lang.Runnable
        public final void run() {
            BiometricLockTimer biometricLockTimer = BiometricLockTimer.this;
            biometricLockTimer.mHandler.post(
                    biometricLockTimer.new TickRunnable(biometricLockTimer.mCount));
            BiometricLockTimer biometricLockTimer2 = BiometricLockTimer.this;
            long j = biometricLockTimer2.mCount - 1;
            biometricLockTimer2.mCount = j;
            if (j < 0) {
                Timer timer = biometricLockTimer2.mTimer;
                if (timer != null) {
                    timer.cancel();
                    BiometricLockTimer.this.mTimer = null;
                }
                BiometricLockTimer.this.mHandler.post(new AnonymousClass2(1, this));
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.knox.BiometricLockTimer$2, reason: invalid class name */
    public final class AnonymousClass2 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass2(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    OnTickListener onTickListener =
                            ((BiometricLockTimer) this.this$0).mOnTickListener;
                    if (onTickListener != null) {
                        ((BiometricLockTimerDialogActivity.LockTimerDialogFragment.AnonymousClass3)
                                        onTickListener)
                                .getClass();
                        Log.d("KKG::BiometricLockTimerDialogActivity", "runTimer : onStop");
                        break;
                    }
                    break;
                default:
                    OnTickListener onTickListener2 = BiometricLockTimer.this.mOnTickListener;
                    if (onTickListener2 != null) {
                        Log.d("KKG::BiometricLockTimerDialogActivity", "runTimer : onEnd");
                        BiometricLockTimerDialogActivity.LockTimerDialogFragment
                                lockTimerDialogFragment =
                                        ((BiometricLockTimerDialogActivity.LockTimerDialogFragment
                                                                .AnonymousClass3)
                                                        onTickListener2)
                                                .this$0;
                        if (lockTimerDialogFragment.getActivity() == null
                                || lockTimerDialogFragment.getActivity().getSupportFragmentManager()
                                        != null) {
                            AlertDialog alertDialog = lockTimerDialogFragment.mAlertDialog;
                            if (alertDialog != null) {
                                alertDialog.dismiss();
                            }
                            lockTimerDialogFragment.mLockTimer = null;
                            lockTimerDialogFragment.getActivity().finish();
                            break;
                        }
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnTickListener {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TickRunnable implements Runnable {
        public final long mSetCount;

        public TickRunnable(long j) {
            this.mSetCount = j;
        }

        @Override // java.lang.Runnable
        public final void run() {
            OnTickListener onTickListener = BiometricLockTimer.this.mOnTickListener;
            if (onTickListener != null) {
                long j = this.mSetCount;
                BiometricLockTimerDialogActivity.LockTimerDialogFragment lockTimerDialogFragment =
                        ((BiometricLockTimerDialogActivity.LockTimerDialogFragment.AnonymousClass3)
                                        onTickListener)
                                .this$0;
                if (lockTimerDialogFragment.mAlertDialog == null
                        || lockTimerDialogFragment.getActivity() == null) {
                    return;
                }
                lockTimerDialogFragment.setDialogMessage(j);
            }
        }
    }
}
