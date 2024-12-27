package com.samsung.android.settings.accessibility.advanced.flashnotification;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.accessibility.FlashNotificationsUtil;
import com.android.settings.accessibility.ScreenFlashNotificationColorDialogFragment;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider;
import com.samsung.android.settings.accessibility.base.widget.AccessibilitySelectorWithWidgetPreference;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ScreenFlashNotificationAllAppsPreferenceController extends BasePreferenceController
        implements View.OnClickListener, DefaultLifecycleObserver, A11yStatusLoggingProvider {
    private static final String TAG = "ScreenFlashNotificationAllAppsPreferenceController";
    private final Drawable extraButtonDrawable;
    private final ContentObserver mScreenFlashModeObserver;
    private Fragment parentFragment;
    private AccessibilitySelectorWithWidgetPreference radioPreference;

    public ScreenFlashNotificationAllAppsPreferenceController(Context context, String str) {
        super(context, str);
        this.mScreenFlashModeObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.accessibility.advanced.flashnotification.ScreenFlashNotificationAllAppsPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        ScreenFlashNotificationAllAppsPreferenceController.this.refresh();
                    }
                };
        this.extraButtonDrawable =
                AppCompatResources.getDrawable(context, R.drawable.screen_flash_color_chip)
                        .mutate();
    }

    private boolean isColorModeAllApps() {
        return Settings.System.getInt(
                        this.mContext.getContentResolver(),
                        "screen_flash_notification_color_mode",
                        0)
                == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$0(Integer num) {
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "screen_flash_notification_color_global",
                num.intValue());
        refresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStart$1(Uri uri) {
        this.mContext
                .getContentResolver()
                .registerContentObserver(uri, false, this.mScreenFlashModeObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refresh() {
        Log.d(TAG, "refresh");
        if (this.radioPreference != null) {
            if (getAvailabilityStatus() != 0) {
                this.radioPreference.setVisible(false);
                return;
            }
            this.radioPreference.setVisible(true);
            boolean isColorModeAllApps = isColorModeAllApps();
            int i =
                    Settings.System.getInt(
                            this.mContext.getContentResolver(),
                            "screen_flash_notification_color_global",
                            FlashNotificationsUtil.DEFAULT_SCREEN_FLASH_COLOR);
            AccessibilitySelectorWithWidgetPreference accessibilitySelectorWithWidgetPreference =
                    this.radioPreference;
            accessibilitySelectorWithWidgetPreference.isExtraWidgetEnabled = isColorModeAllApps;
            accessibilitySelectorWithWidgetPreference.notifyChanged();
            this.radioPreference.setChecked(isColorModeAllApps);
            this.extraButtonDrawable.setTintList(
                    FlashNotificationUtil.getColorChipStateList(this.mContext, i));
            AccessibilitySelectorWithWidgetPreference accessibilitySelectorWithWidgetPreference2 =
                    this.radioPreference;
            accessibilitySelectorWithWidgetPreference2.extraImageDrawable =
                    this.extraButtonDrawable;
            accessibilitySelectorWithWidgetPreference2.notifyChanged();
            String colorDescriptionText =
                    FlashNotificationsUtil.getColorDescriptionText(this.mContext, i);
            String string = this.mContext.getString(R.string.comma);
            String string2 =
                    this.mContext.getString(
                            R.string.screen_flash_notification_color_setting_icon_all_apps);
            AccessibilitySelectorWithWidgetPreference accessibilitySelectorWithWidgetPreference3 =
                    this.radioPreference;
            if (!colorDescriptionText.equals(ApnSettings.MVNO_NONE)) {
                string2 =
                        AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(
                                colorDescriptionText, string, string2);
            }
            accessibilitySelectorWithWidgetPreference3.extraWidgetContentDescription = string2;
            accessibilitySelectorWithWidgetPreference3.notifyChanged();
        }
    }

    private void setRadioPreference(Preference preference) {
        if (preference instanceof AccessibilitySelectorWithWidgetPreference) {
            AccessibilitySelectorWithWidgetPreference accessibilitySelectorWithWidgetPreference =
                    (AccessibilitySelectorWithWidgetPreference) preference;
            this.radioPreference = accessibilitySelectorWithWidgetPreference;
            accessibilitySelectorWithWidgetPreference.setExtraWidgetOnClickListener(this);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        setRadioPreference(preferenceScreen.findPreference(getPreferenceKey()));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return Settings.System.getInt(
                                this.mContext.getContentResolver(), "screen_flash_notification", 0)
                        != 0
                ? 0
                : 2;
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

    @Override // com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider
    public Map<String, String> getStatusLoggingData(Context context) {
        String str = "Yellow";
        if (!isColorModeAllApps()) {
            return Map.of();
        }
        try {
            switch (FlashNotificationsUtil.getScreenColor(
                            Settings.System.getInt(
                                    this.mContext.getContentResolver(),
                                    "screen_flash_notification_color_global",
                                    FlashNotificationsUtil.DEFAULT_SCREEN_FLASH_COLOR))
                    .ordinal()) {
                case 0:
                    str = "Blue";
                    break;
                case 1:
                    str = "Azure";
                    break;
                case 2:
                    str = "Cyan";
                    break;
                case 3:
                    str = "Spring green";
                    break;
                case 4:
                    str = "Green";
                    break;
                case 5:
                    str = "Chartreuse green";
                    break;
                case 7:
                    str = "Orange";
                    break;
                case 8:
                    str = "Red";
                    break;
                case 9:
                    str = "Rose";
                    break;
                case 10:
                    str = "Magenta";
                    break;
                case 11:
                    str = "Violet";
                    break;
            }
        } catch (FlashNotificationsUtil.ScreenColorNotFoundException unused) {
            Log.e(TAG, "getStatusLoggingData got wrong colorInt. info");
        }
        return Map.of("A11YS6012", str);
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
        if (!getPreferenceKey().equals(preference.getKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        Settings.System.putInt(
                this.mContext.getContentResolver(), "screen_flash_notification_color_mode", 0);
        refresh();
        return true;
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

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        ScreenFlashNotificationColorDialogFragment.getInstance(
                        Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                "screen_flash_notification_color_global",
                                FlashNotificationsUtil.DEFAULT_SCREEN_FLASH_COLOR),
                        new ScreenFlashNotificationAllAppsPreferenceController$$ExternalSyntheticLambda0(
                                this, 0))
                .show(
                        this.parentFragment.getParentFragmentManager(),
                        "ScreenFlashNotificationColorDialogFragment");
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onCreate(LifecycleOwner lifecycleOwner) {
        super.onCreate(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onDestroy(LifecycleOwner lifecycleOwner) {
        super.onDestroy(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onPause(LifecycleOwner lifecycleOwner) {
        super.onPause(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onResume(LifecycleOwner lifecycleOwner) {
        super.onResume(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStart(LifecycleOwner lifecycleOwner) {
        List.of(
                        Settings.System.getUriFor("screen_flash_notification"),
                        Settings.System.getUriFor("screen_flash_notification_color_mode"),
                        Settings.Secure.getUriFor("screen_flash_notification_color_global"))
                .forEach(
                        new ScreenFlashNotificationAllAppsPreferenceController$$ExternalSyntheticLambda0(
                                this, 1));
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStop(LifecycleOwner lifecycleOwner) {
        try {
            this.mContext
                    .getContentResolver()
                    .unregisterContentObserver(this.mScreenFlashModeObserver);
        } catch (IllegalArgumentException unused) {
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setParentFragment(Fragment fragment) {
        this.parentFragment = fragment;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        refresh();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
