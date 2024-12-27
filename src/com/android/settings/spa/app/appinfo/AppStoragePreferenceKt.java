package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settings.applications.AppStorageSettings;
import com.android.settings.applications.appinfo.AppInfoDashboardFragment;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;
import com.android.settingslib.spaprivileged.template.app.AppStorageSizeKt;

import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppStoragePreferenceKt {
    public static final void AppStoragePreference(
            final ApplicationInfo app, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(app, "app");
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1791673173);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        if (!ApplicationInfosKt.hasFlag(8388608, app)) {
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settings.spa.app.appinfo.AppStoragePreferenceKt$AppStoragePreference$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                AppStoragePreferenceKt.AppStoragePreference(
                                        app,
                                        (Composer) obj,
                                        RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                return Unit.INSTANCE;
                            }
                        };
                return;
            }
            return;
        }
        final Context context =
                (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        PreferenceKt.Preference(
                new PreferenceModel(
                        composerImpl,
                        context,
                        app) { // from class:
                               // com.android.settings.spa.app.appinfo.AppStoragePreferenceKt$AppStoragePreference$2
                    public final Function0 onClick;
                    public final Function0 summary;
                    public final String title;

                    {
                        this.title =
                                StringResources_androidKt.stringResource(
                                        composerImpl, R.string.storage_settings_for_app);
                        ComposerImpl composerImpl2 = (ComposerImpl) composerImpl;
                        composerImpl2.startReplaceGroup(-504963075);
                        OpaqueKey opaqueKey2 = ComposerKt.invocation;
                        final MutableState storageSize =
                                AppStorageSizeKt.getStorageSize(app, composerImpl2);
                        Function0 function0 =
                                new Function0() { // from class:
                                                  // com.android.settings.spa.app.appinfo.AppStoragePreferenceKt$getSummary$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        int i2;
                                        String str = (String) storageSize.getValue();
                                        if (StringsKt__StringsJVMKt.isBlank(str)) {
                                            String string =
                                                    context.getString(R.string.computing_size);
                                            Intrinsics.checkNotNull(string);
                                            return string;
                                        }
                                        Context context2 = context;
                                        boolean hasFlag = ApplicationInfosKt.hasFlag(262144, app);
                                        if (hasFlag) {
                                            i2 = R.string.storage_type_external;
                                        } else {
                                            if (hasFlag) {
                                                throw new NoWhenBranchMatchedException();
                                            }
                                            i2 = R.string.storage_type_internal;
                                        }
                                        String string2 = context2.getString(i2);
                                        Intrinsics.checkNotNullExpressionValue(
                                                string2, "getString(...)");
                                        String string3 =
                                                context.getString(
                                                        R.string.storage_summary_format,
                                                        str,
                                                        string2);
                                        Intrinsics.checkNotNull(string3);
                                        return string3;
                                    }
                                };
                        composerImpl2.end(false);
                        this.summary = function0;
                        this.onClick =
                                new Function0() { // from class:
                                                  // com.android.settings.spa.app.appinfo.AppStoragePreferenceKt$AppStoragePreference$2$onClick$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        AppInfoDashboardFragment.startAppInfoFragment(
                                                AppStorageSettings.class, app, context, 20);
                                        return Unit.INSTANCE;
                                    }
                                };
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
                },
                true,
                composerImpl,
                48,
                0);
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.appinfo.AppStoragePreferenceKt$AppStoragePreference$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppStoragePreferenceKt.AppStoragePreference(
                                    app,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
