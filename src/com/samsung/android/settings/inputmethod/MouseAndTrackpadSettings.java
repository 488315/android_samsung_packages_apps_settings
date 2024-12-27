package com.samsung.android.settings.inputmethod;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.settings.general.BaseResetSettingsData;
import com.samsung.android.settings.general.OnResetSettingsDataListener;
import com.samsung.android.settings.gts.GtsGroup;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MouseAndTrackpadSettings extends DashboardFragment {
    public static final String TAG = MouseAndTrackpadSettings.class.getName();
    public static final OnResetSettingsDataListener RESET_SETTINGS_DATA = new AnonymousClass1();
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass2(R.xml.sec_mouse_and_trackpad_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.inputmethod.MouseAndTrackpadSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseResetSettingsData {
        @Override // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void resetSettings(Context context) {
            Settings.System.putInt(context.getContentResolver(), "pointer_speed", 0);
            Settings.System.putInt(context.getContentResolver(), "mouse_scrolling_speed", 1);
            Settings.System.putInt(context.getContentResolver(), "enhance_pointer_precision", 1);
            Settings.System.putInt(context.getContentResolver(), "primary_mouse_button_option", 0);
            Settings.System.putInt(context.getContentResolver(), "touchpad_scrolling_direction", 0);
            Settings.System.putInt(context.getContentResolver(), "mouse_pointer_size_step", 1);
            Settings.System.putInt(
                    context.getContentResolver(),
                    "mouse_pointer_color",
                    context.getResources().getIntArray(R.array.mouse_pointer_color_array)[0]);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.inputmethod.MouseAndTrackpadSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final HashMap getGtsResourceGroup() {
            HashMap hashMap = new HashMap();
            String groupName = GtsGroup.GROUP_KEY_MOUSE_AND_TRACKPAD.getGroupName();
            hashMap.put("pointer_speed", groupName);
            hashMap.put("mouse_pointer_settings", groupName);
            hashMap.put("mouse_scrolling_speed", groupName);
            hashMap.put("enhance_pointer_precision", groupName);
            hashMap.put("key_primary_mouse_button", groupName);
            hashMap.put("key_secondary_button", groupName);
            hashMap.put("key_middle_button", groupName);
            hashMap.put("key_additional_button_1", groupName);
            hashMap.put("key_additional_button_2", groupName);
            return hashMap;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return TAG;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return Integer.parseInt("770100");
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_mouse_and_trackpad_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setAutoRemoveInsetCategory(false);
    }
}
