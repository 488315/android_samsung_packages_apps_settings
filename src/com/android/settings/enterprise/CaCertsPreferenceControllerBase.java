package com.android.settings.enterprise;

import android.content.Context;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.utils.StringUtil;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class CaCertsPreferenceControllerBase extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public final EnterprisePrivacyFeatureProviderImpl mFeatureProvider;

    public CaCertsPreferenceControllerBase(Context context) {
        super(context);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mFeatureProvider = featureFactoryImpl.getEnterprisePrivacyFeatureProvider();
    }

    public abstract int getNumberOfCaCerts();

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return getNumberOfCaCerts() > 0;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        preference.setSummary(
                StringUtil.getIcuPluralsString(
                        this.mContext,
                        getNumberOfCaCerts(),
                        R.string.enterprise_privacy_number_ca_certs));
    }
}
