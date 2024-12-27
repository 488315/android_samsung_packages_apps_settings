package com.android.settings.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.view.accessibility.AccessibilityManager;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.accessibility.AccessibilityUtils;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AccessibilitySlicePreferenceController extends TogglePreferenceController {
    private static final String EMPTY_STRING = "";
    private final ComponentName mComponentName;

    public AccessibilitySlicePreferenceController(Context context, String str) {
        super(context, str);
        ComponentName unflattenFromString = ComponentName.unflattenFromString(getPreferenceKey());
        this.mComponentName = unflattenFromString;
        if (unflattenFromString == null) {
            throw new IllegalArgumentException(
                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                            "Illegal Component Name from: ", str));
        }
    }

    private AccessibilityServiceInfo getAccessibilityServiceInfo() {
        for (AccessibilityServiceInfo accessibilityServiceInfo :
                ((AccessibilityManager) this.mContext.getSystemService(AccessibilityManager.class))
                        .getInstalledAccessibilityServiceList()) {
            if (this.mComponentName.equals(accessibilityServiceInfo.getComponentName())) {
                return accessibilityServiceInfo;
            }
        }
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return getAccessibilityServiceInfo() == null ? 3 : 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_accessibility;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        AccessibilityServiceInfo accessibilityServiceInfo = getAccessibilityServiceInfo();
        return accessibilityServiceInfo == null
                ? ""
                : AccessibilitySettings.getServiceSummary(
                        this.mContext, accessibilityServiceInfo, getThreadEnabled());
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        if (Settings.Secure.getInt(this.mContext.getContentResolver(), "accessibility_enabled", 0)
                == 1) {
            return AccessibilityUtils.getEnabledServicesFromSettings(this.mContext)
                    .contains(this.mComponentName);
        }
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isPublicSlice() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (getAccessibilityServiceInfo() == null) {
            return false;
        }
        AccessibilityUtils.setAccessibilityServiceState(this.mContext, this.mComponentName, z);
        return z == getThreadEnabled();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
