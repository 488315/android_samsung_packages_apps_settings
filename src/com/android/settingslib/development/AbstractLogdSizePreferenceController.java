package com.android.settingslib.development;

import android.content.Intent;
import android.os.SystemProperties;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.development.BluetoothMaxConnectedAudioDevicesPreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AbstractLogdSizePreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener {
    static final String DEFAULT_SNET_TAG = "I";
    static final String LOW_RAM_CONFIG_PROPERTY_KEY = "ro.config.low_ram";
    static final String SELECT_LOGD_DEFAULT_SIZE_VALUE = "4194304";
    static final String SELECT_LOGD_MINIMUM_SIZE_VALUE = "65536";
    static final String SELECT_LOGD_SIZE_PROPERTY = "persist.logd.size";
    static final String SELECT_LOGD_SNET_TAG_PROPERTY = "persist.log.tag.snet_event_log";
    public ListPreference mLogdSize;

    public static String defaultLogdSizeValue() {
        String str = SystemProperties.get("ro.logd.size");
        return (str == null || str.length() == 0)
                ? SystemProperties.get(LOW_RAM_CONFIG_PROPERTY_KEY).equals("true")
                        ? SELECT_LOGD_MINIMUM_SIZE_VALUE
                        : SELECT_LOGD_DEFAULT_SIZE_VALUE
                : str;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (!(this instanceof BluetoothMaxConnectedAudioDevicesPreferenceController)) {
            this.mLogdSize = (ListPreference) preferenceScreen.findPreference("select_logd_size");
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "select_logd_size";
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (preference != this.mLogdSize) {
            return false;
        }
        writeLogdSizeOption(obj);
        return true;
    }

    public final void updateLogdSizeValues() {
        int i;
        if (this.mLogdSize != null) {
            String str = SystemProperties.get("persist.log.tag");
            String str2 = SystemProperties.get(SELECT_LOGD_SIZE_PROPERTY);
            if (str != null && str.startsWith("Settings")) {
                str2 = "32768";
            }
            LocalBroadcastManager localBroadcastManager =
                    LocalBroadcastManager.getInstance(this.mContext);
            if (localBroadcastManager.sendBroadcast(
                    new Intent(
                                    "com.android.settingslib.development.AbstractLogdSizePreferenceController.LOGD_SIZE_UPDATED")
                            .putExtra("CURRENT_LOGD_VALUE", str2))) {
                localBroadcastManager.executePendingBroadcasts();
            }
            if (str2 == null || str2.length() == 0) {
                str2 = defaultLogdSizeValue();
            }
            String[] stringArray =
                    this.mContext.getResources().getStringArray(R.array.select_logd_size_values);
            String[] stringArray2 =
                    this.mContext.getResources().getStringArray(R.array.select_logd_size_titles);
            if (SystemProperties.get(LOW_RAM_CONFIG_PROPERTY_KEY).equals("true")) {
                this.mLogdSize.setEntries(R.array.select_logd_size_lowram_titles);
                stringArray2 =
                        this.mContext
                                .getResources()
                                .getStringArray(R.array.select_logd_size_lowram_titles);
                i = 1;
            } else {
                i = 2;
            }
            String[] stringArray3 =
                    this.mContext.getResources().getStringArray(R.array.select_logd_size_summaries);
            for (int i2 = 0; i2 < stringArray2.length; i2++) {
                if (str2.equals(stringArray[i2]) || str2.equals(stringArray2[i2])) {
                    i = i2;
                    break;
                }
            }
            this.mLogdSize.setValue(stringArray[i]);
            this.mLogdSize.setSummary(stringArray3[i]);
        }
    }

    public final void writeLogdSizeOption(Object obj) {
        String str;
        boolean z = obj != null && obj.toString().equals("32768");
        String str2 = SystemProperties.get("persist.log.tag");
        String str3 = ApnSettings.MVNO_NONE;
        if (str2 == null) {
            str2 = ApnSettings.MVNO_NONE;
        }
        String replaceFirst =
                str2.replaceAll(",+Settings", ApnSettings.MVNO_NONE)
                        .replaceFirst("^Settings,*", ApnSettings.MVNO_NONE)
                        .replaceAll(",+", ",")
                        .replaceFirst(",+$", ApnSettings.MVNO_NONE);
        if (z) {
            String str4 = SystemProperties.get(SELECT_LOGD_SNET_TAG_PROPERTY);
            if ((str4 == null || str4.length() == 0)
                    && ((str = SystemProperties.get("log.tag.snet_event_log")) == null
                            || str.length() == 0)) {
                SystemProperties.set(SELECT_LOGD_SNET_TAG_PROPERTY, "I");
            }
            if (replaceFirst.length() != 0) {
                replaceFirst = ",".concat(replaceFirst);
            }
            replaceFirst =
                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                            "Settings", replaceFirst);
            obj = SELECT_LOGD_MINIMUM_SIZE_VALUE;
        }
        if (!replaceFirst.equals(str2)) {
            SystemProperties.set("persist.log.tag", replaceFirst);
        }
        String defaultLogdSizeValue = defaultLogdSizeValue();
        String obj2 =
                (obj == null || obj.toString().length() == 0)
                        ? defaultLogdSizeValue
                        : obj.toString();
        if (!defaultLogdSizeValue.equals(obj2)) {
            str3 = obj2;
        }
        SystemProperties.set(SELECT_LOGD_SIZE_PROPERTY, str3);
        SystemProperties.set("ctl.start", "logd-reinit");
        SystemPropPoker.sInstance.poke();
        updateLogdSizeValues();
    }
}
