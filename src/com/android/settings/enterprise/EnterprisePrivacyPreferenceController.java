package com.android.settings.enterprise;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.preference.Preference;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EnterprisePrivacyPreferenceController extends BasePreferenceController
        implements PreferenceControllerMixin {
    private final PrivacyPreferenceControllerHelper mPrivacyPreferenceControllerHelper;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public EnterprisePrivacyPreferenceController(Context context, String str) {
        this(context, new PrivacyPreferenceControllerHelper(context), str);
        Objects.requireNonNull(context);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (this.mPrivacyPreferenceControllerHelper.mFeatureProvider.hasDeviceOwner()) {
            PrivacyPreferenceControllerHelper privacyPreferenceControllerHelper =
                    this.mPrivacyPreferenceControllerHelper;
            if (privacyPreferenceControllerHelper.mDevicePolicyManager.isDeviceManaged()) {
                DevicePolicyManager devicePolicyManager =
                        privacyPreferenceControllerHelper.mDevicePolicyManager;
                if (devicePolicyManager.getDeviceOwnerType(
                                devicePolicyManager.getDeviceOwnerComponentOnAnyUser())
                        == 1) {}
            }
            return 0;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        this.mPrivacyPreferenceControllerHelper.updateState(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @VisibleForTesting
    public EnterprisePrivacyPreferenceController(
            Context context,
            PrivacyPreferenceControllerHelper privacyPreferenceControllerHelper,
            String str) {
        super(context, str);
        Objects.requireNonNull(context);
        Objects.requireNonNull(privacyPreferenceControllerHelper);
        this.mPrivacyPreferenceControllerHelper = privacyPreferenceControllerHelper;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
