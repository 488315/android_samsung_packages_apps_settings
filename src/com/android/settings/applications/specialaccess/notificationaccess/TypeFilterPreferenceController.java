package com.android.settings.applications.specialaccess.notificationaccess;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.service.notification.NotificationListenerFilter;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.notification.NotificationBackend;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class TypeFilterPreferenceController extends BasePreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    private static final String FLAG_SEPARATOR = "\\|";
    private static final String TAG = "TypeFilterPrefCntlr";
    private ComponentName mCn;
    private NotificationListenerFilter mNlf;
    private NotificationBackend mNm;
    private ServiceInfo mSi;
    private int mTargetSdk;
    private int mUserId;

    public TypeFilterPreferenceController(Context context, String str) {
        super(context, str);
    }

    private boolean hasFlag(int i, int i2) {
        return (i & i2) != 0;
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

    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    public abstract int getType();

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        NotificationBackend notificationBackend = this.mNm;
        ComponentName componentName = this.mCn;
        int i = this.mUserId;
        notificationBackend.getClass();
        this.mNlf = NotificationBackend.getListenerFilter(componentName, i);
        boolean booleanValue = ((Boolean) obj).booleanValue();
        int types = this.mNlf.getTypes();
        this.mNlf.setTypes(booleanValue ? getType() | types : (~getType()) & types);
        NotificationBackend notificationBackend2 = this.mNm;
        ComponentName componentName2 = this.mCn;
        int i2 = this.mUserId;
        NotificationListenerFilter notificationListenerFilter = this.mNlf;
        notificationBackend2.getClass();
        try {
            NotificationBackend.sINM.setListenerFilter(
                    componentName2, i2, notificationListenerFilter);
            return true;
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            return true;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public TypeFilterPreferenceController setCn(ComponentName componentName) {
        this.mCn = componentName;
        return this;
    }

    public TypeFilterPreferenceController setNm(NotificationBackend notificationBackend) {
        this.mNm = notificationBackend;
        return this;
    }

    public TypeFilterPreferenceController setServiceInfo(ServiceInfo serviceInfo) {
        this.mSi = serviceInfo;
        return this;
    }

    public TypeFilterPreferenceController setTargetSdk(int i) {
        this.mTargetSdk = i;
        return this;
    }

    public TypeFilterPreferenceController setUserId(int i) {
        this.mUserId = i;
        return this;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        boolean z;
        Bundle bundle;
        String obj;
        NotificationBackend notificationBackend = this.mNm;
        ComponentName componentName = this.mCn;
        int i = this.mUserId;
        notificationBackend.getClass();
        NotificationListenerFilter listenerFilter =
                NotificationBackend.getListenerFilter(componentName, i);
        this.mNlf = listenerFilter;
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
        checkBoxPreference.setChecked(hasFlag(listenerFilter.getTypes(), getType()));
        ServiceInfo serviceInfo = this.mSi;
        if (serviceInfo != null
                && (bundle = serviceInfo.metaData) != null
                && bundle.containsKey("android.service.notification.disabled_filter_types")
                && (obj =
                                this.mSi
                                        .metaData
                                        .get("android.service.notification.disabled_filter_types")
                                        .toString())
                        != null) {
            int i2 = 0;
            for (String str : obj.split(FLAG_SEPARATOR)) {
                if (!TextUtils.isEmpty(str)) {
                    if (str.equalsIgnoreCase("ONGOING")) {
                        i2 |= 8;
                    } else if (str.equalsIgnoreCase("CONVERSATIONS")) {
                        i2 |= 1;
                    } else if (str.equalsIgnoreCase("SILENT")) {
                        i2 |= 4;
                    } else if (str.equalsIgnoreCase("ALERTING")) {
                        i2 |= 2;
                    } else {
                        try {
                            i2 |= Integer.parseInt(str);
                        } catch (NumberFormatException unused) {
                        }
                    }
                }
            }
            if (hasFlag(i2, getType()) && !checkBoxPreference.mChecked) {
                z = true;
                preference.setEnabled((getAvailabilityStatus() == 0 || z) ? false : true);
            }
        }
        z = false;
        preference.setEnabled((getAvailabilityStatus() == 0 || z) ? false : true);
    }

    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
