package com.samsung.android.settings.usefulfeature.functionkey;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.SecPreferenceCategory;

import com.android.settings.R;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.usefulfeature.functionkey.item.FunctionKeyAction;
import com.samsung.android.settings.usefulfeature.functionkey.item.FunctionKeyItem;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;
import com.samsung.android.settings.widget.SecRadioButtonGearPreferenceControllersHandler;

import java.util.ArrayList;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class FunctionKeyLongPressSettings extends FunctionKeyItemSettings {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Uri FUNC_KEY_LONG_PRESS_TYPE =
            Settings.Global.getUriFor("function_key_config_longpress_type");

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemSettings,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        Bundle arguments = getArguments();
        if (arguments == null) {
            arguments = new Bundle();
        }
        arguments.putInt("pressType", 1);
        super.onAttach(context);
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemSettings,
              // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceControllersHandler.OnChangeListener
    public final void onCheckedChanged(
            final SecRadioButtonGearPreferenceControllersHandler
                    secRadioButtonGearPreferenceControllersHandler) {
        Settings.Global.putString(
                ((FunctionKeyItemSettings) this).mContext.getContentResolver(),
                "function_key_config_longpress_selected_item",
                secRadioButtonGearPreferenceControllersHandler.mSelectedKey);
        if (((FunctionKeyItem)
                        this.mFunctionKeyItems.stream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyLongPressSettings$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                SecRadioButtonGearPreferenceControllersHandler
                                                        secRadioButtonGearPreferenceControllersHandler2 =
                                                                SecRadioButtonGearPreferenceControllersHandler
                                                                        .this;
                                                int i = FunctionKeyLongPressSettings.$r8$clinit;
                                                return TextUtils.equals(
                                                        ((FunctionKeyItem) obj).key,
                                                        secRadioButtonGearPreferenceControllersHandler2
                                                                .mSelectedKey);
                                            }
                                        })
                                .findFirst()
                                .orElse(null))
                instanceof FunctionKeyAction) {
            UsefulfeatureUtils.setSideKeyCustomizationInfo(
                    ((FunctionKeyItemSettings) this).mContext, 1, true);
        }
        finish();
        super.onCheckedChanged(secRadioButtonGearPreferenceControllersHandler);
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemSettings,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getActivity() != null) {
            getActivity().setTitle(R.string.sec_function_key_long_press_title);
        }
        SecRadioButtonGearPreferenceControllersHandler
                secRadioButtonGearPreferenceControllersHandler = this.mControllersHandler;
        secRadioButtonGearPreferenceControllersHandler.mSelectedKey =
                Settings.Global.getString(
                        ((FunctionKeyItemSettings) this).mContext.getContentResolver(),
                        "function_key_config_longpress_selected_item");
        secRadioButtonGearPreferenceControllersHandler.updateStates(false);
        FunctionKeyItemSettings.SettingsObserver settingsObserver = this.mSettingsObserver;
        Uri uri = this.FUNC_KEY_LONG_PRESS_TYPE;
        if (uri != null) {
            ((ArrayList) settingsObserver.mUris).add(uri);
        } else {
            settingsObserver.getClass();
        }
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemSettings,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        if (Rune.FEATURE_SIDE_BUTTON_SUPPORT_AI_AGENT_APP) {
            SecPreferenceCategory secPreferenceCategory =
                    new SecPreferenceCategory(getPrefContext());
            secPreferenceCategory.setTitle(R.string.sec_long_press_ai_assistant);
            secPreferenceCategory.setOrder(50000);
            getPreferenceScreen().addPreference(secPreferenceCategory);
            SecInsetCategoryPreference secInsetCategoryPreference =
                    new SecInsetCategoryPreference(getPrefContext());
            secInsetCategoryPreference.setOrder(250000);
            getPreferenceScreen().addPreference(secInsetCategoryPreference);
        }
    }
}
