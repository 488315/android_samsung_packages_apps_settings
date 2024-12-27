package com.samsung.android.settings.display.controller;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.kiosk.KioskMode;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EdgeScreenPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    public static final String EASY_MODE = "edgepanel_err_easy_mode_on";
    public static final String EDGE_PANEL_NOT_SUPPORT = "err_edge_not_supported_device";
    private static final String EDGE_SETTING =
            "com.samsung.app.cocktailbarservice.settings.SETTINGSMAIN";
    public static final String MINIMAL_BATTERY_USE_MODE =
            "edgepanel_err_minimal_battery_use_mode_on";
    private static final int MSG_LAUNCH_EDGE_SCREEN = 1;
    public static final String SUCCESS = "success";
    private static final String TAG = "EdgeScreenPreferenceController";
    public static final String WINNER_COVER_SCREEN = "edgepanel_err_winner_cover_screen";
    private ContentObserver mContentObserver;
    private Handler mHandler;
    private SecSwitchPreferenceScreen mPreference;
    private static final String EDGE_ENABLE = "edge_enable";
    private static final Uri EDGE_PANEL_URI = Settings.Secure.getUriFor(EDGE_ENABLE);
    private static String EDGE_PKG = "com.sec.android.app.launcher";
    private static String EDGE_SERVICE =
            "com.samsung.app.honeyspace.edge.edgepanel.app.CocktailBarService";

    public EdgeScreenPreferenceController(Context context, String str) {
        super(context, str);
        this.mContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.display.controller.EdgeScreenPreferenceController.2
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        EdgeScreenPreferenceController edgeScreenPreferenceController =
                                EdgeScreenPreferenceController.this;
                        edgeScreenPreferenceController.updateState(
                                edgeScreenPreferenceController.mPreference);
                    }
                };
        this.mHandler =
                new Handler(
                        Looper
                                .getMainLooper()) { // from class:
                                                    // com.samsung.android.settings.display.controller.EdgeScreenPreferenceController.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        EdgeScreenPreferenceController edgeScreenPreferenceController =
                                EdgeScreenPreferenceController.this;
                        if (((AbstractPreferenceController) edgeScreenPreferenceController).mContext
                                        == null
                                || message.what != 1) {
                            return;
                        }
                        edgeScreenPreferenceController.launchEdgeScreen(
                                ((Boolean) message.obj).booleanValue());
                    }
                };
    }

    private ContentResolver getContentResolver() {
        return this.mContext.getContentResolver();
    }

    private int getEdgeEnable() {
        return Settings.Secure.getInt(
                this.mContext.getContentResolver(), EDGE_ENABLE, !isEdgePanelDefaultOff() ? 1 : 0);
    }

    private boolean isCoverScreen() {
        return this.mContext.getResources().getConfiguration().semDisplayDeviceType == 5;
    }

    private boolean isEasyMode(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "easy_mode_switch", 1) == 0;
    }

    private boolean isEdgePanelDefaultOff() {
        String string =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_EDGE");
        return string != null && string.contains("off");
    }

    private boolean isMinimalBatteryUseMode(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "minimal_battery_use", 0) == 1;
    }

    public static boolean isNewDex(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "new_dex", 0) == 1;
    }

    private boolean isSamsungDexMode(Context context) {
        return Rune.isSamsungDexMode(context) && !isNewDex(context);
    }

    private boolean isWinnerCover() {
        return isWinnerProduct() && isCoverScreen();
    }

    private boolean isWinnerProduct() {
        String string =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_EDGE");
        return string != null && string.contains("WINNER");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchEdgeScreen(boolean z) {
        Intent intent = new Intent();
        intent.setClassName(EDGE_PKG, EDGE_SERVICE);
        if (z) {
            this.mContext.startService(intent);
        }
    }

    private void setEdgeEnable(boolean z) {
        Settings.Secure.putInt(this.mContext.getContentResolver(), EDGE_ENABLE, z ? 1 : 0);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecSwitchPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        registerStateCode();
        if (SemFloatingFeature.getInstance()
                .getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_EDGE")
                .contains("hideinsettings")) {
            return 3;
        }
        if ((Rune.SUPPORT_EDGE_MUM || UserHandle.myUserId() == 0) && !Rune.isMaintenanceMode()) {
            return (isEasyMode(this.mContext)
                            || isMinimalBatteryUseMode(this.mContext)
                            || isSamsungDexMode(this.mContext)
                            || Utils.isDesktopStandaloneMode(this.mContext)
                            || isWinnerCover())
                    ? 5
                    : 0;
        }
        return 3;
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
    public Intent getLaunchIntent() {
        Intent intent = new Intent(EDGE_SETTING);
        intent.setPackage(EDGE_PKG);
        intent.putExtra(
                "android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_HIGHLIGHT_MENU_KEY",
                this.mContext.getString(R.string.menu_key_display));
        intent.addFlags(268468224);
        return intent;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!preference.getKey().equals(getPreferenceKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        try {
            Intent intent = new Intent(EDGE_SETTING);
            intent.setPackage(EDGE_PKG);
            ((Activity) this.mContext).startActivityForResult(intent, -1);
            return true;
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "Activity Not Found " + e.getMessage());
            return true;
        }
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
        return getEdgeEnable() == 1;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        getContentResolver().registerContentObserver(EDGE_PANEL_URI, false, this.mContentObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        getContentResolver().unregisterContentObserver(this.mContentObserver);
    }

    public void registerStateCode() {
        if (isEasyMode(this.mContext)) {
            setStatusCode(EASY_MODE);
            return;
        }
        if ((!Rune.SUPPORT_EDGE_MUM && UserHandle.myUserId() != 0) || Rune.isMaintenanceMode()) {
            setStatusCode(EDGE_PANEL_NOT_SUPPORT);
        } else if (isMinimalBatteryUseMode(this.mContext)) {
            setStatusCode(MINIMAL_BATTERY_USE_MODE);
        } else if (isWinnerCover()) {
            setStatusCode(WINNER_COVER_SCREEN);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        setEdgeEnable(z);
        this.mHandler.removeMessages(1);
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(1, Boolean.valueOf(z)), 400L);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        EnterpriseDeviceManager enterpriseDeviceManager;
        if (isEasyMode(this.mContext)
                || isMinimalBatteryUseMode(this.mContext)
                || isSamsungDexMode(this.mContext)
                || Utils.isDesktopStandaloneMode(this.mContext)
                || isWinnerCover()) {
            preference.setEnabled(false);
            if (isEasyMode(this.mContext)) {
                preference.setSummary(
                        this.mContext
                                .getResources()
                                .getString(R.string.edge_panels_not_supported_in_easy_mode));
            } else if (isWinnerCover()) {
                preference.setSummary(
                        this.mContext
                                .getResources()
                                .getString(
                                        R.string.edge_panels_not_supported_in_winner_cover_screen));
            } else {
                preference.setSummary(ApnSettings.MVNO_NONE);
            }
        } else {
            preference.setEnabled(true);
            preference.setSummary(ApnSettings.MVNO_NONE);
        }
        Context context = this.mContext;
        String str = KnoxUtils.mDeviceType;
        KioskMode kioskMode =
                (context == null
                                || (enterpriseDeviceManager =
                                                EnterpriseDeviceManager.getInstance(
                                                        context.getApplicationContext()))
                                        == null)
                        ? null
                        : enterpriseDeviceManager.getKioskMode();
        if (kioskMode != null && (kioskMode.getBlockedEdgeScreen() & 31) > 0) {
            preference.setEnabled(false);
        }
        super.updateState(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
