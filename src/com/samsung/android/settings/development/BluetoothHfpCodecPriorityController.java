package com.samsung.android.settings.development;

import android.bluetooth.BluetoothDump;
import android.os.Debug;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BluetoothHfpCodecPriorityController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener {
    public static final boolean DBG = Debug.semIsProductDev();

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        int i;
        super.displayPreference(preferenceScreen);
        Log.i("BluetoothHfpCodecPriorityController", "displayPreference ::");
        ListPreference listPreference = (ListPreference) this.mPreference;
        ArrayList arrayList = new ArrayList();
        for (CharSequence charSequence : listPreference.mEntries) {
            if ("LC3".equals(charSequence)) {
                boolean z = SystemProperties.getBoolean("bluetooth.hfp.swb.supported", false);
                AbsAdapter$$ExternalSyntheticOutline0.m(
                        "displayPreference :: lc3 supported = ",
                        "BluetoothHfpCodecPriorityController",
                        z);
                i = z ? 0 : i + 1;
                arrayList.add(charSequence.toString());
            } else {
                if ("RVP".equals(charSequence)) {
                    String str = SystemProperties.get("persist.bluetooth.rvpcapability");
                    Log.i(
                            "BluetoothHfpCodecPriorityController",
                            "displayPreference :: rvpCapability = " + str);
                    if (!"bt".equals(str) && !"adsp".equals(str)) {}
                }
                arrayList.add(charSequence.toString());
            }
        }
        CharSequence[] charSequenceArr =
                (CharSequence[]) arrayList.toArray(new String[arrayList.size()]);
        listPreference.setEntries(charSequenceArr);
        listPreference.mEntryValues = charSequenceArr;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_hfp_codec_priority";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        StringBuilder sb = new StringBuilder("isAvailable :: debug : ");
        boolean z = DBG;
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                sb, z, "BluetoothHfpCodecPriorityController");
        return z;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        String obj2 = obj.toString();
        Log.i("BluetoothHfpCodecPriorityController", "onPreferenceChange :: newValue : " + obj2);
        BluetoothDump.BtLog("HFCodec-Change HFP Codec Priority to " + obj2);
        SystemProperties.set("persist.bluetooth.hfpcodecpriority", obj2);
        updateState(preference);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        Log.i("BluetoothHfpCodecPriorityController", "updateState");
        if (!DBG) {
            Log.w("BluetoothHfpCodecPriorityController", "updateState :: it is not debug mode");
            return;
        }
        ListPreference listPreference = (ListPreference) preference;
        CharSequence[] charSequenceArr = listPreference.mEntries;
        String str = SystemProperties.get("persist.bluetooth.hfpcodecpriority");
        if (TextUtils.isEmpty(str)) {
            Log.i(
                    "BluetoothHfpCodecPriorityController",
                    "updateState :: set default codec priority to none");
            str = "NONE";
            SystemProperties.set("persist.bluetooth.hfpcodecpriority", "NONE");
        }
        int findIndexOfValue = listPreference.findIndexOfValue(str);
        if (findIndexOfValue < 0) {
            Log.e("BluetoothHfpCodecPriorityController", "updateState :: failed to get index");
            listPreference.setSummary("EMPTY");
        } else {
            listPreference.setValueIndex(findIndexOfValue);
            listPreference.setSummary(charSequenceArr[findIndexOfValue]);
        }
    }
}
