package com.samsung.android.settings.accessibility.dexterity.autoaction;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.SliderPreferenceController;

import com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController;
import com.samsung.android.settings.accessibility.base.widget.A11ySeekBarWithButtonsPreference;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AutoActionDelayPreferenceController extends SliderPreferenceController
        implements AccessibilityObservableController {
    static final int[] mAutoActionDelayPreferenceSummaries = {
        R.string.accessibilty_auto_action_preference_subtitle_extremely_short_delay,
        R.string.accessibilty_auto_action_preference_subtitle_very_short_delay,
        R.string.accessibilty_auto_action_preference_subtitle_short_delay,
        R.string.accessibilty_auto_action_preference_subtitle_long_delay,
        R.string.accessibilty_auto_action_preference_subtitle_very_long_delay
    };

    public AutoActionDelayPreferenceController(Context context, String str) {
        super(context, str);
    }

    public static String convertToArabic(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                sb.append((char) (str.charAt(i) + 1584));
            } else {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }

    public static CharSequence getAutoActionDelayPreferenceSummary(Resources resources, int i) {
        int autoActionPreferenceSummaryIndex = getAutoActionPreferenceSummaryIndex(i);
        boolean z = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
        String language = Locale.getDefault().getLanguage();
        String string =
                resources.getString(
                        mAutoActionDelayPreferenceSummaries[autoActionPreferenceSummaryIndex],
                        Integer.valueOf(i));
        return (z && language.contains("ar")) ? convertToArabic(string.toString()) : string;
    }

    public static int getAutoActionPreferenceSummaryIndex(int i) {
        if (i <= 200) {
            return 0;
        }
        if (i >= 1000) {
            return mAutoActionDelayPreferenceSummaries.length - 1;
        }
        return (i - 200) / (800 / (mAutoActionDelayPreferenceSummaries.length - 1));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference instanceof A11ySeekBarWithButtonsPreference) {
            findPreference.seslSetSummaryColor(
                    this.mContext.getColorStateList(R.color.text_color_primary_dark));
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getMax() {
        return 10;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getMin() {
        return 2;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getSliderPosition() {
        return (Settings.Secure.getInt(
                                this.mContext.getContentResolver(),
                                "accessibility_auto_action_delay",
                                600)
                        - 200)
                / 100;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController
    public List<Uri> getUriList() {
        return List.of(Settings.Secure.getUriFor("accessibility_auto_action_delay"));
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public boolean setSliderPosition(int i) {
        Settings.Secure.putInt(
                this.mContext.getContentResolver(),
                "accessibility_auto_action_delay",
                (i * 100) + 200);
        return true;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference instanceof A11ySeekBarWithButtonsPreference) {
            preference.setSummary(
                    getAutoActionDelayPreferenceSummary(
                            this.mContext.getResources(),
                            Settings.Secure.getInt(
                                    this.mContext.getContentResolver(),
                                    "accessibility_auto_action_delay",
                                    600)));
            ((A11ySeekBarWithButtonsPreference) preference)
                    .setStateDescription(preference.getSummary());
        }
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
