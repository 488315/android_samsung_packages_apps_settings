package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.network.SubscriptionUtil;
import com.android.settingslib.spa.framework.util.FlowsKt;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000J\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\bH\u0016J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010"
                + "\t\u001a\u00020\n"
                + "H\u0016J\u000e\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\bJ\u0010\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\t\u001a\u00020\n"
                + "X\u0082.¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\r"
                + "X\u0082\u000e¢\u0006\u0002\n"
                + "\u0000¨\u0006\u001a"
        },
        d2 = {
            "Lcom/android/settings/network/telephony/DeleteSimProfilePreferenceController;",
            "Lcom/android/settings/core/BasePreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "carrierId",
            ApnSettings.MVNO_NONE,
            "preference",
            "Landroidx/preference/Preference;",
            "subscriptionId",
            "subscriptionInfo",
            "Landroid/telephony/SubscriptionInfo;",
            "deleteSim",
            ApnSettings.MVNO_NONE,
            "displayPreference",
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getAvailabilityStatus",
            "handlePreferenceTreeClick",
            ApnSettings.MVNO_NONE,
            "init",
            "onViewCreated",
            "viewLifecycleOwner",
            "Landroidx/lifecycle/LifecycleOwner;",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class DeleteSimProfilePreferenceController extends BasePreferenceController {
    public static final int $stable = 8;
    private int carrierId;
    private Preference preference;
    private int subscriptionId;
    private SubscriptionInfo subscriptionInfo;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeleteSimProfilePreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preferenceKey, "preferenceKey");
        this.subscriptionId = -1;
        this.carrierId = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void deleteSim() {
        Context context = this.mContext;
        int i = this.subscriptionId;
        int i2 = this.carrierId;
        Pattern pattern = SubscriptionUtil.REGEX_DISPLAY_NAME_SUFFIX_PATTERN;
        if (!SubscriptionManager.isUsableSubscriptionId(i)) {
            Log.i(
                    "SubscriptionUtil",
                    "Unable to delete subscription due to invalid subscription ID.");
            return;
        }
        if (SubscriptionUtil.shouldShowRacDialogWhenErasingEsim(context, i, i2)) {
            int i3 = EuiccRacConnectivityDialogActivity.$r8$clinit;
            Intent intent =
                    new Intent(context, (Class<?>) EuiccRacConnectivityDialogActivity.class);
            intent.putExtra("sub_id", i);
            context.startActivity(intent);
            return;
        }
        int i4 = DeleteEuiccSubscriptionDialogActivity.$r8$clinit;
        Intent intent2 =
                new Intent(context, (Class<?>) DeleteEuiccSubscriptionDialogActivity.class);
        intent2.putExtra("sub_id", i);
        context.startActivity(intent2);
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

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.subscriptionInfo == null ? 2 : 0;
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        Intrinsics.checkNotNullParameter(preference, "preference");
        if (!Intrinsics.areEqual(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        MobileNetworkUtils.showLockScreen(
                this.mContext,
                new Runnable() { // from class:
                                 // com.android.settings.network.telephony.DeleteSimProfilePreferenceController$handlePreferenceTreeClick$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DeleteSimProfilePreferenceController.this.deleteSim();
                    }
                });
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public final void init(int subscriptionId) {
        Object obj;
        this.subscriptionId = subscriptionId;
        List availableSubscriptions = SubscriptionUtil.getAvailableSubscriptions(this.mContext);
        Intrinsics.checkNotNullExpressionValue(
                availableSubscriptions, "getAvailableSubscriptions(...)");
        Iterator it = availableSubscriptions.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
            if (subscriptionInfo.getSubscriptionId() == subscriptionId
                    && subscriptionInfo.isEmbedded()) {
                break;
            }
        }
        SubscriptionInfo subscriptionInfo2 = (SubscriptionInfo) obj;
        this.subscriptionInfo = subscriptionInfo2;
        if (subscriptionInfo2 != null) {
            this.carrierId = subscriptionInfo2.getCarrierId();
        }
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
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void onViewCreated(LifecycleOwner viewLifecycleOwner) {
        Intrinsics.checkNotNullParameter(viewLifecycleOwner, "viewLifecycleOwner");
        Context mContext = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        SubscriptionRepositoryKt.requireSubscriptionManager(mContext);
        FlowsKt.collectLatestWithLifecycle(
                TelephonyRepositoryKt.telephonyCallbackFlow(
                        mContext,
                        this.subscriptionId,
                        CallStateRepository$callStateFlow$1.INSTANCE),
                viewLifecycleOwner,
                Lifecycle.State.STARTED,
                new DeleteSimProfilePreferenceController$onViewCreated$1(this, null));
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

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
