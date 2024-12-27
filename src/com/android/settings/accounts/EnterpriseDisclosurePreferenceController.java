package com.android.settings.accounts;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.SpannableStringBuilder;
import android.view.View;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.enterprise.EnterprisePrivacyFeatureProvider;
import com.android.settings.enterprise.EnterprisePrivacyFeatureProviderImpl;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.widget.FooterPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EnterpriseDisclosurePreferenceController extends BasePreferenceController {
    private final EnterprisePrivacyFeatureProvider mFeatureProvider;

    public EnterpriseDisclosurePreferenceController(Context context, String str) {
        super(context, str);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mFeatureProvider = featureFactoryImpl.getEnterprisePrivacyFeatureProvider();
    }

    private String getLabelName() {
        return this.mContext.getString(R.string.header_add_an_account);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateFooterPreference$0(View view) {
        this.mContext.startActivity(
                new Intent("android.settings.ENTERPRISE_PRIVACY_SETTINGS")
                        .setPackage(this.mContext.getPackageName()));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        CharSequence disclosure = getDisclosure();
        if (disclosure == null) {
            return;
        }
        updateFooterPreference(preferenceScreen, disclosure);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return getDisclosure() == null ? 3 : 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public CharSequence getDisclosure() {
        final EnterprisePrivacyFeatureProviderImpl enterprisePrivacyFeatureProviderImpl =
                (EnterprisePrivacyFeatureProviderImpl) this.mFeatureProvider;
        if (!enterprisePrivacyFeatureProviderImpl.hasDeviceOwner()) {
            return null;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        final CharSequence deviceOwnerOrganizationName =
                enterprisePrivacyFeatureProviderImpl.mDpm.getDeviceOwnerOrganizationName();
        if (deviceOwnerOrganizationName != null) {
            spannableStringBuilder.append(
                    (CharSequence)
                            enterprisePrivacyFeatureProviderImpl
                                    .mDpm
                                    .getResources()
                                    .getString(
                                            "Settings.DEVICE_MANAGED_WITH_NAME",
                                            new Supplier() { // from class:
                                                             // com.android.settings.enterprise.EnterprisePrivacyFeatureProviderImpl$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Supplier
                                                public final Object get() {
                                                    return EnterprisePrivacyFeatureProviderImpl.this
                                                            .mResources.getString(
                                                            R.string.do_disclosure_with_name,
                                                            deviceOwnerOrganizationName);
                                                }
                                            },
                                            deviceOwnerOrganizationName));
        } else {
            spannableStringBuilder.append(
                    (CharSequence)
                            enterprisePrivacyFeatureProviderImpl
                                    .mDpm
                                    .getResources()
                                    .getString(
                                            "Settings.DEVICE_MANAGED_WITHOUT_NAME",
                                            new Supplier() { // from class:
                                                             // com.android.settings.enterprise.EnterprisePrivacyFeatureProviderImpl$$ExternalSyntheticLambda1
                                                @Override // java.util.function.Supplier
                                                public final Object get() {
                                                    return EnterprisePrivacyFeatureProviderImpl.this
                                                            .mResources.getString(
                                                            R.string.do_disclosure_generic);
                                                }
                                            }));
        }
        return spannableStringBuilder;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void updateFooterPreference(
            PreferenceScreen preferenceScreen, CharSequence charSequence) {
        FooterPreference footerPreference =
                (FooterPreference) preferenceScreen.findPreference(getPreferenceKey());
        footerPreference.setTitle(charSequence);
        footerPreference.setLearnMoreAction(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.accounts.EnterpriseDisclosurePreferenceController$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        EnterpriseDisclosurePreferenceController.this
                                .lambda$updateFooterPreference$0(view);
                    }
                });
        footerPreference.setLearnMoreText(
                this.mContext.getString(
                        R.string.footer_learn_more_content_description, getLabelName()));
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
