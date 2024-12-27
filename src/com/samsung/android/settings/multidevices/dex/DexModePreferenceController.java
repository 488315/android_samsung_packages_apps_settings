package com.samsung.android.settings.multidevices.dex;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.hardware.display.SemWifiDisplayStatus;
import android.os.UserHandle;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.multidevices.SecMultiDevicesPreference;
import com.sec.ims.im.ImIntent;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DexModePreferenceController extends BasePreferenceController
        implements LifecycleObserver {
    private static final String KEY_DEX = "key_dex";
    private static final String TAG = "DexModePreferenceController";
    private SecMultiDevicesPreference mPreference;

    public DexModePreferenceController(Context context) {
        this(context, KEY_DEX);
    }

    private Intent getConnectivityActivityIntent() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setClassName(
                "com.sec.android.desktopmode.uiservice",
                "com.sec.android.desktopmode.uiservice.activity.connectivity.ConnectivityActivity");
        return intent;
    }

    public static boolean isSmartViewEnabled(Context context) {
        DisplayManager displayManager =
                (DisplayManager) context.getSystemService(DisplayManager.class);
        SemWifiDisplayStatus semGetWifiDisplayStatus = displayManager.semGetWifiDisplayStatus();
        return !(semGetWifiDisplayStatus.getActiveDisplayState() != 2
                        || semGetWifiDisplayStatus.getConnectedState() == 2
                        || semGetWifiDisplayStatus.getConnectedState() == 3
                        || Utils.isDesktopDualMode(context))
                || (displayManager.semGetActiveDlnaState() == 1
                        && !displayManager.semGetActiveDlnaDevice().isSwitchingDevice());
    }

    private boolean supportOnlyWirelessDex() {
        return (!Rune.SUPPORT_WIRELESS_DESKTOP_MODE
                        || Rune.SUPPORT_STANDALONE_DESKTOP_MODE
                        || Rune.SUPPORT_DUAL_DESKTOP_MODE)
                ? false
                : true;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = (SecMultiDevicesPreference) preferenceScreen.findPreference(KEY_DEX);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!Rune.supportDesktopMode()) {
            return 3;
        }
        if (!Utils.isTablet()
                || Rune.SUPPORT_STANDALONE_DESKTOP_MODE
                || Rune.SUPPORT_DUAL_DESKTOP_MODE
                || Rune.SUPPORT_WIRELESS_DESKTOP_MODE) {
            return UserHandle.myUserId() != 0 ? 4 : 0;
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
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_advanced_features;
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
        SecMultiDevicesPreference secMultiDevicesPreference = this.mPreference;
        if (secMultiDevicesPreference == null || !preference.equals(secMultiDevicesPreference)) {
            return false;
        }
        try {
            if (supportOnlyWirelessDex()) {
                this.mContext.startActivity(getConnectivityActivityIntent());
                return true;
            }
            Intent intent = new Intent();
            intent.setAction("android.intent.action.MAIN");
            intent.setClassName(
                    "com.sec.android.app.desktoplauncher",
                    "com.android.launcher3.settings.DesktopSettingsActivity");
            intent.putExtra(ImIntent.Extras.EXTRA_FROM, "connected_devices");
            this.mContext.startActivity(intent);
            LoggingHelper.insertEventLogging(4350, 7601);
            return true;
        } catch (ActivityNotFoundException e) {
            Log.d(TAG, "ActivityNotFoundException in DexMode");
            e.printStackTrace();
            return false;
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
    public boolean isSliceable() {
        return getAvailabilityStatus() != 3;
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

    public DexModePreferenceController(Context context, String str) {
        super(context, str);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
