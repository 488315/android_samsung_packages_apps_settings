package com.samsung.android.settings.display.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.core.SecCustomPreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.navigationbar.NavigationBarOverlayInteractor;
import com.samsung.android.settings.navigationbar.NavigationBarSettings;
import com.samsung.android.settings.navigationbar.NavigationBarSettingsUtil;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NavigationBarPreferenceController extends SecCustomPreferenceController
        implements PreferenceControllerMixin {
    private static final String KEY = "navigation_Bar";
    private static final String KEY_BLOCK_GESTURES_WITH_SPEN_GTS = "block_gestures_with_spen";
    private static final String KEY_BUTTON_ORDER_GTS = "button_order";
    private static final String KEY_BUTTON_POSITION_GTS = "button_position";
    private static final String KEY_GESTURE_DETAILED_GTS = "gesture_detailed";
    private static final String KEY_GESTURE_HINT_GTS = "gesture_hint";
    private static final String KEY_SHOW_BUTTON_TO_HIDE_KEYBOARD_GTS =
            "show_button_to_hide_keyboard";
    private static final String KEY_SWITCH_APPS_WHEN_HINT_HIDDEN_GTS =
            "switch_apps_when_hint_hidden";
    private static final int OFF = 0;
    private static final int ON = 1;
    private static final String TAG = "NavigationBarPreferenceController";
    private int mBtnOrder;
    private int mBtnPosition;
    private int mDetailType;
    private boolean mIsBlockGesturesOn;
    private boolean mIsGestureHintOn;
    private boolean mIsHideKeyboardButtonOn;
    private boolean mIsSwitchAppsOn;
    private int mMode;
    private SecPreferenceScreen mPreference;

    public NavigationBarPreferenceController(Context context, Lifecycle lifecycle) {
        super(context, KEY);
    }

    private String getNavigationBarGTSSummary() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.mContext.getString(R.string.navigationbar_gesture_category_name));
        stringBuffer.append(" - ");
        stringBuffer.append(
                this.mMode == 0
                        ? this.mContext.getString(R.string.navigationbar_buttons)
                        : this.mContext.getString(R.string.navigationbar_full_screen_gestures));
        if (this.mMode != 0) {
            stringBuffer.append("(");
            stringBuffer.append(
                    this.mDetailType == 0
                            ? this.mContext.getString(R.string.navigationbar_swipe_from_bottom)
                            : this.mContext.getString(
                                    R.string.navigationbar_swipe_from_side_and_bottom));
            stringBuffer.append(")");
        }
        return stringBuffer.toString();
    }

    private void getNavigationSettingsValueForGTS() {
        this.mMode = NavigationBarSettingsUtil.getNavBarMode(this.mContext);
        this.mDetailType =
                Settings.Global.getInt(
                        this.mContext.getContentResolver(),
                        "navigation_bar_gesture_detail_type",
                        1);
        this.mBtnPosition =
                Settings.Global.getInt(
                        this.mContext.getContentResolver(), "navigationbar_key_position", 2);
        this.mBtnOrder =
                Settings.Global.getInt(
                        this.mContext.getContentResolver(), "navigationbar_key_order", 0);
        this.mIsGestureHintOn = NavigationBarSettingsUtil.isGestureHintOn(this.mContext);
        this.mIsBlockGesturesOn =
                Settings.Global.getInt(
                                this.mContext.getContentResolver(),
                                "navigation_bar_block_gestures_with_spen",
                                0)
                        != 0;
        this.mIsHideKeyboardButtonOn =
                Settings.Global.getInt(
                                this.mContext.getContentResolver(),
                                "navigation_bar_button_to_hide_keyboard",
                                1)
                        != 0;
        this.mIsSwitchAppsOn =
                Settings.Global.getInt(
                                this.mContext.getContentResolver(),
                                "navigationbar_switch_apps_when_hint_hidden",
                                0)
                        != 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void setNavigationSettingsValueForGTS(ControlValue controlValue) {
        int i = this.mMode;
        int i2 = this.mDetailType;
        this.mMode = Integer.parseInt(controlValue.getValue());
        int attributeInt = controlValue.getAttributeInt(KEY_GESTURE_DETAILED_GTS);
        this.mDetailType = attributeInt;
        byte b = (i == this.mMode && i2 == attributeInt) ? false : true;
        this.mBtnPosition = controlValue.getAttributeInt(KEY_BUTTON_POSITION_GTS);
        this.mBtnOrder = controlValue.getAttributeInt(KEY_BUTTON_ORDER_GTS);
        this.mIsGestureHintOn = controlValue.getAttributeInt(KEY_GESTURE_HINT_GTS) == 1;
        this.mIsBlockGesturesOn =
                controlValue.getAttributeInt(KEY_BLOCK_GESTURES_WITH_SPEN_GTS) == 1;
        this.mIsHideKeyboardButtonOn =
                controlValue.getAttributeInt(KEY_SHOW_BUTTON_TO_HIDE_KEYBOARD_GTS) == 1;
        this.mIsSwitchAppsOn =
                controlValue.getAttributeInt(KEY_SWITCH_APPS_WHEN_HINT_HIDDEN_GTS) == 1;
        Settings.Global.putInt(
                this.mContext.getContentResolver(),
                "navigation_bar_gesture_while_hidden",
                this.mMode);
        Settings.Global.putInt(
                this.mContext.getContentResolver(),
                "navigation_bar_gesture_detail_type",
                this.mDetailType);
        Settings.Global.putInt(
                this.mContext.getContentResolver(),
                "navigationbar_key_position",
                NavigationBarSettingsUtil.isTaskBarEnabled(this.mContext)
                        ? updateButtonPositionNumberForTaskBar(this.mBtnPosition)
                        : this.mBtnPosition);
        Settings.Global.putInt(
                this.mContext.getContentResolver(), "navigationbar_key_order", this.mBtnOrder);
        Settings.Global.putInt(
                this.mContext.getContentResolver(),
                "navigation_bar_gesture_hint",
                this.mIsGestureHintOn ? 1 : 0);
        Settings.Global.putInt(
                this.mContext.getContentResolver(),
                "navigation_bar_block_gestures_with_spen",
                this.mIsBlockGesturesOn ? 1 : 0);
        Settings.Global.putInt(
                this.mContext.getContentResolver(),
                "navigation_bar_button_to_hide_keyboard",
                this.mIsHideKeyboardButtonOn ? 1 : 0);
        Settings.Global.putInt(
                this.mContext.getContentResolver(),
                "navigationbar_switch_apps_when_hint_hidden",
                this.mIsSwitchAppsOn ? 1 : 0);
        if (b == true) {
            new NavigationBarOverlayInteractor(this.mContext).setInteractionMode();
        }
    }

    private int updateButtonPositionNumberForTaskBar(int i) {
        if (i != 0) {
            return 2;
        }
        return i;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreference = (SecPreferenceScreen) preferenceScreen.findPreference(KEY);
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!Rune.supportNavigationBar()) {
            return 3;
        }
        SecPreferenceScreen secPreferenceScreen = this.mPreference;
        if (secPreferenceScreen == null) {
            return 0;
        }
        secPreferenceScreen.setEnabled(Rune.isNavigationBarEnabled(this.mContext));
        if (!Rune.supportNavigationBarForHardKey()) {
            return 0;
        }
        this.mPreference.setTitle(R.string.navigationbar_gesture_category_name);
        this.mPreference.setSummary((CharSequence) null);
        return 0;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public Intent getLaunchIntent() {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = NavigationBarSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        subSettingLauncher.setTitleRes(R.string.navigationbar_title, null);
        launchRequest.mSourceMetricsCategory = 100;
        subSettingLauncher.addFlags(268468224);
        return subSettingLauncher.toIntent();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        if (!Rune.supportNavigationBar()) {
            return null;
        }
        getNavigationSettingsValueForGTS();
        ControlValue.Builder builder =
                new ControlValue.Builder(getPreferenceKey(), getControlType());
        builder.addAttributeInt(this.mDetailType, KEY_GESTURE_DETAILED_GTS);
        builder.addAttributeInt(this.mBtnPosition, KEY_BUTTON_POSITION_GTS);
        builder.addAttributeInt(this.mBtnOrder, KEY_BUTTON_ORDER_GTS);
        builder.addAttributeInt(this.mIsGestureHintOn ? 1 : 0, KEY_GESTURE_HINT_GTS);
        builder.addAttributeInt(this.mIsBlockGesturesOn ? 1 : 0, KEY_BLOCK_GESTURES_WITH_SPEN_GTS);
        builder.addAttributeInt(
                this.mIsHideKeyboardButtonOn ? 1 : 0, KEY_SHOW_BUTTON_TO_HIDE_KEYBOARD_GTS);
        builder.addAttributeInt(this.mIsSwitchAppsOn ? 1 : 0, KEY_SWITCH_APPS_WHEN_HINT_HIDDEN_GTS);
        builder.mAvailabilityStatus = getAvailabilityStatus();
        Boolean bool = Boolean.TRUE;
        builder.mForceChange = bool;
        builder.setControlId(getControlId());
        builder.mIsDefault = bool;
        builder.mStatusCode = getStatusCode();
        builder.setValue(Integer.valueOf(this.mMode));
        builder.mSummary = getNavigationBarGTSSummary();
        return builder.build();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlResult setValue(ControlValue controlValue) {
        ControlResult.Builder builder = new ControlResult.Builder(getPreferenceKey());
        if (Rune.supportNavigationBar()) {
            setNavigationSettingsValueForGTS(controlValue);
            builder.mResultCode = ControlResult.ResultCode.SUCCESS;
        } else {
            builder.mResultCode = ControlResult.ResultCode.FAIL;
        }
        return builder.build();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public NavigationBarPreferenceController(Context context) {
        super(context, KEY);
    }
}
