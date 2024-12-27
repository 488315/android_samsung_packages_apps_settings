package com.android.settings.enterprise;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ManageDeviceAdminPreferenceController extends BasePreferenceController {
    private final DevicePolicyManager mDevicePolicyManager;
    private final EnterprisePrivacyFeatureProvider mFeatureProvider;

    public ManageDeviceAdminPreferenceController(Context context, String str) {
        super(context, str);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mFeatureProvider = featureFactoryImpl.getEnterprisePrivacyFeatureProvider();
        this.mDevicePolicyManager =
                (DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getSummary$0() {
        return this.mContext.getResources().getString(R.string.number_of_device_admins_none);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mContext.getResources().getBoolean(R.bool.config_show_manage_device_admin)
                ? 0
                : 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        if (getPreferenceKey().equalsIgnoreCase("device_administrators")) {
            return null;
        }
        EnterprisePrivacyFeatureProviderImpl enterprisePrivacyFeatureProviderImpl =
                (EnterprisePrivacyFeatureProviderImpl) this.mFeatureProvider;
        int i = 0;
        for (UserInfo userInfo :
                enterprisePrivacyFeatureProviderImpl.mUm.getProfiles(
                        EnterprisePrivacyFeatureProviderImpl.MY_USER_ID)) {
            if (!userInfo.isDualAppProfile()
                    && (!Flags.allowPrivateProfile()
                            || !android.multiuser.Flags.handleInterleavedSettingsForPrivateSpace()
                            || !android.multiuser.Flags.enablePrivateSpaceFeatures()
                            || !userInfo.isQuietModeEnabled()
                            || enterprisePrivacyFeatureProviderImpl
                                            .mUm
                                            .getUserProperties(userInfo.getUserHandle())
                                            .getShowInQuietMode()
                                    != 1)) {
                List activeAdminsAsUser =
                        enterprisePrivacyFeatureProviderImpl.mDpm.getActiveAdminsAsUser(
                                userInfo.id);
                if (activeAdminsAsUser != null) {
                    int size = activeAdminsAsUser.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        ComponentName componentName = (ComponentName) activeAdminsAsUser.get(i2);
                        if (!componentName
                                        .getPackageName()
                                        .equals("com.samsung.android.knox.containercore")
                                && !componentName
                                        .getPackageName()
                                        .equals("com.samsung.knox.securefolder")
                                && !"com.samsung.android.kgclient"
                                        .equals(componentName.getPackageName())) {
                            i++;
                        }
                    }
                }
            }
        }
        return i == 0
                ? this.mDevicePolicyManager
                        .getResources()
                        .getString(
                                "Settings.NUMBER_OF_DEVICE_ADMINS_NONE",
                                new Supplier() { // from class:
                                                 // com.android.settings.enterprise.ManageDeviceAdminPreferenceController$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        String lambda$getSummary$0;
                                        lambda$getSummary$0 =
                                                ManageDeviceAdminPreferenceController.this
                                                        .lambda$getSummary$0();
                                        return lambda$getSummary$0;
                                    }
                                })
                : this.mContext
                        .getResources()
                        .getQuantityString(
                                R.plurals.sec_number_of_device_admins, i, Integer.valueOf(i));
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

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
