package com.android.settings.spa.app.specialaccess;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;

import com.android.settings.R;
import com.android.settingslib.spa.lifecycle.FlowExtKt;
import com.android.settingslib.spaprivileged.model.app.AppOps;
import com.android.settingslib.spaprivileged.model.app.AppOpsController;
import com.android.settingslib.spaprivileged.model.app.AppRecord;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;
import com.android.settingslib.spaprivileged.model.app.IAppOpsController$special$$inlined$map$1;
import com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel;
import com.android.settingslib.spaprivileged.template.app.TogglePermissionInternalAppListModel$filter$$inlined$filterItem$1;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PictureInPictureListModel implements TogglePermissionAppListModel {
    public static final AppOps APP_OPS = new AppOps(67, 0, false, 6);
    public static final Companion Companion = null;
    public static final PackageManager.PackageInfoFlags GET_ACTIVITIES_FLAGS;
    public final Context context;
    public final PackageManager packageManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {}

    static {
        PackageManager.PackageInfoFlags of = PackageManager.PackageInfoFlags.of(1L);
        Intrinsics.checkNotNullExpressionValue(of, "of(...)");
        GET_ACTIVITIES_FLAGS = of;
    }

    public PictureInPictureListModel(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.packageManager = context.getPackageManager();
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final Flow filter(
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            final TogglePermissionInternalAppListModel$filter$$inlined$filterItem$1
                    togglePermissionInternalAppListModel$filter$$inlined$filterItem$1) {
        return new Flow() { // from class:
            // com.android.settings.spa.app.specialaccess.PictureInPictureListModel$filter$$inlined$map$1

            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
            /* renamed from: com.android.settings.spa.app.specialaccess.PictureInPictureListModel$filter$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                @Metadata(
                        k = 3,
                        mv = {1, 9, 0},
                        xi = 48)
                /* renamed from: com.android.settings.spa.app.specialaccess.PictureInPictureListModel$filter$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(
                        java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.settings.spa.app.specialaccess.PictureInPictureListModel$filter$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.settings.spa.app.specialaccess.PictureInPictureListModel$filter$$inlined$map$1$2$1 r0 = (com.android.settings.spa.app.specialaccess.PictureInPictureListModel$filter$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.settings.spa.app.specialaccess.PictureInPictureListModel$filter$$inlined$map$1$2$1 r0 = new com.android.settings.spa.app.specialaccess.PictureInPictureListModel$filter$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L5f
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        java.util.List r6 = (java.util.List) r6
                        java.lang.Iterable r6 = (java.lang.Iterable) r6
                        java.util.ArrayList r7 = new java.util.ArrayList
                        r7.<init>()
                        java.util.Iterator r6 = r6.iterator()
                    L3f:
                        boolean r2 = r6.hasNext()
                        if (r2 == 0) goto L54
                        java.lang.Object r2 = r6.next()
                        r4 = r2
                        com.android.settings.spa.app.specialaccess.PictureInPictureRecord r4 = (com.android.settings.spa.app.specialaccess.PictureInPictureRecord) r4
                        boolean r4 = r4.isSupport
                        if (r4 == 0) goto L3f
                        r7.add(r2)
                        goto L3f
                    L54:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r7, r0)
                        if (r5 != r1) goto L5f
                        return r1
                    L5f:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException(
                            "Method not decompiled:"
                                + " com.android.settings.spa.app.specialaccess.PictureInPictureListModel$filter$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                + " kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect =
                        togglePermissionInternalAppListModel$filter$$inlined$filterItem$1.collect(
                                new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final String getEnhancedConfirmationKey() {
        return "android:picture_in_picture";
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final int getFooterResId() {
        return R.string.picture_in_picture_app_detail_summary;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final int getPageTitleResId() {
        return R.string.picture_in_picture_title;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final int getSwitchTitleResId() {
        return R.string.picture_in_picture_app_detail_switch;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final Function0 isAllowed(AppRecord appRecord, Composer composer) {
        PictureInPictureRecord record = (PictureInPictureRecord) appRecord;
        Intrinsics.checkNotNullParameter(record, "record");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-919870686);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        AppOpsController appOpsController = record.appOpsController;
        appOpsController.getClass();
        Function0 collectAsCallbackWithLifecycle =
                FlowExtKt.collectAsCallbackWithLifecycle(
                        new IAppOpsController$special$$inlined$map$1(appOpsController.modeFlow),
                        composerImpl);
        composerImpl.end(false);
        return collectAsCallbackWithLifecycle;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final boolean isChangeable(AppRecord appRecord) {
        PictureInPictureRecord record = (PictureInPictureRecord) appRecord;
        Intrinsics.checkNotNullParameter(record, "record");
        return record.isSupport;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final void setAllowed(AppRecord appRecord, boolean z) {
        PictureInPictureRecord record = (PictureInPictureRecord) appRecord;
        Intrinsics.checkNotNullParameter(record, "record");
        record.appOpsController.setAllowed(z);
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final Flow transform(
            final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1
                    flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1) {
        return FlowKt.flowCombine(
                new Flow() { // from class:
                    // com.android.settings.spa.app.specialaccess.PictureInPictureListModel$transform$$inlined$map$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.spa.app.specialaccess.PictureInPictureListModel$transform$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ PictureInPictureListModel receiver$inlined;

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.spa.app.specialaccess.PictureInPictureListModel$transform$$inlined$map$1$2$1, reason: invalid class name */
                        public final class AnonymousClass1 extends ContinuationImpl {
                            Object L$0;
                            int label;
                            /* synthetic */ Object result;

                            public AnonymousClass1(Continuation continuation) {
                                super(continuation);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                this.result = obj;
                                this.label |= Integer.MIN_VALUE;
                                return AnonymousClass2.this.emit(null, this);
                            }
                        }

                        public AnonymousClass2(
                                FlowCollector flowCollector,
                                PictureInPictureListModel pictureInPictureListModel) {
                            this.$this_unsafeFlow = flowCollector;
                            this.receiver$inlined = pictureInPictureListModel;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(
                                java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                            /*
                                r8 = this;
                                r0 = 1
                                boolean r1 = r10 instanceof com.android.settings.spa.app.specialaccess.PictureInPictureListModel$transform$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r1 == 0) goto L14
                                r1 = r10
                                com.android.settings.spa.app.specialaccess.PictureInPictureListModel$transform$$inlined$map$1$2$1 r1 = (com.android.settings.spa.app.specialaccess.PictureInPictureListModel$transform$$inlined$map$1.AnonymousClass2.AnonymousClass1) r1
                                int r2 = r1.label
                                r3 = -2147483648(0xffffffff80000000, float:-0.0)
                                r4 = r2 & r3
                                if (r4 == 0) goto L14
                                int r2 = r2 - r3
                                r1.label = r2
                                goto L19
                            L14:
                                com.android.settings.spa.app.specialaccess.PictureInPictureListModel$transform$$inlined$map$1$2$1 r1 = new com.android.settings.spa.app.specialaccess.PictureInPictureListModel$transform$$inlined$map$1$2$1
                                r1.<init>(r10)
                            L19:
                                java.lang.Object r10 = r1.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r2 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r3 = r1.label
                                if (r3 == 0) goto L30
                                if (r3 != r0) goto L28
                                kotlin.ResultKt.throwOnFailure(r10)
                                goto Lb6
                            L28:
                                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                                java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                                r8.<init>(r9)
                                throw r8
                            L30:
                                kotlin.ResultKt.throwOnFailure(r10)
                                java.lang.Number r9 = (java.lang.Number) r9
                                int r9 = r9.intValue()
                                com.android.settings.spa.app.specialaccess.PictureInPictureListModel$Companion r10 = com.android.settings.spa.app.specialaccess.PictureInPictureListModel.Companion
                                com.android.settings.spa.app.specialaccess.PictureInPictureListModel r10 = r8.receiver$inlined
                                r10.getClass()
                                android.content.pm.PackageManager r10 = r10.packageManager     // Catch: java.lang.Exception -> L4c
                                android.content.pm.PackageManager$PackageInfoFlags r3 = com.android.settings.spa.app.specialaccess.PictureInPictureListModel.GET_ACTIVITIES_FLAGS     // Catch: java.lang.Exception -> L4c
                                java.util.List r9 = r10.getInstalledPackagesAsUser(r3, r9)     // Catch: java.lang.Exception -> L4c
                                kotlin.jvm.internal.Intrinsics.checkNotNull(r9)     // Catch: java.lang.Exception -> L4c
                                goto L56
                            L4c:
                                r9 = move-exception
                                java.lang.String r10 = "PictureInPictureListModel"
                                java.lang.String r3 = "Exception while getInstalledPackagesAsUser"
                                android.util.Log.e(r10, r3, r9)
                                kotlin.collections.EmptyList r9 = kotlin.collections.EmptyList.INSTANCE
                            L56:
                                java.lang.Iterable r9 = (java.lang.Iterable) r9
                                java.util.ArrayList r10 = new java.util.ArrayList
                                r10.<init>()
                                java.util.Iterator r9 = r9.iterator()
                            L61:
                                boolean r3 = r9.hasNext()
                                if (r3 == 0) goto L86
                                java.lang.Object r3 = r9.next()
                                r4 = r3
                                android.content.pm.PackageInfo r4 = (android.content.pm.PackageInfo) r4
                                com.android.settings.spa.app.specialaccess.PictureInPictureListModel$Companion r5 = com.android.settings.spa.app.specialaccess.PictureInPictureListModel.Companion
                                android.content.pm.ActivityInfo[] r4 = r4.activities
                                if (r4 == 0) goto L61
                                int r5 = r4.length
                                r6 = 0
                            L76:
                                if (r6 >= r5) goto L61
                                r7 = r4[r6]
                                boolean r7 = r7.supportsPictureInPicture()
                                if (r7 == 0) goto L84
                                r10.add(r3)
                                goto L61
                            L84:
                                int r6 = r6 + r0
                                goto L76
                            L86:
                                java.util.ArrayList r9 = new java.util.ArrayList
                                r3 = 10
                                int r3 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r10, r3)
                                r9.<init>(r3)
                                java.util.Iterator r10 = r10.iterator()
                            L95:
                                boolean r3 = r10.hasNext()
                                if (r3 == 0) goto La7
                                java.lang.Object r3 = r10.next()
                                android.content.pm.PackageInfo r3 = (android.content.pm.PackageInfo) r3
                                java.lang.String r3 = r3.packageName
                                r9.add(r3)
                                goto L95
                            La7:
                                java.util.Set r9 = kotlin.collections.CollectionsKt___CollectionsKt.toSet(r9)
                                r1.label = r0
                                kotlinx.coroutines.flow.FlowCollector r8 = r8.$this_unsafeFlow
                                java.lang.Object r8 = r8.emit(r9, r1)
                                if (r8 != r2) goto Lb6
                                return r2
                            Lb6:
                                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                                return r8
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.settings.spa.app.specialaccess.PictureInPictureListModel$transform$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(
                            FlowCollector flowCollector, Continuation continuation) {
                        Object collect =
                                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2.collect(
                                        new AnonymousClass2(flowCollector, this), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                ? collect
                                : Unit.INSTANCE;
                    }
                },
                flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1,
                new PictureInPictureListModel$transform$2(this, null));
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final AppRecord transformItem(ApplicationInfo app) {
        PackageInfo packageInfo;
        boolean z;
        Intrinsics.checkNotNullParameter(app, "app");
        boolean z2 = false;
        if (ApplicationInfosKt.getInstalled(app)) {
            try {
                packageInfo =
                        this.packageManager.getPackageInfoAsUser(
                                app.packageName,
                                GET_ACTIVITIES_FLAGS,
                                ApplicationInfosKt.getUserId(app));
            } catch (Exception e) {
                Log.e("PictureInPictureListModel", "Exception while getPackageInfoAsUser", e);
                packageInfo = null;
            }
            if (packageInfo != null) {
                ActivityInfo[] activityInfoArr = packageInfo.activities;
                if (activityInfoArr != null) {
                    for (ActivityInfo activityInfo : activityInfoArr) {
                        if (activityInfo.supportsPictureInPicture()) {
                            z = true;
                            break;
                        }
                    }
                }
                z = false;
                if (z) {
                    z2 = true;
                }
            }
        }
        return new PictureInPictureRecord(
                app, z2, new AppOpsController(this.context, app, APP_OPS));
    }
}
