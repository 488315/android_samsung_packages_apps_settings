package com.android.settings.display;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import androidx.preference.Preference;

import com.android.internal.foldables.FoldGracePeriodProvider;
import com.android.internal.foldables.FoldLockSettingAvailabilityProvider;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FoldLockBehaviorPreferenceController extends BasePreferenceController {
    private static final Map<String, String> KEY_TO_TEXT = new HashMap();
    private final FoldLockSettingAvailabilityProvider mFoldLockSettingAvailabilityProvider;

    public FoldLockBehaviorPreferenceController(Context context, String str) {
        this(context, str, new FoldLockSettingAvailabilityProvider(context.getResources()));
    }

    private String getFoldSettingValue() {
        String stringForUser =
                Settings.System.getStringForUser(
                        this.mContext.getContentResolver(), "fold_lock_behavior_setting", -2);
        return (stringForUser == null
                        || !FoldLockBehaviorSettings.SETTING_VALUES.contains(stringForUser))
                ? "selective_stay_awake_key"
                : stringForUser;
    }

    private String resourceToString(int i) {
        return this.mContext.getText(i).toString();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mFoldLockSettingAvailabilityProvider.isFoldLockBehaviorAvailable() ? 0 : 3;
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
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_display;
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
        preference.setSummary(KEY_TO_TEXT.get(getFoldSettingValue()));
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public FoldLockBehaviorPreferenceController(
            Context context,
            String str,
            FoldLockSettingAvailabilityProvider foldLockSettingAvailabilityProvider) {
        super(context, str);
        this.mFoldLockSettingAvailabilityProvider = foldLockSettingAvailabilityProvider;
        Map<String, String> map = KEY_TO_TEXT;
        map.put("stay_awake_on_fold_key", resourceToString(R.string.stay_awake_on_fold_title));
        if (new FoldGracePeriodProvider().isEnabled()) {
            map.put(
                    "selective_stay_awake_key",
                    resourceToString(R.string.stay_awake_on_lockscreen_title));
        } else {
            map.put(
                    "selective_stay_awake_key",
                    resourceToString(R.string.selective_stay_awake_title));
        }
        map.put("sleep_on_fold_key", resourceToString(R.string.sleep_on_fold_title));
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
