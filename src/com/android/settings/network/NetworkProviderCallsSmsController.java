package com.android.settings.network;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.Utils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.spa.framework.util.FlowsKt;
import com.android.settingslib.spaprivileged.framework.common.BroadcastReceiverFlowKt;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0017\u0018\u00002\u00020\u0001BC\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007\u0012\u0014\b\u0002\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\u0007¢\u0006\u0002\u0010\rJ\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u000bH\u0016J/\u0010\u0018\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\fH\u0002¢\u0006\u0002\u0010\u001dJ&\u0010\u001e\u001a\u00020\u00052\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\b0 2\u0006\u0010!\u001a\u00020\u000b2\u0006\u0010\"\u001a\u00020\u000bH\u0007J\u000e\u0010#\u001a\u00020\u00142\u0006\u0010$\u001a\u00020%J\u0010\u0010&\u001a\u00020\u00142\u0006\u0010'\u001a\u00020(H\u0016J\"\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00050*2\u0012\u0010+\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0 0*H\u0002R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000¨\u0006,²\u0006\n\u0010-\u001a\u00020\u0010X\u008a\u0084\u0002"}, d2 = {"Lcom/android/settings/network/NetworkProviderCallsSmsController;", "Lcom/android/settings/core/BasePreferenceController;", "context", "Landroid/content/Context;", "preferenceKey", ApnSettings.MVNO_NONE, "getDisplayName", "Lkotlin/Function1;", "Landroid/telephony/SubscriptionInfo;", ApnSettings.MVNO_NONE, "isInService", ApnSettings.MVNO_NONE, ApnSettings.MVNO_NONE, "(Landroid/content/Context;Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "lazyViewModel", "Lkotlin/Lazy;", "Lcom/android/settings/network/SubscriptionInfoListViewModel;", "preference", "Lcom/android/settingslib/RestrictedPreference;", "displayPreference", ApnSettings.MVNO_NONE, "screen", "Landroidx/preference/PreferenceScreen;", "getAvailabilityStatus", "getPreferredStatus", "subId", "subsSize", "isCallPreferred", "isSmsPreferred", "(IIZZ)Ljava/lang/Integer;", "getSummary", "activeSubscriptionInfoList", ApnSettings.MVNO_NONE, "defaultVoiceSubscriptionId", "defaultSmsSubscriptionId", "init", "fragment", "Landroidx/fragment/app/Fragment;", "onViewCreated", "viewLifecycleOwner", "Landroidx/lifecycle/LifecycleOwner;", "summaryFlow", "Lkotlinx/coroutines/flow/Flow;", "subscriptionInfoListFlow", "applications__sources__apps__SecSettings__android_common__SecSettings-core", "viewModel"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes2.dex */
public class NetworkProviderCallsSmsController extends BasePreferenceController {
    public static final int $stable = 8;
    private final Function1 getDisplayName;
    private final Function1 isInService;
    private Lazy lazyViewModel;
    private RestrictedPreference preference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* renamed from: com.android.settings.network.NetworkProviderCallsSmsController$2, reason: invalid class name */
    public /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1 {
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            int intValue = ((Number) obj).intValue();
            IsInServiceImpl isInServiceImpl = (IsInServiceImpl) this.receiver;
            isInServiceImpl.getClass();
            return Boolean.valueOf(!SubscriptionManager.isValidSubscriptionId(intValue) ? false : Utils.isInService(isInServiceImpl.telephonyManager.createForSubscriptionId(intValue).getServiceState()));
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public NetworkProviderCallsSmsController(Context context, String preferenceKey) {
        this(context, preferenceKey, null, null, 12, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preferenceKey, "preferenceKey");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Integer getPreferredStatus(int subId, int subsSize, boolean isCallPreferred, boolean isSmsPreferred) {
        if (!((Boolean) this.isInService.invoke(Integer.valueOf(subId))).booleanValue()) {
            return Integer.valueOf(subsSize > 1 ? R.string.calls_sms_unavailable : R.string.calls_sms_temp_unavailable);
        }
        if (isCallPreferred && isSmsPreferred) {
            return Integer.valueOf(R.string.calls_sms_preferred);
        }
        if (isCallPreferred) {
            return Integer.valueOf(R.string.calls_sms_calls_preferred);
        }
        if (isSmsPreferred) {
            return Integer.valueOf(R.string.calls_sms_sms_preferred);
        }
        return null;
    }

    private static final SubscriptionInfoListViewModel onViewCreated$lambda$0(Lazy lazy) {
        return (SubscriptionInfoListViewModel) lazy.getValue();
    }

    private final Flow summaryFlow(Flow subscriptionInfoListFlow) {
        Context mContext = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        final ChannelLimitedFlowMerge merge = FlowKt.merge(new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null), BroadcastReceiverFlowKt.broadcastReceiverFlow(mContext, new IntentFilter("android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED")));
        final int i = 1;
        Flow buffer$default = FlowKt.buffer$default(new Flow() { // from class: com.android.settings.network.NetworkProviderCallsSmsControllerKt$defaultSmsSubscriptionFlow$$inlined$map$1

            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
            /* renamed from: com.android.settings.network.NetworkProviderCallsSmsControllerKt$defaultSmsSubscriptionFlow$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                /* renamed from: com.android.settings.network.NetworkProviderCallsSmsControllerKt$defaultSmsSubscriptionFlow$$inlined$map$1$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.settings.network.NetworkProviderCallsSmsControllerKt$defaultSmsSubscriptionFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.settings.network.NetworkProviderCallsSmsControllerKt$defaultSmsSubscriptionFlow$$inlined$map$1$2$1 r0 = (com.android.settings.network.NetworkProviderCallsSmsControllerKt$defaultSmsSubscriptionFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.settings.network.NetworkProviderCallsSmsControllerKt$defaultSmsSubscriptionFlow$$inlined$map$1$2$1 r0 = new com.android.settings.network.NetworkProviderCallsSmsControllerKt$defaultSmsSubscriptionFlow$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        android.content.Intent r5 = (android.content.Intent) r5
                        int r5 = android.telephony.SubscriptionManager.getDefaultSmsSubscriptionId()
                        java.lang.Integer r6 = new java.lang.Integer
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settings.network.NetworkProviderCallsSmsControllerKt$defaultSmsSubscriptionFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                switch (i) {
                    case 0:
                        Object collect = merge.collect(new AnonymousClass2(flowCollector), continuation);
                        if (collect != CoroutineSingletons.COROUTINE_SUSPENDED) {
                            break;
                        }
                        break;
                    default:
                        Object collect2 = merge.collect(new NetworkProviderCallsSmsControllerKt$defaultVoiceSubscriptionFlow$$inlined$map$1$2(flowCollector), continuation);
                        if (collect2 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                            break;
                        }
                        break;
                }
                return Unit.INSTANCE;
            }
        }, -1);
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        Flow flowOn = FlowKt.flowOn(buffer$default, defaultScheduler);
        Context mContext2 = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext2, "mContext");
        final ChannelLimitedFlowMerge merge2 = FlowKt.merge(new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null), BroadcastReceiverFlowKt.broadcastReceiverFlow(mContext2, new IntentFilter("android.telephony.action.DEFAULT_SMS_SUBSCRIPTION_CHANGED")));
        final int i2 = 0;
        return FlowKt.flowOn(FlowKt.combine(subscriptionInfoListFlow, flowOn, FlowKt.flowOn(FlowKt.buffer$default(new Flow() { // from class: com.android.settings.network.NetworkProviderCallsSmsControllerKt$defaultSmsSubscriptionFlow$$inlined$map$1

            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
            /* renamed from: com.android.settings.network.NetworkProviderCallsSmsControllerKt$defaultSmsSubscriptionFlow$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                /* renamed from: com.android.settings.network.NetworkProviderCallsSmsControllerKt$defaultSmsSubscriptionFlow$$inlined$map$1$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj, Continuation continuation) {
                    /*
                        this = this;
                        boolean r0 = r6 instanceof com.android.settings.network.NetworkProviderCallsSmsControllerKt$defaultSmsSubscriptionFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.settings.network.NetworkProviderCallsSmsControllerKt$defaultSmsSubscriptionFlow$$inlined$map$1$2$1 r0 = (com.android.settings.network.NetworkProviderCallsSmsControllerKt$defaultSmsSubscriptionFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.settings.network.NetworkProviderCallsSmsControllerKt$defaultSmsSubscriptionFlow$$inlined$map$1$2$1 r0 = new com.android.settings.network.NetworkProviderCallsSmsControllerKt$defaultSmsSubscriptionFlow$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        android.content.Intent r5 = (android.content.Intent) r5
                        int r5 = android.telephony.SubscriptionManager.getDefaultSmsSubscriptionId()
                        java.lang.Integer r6 = new java.lang.Integer
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settings.network.NetworkProviderCallsSmsControllerKt$defaultSmsSubscriptionFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                switch (i2) {
                    case 0:
                        Object collect = merge2.collect(new AnonymousClass2(flowCollector), continuation);
                        if (collect != CoroutineSingletons.COROUTINE_SUSPENDED) {
                            break;
                        }
                        break;
                    default:
                        Object collect2 = merge2.collect(new NetworkProviderCallsSmsControllerKt$defaultVoiceSubscriptionFlow$$inlined$map$1$2(flowCollector), continuation);
                        if (collect2 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                            break;
                        }
                        break;
                }
                return Unit.INSTANCE;
            }
        }, -1), defaultScheduler), new NetworkProviderCallsSmsController$summaryFlow$1(4, this, NetworkProviderCallsSmsController.class, "getSummary", "getSummary(Ljava/util/List;II)Ljava/lang/String;", 4)), defaultScheduler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ Object summaryFlow$getSummary(NetworkProviderCallsSmsController networkProviderCallsSmsController, List list, int i, int i2, Continuation continuation) {
        return networkProviderCallsSmsController.getSummary(list, i, i2);
    }

    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        Preference findPreference = screen.findPreference(getPreferenceKey());
        Intrinsics.checkNotNull(findPreference);
        this.preference = (RestrictedPreference) findPreference;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    public final String getSummary(final List<? extends SubscriptionInfo> activeSubscriptionInfoList, final int defaultVoiceSubscriptionId, final int defaultSmsSubscriptionId) {
        Intrinsics.checkNotNullParameter(activeSubscriptionInfoList, "activeSubscriptionInfoList");
        if (!activeSubscriptionInfoList.isEmpty()) {
            SubscriptionInfo subscriptionInfo = (SubscriptionInfo) CollectionsKt___CollectionsKt.singleOrNull((List) activeSubscriptionInfoList);
            return (subscriptionInfo == null || !((Boolean) this.isInService.invoke(Integer.valueOf(subscriptionInfo.getSubscriptionId()))).booleanValue()) ? CollectionsKt___CollectionsKt.joinToString$default(activeSubscriptionInfoList, null, null, null, new Function1() { // from class: com.android.settings.network.NetworkProviderCallsSmsController$getSummary$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Function1 function1;
                    Integer preferredStatus;
                    Context context;
                    SubscriptionInfo subInfo = (SubscriptionInfo) obj;
                    Intrinsics.checkNotNullParameter(subInfo, "subInfo");
                    function1 = NetworkProviderCallsSmsController.this.getDisplayName;
                    CharSequence charSequence = (CharSequence) function1.invoke(subInfo);
                    int subscriptionId = subInfo.getSubscriptionId();
                    preferredStatus = NetworkProviderCallsSmsController.this.getPreferredStatus(subscriptionId, activeSubscriptionInfoList.size(), subscriptionId == defaultVoiceSubscriptionId, subscriptionId == defaultSmsSubscriptionId);
                    if (preferredStatus == null) {
                        return charSequence;
                    }
                    context = ((AbstractPreferenceController) NetworkProviderCallsSmsController.this).mContext;
                    return ((Object) charSequence) + " (" + context.getString(preferredStatus.intValue()) + ")";
                }
            }, 31) : subscriptionInfo.getDisplayName().toString();
        }
        String string = this.mContext.getString(R.string.calls_sms_no_sim);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.network.NetworkProviderCallsSmsController$init$$inlined$viewModels$default$1] */
    public final void init(final Fragment fragment) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        final ?? r0 = new Function0() { // from class: com.android.settings.network.NetworkProviderCallsSmsController$init$$inlined$viewModels$default$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return Fragment.this;
            }
        };
        final Lazy lazy = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, new Function0() { // from class: com.android.settings.network.NetworkProviderCallsSmsController$init$$inlined$viewModels$default$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return (ViewModelStoreOwner) r0.mo1068invoke();
            }
        });
        this.lazyViewModel = new ViewModelLazy(Reflection.factory.getOrCreateKotlinClass(SubscriptionInfoListViewModel.class), new Function0() { // from class: com.android.settings.network.NetworkProviderCallsSmsController$init$$inlined$viewModels$default$3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return ((ViewModelStoreOwner) Lazy.this.getValue()).getViewModelStore();
            }
        }, new Function0() { // from class: com.android.settings.network.NetworkProviderCallsSmsController$init$$inlined$viewModels$default$5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory;
                ViewModelStoreOwner viewModelStoreOwner = (ViewModelStoreOwner) lazy.getValue();
                HasDefaultViewModelProviderFactory hasDefaultViewModelProviderFactory = viewModelStoreOwner instanceof HasDefaultViewModelProviderFactory ? (HasDefaultViewModelProviderFactory) viewModelStoreOwner : null;
                return (hasDefaultViewModelProviderFactory == null || (defaultViewModelProviderFactory = hasDefaultViewModelProviderFactory.getDefaultViewModelProviderFactory()) == null) ? Fragment.this.getDefaultViewModelProviderFactory() : defaultViewModelProviderFactory;
            }
        }, new Function0() { // from class: com.android.settings.network.NetworkProviderCallsSmsController$init$$inlined$viewModels$default$4
            final /* synthetic */ Function0 $extrasProducer = null;

            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                CreationExtras creationExtras;
                Function0 function0 = this.$extrasProducer;
                if (function0 != null && (creationExtras = (CreationExtras) function0.mo1068invoke()) != null) {
                    return creationExtras;
                }
                ViewModelStoreOwner viewModelStoreOwner = (ViewModelStoreOwner) Lazy.this.getValue();
                HasDefaultViewModelProviderFactory hasDefaultViewModelProviderFactory = viewModelStoreOwner instanceof HasDefaultViewModelProviderFactory ? (HasDefaultViewModelProviderFactory) viewModelStoreOwner : null;
                return hasDefaultViewModelProviderFactory != null ? hasDefaultViewModelProviderFactory.getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
            }
        });
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void onViewCreated(LifecycleOwner viewLifecycleOwner) {
        Intrinsics.checkNotNullParameter(viewLifecycleOwner, "viewLifecycleOwner");
        Lazy lazy = this.lazyViewModel;
        if (lazy == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lazyViewModel");
            throw null;
        }
        FlowsKt.collectLatestWithLifecycle(summaryFlow(onViewCreated$lambda$0(lazy).subscriptionInfoListFlow), viewLifecycleOwner, Lifecycle.State.STARTED, new NetworkProviderCallsSmsController$onViewCreated$1(this, null));
        FlowsKt.collectLatestWithLifecycle(onViewCreated$lambda$0(lazy).subscriptionInfoListFlow, viewLifecycleOwner, Lifecycle.State.STARTED, new NetworkProviderCallsSmsController$onViewCreated$2(this, null));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public NetworkProviderCallsSmsController(Context context, String preferenceKey, Function1 getDisplayName) {
        this(context, preferenceKey, getDisplayName, null, 8, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preferenceKey, "preferenceKey");
        Intrinsics.checkNotNullParameter(getDisplayName, "getDisplayName");
    }

    public NetworkProviderCallsSmsController(final Context context, String str, Function1 function1, Function1 function12, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, str, (i & 4) != 0 ? new Function1() { // from class: com.android.settings.network.NetworkProviderCallsSmsController.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                SubscriptionInfo subInfo = (SubscriptionInfo) obj;
                Intrinsics.checkNotNullParameter(subInfo, "subInfo");
                CharSequence uniqueSubscriptionDisplayName = SubscriptionUtil.getUniqueSubscriptionDisplayName(context, subInfo);
                Intrinsics.checkNotNullExpressionValue(uniqueSubscriptionDisplayName, "getUniqueSubscriptionDisplayName(...)");
                return uniqueSubscriptionDisplayName;
            }
        } : function1, (i & 8) != 0 ? new AnonymousClass2(1, new IsInServiceImpl(context), IsInServiceImpl.class, "isInService", "isInService(I)Z", 0) : function12);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetworkProviderCallsSmsController(Context context, String preferenceKey, Function1 getDisplayName, Function1 isInService) {
        super(context, preferenceKey);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preferenceKey, "preferenceKey");
        Intrinsics.checkNotNullParameter(getDisplayName, "getDisplayName");
        Intrinsics.checkNotNullParameter(isInService, "isInService");
        this.getDisplayName = getDisplayName;
        this.isInService = isInService;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
    }
}
