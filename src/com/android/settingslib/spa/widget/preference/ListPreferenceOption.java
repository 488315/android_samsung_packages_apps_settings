package com.android.settingslib.spa.widget.preference;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.modifiers.TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ListPreferenceOption {
    public final int id;
    public final String summary;
    public final String text;

    public ListPreferenceOption(int i, String text, String summary) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(summary, "summary");
        this.id = i;
        this.text = text;
        this.summary = summary;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ListPreferenceOption)) {
            return false;
        }
        ListPreferenceOption listPreferenceOption = (ListPreferenceOption) obj;
        return this.id == listPreferenceOption.id
                && Intrinsics.areEqual(this.text, listPreferenceOption.text)
                && Intrinsics.areEqual(this.summary, listPreferenceOption.summary);
    }

    public final int hashCode() {
        return this.summary.hashCode()
                + TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0.m(
                        Integer.hashCode(this.id) * 31, 31, this.text);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ListPreferenceOption(id=");
        sb.append(this.id);
        sb.append(", text=");
        sb.append(this.text);
        sb.append(", summary=");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.summary, ")");
    }

    public /* synthetic */ ListPreferenceOption(int i, String str) {
        this(i, str, new String());
    }
}
