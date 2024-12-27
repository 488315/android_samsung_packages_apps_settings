package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkTemplate;
import android.os.Parcelable;
import android.telephony.SubscriptionManager;
import android.text.format.DateUtils;
import android.util.Range;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.datausage.DataUsageUtils;
import com.android.settings.datausage.lib.DataUsageFormatter;
import com.android.settings.datausage.lib.DataUsageLib;
import com.android.settings.datausage.lib.NetworkCycleDataRepository;
import com.android.settings.datausage.lib.NetworkStatsRepository;
import com.android.settings.datausage.lib.NetworkUsageData;
import com.android.settingslib.spaprivileged.framework.compose.StringResourcesKt;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000V\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\n"
                + "\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007J\u0010\u0010\r"
                + "\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J\u0016\u0010\u0014\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0016\u0012\u0004\u0012\u00020\u00170\u0015H\u0002J\n"
                + "\u0010\u0018\u001a\u0004\u0018\u00010\bH\u0002J\u0010\u0010\u0019\u001a\u00020\u00172\u0006\u0010"
                + "\t\u001a\u00020\n"
                + "H\u0016J\u000e\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u0012J\u0010\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u000e\u0010\u001e\u001a\u00020\u000eH\u0082@¢\u0006\u0002\u0010\u001fR\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\t\u001a\u00020\n"
                + "X\u0082.¢\u0006\u0002\n"
                + "\u0000¨\u0006 "
        },
        d2 = {
            "Lcom/android/settings/network/telephony/DataUsagePreferenceController;",
            "Lcom/android/settings/network/telephony/TelephonyBasePreferenceController;",
            "context",
            "Landroid/content/Context;",
            GoodSettingsContract.EXTRA_NAME.POLICY_KEY,
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "networkTemplate",
            "Landroid/net/NetworkTemplate;",
            "preference",
            "Landroidx/preference/Preference;",
            "createNetworkCycleDataRepository",
            "Lcom/android/settings/datausage/lib/NetworkCycleDataRepository;",
            "displayPreference",
            ApnSettings.MVNO_NONE,
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "subId",
            "getDataUsageSummaryAndEnabled",
            "Lkotlin/Pair;",
            "Lcom/android/settings/datausage/lib/DataUsageFormatter$FormattedDataUsage;",
            ApnSettings.MVNO_NONE,
            "getNetworkTemplate",
            "handlePreferenceTreeClick",
            "init",
            "onViewCreated",
            "viewLifecycleOwner",
            "Landroidx/lifecycle/LifecycleOwner;",
            "update",
            "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class DataUsagePreferenceController extends TelephonyBasePreferenceController {
    public static final int $stable = 8;
    private NetworkTemplate networkTemplate;
    private Preference preference;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataUsagePreferenceController(Context context, String key) {
        super(context, key);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Pair<DataUsageFormatter.FormattedDataUsage, Boolean>
            getDataUsageSummaryAndEnabled() {
        NetworkCycleDataRepository createNetworkCycleDataRepository =
                createNetworkCycleDataRepository();
        if (createNetworkCycleDataRepository == null) {
            return new Pair<>(null, Boolean.FALSE);
        }
        Range range =
                (Range)
                        CollectionsKt___CollectionsKt.firstOrNull(
                                createNetworkCycleDataRepository.getCycles());
        NetworkUsageData queryUsage =
                range != null ? createNetworkCycleDataRepository.queryUsage(range) : null;
        if (queryUsage == null) {
            NetworkStatsRepository.Companion companion = NetworkStatsRepository.Companion;
            NetworkUsageData queryUsage2 =
                    createNetworkCycleDataRepository.queryUsage(
                            NetworkStatsRepository.AllTimeRange);
            Context mContext = this.mContext;
            Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
            return new Pair<>(
                    queryUsage2
                            .formatUsage(mContext)
                            .format(mContext, R.string.data_used_template, new Object[0]),
                    Boolean.valueOf(queryUsage2.usage > 0));
        }
        Context mContext2 = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext2, "mContext");
        DataUsageFormatter.FormattedDataUsage formatUsage = queryUsage.formatUsage(mContext2);
        Context mContext3 = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext3, "mContext");
        Context mContext4 = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext4, "mContext");
        String formatDateRange =
                DateUtils.formatDateRange(
                        mContext4, queryUsage.startTime, queryUsage.endTime, 65552);
        Intrinsics.checkNotNullExpressionValue(formatDateRange, "formatDateRange(...)");
        DataUsageFormatter.FormattedDataUsage format =
                formatUsage.format(mContext3, R.string.data_usage_template, formatDateRange);
        if (queryUsage.usage <= 0) {
            NetworkStatsRepository.Companion companion2 = NetworkStatsRepository.Companion;
            if (createNetworkCycleDataRepository.queryUsage(NetworkStatsRepository.AllTimeRange)
                            .usage
                    <= 0) {
                r2 = false;
            }
        }
        return new Pair<>(format, Boolean.valueOf(r2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final NetworkTemplate getNetworkTemplate() {
        if (!SubscriptionManager.isValidSubscriptionId(this.mSubId)) {
            return null;
        }
        Context mContext = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        return DataUsageLib.getMobileTemplate(mContext, this.mSubId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object update(kotlin.coroutines.Continuation r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.settings.network.telephony.DataUsagePreferenceController$update$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.settings.network.telephony.DataUsagePreferenceController$update$1 r0 = (com.android.settings.network.telephony.DataUsagePreferenceController$update$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.network.telephony.DataUsagePreferenceController$update$1 r0 = new com.android.settings.network.telephony.DataUsagePreferenceController$update$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L34
            if (r2 != r4) goto L2c
            java.lang.Object r5 = r0.L$0
            com.android.settings.network.telephony.DataUsagePreferenceController r5 = (com.android.settings.network.telephony.DataUsagePreferenceController) r5
            kotlin.ResultKt.throwOnFailure(r6)
            goto L49
        L2c:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L34:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlinx.coroutines.scheduling.DefaultScheduler r6 = kotlinx.coroutines.Dispatchers.Default
            com.android.settings.network.telephony.DataUsagePreferenceController$update$2 r2 = new com.android.settings.network.telephony.DataUsagePreferenceController$update$2
            r2.<init>(r5, r3)
            r0.L$0 = r5
            r0.label = r4
            java.lang.Object r6 = kotlinx.coroutines.BuildersKt.withContext(r6, r2, r0)
            if (r6 != r1) goto L49
            return r1
        L49:
            kotlin.Pair r6 = (kotlin.Pair) r6
            java.lang.Object r0 = r6.getFirst()
            com.android.settings.datausage.lib.DataUsageFormatter$FormattedDataUsage r0 = (com.android.settings.datausage.lib.DataUsageFormatter.FormattedDataUsage) r0
            java.lang.Object r6 = r6.getSecond()
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            androidx.preference.Preference r1 = r5.preference
            java.lang.String r2 = "preference"
            if (r1 == 0) goto L76
            r1.setEnabled(r6)
            androidx.preference.Preference r5 = r5.preference
            if (r5 == 0) goto L72
            if (r0 == 0) goto L6c
            java.lang.String r3 = r0.displayText
        L6c:
            r5.setSummary(r3)
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        L72:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            throw r3
        L76:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            throw r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.telephony.DataUsagePreferenceController.update(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final NetworkCycleDataRepository createNetworkCycleDataRepository() {
        NetworkTemplate networkTemplate = this.networkTemplate;
        if (networkTemplate == null) {
            return null;
        }
        Context mContext = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        return new NetworkCycleDataRepository(mContext, networkTemplate);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        Preference findPreference = screen.findPreference(getPreferenceKey());
        Intrinsics.checkNotNull(findPreference);
        this.preference = findPreference;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int subId) {
        return (SubscriptionManager.isValidSubscriptionId(subId)
                        && DataUsageUtils.hasMobileData(this.mContext))
                ? 0
                : 1;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        Intrinsics.checkNotNullParameter(preference, "preference");
        if (!Intrinsics.areEqual(preference.getKey(), getPreferenceKey())
                || this.networkTemplate == null) {
            return false;
        }
        Intent intent = new Intent("android.settings.MOBILE_DATA_USAGE");
        intent.setPackage(this.mContext.getPackageName());
        intent.putExtra("network_template", (Parcelable) this.networkTemplate);
        intent.putExtra("android.provider.extra.SUB_ID", this.mSubId);
        this.mContext.startActivity(intent);
        return true;
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

    public final void init(int subId) {
        this.mSubId = subId;
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
        Preference preference = this.preference;
        if (preference == null) {
            Intrinsics.throwUninitializedPropertyAccessException("preference");
            throw null;
        }
        Context mContext = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        preference.setSummary(StringResourcesKt.getPlaceholder(mContext));
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner),
                null,
                null,
                new DataUsagePreferenceController$onViewCreated$1(viewLifecycleOwner, this, null),
                3);
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
}
