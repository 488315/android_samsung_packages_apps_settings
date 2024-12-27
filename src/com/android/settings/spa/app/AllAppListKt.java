package com.android.settings.spa.app;

import android.content.Context;
import android.os.AsyncTask;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.lifecycle.compose.FlowExtKt;

import com.android.settings.R;
import com.android.settings.applications.manageapplications.ResetAppsHelper;
import com.android.settings.applications.manageapplications.ResetAppsHelper$$ExternalSyntheticLambda0;
import com.android.settings.network.telephony.CallStateRepository;
import com.android.settingslib.spa.framework.compose.RuntimeUtilsKt;
import com.android.settingslib.spa.widget.dialog.AlertDialogButton;
import com.android.settingslib.spa.widget.dialog.AlertDialogPresenter;
import com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt;
import com.android.settingslib.spa.widget.dialog.SettingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1;
import com.android.settingslib.spa.widget.scaffold.MoreOptionsScope;
import com.android.settingslib.spaprivileged.model.app.AppListModel;
import com.android.settingslib.spaprivileged.template.app.AppListPageKt;

import com.sec.ims.presence.ServiceTuple;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AllAppListKt {
    /* JADX WARN: Type inference failed for: r5v8, types: [com.android.settings.spa.app.AllAppListKt$AllAppListPage$2, kotlin.jvm.internal.Lambda] */
    public static final void AllAppListPage(
            Function3 function3, Composer composer, final int i, final int i2) {
        final Function3 function32;
        int i3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1847871184);
        int i4 = i2 & 1;
        if (i4 != 0) {
            i3 = i | 6;
            function32 = function3;
        } else if ((i & 14) == 0) {
            function32 = function3;
            i3 = (composerImpl.changedInstance(function32) ? 4 : 2) | i;
        } else {
            function32 = function3;
            i3 = i;
        }
        if ((i3 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            Function3 function33 =
                    i4 != 0 ? ComposableSingletons$AllAppListKt.f33lambda2 : function32;
            OpaqueKey opaqueKey = ComposerKt.invocation;
            composerImpl.startReplaceGroup(-597397885);
            final Context context =
                    (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            composerImpl.startReplaceGroup(-1186211285);
            Object rememberedValue = composerImpl.rememberedValue();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = new CallStateRepository(context).isInCallFlow();
                composerImpl.updateRememberedValue(rememberedValue);
            }
            composerImpl.end(false);
            final SettingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1
                    rememberAlertDialogPresenter =
                            SettingsAlertDialogKt.rememberAlertDialogPresenter(
                                    new AlertDialogButton(
                                            StringResources_androidKt.stringResource(
                                                    composerImpl,
                                                    R.string.reset_app_preferences_button),
                                            !((Boolean)
                                                            FlowExtKt.collectAsStateWithLifecycle(
                                                                            (Flow) rememberedValue,
                                                                            Boolean.FALSE,
                                                                            composerImpl,
                                                                            56)
                                                                    .getValue())
                                                    .booleanValue(),
                                            new Function0() { // from class:
                                                              // com.android.settings.spa.app.ResetAppPreferencesKt$rememberResetAppDialogPresenter$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                /* renamed from: invoke */
                                                public final Object mo1068invoke() {
                                                    AsyncTask.execute(
                                                            new ResetAppsHelper$$ExternalSyntheticLambda0(
                                                                    new ResetAppsHelper(context)));
                                                    return Unit.INSTANCE;
                                                }
                                            }),
                                    new AlertDialogButton(
                                            StringResources_androidKt.stringResource(
                                                    composerImpl, R.string.cancel),
                                            (Function0) null,
                                            6),
                                    StringResources_androidKt.stringResource(
                                            composerImpl, R.string.reset_app_preferences_title),
                                    ComposableSingletons$ResetAppPreferencesKt.f37lambda1,
                                    composerImpl,
                                    0);
            composerImpl.end(false);
            AppListPageKt.AppListPage(
                    StringResources_androidKt.stringResource(composerImpl, R.string.all_apps),
                    (AppListModel)
                            RuntimeUtilsKt.rememberContext(
                                    AllAppListKt$AllAppListPage$1.INSTANCE, composerImpl),
                    true,
                    false,
                    true,
                    null,
                    ComposableLambdaKt.rememberComposableLambda(
                            -1795026924,
                            new Function3() { // from class:
                                              // com.android.settings.spa.app.AllAppListKt$AllAppListPage$2

                                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                @Metadata(
                                        k = 3,
                                        mv = {1, 9, 0},
                                        xi = 48)
                                /* renamed from: com.android.settings.spa.app.AllAppListKt$AllAppListPage$2$1, reason: invalid class name */
                                final /* synthetic */ class AnonymousClass1
                                        extends FunctionReferenceImpl implements Function0 {
                                    @Override // kotlin.jvm.functions.Function0
                                    /* renamed from: invoke */
                                    public final Object mo1068invoke() {
                                        ((SettingsAlertDialogKt$rememberAlertDialogPresenter$alertDialogPresenter$1$1)
                                                        ((AlertDialogPresenter) this.receiver))
                                                .open();
                                        return Unit.INSTANCE;
                                    }
                                }

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(3);
                                }

                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj, Object obj2, Object obj3) {
                                    MoreOptionsScope AppListPage = (MoreOptionsScope) obj;
                                    Composer composer2 = (Composer) obj2;
                                    int intValue = ((Number) obj3).intValue();
                                    Intrinsics.checkNotNullParameter(
                                            AppListPage, "$this$AppListPage");
                                    if ((intValue & 14) == 0) {
                                        intValue |=
                                                ((ComposerImpl) composer2).changed(AppListPage)
                                                        ? 4
                                                        : 2;
                                    }
                                    if ((intValue & 91) == 18) {
                                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                        if (composerImpl2.getSkipping()) {
                                            composerImpl2.skipToGroupEnd();
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                    ResetAppPreferencesKt.ResetAppPreferences(
                                            AppListPage,
                                            new AnonymousClass1(
                                                    0,
                                                    rememberAlertDialogPresenter,
                                                    AlertDialogPresenter.class,
                                                    ServiceTuple.BASIC_STATUS_OPEN,
                                                    "open()V",
                                                    0),
                                            composer2,
                                            intValue & 14);
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl),
                    null,
                    function33,
                    composerImpl,
                    ((i3 << 24) & 234881024) | 1597888,
                    168);
            function32 = function33;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.AllAppListKt$AllAppListPage$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AllAppListKt.AllAppListPage(
                                    Function3.this,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1),
                                    i2);
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
