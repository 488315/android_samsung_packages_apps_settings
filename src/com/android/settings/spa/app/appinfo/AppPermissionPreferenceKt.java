package com.android.settings.spa.app.appinfo;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.lifecycle.compose.FlowExtKt;

import com.android.settings.R;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.settingslib.spaprivileged.framework.compose.StringResourcesKt;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppPermissionPreferenceKt {
    public static final void AppPermissionPreference(
            final ApplicationInfo app,
            final Flow flow,
            Composer composer,
            final int i,
            final int i2) {
        Intrinsics.checkNotNullParameter(app, "app");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(107068433);
        int i3 = i2 & 2;
        Object obj = Composer.Companion.Empty;
        if (i3 != 0) {
            composerImpl.startReplaceGroup(2039035430);
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Context context =
                    (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            composerImpl.startReplaceGroup(416435980);
            boolean changed = composerImpl.changed(app);
            Object rememberedValue = composerImpl.rememberedValue();
            if (changed || rememberedValue == obj) {
                rememberedValue = new AppPermissionSummaryRepository(context, app).flow;
                composerImpl.updateRememberedValue(rememberedValue);
            }
            flow = (Flow) rememberedValue;
            composerImpl.end(false);
            composerImpl.end(false);
        }
        OpaqueKey opaqueKey2 = ComposerKt.invocation;
        final Context context2 =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        final MutableState collectAsStateWithLifecycle =
                FlowExtKt.collectAsStateWithLifecycle(
                        flow,
                        new AppPermissionSummaryState(
                                StringResourcesKt.placeholder(composerImpl), false),
                        composerImpl,
                        8);
        composerImpl.startReplaceGroup(161373570);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (rememberedValue2 == obj) {
            rememberedValue2 =
                    new PreferenceModel(context2, app, collectAsStateWithLifecycle) { // from class:
                        // com.android.settings.spa.app.appinfo.AppPermissionPreferenceKt$AppPermissionPreference$1$1
                        public final Function0 enabled;
                        public final Function0 onClick;
                        public final Function0 summary;
                        public final String title;

                        {
                            String string = context2.getString(R.string.permissions_label);
                            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                            this.title = string;
                            this.summary =
                                    new Function0() { // from class:
                                        // com.android.settings.spa.app.appinfo.AppPermissionPreferenceKt$AppPermissionPreference$1$1$summary$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        /* renamed from: invoke */
                                        public final Object mo1068invoke() {
                                            return ((AppPermissionSummaryState)
                                                            collectAsStateWithLifecycle.getValue())
                                                    .summary;
                                        }
                                    };
                            this.enabled =
                                    new Function0() { // from class:
                                        // com.android.settings.spa.app.appinfo.AppPermissionPreferenceKt$AppPermissionPreference$1$1$enabled$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        /* renamed from: invoke */
                                        public final Object mo1068invoke() {
                                            return Boolean.valueOf(
                                                    ((AppPermissionSummaryState)
                                                                    collectAsStateWithLifecycle
                                                                            .getValue())
                                                            .enabled);
                                        }
                                    };
                            this.onClick =
                                    new Function0() { // from class:
                                        // com.android.settings.spa.app.appinfo.AppPermissionPreferenceKt$AppPermissionPreference$1$1$onClick$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        /* renamed from: invoke */
                                        public final Object mo1068invoke() {
                                            Context context3 = context2;
                                            ApplicationInfo applicationInfo = app;
                                            Intent intent =
                                                    new Intent(
                                                            "android.intent.action.MANAGE_APP_PERMISSIONS");
                                            intent.putExtra(
                                                    "android.intent.extra.PACKAGE_NAME",
                                                    applicationInfo.packageName);
                                            intent.putExtra("hideInfoButton", true);
                                            try {
                                                context3.startActivityAsUser(
                                                        intent,
                                                        ApplicationInfosKt.getUserHandle(
                                                                applicationInfo));
                                            } catch (ActivityNotFoundException unused) {
                                                Log.w(
                                                        "AppPermissionPreference",
                                                        "No app can handle"
                                                            + " android.intent.action.MANAGE_APP_PERMISSIONS");
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    };
                        }

                        @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                        public final Function0 getEnabled() {
                            return this.enabled;
                        }

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
                    };
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        composerImpl.end(false);
        PreferenceKt.Preference(
                (AppPermissionPreferenceKt$AppPermissionPreference$1$1) rememberedValue2,
                true,
                composerImpl,
                54,
                0);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.appinfo.AppPermissionPreferenceKt$AppPermissionPreference$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj2, Object obj3) {
                            ((Number) obj3).intValue();
                            AppPermissionPreferenceKt.AppPermissionPreference(
                                    app,
                                    flow,
                                    (Composer) obj2,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1),
                                    i2);
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
