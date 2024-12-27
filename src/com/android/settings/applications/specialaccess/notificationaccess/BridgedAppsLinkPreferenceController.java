package com.android.settings.applications.specialaccess.notificationaccess;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.service.notification.NotificationListenerFilter;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.notification.NotificationBackend;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BridgedAppsLinkPreferenceController extends BasePreferenceController {
    private ComponentName mCn;
    private NotificationListenerFilter mNlf;
    private NotificationBackend mNm;
    private int mTargetSdk;
    private int mUserId;

    public BridgedAppsLinkPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        boolean z;
        NotificationBackend notificationBackend = this.mNm;
        ComponentName componentName = this.mCn;
        notificationBackend.getClass();
        try {
            z = NotificationBackend.sINM.isNotificationListenerAccessGranted(componentName);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            z = false;
        }
        if (!z) {
            return 5;
        }
        if (this.mTargetSdk > 31) {
            return 0;
        }
        NotificationBackend notificationBackend2 = this.mNm;
        ComponentName componentName2 = this.mCn;
        int i = this.mUserId;
        notificationBackend2.getClass();
        NotificationListenerFilter listenerFilter =
                NotificationBackend.getListenerFilter(componentName2, i);
        this.mNlf = listenerFilter;
        return (listenerFilter.areAllTypesAllowed() && this.mNlf.getDisallowedPackages().isEmpty())
                ? 5
                : 0;
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

    public BridgedAppsLinkPreferenceController setCn(ComponentName componentName) {
        this.mCn = componentName;
        return this;
    }

    public BridgedAppsLinkPreferenceController setNm(NotificationBackend notificationBackend) {
        this.mNm = notificationBackend;
        return this;
    }

    public BridgedAppsLinkPreferenceController setTargetSdk(int i) {
        this.mTargetSdk = i;
        return this;
    }

    public BridgedAppsLinkPreferenceController setUserId(int i) {
        this.mUserId = i;
        return this;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        preference.setEnabled(getAvailabilityStatus() == 0);
        super.updateState(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
