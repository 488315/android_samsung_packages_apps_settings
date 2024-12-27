package com.samsung.android.settings.display.gtscontroller;

import android.annotation.Nullable;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SecSingleChoicePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.display.controller.SecDarkModePreferenceController;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DarkModePreferenceController extends SecSingleChoicePreferenceController {
    private static final String DARK_MODE_NO = "0";
    public static final int DARK_MODE_SCHEDULED_TYPE_AUTO = 1;
    public static final int DARK_MODE_SCHEDULED_TYPE_USER = 2;
    public static final int DARK_MODE_SCHEDULE_UNSUPPORTED = -1;
    private static final String DARK_MODE_YES = "1";
    private static final String KEY = "dark_mode";
    private static final String KEY_END_TIME = "end_time";
    private static final String KEY_SCHEDULED = "scheduled";
    private static final String KEY_SCHEDULE_TYPE = "schedule_type";
    private static final String KEY_START_TIME = "start_time";
    private static final String TAG = "DarkModePreferenceController";
    private final SecDarkModePreferenceController mDelegate;
    private final Handler mHandler;

    public DarkModePreferenceController(Context context) {
        super(context, KEY);
        BasePreferenceController createInstance;
        this.mHandler = new Handler(Looper.getMainLooper());
        String name = SecDarkModePreferenceController.class.getName();
        try {
            createInstance = BasePreferenceController.createInstance(context, name);
        } catch (IllegalStateException unused) {
            createInstance = BasePreferenceController.createInstance(context, name, KEY);
        }
        this.mDelegate = (SecDarkModePreferenceController) createInstance;
    }

    private int getDarkModeScheduledType() {
        if (isDarkModeScheduledTypeSupported()) {
            return Settings.System.getInt(
                    this.mContext.getContentResolver(), "display_night_theme_scheduled_type", 2);
        }
        return -1;
    }

    private LocalTime getTime(long j) {
        return LocalTime.of((int) (j / 60), (int) (j % 60));
    }

    private boolean isDarkModeScheduled() {
        return Settings.System.getInt(
                        this.mContext.getContentResolver(), "display_night_theme_scheduled", 0)
                == 1;
    }

    private boolean isDarkModeScheduledTypeSupported() {
        return Settings.System.getInt(
                        this.mContext.getContentResolver(), "display_night_theme_scheduled", 0)
                != 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mDelegate.getAvailabilityStatus();
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

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getControlType() {
        return 101;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<String> getEntries() {
        return this.mDelegate.getEntries();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<String> getEntryValues() {
        return this.mDelegate.getEntryValues();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    @Nullable
    public ArrayList<Integer> getImageEntries() {
        return this.mDelegate.getImageEntries();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public String getSelectedValue() {
        return this.mDelegate.getSelectedValue();
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
    @Nullable
    public ArrayList<String> getSubEntries() {
        return this.mDelegate.getSubEntries();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        ControlValue value = super.getValue();
        ControlValue.Builder builder = new ControlValue.Builder(value.mKey, value.mControlType);
        builder.mBundle = value.mBundle;
        builder.mAvailabilityStatus = getAvailabilityStatus();
        builder.mForceChange = Boolean.TRUE;
        builder.setControlId(value.mControlId);
        builder.mIsDefault = Boolean.valueOf(value.mIsDefault);
        builder.mStatusCode = value.mStatusCode;
        builder.setValue(value.getValue());
        int darkModeScheduledType = getDarkModeScheduledType();
        if (darkModeScheduledType != -1) {
            int i =
                    (int)
                            Settings.System.getLong(
                                    this.mContext.getContentResolver(),
                                    "display_night_theme_on_time",
                                    1140L);
            int i2 =
                    (int)
                            Settings.System.getLong(
                                    this.mContext.getContentResolver(),
                                    "display_night_theme_off_time",
                                    420L);
            int i3 =
                    Settings.System.getInt(
                            this.mContext.getContentResolver(), "display_night_theme_scheduled", 1);
            builder.addAttributeInt(darkModeScheduledType, KEY_SCHEDULE_TYPE);
            builder.addAttribute("start_time", ApnSettings.MVNO_NONE + i);
            builder.addAttribute("end_time", ApnSettings.MVNO_NONE + i2);
            builder.addAttributeInt(i3, KEY_SCHEDULED);
        }
        return builder.build();
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
    public Controllable$ControllableType needUserInteraction(Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public boolean setSelectedValue(String str) {
        return this.mDelegate.setSelectedValue(str);
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlResult setValue(ControlValue controlValue) {
        final UiModeManager uiModeManager =
                (UiModeManager) this.mContext.getSystemService(UiModeManager.class);
        final int i = "1".equals(controlValue.getValue()) ? 2 : 1;
        if (controlValue.mBundle.containsKey(KEY_SCHEDULE_TYPE)) {
            Log.d(TAG, "schedule type: " + controlValue.getAttributeInt(KEY_SCHEDULE_TYPE));
            int attributeInt = controlValue.getAttributeInt(KEY_SCHEDULED);
            Settings.System.putInt(
                    this.mContext.getContentResolver(),
                    "display_night_theme_scheduled",
                    attributeInt);
            if (controlValue.getAttributeInt(KEY_SCHEDULE_TYPE) == 1) {
                Settings.System.putInt(
                        this.mContext.getContentResolver(),
                        "display_night_theme_scheduled_type",
                        1);
                if (attributeInt != 0) {
                    uiModeManager.setNightMode(0);
                } else {
                    uiModeManager.setNightMode(i);
                }
            } else {
                if (controlValue.mBundle.containsKey("start_time")
                        && controlValue.mBundle.containsKey("end_time")) {
                    long parseLong = Long.parseLong(controlValue.getAttribute("start_time"));
                    Settings.System.putLong(
                            this.mContext.getContentResolver(),
                            "display_night_theme_on_time",
                            parseLong);
                    if (parseLong >= 0 && parseLong < 1440) {
                        uiModeManager.setCustomNightModeStart(getTime(parseLong));
                    }
                    long parseLong2 = Long.parseLong(controlValue.getAttribute("end_time"));
                    Settings.System.putLong(
                            this.mContext.getContentResolver(),
                            "display_night_theme_off_time",
                            parseLong2);
                    if (parseLong2 >= 0 && parseLong2 < 1440) {
                        uiModeManager.setCustomNightModeEnd(getTime(parseLong2));
                    }
                }
                Settings.System.putInt(
                        this.mContext.getContentResolver(),
                        "display_night_theme_scheduled_type",
                        2);
                if (attributeInt != 0) {
                    uiModeManager.setNightMode(3);
                } else {
                    uiModeManager.setNightMode(i);
                }
            }
        } else {
            if (i == 2) {
                Settings.System.putInt(
                        this.mContext.getContentResolver(), "display_night_theme", 1);
            } else {
                Settings.System.putInt(
                        this.mContext.getContentResolver(), "display_night_theme", 0);
            }
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.samsung.android.settings.display.gtscontroller.DarkModePreferenceController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            uiModeManager.setNightMode(i);
                        }
                    });
        }
        ControlResult.Builder builder = new ControlResult.Builder(getPreferenceKey());
        builder.mResultCode = ControlResult.ResultCode.SUCCESS;
        return builder.build();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
