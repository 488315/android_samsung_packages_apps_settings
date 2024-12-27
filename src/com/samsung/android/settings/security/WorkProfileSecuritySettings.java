package com.samsung.android.settings.security;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.security.ChangeProfileScreenLockPreferenceController;
import com.android.settings.security.LockUnificationPreferenceController;
import com.android.settings.security.VisiblePatternProfilePreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class WorkProfileSecuritySettings extends DashboardFragment {
    public AnonymousClass1 mBroadcastReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.security.WorkProfileSecuritySettings.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (intent == null || context == null) {
                        Log.d(
                                "WorkProfileSecuritySettings",
                                "onReceive | intent or context is null");
                    } else if (intent.getAction()
                            .equals("com.samsung.android.intent.action.FINGERPRINT_RESET")) {
                        WorkProfileSecuritySettings.this.updateFingerprintPreference();
                    }
                }
            };
    public IntentFilter mIntentFilter;

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ChangeProfileScreenLockPreferenceController(context, this));
        arrayList.add(new LockUnificationPreferenceController(context, this));
        arrayList.add(new LockKnoxProfileAfterTimeoutPreferenceController(context, this));
        arrayList.add(new VisiblePatternProfilePreferenceController(context, settingsLifecycle));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WorkProfileSecuritySettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_work_profile_security_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        if (((LockUnificationPreferenceController) use(LockUnificationPreferenceController.class))
                .handleActivityResult(i, i2, intent)) {
            return;
        }
        super.onActivityResult(i, i2, intent);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        replaceEnterpriseStringTitle(
                "security_category_profile",
                "Settings.WORK_PROFILE_SECURITY_TITLE",
                R.string.lock_settings_profile_title);
        getIntent()
                .putExtra(
                        "android.intent.extra.USER_ID",
                        Utils.getManagedProfileId(
                                (UserManager) getContext().getSystemService("user"),
                                UserHandle.myUserId()));
        this.mIntentFilter =
                new IntentFilter("com.samsung.android.intent.action.FINGERPRINT_RESET");
        try {
            getContext()
                    .registerReceiverAsUser(
                            this.mBroadcastReceiver,
                            UserHandle.ALL,
                            this.mIntentFilter,
                            null,
                            null);
        } catch (IllegalArgumentException unused) {
            Log.e("WorkProfileSecuritySettings", "Receiver is already registered ");
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        if (this.mBroadcastReceiver != null) {
            try {
                getContext().unregisterReceiver(this.mBroadcastReceiver);
            } catch (IllegalArgumentException unused) {
                Log.e("WorkProfileSecuritySettings", "Receiver is already unregistered ");
            }
            this.mBroadcastReceiver = null;
        }
        super.onDestroy();
    }

    public final void updateFingerprintPreference() {
        super.updatePreferenceStates();
    }

    public final void updateUnificationPreference() {
        super.updatePreferenceStates();
    }
}
