package com.android.settings.enterprise;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.text.format.DateUtils;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.AbstractPreferenceController;

import java.util.Date;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AdminActionPreferenceControllerBase extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public final EnterprisePrivacyFeatureProviderImpl mFeatureProvider;

    public AdminActionPreferenceControllerBase(Context context) {
        super(context);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mFeatureProvider = featureFactoryImpl.getEnterprisePrivacyFeatureProvider();
    }

    public abstract Date getAdminActionTimestamp();

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        Date adminActionTimestamp = getAdminActionTimestamp();
        preference.setSummary(
                adminActionTimestamp == null
                        ? ((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
                                .getResources()
                                .getString(
                                        "Settings.ADMIN_ACTION_NONE",
                                        new Supplier() { // from class:
                                                         // com.android.settings.enterprise.AdminActionPreferenceControllerBase$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Supplier
                                            public final Object get() {
                                                String string;
                                                string =
                                                        AdminActionPreferenceControllerBase.this
                                                                .mContext.getString(
                                                                R.string.enterprise_privacy_none);
                                                return string;
                                            }
                                        })
                        : DateUtils.formatDateTime(
                                this.mContext, adminActionTimestamp.getTime(), 17));
    }
}
