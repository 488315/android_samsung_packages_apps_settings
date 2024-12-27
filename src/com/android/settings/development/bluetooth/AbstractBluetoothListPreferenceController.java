package com.android.settings.development.bluetooth;

import android.content.Context;
import android.util.Log;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.development.BluetoothA2dpConfigStore;
import com.android.settingslib.core.lifecycle.Lifecycle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AbstractBluetoothListPreferenceController
        extends AbstractBluetoothPreferenceController
        implements Preference.OnPreferenceChangeListener {
    public static final boolean DEBUG = Log.isLoggable("AbstrBtListPrefCtrl", 3);
    public final BluetoothA2dpConfigStore mBluetoothA2dpConfigStore;
    public final String mDefaultEntry;
    public final String mDefaultValue;
    public ListPreference mListPreference;

    public AbstractBluetoothListPreferenceController(
            Context context,
            Lifecycle lifecycle,
            BluetoothA2dpConfigStore bluetoothA2dpConfigStore) {
        super(context, lifecycle);
        this.mDefaultEntry =
                this.mContext.getString(R.string.bluetooth_audio_codec_default_selection);
        this.mDefaultValue = String.valueOf(1000);
        this.mBluetoothA2dpConfigStore = bluetoothA2dpConfigStore;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mListPreference =
                (ListPreference)
                        preferenceScreen.findPreference("bluetooth_audio_codec_settings_list");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0037  */
    @Override // com.android.settings.development.bluetooth.AbstractBluetoothPreferenceController,
              // com.android.settings.development.BluetoothServiceConnectionListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onBluetoothServiceConnected(android.bluetooth.BluetoothA2dp r4) {
        /*
            r3 = this;
            super.onBluetoothServiceConnected(r4)
            android.bluetooth.BluetoothA2dp r4 = r3.mBluetoothA2dp
            java.lang.String r0 = "AbstrBtListPrefCtrl"
            r1 = 0
            if (r4 != 0) goto L11
            java.lang.String r4 = "getBluetoothCodecStatus: Unable to get codec status. Bluetooth A2dp is null."
            android.util.Log.e(r0, r4)
        Lf:
            r4 = r1
            goto L29
        L11:
            android.bluetooth.BluetoothDevice r2 = r3.getA2dpActiveDevice()
            if (r2 != 0) goto L1d
            java.lang.String r4 = "getBluetoothCodecStatus: Unable to get codec status. No active device."
            android.util.Log.e(r0, r4)
            goto Lf
        L1d:
            android.bluetooth.BluetoothCodecStatus r4 = r4.getCodecStatus(r2)
            if (r4 != 0) goto L29
            java.lang.String r4 = "getBluetoothCodecStatus: Codec status is null"
            android.util.Log.e(r0, r4)
            goto Lf
        L29:
            if (r4 != 0) goto L31
            java.lang.String r4 = "getCurrentCodecConfig: Unable to get current codec config. Codec status is null"
            android.util.Log.e(r0, r4)
            goto L35
        L31:
            android.bluetooth.BluetoothCodecConfig r1 = r4.getCodecConfig()
        L35:
            if (r1 != 0) goto L3d
            java.lang.String r3 = "initConfigStore: Current codec config is null."
            android.util.Log.e(r0, r3)
            goto L67
        L3d:
            com.android.settings.development.BluetoothA2dpConfigStore r3 = r3.mBluetoothA2dpConfigStore
            if (r3 != 0) goto L47
            java.lang.String r3 = "initConfigStore: Bluetooth A2dp Config Store is null."
            android.util.Log.e(r0, r3)
            goto L67
        L47:
            r1.getExtendedCodecType()
            int r4 = r1.getSampleRate()
            r3.mSampleRate = r4
            int r4 = r1.getBitsPerSample()
            r3.mBitsPerSample = r4
            int r4 = r1.getChannelMode()
            r3.mChannelMode = r4
            r4 = 1000000(0xf4240, float:1.401298E-39)
            r3.mCodecPriority = r4
            long r0 = r1.getCodecSpecific1()
            r3.mCodecSpecific1Value = r0
        L67:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.development.bluetooth.AbstractBluetoothListPreferenceController.onBluetoothServiceConnected(android.bluetooth.BluetoothA2dp):void");
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        if (DEBUG) {
            Log.d("AbstrBtListPrefCtrl", "onDeveloperOptionsSwitchDisabled");
        }
        if (this.mListPreference == null) {
            Log.e(
                    "AbstrBtListPrefCtrl",
                    "onDeveloperOptionsSwitchDisabled: List preference is null");
        } else {
            ((BluetoothCodecListPreferenceController) this).setupDefaultListPreference();
        }
    }

    public final void setupDefaultListPreference() {
        String str = this.mDefaultValue;
        String str2 = this.mDefaultEntry;
        if (DEBUG) {
            Log.d(
                    "AbstrBtListPrefCtrl",
                    "setupDefaultListPreference: mDefaultEntry=" + str2 + ", mDefaultValue=" + str);
        }
        ListPreference listPreference = this.mListPreference;
        if (listPreference == null) {
            Log.e("AbstrBtListPrefCtrl", "setupListPreference: List preference is null");
            return;
        }
        listPreference.setEntries(new String[] {str2});
        ListPreference listPreference2 = this.mListPreference;
        listPreference2.mEntryValues = new String[] {str};
        listPreference2.setValue(str);
        this.mListPreference.setSummary(str2);
    }
}
