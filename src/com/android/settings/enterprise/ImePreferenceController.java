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
public final class ImePreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public final EnterprisePrivacyFeatureProviderImpl mFeatureProvider;

    public ImePreferenceController(Context context) {
        super(context);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mFeatureProvider = featureFactoryImpl.getEnterprisePrivacyFeatureProvider();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "input_method";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mFeatureProvider.getImeLabelIfOwnerSet() != null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        preference.setSummary(
                ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class))
                        .getResources()
                        .getString(
                                "Settings.ADMIN_ACTION_SET_INPUT_METHOD_NAME",
                                new Supplier() { // from class:
                                                 // com.android.settings.enterprise.ImePreferenceController$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        String string;
                                        string =
                                                r0.mContext
                                                        .getResources()
                                                        .getString(
                                                                R.string
                                                                        .enterprise_privacy_input_method_name,
                                                                ImePreferenceController.this
                                                                        .mFeatureProvider
                                                                        .getImeLabelIfOwnerSet());
                                        return string;
                                    }
                                },
                                this.mFeatureProvider.getImeLabelIfOwnerSet()));
    }
}
