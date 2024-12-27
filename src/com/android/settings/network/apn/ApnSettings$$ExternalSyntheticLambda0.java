package com.android.settings.network.apn;

import android.util.Log;
import android.widget.RadioButton;

import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;

import com.samsung.android.settings.widget.SecInsetCategoryPreference;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ApnSettings$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ApnSettings f$0;

    public /* synthetic */ ApnSettings$$ExternalSyntheticLambda0(ApnSettings apnSettings, int i) {
        this.$r8$classId = i;
        this.f$0 = apnSettings;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        Unit unit = Unit.INSTANCE;
        ApnSettings apnSettings = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                boolean z = ApnSettings.DEBUG;
                apnSettings.getClass();
                if (!((Boolean) obj).booleanValue()) {
                    Log.d(
                            "ApnSettings",
                            "Due to subscription not enabled, closes APN settings page");
                    apnSettings.finish();
                    break;
                }
                break;
            case 1:
                String str = (String) obj;
                if (apnSettings.mPreferredApnRepositorySim1.subId == apnSettings.mSubId) {
                    PreferenceGroup preferenceGroup =
                            (PreferenceGroup) apnSettings.findPreference("apn_list");
                    for (int i = 0; i < preferenceGroup.getPreferenceCount(); i++) {
                        if (!(preferenceGroup.getPreference(i)
                                        instanceof SecInsetCategoryPreference)
                                && (preferenceGroup.getPreference(i) instanceof Preference)) {
                            ApnPreference apnPreference =
                                    (ApnPreference) preferenceGroup.getPreference(i);
                            boolean equals = apnPreference.getKey().equals(str);
                            apnPreference.mIsChecked = equals;
                            RadioButton radioButton = apnPreference.mRadioButton;
                            if (radioButton != null) {
                                apnPreference.mProtectFromCheckedChange = true;
                                radioButton.setChecked(equals);
                                apnPreference.mProtectFromCheckedChange = false;
                            }
                        }
                    }
                    break;
                }
                break;
            default:
                String str2 = (String) obj;
                if (apnSettings.mPreferredApnRepositorySim2.subId == apnSettings.mSubId) {
                    PreferenceGroup preferenceGroup2 =
                            (PreferenceGroup) apnSettings.findPreference("apn_list");
                    for (int i2 = 0; i2 < preferenceGroup2.getPreferenceCount(); i2++) {
                        if (!(preferenceGroup2.getPreference(i2)
                                        instanceof SecInsetCategoryPreference)
                                && (preferenceGroup2.getPreference(i2) instanceof Preference)) {
                            ApnPreference apnPreference2 =
                                    (ApnPreference) preferenceGroup2.getPreference(i2);
                            boolean equals2 = apnPreference2.getKey().equals(str2);
                            apnPreference2.mIsChecked = equals2;
                            RadioButton radioButton2 = apnPreference2.mRadioButton;
                            if (radioButton2 != null) {
                                apnPreference2.mProtectFromCheckedChange = true;
                                radioButton2.setChecked(equals2);
                                apnPreference2.mProtectFromCheckedChange = false;
                            }
                        }
                    }
                    break;
                }
                break;
        }
        return unit;
    }
}
