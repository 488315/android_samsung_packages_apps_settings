package com.android.settings.network.telephony;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.UiccCardInfo;
import android.telephony.euicc.EuiccManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import com.android.settings.R;
import com.android.settings.network.SubscriptionInfoListViewModel;
import com.android.settingslib.CustomDialogPreferenceCompat;
import com.android.settingslib.spa.framework.util.FlowsKt;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\b\u0017\u0018\u0000 12\u00020\u0001:\u00011B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H\u0016J\u0012\u0010\u0019\u001a\u00020\u00052\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0002J\u0010\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u001eH\u0007J \u0010\u001f\u001a\u00020\u00052\u0006\u0010 \u001a\u00020!2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0012\u0010\"\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u0002\u001a\u00020\u0003H\u0004J\u0012\u0010#\u001a\u0004\u0018\u00010!2\u0006\u0010\u0002\u001a\u00020\u0003H\u0004J\b\u0010$\u001a\u00020\u0005H\u0002J\u0010\u0010%\u001a\u00020&2\u0006\u0010\u000f\u001a\u00020'H\u0016J\u0016\u0010(\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u0017J\u0010\u0010)\u001a\u00020\u00132\u0006\u0010*\u001a\u00020+H\u0016J\u0016\u0010,\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u001eH\u0087@¢\u0006\u0002\u0010-J\u0006\u0010.\u001a\u00020\u0013J\u000e\u0010/\u001a\u00020\u0013H\u0082@¢\u0006\u0002\u00100R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u00062²\u0006\n\u00103\u001a\u00020\u000eX\u008a\u0084\u0002"}, d2 = {"Lcom/android/settings/network/telephony/MobileNetworkEidPreferenceController;", "Lcom/android/settings/network/telephony/TelephonyBasePreferenceController;", "context", "Landroid/content/Context;", GoodSettingsContract.EXTRA_NAME.POLICY_KEY, ApnSettings.MVNO_NONE, "(Landroid/content/Context;Ljava/lang/String;)V", "coroutineScope", "Lkotlinx/coroutines/CoroutineScope;", "eid", "fragment", "Landroidx/fragment/app/Fragment;", "lazyViewModel", "Lkotlin/Lazy;", "Lcom/android/settings/network/SubscriptionInfoListViewModel;", "preference", "Lcom/android/settingslib/CustomDialogPreferenceCompat;", UniversalCredentialUtil.AGENT_TITLE, "displayPreference", ApnSettings.MVNO_NONE, "screen", "Landroidx/preference/PreferenceScreen;", "getAvailabilityStatus", ApnSettings.MVNO_NONE, "subId", "getDefaultEid", "euiccMgr", "Landroid/telephony/euicc/EuiccManager;", "getEid", "subscriptionInfo", "Landroid/telephony/SubscriptionInfo;", "getEidPerSlot", "telMgr", "Landroid/telephony/TelephonyManager;", "getEuiccManager", "getTelephonyManager", "getTitle", "handlePreferenceTreeClick", ApnSettings.MVNO_NONE, "Landroidx/preference/Preference;", "init", "onViewCreated", "viewLifecycleOwner", "Landroidx/lifecycle/LifecycleOwner;", "refreshData", "(Landroid/telephony/SubscriptionInfo;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "refreshUi", "updateDialog", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "applications__sources__apps__SecSettings__android_common__SecSettings-core", "viewModel"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes2.dex */
public class MobileNetworkEidPreferenceController extends TelephonyBasePreferenceController {
    public static final int $stable = 8;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion();
    private static final int QR_CODE_SIZE = 600;
    private static final String TAG = "MobileNetworkEidPreferenceController";
    private CoroutineScope coroutineScope;
    private String eid;
    private Fragment fragment;
    private Lazy lazyViewModel;
    private CustomDialogPreferenceCompat preference;
    private String title;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileNetworkEidPreferenceController(Context context, String key) {
        super(context, key);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
        this.title = new String();
        this.eid = new String();
    }

    private final String getDefaultEid(EuiccManager euiccMgr) {
        if (euiccMgr == null || !euiccMgr.isEnabled()) {
            return new String();
        }
        String eid = euiccMgr.getEid();
        return eid == null ? new String() : eid;
    }

    private final String getEidPerSlot(TelephonyManager telMgr, EuiccManager euiccMgr, SubscriptionInfo subscriptionInfo) {
        Object obj;
        List<UiccCardInfo> uiccCardsInfo = telMgr.getUiccCardsInfo();
        Intrinsics.checkNotNullExpressionValue(uiccCardsInfo, "getUiccCardsInfo(...)");
        int cardId = subscriptionInfo.getCardId();
        Iterator<T> it = uiccCardsInfo.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            UiccCardInfo uiccCardInfo = (UiccCardInfo) obj;
            if (uiccCardInfo.isEuicc() && uiccCardInfo.getCardId() == cardId) {
                break;
            }
        }
        UiccCardInfo uiccCardInfo2 = (UiccCardInfo) obj;
        if (uiccCardInfo2 != null) {
            String eid = uiccCardInfo2.getEid();
            if (TextUtils.isEmpty(eid)) {
                eid = euiccMgr.createForCardId(uiccCardInfo2.getCardId()).getEid();
            }
            if (eid != null) {
                return eid;
            }
        }
        return new String();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getTitle() {
        String string = this.mContext.getString(R.string.status_eid);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }

    private static final SubscriptionInfoListViewModel onViewCreated$lambda$0(Lazy lazy) {
        return (SubscriptionInfoListViewModel) lazy.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object updateDialog(kotlin.coroutines.Continuation r10) {
        /*
            r9 = this;
            boolean r0 = r10 instanceof com.android.settings.network.telephony.MobileNetworkEidPreferenceController$updateDialog$1
            if (r0 == 0) goto L13
            r0 = r10
            com.android.settings.network.telephony.MobileNetworkEidPreferenceController$updateDialog$1 r0 = (com.android.settings.network.telephony.MobileNetworkEidPreferenceController$updateDialog$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.network.telephony.MobileNetworkEidPreferenceController$updateDialog$1 r0 = new com.android.settings.network.telephony.MobileNetworkEidPreferenceController$updateDialog$1
            r0.<init>(r9, r10)
        L18:
            java.lang.Object r10 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            r4 = 1
            if (r2 == 0) goto L35
            if (r2 != r4) goto L2d
            java.lang.Object r9 = r0.L$0
            android.widget.ImageView r9 = (android.widget.ImageView) r9
            kotlin.ResultKt.throwOnFailure(r10)
            goto L92
        L2d:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L35:
            kotlin.ResultKt.throwOnFailure(r10)
            com.android.settingslib.CustomDialogPreferenceCompat r10 = r9.preference
            r2 = 0
            if (r10 == 0) goto L98
            android.app.Dialog r10 = r10.getDialog()
            if (r10 != 0) goto L44
            return r3
        L44:
            android.view.Window r5 = r10.getWindow()
            if (r5 == 0) goto L4f
            r6 = 8192(0x2000, float:1.14794E-41)
            r5.setFlags(r6, r6)
        L4f:
            r5 = 0
            r10.setCanceledOnTouchOutside(r5)
            r5 = 2131363288(0x7f0a05d8, float:1.834638E38)
            android.view.View r5 = r10.requireViewById(r5)
            java.lang.String r6 = "requireViewById(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r6)
            android.widget.TextView r5 = (android.widget.TextView) r5
            java.lang.String r7 = r9.eid
            java.lang.CharSequence r7 = com.android.settings.deviceinfo.PhoneNumberUtil.expandByTts(r7)
            r5.setText(r7)
            r5 = 2131363287(0x7f0a05d7, float:1.8346379E38)
            android.view.View r10 = r10.requireViewById(r5)
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r10, r6)
            android.widget.ImageView r10 = (android.widget.ImageView) r10
            com.android.settings.network.telephony.MobileNetworkEidPreferenceController$Companion r5 = com.android.settings.network.telephony.MobileNetworkEidPreferenceController.INSTANCE
            java.lang.String r9 = r9.eid
            r0.L$0 = r10
            r0.label = r4
            r5.getClass()
            kotlinx.coroutines.scheduling.DefaultScheduler r4 = kotlinx.coroutines.Dispatchers.Default
            com.android.settings.network.telephony.MobileNetworkEidPreferenceController$Companion$getEidQrCode$2 r5 = new com.android.settings.network.telephony.MobileNetworkEidPreferenceController$Companion$getEidQrCode$2
            r5.<init>(r9, r2)
            java.lang.Object r9 = kotlinx.coroutines.BuildersKt.withContext(r4, r5, r0)
            if (r9 != r1) goto L8f
            return r1
        L8f:
            r8 = r10
            r10 = r9
            r9 = r8
        L92:
            android.graphics.Bitmap r10 = (android.graphics.Bitmap) r10
            r9.setImageBitmap(r10)
            return r3
        L98:
            java.lang.String r9 = "preference"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r9)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settings.network.telephony.MobileNetworkEidPreferenceController.updateDialog(kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        Preference findPreference = screen.findPreference(getPreferenceKey());
        Intrinsics.checkNotNull(findPreference);
        this.preference = (CustomDialogPreferenceCompat) findPreference;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int subId) {
        if (SubscriptionManager.isValidSubscriptionId(subId) && this.eid.length() > 0) {
            Context mContext = this.mContext;
            Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
            if (ContextsKt.getUserManager(mContext).isAdminUser()) {
                return 0;
            }
        }
        return 2;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    public final String getEid(SubscriptionInfo subscriptionInfo) {
        Intrinsics.checkNotNullParameter(subscriptionInfo, "subscriptionInfo");
        Context mContext = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        EuiccManager euiccManager = getEuiccManager(mContext);
        Context mContext2 = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext2, "mContext");
        TelephonyManager telephonyManager = getTelephonyManager(mContext2);
        if (euiccManager == null || telephonyManager == null) {
            return new String();
        }
        String eidPerSlot = getEidPerSlot(telephonyManager, euiccManager, subscriptionInfo);
        return eidPerSlot.length() == 0 ? getDefaultEid(euiccManager) : eidPerSlot;
    }

    public final EuiccManager getEuiccManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return (EuiccManager) context.getSystemService(EuiccManager.class);
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    public final TelephonyManager getTelephonyManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return (TelephonyManager) context.getSystemService(TelephonyManager.class);
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        Intrinsics.checkNotNullParameter(preference, "preference");
        if (!Intrinsics.areEqual(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        CustomDialogPreferenceCompat customDialogPreferenceCompat = this.preference;
        if (customDialogPreferenceCompat != null) {
            customDialogPreferenceCompat.mOnShowListener = new DialogInterface.OnShowListener() { // from class: com.android.settings.network.telephony.MobileNetworkEidPreferenceController$handlePreferenceTreeClick$1

                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
                /* renamed from: com.android.settings.network.telephony.MobileNetworkEidPreferenceController$handlePreferenceTreeClick$1$1, reason: invalid class name */
                final class AnonymousClass1 extends SuspendLambda implements Function2 {
                    int label;
                    final /* synthetic */ MobileNetworkEidPreferenceController this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass1(MobileNetworkEidPreferenceController mobileNetworkEidPreferenceController, Continuation continuation) {
                        super(2, continuation);
                        this.this$0 = mobileNetworkEidPreferenceController;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new AnonymousClass1(this.this$0, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        Object updateDialog;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            MobileNetworkEidPreferenceController mobileNetworkEidPreferenceController = this.this$0;
                            this.label = 1;
                            updateDialog = mobileNetworkEidPreferenceController.updateDialog(this);
                            if (updateDialog == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                        }
                        return Unit.INSTANCE;
                    }
                }

                @Override // android.content.DialogInterface.OnShowListener
                public final void onShow(DialogInterface dialogInterface) {
                    CoroutineScope coroutineScope;
                    coroutineScope = MobileNetworkEidPreferenceController.this.coroutineScope;
                    if (coroutineScope != null) {
                        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(MobileNetworkEidPreferenceController.this, null), 3);
                    }
                }
            };
            return true;
        }
        Intrinsics.throwUninitializedPropertyAccessException("preference");
        throw null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.network.telephony.MobileNetworkEidPreferenceController$init$$inlined$viewModels$default$1] */
    public final void init(final Fragment fragment, int subId) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        this.fragment = fragment;
        final ?? r0 = new Function0() { // from class: com.android.settings.network.telephony.MobileNetworkEidPreferenceController$init$$inlined$viewModels$default$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return Fragment.this;
            }
        };
        final Lazy lazy = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, new Function0() { // from class: com.android.settings.network.telephony.MobileNetworkEidPreferenceController$init$$inlined$viewModels$default$2
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
        this.lazyViewModel = new ViewModelLazy(Reflection.factory.getOrCreateKotlinClass(SubscriptionInfoListViewModel.class), new Function0() { // from class: com.android.settings.network.telephony.MobileNetworkEidPreferenceController$init$$inlined$viewModels$default$3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                return ((ViewModelStoreOwner) Lazy.this.getValue()).getViewModelStore();
            }
        }, new Function0() { // from class: com.android.settings.network.telephony.MobileNetworkEidPreferenceController$init$$inlined$viewModels$default$5
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
        }, new Function0() { // from class: com.android.settings.network.telephony.MobileNetworkEidPreferenceController$init$$inlined$viewModels$default$4
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
        this.mSubId = subId;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void onViewCreated(LifecycleOwner viewLifecycleOwner) {
        Intrinsics.checkNotNullParameter(viewLifecycleOwner, "viewLifecycleOwner");
        if (this.lazyViewModel == null) {
            Log.e(getClass().getSimpleName(), "lateinit property lazyViewModel has not been initialized");
            return;
        }
        CustomDialogPreferenceCompat customDialogPreferenceCompat = this.preference;
        if (customDialogPreferenceCompat == null) {
            Intrinsics.throwUninitializedPropertyAccessException("preference");
            throw null;
        }
        customDialogPreferenceCompat.setVisible(false);
        Lazy lazy = this.lazyViewModel;
        if (lazy == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lazyViewModel");
            throw null;
        }
        this.coroutineScope = LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner);
        final ReadonlyStateFlow readonlyStateFlow = onViewCreated$lambda$0(lazy).subscriptionInfoListFlow;
        FlowsKt.collectLatestWithLifecycle(new Flow() { // from class: com.android.settings.network.telephony.MobileNetworkEidPreferenceController$onViewCreated$$inlined$map$1

            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
            /* renamed from: com.android.settings.network.telephony.MobileNetworkEidPreferenceController$onViewCreated$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileNetworkEidPreferenceController this$0;

                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                /* renamed from: com.android.settings.network.telephony.MobileNetworkEidPreferenceController$onViewCreated$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MobileNetworkEidPreferenceController mobileNetworkEidPreferenceController) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mobileNetworkEidPreferenceController;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.settings.network.telephony.MobileNetworkEidPreferenceController$onViewCreated$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.settings.network.telephony.MobileNetworkEidPreferenceController$onViewCreated$$inlined$map$1$2$1 r0 = (com.android.settings.network.telephony.MobileNetworkEidPreferenceController$onViewCreated$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.settings.network.telephony.MobileNetworkEidPreferenceController$onViewCreated$$inlined$map$1$2$1 r0 = new com.android.settings.network.telephony.MobileNetworkEidPreferenceController$onViewCreated$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L67
                    L27:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r8)
                        java.util.List r7 = (java.util.List) r7
                        kotlin.jvm.internal.Intrinsics.checkNotNull(r7)
                        java.lang.Iterable r7 = (java.lang.Iterable) r7
                        java.util.Iterator r7 = r7.iterator()
                    L3d:
                        boolean r8 = r7.hasNext()
                        if (r8 == 0) goto L5b
                        java.lang.Object r8 = r7.next()
                        r2 = r8
                        android.telephony.SubscriptionInfo r2 = (android.telephony.SubscriptionInfo) r2
                        int r4 = r2.getSubscriptionId()
                        com.android.settings.network.telephony.MobileNetworkEidPreferenceController r5 = r6.this$0
                        int r5 = r5.mSubId
                        if (r4 != r5) goto L3d
                        boolean r2 = r2.isEmbedded()
                        if (r2 == 0) goto L3d
                        goto L5c
                    L5b:
                        r8 = 0
                    L5c:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r8, r0)
                        if (r6 != r1) goto L67
                        return r1
                    L67:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settings.network.telephony.MobileNetworkEidPreferenceController$onViewCreated$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, viewLifecycleOwner, Lifecycle.State.STARTED, new MobileNetworkEidPreferenceController$onViewCreated$2(this, null));
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object refreshData(android.telephony.SubscriptionInfo r6, kotlin.coroutines.Continuation r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.settings.network.telephony.MobileNetworkEidPreferenceController$refreshData$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.settings.network.telephony.MobileNetworkEidPreferenceController$refreshData$1 r0 = (com.android.settings.network.telephony.MobileNetworkEidPreferenceController$refreshData$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.network.telephony.MobileNetworkEidPreferenceController$refreshData$1 r0 = new com.android.settings.network.telephony.MobileNetworkEidPreferenceController$refreshData$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r5 = r0.L$0
            com.android.settings.network.telephony.MobileNetworkEidPreferenceController r5 = (com.android.settings.network.telephony.MobileNetworkEidPreferenceController) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L49
        L2b:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L33:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.scheduling.DefaultScheduler r7 = kotlinx.coroutines.Dispatchers.Default
            com.android.settings.network.telephony.MobileNetworkEidPreferenceController$refreshData$2 r2 = new com.android.settings.network.telephony.MobileNetworkEidPreferenceController$refreshData$2
            r4 = 0
            r2.<init>(r5, r6, r4)
            r0.L$0 = r5
            r0.label = r3
            java.lang.Object r6 = kotlinx.coroutines.BuildersKt.withContext(r7, r2, r0)
            if (r6 != r1) goto L49
            return r1
        L49:
            r5.refreshUi()
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settings.network.telephony.MobileNetworkEidPreferenceController.refreshData(android.telephony.SubscriptionInfo, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void refreshUi() {
        CustomDialogPreferenceCompat customDialogPreferenceCompat = this.preference;
        if (customDialogPreferenceCompat == null) {
            Intrinsics.throwUninitializedPropertyAccessException("preference");
            throw null;
        }
        customDialogPreferenceCompat.setTitle(this.title);
        CustomDialogPreferenceCompat customDialogPreferenceCompat2 = this.preference;
        if (customDialogPreferenceCompat2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("preference");
            throw null;
        }
        customDialogPreferenceCompat2.mDialogTitle = this.title;
        if (customDialogPreferenceCompat2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("preference");
            throw null;
        }
        customDialogPreferenceCompat2.setSummary(this.eid);
        CustomDialogPreferenceCompat customDialogPreferenceCompat3 = this.preference;
        if (customDialogPreferenceCompat3 != null) {
            customDialogPreferenceCompat3.setVisible(this.eid.length() > 0);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("preference");
            throw null;
        }
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
