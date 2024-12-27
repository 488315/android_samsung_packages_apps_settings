package com.samsung.android.settings.deviceinfo.softwareinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.Utils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.Rune;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class CarrierConfigPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, OnResume, LifecycleObserver, OnStart, OnStop {
    public final String mConfig;
    public final Context mContext;
    public int mDevHitCountdown;
    public Preference mPreference;
    public final AnonymousClass1 mReceiver;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.settings.deviceinfo.softwareinfo.CarrierConfigPreferenceController$1] */
    public CarrierConfigPreferenceController(Context context) {
        super(context);
        String string;
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.settings.deviceinfo.softwareinfo.CarrierConfigPreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if ("com.samsung.nsds.action.DEVICE_CONFIG_UPDATED"
                                        .equals(intent.getAction())
                                && intent.getExtras().getBoolean("forced_config_udpate", false)) {
                            if (intent.getExtras().getBoolean("request_status", false)) {
                                ((CarrierConfigPreference)
                                                CarrierConfigPreferenceController.this.mPreference)
                                        .setUpdateButtonVisibility(0);
                            } else {
                                ((CarrierConfigPreference)
                                                CarrierConfigPreferenceController.this.mPreference)
                                        .setUpdateButtonVisibility(3);
                            }
                        }
                    }
                };
        this.mDevHitCountdown = 5;
        this.mContext = context;
        Cursor query =
                context.getContentResolver()
                        .query(
                                Uri.parse("com.samsung.nsds.provider/device_config"),
                                new String[] {"VERSION"},
                                null,
                                null,
                                null);
        if (query != null) {
            try {
                if (query.moveToFirst() && query.getString(0) != null) {
                    string = query.getString(0);
                    this.mConfig = string;
                }
            } finally {
                query.close();
            }
        }
        if (query != null) {
            query.close();
        }
        string = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
        this.mConfig = string;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference("carrier_config_ver");
        if (findPreference != null) {
            findPreference.setSummary(this.mConfig);
        }
        this.mPreference = preferenceScreen.findPreference("carrier_config_ver");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "carrier_config_ver";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), "carrier_config_ver")) {
            return false;
        }
        int i = this.mDevHitCountdown;
        if (i > 0) {
            int i2 = i - 1;
            this.mDevHitCountdown = i2;
            if (i2 == 0) {
                ((CarrierConfigPreference) preference).setUpdateButtonVisibility(1);
                this.mDevHitCountdown = 5;
            }
        }
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (!TextUtils.isEmpty(this.mConfig)) {
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            if ("TMB".equals(Utils.getSalesCode()) || "XAU".equals(Utils.getSalesCode())) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        this.mDevHitCountdown = 5;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public final void onStart() {
        this.mContext.registerReceiver(
                this.mReceiver,
                new IntentFilter("com.samsung.nsds.action.DEVICE_CONFIG_UPDATED"),
                2);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public final void onStop() {
        try {
            this.mContext.unregisterReceiver(this.mReceiver);
        } catch (IllegalArgumentException unused) {
            Log.d("ConfigPref", "exception in unregisterReceiver");
        }
    }
}
