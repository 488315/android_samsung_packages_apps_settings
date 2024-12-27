package com.samsung.android.settings.accessibility.dexterity.controllers;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider;
import com.samsung.android.settings.accessibility.dexterity.TapDurationUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TapDurationPreferenceController extends BasePreferenceController
        implements AccessibilityObservableController,
                AccessibilityUsingFunction,
                A11yStatusLoggingProvider {
    public TapDurationPreferenceController(Context context, String str) {
        super(context, str);
    }

    private boolean isTapDurationEnabled() {
        return Settings.Secure.getInt(this.mContext.getContentResolver(), "tap_duration_enabled", 0)
                == 1;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getActionButtonDescription(Context context) {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (ActivityManager.semGetCurrentUser() != 0) {
            return 4;
        }
        if (!Rune.isSamsungDexOnPCMode(this.mContext)) {
            Context context = this.mContext;
            HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
            if (!Utils.isTalkBackEnabled(context)) {
                return 0;
            }
        }
        return 5;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public Object getDefaultValue(Context context) {
        return Boolean.FALSE;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public String getFragmentClassName() {
        return "com.samsung.android.settings.accessibility.dexterity.InteractionAndDexterityFragment";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public String getFunctionName() {
        return "TapDuration";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public Drawable getIcon(Context context) {
        return context.getDrawable(R.drawable.accessibility_setting_list_ic_interaction);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ Intent getLearnMoreButtonIntent(Context context) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider
    public Map<String, String> getStatusLoggingData(Context context) {
        return Map.of(
                "A11YS5006",
                isTapDurationEnabled()
                        ? Float.toString(TapDurationUtils.getTapDurationValue(this.mContext))
                        : "Off");
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        float tapDurationValue = TapDurationUtils.getTapDurationValue(this.mContext);
        return isTapDurationEnabled()
                ? Float.compare(tapDurationValue, 1.0f) == 0
                        ? this.mContext.getString(R.string.second)
                        : this.mContext.getString(
                                R.string.seconds,
                                String.format(
                                        Locale.getDefault(),
                                        "%.1f",
                                        Float.valueOf(tapDurationValue)))
                : this.mContext.getText(R.string.tap_duration_off);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController
    public List<Uri> getUriList() {
        return List.of(
                Settings.Secure.getUriFor("tap_duration_enabled"),
                Settings.Secure.getUriFor("enabled_accessibility_services"));
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getUsingFunctionHighlightKey() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public CharSequence getUsingFunctionTitle(Context context) {
        return context.getText(R.string.tap_duration_title);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public int getUsingFunctionType() {
        return 1;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        ControlValue.Builder builder =
                new ControlValue.Builder(getPreferenceKey(), getControlType());
        builder.setValue(Boolean.valueOf(isTapDurationEnabled()));
        builder.mAvailabilityStatus = getAvailabilityStatusForControl();
        builder.mStatusCode = getStatusCode();
        return builder.build();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
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
    public ControlResult setValue(ControlValue controlValue) {
        if (!"recommend".equals(controlValue.mControlId)) {
            return null;
        }
        Settings.Secure.putInt(this.mContext.getContentResolver(), "tap_duration_enabled", 0);
        ControlResult.Builder builder = new ControlResult.Builder(getPreferenceKey());
        builder.mResultCode = ControlResult.ResultCode.SUCCESS;
        return builder.build();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        boolean z;
        super.updateState(preference);
        if (preference != null) {
            preference.seslSetSummaryColor(
                    TapDurationUtils.isTapDurationEnabled(this.mContext)
                            ? this.mContext.getColorStateList(R.color.text_color_primary_dark)
                            : this.mContext.getColorStateList(R.color.text_color_secondary));
            if (!Rune.isSamsungDexOnPCMode(this.mContext)) {
                Context context = this.mContext;
                HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
                if (!Utils.isTalkBackEnabled(context)) {
                    z = true;
                    preference.setEnabled(z);
                }
            }
            z = false;
            preference.setEnabled(z);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
