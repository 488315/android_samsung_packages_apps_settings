package com.samsung.android.settings.knox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImeAwareEditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.android.internal.widget.LockPatternView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class KnoxConfirmLockHelper {
    public boolean confirmBiometricIfNeeded(
            boolean z, FragmentActivity fragmentActivity, boolean z2) {
        return false;
    }

    public abstract int getDefaultDetails();

    public abstract String getDefaultHeader(String str, boolean z);

    public abstract int getErrorMessage();

    public abstract void hideSoftInputIfNeeded(ImeAwareEditText imeAwareEditText);

    public abstract boolean interceptAuthenticationSucceededIfNeeded(boolean z, boolean z2);

    public boolean interceptOnResumeIfNeeded(FragmentActivity fragmentActivity) {
        return false;
    }

    public boolean interceptUpdateEntryOrStateIfNeeded(boolean z) {
        return false;
    }

    public abstract void interceptUpdateErrorMessageIfNeeded(int i);

    public abstract void needToUpdateErrorMessage(int i, TextView textView);

    public abstract void onCreateView(View view);

    public abstract void onDestroy();

    public abstract void onWindowFocusChanged(boolean z);

    public abstract void removeGlobalLayoutListenerIfRequired();

    public abstract boolean reportFailedAttempt(
            int i, FragmentManager fragmentManager, FragmentActivity fragmentActivity);

    public abstract void reportSuccessfulAttempt();

    public abstract void setBiometricLockoutDeadline(int i);

    public boolean setCredentialCheckResultTrackerIfNeeded(boolean z) {
        return false;
    }

    public abstract void setPasswordEntry(ImeAwareEditText imeAwareEditText);

    public abstract void setPatternColor(LockPatternView lockPatternView);

    public abstract void setShowPwdImgColor(ImageButton imageButton, boolean z);

    public abstract void setWorkChallengeBackgroundIfNeeded(
            View view, FragmentActivity fragmentActivity);

    public abstract View switchViewIfNeeded(
            LayoutInflater layoutInflater, View view, FragmentActivity fragmentActivity);

    public abstract void updateStageNeedToUnlock(TextView textView);

    public void cancelBiometricIfNeeded() {}

    public void updateState() {}

    public void checkPasswordPolicy(FragmentActivity fragmentActivity) {}

    public void onCreate(Bundle bundle) {}

    public void onSavedInstanceState(Bundle bundle) {}

    public void setupForgotButtonIfManagedProfile(Button button) {}
}
