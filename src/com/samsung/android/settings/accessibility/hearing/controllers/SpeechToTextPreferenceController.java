package com.samsung.android.settings.accessibility.hearing.controllers;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SpeechToTextPreferenceController extends BasePreferenceController {
    private static final String TAG = "STTPrefController";

    public SpeechToTextPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!Rune.isChinaModel()) {
            return 3;
        }
        HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
        Uri uri = AccessibilityConstant.URI_ACCESSIBILITY_PROVIDER;
        String string =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_VOICERECORDER_CONFIG_DEF_MODE");
        if (TextUtils.isEmpty(string)) {
            return 3;
        }
        for (String str : string.split(",")) {
            if (str.equalsIgnoreCase("voicememo")) {
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
        PackageInfo packageInfo;
        if (!getPreferenceKey().equals(preference.getKey())) {
            return false;
        }
        Context context = this.mContext;
        Uri uri = AccessibilityConstant.URI_ACCESSIBILITY_PROVIDER;
        String string =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_VOICERECORDER_CONFIG_PACKAGE_NAME");
        HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
        long j = -1;
        if (!TextUtils.isEmpty(string)) {
            try {
                packageInfo =
                        context.getPackageManager()
                                .getPackageInfo(
                                        string.toString(), PackageManager.PackageInfoFlags.of(0L));
            } catch (PackageManager.NameNotFoundException unused) {
                packageInfo = null;
            }
            if (packageInfo == null) {
                Log.e("A11yUtils", "Could not find package: %s" + ((Object) string));
            } else {
                j = packageInfo.getLongVersionCode();
            }
        }
        Log.d(TAG, "handlePreferenceTreeClick() - versionCode : " + j);
        if (j < 2021100025) {
            SecAccessibilityUtils.showVoiceRecorderDownloadDialog(this.mContext);
            return true;
        }
        Intent intent = new Intent("voicenote.intent.action.SPEECH_TO_TEXT");
        try {
            intent.addFlags(67108864);
            this.mContext.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            Log.w(TAG, "ActivityNotFoundException!", e);
            return true;
        }
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
