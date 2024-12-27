package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.telephony.SubscriptionManager;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.network.telephony.wificalling.WifiCallingRepository;
import com.android.settings.network.telephony.wificalling.WifiCallingRepository$wifiCallingReadyFlow$$inlined$flatMapLatest$1;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.spa.framework.util.FlowsKt;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000Z\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0006\b\u0017\u0018\u00002\u00020\u0001B9\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\u0014\b\u0002\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020"
                + "\t\u0012\u0004\u0012\u00020\n"
                + "0\b¢\u0006\u0004\b\f\u0010\r"
                + "J\u0010\u0010\u000f\u001a\u00020\u000eH\u0082@¢\u0006\u0004\b\u000f\u0010\u0010J\u000f\u0010\u0011\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u0011\u0010\u0012J\u001d\u0010\u0016\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020"
                + "\t2\u0006\u0010\u0015\u001a\u00020\u0014¢\u0006\u0004\b\u0016\u0010\u0017J\u0017\u0010\u0018\u001a\u00020"
                + "\t2\u0006\u0010\u0013\u001a\u00020"
                + "\tH\u0016¢\u0006\u0004\b\u0018\u0010\u0019J\u0017\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u001aH\u0016¢\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010"
                + " \u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u001eH\u0016¢\u0006\u0004\b"
                + " \u0010!R\u0014\u0010\u0007\u001a\u00020\u00068\u0002X\u0082\u0004¢\u0006\u0006\n"
                + "\u0004\b\u0007\u0010\"R \u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020"
                + "\t\u0012\u0004\u0012\u00020\n"
                + "0\b8\u0002X\u0082\u0004¢\u0006\u0006\n"
                + "\u0004\b\u000b\u0010#R\u0016\u0010%\u001a\u00020$8\u0002@\u0002X\u0082.¢\u0006\u0006\n"
                + "\u0004\b%\u0010&R\u0016\u0010\u0015\u001a\u00020\u00148\u0002@\u0002X\u0082.¢\u0006\u0006\n"
                + "\u0004\b\u0015\u0010'R\u001b\u0010-\u001a\u00020(8BX\u0082\u0084\u0002¢\u0006\f\n"
                + "\u0004\b)\u0010*\u001a\u0004\b+\u0010,¨\u0006."
        },
        d2 = {
            "Lcom/android/settings/network/telephony/WifiCallingPreferenceController;",
            "Lcom/android/settings/network/telephony/TelephonyBasePreferenceController;",
            "Landroid/content/Context;",
            "context",
            ApnSettings.MVNO_NONE,
            GoodSettingsContract.EXTRA_NAME.POLICY_KEY,
            "Lcom/android/settings/network/telephony/CallStateRepository;",
            "callStateRepository",
            "Lkotlin/Function1;",
            ApnSettings.MVNO_NONE,
            "Lcom/android/settings/network/telephony/wificalling/WifiCallingRepository;",
            "wifiCallingRepositoryFactory",
            "<init>",
            "(Landroid/content/Context;Ljava/lang/String;Lcom/android/settings/network/telephony/CallStateRepository;Lkotlin/jvm/functions/Function1;)V",
            ApnSettings.MVNO_NONE,
            "update",
            "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
            "getSummaryForWfcMode",
            "()Ljava/lang/String;",
            "subId",
            "Lcom/android/settings/network/telephony/CallingPreferenceCategoryController;",
            "callingPreferenceCategoryController",
            "init",
            "(ILcom/android/settings/network/telephony/CallingPreferenceCategoryController;)Lcom/android/settings/network/telephony/WifiCallingPreferenceController;",
            "getAvailabilityStatus",
            "(I)I",
            "Landroidx/preference/PreferenceScreen;",
            "screen",
            "displayPreference",
            "(Landroidx/preference/PreferenceScreen;)V",
            "Landroidx/lifecycle/LifecycleOwner;",
            "viewLifecycleOwner",
            "onViewCreated",
            "(Landroidx/lifecycle/LifecycleOwner;)V",
            "Lcom/android/settings/network/telephony/CallStateRepository;",
            "Lkotlin/jvm/functions/Function1;",
            "Landroidx/preference/Preference;",
            "preference",
            "Landroidx/preference/Preference;",
            "Lcom/android/settings/network/telephony/CallingPreferenceCategoryController;",
            "Landroid/content/res/Resources;",
            "resourcesForSub$delegate",
            "Lkotlin/Lazy;",
            "getResourcesForSub",
            "()Landroid/content/res/Resources;",
            "resourcesForSub",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public class WifiCallingPreferenceController extends TelephonyBasePreferenceController {
    public static final int $stable = 8;
    private final CallStateRepository callStateRepository;
    private CallingPreferenceCategoryController callingPreferenceCategoryController;
    private Preference preference;

    /* renamed from: resourcesForSub$delegate, reason: from kotlin metadata */
    private final Lazy resourcesForSub;
    private final Function1 wifiCallingRepositoryFactory;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public WifiCallingPreferenceController(Context context, String key) {
        this(context, key, null, null, 12, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
    }

    private final Resources getResourcesForSub() {
        return (Resources) this.resourcesForSub.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(12:0|1|(2:3|(9:5|6|7|8|(1:(1:25)(1:26))(1:10)|11|(1:(2:14|(1:16)(1:20))(1:21))(1:22)|17|18))|30|6|7|8|(0)(0)|11|(0)(0)|17|18) */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0046, code lost:

       r4 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x004f, code lost:

       android.util.Log.w("ImsMmTelRepository", "[" + r0.subId + "] getWiFiCallingMode failed useRoamingMode=" + r1, r4);
    */
    /* JADX WARN: Removed duplicated region for block: B:10:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x003d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getSummaryForWfcMode() {
        /*
            r7 = this;
            kotlin.jvm.functions.Function1 r0 = r7.wifiCallingRepositoryFactory
            int r1 = r7.mSubId
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            java.lang.Object r0 = r0.invoke(r1)
            com.android.settings.network.telephony.wificalling.WifiCallingRepository r0 = (com.android.settings.network.telephony.wificalling.WifiCallingRepository) r0
            android.telephony.TelephonyManager r1 = r0.telephonyManager
            boolean r1 = r1.isNetworkRoaming()
            r2 = 1
            if (r1 == 0) goto L2d
            android.telephony.CarrierConfigManager r1 = r0.carrierConfigManager
            java.lang.String r3 = "use_wfc_home_network_mode_in_roaming_network_bool"
            java.lang.String[] r4 = new java.lang.String[]{r3}
            int r5 = r0.subId
            android.os.PersistableBundle r1 = r1.getConfigForSubId(r5, r4)
            boolean r1 = r1.getBoolean(r3)
            if (r1 != 0) goto L2d
            r1 = r2
            goto L2e
        L2d:
            r1 = 0
        L2e:
            com.android.settings.network.telephony.ims.ImsMmTelRepositoryImpl r0 = r0.imsMmTelRepository
            r0.getClass()
            r3 = -1
            android.telephony.ims.ImsMmTelManager r4 = r0.imsMmTelManager     // Catch: java.lang.IllegalArgumentException -> L46
            boolean r4 = r4.isVoWiFiSettingEnabled()     // Catch: java.lang.IllegalArgumentException -> L46
            if (r4 != 0) goto L3d
            goto L6c
        L3d:
            if (r1 == 0) goto L48
            android.telephony.ims.ImsMmTelManager r4 = r0.imsMmTelManager     // Catch: java.lang.IllegalArgumentException -> L46
            int r3 = r4.getVoWiFiRoamingModeSetting()     // Catch: java.lang.IllegalArgumentException -> L46
            goto L6c
        L46:
            r4 = move-exception
            goto L4f
        L48:
            android.telephony.ims.ImsMmTelManager r4 = r0.imsMmTelManager     // Catch: java.lang.IllegalArgumentException -> L46
            int r3 = r4.getVoWiFiModeSetting()     // Catch: java.lang.IllegalArgumentException -> L46
            goto L6c
        L4f:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "["
            r5.<init>(r6)
            int r0 = r0.subId
            r5.append(r0)
            java.lang.String r0 = "] getWiFiCallingMode failed useRoamingMode="
            r5.append(r0)
            r5.append(r1)
            java.lang.String r0 = r5.toString()
            java.lang.String r1 = "ImsMmTelRepository"
            android.util.Log.w(r1, r0, r4)
        L6c:
            if (r3 == 0) goto L7f
            if (r3 == r2) goto L7b
            r0 = 2
            if (r3 == r0) goto L77
            r0 = 17043585(0x1041081, float:2.4256412E-38)
            goto L82
        L77:
            r0 = 17043508(0x1041034, float:2.4256196E-38)
            goto L82
        L7b:
            r0 = 17043506(0x1041032, float:2.425619E-38)
            goto L82
        L7f:
            r0 = 17043507(0x1041033, float:2.4256193E-38)
        L82:
            android.content.res.Resources r7 = r7.getResourcesForSub()
            java.lang.String r7 = r7.getString(r0)
            java.lang.String r0 = "getString(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r7, r0)
            return r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.telephony.WifiCallingPreferenceController.getSummaryForWfcMode():java.lang.String");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x009f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0082 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object update(kotlin.coroutines.Continuation r11) {
        /*
            Method dump skipped, instructions count: 251
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.telephony.WifiCallingPreferenceController.update(kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        Preference findPreference = screen.findPreference(getPreferenceKey());
        Intrinsics.checkNotNull(findPreference);
        this.preference = findPreference;
        Intent intent = findPreference.getIntent();
        if (intent != null) {
            intent.putExtra("android.provider.extra.SUB_ID", this.mSubId);
        }
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int subId) {
        return SubscriptionManager.isValidSubscriptionId(subId) ? 0 : 2;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public final WifiCallingPreferenceController init(
            int subId, CallingPreferenceCategoryController callingPreferenceCategoryController) {
        Intrinsics.checkNotNullParameter(
                callingPreferenceCategoryController, "callingPreferenceCategoryController");
        this.mSubId = subId;
        this.callingPreferenceCategoryController = callingPreferenceCategoryController;
        return this;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void onViewCreated(LifecycleOwner viewLifecycleOwner) {
        Intrinsics.checkNotNullParameter(viewLifecycleOwner, "viewLifecycleOwner");
        int i = this.mSubId;
        if (i == -1) {
            Log.e(getClass().getSimpleName(), "mSubId is INVALID_SUBSCRIPTION_ID");
            return;
        }
        WifiCallingRepository wifiCallingRepository =
                (WifiCallingRepository)
                        this.wifiCallingRepositoryFactory.invoke(Integer.valueOf(i));
        FlowsKt.collectLatestWithLifecycle(
                !SubscriptionManager.isValidSubscriptionId(wifiCallingRepository.subId)
                        ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE)
                        : FlowKt.transformLatest(
                                SubscriptionRepositoryKt.subscriptionsChangedFlow(
                                        wifiCallingRepository.context),
                                new WifiCallingRepository$wifiCallingReadyFlow$$inlined$flatMapLatest$1(
                                        wifiCallingRepository, null)),
                viewLifecycleOwner,
                Lifecycle.State.STARTED,
                new WifiCallingPreferenceController$onViewCreated$1(this, null));
        FlowsKt.collectLatestWithLifecycle(
                TelephonyRepositoryKt.telephonyCallbackFlow(
                        this.callStateRepository.context,
                        this.mSubId,
                        CallStateRepository$callStateFlow$1.INSTANCE),
                viewLifecycleOwner,
                Lifecycle.State.STARTED,
                new WifiCallingPreferenceController$onViewCreated$2(this, null));
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public WifiCallingPreferenceController(
            Context context, String key, CallStateRepository callStateRepository) {
        this(context, key, callStateRepository, null, 8, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(callStateRepository, "callStateRepository");
    }

    public /* synthetic */ WifiCallingPreferenceController(
            final Context context,
            String str,
            CallStateRepository callStateRepository,
            Function1 function1,
            int i,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(
                context,
                str,
                (i & 4) != 0 ? new CallStateRepository(context) : callStateRepository,
                (i & 8) != 0
                        ? new Function1() { // from class:
                                            // com.android.settings.network.telephony.WifiCallingPreferenceController.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                return new WifiCallingRepository(
                                        context, ((Number) obj).intValue());
                            }
                        }
                        : function1);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiCallingPreferenceController(
            Context context,
            String key,
            CallStateRepository callStateRepository,
            Function1 wifiCallingRepositoryFactory) {
        super(context, key);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(callStateRepository, "callStateRepository");
        Intrinsics.checkNotNullParameter(
                wifiCallingRepositoryFactory, "wifiCallingRepositoryFactory");
        this.callStateRepository = callStateRepository;
        this.wifiCallingRepositoryFactory = wifiCallingRepositoryFactory;
        this.resourcesForSub =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settings.network.telephony.WifiCallingPreferenceController$resourcesForSub$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                Context context2;
                                context2 =
                                        ((AbstractPreferenceController)
                                                        WifiCallingPreferenceController.this)
                                                .mContext;
                                return SubscriptionManager.getResourcesForSubId(
                                        context2, WifiCallingPreferenceController.this.mSubId);
                            }
                        });
    }
}
