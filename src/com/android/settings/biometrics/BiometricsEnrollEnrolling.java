package com.android.settings.biometrics;

import android.content.Intent;

import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.window.embedding.ActivityEmbeddingController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BiometricsEnrollEnrolling extends BiometricEnrollBase
        implements BiometricEnrollSidecar.Listener {
    public BiometricEnrollSidecar mSidecar;

    public final void cancelEnrollment() {
        BiometricEnrollSidecar biometricEnrollSidecar = this.mSidecar;
        if (biometricEnrollSidecar != null) {
            biometricEnrollSidecar.mListener = null;
            biometricEnrollSidecar.cancelEnrollment();
            FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
            supportFragmentManager.getClass();
            BackStackRecord backStackRecord = new BackStackRecord(supportFragmentManager);
            backStackRecord.remove(this.mSidecar);
            backStackRecord.commitInternal(true);
            this.mSidecar = null;
        }
    }

    public abstract Intent getFinishIntent();

    public abstract BiometricEnrollSidecar getSidecar();

    public final void launchFinish(byte[] bArr) {
        Intent finishIntent = getFinishIntent();
        finishIntent.addFlags(637534208);
        finishIntent.putExtra("hw_auth_token", bArr);
        finishIntent.putExtra("sensor_id", this.mSensorId);
        finishIntent.putExtra("challenge", this.mChallenge);
        finishIntent.putExtra("from_settings_summary", this.mFromSettingsSummary);
        int i = this.mUserId;
        if (i != -10000) {
            finishIntent.putExtra("android.intent.extra.USER_ID", i);
        }
        startActivity(finishIntent);
        finish();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onBackPressed() {
        cancelEnrollment();
        super.onBackPressed();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        if (shouldStartAutomatically()) {
            if (!isInMultiWindowMode()
                    || ActivityEmbeddingController.getInstance(this).isActivityEmbedded(this)) {
                startEnrollmentInternal();
            }
        }
    }

    @Override // com.android.settings.biometrics.BiometricEnrollBase,
              // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        BiometricEnrollSidecar biometricEnrollSidecar;
        BiometricEnrollSidecar biometricEnrollSidecar2 = this.mSidecar;
        if (biometricEnrollSidecar2 != null) {
            biometricEnrollSidecar2.mListener = null;
        }
        if (!isChangingConfigurations() && (biometricEnrollSidecar = this.mSidecar) != null) {
            biometricEnrollSidecar.cancelEnrollment();
            FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
            supportFragmentManager.getClass();
            BackStackRecord backStackRecord = new BackStackRecord(supportFragmentManager);
            backStackRecord.remove(this.mSidecar);
            backStackRecord.commitInternal(true);
        }
        super.onStop();
    }

    public abstract boolean shouldStartAutomatically();

    public void startEnrollmentInternal() {
        BiometricEnrollSidecar biometricEnrollSidecar =
                (BiometricEnrollSidecar) getSupportFragmentManager().findFragmentByTag("sidecar");
        this.mSidecar = biometricEnrollSidecar;
        if (biometricEnrollSidecar == null) {
            this.mSidecar = getSidecar();
            FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
            BackStackRecord m =
                    BiometricsEnrollEnrolling$$ExternalSyntheticOutline0.m(
                            supportFragmentManager, supportFragmentManager);
            m.doAddOp(0, this.mSidecar, "sidecar", 1);
            m.commitInternal(true);
        }
        this.mSidecar.setListener(this);
    }
}
