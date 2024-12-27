package com.android.settingslib.spa.widget.preference;

import androidx.compose.runtime.MutableIntState;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface ListPreferenceModel {
    default Function0 getEnabled() {
        return new Function0() { // from class:
                                 // com.android.settingslib.spa.widget.preference.ListPreferenceModel$enabled$1
            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                return Boolean.TRUE;
            }
        };
    }

    default Function2 getIcon() {
        return null;
    }

    Function1 getOnIdSelected();

    List getOptions();

    MutableIntState getSelectedId();

    String getTitle();
}
