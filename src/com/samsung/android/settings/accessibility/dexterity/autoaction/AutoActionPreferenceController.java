package com.samsung.android.settings.accessibility.dexterity.autoaction;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AutoActionPreferenceController extends BasePreferenceController
        implements AccessibilityObservableController,
                AccessibilityUsingFunction,
                A11yStatusLoggingProvider {
    public AutoActionPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getActionButtonDescription(Context context) {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (SecAccessibilityUtils.isDesktopDualModeMonitorDisplay(this.mContext)
                        && Rune.isSamsungDexOnPCMode(this.mContext))
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

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public Object getDefaultValue(Context context) {
        return 0;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public String getFragmentClassName() {
        return "com.samsung.android.settings.accessibility.dexterity.InteractionAndDexterityFragment";
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public String getFunctionName() {
        return "AutoActionAfterPointerStops";
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

    /* JADX WARN: Removed duplicated region for block: B:11:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0057  */
    @Override // com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.Map<java.lang.String, java.lang.String> getStatusLoggingData(
            android.content.Context r21) {
        /*
            r20 = this;
            r0 = r20
            android.content.Context r1 = r0.mContext
            android.content.ContentResolver r1 = r1.getContentResolver()
            java.lang.String r2 = "accessibility_auto_action_type"
            r3 = 0
            int r1 = android.provider.Settings.Secure.getInt(r1, r2, r3)
            android.content.Context r2 = r0.mContext
            android.content.ContentResolver r2 = r2.getContentResolver()
            java.lang.String r4 = "accessibility_auto_action_delay"
            r5 = 600(0x258, float:8.41E-43)
            int r2 = android.provider.Settings.Secure.getInt(r2, r4, r5)
            android.content.Context r4 = r0.mContext
            android.content.ContentResolver r4 = r4.getContentResolver()
            java.lang.String r5 = "accessibility_pause_auto_click_with"
            int r4 = android.provider.Settings.Secure.getInt(r4, r5, r3)
            r5 = 3
            r6 = 2
            r7 = 1
            if (r1 == 0) goto L43
            if (r4 != 0) goto L34
            java.lang.String r1 = "TL"
        L32:
            r9 = r1
            goto L46
        L34:
            if (r4 != r7) goto L39
            java.lang.String r1 = "TR"
            goto L32
        L39:
            if (r4 != r6) goto L3e
            java.lang.String r1 = "BL"
            goto L32
        L3e:
            if (r4 != r5) goto L43
            java.lang.String r1 = "BR"
            goto L32
        L43:
            java.lang.String r1 = "Off"
            goto L32
        L46:
            android.content.Context r0 = r0.mContext
            java.util.HashMap r1 = com.samsung.android.settings.accessibility.dexterity.autoaction.AutoActionUtils.mActionMap
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r1 = "accessibility_corner_actions"
            java.lang.String r0 = android.provider.Settings.Secure.getString(r0, r1)
            if (r0 == 0) goto L57
            goto L5a
        L57:
            java.lang.String r0 = "none:none:none:none"
        L5a:
            java.lang.String r1 = ":"
            java.lang.String[] r0 = r0.split(r1)
            java.util.ArrayList r1 = new java.util.ArrayList
            r3 = r0[r3]
            java.lang.String r4 = ","
            java.lang.String[] r3 = r3.split(r4)
            java.util.List r3 = java.util.Arrays.asList(r3)
            r1.<init>(r3)
            java.util.ArrayList r3 = new java.util.ArrayList
            r7 = r0[r7]
            java.lang.String[] r7 = r7.split(r4)
            java.util.List r7 = java.util.Arrays.asList(r7)
            r3.<init>(r7)
            java.util.ArrayList r7 = new java.util.ArrayList
            r6 = r0[r6]
            java.lang.String[] r6 = r6.split(r4)
            java.util.List r6 = java.util.Arrays.asList(r6)
            r7.<init>(r6)
            java.util.ArrayList r6 = new java.util.ArrayList
            r0 = r0[r5]
            java.lang.String[] r0 = r0.split(r4)
            java.util.List r0 = java.util.Arrays.asList(r0)
            r6.<init>(r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r2)
            java.lang.String r2 = "ms"
            r0.append(r2)
            java.lang.String r11 = r0.toString()
            com.android.settings.overlay.FeatureFactoryImpl r0 = com.android.settings.overlay.FeatureFactoryImpl._factory
            if (r0 == 0) goto Lf4
            com.android.settingslib.core.instrumentation.MetricsFeatureProvider r0 = r0.getA11ySettingsMetricsFeatureProvider()
            int r1 = r1.size()
            java.lang.String r13 = java.lang.String.valueOf(r1)
            int r1 = r3.size()
            java.lang.String r15 = java.lang.String.valueOf(r1)
            int r1 = r7.size()
            java.lang.String r17 = java.lang.String.valueOf(r1)
            int r1 = r6.size()
            java.lang.String r19 = java.lang.String.valueOf(r1)
            java.lang.String r16 = "BL"
            java.lang.String r18 = "BR"
            java.lang.String r8 = "A_click"
            java.lang.String r10 = "Delay"
            java.lang.String r12 = "TL"
            java.lang.String r14 = "TR"
            java.util.Map r1 = java.util.Map.of(r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19)
            r2 = 5008(0x1390, float:7.018E-42)
            java.lang.String r3 = "A11YSE5008"
            r0.actionBackground(r2, r3, r1)
            java.util.Map r0 = java.util.Map.of()
            return r0
        Lf4:
            java.lang.UnsupportedOperationException r0 = new java.lang.UnsupportedOperationException
            java.lang.String r1 = "No feature factory configured"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.accessibility.dexterity.autoaction.AutoActionPreferenceController.getStatusLoggingData(android.content.Context):java.util.Map");
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController
    public List<Uri> getUriList() {
        return List.of(Settings.Secure.getUriFor("accessibility_auto_action_type"));
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public /* bridge */ /* synthetic */ String getUsingFunctionHighlightKey() {
        return null;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public CharSequence getUsingFunctionTitle(Context context) {
        return context.getText(R.string.accessibility_auto_action_preference_title);
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityUsingFunction
    public int getUsingFunctionType() {
        return 101;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        int i =
                Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "accessibility_auto_action_type", 0);
        ControlValue.Builder builder =
                new ControlValue.Builder(getPreferenceKey(), getControlType());
        builder.setValue(Integer.valueOf(i));
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
        AutoActionUtils.autoActionSetAsDefault(this.mContext);
        ControlResult.Builder builder = new ControlResult.Builder(getPreferenceKey());
        builder.mResultCode = ControlResult.ResultCode.SUCCESS;
        return builder.build();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
