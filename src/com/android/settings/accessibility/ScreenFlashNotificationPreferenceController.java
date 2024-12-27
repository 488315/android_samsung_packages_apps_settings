package com.android.settings.accessibility;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ScreenFlashNotificationPreferenceController extends TogglePreferenceController {
    private Fragment mParentFragment;
    private Preference mPreference;

    public ScreenFlashNotificationPreferenceController(Context context, String str) {
        super(context, str);
    }

    private void checkAndSetInitialColor() {
        if (Settings.System.getInt(
                        this.mContext.getContentResolver(),
                        "screen_flash_notification_color_global",
                        0)
                == 0) {
            Settings.System.putInt(
                    this.mContext.getContentResolver(),
                    "screen_flash_notification_color_global",
                    FlashNotificationsUtil.DEFAULT_SCREEN_FLASH_COLOR);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handlePreferenceTreeClick$0(Integer num) {
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                "screen_flash_notification_color_global",
                num.intValue());
        refreshColorSummary();
    }

    private void refreshColorSummary() {
        Preference preference = this.mPreference;
        if (preference != null) {
            preference.setSummary(getSummary());
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
        refreshColorSummary();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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
        Context context = this.mContext;
        return FlashNotificationsUtil.getColorDescriptionText(
                context,
                Settings.System.getInt(
                        context.getContentResolver(),
                        "screen_flash_notification_color_global",
                        FlashNotificationsUtil.DEFAULT_SCREEN_FLASH_COLOR));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!getPreferenceKey().equals(preference.getKey()) || this.mParentFragment == null) {
            return super.handlePreferenceTreeClick(preference);
        }
        ScreenFlashNotificationColorDialogFragment.getInstance(
                        Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                "screen_flash_notification_color_global",
                                FlashNotificationsUtil.DEFAULT_SCREEN_FLASH_COLOR),
                        new Consumer() { // from class:
                                         // com.android.settings.accessibility.ScreenFlashNotificationPreferenceController$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ScreenFlashNotificationPreferenceController.this
                                        .lambda$handlePreferenceTreeClick$0((Integer) obj);
                            }
                        })
                .show(
                        this.mParentFragment.getParentFragmentManager(),
                        "ScreenFlashNotificationColorDialogFragment");
        return true;
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
        return Settings.System.getInt(
                        this.mContext.getContentResolver(), "screen_flash_notification", 0)
                != 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
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
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl
                .getMetricsFeatureProvider()
                .changed(getMetricsCategory(), z ? 1 : 0, getPreferenceKey());
        if (z) {
            checkAndSetInitialColor();
        }
        return Settings.System.putInt(
                this.mContext.getContentResolver(), "screen_flash_notification", z ? 1 : 0);
    }

    public void setParentFragment(Fragment fragment) {
        this.mParentFragment = fragment;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
