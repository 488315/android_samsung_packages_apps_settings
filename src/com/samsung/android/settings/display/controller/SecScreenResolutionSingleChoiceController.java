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
import android.util.Log;
import android.view.WindowManagerGlobal;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SecSingleChoicePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.display.ScreenResolutionFragment;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecHorizontalRadioPreference;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecScreenResolutionSingleChoiceController extends SecSingleChoicePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private static final Uri DISPLAY_SIZE_FORCED = Settings.System.getUriFor("display_size_forced");
    private static final String KEY_SCREEN_RESOLUTION_SEEKBAR = "screen_resolution";
    private static final String TAG = "ScreenResolutionPreferenceController";
    private ContentObserver mContentObserver;
    private ArrayList<String> mEntries;
    private ArrayList<String> mEntryValues;
    private SecHorizontalRadioPreference mPreference;
    private ArrayList<String> mSubEntries;

    public SecScreenResolutionSingleChoiceController(Context context) {
        this(context, KEY_SCREEN_RESOLUTION_SEEKBAR);
    }

    private void buildEntries() {
        Point point = new Point();
        String string = this.mContext.getString(R.string.screen_resolution_hd);
        String string2 = this.mContext.getString(R.string.screen_resolution_fhd);
        String string3 = this.mContext.getString(R.string.screen_resolution_wqhd);
        try {
            WindowManagerGlobal.getWindowManagerService().getInitialDisplaySize(0, point);
            int i = point.x;
            int i2 = point.y;
            if (i2 / i > 1.7777778f) {
                string = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(string, "+");
                string2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(string2, "+");
                string3 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(string3, "+");
            }
            this.mEntries.add(string);
            this.mEntries.add(string2);
            this.mEntries.add(string3);
            boolean z = this.mContext.getResources().getConfiguration().getLayoutDirection() == 1;
            boolean equals = String.valueOf(1234).equals(String.format("%d", 1234));
            String string4 = this.mContext.getString(R.string.screen_resolution_hd_px);
            String string5 = this.mContext.getString(R.string.screen_resolution_fhd_px);
            String string6 = this.mContext.getString(R.string.screen_resolution_wqhd_px);
            NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
            numberFormat.setGroupingUsed(false);
            if (i == 1440) {
                int i3 = (!z || equals) ? i : i2;
                if (!z || equals) {
                    i = i2;
                }
                StringBuilder sb = new StringBuilder();
                double d = i;
                sb.append(numberFormat.format((int) (d * 0.5d)));
                sb.append(" x ");
                double d2 = i3;
                sb.append(numberFormat.format((int) (0.5d * d2)));
                String sb2 = sb.toString();
                string5 =
                        numberFormat.format((int) (d * 0.75d))
                                + " x "
                                + numberFormat.format((int) (d2 * 0.75d));
                string6 = numberFormat.format(i) + " x " + numberFormat.format(i3);
                string4 = sb2;
            }
            this.mSubEntries.add(string4);
            this.mSubEntries.add(string5);
            this.mSubEntries.add(string6);
        } catch (RemoteException unused) {
            Log.secD(TAG, "getInitialDisplaySize() exception!!!");
        }
    }

    private void buildEntryValues() {
        this.mEntryValues.add(Integer.toString(0));
        this.mEntryValues.add(Integer.toString(1));
        this.mEntryValues.add(Integer.toString(2));
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecHorizontalRadioPreference)
                        preferenceScreen.findPreference(KEY_SCREEN_RESOLUTION_SEEKBAR);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return (Utils.isDesktopModeEnabled(this.mContext) || Utils.isDesktopDualMode(this.mContext))
                ? 5
                : 0;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<String> getEntries() {
        return this.mEntries;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<String> getEntryValues() {
        return this.mEntryValues;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<Integer> getImageEntries() {
        return null;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public Intent getLaunchIntent() {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = ScreenResolutionFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        subSettingLauncher.setTitleRes(R.string.screen_resolution_title, null);
        launchRequest.mSourceMetricsCategory = 100;
        subSettingLauncher.addFlags(268468224);
        return subSettingLauncher.toIntent();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_SCREEN_RESOLUTION_SEEKBAR;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public String getSelectedValue() {
        return Integer.toString(SecDisplayUtils.getScreenResolution(this.mContext));
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<String> getSubEntries() {
        return this.mSubEntries;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        int screenResolution = SecDisplayUtils.getScreenResolution(this.mContext);
        String str = (String) obj;
        int parseInt = Integer.parseInt(str);
        if (parseInt == 0) {
            LoggingHelper.insertEventLogging(7440, 7443, 1L);
        } else if (parseInt == 1) {
            LoggingHelper.insertEventLogging(7440, 7443, 2L);
        } else if (parseInt == 2) {
            LoggingHelper.insertEventLogging(7440, 7443, 3L);
        }
        int intRefreshRate = SecDisplayUtils.getIntRefreshRate(this.mContext, 999);
        boolean z = screenResolution != Integer.parseInt(str);
        if (z) {
            if (screenResolution == 2
                    && !SecDisplayUtils.canSetHighRefreshRateAboveWQHD(this.mContext)
                    && (intRefreshRate == 2 || intRefreshRate == 1)) {
                this.mContext.startActivity(
                        new Intent()
                                .setAction("com.samsung.settings.display.ScreenResolutionDialog"));
            } else {
                super.onPreferenceChange(preference, obj);
                LoggingHelper.insertEventLogging(7440, 7450);
            }
        }
        updateState(preference);
        return z;
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

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public boolean setSelectedValue(String str) {
        SecDisplayUtils.setSelectedScreenResolution(this.mContext, Integer.parseInt(str));
        return getSelectedValue().equals(str);
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlResult setValue(ControlValue controlValue) {
        int intRefreshRate = SecDisplayUtils.getIntRefreshRate(this.mContext, 999);
        if (SecDisplayUtils.canSetHighRefreshRateAboveWQHD(this.mContext)
                || !(intRefreshRate == 2 || intRefreshRate == 1)) {
            return super.setValue(controlValue);
        }
        ControlResult.Builder builder = new ControlResult.Builder(getPreferenceKey());
        builder.mResultCode = ControlResult.ResultCode.SUCCESS;
        return builder.build();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        ((SecHorizontalRadioPreference) preference).mIsColorFilterEnabled = true;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public SecScreenResolutionSingleChoiceController(Context context, String str) {
        super(context, str);
        this.mEntries = new ArrayList<>();
        this.mEntryValues = new ArrayList<>();
        this.mSubEntries = new ArrayList<>();
        this.mContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.display.controller.SecScreenResolutionSingleChoiceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        SecScreenResolutionSingleChoiceController
                                secScreenResolutionSingleChoiceController =
                                        SecScreenResolutionSingleChoiceController.this;
                        secScreenResolutionSingleChoiceController.updateState(
                                secScreenResolutionSingleChoiceController.mPreference);
                    }
                };
        buildEntries();
        buildEntryValues();
    }
}
