package com.android.settings.notification.modes;

import android.service.notification.ZenPolicy;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeOtherPreferenceController extends AbstractZenModePreferenceController
        implements Preference.OnPreferenceChangeListener {
    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        final boolean booleanValue = ((Boolean) obj).booleanValue();
        return savePolicy(
                new Function() { // from class:
                                 // com.android.settings.notification.modes.ZenModeOtherPreferenceController$$ExternalSyntheticLambda0
                    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                    @Override // java.util.function.Function
                    public final Object apply(Object obj2) {
                        char c;
                        int i = 1;
                        ZenModeOtherPreferenceController zenModeOtherPreferenceController =
                                ZenModeOtherPreferenceController.this;
                        boolean z = booleanValue;
                        ZenPolicy.Builder builder = (ZenPolicy.Builder) obj2;
                        String str = zenModeOtherPreferenceController.mKey;
                        str.getClass();
                        switch (str.hashCode()) {
                            case -2032725825:
                                if (str.equals("modes_category_alarm")) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -2021849518:
                                if (str.equals("modes_category_media")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1671299665:
                                if (str.equals("modes_category_reminders")) {
                                    c = 2;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1533876331:
                                if (str.equals("modes_category_events")) {
                                    c = 3;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1937877377:
                                if (str.equals("modes_category_system")) {
                                    c = 4;
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                                i = 5;
                                break;
                            case 1:
                                i = 6;
                                break;
                            case 2:
                                i = 0;
                                break;
                            case 3:
                                break;
                            case 4:
                                i = 7;
                                break;
                            default:
                                i = -1;
                                break;
                        }
                        return builder.allowCategory(i, z);
                    }
                });
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    public final void updateState(Preference preference, ZenMode zenMode) {
        char c;
        int i = 0;
        TwoStatePreference twoStatePreference = (TwoStatePreference) preference;
        ZenPolicy policy = zenMode.getPolicy();
        String str = this.mKey;
        str.getClass();
        switch (str.hashCode()) {
            case -2032725825:
                if (str.equals("modes_category_alarm")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -2021849518:
                if (str.equals("modes_category_media")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1671299665:
                if (str.equals("modes_category_reminders")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1533876331:
                if (str.equals("modes_category_events")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1937877377:
                if (str.equals("modes_category_system")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                i = 5;
                break;
            case 1:
                i = 6;
                break;
            case 2:
                break;
            case 3:
                i = 1;
                break;
            case 4:
                i = 7;
                break;
            default:
                i = -1;
                break;
        }
        twoStatePreference.setChecked(policy.isCategoryAllowed(i, true));
    }
}
