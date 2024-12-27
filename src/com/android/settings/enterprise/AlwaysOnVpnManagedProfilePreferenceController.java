package com.android.settings.enterprise;

import android.content.Context;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.AbstractPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AlwaysOnVpnManagedProfilePreferenceController
        extends AbstractPreferenceController implements PreferenceControllerMixin {
    public final EnterprisePrivacyFeatureProviderImpl mFeatureProvider;

    public AlwaysOnVpnManagedProfilePreferenceController(Context context) {
        super(context);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mFeatureProvider = featureFactoryImpl.getEnterprisePrivacyFeatureProvider();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "always_on_vpn_managed_profile";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        EnterprisePrivacyFeatureProviderImpl enterprisePrivacyFeatureProviderImpl =
                this.mFeatureProvider;
        int managedProfileUserId = enterprisePrivacyFeatureProviderImpl.getManagedProfileUserId();
        return (managedProfileUserId == -10000
                        || enterprisePrivacyFeatureProviderImpl.mVm.getAlwaysOnVpnPackageForUser(
                                        managedProfileUserId)
                                == null)
                ? false
                : true;
    }
}
