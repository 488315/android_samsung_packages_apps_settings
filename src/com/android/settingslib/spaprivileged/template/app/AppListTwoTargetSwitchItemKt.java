package com.android.settingslib.spaprivileged.template.app;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;

import com.android.settingslib.spa.framework.theme.SettingsDimension;
import com.android.settingslib.spa.widget.preference.SwitchPreferenceModel;
import com.android.settingslib.spa.widget.preference.TwoTargetSwitchPreferenceKt;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppListTwoTargetSwitchItemKt {
    public static final void AppListTwoTargetSwitchItem(
            final AppListItemModel appListItemModel,
            final Function0 onClick,
            final Function0 checked,
            final Function0 changeable,
            final Function1 function1,
            Composer composer,
            final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(appListItemModel, "<this>");
        Intrinsics.checkNotNullParameter(onClick, "onClick");
        Intrinsics.checkNotNullParameter(checked, "checked");
        Intrinsics.checkNotNullParameter(changeable, "changeable");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1579102446);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(appListItemModel) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changedInstance(onClick) ? 32 : 16;
        }
        if ((i & 896) == 0) {
            i2 |= composerImpl.changedInstance(checked) ? 256 : 128;
        }
        if ((i & 7168) == 0) {
            i2 |= composerImpl.changedInstance(changeable) ? 2048 : 1024;
        }
        if ((57344 & i) == 0) {
            i2 |=
                    composerImpl.changedInstance(function1)
                            ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT
                            : 8192;
        }
        if ((46811 & i2) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            TwoTargetSwitchPreferenceKt.TwoTargetSwitchPreference(
                    new SwitchPreferenceModel(
                            appListItemModel,
                            checked,
                            changeable,
                            function1) { // from class:
                                         // com.android.settingslib.spaprivileged.template.app.AppListTwoTargetSwitchItemKt$AppListTwoTargetSwitchItem$1
                        public final Function0 changeable;
                        public final Function0 checked;
                        public final ComposableLambdaImpl icon;
                        public final Function1 onCheckedChange;
                        public final Function0 summary;
                        public final String title;

                        {
                            this.title = appListItemModel.label;
                            this.summary = appListItemModel.summary;
                            this.icon =
                                    new ComposableLambdaImpl(
                                            2045266372,
                                            true,
                                            new Function2() { // from class:
                                                              // com.android.settingslib.spaprivileged.template.app.AppListTwoTargetSwitchItemKt$AppListTwoTargetSwitchItem$1$icon$1
                                                {
                                                    super(2);
                                                }

                                                @Override // kotlin.jvm.functions.Function2
                                                public final Object invoke(
                                                        Object obj, Object obj2) {
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
                                                    AppInfoKt.m1050AppIconziNgDLE(
                                                            AppListItemModel.this.record.getApp(),
                                                            SettingsDimension.appIconItemSize,
                                                            composer2,
                                                            8);
                                                    return Unit.INSTANCE;
                                                }
                                            });
                            this.checked = checked;
                            this.changeable = changeable;
                            this.onCheckedChange = function1;
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
                        public final Function2 getIcon() {
                            return this.icon;
                        }

                        @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                        public final Function1 getOnCheckedChange() {
                            return this.onCheckedChange;
                        }

                        @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                        public final Function0 getSummary() {
                            return this.summary;
                        }

                        @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
                        public final String getTitle() {
                            return this.title;
                        }
                    },
                    null,
                    onClick,
                    composerImpl,
                    (i2 << 3) & 896,
                    2);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.AppListTwoTargetSwitchItemKt$AppListTwoTargetSwitchItem$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppListTwoTargetSwitchItemKt.AppListTwoTargetSwitchItem(
                                    AppListItemModel.this,
                                    onClick,
                                    checked,
                                    changeable,
                                    function1,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
