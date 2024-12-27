package com.samsung.android.settings.notification.brief;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BriefPopUpKeywordColorController extends BasePreferenceController {
    private static final String DB_NAME = "edge_lighting";
    public static final String EDGE_LIGHTING_STYLE_NO_FRAME = "preload/noframe";
    public static final String EDGE_LIGHTING_STYLE_REFLECTION = "preload/reflection";
    private static final String KEY_BRIEF_POPUP_KEYWORD_COLOR =
            "brief_popup_keyword_color_settings";
    private static final String KEY_SHOW_NOTIFICATION_APP_ICON = "show_notification_app_icon";
    private static final String STYLE_DB = "edge_lighting_style_type_str";
    private static final String TAG = "BriefPopUpKeywordColorController";
    PreferenceScreen mParentScreen;
    private SecPreference mPreference;
    private String mPreferenceKey;

    public BriefPopUpKeywordColorController(Context context, String str) {
        super(context, KEY_BRIEF_POPUP_KEYWORD_COLOR);
        this.mPreferenceKey = str;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = (SecPreference) preferenceScreen.findPreference(this.mPreferenceKey);
        this.mParentScreen = preferenceScreen;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    @Override // com.android.settings.core.BasePreferenceController
    public int getMetricsCategory() {
        return 36013;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
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
        if (!preference.getKey().equals(KEY_BRIEF_POPUP_KEYWORD_COLOR)) {
            return false;
        }
        SALogging.insertSALog(String.valueOf(getMetricsCategory()), "NSTE0017");
        return false;
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

    /* JADX WARN: Multi-variable type inference failed */
    public void updateVisible() {
        boolean z =
                Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                DB_NAME,
                                !Rune.isEdgeLightingDefaultOff() ? 1 : 0)
                        == 1;
        byte b =
                Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                KEY_SHOW_NOTIFICATION_APP_ICON,
                                1)
                        == 1;
        String stringForUser =
                Settings.System.getStringForUser(this.mContext.getContentResolver(), STYLE_DB, -2);
        if (b == true && (stringForUser == null || stringForUser.equals("preload/noframe"))) {
            this.mPreference.setEnabled(false);
            this.mPreference.setSummary(R.string.breif_popup_style_effect_set_summary);
        } else if (b == true && stringForUser.equals("preload/reflection")) {
            this.mPreference.setEnabled(false);
            this.mPreference.setSummary(R.string.breif_pop_style_effect_style_not_supported);
        } else {
            this.mPreference.setEnabled(true);
            this.mPreference.setSummary((CharSequence) null);
        }
        setVisible(this.mParentScreen, getPreferenceKey(), z);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
