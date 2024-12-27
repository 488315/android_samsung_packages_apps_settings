package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.ParcelableSnapshotMutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.compose.LocalLifecycleOwnerKt;

import com.android.settings.R;
import com.android.settings.fuelgauge.batteryusage.BatteryDiffEntry;
import com.android.settingslib.Utils;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppBatteryPresenter {
    public final ApplicationInfo app;
    public final ParcelableSnapshotMutableState batteryDiffEntryState$delegate;
    public final Context context;
    public final Function0 enabled;
    public final Function0 summary;

    public AppBatteryPresenter(Context context, ApplicationInfo app) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(app, "app");
        this.context = context;
        this.app = app;
        this.batteryDiffEntryState$delegate =
                SnapshotStateKt.mutableStateOf(
                        LoadingState.Loading.INSTANCE, StructuralEqualityPolicy.INSTANCE);
        this.enabled =
                new Function0() { // from class:
                                  // com.android.settings.spa.app.appinfo.AppBatteryPresenter$enabled$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return Boolean.valueOf(
                                ((LoadingState)
                                                AppBatteryPresenter.this
                                                        .batteryDiffEntryState$delegate.getValue())
                                        instanceof LoadingState.Done);
                    }
                };
        this.summary =
                new Function0() { // from class:
                                  // com.android.settings.spa.app.appinfo.AppBatteryPresenter$summary$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        if (!ApplicationInfosKt.getInstalled(AppBatteryPresenter.this.app)) {
                            return ApnSettings.MVNO_NONE;
                        }
                        LoadingState loadingState =
                                (LoadingState)
                                        AppBatteryPresenter.this.batteryDiffEntryState$delegate
                                                .getValue();
                        AppBatteryPresenter appBatteryPresenter = AppBatteryPresenter.this;
                        if (loadingState instanceof LoadingState.Loading) {
                            return appBatteryPresenter.context.getString(
                                    R.string.summary_placeholder);
                        }
                        if (!(loadingState instanceof LoadingState.Done)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        BatteryDiffEntry batteryDiffEntry =
                                (BatteryDiffEntry) ((LoadingState.Done) loadingState).result;
                        appBatteryPresenter.getClass();
                        String str = null;
                        if (batteryDiffEntry != null) {
                            if ((batteryDiffEntry.mConsumePower > 0.0d ? batteryDiffEntry : null)
                                    != null) {
                                str =
                                        appBatteryPresenter.context.getString(
                                                R.string.battery_summary,
                                                Utils.formatPercentage(
                                                        batteryDiffEntry.mPercentage, true));
                            }
                        }
                        if (str != null) {
                            return str;
                        }
                        String string =
                                appBatteryPresenter.context.getString(R.string.no_battery_summary);
                        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                        return string;
                    }
                };
    }

    public final void Updater(Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-976327459);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        if (!ApplicationInfosKt.getInstalled(this.app)) {
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settings.spa.app.appinfo.AppBatteryPresenter$Updater$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                AppBatteryPresenter.this.Updater(
                                        (Composer) obj,
                                        RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                return Unit.INSTANCE;
                            }
                        };
                return;
            }
            return;
        }
        DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal =
                AndroidCompositionLocals_androidKt.LocalConfiguration;
        EffectsKt.LaunchedEffect(
                composerImpl,
                this.app,
                new AppBatteryPresenter$Updater$2(
                        (LifecycleOwner)
                                composerImpl.consume(LocalLifecycleOwnerKt.LocalLifecycleOwner),
                        this,
                        null));
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.appinfo.AppBatteryPresenter$Updater$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppBatteryPresenter.this.Updater(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
