package com.android.settings.print;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.print.PrintManager;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.modifiers.TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PrintRepository {
    public final Context context;
    public final PackageManager packageManager;
    public final PrintManager printManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PrintServiceDisplayInfo {
        public final String componentName;
        public final Drawable icon;
        public final boolean isEnabled;
        public final String summary;
        public final String title;

        public PrintServiceDisplayInfo(
                String title, boolean z, String str, Drawable drawable, String str2) {
            Intrinsics.checkNotNullParameter(title, "title");
            this.title = title;
            this.isEnabled = z;
            this.summary = str;
            this.icon = drawable;
            this.componentName = str2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PrintServiceDisplayInfo)) {
                return false;
            }
            PrintServiceDisplayInfo printServiceDisplayInfo = (PrintServiceDisplayInfo) obj;
            return Intrinsics.areEqual(this.title, printServiceDisplayInfo.title)
                    && this.isEnabled == printServiceDisplayInfo.isEnabled
                    && Intrinsics.areEqual(this.summary, printServiceDisplayInfo.summary)
                    && Intrinsics.areEqual(this.icon, printServiceDisplayInfo.icon)
                    && Intrinsics.areEqual(
                            this.componentName, printServiceDisplayInfo.componentName);
        }

        public final int hashCode() {
            return this.componentName.hashCode()
                    + ((this.icon.hashCode()
                                    + TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0
                                            .m(
                                                    TransitionData$$ExternalSyntheticOutline0.m(
                                                            this.title.hashCode() * 31,
                                                            31,
                                                            this.isEnabled),
                                                    31,
                                                    this.summary))
                            * 31);
        }

        public final String toString() {
            Drawable drawable = this.icon;
            StringBuilder sb = new StringBuilder("PrintServiceDisplayInfo(title=");
            sb.append(this.title);
            sb.append(", isEnabled=");
            sb.append(this.isEnabled);
            sb.append(", summary=");
            sb.append(this.summary);
            sb.append(", icon=");
            sb.append(drawable);
            sb.append(", componentName=");
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.componentName, ")");
        }
    }

    public PrintRepository(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        Object systemService = context.getSystemService((Class<Object>) PrintManager.class);
        Intrinsics.checkNotNull(systemService);
        this.printManager = (PrintManager) systemService;
        this.packageManager = context.getPackageManager();
    }
}
