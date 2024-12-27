package com.android.settings.datausage.lib;

import android.R;
import android.content.Context;
import android.text.BidiFormatter;
import android.text.format.Formatter;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DataUsageFormatter {
    public final Context context;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class FormattedDataUsage {
        public final String contentDescription;
        public final String displayText;

        public FormattedDataUsage(String str, String str2) {
            this.displayText = str;
            this.contentDescription = str2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FormattedDataUsage)) {
                return false;
            }
            FormattedDataUsage formattedDataUsage = (FormattedDataUsage) obj;
            return Intrinsics.areEqual(this.displayText, formattedDataUsage.displayText)
                    && Intrinsics.areEqual(
                            this.contentDescription, formattedDataUsage.contentDescription);
        }

        public final FormattedDataUsage format(Context context, int i, Object... objArr) {
            Intrinsics.checkNotNullParameter(context, "context");
            SpreadBuilder spreadBuilder = new SpreadBuilder(2);
            spreadBuilder.add(this.displayText);
            spreadBuilder.addSpread(objArr);
            String string =
                    context.getString(
                            i, spreadBuilder.list.toArray(new Object[spreadBuilder.list.size()]));
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            SpreadBuilder spreadBuilder2 = new SpreadBuilder(2);
            spreadBuilder2.add(this.contentDescription);
            spreadBuilder2.addSpread(objArr);
            String string2 =
                    context.getString(
                            i, spreadBuilder2.list.toArray(new Object[spreadBuilder2.list.size()]));
            Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
            return new FormattedDataUsage(string, string2);
        }

        public final int hashCode() {
            return this.contentDescription.hashCode() + (this.displayText.hashCode() * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("FormattedDataUsage(displayText=");
            sb.append(this.displayText);
            sb.append(", contentDescription=");
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(
                    sb, this.contentDescription, ")");
        }
    }

    public DataUsageFormatter(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final FormattedDataUsage formatDataUsage(long j) {
        Formatter.BytesResult formatBytes =
                Formatter.formatBytes(this.context.getResources(), j, 8);
        String unicodeWrap =
                BidiFormatter.getInstance()
                        .unicodeWrap(
                                this.context.getString(
                                        R.string.lockscreen_glogin_invalid_input,
                                        formatBytes.value,
                                        formatBytes.units));
        Intrinsics.checkNotNullExpressionValue(unicodeWrap, "unicodeWrap(...)");
        String string =
                this.context.getString(
                        R.string.lockscreen_glogin_invalid_input,
                        formatBytes.value,
                        formatBytes.unitsContentDescription);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return new FormattedDataUsage(unicodeWrap, string);
    }
}
