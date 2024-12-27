package com.android.settingslib.spaprivileged.model.app;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;

import com.android.settingslib.spaprivileged.template.app.AppListItemModel;
import com.android.settingslib.spaprivileged.template.app.AppListItemModelKt;

import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

import java.util.Comparator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface AppListModel {
    default void AppItem(final AppListItemModel appListItemModel, Composer composer, final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(appListItemModel, "<this>");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-2134253968);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(appListItemModel) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            AppListItemModelKt.AppListItem(
                    appListItemModel,
                    new Function0() { // from class:
                                      // com.android.settingslib.spaprivileged.model.app.AppListModel$AppItem$1
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                            return Unit.INSTANCE;
                        }
                    },
                    composerImpl,
                    (i2 & 14) | 48);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.model.app.AppListModel$AppItem$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppListModel.this.AppItem(
                                    appListItemModel,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    Flow filter(
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            int i,
            ReadonlySharedFlow readonlySharedFlow);

    default Comparator getComparator(int i) {
        return ComparisonsKt__ComparisonsKt.compareBy(
                new Function1() { // from class:
                                  // com.android.settingslib.spaprivileged.model.app.AppListModel$getComparator$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        AppEntry it = (AppEntry) obj;
                        Intrinsics.checkNotNullParameter(it, "it");
                        return it.labelCollationKey;
                    }
                },
                new Function1() { // from class:
                                  // com.android.settingslib.spaprivileged.model.app.AppListModel$getComparator$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        AppEntry it = (AppEntry) obj;
                        Intrinsics.checkNotNullParameter(it, "it");
                        return it.record.getApp().packageName;
                    }
                },
                new Function1() { // from class:
                                  // com.android.settingslib.spaprivileged.model.app.AppListModel$getComparator$3
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        AppEntry it = (AppEntry) obj;
                        Intrinsics.checkNotNullParameter(it, "it");
                        return Integer.valueOf(it.record.getApp().uid);
                    }
                });
    }

    default String getGroupTitle(AppRecord record) {
        Intrinsics.checkNotNullParameter(record, "record");
        return null;
    }

    default List getSpinnerOptions(List recordList) {
        Intrinsics.checkNotNullParameter(recordList, "recordList");
        return EmptyList.INSTANCE;
    }

    default Function0 getSummary(int i, AppRecord record, Composer composer) {
        Intrinsics.checkNotNullParameter(record, "record");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-879864484);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        composerImpl.end(false);
        return null;
    }

    default Object onFirstLoaded(List list, Continuation continuation) {
        return Boolean.FALSE;
    }

    Flow transform(
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1
                    flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1);
}
