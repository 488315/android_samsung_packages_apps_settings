package com.android.settingslib.spaprivileged.template.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.text.BidiFormatter;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.material3.DividerKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;

import com.android.settings.R;
import com.android.settingslib.development.DevelopmentSettingsEnabler;
import com.android.settingslib.spa.framework.theme.SettingsDimension;
import com.android.settingslib.spa.widget.ui.CopyableBodyKt;
import com.android.settingslib.spa.widget.ui.TextKt;

import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppInfoProvider {
    public final PackageInfo packageInfo;

    public AppInfoProvider(PackageInfo packageInfo) {
        Intrinsics.checkNotNullParameter(packageInfo, "packageInfo");
        this.packageInfo = packageInfo;
    }

    public final void AppInfo(boolean z, boolean z2, Composer composer, final int i, final int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(102426634);
        boolean z3 = (i2 & 1) != 0 ? false : z;
        boolean z4 = (i2 & 2) != 0 ? false : z2;
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
        Modifier semantics =
                SemanticsModifierKt.semantics(
                        PaddingKt.m86paddingVpY3zN4(
                                SizeKt.FillWholeMaxWidth,
                                SettingsDimension.itemPaddingStart,
                                SettingsDimension.itemPaddingVertical),
                        true,
                        new Function1() { // from class:
                                          // com.android.settingslib.spaprivileged.template.app.AppInfoProvider$AppInfo$1
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                SemanticsPropertyReceiver semantics2 =
                                        (SemanticsPropertyReceiver) obj;
                                Intrinsics.checkNotNullParameter(semantics2, "$this$semantics");
                                return Unit.INSTANCE;
                            }
                        });
        ColumnMeasurePolicy columnMeasurePolicy =
                ColumnKt.columnMeasurePolicy(
                        Arrangement.Top, Alignment.Companion.CenterHorizontally, composerImpl, 48);
        int i3 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope =
                composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier =
                ComposedModifierKt.materializeModifier(composerImpl, semantics);
        ComposeUiNode.Companion.getClass();
        Function0 function0 = ComposeUiNode.Companion.Constructor;
        boolean z5 = composerImpl.applier instanceof Applier;
        if (!z5) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function0);
        } else {
            composerImpl.useNode();
        }
        Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
        Updater.m221setimpl(composerImpl, columnMeasurePolicy, function2);
        Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
        Updater.m221setimpl(composerImpl, currentCompositionLocalScope, function22);
        Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting
                || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i3))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i3, composerImpl, i3, function23);
        }
        Function2 function24 = ComposeUiNode.Companion.SetModifier;
        Updater.m221setimpl(composerImpl, materializeModifier, function24);
        ApplicationInfo applicationInfo = this.packageInfo.applicationInfo;
        if (applicationInfo == null) {
            throw new IllegalStateException("Required value was null.".toString());
        }
        Modifier m85padding3ABfNKs =
                PaddingKt.m85padding3ABfNKs(companion, SettingsDimension.itemPaddingAround);
        MeasurePolicy maybeCachedBoxMeasurePolicy =
                BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
        int i4 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope2 =
                composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier2 =
                ComposedModifierKt.materializeModifier(composerImpl, m85padding3ABfNKs);
        if (!z5) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function0);
        } else {
            composerImpl.useNode();
        }
        Updater.m221setimpl(composerImpl, maybeCachedBoxMeasurePolicy, function2);
        Updater.m221setimpl(composerImpl, currentCompositionLocalScope2, function22);
        if (composerImpl.inserting
                || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i4))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i4, composerImpl, i4, function23);
        }
        Updater.m221setimpl(composerImpl, materializeModifier2, function24);
        AppInfoKt.m1050AppIconziNgDLE(
                applicationInfo, SettingsDimension.appIconInfoSize, composerImpl, 8);
        composerImpl.end(true);
        AppInfoKt.AppLabel(applicationInfo, z4, composerImpl, (i & 112) | 8, 0);
        InstallType(applicationInfo, composerImpl, 72);
        composerImpl.startReplaceGroup(-1597202299);
        if (z3) {
            AppVersion(composerImpl, 8);
        }
        composerImpl.end(false);
        composerImpl.end(true);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final boolean z6 = z3;
            final boolean z7 = z4;
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.AppInfoProvider$AppInfo$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppInfoProvider.this.AppInfo(
                                    z6,
                                    z7,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1),
                                    i2);
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public final void AppVersion(Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1181906800);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        PackageInfo packageInfo = this.packageInfo;
        Intrinsics.checkNotNullParameter(packageInfo, "<this>");
        String unicodeWrap = BidiFormatter.getInstance().unicodeWrap(packageInfo.versionName);
        if (unicodeWrap == null) {
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settingslib.spaprivileged.template.app.AppInfoProvider$AppVersion$versionName$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                AppInfoProvider.this.AppVersion(
                                        (Composer) obj,
                                        RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                return Unit.INSTANCE;
                            }
                        };
                return;
            }
            return;
        }
        SpacerKt.Spacer(
                composerImpl,
                SizeKt.m91height3ABfNKs(
                        Modifier.Companion.$$INSTANCE, SettingsDimension.paddingSmall));
        TextKt.SettingsBody(unicodeWrap, null, 0, composerImpl, 0, 6);
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.AppInfoProvider$AppVersion$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppInfoProvider.this.AppVersion(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public final void FooterAppVersion(boolean z, Composer composer, final int i, final int i2) {
        final boolean z2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-208425715);
        int i3 = i2 & 1;
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (i3 != 0) {
            composerImpl.startReplaceGroup(66341403);
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Context context =
                    (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            composerImpl.startReplaceGroup(1398359712);
            Object rememberedValue = composerImpl.rememberedValue();
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue =
                        Boolean.valueOf(
                                DevelopmentSettingsEnabler.isDevelopmentSettingsEnabled(context));
                composerImpl.updateRememberedValue(rememberedValue);
            }
            boolean booleanValue = ((Boolean) rememberedValue).booleanValue();
            composerImpl.end(false);
            composerImpl.end(false);
            z2 = booleanValue;
        } else {
            z2 = z;
        }
        OpaqueKey opaqueKey2 = ComposerKt.invocation;
        Context context2 =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        PackageInfo packageInfo = this.packageInfo;
        composerImpl.startReplaceGroup(-1080181746);
        boolean changed =
                composerImpl.changed(packageInfo)
                        | ((((i & 14) ^ 6) > 4 && composerImpl.changed(z2)) || (i & 6) == 4);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (changed || rememberedValue2 == composer$Companion$Empty$1) {
            ArrayList arrayList = new ArrayList();
            PackageInfo packageInfo2 = this.packageInfo;
            Intrinsics.checkNotNullParameter(packageInfo2, "<this>");
            String unicodeWrap = BidiFormatter.getInstance().unicodeWrap(packageInfo2.versionName);
            if (unicodeWrap != null) {
                arrayList.add(context2.getString(R.string.version_text, unicodeWrap));
            }
            if (z2) {
                arrayList.add(this.packageInfo.packageName);
            }
            String lineSeparator = System.lineSeparator();
            Intrinsics.checkNotNullExpressionValue(lineSeparator, "lineSeparator(...)");
            rememberedValue2 =
                    CollectionsKt___CollectionsKt.joinToString$default(
                            arrayList, lineSeparator, null, null, null, 62);
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        String str = (String) rememberedValue2;
        composerImpl.end(false);
        if (StringsKt__StringsJVMKt.isBlank(str)) {
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settingslib.spaprivileged.template.app.AppInfoProvider$FooterAppVersion$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                AppInfoProvider.this.FooterAppVersion(
                                        z2,
                                        (Composer) obj,
                                        RecomposeScopeImplKt.updateChangedFlags(i | 1),
                                        i2);
                                return Unit.INSTANCE;
                            }
                        };
                return;
            }
            return;
        }
        DividerKt.m184HorizontalDivider9IZ8Weo(null, 0.0f, 0L, composerImpl, 0, 7);
        Modifier padding =
                PaddingKt.padding(Modifier.Companion.$$INSTANCE, SettingsDimension.itemPadding);
        ColumnMeasurePolicy columnMeasurePolicy =
                ColumnKt.columnMeasurePolicy(
                        Arrangement.Top, Alignment.Companion.Start, composerImpl, 0);
        int i4 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope =
                composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier =
                ComposedModifierKt.materializeModifier(composerImpl, padding);
        ComposeUiNode.Companion.getClass();
        Function0 function0 = ComposeUiNode.Companion.Constructor;
        if (!(composerImpl.applier instanceof Applier)) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function0);
        } else {
            composerImpl.useNode();
        }
        Updater.m221setimpl(
                composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m221setimpl(
                composerImpl,
                currentCompositionLocalScope,
                ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting
                || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i4))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i4, composerImpl, i4, function2);
        }
        Updater.m221setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        CopyableBodyKt.CopyableBody(str, composerImpl, 0);
        composerImpl.end(true);
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.AppInfoProvider$FooterAppVersion$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppInfoProvider.this.FooterAppVersion(
                                    z2,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1),
                                    i2);
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public final void InstallType(
            final ApplicationInfo applicationInfo, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1513479082);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        if (!applicationInfo.isInstantApp()) {
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settingslib.spaprivileged.template.app.AppInfoProvider$InstallType$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                AppInfoProvider.this.InstallType(
                                        applicationInfo,
                                        (Composer) obj,
                                        RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                return Unit.INSTANCE;
                            }
                        };
                return;
            }
            return;
        }
        SpacerKt.Spacer(
                composerImpl,
                SizeKt.m91height3ABfNKs(
                        Modifier.Companion.$$INSTANCE, SettingsDimension.paddingSmall));
        TextKt.SettingsBody(
                StringResources_androidKt.stringResource(
                        composerImpl, R.string.install_type_instant),
                null,
                0,
                composerImpl,
                0,
                6);
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.AppInfoProvider$InstallType$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppInfoProvider.this.InstallType(
                                    applicationInfo,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
