package com.android.settings.development;

import androidx.preference.Preference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WirelessDebuggingFragment$$ExternalSyntheticLambda0
        implements Preference.OnPreferenceClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WirelessDebuggingFragment f$0;

    public /* synthetic */ WirelessDebuggingFragment$$ExternalSyntheticLambda0(
            WirelessDebuggingFragment wirelessDebuggingFragment, int i) {
        this.$r8$classId = i;
        this.f$0 = wirelessDebuggingFragment;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        WirelessDebuggingFragment wirelessDebuggingFragment = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                AdbIpAddressPreferenceController adbIpAddressPreferenceController =
                        WirelessDebuggingFragment.sAdbIpAddressPreferenceController;
                wirelessDebuggingFragment.showDialog(0);
                break;
            case 1:
                AdbIpAddressPreferenceController adbIpAddressPreferenceController2 =
                        WirelessDebuggingFragment.sAdbIpAddressPreferenceController;
                wirelessDebuggingFragment.getClass();
                wirelessDebuggingFragment.launchPairedDeviceDetailsFragment(
                        (AdbPairedDevicePreference) preference);
                break;
            default:
                AdbIpAddressPreferenceController adbIpAddressPreferenceController3 =
                        WirelessDebuggingFragment.sAdbIpAddressPreferenceController;
                wirelessDebuggingFragment.getClass();
                wirelessDebuggingFragment.launchPairedDeviceDetailsFragment(
                        (AdbPairedDevicePreference) preference);
                break;
        }
        return true;
    }
}
