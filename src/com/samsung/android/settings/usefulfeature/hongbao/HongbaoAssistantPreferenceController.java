package com.samsung.android.settings.usefulfeature.hongbao;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.secutil.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;

import com.android.settings.DisplaySettings$$ExternalSyntheticOutline0;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class HongbaoAssistantPreferenceController extends BasePreferenceController {
    private static final String KEY_HONGBAO_ASSISTANT = "hongbao_assistant";
    private static final String TAG = "HongbaoAssistantPreferenceController";
    private SecPreferenceScreen mPreference;

    public HongbaoAssistantPreferenceController(Context context) {
        this(context, KEY_HONGBAO_ASSISTANT);
    }

    private boolean isExternalServiceExist() {
        ResolveInfo resolveService =
                this.mContext
                        .getPackageManager()
                        .resolveService(
                                DisplaySettings$$ExternalSyntheticOutline0.m(
                                        "com.samsung.hongbaoassistant",
                                        "com.samsung.hongbaoassistant.ExternalService"),
                                0);
        return (resolveService == null || resolveService.serviceInfo == null) ? false : true;
    }

    private static boolean isHongbaoAssistantEnabled(Context context) {
        String str = "com.tencent.mm";
        if (UserHandle.myUserId() != 0
                || context.getPackageManager().isSafeMode()
                || !Utils.hasPackage(context, "com.samsung.hongbaoassistant")) {
            return false;
        }
        try {
            Bundle bundle =
                    context.getPackageManager()
                            .getApplicationInfo("com.samsung.hongbaoassistant", 128)
                            .metaData;
            if (bundle != null) {
                str = bundle.getString("HongbaoSupportApps", "com.tencent.mm");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (str == null || str.isEmpty()) {
            return true;
        }
        for (String str2 : str.split(",")) {
            if (Utils.hasPackage(context, str2)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecPreferenceScreen) preferenceScreen.findPreference(KEY_HONGBAO_ASSISTANT);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (!isHongbaoAssistantEnabled(this.mContext) || isExternalServiceExist()) ? 3 : 0;
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
        if (preference.equals(this.mPreference)) {
            try {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.MAIN");
                intent.setClassName(
                        "com.samsung.hongbaoassistant",
                        "com.samsung.hongbaoassistant.settings.HongbaoAssistantActivity");
                this.mContext.startActivity(intent);
                return true;
            } catch (ActivityNotFoundException e) {
                Log.secD(TAG, "ActivityNotFoundException in HongbaoAssistant");
                e.printStackTrace();
            }
        }
        return super.handlePreferenceTreeClick(preference);
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
    public boolean isSliceable() {
        return isAvailable();
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

    public HongbaoAssistantPreferenceController(Context context, String str) {
        super(context, str);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
