package com.android.settingslib.spaprivileged.template.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.lifecycle.compose.FlowExtKt;

import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.settingslib.spa.widget.preference.SwitchPreferenceModel;
import com.android.settingslib.spa.widget.ui.AnnotatedTextKt;
import com.android.settingslib.spaprivileged.model.app.AppRecord;
import com.android.settingslib.spaprivileged.model.app.IPackageManagers;
import com.android.settingslib.spaprivileged.model.app.PackageManagers;
import com.android.settingslib.spaprivileged.model.enterprise.EnhancedConfirmation;
import com.android.settingslib.spaprivileged.model.enterprise.Restrictions;
import com.android.settingslib.spaprivileged.template.preference.RestrictedSwitchPreferenceKt;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class TogglePermissionAppInfoPageKt {
    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.settingslib.spaprivileged.template.app.TogglePermissionAppInfoPageKt$TogglePermissionAppInfoPage$2, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r6v4, types: [com.android.settingslib.spaprivileged.template.app.TogglePermissionAppInfoPageKt$TogglePermissionAppInfoPage$3, kotlin.jvm.internal.Lambda] */
    public static final <T extends AppRecord> void TogglePermissionAppInfoPage(
            final TogglePermissionAppListModel togglePermissionAppListModel,
            final String packageName,
            final int i,
            IPackageManagers iPackageManagers,
            Function2 function2,
            Composer composer,
            final int i2,
            final int i3) {
        int i4;
        IPackageManagers iPackageManagers2;
        final Function2 function22;
        final Function2 function23;
        final IPackageManagers iPackageManagers3;
        int i5;
        int i6;
        Intrinsics.checkNotNullParameter(togglePermissionAppListModel, "<this>");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2076458566);
        if ((i3 & Integer.MIN_VALUE) != 0) {
            i4 = i2 | 6;
        } else if ((i2 & 14) == 0) {
            i4 = (composerImpl.changed(togglePermissionAppListModel) ? 4 : 2) | i2;
        } else {
            i4 = i2;
        }
        if ((i3 & 1) != 0) {
            i4 |= 48;
        } else if ((i2 & 112) == 0) {
            i4 |= composerImpl.changed(packageName) ? 32 : 16;
        }
        if ((i3 & 2) != 0) {
            i4 |= 384;
        } else if ((i2 & 896) == 0) {
            i4 |= composerImpl.changed(i) ? 256 : 128;
        }
        if ((i2 & 7168) == 0) {
            if ((i3 & 4) == 0) {
                iPackageManagers2 = iPackageManagers;
                if (composerImpl.changed(iPackageManagers2)) {
                    i6 = 2048;
                    i4 |= i6;
                }
            } else {
                iPackageManagers2 = iPackageManagers;
            }
            i6 = 1024;
            i4 |= i6;
        } else {
            iPackageManagers2 = iPackageManagers;
        }
        if ((i2 & 57344) == 0) {
            if ((i3 & 8) == 0) {
                function22 = function2;
                if (composerImpl.changedInstance(function22)) {
                    i5 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                    i4 |= i5;
                }
            } else {
                function22 = function2;
            }
            i5 = 8192;
            i4 |= i5;
        } else {
            function22 = function2;
        }
        if ((46811 & i4) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            iPackageManagers3 = iPackageManagers2;
        } else {
            composerImpl.startDefaults();
            if ((i2 & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                if ((i3 & 4) != 0) {
                    iPackageManagers2 = PackageManagers.INSTANCE;
                    i4 &= -7169;
                }
                if ((i3 & 8) != 0) {
                    i4 &= -57345;
                    function23 =
                            TogglePermissionAppInfoPageKt$TogglePermissionAppInfoPage$1.INSTANCE;
                    composerImpl.endDefaults();
                    OpaqueKey opaqueKey = ComposerKt.invocation;
                    AppInfoPageKt.AppInfoPage(
                            StringResources_androidKt.stringResource(
                                    composerImpl, togglePermissionAppListModel.getPageTitleResId()),
                            packageName,
                            i,
                            ComposableLambdaKt.rememberComposableLambda(
                                    -1014843018,
                                    new Function2() { // from class:
                                                      // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppInfoPageKt$TogglePermissionAppInfoPage$2
                                        {
                                            super(2);
                                        }

                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            Composer composer2 = (Composer) obj;
                                            if ((((Number) obj2).intValue() & 11) == 2) {
                                                ComposerImpl composerImpl2 =
                                                        (ComposerImpl) composer2;
                                                if (composerImpl2.getSkipping()) {
                                                    composerImpl2.skipToGroupEnd();
                                                    return Unit.INSTANCE;
                                                }
                                            }
                                            OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                            AnnotatedTextKt.AnnotatedText(
                                                    TogglePermissionAppListModel.this
                                                            .getFooterResId(),
                                                    composer2,
                                                    0);
                                            return Unit.INSTANCE;
                                        }
                                    },
                                    composerImpl),
                            iPackageManagers2,
                            ComposableLambdaKt.rememberComposableLambda(
                                    -465871746,
                                    new Function3() { // from class:
                                                      // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppInfoPageKt$TogglePermissionAppInfoPage$3
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(3);
                                        }

                                        @Override // kotlin.jvm.functions.Function3
                                        public final Object invoke(
                                                Object obj, Object obj2, Object obj3) {
                                            PackageInfo AppInfoPage = (PackageInfo) obj;
                                            final Composer composer2 = (Composer) obj2;
                                            ((Number) obj3).intValue();
                                            Intrinsics.checkNotNullParameter(
                                                    AppInfoPage, "$this$AppInfoPage");
                                            OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                            ApplicationInfo applicationInfo =
                                                    AppInfoPage.applicationInfo;
                                            if (applicationInfo != null) {
                                                TogglePermissionAppListModel
                                                        togglePermissionAppListModel2 =
                                                                TogglePermissionAppListModel.this;
                                                ComposerImpl composerImpl2 =
                                                        (ComposerImpl) composer2;
                                                composerImpl2.startReplaceGroup(1460287419);
                                                composerImpl2.startReplaceGroup(1398356401);
                                                boolean changed =
                                                        composerImpl2.changed(applicationInfo);
                                                Object rememberedValue =
                                                        composerImpl2.rememberedValue();
                                                Composer$Companion$Empty$1
                                                        composer$Companion$Empty$1 =
                                                                Composer.Companion.Empty;
                                                if (changed
                                                        || rememberedValue
                                                                == composer$Companion$Empty$1) {
                                                    rememberedValue =
                                                            FlowKt.flowOn(
                                                                    new SafeFlow(
                                                                            new TogglePermissionAppInfoPageKt$rememberRecord$1$1(
                                                                                    togglePermissionAppListModel2,
                                                                                    applicationInfo,
                                                                                    null)),
                                                                    Dispatchers.Default);
                                                    composerImpl2.updateRememberedValue(
                                                            rememberedValue);
                                                }
                                                composerImpl2.end(false);
                                                MutableState collectAsStateWithLifecycle =
                                                        FlowExtKt.collectAsStateWithLifecycle(
                                                                (Flow) rememberedValue,
                                                                null,
                                                                composerImpl2,
                                                                56);
                                                composerImpl2.end(false);
                                                final AppRecord appRecord =
                                                        (AppRecord)
                                                                collectAsStateWithLifecycle
                                                                        .getValue();
                                                if (appRecord != null) {
                                                    final Function0 isAllowed =
                                                            TogglePermissionAppListModel.this
                                                                    .isAllowed(
                                                                            appRecord, composer2);
                                                    TogglePermissionAppListModel
                                                            togglePermissionAppListModel3 =
                                                                    TogglePermissionAppListModel
                                                                            .this;
                                                    composerImpl2.startReplaceGroup(-1820068862);
                                                    composerImpl2.startReplaceGroup(249322719);
                                                    boolean changed2 =
                                                            composerImpl2.changed(appRecord);
                                                    Object rememberedValue2 =
                                                            composerImpl2.rememberedValue();
                                                    if (changed2
                                                            || rememberedValue2
                                                                    == composer$Companion$Empty$1) {
                                                        rememberedValue2 =
                                                                FlowKt.flowOn(
                                                                        new SafeFlow(
                                                                                new TogglePermissionAppInfoPageKt$rememberIsChangeable$1$1(
                                                                                        togglePermissionAppListModel3,
                                                                                        appRecord,
                                                                                        null)),
                                                                        Dispatchers.Default);
                                                        composerImpl2.updateRememberedValue(
                                                                rememberedValue2);
                                                    }
                                                    composerImpl2.end(false);
                                                    final MutableState
                                                            collectAsStateWithLifecycle2 =
                                                                    FlowExtKt
                                                                            .collectAsStateWithLifecycle(
                                                                                    (Flow)
                                                                                            rememberedValue2,
                                                                                    Boolean.FALSE,
                                                                                    composerImpl2,
                                                                                    56);
                                                    composerImpl2.end(false);
                                                    final TogglePermissionAppListModel
                                                            togglePermissionAppListModel4 =
                                                                    TogglePermissionAppListModel
                                                                            .this;
                                                    SwitchPreferenceModel switchPreferenceModel =
                                                            new SwitchPreferenceModel(
                                                                    togglePermissionAppListModel4,
                                                                    composer2,
                                                                    isAllowed,
                                                                    collectAsStateWithLifecycle2,
                                                                    appRecord) { // from class:
                                                                                 // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppInfoPageKt$TogglePermissionAppInfoPage$3$switchModel$1
                                                                public final Function0 changeable;
                                                                public final Function0 checked;
                                                                public final Function1
                                                                        onCheckedChange;
                                                                public final String title;

                                                                {
                                                                    this.title =
                                                                            StringResources_androidKt
                                                                                    .stringResource(
                                                                                            composer2,
                                                                                            togglePermissionAppListModel4
                                                                                                    .getSwitchTitleResId());
                                                                    this.checked = isAllowed;
                                                                    this.changeable =
                                                                            new Function0() { // from class: com.android.settingslib.spaprivileged.template.app.TogglePermissionAppInfoPageKt$TogglePermissionAppInfoPage$3$switchModel$1$changeable$1
                                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                {
                                                                                    super(0);
                                                                                }

                                                                                @Override // kotlin.jvm.functions.Function0
                                                                                /* renamed from: invoke */
                                                                                public final Object
                                                                                        mo1068invoke() {
                                                                                    Boolean bool =
                                                                                            (Boolean)
                                                                                                    collectAsStateWithLifecycle2
                                                                                                            .getValue();
                                                                                    bool
                                                                                            .booleanValue();
                                                                                    return bool;
                                                                                }
                                                                            };
                                                                    this.onCheckedChange =
                                                                            new Function1() { // from class: com.android.settingslib.spaprivileged.template.app.TogglePermissionAppInfoPageKt$TogglePermissionAppInfoPage$3$switchModel$1$onCheckedChange$1
                                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                {
                                                                                    super(1);
                                                                                }

                                                                                @Override // kotlin.jvm.functions.Function1
                                                                                public final Object
                                                                                        invoke(
                                                                                                Object
                                                                                                        obj4) {
                                                                                    TogglePermissionAppListModel
                                                                                            .this
                                                                                            .setAllowed(
                                                                                                    appRecord,
                                                                                                    ((Boolean)
                                                                                                                    obj4)
                                                                                                            .booleanValue());
                                                                                    return Unit
                                                                                            .INSTANCE;
                                                                                }
                                                                            };
                                                                }

                                                                @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                                                                public final Function0
                                                                        getChangeable() {
                                                                    return this.changeable;
                                                                }

                                                                @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                                                                public final Function0
                                                                        getChecked() {
                                                                    return this.checked;
                                                                }

                                                                @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                                                                public final Function1
                                                                        getOnCheckedChange() {
                                                                    return this.onCheckedChange;
                                                                }

                                                                @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                                                                public final String getTitle() {
                                                                    return this.title;
                                                                }
                                                            };
                                                    int i7 = i;
                                                    List switchRestrictionKeys =
                                                            TogglePermissionAppListModel.this
                                                                    .getSwitchRestrictionKeys();
                                                    String enhancedConfirmationKey =
                                                            TogglePermissionAppListModel.this
                                                                    .getEnhancedConfirmationKey();
                                                    RestrictedSwitchPreferenceKt
                                                            .RestrictedSwitchPreference(
                                                                    switchPreferenceModel,
                                                                    new Restrictions(
                                                                            i7,
                                                                            switchRestrictionKeys,
                                                                            enhancedConfirmationKey
                                                                                            != null
                                                                                    ? new EnhancedConfirmation(
                                                                                            enhancedConfirmationKey,
                                                                                            packageName)
                                                                                    : null),
                                                                    function23,
                                                                    composer2,
                                                                    64);
                                                    TogglePermissionAppListModel.this
                                                            .InfoPageAdditionalContent(
                                                                    appRecord, isAllowed, composer2,
                                                                    0);
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    },
                                    composerImpl),
                            composerImpl,
                            199680 | (i4 & 112) | (i4 & 896) | ((i4 << 3) & 57344));
                    iPackageManagers3 = iPackageManagers2;
                    function22 = function23;
                }
            } else {
                composerImpl.skipToGroupEnd();
                if ((i3 & 4) != 0) {
                    i4 &= -7169;
                }
                if ((i3 & 8) != 0) {
                    i4 &= -57345;
                }
            }
            function23 = function22;
            composerImpl.endDefaults();
            OpaqueKey opaqueKey2 = ComposerKt.invocation;
            AppInfoPageKt.AppInfoPage(
                    StringResources_androidKt.stringResource(
                            composerImpl, togglePermissionAppListModel.getPageTitleResId()),
                    packageName,
                    i,
                    ComposableLambdaKt.rememberComposableLambda(
                            -1014843018,
                            new Function2() { // from class:
                                              // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppInfoPageKt$TogglePermissionAppInfoPage$2
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    Composer composer2 = (Composer) obj;
                                    if ((((Number) obj2).intValue() & 11) == 2) {
                                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                        if (composerImpl2.getSkipping()) {
                                            composerImpl2.skipToGroupEnd();
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    OpaqueKey opaqueKey22 = ComposerKt.invocation;
                                    AnnotatedTextKt.AnnotatedText(
                                            TogglePermissionAppListModel.this.getFooterResId(),
                                            composer2,
                                            0);
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl),
                    iPackageManagers2,
                    ComposableLambdaKt.rememberComposableLambda(
                            -465871746,
                            new Function3() { // from class:
                                              // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppInfoPageKt$TogglePermissionAppInfoPage$3
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(3);
                                }

                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj, Object obj2, Object obj3) {
                                    PackageInfo AppInfoPage = (PackageInfo) obj;
                                    final Composer composer2 = (Composer) obj2;
                                    ((Number) obj3).intValue();
                                    Intrinsics.checkNotNullParameter(
                                            AppInfoPage, "$this$AppInfoPage");
                                    OpaqueKey opaqueKey22 = ComposerKt.invocation;
                                    ApplicationInfo applicationInfo = AppInfoPage.applicationInfo;
                                    if (applicationInfo != null) {
                                        TogglePermissionAppListModel togglePermissionAppListModel2 =
                                                TogglePermissionAppListModel.this;
                                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                        composerImpl2.startReplaceGroup(1460287419);
                                        composerImpl2.startReplaceGroup(1398356401);
                                        boolean changed = composerImpl2.changed(applicationInfo);
                                        Object rememberedValue = composerImpl2.rememberedValue();
                                        Composer$Companion$Empty$1 composer$Companion$Empty$1 =
                                                Composer.Companion.Empty;
                                        if (changed
                                                || rememberedValue == composer$Companion$Empty$1) {
                                            rememberedValue =
                                                    FlowKt.flowOn(
                                                            new SafeFlow(
                                                                    new TogglePermissionAppInfoPageKt$rememberRecord$1$1(
                                                                            togglePermissionAppListModel2,
                                                                            applicationInfo,
                                                                            null)),
                                                            Dispatchers.Default);
                                            composerImpl2.updateRememberedValue(rememberedValue);
                                        }
                                        composerImpl2.end(false);
                                        MutableState collectAsStateWithLifecycle =
                                                FlowExtKt.collectAsStateWithLifecycle(
                                                        (Flow) rememberedValue,
                                                        null,
                                                        composerImpl2,
                                                        56);
                                        composerImpl2.end(false);
                                        final AppRecord appRecord =
                                                (AppRecord) collectAsStateWithLifecycle.getValue();
                                        if (appRecord != null) {
                                            final Function0 isAllowed =
                                                    TogglePermissionAppListModel.this.isAllowed(
                                                            appRecord, composer2);
                                            TogglePermissionAppListModel
                                                    togglePermissionAppListModel3 =
                                                            TogglePermissionAppListModel.this;
                                            composerImpl2.startReplaceGroup(-1820068862);
                                            composerImpl2.startReplaceGroup(249322719);
                                            boolean changed2 = composerImpl2.changed(appRecord);
                                            Object rememberedValue2 =
                                                    composerImpl2.rememberedValue();
                                            if (changed2
                                                    || rememberedValue2
                                                            == composer$Companion$Empty$1) {
                                                rememberedValue2 =
                                                        FlowKt.flowOn(
                                                                new SafeFlow(
                                                                        new TogglePermissionAppInfoPageKt$rememberIsChangeable$1$1(
                                                                                togglePermissionAppListModel3,
                                                                                appRecord,
                                                                                null)),
                                                                Dispatchers.Default);
                                                composerImpl2.updateRememberedValue(
                                                        rememberedValue2);
                                            }
                                            composerImpl2.end(false);
                                            final MutableState collectAsStateWithLifecycle2 =
                                                    FlowExtKt.collectAsStateWithLifecycle(
                                                            (Flow) rememberedValue2,
                                                            Boolean.FALSE,
                                                            composerImpl2,
                                                            56);
                                            composerImpl2.end(false);
                                            final TogglePermissionAppListModel
                                                    togglePermissionAppListModel4 =
                                                            TogglePermissionAppListModel.this;
                                            SwitchPreferenceModel switchPreferenceModel =
                                                    new SwitchPreferenceModel(
                                                            togglePermissionAppListModel4,
                                                            composer2,
                                                            isAllowed,
                                                            collectAsStateWithLifecycle2,
                                                            appRecord) { // from class:
                                                                         // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppInfoPageKt$TogglePermissionAppInfoPage$3$switchModel$1
                                                        public final Function0 changeable;
                                                        public final Function0 checked;
                                                        public final Function1 onCheckedChange;
                                                        public final String title;

                                                        {
                                                            this.title =
                                                                    StringResources_androidKt
                                                                            .stringResource(
                                                                                    composer2,
                                                                                    togglePermissionAppListModel4
                                                                                            .getSwitchTitleResId());
                                                            this.checked = isAllowed;
                                                            this.changeable =
                                                                    new Function0() { // from class:
                                                                                      // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppInfoPageKt$TogglePermissionAppInfoPage$3$switchModel$1$changeable$1
                                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                        {
                                                                            super(0);
                                                                        }

                                                                        @Override // kotlin.jvm.functions.Function0
                                                                        /* renamed from: invoke */
                                                                        public final Object
                                                                                mo1068invoke() {
                                                                            Boolean bool =
                                                                                    (Boolean)
                                                                                            collectAsStateWithLifecycle2
                                                                                                    .getValue();
                                                                            bool.booleanValue();
                                                                            return bool;
                                                                        }
                                                                    };
                                                            this.onCheckedChange =
                                                                    new Function1() { // from class:
                                                                                      // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppInfoPageKt$TogglePermissionAppInfoPage$3$switchModel$1$onCheckedChange$1
                                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                        {
                                                                            super(1);
                                                                        }

                                                                        @Override // kotlin.jvm.functions.Function1
                                                                        public final Object invoke(
                                                                                Object obj4) {
                                                                            TogglePermissionAppListModel
                                                                                    .this
                                                                                    .setAllowed(
                                                                                            appRecord,
                                                                                            ((Boolean)
                                                                                                            obj4)
                                                                                                    .booleanValue());
                                                                            return Unit.INSTANCE;
                                                                        }
                                                                    };
                                                        }

                                                        @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                                                        public final Function0 getChangeable() {
                                                            return this.changeable;
                                                        }

                                                        @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                                                        public final Function0 getChecked() {
                                                            return this.checked;
                                                        }

                                                        @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                                                        public final Function1
                                                                getOnCheckedChange() {
                                                            return this.onCheckedChange;
                                                        }

                                                        @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                                                        public final String getTitle() {
                                                            return this.title;
                                                        }
                                                    };
                                            int i7 = i;
                                            List switchRestrictionKeys =
                                                    TogglePermissionAppListModel.this
                                                            .getSwitchRestrictionKeys();
                                            String enhancedConfirmationKey =
                                                    TogglePermissionAppListModel.this
                                                            .getEnhancedConfirmationKey();
                                            RestrictedSwitchPreferenceKt.RestrictedSwitchPreference(
                                                    switchPreferenceModel,
                                                    new Restrictions(
                                                            i7,
                                                            switchRestrictionKeys,
                                                            enhancedConfirmationKey != null
                                                                    ? new EnhancedConfirmation(
                                                                            enhancedConfirmationKey,
                                                                            packageName)
                                                                    : null),
                                                    function23,
                                                    composer2,
                                                    64);
                                            TogglePermissionAppListModel.this
                                                    .InfoPageAdditionalContent(
                                                            appRecord, isAllowed, composer2, 0);
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl),
                    composerImpl,
                    199680 | (i4 & 112) | (i4 & 896) | ((i4 << 3) & 57344));
            iPackageManagers3 = iPackageManagers2;
            function22 = function23;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppInfoPageKt$TogglePermissionAppInfoPage$4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            TogglePermissionAppInfoPageKt.TogglePermissionAppInfoPage(
                                    TogglePermissionAppListModel.this,
                                    packageName,
                                    i,
                                    iPackageManagers3,
                                    function22,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i2 | 1),
                                    i3);
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void TogglePermissionAppInfoPageEntryItem(
            final TogglePermissionAppListModel togglePermissionAppListModel,
            final String permissionType,
            final ApplicationInfo app,
            Composer composer,
            final int i) {
        Intrinsics.checkNotNullParameter(togglePermissionAppListModel, "<this>");
        Intrinsics.checkNotNullParameter(permissionType, "permissionType");
        Intrinsics.checkNotNullParameter(app, "app");
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(28379403);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        composerImpl.startReplaceGroup(-99984118);
        Object rememberedValue = composerImpl.rememberedValue();
        Object obj = Composer.Companion.Empty;
        if (rememberedValue == obj) {
            rememberedValue = togglePermissionAppListModel.transformItem(app);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        final AppRecord record = (AppRecord) rememberedValue;
        composerImpl.end(false);
        composerImpl.startReplaceGroup(-99984077);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (rememberedValue2 == obj) {
            Intrinsics.checkNotNullParameter(record, "record");
            rememberedValue2 =
                    Boolean.valueOf(
                            !TogglePermissionAppListKt.isSystemOrRootUid(record)
                                    && togglePermissionAppListModel.isChangeable(record));
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        boolean booleanValue = ((Boolean) rememberedValue2).booleanValue();
        composerImpl.end(false);
        if (!booleanValue) {
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppInfoPageKt$TogglePermissionAppInfoPageEntryItem$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) {
                                ((Number) obj3).intValue();
                                TogglePermissionAppInfoPageKt.TogglePermissionAppInfoPageEntryItem(
                                        TogglePermissionAppListModel.this,
                                        permissionType,
                                        app,
                                        (Composer) obj2,
                                        RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                return Unit.INSTANCE;
                            }
                        };
                return;
            }
            return;
        }
        Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        composerImpl.startReplaceGroup(-99983950);
        Object rememberedValue3 = composerImpl.rememberedValue();
        if (rememberedValue3 == obj) {
            rememberedValue3 =
                    new TogglePermissionInternalAppListModel(
                            context,
                            permissionType,
                            togglePermissionAppListModel,
                            TogglePermissionAppInfoPageKt$TogglePermissionAppInfoPageEntryItem$internalListModel$1$1
                                    .INSTANCE);
            composerImpl.updateRememberedValue(rememberedValue3);
        }
        final TogglePermissionInternalAppListModel togglePermissionInternalAppListModel =
                (TogglePermissionInternalAppListModel) rememberedValue3;
        composerImpl.end(false);
        PreferenceKt.Preference(
                new PreferenceModel(
                        togglePermissionAppListModel,
                        composerImpl,
                        togglePermissionInternalAppListModel,
                        record,
                        permissionType,
                        app,
                        i) { // from class:
                             // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppInfoPageKt$TogglePermissionAppInfoPageEntryItem$3
                    public final Lambda onClick;
                    public final Function0 summary;
                    public final String title;

                    {
                        this.title =
                                StringResources_androidKt.stringResource(
                                        composerImpl,
                                        togglePermissionAppListModel.getPageTitleResId());
                        this.summary =
                                togglePermissionInternalAppListModel.getSummary(
                                        record, composerImpl, 64);
                        List list = TogglePermissionAppInfoPageProvider.PAGE_PARAMETER;
                        this.onClick =
                                (Lambda)
                                        TogglePermissionAppInfoPageProvider.Companion.navigator(
                                                permissionType, app, composerImpl);
                    }

                    /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.Lambda] */
                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function0 getOnClick() {
                        return this.onClick;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function0 getSummary() {
                        return this.summary;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final String getTitle() {
                        return this.title;
                    }
                },
                false,
                composerImpl,
                0,
                2);
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppInfoPageKt$TogglePermissionAppInfoPageEntryItem$4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj2, Object obj3) {
                            ((Number) obj3).intValue();
                            TogglePermissionAppInfoPageKt.TogglePermissionAppInfoPageEntryItem(
                                    TogglePermissionAppListModel.this,
                                    permissionType,
                                    app,
                                    (Composer) obj2,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
