package com.android.settings.spa.app.storage;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;

import com.android.settings.spa.app.appinfo.AppInfoSettingsProvider;
import com.android.settingslib.spaprivileged.model.app.AppListModel;
import com.android.settingslib.spaprivileged.model.app.AppRecord;
import com.android.settingslib.spaprivileged.template.app.AppListItemModel;
import com.android.settingslib.spaprivileged.template.app.AppListItemModelKt;
import com.android.settingslib.spaprivileged.template.app.AppStorageSizeKt;

import kotlin.Unit;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda1;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

import java.util.Comparator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class StorageAppListModel implements AppListModel {
    public final Context context;
    public final Function3 getStorageSummary;
    public final StorageType type;

    public StorageAppListModel(Context context, StorageType type) {
        AnonymousClass1 getStorageSummary =
                new Function3() { // from class:
                                  // com.android.settings.spa.app.storage.StorageAppListModel.1
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        ApplicationInfo applicationInfo = (ApplicationInfo) obj;
                        ((Number) obj3).intValue();
                        Intrinsics.checkNotNullParameter(applicationInfo, "$this$null");
                        ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
                        composerImpl.startReplaceGroup(1313545186);
                        OpaqueKey opaqueKey = ComposerKt.invocation;
                        MutableState storageSize =
                                AppStorageSizeKt.getStorageSize(applicationInfo, composerImpl);
                        composerImpl.end(false);
                        return storageSize;
                    }
                };
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(getStorageSummary, "getStorageSummary");
        this.context = context;
        this.type = type;
        this.getStorageSummary = getStorageSummary;
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final void AppItem(
            final AppListItemModel appListItemModel, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(appListItemModel, "<this>");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1431926317);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        AppInfoSettingsProvider appInfoSettingsProvider = AppInfoSettingsProvider.INSTANCE;
        AppListItemModelKt.AppListItem(
                appListItemModel,
                AppInfoSettingsProvider.navigator(
                        ((AppRecordWithSize) appListItemModel.record).app, composerImpl),
                composerImpl,
                8);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.storage.StorageAppListModel$AppItem$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            StorageAppListModel.this.AppItem(
                                    appListItemModel,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final Flow filter(
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            int i,
            ReadonlySharedFlow recordListFlow) {
        Intrinsics.checkNotNullParameter(recordListFlow, "recordListFlow");
        return new StorageAppListModel$filter$$inlined$filterItem$1(recordListFlow, this, 0);
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final Comparator getComparator(int i) {
        return new ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0(
                new StorageAppListModel$getComparator$$inlined$compareByDescending$1(),
                (ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda1) super.getComparator(i));
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final Function0 getSummary(int i, AppRecord appRecord, Composer composer) {
        AppRecordWithSize record = (AppRecordWithSize) appRecord;
        Intrinsics.checkNotNullParameter(record, "record");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1171830530);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final State state = (State) this.getStorageSummary.invoke(record.app, composerImpl, 8);
        composerImpl.startReplaceGroup(-2131856164);
        boolean changed = composerImpl.changed(state);
        Object rememberedValue = composerImpl.rememberedValue();
        if (changed || rememberedValue == Composer.Companion.Empty) {
            rememberedValue =
                    new Function0() { // from class:
                                      // com.android.settings.spa.app.storage.StorageAppListModel$getSummary$1$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return (String) State.this.getValue();
                        }
                    };
            composerImpl.updateRememberedValue(rememberedValue);
        }
        Function0 function0 = (Function0) rememberedValue;
        composerImpl.end(false);
        composerImpl.end(false);
        return function0;
    }

    @Override // com.android.settingslib.spaprivileged.model.app.AppListModel
    public final Flow transform(
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1
                    flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1) {
        return new StorageAppListModel$filter$$inlined$filterItem$1(
                flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1, this, 1);
    }
}
