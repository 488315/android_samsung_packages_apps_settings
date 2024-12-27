package com.android.settingslib.spa.framework.common;

import android.net.Uri;

import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SettingsEntryBuilder {
    public static final Companion Companion = null;
    public SettingsPage fromPage;
    public boolean isAllowSearch;
    public String label;
    public final String name;
    public final SettingsPage owner;
    public Function1 searchDataFn;
    public final Function2 sliceDataFn;
    public final Function1 statusDataFn;
    public SettingsPage toPage;
    public Function3 uiLayoutFn;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {
        public static SettingsEntryBuilder createInject$default(SettingsPage owner) {
            String label = "INJECT_" + owner.displayName;
            Intrinsics.checkNotNullParameter(owner, "owner");
            Intrinsics.checkNotNullParameter(label, "label");
            SettingsEntryBuilder settingsEntryBuilder = new SettingsEntryBuilder("INJECT", owner);
            SettingsEntryBuilder.setLink$default(settingsEntryBuilder, null, owner, 1);
            settingsEntryBuilder.label = label;
            return settingsEntryBuilder;
        }
    }

    public SettingsEntryBuilder(String str, SettingsPage owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        this.name = str;
        this.owner = owner;
        this.label = str;
        this.uiLayoutFn = ComposableSingletons$SettingsEntryBuilderKt.f72lambda1;
        this.statusDataFn =
                new Function1() { // from class:
                                  // com.android.settingslib.spa.framework.common.SettingsEntryBuilder$statusDataFn$1
                    @Override // kotlin.jvm.functions.Function1
                    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        return null;
                    }
                };
        this.searchDataFn =
                new Function1() { // from class:
                                  // com.android.settingslib.spa.framework.common.SettingsEntryBuilder$searchDataFn$1
                    @Override // kotlin.jvm.functions.Function1
                    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        return null;
                    }
                };
        this.sliceDataFn =
                new Function2() { // from class:
                                  // com.android.settingslib.spa.framework.common.SettingsEntryBuilder$sliceDataFn$1
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        Intrinsics.checkNotNullParameter((Uri) obj, "<anonymous parameter 0>");
                        return null;
                    }
                };
    }

    public static void setLink$default(
            SettingsEntryBuilder settingsEntryBuilder,
            SettingsPage settingsPage,
            SettingsPage settingsPage2,
            int i) {
        if ((i & 1) != 0) {
            settingsPage = null;
        }
        if ((i & 2) != 0) {
            settingsPage2 = null;
        }
        if (settingsPage != null) {
            settingsEntryBuilder.fromPage = settingsPage;
        }
        if (settingsPage2 != null) {
            settingsEntryBuilder.toPage = settingsPage2;
        }
    }

    public final SettingsEntry build() {
        SettingsPage settingsPage = this.fromPage;
        SettingsPage owner = this.owner;
        if (settingsPage == null) {
            settingsPage = owner;
        }
        SettingsPageProvider pageProvider =
                SettingsPageProviderKt.getPageProvider(settingsPage.sppName);
        boolean z = false;
        boolean isEnabled = pageProvider != null ? pageProvider.isEnabled() : false;
        SettingsPage settingsPage2 = this.fromPage;
        SettingsPage settingsPage3 = this.toPage;
        String name = this.name;
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(owner, "owner");
        String str = settingsPage2 != null ? settingsPage2.id : null;
        String str2 = settingsPage3 != null ? settingsPage3.id : null;
        StringBuilder m =
                PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(name, ":");
        ConstraintWidget$$ExternalSyntheticOutline0.m(m, owner.id, "(", str, "-");
        m.append(str2);
        m.append(")");
        CharsKt.checkRadix(36);
        String l = Long.toString(m.toString().hashCode() & 4294967295L, 36);
        Intrinsics.checkNotNullExpressionValue(l, "toString(...)");
        String str3 = this.label;
        SettingsPage settingsPage4 = this.fromPage;
        SettingsPage settingsPage5 = this.toPage;
        if (isEnabled && this.isAllowSearch) {
            z = true;
        }
        return new SettingsEntry(
                l,
                this.name,
                str3,
                this.owner,
                settingsPage4,
                settingsPage5,
                z,
                this.statusDataFn,
                this.searchDataFn,
                this.sliceDataFn,
                this.uiLayoutFn);
    }

    public final void setUiLayoutFn(ComposableLambdaImpl fn) {
        Intrinsics.checkNotNullParameter(fn, "fn");
        this.uiLayoutFn = fn;
    }
}
