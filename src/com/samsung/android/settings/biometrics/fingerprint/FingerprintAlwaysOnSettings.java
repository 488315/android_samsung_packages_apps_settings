package com.samsung.android.settings.biometrics.fingerprint;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.widget.Toast;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.widget.SecRadioButtonPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FingerprintAlwaysOnSettings extends SettingsPreferenceFragment
        implements SecRadioButtonPreference.OnClickListener {
    public SecRadioButtonPreference mButtonCoverMainScreens;
    public SecRadioButtonPreference mButtonCoverScreen;
    public SecRadioButtonPreference mButtonMainScreen;
    public SecRadioButtonPreference mButtonOff;
    public boolean mIsFromUsefulFeature;
    public int mUserId;

    public final void cancelAndSessionEnd() {
        Log.d("FpstFingerprintAlwaysOnSettings", "Cancel session");
        Intent intent = new Intent();
        intent.putExtra("reason", "cancelsession");
        setResult(0, intent);
        if (isFinishingOrDestroyed()) {
            return;
        }
        finish();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return this.mIsFromUsefulFeature
                ? ApnSettings.MVNO_NONE
                : FingerprintSettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 8285;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("FpstFingerprintAlwaysOnSettings", "onCreate()");
        addPreferencesFromResource(R.xml.sec_fingerprint_always_on_settings);
        this.mUserId = UserHandle.myUserId();
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mUserId = arguments.getInt("android.intent.extra.USER_ID", UserHandle.myUserId());
            this.mIsFromUsefulFeature = arguments.getBoolean("fingerprint_useful_feature", false);
        }
        try {
            Resources resourcesForApplication =
                    getPackageManager()
                            .getResourcesForApplication(
                                    "com.samsung.android.biometrics.app.setting");
            findPreference(UniversalCredentialUtil.AGENT_SUMMARY)
                    .setTitle(
                            resourcesForApplication.getString(
                                    resourcesForApplication.getIdentifier(
                                            "sec_fingerprint_always_on_summary",
                                            "string",
                                            "com.samsung.android.biometrics.app.setting")));
        } catch (PackageManager.NameNotFoundException
                | Resources.NotFoundException
                | NullPointerException e) {
            Log.w("FpstFingerprintAlwaysOnSettings", "initPreference : resources exception = " + e);
        }
        SecRadioButtonPreference secRadioButtonPreference =
                (SecRadioButtonPreference) findPreference("button_off");
        this.mButtonOff = secRadioButtonPreference;
        secRadioButtonPreference.mListener = this;
        SecRadioButtonPreference secRadioButtonPreference2 =
                (SecRadioButtonPreference) findPreference("button_cover_screen");
        this.mButtonCoverScreen = secRadioButtonPreference2;
        secRadioButtonPreference2.mListener = this;
        SecRadioButtonPreference secRadioButtonPreference3 =
                (SecRadioButtonPreference) findPreference("button_main_screen");
        this.mButtonMainScreen = secRadioButtonPreference3;
        secRadioButtonPreference3.mListener = this;
        SecRadioButtonPreference secRadioButtonPreference4 =
                (SecRadioButtonPreference) findPreference("button_cover_main_screens");
        this.mButtonCoverMainScreens = secRadioButtonPreference4;
        secRadioButtonPreference4.mListener = this;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onMultiWindowModeChanged(boolean z) {
        super.onMultiWindowModeChanged(z);
        if (!z || Rune.isSamsungDexMode(getContext())) {
            return;
        }
        Toast.makeText(
                        getActivity(),
                        getString(R.string.sec_fingerprint_doesnt_support_multi_window_text),
                        0)
                .show();
        cancelAndSessionEnd();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        Log.d("FpstFingerprintAlwaysOnSettings", "onPause");
        if (getActivity().isChangingConfigurations() || isFinishingOrDestroyed()) {
            return;
        }
        cancelAndSessionEnd();
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonPreference.OnClickListener
    public final void onRadioButtonClicked(SecRadioButtonPreference secRadioButtonPreference) {
        int i;
        i = 2;
        Log.d(
                "FpstFingerprintAlwaysOnSettings",
                "onRadioButtonClicked : " + ((Object) secRadioButtonPreference.getTitle()));
        String key = secRadioButtonPreference.getKey();
        key.getClass();
        switch (key) {
            case "button_cover_main_screens":
                i = 3;
                break;
            case "button_main_screen":
                break;
            case "button_cover_screen":
                i = 1;
                break;
            default:
                i = 0;
                break;
        }
        FingerprintSettingsUtils.setFingerprintAlwaysOnTypeValue(getContext(), i, this.mUserId);
        updatePreference$8();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.d("FpstFingerprintAlwaysOnSettings", "onResume");
        updatePreference$8();
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x004a, code lost:

       if (r9 != false) goto L7;
    */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x0042, code lost:

       if (r9 != false) goto L7;
    */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0044, code lost:

       r1 = 2;
    */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0046, code lost:

       r1 = 3;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updatePreference$8() {
        /*
            r10 = this;
            com.samsung.android.settings.widget.SecRadioButtonPreference r0 = r10.mButtonOff
            r1 = 0
            r0.setChecked(r1)
            com.samsung.android.settings.widget.SecRadioButtonPreference r0 = r10.mButtonCoverScreen
            r0.setChecked(r1)
            com.samsung.android.settings.widget.SecRadioButtonPreference r0 = r10.mButtonMainScreen
            r0.setChecked(r1)
            com.samsung.android.settings.widget.SecRadioButtonPreference r0 = r10.mButtonCoverMainScreens
            r0.setChecked(r1)
            androidx.fragment.app.FragmentActivity r0 = r10.getActivity()
            int r2 = r10.mUserId
            boolean r3 = com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsUtils.SUB_DISPLAY_IS_LARGE_SCREEN
            android.content.ContentResolver r3 = r0.getContentResolver()
            java.lang.String r4 = "fingerprint_always_on_type"
            r5 = -1
            int r3 = android.provider.Settings.Secure.getIntForUser(r3, r4, r5, r2)
            r4 = 1
            r6 = 2
            r7 = 3
            java.lang.String r8 = "FpstFingerprintSettingsUtils"
            if (r3 != r5) goto L51
            android.content.ContentResolver r3 = r0.getContentResolver()
            java.lang.String r9 = "fingerprint_always_on"
            int r3 = android.provider.Settings.Secure.getIntForUser(r3, r9, r5, r2)
            java.lang.String r9 = "getFingerprintAlwaysOnDbValue : "
            androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0.m1m(r3, r9, r8)
            boolean r9 = com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsUtils.SUB_DISPLAY_IS_LARGE_SCREEN
            if (r3 != r5) goto L48
            if (r9 == 0) goto L46
        L44:
            r1 = r6
            goto L4d
        L46:
            r1 = r7
            goto L4d
        L48:
            if (r3 != r4) goto L4d
            if (r9 == 0) goto L46
            goto L44
        L4d:
            com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsUtils.setFingerprintAlwaysOnTypeValue(r0, r1, r2)
            r3 = r1
        L51:
            java.lang.String r0 = "Get Fingerprint Always On Type : "
            androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0.m1m(r3, r0, r8)
            if (r3 == 0) goto L71
            if (r3 == r4) goto L6b
            if (r3 == r6) goto L65
            if (r3 == r7) goto L5f
            goto L76
        L5f:
            com.samsung.android.settings.widget.SecRadioButtonPreference r10 = r10.mButtonCoverMainScreens
            r10.setChecked(r4)
            goto L76
        L65:
            com.samsung.android.settings.widget.SecRadioButtonPreference r10 = r10.mButtonMainScreen
            r10.setChecked(r4)
            goto L76
        L6b:
            com.samsung.android.settings.widget.SecRadioButtonPreference r10 = r10.mButtonCoverScreen
            r10.setChecked(r4)
            goto L76
        L71:
            com.samsung.android.settings.widget.SecRadioButtonPreference r10 = r10.mButtonOff
            r10.setChecked(r4)
        L76:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.biometrics.fingerprint.FingerprintAlwaysOnSettings.updatePreference$8():void");
    }
}
