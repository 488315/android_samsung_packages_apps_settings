package com.android.settings.enterprise;

import android.app.admin.DevicePolicyManager;
import android.content.Context;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;

import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PrivacyPreferenceControllerHelper {
    public final Context mContext;
    public final DevicePolicyManager mDevicePolicyManager;
    public final EnterprisePrivacyFeatureProviderImpl mFeatureProvider;

    public PrivacyPreferenceControllerHelper(Context context) {
        Objects.requireNonNull(context);
        this.mContext = context;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mFeatureProvider = featureFactoryImpl.getEnterprisePrivacyFeatureProvider();
        this.mDevicePolicyManager =
                (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
    }

    public final void updateState(Preference preference) {
        if (preference == null) {
            return;
        }
        CharSequence deviceOwnerOrganizationName =
                this.mFeatureProvider.mDpm.getDeviceOwnerOrganizationName();
        final String charSequence =
                deviceOwnerOrganizationName == null ? null : deviceOwnerOrganizationName.toString();
        if (charSequence == null) {
            preference.setSummary(
                    this.mDevicePolicyManager
                            .getResources()
                            .getString(
                                    "Settings.MANAGED_DEVICE_INFO_SUMMARY",
                                    new Supplier() { // from class:
                                                     // com.android.settings.enterprise.PrivacyPreferenceControllerHelper$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            return PrivacyPreferenceControllerHelper.this.mContext
                                                    .getString(
                                                            R.string
                                                                    .enterprise_privacy_settings_summary_generic);
                                        }
                                    }));
        } else {
            preference.setSummary(
                    this.mDevicePolicyManager
                            .getResources()
                            .getString(
                                    "Settings.MANAGED_DEVICE_INFO_SUMMARY_WITH_NAME",
                                    new Supplier() { // from class:
                                                     // com.android.settings.enterprise.PrivacyPreferenceControllerHelper$$ExternalSyntheticLambda1
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            return PrivacyPreferenceControllerHelper.this
                                                    .mContext
                                                    .getResources()
                                                    .getString(
                                                            R.string
                                                                    .enterprise_privacy_settings_summary_with_name,
                                                            charSequence);
                                        }
                                    },
                                    charSequence));
        }
    }
}
