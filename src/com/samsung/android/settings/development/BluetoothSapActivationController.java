package com.samsung.android.settings.development;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Debug;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BluetoothSapActivationController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    public static final boolean DBG = Debug.semIsProductDev();
    public final String[] mListSummaries;
    public final String[] mListValues;

    public BluetoothSapActivationController(Context context) {
        super(context);
        this.mListValues =
                context.getResources().getStringArray(R.array.bluetooth_sap_activation_values);
        this.mListSummaries =
                context.getResources().getStringArray(R.array.bluetooth_sap_activation_entries);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_sap_activation";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsEnabled() {
        super.onDeveloperOptionsEnabled();
        StringBuilder sb = new StringBuilder("onDeveloperOptionsEnabled :: debug ");
        boolean z = DBG;
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                sb, z, "BluetoothSapActivationController");
        if (!z) {
            this.mPreference.setVisible(false);
        } else {
            this.mPreference.setVisible(true);
            updateState(this.mPreference);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        SystemProperties.set("persist.bluetooth.sapactivation", obj.toString());
        updateState(this.mPreference);
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            Log.e(
                    "BluetoothSapActivationController",
                    "onPreferenceChange :: Failed to get Bluetooth adapter");
            return true;
        }
        defaultAdapter.semShutdown();
        defaultAdapter.enable();
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        String[] strArr;
        ListPreference listPreference = (ListPreference) preference;
        String str = SystemProperties.get("persist.bluetooth.sapactivation");
        int i = 0;
        int i2 = 0;
        while (true) {
            strArr = this.mListValues;
            if (i2 >= strArr.length) {
                break;
            }
            if (TextUtils.equals(str, strArr[i2])) {
                i = i2;
                break;
            }
            i2++;
        }
        listPreference.setValue(strArr[i]);
        listPreference.setSummary(this.mListSummaries[i]);
    }
}
