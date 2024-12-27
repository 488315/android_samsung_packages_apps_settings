package com.android.settings.enterprise;

import android.app.admin.DevicePolicyManager;
import android.content.Context;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.AbstractPreferenceController;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AlwaysOnVpnCurrentUserPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public final DevicePolicyManager mDevicePolicyManager;
    public final EnterprisePrivacyFeatureProviderImpl mFeatureProvider;

    public AlwaysOnVpnCurrentUserPreferenceController(Context context) {
        super(context);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mFeatureProvider = featureFactoryImpl.getEnterprisePrivacyFeatureProvider();
        this.mDevicePolicyManager =
                (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "always_on_vpn_primary_user";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mFeatureProvider.mVm.getAlwaysOnVpnPackageForUser(
                        EnterprisePrivacyFeatureProviderImpl.MY_USER_ID)
                != null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        if (this.mFeatureProvider.isInCompMode()) {
            final int i = 0;
            preference.setTitle(
                    this.mDevicePolicyManager
                            .getResources()
                            .getString(
                                    "Settings.ALWAYS_ON_VPN_PERSONAL_PROFILE",
                                    new Supplier(
                                            this) { // from class:
                                                    // com.android.settings.enterprise.AlwaysOnVpnCurrentUserPreferenceController$$ExternalSyntheticLambda0
                                        public final /* synthetic */
                                        AlwaysOnVpnCurrentUserPreferenceController f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            String string;
                                            String string2;
                                            int i2 = i;
                                            AlwaysOnVpnCurrentUserPreferenceController
                                                    alwaysOnVpnCurrentUserPreferenceController =
                                                            this.f$0;
                                            switch (i2) {
                                                case 0:
                                                    string =
                                                            alwaysOnVpnCurrentUserPreferenceController
                                                                    .mContext.getString(
                                                                    R.string
                                                                            .enterprise_privacy_always_on_vpn_personal);
                                                    return string;
                                                default:
                                                    string2 =
                                                            alwaysOnVpnCurrentUserPreferenceController
                                                                    .mContext.getString(
                                                                    R.string
                                                                            .enterprise_privacy_always_on_vpn_device);
                                                    return string2;
                                            }
                                        }
                                    }));
        } else {
            final int i2 = 1;
            preference.setTitle(
                    this.mDevicePolicyManager
                            .getResources()
                            .getString(
                                    "Settings.ALWAYS_ON_VPN_DEVICE",
                                    new Supplier(
                                            this) { // from class:
                                                    // com.android.settings.enterprise.AlwaysOnVpnCurrentUserPreferenceController$$ExternalSyntheticLambda0
                                        public final /* synthetic */
                                        AlwaysOnVpnCurrentUserPreferenceController f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            String string;
                                            String string2;
                                            int i22 = i2;
                                            AlwaysOnVpnCurrentUserPreferenceController
                                                    alwaysOnVpnCurrentUserPreferenceController =
                                                            this.f$0;
                                            switch (i22) {
                                                case 0:
                                                    string =
                                                            alwaysOnVpnCurrentUserPreferenceController
                                                                    .mContext.getString(
                                                                    R.string
                                                                            .enterprise_privacy_always_on_vpn_personal);
                                                    return string;
                                                default:
                                                    string2 =
                                                            alwaysOnVpnCurrentUserPreferenceController
                                                                    .mContext.getString(
                                                                    R.string
                                                                            .enterprise_privacy_always_on_vpn_device);
                                                    return string2;
                                            }
                                        }
                                    }));
        }
    }
}
