package com.android.settings.enterprise;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.UserHandle;

import androidx.preference.Preference;

import com.android.settings.R;

import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CaCertsCurrentUserPreferenceController extends CaCertsPreferenceControllerBase {
    static final String CA_CERTS_CURRENT_USER = "ca_certs_current_user";
    public final DevicePolicyManager mDevicePolicyManager;

    public CaCertsCurrentUserPreferenceController(Context context) {
        super(context);
        this.mDevicePolicyManager =
                (DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class);
    }

    @Override // com.android.settings.enterprise.CaCertsPreferenceControllerBase
    public final int getNumberOfCaCerts() {
        List ownerInstalledCaCerts =
                this.mFeatureProvider.mDpm.getOwnerInstalledCaCerts(
                        new UserHandle(EnterprisePrivacyFeatureProviderImpl.MY_USER_ID));
        if (ownerInstalledCaCerts == null) {
            return 0;
        }
        return ownerInstalledCaCerts.size();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return CA_CERTS_CURRENT_USER;
    }

    @Override // com.android.settings.enterprise.CaCertsPreferenceControllerBase,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        if (this.mFeatureProvider.isInCompMode()) {
            final int i = 0;
            preference.setTitle(
                    this.mDevicePolicyManager
                            .getResources()
                            .getString(
                                    "Settings.CA_CERTS_PERSONAL_PROFILE",
                                    new Supplier(
                                            this) { // from class:
                                                    // com.android.settings.enterprise.CaCertsCurrentUserPreferenceController$$ExternalSyntheticLambda0
                                        public final /* synthetic */
                                        CaCertsCurrentUserPreferenceController f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            String string;
                                            String string2;
                                            int i2 = i;
                                            CaCertsCurrentUserPreferenceController
                                                    caCertsCurrentUserPreferenceController =
                                                            this.f$0;
                                            switch (i2) {
                                                case 0:
                                                    string =
                                                            caCertsCurrentUserPreferenceController
                                                                    .mContext.getString(
                                                                    R.string
                                                                            .enterprise_privacy_ca_certs_personal);
                                                    return string;
                                                default:
                                                    string2 =
                                                            caCertsCurrentUserPreferenceController
                                                                    .mContext.getString(
                                                                    R.string
                                                                            .enterprise_privacy_ca_certs_device);
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
                                    "Settings.CA_CERTS_DEVICE",
                                    new Supplier(
                                            this) { // from class:
                                                    // com.android.settings.enterprise.CaCertsCurrentUserPreferenceController$$ExternalSyntheticLambda0
                                        public final /* synthetic */
                                        CaCertsCurrentUserPreferenceController f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            String string;
                                            String string2;
                                            int i22 = i2;
                                            CaCertsCurrentUserPreferenceController
                                                    caCertsCurrentUserPreferenceController =
                                                            this.f$0;
                                            switch (i22) {
                                                case 0:
                                                    string =
                                                            caCertsCurrentUserPreferenceController
                                                                    .mContext.getString(
                                                                    R.string
                                                                            .enterprise_privacy_ca_certs_personal);
                                                    return string;
                                                default:
                                                    string2 =
                                                            caCertsCurrentUserPreferenceController
                                                                    .mContext.getString(
                                                                    R.string
                                                                            .enterprise_privacy_ca_certs_device);
                                                    return string2;
                                            }
                                        }
                                    }));
        }
    }
}
