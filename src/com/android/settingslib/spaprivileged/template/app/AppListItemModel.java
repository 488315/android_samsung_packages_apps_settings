package com.android.settingslib.spaprivileged.template.app;

import androidx.compose.foundation.text.modifiers.TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0;

import com.android.settingslib.spaprivileged.model.app.AppRecord;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppListItemModel {
    public final String label;
    public final AppRecord record;
    public final Function0 summary;

    public AppListItemModel(AppRecord record, String str, Function0 summary) {
        Intrinsics.checkNotNullParameter(record, "record");
        Intrinsics.checkNotNullParameter(summary, "summary");
        this.record = record;
        this.label = str;
        this.summary = summary;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppListItemModel)) {
            return false;
        }
        AppListItemModel appListItemModel = (AppListItemModel) obj;
        return Intrinsics.areEqual(this.record, appListItemModel.record)
                && Intrinsics.areEqual(this.label, appListItemModel.label)
                && Intrinsics.areEqual(this.summary, appListItemModel.summary);
    }

    public final int hashCode() {
        return this.summary.hashCode()
                + TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0.m(
                        this.record.hashCode() * 31, 31, this.label);
    }

    public final String toString() {
        return "AppListItemModel(record="
                + this.record
                + ", label="
                + this.label
                + ", summary="
                + this.summary
                + ")";
    }
}
