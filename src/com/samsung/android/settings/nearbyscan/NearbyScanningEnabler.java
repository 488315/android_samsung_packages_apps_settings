package com.samsung.android.settings.nearbyscan;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.SwitchPreferenceCompat;
import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class NearbyScanningEnabler {
    public final Context mContext;
    public final SwitchPreferenceCompat mNearbyScanningSettingsSwitch;
    public final SettingsMainSwitchBar mNearbyScanningSwitchBar;
    public boolean mIsInit = false;
    public final AnonymousClass1 mNearbyScanningObserver = new ContentObserver(new Handler()) { // from class: com.samsung.android.settings.nearbyscan.NearbyScanningEnabler.1
        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            int dBInt = NearbyScanningUtil.getDBInt(NearbyScanningEnabler.this.mContext.getContentResolver());
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(dBInt, "mNearbyScanningObserver DB value : ", "NearbyScanningEnabler");
            NearbyScanningEnabler nearbyScanningEnabler = NearbyScanningEnabler.this;
            SwitchPreferenceCompat switchPreferenceCompat = nearbyScanningEnabler.mNearbyScanningSettingsSwitch;
            if (switchPreferenceCompat != null) {
                switchPreferenceCompat.setChecked(dBInt == 1);
                return;
            }
            SettingsMainSwitchBar settingsMainSwitchBar = nearbyScanningEnabler.mNearbyScanningSwitchBar;
            if (settingsMainSwitchBar != null) {
                settingsMainSwitchBar.setChecked(dBInt == 1);
            }
        }
    };
    public final AnonymousClass2 mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() { // from class: com.samsung.android.settings.nearbyscan.NearbyScanningEnabler.2
        /* JADX WARN: Type inference failed for: r7v1, types: [boolean, int] */
        /* JADX WARN: Type inference failed for: r7v5 */
        /* JADX WARN: Type inference failed for: r7v6 */
        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            Context context = NearbyScanningEnabler.this.mContext;
            if (context == null) {
                Log.d("NearbyScanningEnabler", "OnSwitchChangeListener, context is null");
                return;
            }
            int dBInt = NearbyScanningUtil.getDBInt(context.getContentResolver());
            if (compoundButton.isChecked()) {
                String str = z ? "on" : "off";
                NearbyScanningEnabler.this.getClass();
                SALogging.insertSALog("BLEM_100", "BLEM_0101", str);
            }
            if (dBInt != 2 || z) {
                ?? r7 = z;
                if (!NearbyScanningUtil.isBeaconManagerInstall(NearbyScanningEnabler.this.mContext)) {
                    Toast.makeText(NearbyScanningEnabler.this.mContext, "Not Installed BeaconManager", 1).show();
                    r7 = 0;
                }
                AirplaneModeEnabler$$ExternalSyntheticOutline0.m("OnSwitchChangeListener : ", "NearbyScanningEnabler", r7);
                NearbyScanningUtil.setDBInt(NearbyScanningEnabler.this.mContext, r7);
                if (compoundButton.isChecked()) {
                    NearbyScanningUtil.settingslogging(NearbyScanningEnabler.this.mContext);
                }
            }
        }
    };
    public final AnonymousClass3 mOnPreferenceChangeListener = new Preference.OnPreferenceChangeListener() { // from class: com.samsung.android.settings.nearbyscan.NearbyScanningEnabler.3
        /* JADX WARN: Type inference failed for: r5v4, types: [boolean, int] */
        /* JADX WARN: Type inference failed for: r5v7 */
        /* JADX WARN: Type inference failed for: r5v8 */
        @Override // androidx.preference.Preference.OnPreferenceChangeListener
        public final boolean onPreferenceChange(Preference preference, Object obj) {
            NearbyScanningEnabler nearbyScanningEnabler = NearbyScanningEnabler.this;
            if (nearbyScanningEnabler.mContext == null) {
                Log.d("NearbyScanningEnabler", "OnPreferenceChangeListener, context is null");
                return false;
            }
            if (!(obj instanceof Boolean)) {
                Log.d("NearbyScanningEnabler", "OnPreferenceChangeListener, instance not Boolean");
                return false;
            }
            boolean booleanValue = ((Boolean) obj).booleanValue();
            SALogging.insertSALog("BLEM_100", "BLEM_0101", booleanValue ? "on" : "off");
            if (NearbyScanningUtil.getDBInt(nearbyScanningEnabler.mContext.getContentResolver()) != 2 || booleanValue) {
                ?? r5 = booleanValue;
                if (!NearbyScanningUtil.isBeaconManagerInstall(nearbyScanningEnabler.mContext)) {
                    Toast.makeText(nearbyScanningEnabler.mContext, "Not Installed BeaconManager", 1).show();
                    r5 = 0;
                }
                AirplaneModeEnabler$$ExternalSyntheticOutline0.m("OnPreferenceChangeListener : ", "NearbyScanningEnabler", r5);
                NearbyScanningUtil.setDBInt(nearbyScanningEnabler.mContext, r5);
                NearbyScanningUtil.settingslogging(nearbyScanningEnabler.mContext);
            }
            return false;
        }
    };

    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.nearbyscan.NearbyScanningEnabler$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.settings.nearbyscan.NearbyScanningEnabler$2] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.samsung.android.settings.nearbyscan.NearbyScanningEnabler$3] */
    public NearbyScanningEnabler(Context context, Object obj) {
        this.mNearbyScanningSettingsSwitch = null;
        this.mNearbyScanningSwitchBar = null;
        this.mContext = context;
        if (obj instanceof SwitchPreferenceCompat) {
            this.mNearbyScanningSettingsSwitch = (SwitchPreferenceCompat) obj;
        } else if (obj instanceof SettingsMainSwitchBar) {
            this.mNearbyScanningSwitchBar = (SettingsMainSwitchBar) obj;
        }
    }

    public final void init() {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(NearbyScanningUtil.getDBInt(this.mContext.getContentResolver()), "nsValue value : ", "NearbyScanningEnabler");
        SwitchPreferenceCompat switchPreferenceCompat = this.mNearbyScanningSettingsSwitch;
        if (switchPreferenceCompat != null) {
            switchPreferenceCompat.setOnPreferenceChangeListener(this.mOnPreferenceChangeListener);
            this.mIsInit = true;
        } else {
            SettingsMainSwitchBar settingsMainSwitchBar = this.mNearbyScanningSwitchBar;
            if (settingsMainSwitchBar != null) {
                settingsMainSwitchBar.addOnSwitchChangeListener(this.mOnCheckedChangeListener);
                settingsMainSwitchBar.show();
                this.mIsInit = true;
            }
        }
        Log.d("NearbyScanningEnabler", "Switch is null");
    }

    public final void onPause() {
        if (!this.mIsInit) {
            Log.d("NearbyScanningEnabler", "Init is failed");
            return;
        }
        Context context = this.mContext;
        if (context == null) {
            Log.d("NearbyScanningEnabler", "Context is null");
            return;
        }
        ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver == null) {
            Log.d("NearbyScanningEnabler", "ContentResolver is null");
            return;
        }
        try {
            contentResolver.unregisterContentObserver(this.mNearbyScanningObserver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void onResume() {
        if (!this.mIsInit) {
            Log.d("NearbyScanningEnabler", "Init is failed");
            return;
        }
        Context context = this.mContext;
        if (context == null) {
            Log.d("NearbyScanningEnabler", "Context is null");
            return;
        }
        ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver == null) {
            Log.d("NearbyScanningEnabler", "ContentResolver is null");
            return;
        }
        try {
            contentResolver.registerContentObserver(Settings.System.getUriFor("nearby_scanning_enabled"), true, this.mNearbyScanningObserver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int dBInt = NearbyScanningUtil.getDBInt(this.mContext.getContentResolver());
        SwitchPreferenceCompat switchPreferenceCompat = this.mNearbyScanningSettingsSwitch;
        if (switchPreferenceCompat != null) {
            switchPreferenceCompat.setChecked(dBInt == 1);
            return;
        }
        SettingsMainSwitchBar settingsMainSwitchBar = this.mNearbyScanningSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setChecked(dBInt == 1);
        } else {
            Log.d("NearbyScanningEnabler", "Switch is null");
        }
    }
}
