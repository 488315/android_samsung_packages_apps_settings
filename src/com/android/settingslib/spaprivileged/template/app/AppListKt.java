package com.android.settingslib.spaprivileged.template.app;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.lazy.LazyDslKt;
import androidx.compose.foundation.lazy.LazyItemScopeImpl;
import androidx.compose.foundation.lazy.LazyListIntervalContent;
import androidx.compose.foundation.lazy.LazyListState;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.compose.FlowExtKt;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import androidx.lifecycle.viewmodel.compose.ViewModelKt;

import com.android.settings.R;
import com.android.settingslib.spa.framework.compose.KeyboardsKt;
import com.android.settingslib.spa.framework.compose.LifecycleEffectKt;
import com.android.settingslib.spa.framework.compose.TimeMeasurer$Companion$EmptyTimeMeasurer;
import com.android.settingslib.spa.widget.ui.CategoryKt;
import com.android.settingslib.spa.widget.ui.SpinnerKt;
import com.android.settingslib.spa.widget.ui.TextKt;
import com.android.settingslib.spaprivileged.framework.compose.DisposableBroadcastReceiverAsUserKt;
import com.android.settingslib.spaprivileged.model.app.AppEntry;
import com.android.settingslib.spaprivileged.model.app.AppListData;
import com.android.settingslib.spaprivileged.model.app.AppListModel;
import com.android.settingslib.spaprivileged.model.app.AppListViewModel;
import com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl;
import com.android.settingslib.spaprivileged.model.app.AppRecord;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppListKt {
    public static final void AppList(
            final AppListInput appListInput, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(appListInput, "<this>");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-523326249);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        AppListImpl(
                appListInput,
                new Function2() { // from class:
                                  // com.android.settingslib.spaprivileged.template.app.AppListKt$AppList$1
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Number) obj2).intValue();
                        ComposerImpl composerImpl2 = (ComposerImpl) ((Composer) obj);
                        composerImpl2.startReplaceGroup(1875340541);
                        OpaqueKey opaqueKey2 = ComposerKt.invocation;
                        AppListInput appListInput2 = AppListInput.this;
                        AppListConfig appListConfig = appListInput2.config;
                        composerImpl2.startReplaceGroup(491380450);
                        String obj3 = appListConfig.userIds.toString();
                        composerImpl2.startReplaceableGroup(1729797275);
                        ViewModelStoreOwner current =
                                LocalViewModelStoreOwner.getCurrent(composerImpl2);
                        if (current == null) {
                            throw new IllegalStateException(
                                    "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
                                            .toString());
                        }
                        ViewModel viewModel =
                                ViewModelKt.viewModel(
                                        Reflection.factory.getOrCreateKotlinClass(
                                                AppListViewModel.class),
                                        current,
                                        obj3,
                                        current instanceof HasDefaultViewModelProviderFactory
                                                ? ((HasDefaultViewModelProviderFactory) current)
                                                        .getDefaultViewModelCreationExtras()
                                                : CreationExtras.Empty.INSTANCE,
                                        composerImpl2);
                        composerImpl2.end(false);
                        final AppListViewModel appListViewModel = (AppListViewModel) viewModel;
                        StateFlowImpl stateFlowImpl = appListViewModel.appListConfig.stateFlow;
                        if (stateFlowImpl.getValue() == null) {
                            stateFlowImpl.setValue(appListConfig);
                        }
                        StateFlowImpl stateFlowImpl2 = appListViewModel.listModel.stateFlow;
                        if (stateFlowImpl2.getValue() == null) {
                            stateFlowImpl2.setValue(appListInput2.listModel);
                        }
                        AppListState appListState = appListInput2.state;
                        appListViewModel.showSystem.Sync(
                                appListState.showSystem, composerImpl2, 64);
                        appListViewModel.searchQuery.Sync(
                                appListState.searchQuery, composerImpl2, 64);
                        composerImpl2.startReplaceGroup(-1437055759);
                        boolean changed = composerImpl2.changed(appListViewModel);
                        Object rememberedValue = composerImpl2.rememberedValue();
                        Composer$Companion$Empty$1 composer$Companion$Empty$1 =
                                Composer.Companion.Empty;
                        if (changed || rememberedValue == composer$Companion$Empty$1) {
                            rememberedValue =
                                    new Function0() { // from class:
                                                      // com.android.settingslib.spaprivileged.template.app.AppListKt$rememberViewModel$1$1
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        /* renamed from: invoke */
                                        public final Object mo1068invoke() {
                                            AppListViewModel.this.reloadApps();
                                            return Unit.INSTANCE;
                                        }
                                    };
                            composerImpl2.updateRememberedValue(rememberedValue);
                        }
                        composerImpl2.end(false);
                        LifecycleEffectKt.LifecycleEffect(
                                (Function0) rememberedValue, null, composerImpl2, 0, 2);
                        IntentFilter intentFilter =
                                new IntentFilter("android.intent.action.PACKAGE_ADDED");
                        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
                        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
                        intentFilter.addDataScheme("package");
                        Iterator it = appListConfig.userIds.iterator();
                        while (it.hasNext()) {
                            UserHandle of = UserHandle.of(((Number) it.next()).intValue());
                            Intrinsics.checkNotNullExpressionValue(of, "of(...)");
                            composerImpl2.startReplaceGroup(-1437055342);
                            boolean changed2 = composerImpl2.changed(appListViewModel);
                            Object rememberedValue2 = composerImpl2.rememberedValue();
                            if (changed2 || rememberedValue2 == composer$Companion$Empty$1) {
                                rememberedValue2 =
                                        new Function1() { // from class:
                                                          // com.android.settingslib.spaprivileged.template.app.AppListKt$rememberViewModel$2$1
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            public final Object invoke(Object obj4) {
                                                Intent it2 = (Intent) obj4;
                                                Intrinsics.checkNotNullParameter(it2, "it");
                                                AppListViewModel.this.reloadApps();
                                                return Unit.INSTANCE;
                                            }
                                        };
                                composerImpl2.updateRememberedValue(rememberedValue2);
                            }
                            composerImpl2.end(false);
                            DisposableBroadcastReceiverAsUserKt.DisposableBroadcastReceiverAsUser(
                                    intentFilter,
                                    of,
                                    (Function1) rememberedValue2,
                                    composerImpl2,
                                    72);
                        }
                        OpaqueKey opaqueKey3 = ComposerKt.invocation;
                        composerImpl2.end(false);
                        composerImpl2.end(false);
                        return appListViewModel;
                    }
                },
                composerImpl,
                8);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.AppListKt$AppList$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppListKt.AppList(
                                    AppListInput.this,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void AppListImpl(
            final AppListInput appListInput,
            final Function2 viewModelSupplier,
            Composer composer,
            final int i) {
        Intrinsics.checkNotNullParameter(appListInput, "<this>");
        Intrinsics.checkNotNullParameter(viewModelSupplier, "viewModelSupplier");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-538641005);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        appListInput.config.userIds.toString();
        composerImpl.startReplaceGroup(-1979372154);
        composerImpl.end(false);
        AppListViewModelImpl appListViewModelImpl =
                (AppListViewModelImpl)
                        viewModelSupplier.invoke(composerImpl, Integer.valueOf((i >> 3) & 14));
        FillElement fillElement = SizeKt.FillWholeMaxSize;
        ColumnMeasurePolicy columnMeasurePolicy =
                ColumnKt.columnMeasurePolicy(
                        Arrangement.Top, Alignment.Companion.Start, composerImpl, 0);
        int i2 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope =
                composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier =
                ComposedModifierKt.materializeModifier(composerImpl, fillElement);
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
                || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i2))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i2, composerImpl, i2, function2);
        }
        Updater.m221setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        SpinnerOptions(
                FlowExtKt.collectAsStateWithLifecycle(
                        appListViewModelImpl.spinnerOptionsFlow, null, composerImpl, 56),
                appListViewModelImpl.optionFlow,
                composerImpl,
                64);
        m1051AppListWidgetjt2gSs(
                appListInput.listModel,
                FlowExtKt.collectAsStateWithLifecycle(
                        appListViewModelImpl.appListDataFlow, null, composerImpl, 56),
                appListInput.header,
                appListInput.bottomPadding,
                appListInput.noItemMessage,
                composerImpl,
                0);
        composerImpl.end(true);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.AppListKt$AppListImpl$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppListKt.AppListImpl(
                                    AppListInput.this,
                                    viewModelSupplier,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* renamed from: AppListWidget--jt2gSs, reason: not valid java name */
    public static final void m1051AppListWidgetjt2gSs(
            final AppListModel appListModel,
            final State state,
            final Function2 function2,
            final float f,
            final String str,
            Composer composer,
            final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(12318176);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(appListModel) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changed(state) ? 32 : 16;
        }
        if ((i & 896) == 0) {
            i2 |= composerImpl.changedInstance(function2) ? 256 : 128;
        }
        if ((i & 7168) == 0) {
            i2 |= composerImpl.changed(f) ? 2048 : 1024;
        }
        if ((57344 & i) == 0) {
            i2 |=
                    composerImpl.changed(str)
                            ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT
                            : 8192;
        }
        if ((46811 & i2) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            composerImpl.startReplaceGroup(642926772);
            composerImpl.startReplaceGroup(778772618);
            Object rememberedValue = composerImpl.rememberedValue();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = TimeMeasurer$Companion$EmptyTimeMeasurer.INSTANCE;
                composerImpl.updateRememberedValue(rememberedValue);
            }
            TimeMeasurer$Companion$EmptyTimeMeasurer timeMeasurer$Companion$EmptyTimeMeasurer =
                    (TimeMeasurer$Companion$EmptyTimeMeasurer) rememberedValue;
            composerImpl.end(false);
            composerImpl.end(false);
            AppListData appListData = (AppListData) state.getValue();
            if (appListData != null) {
                final List list = appListData.appEntries;
                timeMeasurer$Companion$EmptyTimeMeasurer.getClass();
                composerImpl.startReplaceGroup(1420014100);
                if (list.isEmpty()) {
                    function2.invoke(composerImpl, Integer.valueOf((i2 >> 6) & 14));
                    composerImpl.startReplaceGroup(147527165);
                    String stringResource =
                            str == null
                                    ? StringResources_androidKt.stringResource(
                                            composerImpl, R.string.no_applications)
                                    : str;
                    composerImpl.end(false);
                    TextKt.PlaceholderTitle(stringResource, composerImpl, 0);
                    composerImpl.end(false);
                    RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
                    if (endRestartGroup != null) {
                        endRestartGroup.block =
                                new Function2() { // from class:
                                                  // com.android.settingslib.spaprivileged.template.app.AppListKt$AppListWidget$1$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        ((Number) obj2).intValue();
                                        AppListKt.m1051AppListWidgetjt2gSs(
                                                AppListModel.this,
                                                state,
                                                function2,
                                                f,
                                                str,
                                                (Composer) obj,
                                                RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                        return Unit.INSTANCE;
                                    }
                                };
                        return;
                    }
                    return;
                }
                composerImpl.end(false);
                FillElement fillElement = SizeKt.FillWholeMaxSize;
                LazyListState rememberLazyListStateAndHideKeyboardWhenStartScroll =
                        KeyboardsKt.rememberLazyListStateAndHideKeyboardWhenStartScroll(
                                composerImpl);
                PaddingValuesImpl m84PaddingValuesa9UjIt4$default =
                        PaddingKt.m84PaddingValuesa9UjIt4$default(0.0f, 0.0f, f, 7);
                final int i3 = appListData.option;
                LazyDslKt.LazyColumn(
                        fillElement,
                        rememberLazyListStateAndHideKeyboardWhenStartScroll,
                        m84PaddingValuesa9UjIt4$default,
                        false,
                        null,
                        null,
                        null,
                        false,
                        new Function1() { // from class:
                                          // com.android.settingslib.spaprivileged.template.app.AppListKt$AppListWidget$1$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                LazyListIntervalContent LazyColumn = (LazyListIntervalContent) obj;
                                Intrinsics.checkNotNullParameter(LazyColumn, "$this$LazyColumn");
                                final Function2 function22 = function2;
                                LazyListIntervalContent.item$default(
                                        LazyColumn,
                                        new ComposableLambdaImpl(
                                                409874376,
                                                true,
                                                new Function3() { // from class:
                                                                  // com.android.settingslib.spaprivileged.template.app.AppListKt$AppListWidget$1$2.1
                                                    {
                                                        super(3);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function3
                                                    public final Object invoke(
                                                            Object obj2, Object obj3, Object obj4) {
                                                        LazyItemScopeImpl item =
                                                                (LazyItemScopeImpl) obj2;
                                                        Composer composer2 = (Composer) obj3;
                                                        int intValue = ((Number) obj4).intValue();
                                                        Intrinsics.checkNotNullParameter(
                                                                item, "$this$item");
                                                        if ((intValue & 81) == 16) {
                                                            ComposerImpl composerImpl2 =
                                                                    (ComposerImpl) composer2;
                                                            if (composerImpl2.getSkipping()) {
                                                                composerImpl2.skipToGroupEnd();
                                                                return Unit.INSTANCE;
                                                            }
                                                        }
                                                        OpaqueKey opaqueKey2 =
                                                                ComposerKt.invocation;
                                                        Function2.this.invoke(composer2, 0);
                                                        return Unit.INSTANCE;
                                                    }
                                                }));
                                int size = list.size();
                                final List<AppEntry> list2 = list;
                                final int i4 = i3;
                                Function1 function1 =
                                        new Function1() { // from class:
                                                          // com.android.settingslib.spaprivileged.template.app.AppListKt$AppListWidget$1$2.2
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            public final Object invoke(Object obj2) {
                                                AppRecord appRecord =
                                                        list2.get(((Number) obj2).intValue())
                                                                .record;
                                                return CollectionsKt__CollectionsKt.listOf(
                                                        Integer.valueOf(i4),
                                                        appRecord.getApp().packageName,
                                                        Integer.valueOf(
                                                                ApplicationInfosKt.getUserId(
                                                                        appRecord.getApp())));
                                            }
                                        };
                                final AppListModel appListModel2 = appListModel;
                                LazyListIntervalContent.items$default(
                                        LazyColumn,
                                        size,
                                        function1,
                                        new ComposableLambdaImpl(
                                                803724191,
                                                true,
                                                new Function4() { // from class:
                                                                  // com.android.settingslib.spaprivileged.template.app.AppListKt$AppListWidget$1$2.3
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(4);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function4
                                                    public final Object invoke(
                                                            Object obj2,
                                                            Object obj3,
                                                            Object obj4,
                                                            Object obj5) {
                                                        LazyItemScopeImpl items =
                                                                (LazyItemScopeImpl) obj2;
                                                        int intValue = ((Number) obj3).intValue();
                                                        Composer composer2 = (Composer) obj4;
                                                        int intValue2 = ((Number) obj5).intValue();
                                                        Intrinsics.checkNotNullParameter(
                                                                items, "$this$items");
                                                        if ((intValue2 & 112) == 0) {
                                                            intValue2 |=
                                                                    ((ComposerImpl) composer2)
                                                                                    .changed(
                                                                                            intValue)
                                                                            ? 32
                                                                            : 16;
                                                        }
                                                        if ((intValue2 & 721) == 144) {
                                                            ComposerImpl composerImpl2 =
                                                                    (ComposerImpl) composer2;
                                                            if (composerImpl2.getSkipping()) {
                                                                composerImpl2.skipToGroupEnd();
                                                                return Unit.INSTANCE;
                                                            }
                                                        }
                                                        OpaqueKey opaqueKey2 =
                                                                ComposerKt.invocation;
                                                        ComposerImpl composerImpl3 =
                                                                (ComposerImpl) composer2;
                                                        composerImpl3.startReplaceGroup(
                                                                -1515461963);
                                                        boolean changed =
                                                                composerImpl3.changed(list2);
                                                        AppListModel appListModel3 = appListModel2;
                                                        List<AppEntry> list3 = list2;
                                                        Object rememberedValue2 =
                                                                composerImpl3.rememberedValue();
                                                        Composer$Companion$Empty$1
                                                                composer$Companion$Empty$1 =
                                                                        Composer.Companion.Empty;
                                                        if (changed
                                                                || rememberedValue2
                                                                        == composer$Companion$Empty$1) {
                                                            String groupTitle =
                                                                    appListModel3.getGroupTitle(
                                                                            list3.get(intValue)
                                                                                    .record);
                                                            rememberedValue2 = null;
                                                            if (groupTitle != null
                                                                    && (intValue == 0
                                                                            || !groupTitle.equals(
                                                                                    appListModel3
                                                                                            .getGroupTitle(
                                                                                                    list3
                                                                                                            .get(
                                                                                                                    intValue
                                                                                                                            - 1)
                                                                                                            .record)))) {
                                                                rememberedValue2 = groupTitle;
                                                            }
                                                            composerImpl3.updateRememberedValue(
                                                                    rememberedValue2);
                                                        }
                                                        String str2 = (String) rememberedValue2;
                                                        composerImpl3.end(false);
                                                        composerImpl3.startReplaceGroup(
                                                                -1515461883);
                                                        if (str2 != null) {
                                                            CategoryKt.CategoryTitle(
                                                                    str2, composerImpl3, 0);
                                                        }
                                                        composerImpl3.end(false);
                                                        AppEntry appEntry = list2.get(intValue);
                                                        Function0 summary =
                                                                appListModel2.getSummary(
                                                                        i4,
                                                                        appEntry.record,
                                                                        composerImpl3);
                                                        if (summary == null) {
                                                            summary =
                                                                    new Function0() { // from class:
                                                                                      // com.android.settingslib.spaprivileged.template.app.AppListKt$AppListWidget$1$2$3$summary$1
                                                                        @Override // kotlin.jvm.functions.Function0
                                                                        /* renamed from: invoke */
                                                                        public
                                                                        final /* bridge */ /* synthetic */
                                                                        Object mo1068invoke() {
                                                                            return ApnSettings
                                                                                    .MVNO_NONE;
                                                                        }
                                                                    };
                                                        }
                                                        AppListModel appListModel4 = appListModel2;
                                                        composerImpl3.startReplaceGroup(
                                                                -1515461704);
                                                        boolean changed2 =
                                                                composerImpl3.changed(appEntry);
                                                        Object rememberedValue3 =
                                                                composerImpl3.rememberedValue();
                                                        if (changed2
                                                                || rememberedValue3
                                                                        == composer$Companion$Empty$1) {
                                                            rememberedValue3 =
                                                                    new AppListItemModel(
                                                                            appEntry.record,
                                                                            appEntry.label,
                                                                            summary);
                                                            composerImpl3.updateRememberedValue(
                                                                    rememberedValue3);
                                                        }
                                                        composerImpl3.end(false);
                                                        appListModel4.AppItem(
                                                                (AppListItemModel) rememberedValue3,
                                                                composerImpl3,
                                                                0);
                                                        return Unit.INSTANCE;
                                                    }
                                                }));
                                return Unit.INSTANCE;
                            }
                        },
                        composerImpl,
                        6,
                        IKnoxCustomManager.Stub.TRANSACTION_removeDexShortcut);
            }
        }
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.AppListKt$AppListWidget$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppListKt.m1051AppListWidgetjt2gSs(
                                    AppListModel.this,
                                    state,
                                    function2,
                                    f,
                                    str,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void SpinnerOptions(
            final State state,
            final MutableStateFlow mutableStateFlow,
            Composer composer,
            final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-804459582);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        List list = (List) state.getValue();
        EffectsKt.LaunchedEffect(
                composerImpl, list, new AppListKt$SpinnerOptions$1(list, mutableStateFlow, null));
        if (list != null) {
            SpinnerKt.Spinner(
                    list,
                    (Integer)
                            SnapshotStateKt.collectAsState(mutableStateFlow, composerImpl, 8)
                                    .getValue(),
                    new Function1() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.AppListKt$SpinnerOptions$2
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            int intValue = ((Number) obj).intValue();
                            ((StateFlowImpl) MutableStateFlow.this)
                                    .updateState(null, Integer.valueOf(intValue));
                            return Unit.INSTANCE;
                        }
                    },
                    composerImpl,
                    8);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.AppListKt$SpinnerOptions$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppListKt.SpinnerOptions(
                                    State.this,
                                    mutableStateFlow,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
