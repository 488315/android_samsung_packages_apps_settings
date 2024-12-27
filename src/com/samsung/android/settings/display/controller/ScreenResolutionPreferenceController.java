package com.samsung.android.settings.display.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.Point;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.secutil.Log;
import android.view.WindowManagerGlobal;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.display.DisplayDisabledAppearancePreference;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ScreenResolutionPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private static final Uri DISPLAY_SIZE_FORCED = Settings.System.getUriFor("display_size_forced");
    private static final String DISPLAY_SIZE_SEPARATOR = ",";
    private static final String KEY_SCREEN_RESOLUTION = "screen_resolution";
    private static final String TAG = "ScreenResolutionPreferenceController";
    private ContentObserver mContentObserver;
    private DisplayDisabledAppearancePreference mPreference;

    public ScreenResolutionPreferenceController(Context context, String str) {
        super(context, str);
        this.mContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.display.controller.ScreenResolutionPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        ScreenResolutionPreferenceController screenResolutionPreferenceController =
                                ScreenResolutionPreferenceController.this;
                        screenResolutionPreferenceController.updateState(
                                screenResolutionPreferenceController.mPreference);
                    }
                };
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        DisplayDisabledAppearancePreference displayDisabledAppearancePreference =
                (DisplayDisabledAppearancePreference)
                        preferenceScreen.findPreference(KEY_SCREEN_RESOLUTION);
        this.mPreference = displayDisabledAppearancePreference;
        if (displayDisabledAppearancePreference != null) {
            displayDisabledAppearancePreference.getClass();
            SecPreferenceUtils.applySummaryColor(displayDisabledAppearancePreference, true);
        }
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return (Utils.isDesktopModeEnabled(this.mContext) || Utils.isDesktopDualMode(this.mContext))
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

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_SCREEN_RESOLUTION;
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
        int i;
        int i2;
        String[] split;
        String string =
                Settings.Global.getString(
                        this.mContext.getContentResolver(), "display_size_forced");
        if (TextUtils.isEmpty(string)
                || (split = string.split(DISPLAY_SIZE_SEPARATOR)) == null
                || split.length <= 1) {
            i = 0;
            i2 = 0;
        } else {
            i2 = Integer.parseInt(split[0]);
            i = Integer.parseInt(split[1]);
        }
        if (i2 == 0 || i == 0) {
            Point point = new Point();
            try {
                WindowManagerGlobal.getWindowManagerService().getInitialDisplaySize(0, point);
                i2 = point.x;
                i = point.y;
            } catch (RemoteException unused) {
                Log.secD(TAG, "getInitialDisplaySize() exception");
                return ApnSettings.MVNO_NONE;
            }
        }
        String string2 =
                i2 >= 1440
                        ? this.mContext.getString(R.string.screen_resolution_wqhd)
                        : (i2 <= 720 || i2 > 1080)
                                ? this.mContext.getString(R.string.screen_resolution_hd)
                                : this.mContext.getString(R.string.screen_resolution_fhd);
        boolean z = this.mContext.getResources().getConfiguration().getLayoutDirection() == 1;
        boolean equals = String.valueOf(1234).equals(String.format("%d", 1234));
        if (i / i2 > 1.7777778f) {
            string2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(string2, "+ ");
        }
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
        numberFormat.setGroupingUsed(false);
        if (!z || equals) {
            StringBuilder m =
                    PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(string2, "(");
            m.append(numberFormat.format(i));
            m.append(" x ");
            m.append(numberFormat.format(i2));
            m.append(")");
            return m.toString();
        }
        return "(" + numberFormat.format(i2) + " x " + numberFormat.format(i) + ") " + string2;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(DISPLAY_SIZE_FORCED, false, this.mContentObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void refreshSummary(Preference preference) {
        super.refreshSummary(preference);
        if (preference != null) {
            SecPreferenceUtils.applySummaryColor(
                    (DisplayDisabledAppearancePreference) preference, true);
        }
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
