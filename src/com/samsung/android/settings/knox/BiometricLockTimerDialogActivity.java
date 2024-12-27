package com.samsung.android.settings.knox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import android.view.KeyEvent;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;

import com.samsung.android.settings.knox.BiometricLockTimer.AnonymousClass1;

import java.util.Timer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BiometricLockTimerDialogActivity extends FragmentActivity {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class LockTimerDialogFragment extends DialogFragment {
        public int failAttempts;
        public AlertDialog mAlertDialog;
        public DevicePolicyManager mDevicePolicyManager;
        public LockPatternUtils mLockPatternUtils;
        public BiometricLockTimer mLockTimer;
        public int mUserId;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.samsung.android.settings.knox.BiometricLockTimerDialogActivity$LockTimerDialogFragment$1, reason: invalid class name */
        public final class AnonymousClass1 implements DialogInterface.OnClickListener {
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.samsung.android.settings.knox.BiometricLockTimerDialogActivity$LockTimerDialogFragment$2, reason: invalid class name */
        public final class AnonymousClass2 implements DialogInterface.OnKeyListener {
            @Override // android.content.DialogInterface.OnKeyListener
            public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() != 1 && keyEvent.getAction() != 4) {
                    return false;
                }
                dialogInterface.dismiss();
                return true;
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.samsung.android.settings.knox.BiometricLockTimerDialogActivity$LockTimerDialogFragment$3, reason: invalid class name */
        public final class AnonymousClass3 implements BiometricLockTimer.OnTickListener {
            public AnonymousClass3() {}
        }

        @Override // androidx.fragment.app.Fragment
        public final void onActivityCreated(Bundle bundle) {
            super.onActivityCreated(bundle);
            if (this.mLockTimer != null) {
                return;
            }
            long ceil =
                    (long)
                            Math.ceil(
                                    (this.mLockPatternUtils.getBiometricAttemptDeadline(
                                                            this.mUserId)
                                                    - SystemClock.elapsedRealtime())
                                            / 1000.0d);
            Log.d("KKG::BiometricLockTimerDialogActivity", "BiometricDeadline left: " + ceil);
            Handler handler = new Handler(Looper.getMainLooper());
            BiometricLockTimer biometricLockTimer = new BiometricLockTimer();
            biometricLockTimer.mCount = ceil;
            biometricLockTimer.mHandler = handler;
            this.mLockTimer = biometricLockTimer;
            biometricLockTimer.mOnTickListener = new AnonymousClass3();
            biometricLockTimer.mTask = biometricLockTimer.new AnonymousClass1();
            Timer timer = new Timer();
            biometricLockTimer.mTimer = timer;
            timer.scheduleAtFixedRate(biometricLockTimer.mTask, 0L, 1000L);
        }

        @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            this.mLockPatternUtils = new LockPatternUtils(getActivity());
            UserManager.get(getActivity());
            this.mDevicePolicyManager =
                    (DevicePolicyManager) getActivity().getSystemService("device_policy");
            int intExtra = getActivity().getIntent().getIntExtra("mUserId", UserHandle.myUserId());
            this.mUserId = intExtra;
            this.failAttempts =
                    this.mDevicePolicyManager.getCurrentFailedBiometricAttempts(intExtra);
            StringBuilder sb = new StringBuilder("mUserId : ");
            sb.append(this.mUserId);
            sb.append(", failAttempts : ");
            Preference$$ExternalSyntheticOutline0.m(
                    sb, this.failAttempts, "KKG::BiometricLockTimerDialogActivity");
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            this.mAlertDialog =
                    new AlertDialog.Builder(
                                    getActivity(),
                                    (getActivity().getResources().getConfiguration().uiMode & 48)
                                                    == 32
                                            ? 4
                                            : 5)
                            .create();
            this.mAlertDialog.setTitle(
                    getActivity()
                            .getResources()
                            .getString(R.string.iris_failed_to_verity_identify));
            setDialogMessage(0L);
            this.mAlertDialog.setCancelable(false);
            this.mAlertDialog.setCanceledOnTouchOutside(false);
            this.mAlertDialog.setButton(-1, "OK", new AnonymousClass1());
            this.mAlertDialog.setOnKeyListener(new AnonymousClass2());
            return this.mAlertDialog;
        }

        @Override // androidx.fragment.app.Fragment
        public final void onDestroy() {
            super.onDestroy();
            BiometricLockTimer biometricLockTimer = this.mLockTimer;
            if (biometricLockTimer != null) {
                Timer timer = biometricLockTimer.mTimer;
                if (timer != null) {
                    timer.cancel();
                    biometricLockTimer.mTimer = null;
                }
                biometricLockTimer.mHandler.post(
                        new BiometricLockTimer.AnonymousClass2(0, biometricLockTimer));
            }
            if (getActivity() != null) {
                getActivity().finish();
            }
        }

        public final void setDialogMessage(long j) {
            String quantityString;
            if (this.failAttempts == 0) {
                int i = (int) j;
                quantityString =
                        getResources()
                                .getQuantityString(
                                        R.plurals.keyguard_twostep_throttle_error_text,
                                        i,
                                        Integer.valueOf(i));
            } else if (j <= 1) {
                Resources resources = getResources();
                int i2 = this.failAttempts;
                quantityString =
                        resources.getQuantityString(
                                R.plurals.biometric_throttle_error_text_for_fingerprint_second,
                                i2,
                                Integer.valueOf(i2));
            } else {
                quantityString =
                        String.format(
                                getActivity()
                                        .getResources()
                                        .getString(
                                                R.string
                                                        .biometric_throttle_error_text_for_fingerprint),
                                Integer.valueOf(this.failAttempts),
                                Long.valueOf(j));
            }
            this.mAlertDialog.setMessage(quantityString);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            new LockTimerDialogFragment()
                    .show(getSupportFragmentManager(), "LockTimerDialogFragment");
        }
    }
}
