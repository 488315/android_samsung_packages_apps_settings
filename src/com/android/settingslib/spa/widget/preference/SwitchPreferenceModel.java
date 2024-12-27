package com.android.settingslib.spa.widget.preference;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface SwitchPreferenceModel {
    default Function0 getChangeable() {
        return new Function0() { // from class:
                                 // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel$changeable$1
            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                return Boolean.TRUE;
            }
        };
    }

    Function0 getChecked();

    default Function2 getIcon() {
        return null;
    }

    Function1 getOnCheckedChange();

    default Function0 getSummary() {
        return new Function0() { // from class:
                                 // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel$summary$1
            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                return ApnSettings.MVNO_NONE;
            }
        };
    }

    String getTitle();
}
