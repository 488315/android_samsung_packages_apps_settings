package com.android.settingslib.enterprise;

import android.content.Context;

import com.android.settings.enterprise.DeviceAdminStringProviderImpl;
import com.android.settings.enterprise.DeviceAdminStringProviderImpl$$ExternalSyntheticLambda0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ManagedDeviceActionDisabledByAdminController
        extends BaseActionDisabledByAdminController {
    @Override // com.android.settingslib.enterprise.BaseActionDisabledByAdminController
    public final CharSequence getAdminSupportContentString(CharSequence charSequence) {
        if (charSequence != null) {
            return charSequence;
        }
        DeviceAdminStringProviderImpl deviceAdminStringProviderImpl = this.mStringProvider;
        return deviceAdminStringProviderImpl
                .mDevicePolicyManager
                .getResources()
                .getString(
                        "Settings.CONTACT_YOUR_IT_ADMIN",
                        new DeviceAdminStringProviderImpl$$ExternalSyntheticLambda0(
                                deviceAdminStringProviderImpl, 0));
    }

    @Override // com.android.settingslib.enterprise.BaseActionDisabledByAdminController
    public final String getAdminSupportTitle() {
        DeviceAdminStringProviderImpl deviceAdminStringProviderImpl = this.mStringProvider;
        return deviceAdminStringProviderImpl
                .mDevicePolicyManager
                .getResources()
                .getString(
                        "Settings.DISABLED_BY_IT_ADMIN_TITLE",
                        new DeviceAdminStringProviderImpl$$ExternalSyntheticLambda0(
                                deviceAdminStringProviderImpl, 1));
    }

    @Override // com.android.settingslib.enterprise.BaseActionDisabledByAdminController
    public final void setupLearnMoreButton(Context context) {}
}
