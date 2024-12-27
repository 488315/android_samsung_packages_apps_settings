package com.android.settingslib.spa.framework.common;

import android.os.Bundle;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.modifiers.TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;

import com.android.settingslib.spa.framework.util.UniqueIdKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SettingsPage {
    public static final Companion Companion = null;
    public final Bundle arguments;
    public final String displayName;
    public final String id;
    public final int metricsCategory;
    public final List parameter;
    public final String sppName;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {
        public static SettingsPage create$default(String str, List parameter, Bundle bundle) {
            Intrinsics.checkNotNullParameter(parameter, "parameter");
            return new SettingsPage(
                    UniqueIdKt.genPageId(str, parameter, bundle), str, 0, str, parameter, bundle);
        }
    }

    public SettingsPage(
            String str, String sppName, int i, String displayName, List parameter, Bundle bundle) {
        Intrinsics.checkNotNullParameter(sppName, "sppName");
        Intrinsics.checkNotNullParameter(displayName, "displayName");
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        this.id = str;
        this.sppName = sppName;
        this.metricsCategory = i;
        this.displayName = displayName;
        this.parameter = parameter;
        this.arguments = bundle;
    }

    public final void UiLayout(Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1314051099);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        SettingsPageProvider pageProvider = SettingsPageProviderKt.getPageProvider(this.sppName);
        if (pageProvider != null) {
            pageProvider.Page(this.arguments, composerImpl, 8);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.framework.common.SettingsPage$UiLayout$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SettingsPage.this.UiLayout(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SettingsPage)) {
            return false;
        }
        SettingsPage settingsPage = (SettingsPage) obj;
        return Intrinsics.areEqual(this.id, settingsPage.id)
                && Intrinsics.areEqual(this.sppName, settingsPage.sppName)
                && this.metricsCategory == settingsPage.metricsCategory
                && Intrinsics.areEqual(this.displayName, settingsPage.displayName)
                && Intrinsics.areEqual(this.parameter, settingsPage.parameter)
                && Intrinsics.areEqual(this.arguments, settingsPage.arguments);
    }

    public final int hashCode() {
        int hashCode =
                (this.parameter.hashCode()
                                + TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0
                                        .m(
                                                KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0
                                                        .m(
                                                                this.metricsCategory,
                                                                TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0
                                                                        .m(
                                                                                this.id.hashCode()
                                                                                        * 31,
                                                                                31,
                                                                                this.sppName),
                                                                31),
                                                31,
                                                this.displayName))
                        * 31;
        Bundle bundle = this.arguments;
        return hashCode + (bundle == null ? 0 : bundle.hashCode());
    }

    public final String toString() {
        return "SettingsPage(id="
                + this.id
                + ", sppName="
                + this.sppName
                + ", metricsCategory="
                + this.metricsCategory
                + ", displayName="
                + this.displayName
                + ", parameter="
                + this.parameter
                + ", arguments="
                + this.arguments
                + ")";
    }
}
