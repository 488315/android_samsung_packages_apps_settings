package com.android.settings.enterprise;

import android.content.Context;
import android.os.UserHandle;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.applications.ApplicationFeatureProviderImpl;
import com.android.settings.applications.EnterpriseDefaultApps;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.users.UserFeatureProviderImpl;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.utils.StringUtil;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EnterpriseSetDefaultAppsPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public final ApplicationFeatureProviderImpl mApplicationFeatureProvider;
    public final UserFeatureProviderImpl mUserFeatureProvider;

    public EnterpriseSetDefaultAppsPreferenceController(Context context) {
        super(context);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mApplicationFeatureProvider = featureFactoryImpl.getApplicationFeatureProvider();
        this.mUserFeatureProvider =
                (UserFeatureProviderImpl)
                        featureFactoryImpl.userFeatureProvider$delegate.getValue();
    }

    public final int getNumberOfEnterpriseSetDefaultApps() {
        int i = 0;
        for (UserHandle userHandle : this.mUserFeatureProvider.mUm.getUserProfiles()) {
            for (EnterpriseDefaultApps enterpriseDefaultApps : EnterpriseDefaultApps.values()) {
                i +=
                        ((ArrayList)
                                        this.mApplicationFeatureProvider
                                                .findPersistentPreferredActivities(
                                                        userHandle.getIdentifier(),
                                                        enterpriseDefaultApps.getIntents()))
                                .size();
            }
        }
        return i;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "number_enterprise_set_default_apps";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return getNumberOfEnterpriseSetDefaultApps() > 0;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        preference.setSummary(
                StringUtil.getIcuPluralsString(
                        this.mContext,
                        getNumberOfEnterpriseSetDefaultApps(),
                        R.string.enterprise_privacy_number_packages));
    }
}
