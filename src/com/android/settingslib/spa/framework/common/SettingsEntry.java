package com.android.settingslib.spa.framework.common;

import android.os.Bundle;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.modifiers.TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;

import com.android.settingslib.spa.framework.compose.NavControllerWrapper;
import com.android.settingslib.spa.framework.compose.NavControllerWrapperKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SettingsEntry {
    public final SettingsPage fromPage;
    public final String id;
    public final boolean isAllowSearch;
    public final String label;
    public final String name;
    public final SettingsPage owner;
    public final Function1 searchDataImpl;
    public final Function2 sliceDataImpl;
    public final Function1 statusDataImpl;
    public final SettingsPage toPage;
    public final Function3 uiLayoutImpl;

    public SettingsEntry(
            String str,
            String name,
            String label,
            SettingsPage owner,
            SettingsPage settingsPage,
            SettingsPage settingsPage2,
            boolean z,
            Function1 statusDataImpl,
            Function1 searchDataImpl,
            Function2 sliceDataImpl,
            Function3 uiLayoutImpl) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(statusDataImpl, "statusDataImpl");
        Intrinsics.checkNotNullParameter(searchDataImpl, "searchDataImpl");
        Intrinsics.checkNotNullParameter(sliceDataImpl, "sliceDataImpl");
        Intrinsics.checkNotNullParameter(uiLayoutImpl, "uiLayoutImpl");
        this.id = str;
        this.name = name;
        this.label = label;
        this.owner = owner;
        this.fromPage = settingsPage;
        this.toPage = settingsPage2;
        this.isAllowSearch = z;
        this.statusDataImpl = statusDataImpl;
        this.searchDataImpl = searchDataImpl;
        this.sliceDataImpl = sliceDataImpl;
        this.uiLayoutImpl = uiLayoutImpl;
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.settingslib.spa.framework.common.SettingsEntry$UiLayout$1, kotlin.jvm.internal.Lambda] */
    public final void UiLayout(final Bundle bundle, Composer composer, final int i, final int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1894810275);
        if ((i2 & 1) != 0) {
            bundle = null;
        }
        OpaqueKey opaqueKey = ComposerKt.invocation;
        composerImpl.startReplaceGroup(-2045095837);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        Object obj = rememberedValue;
        if (rememberedValue == composer$Companion$Empty$1) {
            Bundle bundle2 = new Bundle();
            Bundle bundle3 = this.owner.arguments;
            if (bundle3 != null) {
                bundle2.putAll(bundle3);
            }
            if (bundle != null) {
                bundle2.putAll(bundle);
            }
            composerImpl.updateRememberedValue(bundle2);
            obj = bundle2;
        }
        final Bundle bundle4 = (Bundle) obj;
        composerImpl.end(false);
        composerImpl.startReplaceGroup(447986177);
        final NavControllerWrapper navControllerWrapper =
                (NavControllerWrapper)
                        composerImpl.consume(NavControllerWrapperKt.LocalNavController);
        DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal =
                SettingsEntryKt.LocalEntryDataProvider;
        composerImpl.startReplaceGroup(1209729565);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 =
                    new EntryData(
                            this,
                            navControllerWrapper,
                            bundle4) { // from class:
                                       // com.android.settingslib.spa.framework.common.SettingsEntry$provideLocalEntryData$1$1
                        public final Bundle arguments;
                        public final String entryId;
                        public final boolean isHighlighted;

                        {
                            SettingsPage settingsPage = this.fromPage;
                            (settingsPage == null ? this.owner : settingsPage).getClass();
                            String str = this.id;
                            this.entryId = str;
                            this.isHighlighted =
                                    Intrinsics.areEqual(
                                            navControllerWrapper.getHighlightEntryId(), str);
                            this.arguments = bundle4;
                        }

                        @Override // com.android.settingslib.spa.framework.common.EntryData
                        public final Bundle getArguments() {
                            return this.arguments;
                        }

                        @Override // com.android.settingslib.spa.framework.common.EntryData
                        public final String getEntryId() {
                            return this.entryId;
                        }

                        @Override // com.android.settingslib.spa.framework.common.EntryData
                        public final boolean isHighlighted() {
                            return this.isHighlighted;
                        }
                    };
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        composerImpl.end(false);
        ProvidedValue defaultProvidedValue$runtime_release =
                dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(
                        (SettingsEntry$provideLocalEntryData$1$1) rememberedValue2);
        composerImpl.end(false);
        CompositionLocalKt.CompositionLocalProvider(
                defaultProvidedValue$runtime_release,
                ComposableLambdaKt.rememberComposableLambda(
                        -834207203,
                        new Function2() { // from class:
                                          // com.android.settingslib.spa.framework.common.SettingsEntry$UiLayout$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                Composer composer2 = (Composer) obj2;
                                if ((((Number) obj3).intValue() & 11) == 2) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                SettingsEntry.this.uiLayoutImpl.invoke(bundle4, composer2, 8);
                                return Unit.INSTANCE;
                            }
                        },
                        composerImpl),
                composerImpl,
                56);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.framework.common.SettingsEntry$UiLayout$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj2, Object obj3) {
                            ((Number) obj3).intValue();
                            SettingsEntry.this.UiLayout(
                                    bundle,
                                    (Composer) obj2,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1),
                                    i2);
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SettingsEntry)) {
            return false;
        }
        SettingsEntry settingsEntry = (SettingsEntry) obj;
        return Intrinsics.areEqual(this.id, settingsEntry.id)
                && Intrinsics.areEqual(this.name, settingsEntry.name)
                && Intrinsics.areEqual(this.label, settingsEntry.label)
                && Intrinsics.areEqual(this.owner, settingsEntry.owner)
                && Intrinsics.areEqual(this.fromPage, settingsEntry.fromPage)
                && Intrinsics.areEqual(this.toPage, settingsEntry.toPage)
                && this.isAllowSearch == settingsEntry.isAllowSearch
                && Intrinsics.areEqual(this.statusDataImpl, settingsEntry.statusDataImpl)
                && Intrinsics.areEqual(this.searchDataImpl, settingsEntry.searchDataImpl)
                && Intrinsics.areEqual(this.sliceDataImpl, settingsEntry.sliceDataImpl)
                && Intrinsics.areEqual(this.uiLayoutImpl, settingsEntry.uiLayoutImpl);
    }

    public final int hashCode() {
        int hashCode =
                (this.owner.hashCode()
                                + TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0
                                        .m(
                                                TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0
                                                        .m(this.id.hashCode() * 31, 31, this.name),
                                                31,
                                                this.label))
                        * 31;
        SettingsPage settingsPage = this.fromPage;
        int hashCode2 = (hashCode + (settingsPage == null ? 0 : settingsPage.hashCode())) * 31;
        SettingsPage settingsPage2 = this.toPage;
        return this.uiLayoutImpl.hashCode()
                + ((this.sliceDataImpl.hashCode()
                                + ((this.searchDataImpl.hashCode()
                                                + ((this.statusDataImpl.hashCode()
                                                                + TransitionData$$ExternalSyntheticOutline0
                                                                        .m(
                                                                                TransitionData$$ExternalSyntheticOutline0
                                                                                        .m(
                                                                                                TransitionData$$ExternalSyntheticOutline0
                                                                                                        .m(
                                                                                                                TransitionData$$ExternalSyntheticOutline0
                                                                                                                        .m(
                                                                                                                                (hashCode2
                                                                                                                                                + (settingsPage2
                                                                                                                                                                == null
                                                                                                                                                        ? 0
                                                                                                                                                        : settingsPage2
                                                                                                                                                                .hashCode()))
                                                                                                                                        * 31,
                                                                                                                                31,
                                                                                                                                this
                                                                                                                                        .isAllowSearch),
                                                                                                                31,
                                                                                                                false),
                                                                                                31,
                                                                                                false),
                                                                                31,
                                                                                false))
                                                        * 31))
                                        * 31))
                        * 31);
    }

    public final String toString() {
        return "SettingsEntry(id="
                + this.id
                + ", name="
                + this.name
                + ", label="
                + this.label
                + ", owner="
                + this.owner
                + ", fromPage="
                + this.fromPage
                + ", toPage="
                + this.toPage
                + ", isAllowSearch="
                + this.isAllowSearch
                + ", isSearchDataDynamic=false, hasMutableStatus=false, hasSliceSupport=false,"
                + " statusDataImpl="
                + this.statusDataImpl
                + ", searchDataImpl="
                + this.searchDataImpl
                + ", sliceDataImpl="
                + this.sliceDataImpl
                + ", uiLayoutImpl="
                + this.uiLayoutImpl
                + ")";
    }
}
