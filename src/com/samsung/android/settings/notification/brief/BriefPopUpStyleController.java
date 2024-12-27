package com.samsung.android.settings.notification.brief;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.util.secutil.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BriefPopUpStyleController extends BasePreferenceController {
    private static final String DB_NAME = "edge_lighting";
    private static final String DB_REMOVE_ANIMATIONS = "remove_animations";
    public static final String EDGE_LIGHTING_STYLE_BASIC = "preload/basic";
    public static final String EDGE_LIGHTING_STYLE_BUBBLE = "preload/bubble";
    public static final String EDGE_LIGHTING_STYLE_ECHO = "preload/echo";
    public static final String EDGE_LIGHTING_STYLE_ECLIPSE = "preload/eclipse";
    public static final String EDGE_LIGHTING_STYLE_FIREWORKS = "preload/fireworks";
    public static final String EDGE_LIGHTING_STYLE_GLOW = "preload/glow";
    public static final String EDGE_LIGHTING_STYLE_GRADATION = "preload/gradation";
    public static final String EDGE_LIGHTING_STYLE_HEART = "preload/heart";
    public static final String EDGE_LIGHTING_STYLE_NO_FRAME = "preload/noframe";
    public static final String EDGE_LIGHTING_STYLE_REFLECTION = "preload/reflection";
    public static final String EDGE_LIGHTING_STYLE_SPOTLIGHT = "preload/spotlight";
    public static final String EDGE_LIGHTING_STYLE_WAVE = "preload/wave";
    private static final String KEY_BRIEF_POPUP_STYLE = "brief_popup_style_settings";
    static final int OFF = 0;
    static final int ON = 1;
    private static final String STYLE_DB = "edge_lighting_style_type_str";
    private static final String STYLE_DB_OLD = "edge_lighting_style_type";
    private static final String TAG = "BriefPopUpStyleController";
    PreferenceScreen mParentScreen;
    private SecPreference mPreference;
    private String mPreferenceKey;

    public BriefPopUpStyleController(Context context, String str) {
        super(context, KEY_BRIEF_POPUP_STYLE);
        this.mPreferenceKey = str;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecPreference secPreference =
                (SecPreference) preferenceScreen.findPreference(this.mPreferenceKey);
        this.mPreference = secPreference;
        this.mParentScreen = preferenceScreen;
        refreshSummary(secPreference);
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

    public String getBriefPopupStyleType() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (Settings.System.getInt(contentResolver, DB_REMOVE_ANIMATIONS, 0) == 1) {
            return "preload/noframe";
        }
        int intForUser = Settings.System.getIntForUser(contentResolver, STYLE_DB_OLD, -1, -2);
        if (intForUser >= 0) {
            Settings.System.putIntForUser(contentResolver, STYLE_DB_OLD, -1, -2);
            if (intForUser == 0) {
                Settings.System.putStringForUser(contentResolver, STYLE_DB, "preload/noframe", -2);
            } else if (intForUser == 1) {
                Settings.System.putStringForUser(contentResolver, STYLE_DB, "preload/noframe", -2);
            } else if (intForUser == 2) {
                Settings.System.putStringForUser(contentResolver, STYLE_DB, "preload/noframe", -2);
            } else if (intForUser == 3) {
                String string =
                        SemFloatingFeature.getInstance()
                                .getString(
                                        "SEC_FLOATING_FEATURE_SYSTEMUI_CONFIG_EDGELIGHTING_FRAME_EFFECT");
                if (string == null || !string.contains("frame_effect")) {
                    Settings.System.putStringForUser(
                            contentResolver, STYLE_DB, "preload/noframe", -2);
                } else {
                    Settings.System.putStringForUser(
                            contentResolver, STYLE_DB, "preload/reflection", -2);
                }
            }
        }
        String stringForUser = Settings.System.getStringForUser(contentResolver, STYLE_DB, -2);
        return stringForUser == null ? "preload/noframe" : stringForUser;
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

    public String getStyleSummaryName() {
        String briefPopupStyleType = getBriefPopupStyleType();
        briefPopupStyleType.getClass();
        switch (briefPopupStyleType) {
            case "preload/spotlight":
                return this.mContext.getString(R.string.brief_popup_style_spotlight_effect);
            case "preload/fireworks":
                return this.mContext.getString(R.string.brief_popup_style_fireworks_effect);
            case "preload/noframe":
                return this.mContext.getString(R.string.brief_popup_style_noframe_effect);
            case "preload/eclipse":
                return this.mContext.getString(R.string.brief_popup_style_eclipse_effect);
            case "preload/bubble":
                return this.mContext.getString(R.string.brief_popup_style_basic_effect);
            case "preload/reflection":
                return this.mContext.getString(R.string.brief_popup_style_glitter_effect);
            case "preload/basic":
                return this.mContext.getString(R.string.brief_popup_style_basic_effect);
            case "preload/heart":
                return this.mContext.getString(R.string.brief_popup_style_heart_effect);
            case "preload/echo":
                return this.mContext.getString(R.string.brief_popup_style_echo_effect);
            case "preload/glow":
                return this.mContext.getString(R.string.brief_popup_style_basic_effect);
            case "preload/wave":
                return this.mContext.getString(R.string.brief_popup_style_basic_effect);
            case "preload/gradation":
                return this.mContext.getString(R.string.brief_popup_style_basic_effect);
            default:
                return this.mContext.getString(R.string.brief_popup_style_noframe_effect);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        SecPreference secPreference = this.mPreference;
        if (secPreference != null) {
            secPreference.seslSetSummaryColor(
                    this.mContext.getColorStateList(R.color.sesl_primary_dark_color_dark));
        }
        return getStyleSummaryName();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (preference.getKey().equals(KEY_BRIEF_POPUP_STYLE)) {
            SALogging.insertSALog(String.valueOf(getMetricsCategory()), "NSTE0016");
        }
        if (!preference.getKey().equals(getPreferenceKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        try {
            Intent intent = new Intent();
            intent.setClassName(
                    "com.android.systemui",
                    "com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity");
            intent.addFlags(268468224);
            this.mContext.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            Log.d(TAG, "Activity Not Found");
            e.printStackTrace();
            return true;
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

    public void updateVisible() {
        this.mPreference.setEnabled(
                !(Settings.System.getInt(
                                this.mContext.getContentResolver(), DB_REMOVE_ANIMATIONS, 0)
                        == 1));
        boolean z =
                Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                DB_NAME,
                                !Rune.isEdgeLightingDefaultOff() ? 1 : 0)
                        == 1;
        setVisible(this.mParentScreen, getPreferenceKey(), z);
        if (this.mPreference.isVisible()) {
            refreshSummary(this.mPreference);
        }
        SALogging.insertSALog(z ? 1L : 0L, String.valueOf(getMetricsCategory()), "NSTE0001");
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
