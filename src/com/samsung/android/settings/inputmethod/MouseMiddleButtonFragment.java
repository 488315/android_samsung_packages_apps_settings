package com.samsung.android.settings.inputmethod;

import android.content.Context;
import android.provider.Settings;

import com.android.settings.R;

import com.samsung.android.settings.general.BaseResetSettingsData;
import com.samsung.android.settings.general.OnResetSettingsDataListener;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MouseMiddleButtonFragment extends CustomMousePreference {
    public static final OnResetSettingsDataListener RESET_SETTINGS_DATA = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.inputmethod.MouseMiddleButtonFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseResetSettingsData {
        @Override // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void resetSettings(Context context) {
            Settings.System.putInt(context.getContentResolver(), "mouse_middle_button_option", 3);
            Settings.System.putString(
                    context.getContentResolver(),
                    MouseFunctionKeyInfo.getMouseFunctionDBKey(11),
                    null);
        }
    }

    @Override // com.samsung.android.settings.inputmethod.CustomMousePreference
    public final int getButtonType() {
        return Settings.System.getInt(
                ((CustomMousePreference) this).mContext.getContentResolver(),
                "mouse_middle_button_option",
                3);
    }

    @Override // com.samsung.android.settings.inputmethod.CustomMousePreference,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 770102;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_mouse_middle_button_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mMouseButtonValue =
                context.getResources().getStringArray(R.array.key_mouse_middle_button_values);
        this.mMouseButtonType = 11;
        this.mMouseButtonSAConstantValue = 77015;
    }

    @Override // com.samsung.android.settings.inputmethod.CustomMousePreference
    public final void setButtonType(int i) {
        Settings.System.putInt(
                ((CustomMousePreference) this).mContext.getContentResolver(),
                "mouse_middle_button_option",
                i);
    }
}