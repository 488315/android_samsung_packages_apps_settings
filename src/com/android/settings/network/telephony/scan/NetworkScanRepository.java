package com.android.settings.network.telephony.scan;

import android.content.Context;
import android.telephony.CellIdentity;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.modifiers.TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.android.settings.network.telephony.CellInfoUtil;
import com.android.settings.network.telephony.NetworkSelectSettings$$ExternalSyntheticLambda0;
import com.android.settings.network.telephony.TelephonyRepositoryKt;
import com.android.settingslib.spa.framework.util.FlowsKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.FlowKt;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NetworkScanRepository {
    public final Context context;
    public final TelephonyManager telephonyManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CellInfoScanKey {
        public final String className;
        public final boolean isRegistered;
        public final String title;

        public CellInfoScanKey(CellInfo cellInfo) {
            Intrinsics.checkNotNullParameter(cellInfo, "cellInfo");
            CellIdentity cellIdentity = cellInfo.getCellIdentity();
            Intrinsics.checkNotNullExpressionValue(cellIdentity, "getCellIdentity(...)");
            String networkTitle = CellInfoUtil.getNetworkTitle(cellIdentity);
            String name = cellInfo.getClass().getName();
            boolean isRegistered = cellInfo.isRegistered();
            this.title = networkTitle;
            this.className = name;
            this.isRegistered = isRegistered;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CellInfoScanKey)) {
                return false;
            }
            CellInfoScanKey cellInfoScanKey = (CellInfoScanKey) obj;
            return Intrinsics.areEqual(this.title, cellInfoScanKey.title)
                    && Intrinsics.areEqual(this.className, cellInfoScanKey.className)
                    && this.isRegistered == cellInfoScanKey.isRegistered;
        }

        public final int hashCode() {
            String str = this.title;
            return Boolean.hashCode(this.isRegistered)
                    + TextStringSimpleNode$TextSubstitutionValue$$ExternalSyntheticOutline0.m(
                            (str == null ? 0 : str.hashCode()) * 31, 31, this.className);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("CellInfoScanKey(title=");
            sb.append(this.title);
            sb.append(", className=");
            sb.append(this.className);
            sb.append(", isRegistered=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.isRegistered, ")");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NetworkScanResult {
        public final List cellInfos;
        public final NetworkScanState state;

        public NetworkScanResult(NetworkScanState state, List cellInfos) {
            Intrinsics.checkNotNullParameter(state, "state");
            Intrinsics.checkNotNullParameter(cellInfos, "cellInfos");
            this.state = state;
            this.cellInfos = cellInfos;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof NetworkScanResult)) {
                return false;
            }
            NetworkScanResult networkScanResult = (NetworkScanResult) obj;
            return this.state == networkScanResult.state
                    && Intrinsics.areEqual(this.cellInfos, networkScanResult.cellInfos);
        }

        public final int hashCode() {
            return this.cellInfos.hashCode() + (this.state.hashCode() * 31);
        }

        public final String toString() {
            return "NetworkScanResult(state=" + this.state + ", cellInfos=" + this.cellInfos + ")";
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\f\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0010\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001¨\u0006\u0003"
            },
            d2 = {
                "com/android/settings/network/telephony/scan/NetworkScanRepository$NetworkScanState",
                ApnSettings.MVNO_NONE,
                "Lcom/android/settings/network/telephony/scan/NetworkScanRepository$NetworkScanState;",
                "applications__sources__apps__SecSettings__android_common__SecSettings-core"
            },
            k = 1,
            mv = {1, 9, 0})
    public final class NetworkScanState {
        public static final /* synthetic */ NetworkScanState[] $VALUES;
        public static final NetworkScanState ACTIVE;
        public static final NetworkScanState COMPLETE;
        public static final NetworkScanState ERROR;

        static {
            NetworkScanState networkScanState = new NetworkScanState("ACTIVE", 0);
            ACTIVE = networkScanState;
            NetworkScanState networkScanState2 = new NetworkScanState("COMPLETE", 1);
            COMPLETE = networkScanState2;
            NetworkScanState networkScanState3 = new NetworkScanState("ERROR", 2);
            ERROR = networkScanState3;
            NetworkScanState[] networkScanStateArr = {
                networkScanState, networkScanState2, networkScanState3
            };
            $VALUES = networkScanStateArr;
            EnumEntriesKt.enumEntries(networkScanStateArr);
        }

        public static NetworkScanState valueOf(String str) {
            return (NetworkScanState) Enum.valueOf(NetworkScanState.class, str);
        }

        public static NetworkScanState[] values() {
            return (NetworkScanState[]) $VALUES.clone();
        }
    }

    public NetworkScanRepository(Context context, int i) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.telephonyManager = TelephonyRepositoryKt.telephonyManager(context, i);
    }

    public final StandaloneCoroutine launchNetworkScan(
            LifecycleOwner lifecycleOwner,
            NetworkSelectSettings$$ExternalSyntheticLambda0
                    networkSelectSettings$$ExternalSyntheticLambda0) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "lifecycleOwner");
        return FlowsKt.collectLatestWithLifecycle(
                FlowKt.flowOn(
                        FlowKt.onEach(
                                FlowKt.buffer$default(
                                        FlowKt.callbackFlow(
                                                new NetworkScanRepository$networkScanFlow$1(
                                                        this, null)),
                                        -1),
                                new NetworkScanRepository$networkScanFlow$2(2, null)),
                        Dispatchers.Default),
                lifecycleOwner,
                Lifecycle.State.STARTED,
                new NetworkScanRepository$launchNetworkScan$1(
                        2,
                        networkSelectSettings$$ExternalSyntheticLambda0,
                        Intrinsics.Kotlin.class,
                        "suspendConversion0",
                        "launchNetworkScan$suspendConversion0(Lkotlin/jvm/functions/Function1;Lcom/android/settings/network/telephony/scan/NetworkScanRepository$NetworkScanResult;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
                        0));
    }
}
