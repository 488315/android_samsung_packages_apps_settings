package com.android.settings.datausage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.lifecycle.compose.FlowExtKt;

import com.android.settings.R;
import com.android.settings.datausage.lib.DataUsageFormatter;
import com.android.settings.datausage.lib.NetworkUsageDetailsData;
import com.android.settings.spa.preference.ComposePreferenceController;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.settingslib.spaprivileged.framework.compose.StringResourcesKt;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000D\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0004\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\r"
                + "\u0010\u0012\u001a\u00020\u0013H\u0017¢\u0006\u0002\u0010\u0014J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u000e\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\fR\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020"
                + "\t0\bX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u0014\u0010\n"
                + "\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\r"
                + "\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u000f\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020"
                + "\t0\bX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020"
                + "\t0\bX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000¨\u0006\u0019²\u0006\n"
                + "\u0010\u001a\u001a\u00020\tX\u008a\u0084\u0002²\u0006\n"
                + "\u0010\u001b\u001a\u00020\tX\u008a\u0084\u0002²\u0006\n"
                + "\u0010\u001c\u001a\u00020\tX\u008a\u0084\u0002"
        },
        d2 = {
            "Lcom/android/settings/datausage/AppDataUsageSummaryController;",
            "Lcom/android/settings/spa/preference/ComposePreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "backgroundUsageFlow",
            "Lkotlinx/coroutines/flow/Flow;",
            "Lcom/android/settings/datausage/lib/DataUsageFormatter$FormattedDataUsage;",
            "dataFlow",
            "Lkotlinx/coroutines/flow/MutableStateFlow;",
            "Lcom/android/settings/datausage/lib/NetworkUsageDetailsData;",
            "dataUsageFormatter",
            "Lcom/android/settings/datausage/lib/DataUsageFormatter;",
            "emptyDataUsage",
            "foregroundUsageFlow",
            "totalUsageFlow",
            "Content",
            ApnSettings.MVNO_NONE,
            "(Landroidx/compose/runtime/Composer;I)V",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "update",
            "data",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core",
            "totalUsage",
            "foregroundUsage",
            "backgroundUsage"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class AppDataUsageSummaryController extends ComposePreferenceController {
    public static final int $stable = 8;
    private final Flow backgroundUsageFlow;
    private final MutableStateFlow dataFlow;
    private final DataUsageFormatter dataUsageFormatter;
    private final DataUsageFormatter.FormattedDataUsage emptyDataUsage;
    private final Flow foregroundUsageFlow;
    private final Flow totalUsageFlow;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppDataUsageSummaryController(Context context, String preferenceKey) {
        super(context, preferenceKey);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preferenceKey, "preferenceKey");
        final StateFlowImpl MutableStateFlow =
                StateFlowKt.MutableStateFlow(NetworkUsageDetailsData.AllZero);
        this.dataFlow = MutableStateFlow;
        this.dataUsageFormatter = new DataUsageFormatter(context);
        this.emptyDataUsage =
                new DataUsageFormatter.FormattedDataUsage(
                        StringResourcesKt.getPlaceholder(context),
                        StringResourcesKt.getPlaceholder(context));
        final int i = 0;
        this.totalUsageFlow =
                new Flow() { // from class:
                    // com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ AppDataUsageSummaryController this$0;

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1$2$1, reason: invalid class name */
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
                                AppDataUsageSummaryController appDataUsageSummaryController) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = appDataUsageSummaryController;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(
                                java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                            /*
                                r6 = this;
                                boolean r0 = r8 instanceof com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r8
                                com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1$2$1 r0 = (com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1$2$1 r0 = new com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1$2$1
                                r0.<init>(r8)
                            L18:
                                java.lang.Object r8 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r8)
                                goto L4b
                            L27:
                                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                                r6.<init>(r7)
                                throw r6
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r8)
                                com.android.settings.datausage.lib.NetworkUsageDetailsData r7 = (com.android.settings.datausage.lib.NetworkUsageDetailsData) r7
                                com.android.settings.datausage.AppDataUsageSummaryController r8 = r6.this$0
                                com.android.settings.datausage.lib.DataUsageFormatter r8 = com.android.settings.datausage.AppDataUsageSummaryController.access$getDataUsageFormatter$p(r8)
                                long r4 = r7.totalUsage
                                com.android.settings.datausage.lib.DataUsageFormatter$FormattedDataUsage r7 = r8.formatDataUsage(r4)
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                                java.lang.Object r6 = r6.emit(r7, r0)
                                if (r6 != r1) goto L4b
                                return r1
                            L4b:
                                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                return r6
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(
                            FlowCollector flowCollector, Continuation continuation) {
                        switch (i) {
                            case 0:
                                Object collect =
                                        MutableStateFlow.collect(
                                                new AnonymousClass2(flowCollector, this),
                                                continuation);
                                if (collect != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                            case 1:
                                Object collect2 =
                                        MutableStateFlow.collect(
                                                new AppDataUsageSummaryController$special$$inlined$map$2$2(
                                                        flowCollector, this),
                                                continuation);
                                if (collect2 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                            default:
                                Object collect3 =
                                        MutableStateFlow.collect(
                                                new AppDataUsageSummaryController$special$$inlined$map$3$2(
                                                        flowCollector, this),
                                                continuation);
                                if (collect3 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                };
        final int i2 = 1;
        this.foregroundUsageFlow =
                new Flow() { // from class:
                    // com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ AppDataUsageSummaryController this$0;

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1$2$1, reason: invalid class name */
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
                                AppDataUsageSummaryController appDataUsageSummaryController) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = appDataUsageSummaryController;
                        }

                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj, Continuation continuation) {
                            /*
                                this = this;
                                boolean r0 = r8 instanceof com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r8
                                com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1$2$1 r0 = (com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1$2$1 r0 = new com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1$2$1
                                r0.<init>(r8)
                            L18:
                                java.lang.Object r8 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r8)
                                goto L4b
                            L27:
                                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                                r6.<init>(r7)
                                throw r6
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r8)
                                com.android.settings.datausage.lib.NetworkUsageDetailsData r7 = (com.android.settings.datausage.lib.NetworkUsageDetailsData) r7
                                com.android.settings.datausage.AppDataUsageSummaryController r8 = r6.this$0
                                com.android.settings.datausage.lib.DataUsageFormatter r8 = com.android.settings.datausage.AppDataUsageSummaryController.access$getDataUsageFormatter$p(r8)
                                long r4 = r7.totalUsage
                                com.android.settings.datausage.lib.DataUsageFormatter$FormattedDataUsage r7 = r8.formatDataUsage(r4)
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                                java.lang.Object r6 = r6.emit(r7, r0)
                                if (r6 != r1) goto L4b
                                return r1
                            L4b:
                                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                return r6
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(
                            FlowCollector flowCollector, Continuation continuation) {
                        switch (i2) {
                            case 0:
                                Object collect =
                                        MutableStateFlow.collect(
                                                new AnonymousClass2(flowCollector, this),
                                                continuation);
                                if (collect != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                            case 1:
                                Object collect2 =
                                        MutableStateFlow.collect(
                                                new AppDataUsageSummaryController$special$$inlined$map$2$2(
                                                        flowCollector, this),
                                                continuation);
                                if (collect2 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                            default:
                                Object collect3 =
                                        MutableStateFlow.collect(
                                                new AppDataUsageSummaryController$special$$inlined$map$3$2(
                                                        flowCollector, this),
                                                continuation);
                                if (collect3 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                };
        final int i3 = 2;
        this.backgroundUsageFlow =
                new Flow() { // from class:
                    // com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ AppDataUsageSummaryController this$0;

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1$2$1, reason: invalid class name */
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
                                AppDataUsageSummaryController appDataUsageSummaryController) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = appDataUsageSummaryController;
                        }

                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj, Continuation continuation) {
                            /*
                                this = this;
                                boolean r0 = r8 instanceof com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r8
                                com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1$2$1 r0 = (com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1$2$1 r0 = new com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1$2$1
                                r0.<init>(r8)
                            L18:
                                java.lang.Object r8 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r8)
                                goto L4b
                            L27:
                                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                                r6.<init>(r7)
                                throw r6
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r8)
                                com.android.settings.datausage.lib.NetworkUsageDetailsData r7 = (com.android.settings.datausage.lib.NetworkUsageDetailsData) r7
                                com.android.settings.datausage.AppDataUsageSummaryController r8 = r6.this$0
                                com.android.settings.datausage.lib.DataUsageFormatter r8 = com.android.settings.datausage.AppDataUsageSummaryController.access$getDataUsageFormatter$p(r8)
                                long r4 = r7.totalUsage
                                com.android.settings.datausage.lib.DataUsageFormatter$FormattedDataUsage r7 = r8.formatDataUsage(r4)
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                                java.lang.Object r6 = r6.emit(r7, r0)
                                if (r6 != r1) goto L4b
                                return r1
                            L4b:
                                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                return r6
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.settings.datausage.AppDataUsageSummaryController$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(
                            FlowCollector flowCollector, Continuation continuation) {
                        switch (i3) {
                            case 0:
                                Object collect =
                                        MutableStateFlow.collect(
                                                new AnonymousClass2(flowCollector, this),
                                                continuation);
                                if (collect != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                            case 1:
                                Object collect2 =
                                        MutableStateFlow.collect(
                                                new AppDataUsageSummaryController$special$$inlined$map$2$2(
                                                        flowCollector, this),
                                                continuation);
                                if (collect2 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                            default:
                                Object collect3 =
                                        MutableStateFlow.collect(
                                                new AppDataUsageSummaryController$special$$inlined$map$3$2(
                                                        flowCollector, this),
                                                continuation);
                                if (collect3 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final DataUsageFormatter.FormattedDataUsage Content$lambda$6$lambda$3(
            State state) {
        return (DataUsageFormatter.FormattedDataUsage) state.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final DataUsageFormatter.FormattedDataUsage Content$lambda$6$lambda$4(
            State state) {
        return (DataUsageFormatter.FormattedDataUsage) state.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final DataUsageFormatter.FormattedDataUsage Content$lambda$6$lambda$5(
            State state) {
        return (DataUsageFormatter.FormattedDataUsage) state.getValue();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController
    public void Content(Composer composer, final int i) {
        final int i2 = 1;
        final int i3 = 2;
        final int i4 = 0;
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-564637827);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
        ColumnMeasurePolicy columnMeasurePolicy =
                ColumnKt.columnMeasurePolicy(
                        Arrangement.Top, Alignment.Companion.Start, composerImpl, 0);
        int i5 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope =
                composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier =
                ComposedModifierKt.materializeModifier(composerImpl, companion);
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
                || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i5))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i5, composerImpl, i5, function2);
        }
        Updater.m221setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        final MutableState collectAsStateWithLifecycle =
                FlowExtKt.collectAsStateWithLifecycle(
                        this.totalUsageFlow, this.emptyDataUsage, composerImpl, 8);
        final MutableState collectAsStateWithLifecycle2 =
                FlowExtKt.collectAsStateWithLifecycle(
                        this.foregroundUsageFlow, this.emptyDataUsage, composerImpl, 8);
        final MutableState collectAsStateWithLifecycle3 =
                FlowExtKt.collectAsStateWithLifecycle(
                        this.backgroundUsageFlow, this.emptyDataUsage, composerImpl, 8);
        PreferenceKt.Preference(
                new PreferenceModel(
                        composerImpl,
                        collectAsStateWithLifecycle,
                        i4) { // from class:
                              // com.android.settings.datausage.AppDataUsageSummaryController$Content$1$1
                    public final /* synthetic */ int $r8$classId;
                    public final Function0 summary;
                    public final Function0 summaryContentDescription;
                    public final String title;

                    {
                        this.$r8$classId = i4;
                        switch (i4) {
                            case 1:
                                this.title =
                                        StringResources_androidKt.stringResource(
                                                composerImpl, R.string.data_usage_label_foreground);
                                this.summary =
                                        new Function0() { // from class:
                                                          // com.android.settings.datausage.AppDataUsageSummaryController$Content$1$2$summary$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                DataUsageFormatter.FormattedDataUsage
                                                        Content$lambda$6$lambda$4;
                                                Content$lambda$6$lambda$4 =
                                                        AppDataUsageSummaryController
                                                                .Content$lambda$6$lambda$4(
                                                                        collectAsStateWithLifecycle);
                                                return Content$lambda$6$lambda$4.displayText;
                                            }
                                        };
                                this.summaryContentDescription =
                                        new Function0() { // from class:
                                                          // com.android.settings.datausage.AppDataUsageSummaryController$Content$1$2$summaryContentDescription$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                DataUsageFormatter.FormattedDataUsage
                                                        Content$lambda$6$lambda$4;
                                                Content$lambda$6$lambda$4 =
                                                        AppDataUsageSummaryController
                                                                .Content$lambda$6$lambda$4(
                                                                        collectAsStateWithLifecycle);
                                                return Content$lambda$6$lambda$4.contentDescription;
                                            }
                                        };
                                break;
                            case 2:
                                this.title =
                                        StringResources_androidKt.stringResource(
                                                composerImpl, R.string.data_usage_label_background);
                                this.summary =
                                        new Function0() { // from class:
                                                          // com.android.settings.datausage.AppDataUsageSummaryController$Content$1$3$summary$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                DataUsageFormatter.FormattedDataUsage
                                                        Content$lambda$6$lambda$5;
                                                Content$lambda$6$lambda$5 =
                                                        AppDataUsageSummaryController
                                                                .Content$lambda$6$lambda$5(
                                                                        collectAsStateWithLifecycle);
                                                return Content$lambda$6$lambda$5.displayText;
                                            }
                                        };
                                this.summaryContentDescription =
                                        new Function0() { // from class:
                                                          // com.android.settings.datausage.AppDataUsageSummaryController$Content$1$3$summaryContentDescription$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                DataUsageFormatter.FormattedDataUsage
                                                        Content$lambda$6$lambda$5;
                                                Content$lambda$6$lambda$5 =
                                                        AppDataUsageSummaryController
                                                                .Content$lambda$6$lambda$5(
                                                                        collectAsStateWithLifecycle);
                                                return Content$lambda$6$lambda$5.contentDescription;
                                            }
                                        };
                                break;
                            default:
                                this.title =
                                        StringResources_androidKt.stringResource(
                                                composerImpl, R.string.total_size_label);
                                this.summary =
                                        new Function0() { // from class:
                                                          // com.android.settings.datausage.AppDataUsageSummaryController$Content$1$1$summary$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                DataUsageFormatter.FormattedDataUsage
                                                        Content$lambda$6$lambda$3;
                                                Content$lambda$6$lambda$3 =
                                                        AppDataUsageSummaryController
                                                                .Content$lambda$6$lambda$3(
                                                                        collectAsStateWithLifecycle);
                                                return Content$lambda$6$lambda$3.displayText;
                                            }
                                        };
                                this.summaryContentDescription =
                                        new Function0() { // from class:
                                                          // com.android.settings.datausage.AppDataUsageSummaryController$Content$1$1$summaryContentDescription$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                DataUsageFormatter.FormattedDataUsage
                                                        Content$lambda$6$lambda$3;
                                                Content$lambda$6$lambda$3 =
                                                        AppDataUsageSummaryController
                                                                .Content$lambda$6$lambda$3(
                                                                        collectAsStateWithLifecycle);
                                                return Content$lambda$6$lambda$3.contentDescription;
                                            }
                                        };
                                break;
                        }
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function0 getSummary() {
                        switch (this.$r8$classId) {
                        }
                        return this.summary;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function0 getSummaryContentDescription() {
                        switch (this.$r8$classId) {
                        }
                        return this.summaryContentDescription;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final String getTitle() {
                        switch (this.$r8$classId) {
                        }
                        return this.title;
                    }
                },
                false,
                composerImpl,
                0,
                2);
        PreferenceKt.Preference(
                new PreferenceModel(
                        composerImpl,
                        collectAsStateWithLifecycle2,
                        i2) { // from class:
                              // com.android.settings.datausage.AppDataUsageSummaryController$Content$1$1
                    public final /* synthetic */ int $r8$classId;
                    public final Function0 summary;
                    public final Function0 summaryContentDescription;
                    public final String title;

                    {
                        this.$r8$classId = i2;
                        switch (i2) {
                            case 1:
                                this.title =
                                        StringResources_androidKt.stringResource(
                                                composerImpl, R.string.data_usage_label_foreground);
                                this.summary =
                                        new Function0() { // from class:
                                                          // com.android.settings.datausage.AppDataUsageSummaryController$Content$1$2$summary$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                DataUsageFormatter.FormattedDataUsage
                                                        Content$lambda$6$lambda$4;
                                                Content$lambda$6$lambda$4 =
                                                        AppDataUsageSummaryController
                                                                .Content$lambda$6$lambda$4(
                                                                        collectAsStateWithLifecycle2);
                                                return Content$lambda$6$lambda$4.displayText;
                                            }
                                        };
                                this.summaryContentDescription =
                                        new Function0() { // from class:
                                                          // com.android.settings.datausage.AppDataUsageSummaryController$Content$1$2$summaryContentDescription$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                DataUsageFormatter.FormattedDataUsage
                                                        Content$lambda$6$lambda$4;
                                                Content$lambda$6$lambda$4 =
                                                        AppDataUsageSummaryController
                                                                .Content$lambda$6$lambda$4(
                                                                        collectAsStateWithLifecycle2);
                                                return Content$lambda$6$lambda$4.contentDescription;
                                            }
                                        };
                                break;
                            case 2:
                                this.title =
                                        StringResources_androidKt.stringResource(
                                                composerImpl, R.string.data_usage_label_background);
                                this.summary =
                                        new Function0() { // from class:
                                                          // com.android.settings.datausage.AppDataUsageSummaryController$Content$1$3$summary$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                DataUsageFormatter.FormattedDataUsage
                                                        Content$lambda$6$lambda$5;
                                                Content$lambda$6$lambda$5 =
                                                        AppDataUsageSummaryController
                                                                .Content$lambda$6$lambda$5(
                                                                        collectAsStateWithLifecycle2);
                                                return Content$lambda$6$lambda$5.displayText;
                                            }
                                        };
                                this.summaryContentDescription =
                                        new Function0() { // from class:
                                                          // com.android.settings.datausage.AppDataUsageSummaryController$Content$1$3$summaryContentDescription$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                DataUsageFormatter.FormattedDataUsage
                                                        Content$lambda$6$lambda$5;
                                                Content$lambda$6$lambda$5 =
                                                        AppDataUsageSummaryController
                                                                .Content$lambda$6$lambda$5(
                                                                        collectAsStateWithLifecycle2);
                                                return Content$lambda$6$lambda$5.contentDescription;
                                            }
                                        };
                                break;
                            default:
                                this.title =
                                        StringResources_androidKt.stringResource(
                                                composerImpl, R.string.total_size_label);
                                this.summary =
                                        new Function0() { // from class:
                                                          // com.android.settings.datausage.AppDataUsageSummaryController$Content$1$1$summary$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                DataUsageFormatter.FormattedDataUsage
                                                        Content$lambda$6$lambda$3;
                                                Content$lambda$6$lambda$3 =
                                                        AppDataUsageSummaryController
                                                                .Content$lambda$6$lambda$3(
                                                                        collectAsStateWithLifecycle2);
                                                return Content$lambda$6$lambda$3.displayText;
                                            }
                                        };
                                this.summaryContentDescription =
                                        new Function0() { // from class:
                                                          // com.android.settings.datausage.AppDataUsageSummaryController$Content$1$1$summaryContentDescription$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                DataUsageFormatter.FormattedDataUsage
                                                        Content$lambda$6$lambda$3;
                                                Content$lambda$6$lambda$3 =
                                                        AppDataUsageSummaryController
                                                                .Content$lambda$6$lambda$3(
                                                                        collectAsStateWithLifecycle2);
                                                return Content$lambda$6$lambda$3.contentDescription;
                                            }
                                        };
                                break;
                        }
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function0 getSummary() {
                        switch (this.$r8$classId) {
                        }
                        return this.summary;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function0 getSummaryContentDescription() {
                        switch (this.$r8$classId) {
                        }
                        return this.summaryContentDescription;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final String getTitle() {
                        switch (this.$r8$classId) {
                        }
                        return this.title;
                    }
                },
                false,
                composerImpl,
                0,
                2);
        PreferenceKt.Preference(
                new PreferenceModel(
                        composerImpl,
                        collectAsStateWithLifecycle3,
                        i3) { // from class:
                              // com.android.settings.datausage.AppDataUsageSummaryController$Content$1$1
                    public final /* synthetic */ int $r8$classId;
                    public final Function0 summary;
                    public final Function0 summaryContentDescription;
                    public final String title;

                    {
                        this.$r8$classId = i3;
                        switch (i3) {
                            case 1:
                                this.title =
                                        StringResources_androidKt.stringResource(
                                                composerImpl, R.string.data_usage_label_foreground);
                                this.summary =
                                        new Function0() { // from class:
                                                          // com.android.settings.datausage.AppDataUsageSummaryController$Content$1$2$summary$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                DataUsageFormatter.FormattedDataUsage
                                                        Content$lambda$6$lambda$4;
                                                Content$lambda$6$lambda$4 =
                                                        AppDataUsageSummaryController
                                                                .Content$lambda$6$lambda$4(
                                                                        collectAsStateWithLifecycle3);
                                                return Content$lambda$6$lambda$4.displayText;
                                            }
                                        };
                                this.summaryContentDescription =
                                        new Function0() { // from class:
                                                          // com.android.settings.datausage.AppDataUsageSummaryController$Content$1$2$summaryContentDescription$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                DataUsageFormatter.FormattedDataUsage
                                                        Content$lambda$6$lambda$4;
                                                Content$lambda$6$lambda$4 =
                                                        AppDataUsageSummaryController
                                                                .Content$lambda$6$lambda$4(
                                                                        collectAsStateWithLifecycle3);
                                                return Content$lambda$6$lambda$4.contentDescription;
                                            }
                                        };
                                break;
                            case 2:
                                this.title =
                                        StringResources_androidKt.stringResource(
                                                composerImpl, R.string.data_usage_label_background);
                                this.summary =
                                        new Function0() { // from class:
                                                          // com.android.settings.datausage.AppDataUsageSummaryController$Content$1$3$summary$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                DataUsageFormatter.FormattedDataUsage
                                                        Content$lambda$6$lambda$5;
                                                Content$lambda$6$lambda$5 =
                                                        AppDataUsageSummaryController
                                                                .Content$lambda$6$lambda$5(
                                                                        collectAsStateWithLifecycle3);
                                                return Content$lambda$6$lambda$5.displayText;
                                            }
                                        };
                                this.summaryContentDescription =
                                        new Function0() { // from class:
                                                          // com.android.settings.datausage.AppDataUsageSummaryController$Content$1$3$summaryContentDescription$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                DataUsageFormatter.FormattedDataUsage
                                                        Content$lambda$6$lambda$5;
                                                Content$lambda$6$lambda$5 =
                                                        AppDataUsageSummaryController
                                                                .Content$lambda$6$lambda$5(
                                                                        collectAsStateWithLifecycle3);
                                                return Content$lambda$6$lambda$5.contentDescription;
                                            }
                                        };
                                break;
                            default:
                                this.title =
                                        StringResources_androidKt.stringResource(
                                                composerImpl, R.string.total_size_label);
                                this.summary =
                                        new Function0() { // from class:
                                                          // com.android.settings.datausage.AppDataUsageSummaryController$Content$1$1$summary$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                DataUsageFormatter.FormattedDataUsage
                                                        Content$lambda$6$lambda$3;
                                                Content$lambda$6$lambda$3 =
                                                        AppDataUsageSummaryController
                                                                .Content$lambda$6$lambda$3(
                                                                        collectAsStateWithLifecycle3);
                                                return Content$lambda$6$lambda$3.displayText;
                                            }
                                        };
                                this.summaryContentDescription =
                                        new Function0() { // from class:
                                                          // com.android.settings.datausage.AppDataUsageSummaryController$Content$1$1$summaryContentDescription$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final Object mo1068invoke() {
                                                DataUsageFormatter.FormattedDataUsage
                                                        Content$lambda$6$lambda$3;
                                                Content$lambda$6$lambda$3 =
                                                        AppDataUsageSummaryController
                                                                .Content$lambda$6$lambda$3(
                                                                        collectAsStateWithLifecycle3);
                                                return Content$lambda$6$lambda$3.contentDescription;
                                            }
                                        };
                                break;
                        }
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function0 getSummary() {
                        switch (this.$r8$classId) {
                        }
                        return this.summary;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function0 getSummaryContentDescription() {
                        switch (this.$r8$classId) {
                        }
                        return this.summaryContentDescription;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final String getTitle() {
                        switch (this.$r8$classId) {
                        }
                        return this.title;
                    }
                },
                false,
                composerImpl,
                0,
                2);
        composerImpl.end(true);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.datausage.AppDataUsageSummaryController$Content$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppDataUsageSummaryController.this.Content(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    public final void update(NetworkUsageDetailsData data) {
        Intrinsics.checkNotNullParameter(data, "data");
        ((StateFlowImpl) this.dataFlow).updateState(null, data);
    }

    @Override // com.android.settings.spa.preference.ComposePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
