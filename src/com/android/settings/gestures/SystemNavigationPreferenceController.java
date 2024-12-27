package com.android.settings.gestures;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SystemNavigationPreferenceController extends BasePreferenceController {
    private static final String ACTION_QUICKSTEP = "android.intent.action.QUICKSTEP_SERVICE";
    static final String PREF_KEY_SYSTEM_NAVIGATION = "gesture_system_navigation";

    public SystemNavigationPreferenceController(Context context, String str) {
        super(context, str);
    }

    public static boolean is2ButtonNavigationEnabled(Context context) {
        return 1 == context.getResources().getInteger(R.integer.config_pinnerWebviewPinBytes);
    }

    public static boolean isGestureAvailable(Context context) {
        ComponentName unflattenFromString;
        if (context.getResources().getBoolean(R.bool.config_useAssistantVolume)
                && (unflattenFromString =
                                ComponentName.unflattenFromString(
                                        context.getString(R.string.ext_media_status_mounted)))
                        != null) {
            return context.getPackageManager()
                            .resolveService(
                                    new Intent(ACTION_QUICKSTEP)
                                            .setPackage(unflattenFromString.getPackageName()),
                                    1048576)
                    != null;
        }
        return false;
    }

    public static boolean isGestureNavigationEnabled(Context context) {
        return 2 == context.getResources().getInteger(R.integer.config_pinnerWebviewPinBytes);
    }

    public static boolean isOverlayPackageAvailable(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0) != null;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return isGestureAvailable(this.mContext) ? 0 : 3;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return isGestureNavigationEnabled(this.mContext)
                ? this.mContext.getText(com.android.settings.R.string.edge_to_edge_navigation_title)
                : is2ButtonNavigationEnabled(this.mContext)
                        ? this.mContext.getText(
                                com.android.settings.R.string.swipe_up_to_switch_apps_title)
                        : this.mContext.getText(
                                com.android.settings.R.string.legacy_navigation_title);
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

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
