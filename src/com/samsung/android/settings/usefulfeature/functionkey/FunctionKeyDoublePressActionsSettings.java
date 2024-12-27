package com.samsung.android.settings.usefulfeature.functionkey;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.samsung.android.settings.widget.SecRadioButtonGearPreferenceControllersHandler;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class FunctionKeyDoublePressActionsSettings extends FunctionKeyItemSettings {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyDoublePressActionsSettings$1, reason: invalid class name */
    class AnonymousClass1 extends TypeToken<Map<String, String>> {}

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemSettings,
              // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceControllersHandler.OnChangeListener
    public final void onCheckedChanged(
            SecRadioButtonGearPreferenceControllersHandler
                    secRadioButtonGearPreferenceControllersHandler) {
        Intent intent = new Intent();
        intent.putExtra(
                GoodSettingsContract.EXTRA_NAME.POLICY_KEY,
                secRadioButtonGearPreferenceControllersHandler.mSelectedKey);
        setResult(-1, intent);
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
        Map map;
        super.onCreate(bundle);
        String string =
                Settings.Global.getString(
                        ((FunctionKeyItemSettings) this).mContext.getContentResolver(),
                        "function_key_config_doublepress_selected_actions");
        String str =
                (string == null
                                || (map =
                                                (Map)
                                                        new Gson()
                                                                .fromJson(
                                                                        string,
                                                                        new AnonymousClass1()
                                                                                .getType()))
                                        == null)
                        ? null
                        : (String) map.get(this.mApplicationKey);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        SecRadioButtonGearPreferenceControllersHandler
                secRadioButtonGearPreferenceControllersHandler = this.mControllersHandler;
        secRadioButtonGearPreferenceControllersHandler.mSelectedKey = str;
        secRadioButtonGearPreferenceControllersHandler.updateStates(false);
    }
}
