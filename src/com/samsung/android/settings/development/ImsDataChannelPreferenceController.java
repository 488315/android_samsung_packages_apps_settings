package com.samsung.android.settings.development;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.SwitchPreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ImsDataChannelPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    public final boolean mIsIdcPkgInstalled;
    public int mPhoneCount;
    public final TelephonyManager mTelephonyManager;

    public ImsDataChannelPreferenceController(Context context) {
        super(context);
        boolean z;
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
        this.mPhoneCount = -1;
        try {
            context.getPackageManager().getApplicationInfo("com.samsung.android.imsdcservice", 128);
            Log.d("ImsDataChannelPreferenceController", "isPackageInstalled: true");
            z = true;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d("ImsDataChannelPreferenceController", "isPackageInstalled: false");
            z = false;
        }
        this.mIsIdcPkgInstalled = z;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "ims_data_channel";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (!this.mIsIdcPkgInstalled) {
            return false;
        }
        if (this.mPhoneCount == -1) {
            this.mPhoneCount = this.mTelephonyManager.getSupportedModemCount();
        }
        int i = this.mPhoneCount;
        if (i <= 0) {
            return false;
        }
        return Arrays.stream(IntStream.range(0, i).toArray())
                .anyMatch(
                        new IntPredicate() { // from class:
                                             // com.samsung.android.settings.development.ImsDataChannelPreferenceController$$ExternalSyntheticLambda0
                            @Override // java.util.function.IntPredicate
                            public final boolean test(int i2) {
                                return ImsDataChannelPreferenceController.this.mTelephonyManager
                                                .getSimState(i2)
                                        != 1;
                            }
                        });
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        Log.i(
                "ImsDataChannelPreferenceController",
                "onPreferenceChange: data channel enabled: " + booleanValue);
        Log.d(
                "ImsDataChannelPreferenceController",
                "setImsDataChannelEnabled set: " + booleanValue);
        SystemProperties.set("persist.ims.datachannel.enable", booleanValue ? "true" : "false");
        updateState(this.mPreference);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        SwitchPreference switchPreference = (SwitchPreference) this.mPreference;
        boolean z = SystemProperties.getBoolean("persist.ims.datachannel.enable", false);
        Log.d("ImsDataChannelPreferenceController", "getImsDataChannelEnabled: " + z);
        switchPreference.setChecked(z);
    }
}
