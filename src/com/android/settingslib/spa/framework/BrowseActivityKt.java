package com.android.settingslib.spa.framework;

import android.content.Intent;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner;
import androidx.compose.animation.AnimatedContentScope;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavGraphBuilder;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigator;
import androidx.navigation.compose.NavGraphBuilderKt;
import androidx.navigation.compose.NavHostControllerKt;
import androidx.navigation.compose.NavHostKt;
import com.android.settingslib.spa.framework.common.SettingsPage;
import com.android.settingslib.spa.framework.common.SettingsPageProvider;
import com.android.settingslib.spa.framework.common.SettingsPageProviderKt;
import com.android.settingslib.spa.framework.common.SettingsPageProviderRepository;
import com.android.settingslib.spa.framework.compose.AnimatedNavGraphBuilderKt;
import com.android.settingslib.spa.framework.compose.NavControllerWrapperImpl;
import com.android.settingslib.spa.framework.compose.NavControllerWrapperKt;
import com.android.settingslib.spa.framework.util.PageLoggerKt;
import com.android.settingslib.spa.framework.util.ParameterKt;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.net.apn.ApnSettings;
import java.util.Collection;
import java.util.LinkedHashMap;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BrowseActivityKt {
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.settingslib.spa.framework.BrowseActivityKt$BrowseContent$1, kotlin.jvm.internal.Lambda] */
    public static final void BrowseContent(final SettingsPageProviderRepository sppRepository, final Function1 isPageEnabled, final Intent intent, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(sppRepository, "sppRepository");
        Intrinsics.checkNotNullParameter(isPageEnabled, "isPageEnabled");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(387540586);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        NavHostController rememberNavController = NavHostControllerKt.rememberNavController(new Navigator[0], composerImpl);
        DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = NavControllerWrapperKt.LocalNavController;
        Intrinsics.checkNotNullParameter(rememberNavController, "<this>");
        composerImpl.startReplaceGroup(-568227507);
        OnBackPressedDispatcherOwner current = LocalOnBackPressedDispatcherOwner.getCurrent(composerImpl);
        DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal2 = NavControllerWrapperKt.LocalNavController;
        composerImpl.startReplaceGroup(-574383014);
        Object rememberedValue = composerImpl.rememberedValue();
        if (rememberedValue == Composer.Companion.Empty) {
            rememberedValue = new NavControllerWrapperImpl(rememberNavController, current != null ? current.getOnBackPressedDispatcher() : null);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        composerImpl.end(false);
        ProvidedValue defaultProvidedValue$runtime_release = dynamicProvidableCompositionLocal2.defaultProvidedValue$runtime_release((NavControllerWrapperImpl) rememberedValue);
        composerImpl.end(false);
        CompositionLocalKt.CompositionLocalProvider(defaultProvidedValue$runtime_release, ComposableLambdaKt.rememberComposableLambda(-434173142, new Function2() { // from class: com.android.settingslib.spa.framework.BrowseActivityKt$BrowseContent$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            /* JADX WARN: Type inference failed for: r1v0, types: [com.android.settingslib.spa.framework.BrowseActivityKt$BrowseContent$1$1, kotlin.jvm.internal.Lambda] */
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                String m;
                Composer composer2 = (Composer) obj;
                if ((((Number) obj2).intValue() & 11) == 2) {
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    if (composerImpl2.getSkipping()) {
                        composerImpl2.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                Object consume = composerImpl3.consume(NavControllerWrapperKt.LocalNavController);
                Intrinsics.checkNotNull(consume, "null cannot be cast to non-null type com.android.settingslib.spa.framework.compose.NavControllerWrapperImpl");
                final NavControllerWrapperImpl navControllerWrapperImpl = (NavControllerWrapperImpl) consume;
                Collection values = ((LinkedHashMap) SettingsPageProviderRepository.this.pageProviderMap).values();
                final Function1 function1 = isPageEnabled;
                BrowseActivityKt.access$NavContent(navControllerWrapperImpl, values, ComposableLambdaKt.rememberComposableLambda(782928604, new Function3() { // from class: com.android.settingslib.spa.framework.BrowseActivityKt$BrowseContent$1.1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
                    /* renamed from: com.android.settingslib.spa.framework.BrowseActivityKt$BrowseContent$1$1$2, reason: invalid class name */
                    final class AnonymousClass2 extends SuspendLambda implements Function2 {
                        final /* synthetic */ SettingsPage $page;
                        int label;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass2(SettingsPage settingsPage, Continuation continuation) {
                            super(2, continuation);
                            this.$page = settingsPage;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            return new AnonymousClass2(this.$page, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            AnonymousClass2 anonymousClass2 = (AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2);
                            Unit unit = Unit.INSTANCE;
                            anonymousClass2.invokeSuspend(unit);
                            return unit;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            if (this.label != 0) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                            DialogFragment$$ExternalSyntheticOutline0.m("Launching page ", this.$page.sppName, "BrowseActivity");
                            return Unit.INSTANCE;
                        }
                    }

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
                    /* renamed from: com.android.settingslib.spa.framework.BrowseActivityKt$BrowseContent$1$1$3, reason: invalid class name */
                    final class AnonymousClass3 extends SuspendLambda implements Function2 {
                        final /* synthetic */ NavControllerWrapperImpl $controller;
                        int label;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass3(NavControllerWrapperImpl navControllerWrapperImpl, Continuation continuation) {
                            super(2, continuation);
                            this.$controller = navControllerWrapperImpl;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            return new AnonymousClass3(this.$controller, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            AnonymousClass3 anonymousClass3 = (AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2);
                            Unit unit = Unit.INSTANCE;
                            anonymousClass3.invokeSuspend(unit);
                            return unit;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            if (this.label != 0) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                            this.$controller.navigateBack();
                            return Unit.INSTANCE;
                        }
                    }

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(3);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj3, Object obj4, Object obj5) {
                        SettingsPage page = (SettingsPage) obj3;
                        ((Number) obj5).intValue();
                        Intrinsics.checkNotNullParameter(page, "page");
                        OpaqueKey opaqueKey3 = ComposerKt.invocation;
                        ComposerImpl composerImpl4 = (ComposerImpl) ((Composer) obj4);
                        composerImpl4.startReplaceGroup(1745703823);
                        Function1 function12 = Function1.this;
                        Object rememberedValue2 = composerImpl4.rememberedValue();
                        if (rememberedValue2 == Composer.Companion.Empty) {
                            rememberedValue2 = (Boolean) function12.invoke(page);
                            rememberedValue2.getClass();
                            composerImpl4.updateRememberedValue(rememberedValue2);
                        }
                        boolean booleanValue = ((Boolean) rememberedValue2).booleanValue();
                        composerImpl4.end(false);
                        Unit unit = Unit.INSTANCE;
                        if (booleanValue) {
                            composerImpl4.startReplaceGroup(1745703875);
                            EffectsKt.LaunchedEffect(composerImpl4, unit, new AnonymousClass2(page, null));
                            PageLoggerKt.PageLogger(page, composerImpl4, 8);
                            page.UiLayout(composerImpl4, 8);
                            composerImpl4.end(false);
                        } else {
                            composerImpl4.startReplaceGroup(1745704084);
                            EffectsKt.LaunchedEffect(composerImpl4, unit, new AnonymousClass3(navControllerWrapperImpl, null));
                            composerImpl4.end(false);
                        }
                        return unit;
                    }
                }, composerImpl3), composerImpl3, 456);
                Intent intent2 = intent;
                SettingsPageProviderRepository settingsPageProviderRepository = SettingsPageProviderRepository.this;
                if (settingsPageProviderRepository.rootPages.isEmpty()) {
                    m = ApnSettings.MVNO_NONE;
                } else {
                    SettingsPage settingsPage = (SettingsPage) settingsPageProviderRepository.rootPages.get(0);
                    m = ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder(), settingsPage.sppName, ParameterKt.navLink(settingsPage.arguments, settingsPage.parameter));
                }
                BrowseActivityKt.access$InitialDestination(navControllerWrapperImpl, intent2, m, composerImpl3, 72);
                return Unit.INSTANCE;
            }
        }, composerImpl), composerImpl, 56);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.settingslib.spa.framework.BrowseActivityKt$BrowseContent$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    BrowseActivityKt.BrowseContent(SettingsPageProviderRepository.this, isPageEnabled, intent, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void access$InitialDestination(final com.android.settingslib.spa.framework.compose.NavControllerWrapperImpl r8, final android.content.Intent r9, final java.lang.String r10, androidx.compose.runtime.Composer r11, final int r12) {
        /*
            androidx.compose.runtime.ComposerImpl r11 = (androidx.compose.runtime.ComposerImpl) r11
            r0 = -1232342638(0xffffffffb68bf192, float:-4.1706453E-6)
            r11.startRestartGroup(r0)
            androidx.compose.runtime.OpaqueKey r0 = androidx.compose.runtime.ComposerKt.invocation
            r0 = 0
            java.lang.Object[] r0 = new java.lang.Object[r0]
            com.android.settingslib.spa.framework.BrowseActivityKt$InitialDestination$destinationNavigated$1 r2 = new kotlin.jvm.functions.Function0() { // from class: com.android.settingslib.spa.framework.BrowseActivityKt$InitialDestination$destinationNavigated$1
                static {
                    /*
                        com.android.settingslib.spa.framework.BrowseActivityKt$InitialDestination$destinationNavigated$1 r0 = new com.android.settingslib.spa.framework.BrowseActivityKt$InitialDestination$destinationNavigated$1
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:com.android.settingslib.spa.framework.BrowseActivityKt$InitialDestination$destinationNavigated$1) com.android.settingslib.spa.framework.BrowseActivityKt$InitialDestination$destinationNavigated$1.INSTANCE com.android.settingslib.spa.framework.BrowseActivityKt$InitialDestination$destinationNavigated$1
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.spa.framework.BrowseActivityKt$InitialDestination$destinationNavigated$1.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 0
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.spa.framework.BrowseActivityKt$InitialDestination$destinationNavigated$1.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function0
                /* renamed from: invoke */
                public final java.lang.Object mo1068invoke() {
                    /*
                        r1 = this;
                        java.lang.Boolean r1 = java.lang.Boolean.FALSE
                        androidx.compose.runtime.StructuralEqualityPolicy r0 = androidx.compose.runtime.StructuralEqualityPolicy.INSTANCE
                        androidx.compose.runtime.ParcelableSnapshotMutableState r1 = androidx.compose.runtime.SnapshotStateKt.mutableStateOf(r1, r0)
                        return r1
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.spa.framework.BrowseActivityKt$InitialDestination$destinationNavigated$1.mo1068invoke():java.lang.Object");
                }
            }
            r4 = 3080(0xc08, float:4.316E-42)
            r5 = 6
            r1 = 0
            r3 = r11
            java.lang.Object r0 = androidx.compose.runtime.saveable.RememberSaveableKt.rememberSaveable(r0, r1, r2, r3, r4, r5)
            androidx.compose.runtime.MutableState r0 = (androidx.compose.runtime.MutableState) r0
            java.lang.Object r1 = r0.getValue()
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L34
            androidx.compose.runtime.RecomposeScopeImpl r11 = r11.endRestartGroup()
            if (r11 == 0) goto L90
            com.android.settingslib.spa.framework.BrowseActivityKt$InitialDestination$1 r0 = new com.android.settingslib.spa.framework.BrowseActivityKt$InitialDestination$1
            r0.<init>()
            r11.block = r0
            goto L90
        L34:
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            r0.setValue(r1)
            if (r9 == 0) goto L48
            int r0 = com.android.settingslib.spa.framework.util.SpaIntentKt.$r8$clinit
            java.lang.String r0 = "spaActivityDestination"
            java.lang.String r0 = r9.getStringExtra(r0)
            if (r0 != 0) goto L46
            goto L48
        L46:
            r5 = r0
            goto L49
        L48:
            r5 = r10
        L49:
            int r0 = r5.length()
            if (r0 != 0) goto L5d
            androidx.compose.runtime.RecomposeScopeImpl r11 = r11.endRestartGroup()
            if (r11 == 0) goto L90
            com.android.settingslib.spa.framework.BrowseActivityKt$InitialDestination$2 r0 = new com.android.settingslib.spa.framework.BrowseActivityKt$InitialDestination$2
            r0.<init>()
            r11.block = r0
            goto L90
        L5d:
            r0 = 0
            if (r9 == 0) goto L6a
            int r1 = com.android.settingslib.spa.framework.util.SpaIntentKt.$r8$clinit
            java.lang.String r1 = "highlightEntry"
            java.lang.String r1 = r9.getStringExtra(r1)
            r3 = r1
            goto L6b
        L6a:
            r3 = r0
        L6b:
            if (r9 == 0) goto L75
            int r0 = com.android.settingslib.spa.framework.util.SpaIntentKt.$r8$clinit
            java.lang.String r0 = "sessionSource"
            java.lang.String r0 = r9.getStringExtra(r0)
        L75:
            r4 = r0
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            com.android.settingslib.spa.framework.BrowseActivityKt$InitialDestination$3 r7 = new com.android.settingslib.spa.framework.BrowseActivityKt$InitialDestination$3
            r6 = 0
            r1 = r7
            r2 = r8
            r1.<init>(r2, r3, r4, r5, r6)
            androidx.compose.runtime.EffectsKt.LaunchedEffect(r11, r0, r7)
            androidx.compose.runtime.RecomposeScopeImpl r11 = r11.endRestartGroup()
            if (r11 == 0) goto L90
            com.android.settingslib.spa.framework.BrowseActivityKt$InitialDestination$4 r0 = new com.android.settingslib.spa.framework.BrowseActivityKt$InitialDestination$4
            r0.<init>()
            r11.block = r0
        L90:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.spa.framework.BrowseActivityKt.access$InitialDestination(com.android.settingslib.spa.framework.compose.NavControllerWrapperImpl, android.content.Intent, java.lang.String, androidx.compose.runtime.Composer, int):void");
    }

    public static final void access$NavContent(final NavControllerWrapperImpl navControllerWrapperImpl, final Collection collection, final Function3 function3, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1961552012);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        NavHostKt.NavHost(navControllerWrapperImpl.navController, "NULL", SizeKt.FillWholeMaxSize, null, null, null, null, null, null, null, new Function1() { // from class: com.android.settingslib.spa.framework.BrowseActivityKt$NavContent$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                NavGraphBuilder NavHost = (NavGraphBuilder) obj;
                Intrinsics.checkNotNullParameter(NavHost, "$this$NavHost");
                NavGraphBuilderKt.composable$default(NavHost, "NULL", null, null, null, null, null, null, ComposableSingletons$BrowseActivityKt.f71lambda1, 254);
                for (final SettingsPageProvider settingsPageProvider : collection) {
                    final Function3 function32 = function3;
                    final ComposableLambdaImpl composableLambdaImpl = new ComposableLambdaImpl(-327706681, true, new Function3() { // from class: com.android.settingslib.spa.framework.BrowseActivityKt$NavContent$1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj2, Object obj3, Object obj4) {
                            NavBackStackEntry navBackStackEntry = (NavBackStackEntry) obj2;
                            ((Number) obj4).intValue();
                            Intrinsics.checkNotNullParameter(navBackStackEntry, "navBackStackEntry");
                            OpaqueKey opaqueKey2 = ComposerKt.invocation;
                            ComposerImpl composerImpl2 = (ComposerImpl) ((Composer) obj3);
                            composerImpl2.startReplaceGroup(581132344);
                            SettingsPageProvider settingsPageProvider2 = settingsPageProvider;
                            Object rememberedValue = composerImpl2.rememberedValue();
                            if (rememberedValue == Composer.Companion.Empty) {
                                rememberedValue = SettingsPageProviderKt.createSettingsPage(settingsPageProvider2, navBackStackEntry.getArguments());
                                composerImpl2.updateRememberedValue(rememberedValue);
                            }
                            composerImpl2.end(false);
                            Function3.this.invoke((SettingsPage) rememberedValue, composerImpl2, 8);
                            return Unit.INSTANCE;
                        }
                    });
                    String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(settingsPageProvider.getName(), ParameterKt.navRoute(settingsPageProvider.getParameter()));
                    SettingsPageProvider.NavType[] navTypeArr = SettingsPageProvider.NavType.$VALUES;
                    AnimatedNavGraphBuilderKt.animatedComposable$default(NavHost, m, settingsPageProvider.getParameter(), new ComposableLambdaImpl(-1184216405, true, new Function4() { // from class: com.android.settingslib.spa.framework.BrowseActivityKt$destination$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(4);
                        }

                        @Override // kotlin.jvm.functions.Function4
                        public final Object invoke(Object obj2, Object obj3, Object obj4, Object obj5) {
                            AnimatedContentScope animatedComposable = (AnimatedContentScope) obj2;
                            NavBackStackEntry it = (NavBackStackEntry) obj3;
                            ((Number) obj5).intValue();
                            Intrinsics.checkNotNullParameter(animatedComposable, "$this$animatedComposable");
                            Intrinsics.checkNotNullParameter(it, "it");
                            OpaqueKey opaqueKey2 = ComposerKt.invocation;
                            composableLambdaImpl.invoke(it, (Composer) obj4, 8);
                            return Unit.INSTANCE;
                        }
                    }));
                }
                return Unit.INSTANCE;
            }
        }, composerImpl, 440, 0, EnterpriseContainerCallback.CONTAINER_CANCELLED);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.settingslib.spa.framework.BrowseActivityKt$NavContent$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    BrowseActivityKt.access$NavContent(NavControllerWrapperImpl.this, collection, function3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
