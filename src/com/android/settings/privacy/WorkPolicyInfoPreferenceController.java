package com.android.settings.privacy;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.text.TextUtils;

import androidx.preference.Preference;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.enterprise.EnterprisePrivacyFeatureProvider;
import com.android.settings.enterprise.EnterprisePrivacyFeatureProviderImpl;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.safetycenter.SafetyCenterManagerWrapper;
import com.android.settingslib.utils.WorkPolicyUtils;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WorkPolicyInfoPreferenceController extends BasePreferenceController {
    private final EnterprisePrivacyFeatureProvider mEnterpriseProvider;

    public WorkPolicyInfoPreferenceController(Context context, String str) {
        super(context, str);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mEnterpriseProvider = featureFactoryImpl.getEnterprisePrivacyFeatureProvider();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        SafetyCenterManagerWrapper safetyCenterManagerWrapper = SafetyCenterManagerWrapper.get();
        Context context = this.mContext;
        safetyCenterManagerWrapper.getClass();
        if (!SafetyCenterManagerWrapper.isEnabled(context)) {
            WorkPolicyUtils workPolicyUtils =
                    ((EnterprisePrivacyFeatureProviderImpl) this.mEnterpriseProvider)
                            .mWorkPolicyUtils;
            if (workPolicyUtils.getWorkPolicyInfoIntentDO() != null
                    || workPolicyUtils.getWorkPolicyInfoIntentPO() != null) {
                return 0;
            }
        }
        return 3;
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(getPreferenceKey(), preference.getKey())) {
            return false;
        }
        EnterprisePrivacyFeatureProvider enterprisePrivacyFeatureProvider =
                this.mEnterpriseProvider;
        Context context = preference.getContext();
        WorkPolicyUtils workPolicyUtils =
                ((EnterprisePrivacyFeatureProviderImpl) enterprisePrivacyFeatureProvider)
                        .mWorkPolicyUtils;
        Intent workPolicyInfoIntentDO = workPolicyUtils.getWorkPolicyInfoIntentDO();
        if (workPolicyInfoIntentDO != null) {
            context.startActivity(workPolicyInfoIntentDO);
            return true;
        }
        Intent workPolicyInfoIntentPO = workPolicyUtils.getWorkPolicyInfoIntentPO();
        int managedProfileUserId = workPolicyUtils.getManagedProfileUserId();
        if (workPolicyInfoIntentPO == null || managedProfileUserId == -10000) {
            return true;
        }
        context.startActivityAsUser(workPolicyInfoIntentPO, UserHandle.of(managedProfileUserId));
        return true;
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
