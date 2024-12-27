package com.samsung.android.settings.accessibility.dexterity.autoaction;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.Settings;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider;
import com.samsung.android.settings.asbase.utils.VibUtils;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.widget.SecDisabledAppearancePreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CornerActionTypePreferenceController extends TogglePreferenceController
        implements AccessibilityObservableController, A11yStatusLoggingProvider {
    private static final String CORNER_ACTION_TYPE_FRAGMENT =
            "com.samsung.android.settings.accessibility.dexterity.autoaction.CornerActionTypePickerFragment";

    public CornerActionTypePreferenceController(Context context, String str) {
        super(context, str);
    }

    private String cornerActionList(String str, String str2) {
        String[] split = str.split(":");
        return split[0].contains(str2)
                ? "TopLeft"
                : split[1].contains(str2)
                        ? "TopRight"
                        : split[2].contains(str2)
                                ? "BottomLeft"
                                : split[3].contains(str2) ? "BottomRight" : "Off";
    }

    private CharSequence getDynamicSummary() {
        Context context = this.mContext;
        if (context == null) {
            return null;
        }
        String string =
                Settings.Secure.getString(
                        context.getContentResolver(), "accessibility_corner_actions");
        if (string == null) {
            string = "none:none:none:none";
        }
        String[] split = string.split(":");
        int cornerPosition = getCornerPosition(getPreferenceKey());
        Context context2 = this.mContext;
        String str = split[cornerPosition];
        HashMap hashMap = AutoActionUtils.mActionMap;
        String[] split2 = str.split(",");
        ArrayList arrayList = new ArrayList();
        for (String str2 : split2) {
            Integer num = (Integer) AutoActionUtils.mActionMap.get(str2);
            if (str2.equals("sound_vibrate_mute") && !VibUtils.hasVibrator(context2)) {
                num = Integer.valueOf(R.string.accessibility_corner_action_type_sound_mute);
            }
            if (num != null) {
                arrayList.add(context2.getString(num.intValue()));
            }
        }
        return String.join(context2.getString(R.string.comma) + " ", arrayList);
    }

    private String totalActions(String str, int i) {
        String str2 = str.split(":")[i];
        HashMap hashMap = AutoActionUtils.mActionMap;
        String[] split = str2.split(",");
        return String.valueOf(SignalSeverity.NONE.equals(split[0]) ? 0 : split.length);
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int getCornerPosition(String str) {
        boolean z;
        str.getClass();
        switch (str.hashCode()) {
            case -2143526799:
                if (str.equals("bottom_right_corner_action_preference")) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case -1863461881:
                if (str.equals("top_right_corner_action_preference")) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            case -709317314:
                if (str.equals("bottom_left_corner_action_preference")) {
                    z = 2;
                    break;
                }
                z = -1;
                break;
            case 1239379688:
                if (str.equals("top_left_corner_action_preference")) {
                    z = 3;
                    break;
                }
                z = -1;
                break;
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                return 3;
            case true:
                return 1;
            case true:
                return 2;
            case true:
                return 0;
            default:
                return -1;
        }
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

    @Override // com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider
    public Map<String, String> getStatusLoggingData(Context context) {
        String string =
                Settings.Secure.getString(
                        this.mContext.getContentResolver(), "accessibility_corner_actions");
        if (string == null) {
            string = "none:none:none:none";
        }
        return Map.ofEntries(
                Map.entry("A11YS5013", cornerActionList(string, "open_close_notifications")),
                Map.entry("A11YS5014", cornerActionList(string, "open_close_quick_panel")),
                Map.entry("A11YS5015", cornerActionList(string, "screen_shot")),
                Map.entry("A11YS5016", cornerActionList(string, "talk_to_bixby")),
                Map.entry("A11YS5017", cornerActionList(string, "send_sos_messages")),
                Map.entry("A11YS5018", cornerActionList(string, "media_volume_up")),
                Map.entry("A11YS5019", cornerActionList(string, "media_volume_down")),
                Map.entry("A11YS5020", cornerActionList(string, "ringtone_volume_up")),
                Map.entry("A11YS5021", cornerActionList(string, "ringtone_volume_down")),
                Map.entry("A11YS5022", cornerActionList(string, "increase_brightness")),
                Map.entry("A11YS5023", cornerActionList(string, "reduce_brightness")),
                Map.entry("A11YS5024", cornerActionList(string, "screen_rotation")),
                Map.entry("A11YS5025", cornerActionList(string, "sound_vibrate_mute")),
                Map.entry("A11YS5026", cornerActionList(string, "screen_off")),
                Map.entry("A11YS5027", cornerActionList(string, "power_off_menu")),
                Map.entry("A11YS5028", cornerActionList(string, "home")),
                Map.entry("A11YS5029", cornerActionList(string, "recents")),
                Map.entry("A11YS5030", cornerActionList(string, "back")),
                Map.entry("A11YS5031", cornerActionList(string, "accessibility_button")),
                Map.entry("A11YS5032", cornerActionList(string, "double_click")),
                Map.entry("A11YS5033", cornerActionList(string, "click_and_hold")),
                Map.entry("A11YS5034", cornerActionList(string, "drag")),
                Map.entry("A11YS5035", cornerActionList(string, "drag_and_drop")),
                Map.entry("A11YS5036", cornerActionList(string, "zoom_in")),
                Map.entry("A11YS5037", cornerActionList(string, "zoom_out")),
                Map.entry("A11YS5038", cornerActionList(string, "swipe_left")),
                Map.entry("A11YS5039", cornerActionList(string, "swipe_right")),
                Map.entry("A11YS5040", cornerActionList(string, "swipe_up")),
                Map.entry("A11YS5041", cornerActionList(string, "swipe_down")));
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return getDynamicSummary();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController
    public List<Uri> getUriList() {
        return List.of(Settings.Secure.getUriFor("accessibility_corner_actions"));
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
        return true;
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
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        preference.seslSetSummaryColor(
                this.mContext.getColorStateList(R.color.text_color_primary_dark));
        refreshSummary(preference);
        if (preference instanceof SecDisabledAppearancePreference) {
            SecDisabledAppearancePreference secDisabledAppearancePreference =
                    (SecDisabledAppearancePreference) preference;
            Context context = this.mContext;
            HashMap hashMap = AutoActionUtils.mActionMap;
            if (Settings.System.getInt(context.getContentResolver(), "any_screen_enabled", 0)
                    == 0) {
                secDisabledAppearancePreference.setEnabledAppearance(true);
                secDisabledAppearancePreference.setFragment(CORNER_ACTION_TYPE_FRAGMENT);
            } else {
                secDisabledAppearancePreference.setEnabledAppearance(false);
                secDisabledAppearancePreference.setFragment(null);
                Context context2 = this.mContext;
                secDisabledAppearancePreference.mMsg =
                        context2.getString(
                                R.string.cant_use_p1ss_while_p2ss_in_use,
                                context2.getString(
                                        R.string.accessibility_corner_action_preference_title),
                                this.mContext.getString(R.string.onehand_settings_title));
            }
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
