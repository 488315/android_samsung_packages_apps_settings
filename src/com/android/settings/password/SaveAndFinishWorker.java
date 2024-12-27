package com.android.settings.password;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.safetycenter.SafetyEvent;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.settings.R;
import com.android.settings.safetycenter.BiometricsSafetySource;
import com.android.settings.safetycenter.LockScreenSafetySource;
import com.android.settings.security.ScreenLockPreferenceDetailsUtils;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.knox.UCMUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SaveAndFinishWorker extends Fragment {
    public LockscreenCredential mChosenCredential;
    public LockscreenCredential mCurrentCredential;
    public boolean mFinished;
    public Listener mListener;
    public boolean mRequestGatekeeperPassword;
    public boolean mRequestWriteRepairModePassword;
    public Intent mResultData;
    public LockscreenCredential mUnificationProfileCredential;
    public int mUserId;
    public LockPatternUtils mUtils;
    public boolean mWasSecureBefore;
    public int mUnificationProfileId = -10000;
    public boolean mSetPrevious = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Listener {
        void onChosenLockSaveFinished(Intent intent, boolean z);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Task extends AsyncTask {
        public Task() {
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            return SaveAndFinishWorker.this.saveAndVerifyInBackground();
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            Pair pair = (Pair) obj;
            if (!((Boolean) pair.first).booleanValue()) {
                Toast.makeText(SaveAndFinishWorker.this.getContext(), R.string.lockpassword_credential_changed, 1).show();
            }
            SaveAndFinishWorker saveAndFinishWorker = SaveAndFinishWorker.this;
            Intent intent = (Intent) pair.second;
            saveAndFinishWorker.mFinished = true;
            saveAndFinishWorker.mResultData = intent;
            Listener listener = saveAndFinishWorker.mListener;
            if (listener != null) {
                listener.onChosenLockSaveFinished(intent, saveAndFinishWorker.mWasSecureBefore);
            }
            LockscreenCredential lockscreenCredential = saveAndFinishWorker.mUnificationProfileCredential;
            if (lockscreenCredential != null) {
                lockscreenCredential.zeroize();
            }
            Context context = saveAndFinishWorker.getContext();
            LockScreenSafetySource.setSafetySourceData(context, new ScreenLockPreferenceDetailsUtils(context), new SafetyEvent.Builder(100).build());
            BiometricsSafetySource.onBiometricsChanged(context);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    public void prepare(LockPatternUtils lockPatternUtils, LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i) {
        this.mUtils = lockPatternUtils;
        this.mUserId = i;
        this.mWasSecureBefore = lockPatternUtils.isSecure(i);
        this.mFinished = false;
        this.mResultData = null;
        this.mChosenCredential = lockscreenCredential;
        if (lockscreenCredential2 == null) {
            lockscreenCredential2 = LockscreenCredential.createNone();
        }
        this.mCurrentCredential = lockscreenCredential2;
        this.mSetPrevious = getActivity().getIntent().getBooleanExtra("previous_credential", false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [int] */
    /* JADX WARN: Type inference failed for: r3v30 */
    /* JADX WARN: Type inference failed for: r3v31 */
    public Pair<Boolean, Intent> saveAndVerifyInBackground() {
        LockscreenCredential lockscreenCredential;
        int i = this.mUserId;
        try {
            if (UCMUtils.isEnforcedCredentialStorageExistAsUser(i) && UCMUtils.isSupportChangePin(i)) {
                if (!UCMUtils.changeUCMLockPin(UCMUtils.getUCMUri(i), new String(this.mCurrentCredential.getCredential()), new String(this.mChosenCredential.getCredential()))) {
                    return Pair.create(Boolean.FALSE, null);
                }
            } else if (this.mSetPrevious) {
                if (!this.mUtils.setLockCredential(this.mChosenCredential, this.mCurrentCredential, -9899)) {
                    return Pair.create(Boolean.FALSE, null);
                }
            } else if (!this.mUtils.setLockCredential(this.mChosenCredential, this.mCurrentCredential, i)) {
                return Pair.create(Boolean.FALSE, null);
            }
            int i2 = this.mUnificationProfileId;
            if (i2 != -10000) {
                this.mUtils.setSeparateProfileChallengeEnabled(i2, false, this.mUnificationProfileCredential);
            }
            if (SemPersonaManager.isSecureFolderId(this.mUserId) && ((lockscreenCredential = this.mCurrentCredential) == null || lockscreenCredential.isNone())) {
                Log.d("SaveAndFinishWorker", "Reset password of secure folder has requested.");
                this.mUtils.clearLockoutAttemptDeadline(this.mUserId);
                this.mUtils.clearBiometricAttemptDeadline(this.mUserId);
            }
            boolean z = this.mRequestGatekeeperPassword;
            ?? r3 = z;
            if (this.mRequestWriteRepairModePassword) {
                r3 = (z ? 1 : 0) | 2;
            }
            if (r3 == 0) {
                return Pair.create(Boolean.TRUE, null);
            }
            Intent intent = new Intent();
            VerifyCredentialResponse verifyCredential = this.mUtils.verifyCredential(this.mChosenCredential, i, (int) r3);
            if (!verifyCredential.isMatched()) {
                Log.e("SaveAndFinishWorker", "critical: bad response for known good credential: " + verifyCredential);
            } else if (this.mRequestGatekeeperPassword && verifyCredential.containsGatekeeperPasswordHandle()) {
                intent.putExtra("gk_pw_handle", verifyCredential.getGatekeeperPasswordHandle());
            } else if (this.mRequestGatekeeperPassword) {
                Log.e("SaveAndFinishWorker", "critical: missing GK PW handle for known good credential: " + verifyCredential);
            }
            if (this.mRequestWriteRepairModePassword) {
                intent.putExtra("wrote_repair_mode_credential", verifyCredential.isMatched());
            }
            return Pair.create(Boolean.TRUE, intent);
        } catch (RuntimeException e) {
            Log.e("SaveAndFinishWorker", "Failed to set lockscreen credential", e);
            return Pair.create(Boolean.FALSE, null);
        }
    }

    public final void setListener(Listener listener) {
        if (this.mListener == listener) {
            return;
        }
        this.mListener = listener;
        if (!this.mFinished || listener == null) {
            return;
        }
        listener.onChosenLockSaveFinished(this.mResultData, this.mWasSecureBefore);
    }

    public final void start(LockPatternUtils lockPatternUtils, LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i) {
        prepare(lockPatternUtils, lockscreenCredential, lockscreenCredential2, i);
        new Task().execute(new Void[0]);
    }
}
