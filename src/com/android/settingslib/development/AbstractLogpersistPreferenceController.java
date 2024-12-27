package com.android.settingslib.development;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemProperties;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.development.DevelopmentSettingsDashboardFragment;
import com.android.settings.development.DisableLogPersistWarningDialog;
import com.android.settings.development.LogPersistPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnCreate;
import com.android.settingslib.core.lifecycle.events.OnDestroy;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AbstractLogpersistPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, LifecycleObserver, OnCreate, OnDestroy {
    static final String ACTUAL_LOGPERSIST_PROPERTY = "logd.logpersistd";
    static final String ACTUAL_LOGPERSIST_PROPERTY_BUFFER = "logd.logpersistd.buffer";
    static final String SELECT_LOGPERSIST_PROPERTY_SERVICE = "logcatd";
    public ListPreference mLogpersist;
    public boolean mLogpersistCleared;
    public final AnonymousClass1 mReceiver;

    /* renamed from: -$$Nest$monLogdSizeSettingUpdate, reason: not valid java name */
    public static void m1031$$Nest$monLogdSizeSettingUpdate(
            LogPersistPreferenceController logPersistPreferenceController, String str) {
        if (logPersistPreferenceController.mLogpersist != null) {
            String str2 = SystemProperties.get("logd.logpersistd.enable");
            if (str2 == null || !str2.equals("true") || str.equals("32768")) {
                logPersistPreferenceController.writeLogpersistOption(null, true);
                logPersistPreferenceController.mLogpersist.setEnabled(false);
            } else if (DevelopmentSettingsEnabler.isDevelopmentSettingsEnabled(
                    logPersistPreferenceController.mContext)) {
                logPersistPreferenceController.mLogpersist.setEnabled(true);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.settingslib.development.AbstractLogpersistPreferenceController$1] */
    public AbstractLogpersistPreferenceController(Context context, Lifecycle lifecycle) {
        super(context);
        final LogPersistPreferenceController logPersistPreferenceController =
                (LogPersistPreferenceController) this;
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.settingslib.development.AbstractLogpersistPreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        AbstractLogpersistPreferenceController.m1031$$Nest$monLogdSizeSettingUpdate(
                                (LogPersistPreferenceController) logPersistPreferenceController,
                                intent.getStringExtra("CURRENT_LOGD_VALUE"));
                    }
                };
        if (!isAvailable() || lifecycle == null) {
            return;
        }
        lifecycle.addObserver(this);
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (isAvailable()) {
            this.mLogpersist =
                    (ListPreference) preferenceScreen.findPreference("select_logpersist");
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "select_logpersist";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return TextUtils.equals(
                SystemProperties.get("ro.debuggable", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN), "1");
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnCreate
    public final void onCreate(Bundle bundle) {
        LocalBroadcastManager.getInstance(this.mContext)
                .registerReceiver(
                        this.mReceiver,
                        new IntentFilter(
                                "com.android.settingslib.development.AbstractLogdSizePreferenceController.LOGD_SIZE_UPDATED"));
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public final void onDestroy() {
        LocalBroadcastManager.getInstance(this.mContext).unregisterReceiver(this.mReceiver);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (preference != this.mLogpersist) {
            return false;
        }
        writeLogpersistOption(obj, false);
        return true;
    }

    public final void setLogpersistOff(boolean z) {
        String str;
        SystemProperties.set("persist.logd.logpersistd.buffer", ApnSettings.MVNO_NONE);
        SystemProperties.set(ACTUAL_LOGPERSIST_PROPERTY_BUFFER, ApnSettings.MVNO_NONE);
        SystemProperties.set("persist.logd.logpersistd", ApnSettings.MVNO_NONE);
        SystemProperties.set(ACTUAL_LOGPERSIST_PROPERTY, z ? ApnSettings.MVNO_NONE : "stop");
        SystemPropPoker.sInstance.poke();
        if (z) {
            updateLogpersistValues();
            return;
        }
        for (int i = 0;
                i < 3
                        && (str = SystemProperties.get(ACTUAL_LOGPERSIST_PROPERTY)) != null
                        && !str.equals(ApnSettings.MVNO_NONE);
                i++) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException unused) {
            }
        }
    }

    public final void updateLogpersistValues() {
        char c;
        if (this.mLogpersist == null) {
            return;
        }
        String str = SystemProperties.get(ACTUAL_LOGPERSIST_PROPERTY);
        if (str == null) {
            str = ApnSettings.MVNO_NONE;
        }
        String str2 = SystemProperties.get(ACTUAL_LOGPERSIST_PROPERTY_BUFFER);
        if (str2 == null || str2.length() == 0) {
            str2 = "all";
        }
        if (!str.equals(SELECT_LOGPERSIST_PROPERTY_SERVICE)) {
            c = 0;
        } else {
            if (!str2.equals("kernel")) {
                if (!str2.equals("all")
                        && !str2.contains("radio")
                        && str2.contains("security")
                        && str2.contains("kernel")) {
                    if (!str2.contains("default")) {
                        String[] strArr = {"main", "events", "system", "crash"};
                        for (int i = 0; i < 4; i++) {
                            if (str2.contains(strArr[i])) {}
                        }
                    }
                    c = 2;
                }
                c = 1;
                break;
            }
            c = 3;
        }
        this.mLogpersist.setValue(
                this.mContext.getResources().getStringArray(R.array.select_logpersist_values)[c]);
        this.mLogpersist.setSummary(
                this.mContext.getResources()
                        .getStringArray(R.array.select_logpersist_summaries)[c]);
        if (c != 0) {
            this.mLogpersistCleared = false;
        } else {
            if (this.mLogpersistCleared) {
                return;
            }
            SystemProperties.set(ACTUAL_LOGPERSIST_PROPERTY, "clear");
            SystemPropPoker.sInstance.poke();
            this.mLogpersistCleared = true;
        }
    }

    public final void writeLogpersistOption(Object obj, boolean z) {
        String str;
        String str2;
        if (this.mLogpersist == null) {
            return;
        }
        String str3 = SystemProperties.get("persist.log.tag");
        if (str3 != null && str3.startsWith("Settings")) {
            obj = null;
            z = true;
        }
        if (obj != null && !obj.toString().equals(ApnSettings.MVNO_NONE)) {
            String str4 = SystemProperties.get(ACTUAL_LOGPERSIST_PROPERTY_BUFFER);
            if (str4 != null && !str4.equals(obj.toString())) {
                setLogpersistOff(false);
            }
            SystemProperties.set("persist.logd.logpersistd.buffer", obj.toString());
            SystemProperties.set("persist.logd.logpersistd", SELECT_LOGPERSIST_PROPERTY_SERVICE);
            SystemPropPoker.sInstance.poke();
            for (int i = 0;
                    i < 3
                            && ((str2 = SystemProperties.get(ACTUAL_LOGPERSIST_PROPERTY)) == null
                                    || !str2.equals(SELECT_LOGPERSIST_PROPERTY_SERVICE));
                    i++) {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException unused) {
                }
            }
            updateLogpersistValues();
            return;
        }
        if (z) {
            this.mLogpersistCleared = false;
        } else if (!this.mLogpersistCleared
                && (str = SystemProperties.get(ACTUAL_LOGPERSIST_PROPERTY)) != null
                && str.equals(SELECT_LOGPERSIST_PROPERTY_SERVICE)) {
            DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment =
                    ((LogPersistPreferenceController) this).mFragment;
            if (developmentSettingsDashboardFragment instanceof Fragment) {
                FragmentManagerImpl supportFragmentManager =
                        developmentSettingsDashboardFragment
                                .getActivity()
                                .getSupportFragmentManager();
                if (supportFragmentManager.findFragmentByTag("DisableLogPersistDlg") == null) {
                    DisableLogPersistWarningDialog disableLogPersistWarningDialog =
                            new DisableLogPersistWarningDialog();
                    disableLogPersistWarningDialog.setTargetFragment(
                            developmentSettingsDashboardFragment, 0);
                    disableLogPersistWarningDialog.show(
                            supportFragmentManager, "DisableLogPersistDlg");
                    return;
                }
                return;
            }
            return;
        }
        setLogpersistOff(true);
    }
}
