package com.android.settingslib.spaprivileged.model.app;

import android.icu.text.CollationKey;

import androidx.compose.foundation.text.modifiers.TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppEntry {
    public final String label;
    public final CollationKey labelCollationKey;
    public final AppRecord record;

    public AppEntry(AppRecord record, String str, CollationKey collationKey) {
        Intrinsics.checkNotNullParameter(record, "record");
        this.record = record;
        this.label = str;
        this.labelCollationKey = collationKey;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppEntry)) {
            return false;
        }
        AppEntry appEntry = (AppEntry) obj;
        return Intrinsics.areEqual(this.record, appEntry.record)
                && Intrinsics.areEqual(this.label, appEntry.label)
                && Intrinsics.areEqual(this.labelCollationKey, appEntry.labelCollationKey);
    }

    public final int hashCode() {
        return this.labelCollationKey.hashCode()
                + TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0.m(
                        this.record.hashCode() * 31, 31, this.label);
    }

    public final String toString() {
        return "AppEntry(record="
                + this.record
                + ", label="
                + this.label
                + ", labelCollationKey="
                + this.labelCollationKey
                + ")";
    }
}
