package com.android.settings.network;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.TetheringManager;
import android.os.UserHandle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.TetherUtil;
import com.android.settingslib.spa.framework.util.FlowsKt;
import com.android.settingslib.spaprivileged.framework.common.BroadcastReceiverFlowKt;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.scheduling.DefaultScheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000N\n"
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
                + "\u0002\u0010\"\n"
                + "\u0002\b\u0004\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\b\u0007\u0018\u0000"
                + " \u001b2\u00020\u0001:\u0001\u001bB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\r"
                + "\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u0016\u0010\u0013\u001a\u00020\u00122\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00120\u0015H\u0007J\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0012H\u0082@¢\u0006\u0002\u0010\u0017J\u0010\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\t\u001a\u00020\n"
                + "X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000¨\u0006\u001c"
        },
        d2 = {
            "Lcom/android/settings/network/TetherPreferenceController;",
            "Lcom/android/settings/core/BasePreferenceController;",
            "context",
            "Landroid/content/Context;",
            GoodSettingsContract.EXTRA_NAME.POLICY_KEY,
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "preference",
            "Landroidx/preference/Preference;",
            "tetheredRepository",
            "Lcom/android/settings/network/TetheredRepository;",
            "tetheringManager",
            "Landroid/net/TetheringManager;",
            "displayPreference",
            ApnSettings.MVNO_NONE,
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "getSummaryResId",
            "tetheredTypes",
            ApnSettings.MVNO_NONE,
            "getTitleResId",
            "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
            "onViewCreated",
            "viewLifecycleOwner",
            "Landroidx/lifecycle/LifecycleOwner;",
            "Companion",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class TetherPreferenceController extends BasePreferenceController {
    public static final int $stable = 8;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion();
    private Preference preference;
    private final TetheredRepository tetheredRepository;
    private final TetheringManager tetheringManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {}

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TetherPreferenceController(Context context, String key) {
        super(context, key);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
        this.tetheredRepository = new TetheredRepository(context);
        Object systemService =
                this.mContext.getSystemService((Class<Object>) TetheringManager.class);
        Intrinsics.checkNotNull(systemService);
        this.tetheringManager = (TetheringManager) systemService;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object getTitleResId(Continuation continuation) {
        return BuildersKt.withContext(
                Dispatchers.Default,
                new TetherPreferenceController$getTitleResId$2(this, null),
                continuation);
    }

    public static final boolean isTetherConfigDisallowed(Context context) {
        INSTANCE.getClass();
        return RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        context, UserHandle.myUserId(), "no_config_tethering")
                != null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        this.preference = screen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return TetherUtil.isTetherAvailable(this.mContext) ? 0 : 2;
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

    public final int getSummaryResId(Set<Integer> tetheredTypes) {
        Intrinsics.checkNotNullParameter(tetheredTypes, "tetheredTypes");
        boolean z = false;
        boolean contains = tetheredTypes.contains(0);
        Set<Integer> set = tetheredTypes;
        if (!(set instanceof Collection) || !set.isEmpty()) {
            Iterator<T> it = set.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (((Number) it.next()).intValue() != 0) {
                    z = true;
                    break;
                }
            }
        }
        return (contains && z)
                ? R.string.tether_settings_summary_hotspot_on_tether_on
                : contains
                        ? R.string.tether_settings_summary_hotspot_on_tether_off
                        : z
                                ? R.string.tether_settings_summary_hotspot_off_tether_on
                                : R.string.tether_preference_summary_off;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
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
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner),
                null,
                null,
                new TetherPreferenceController$onViewCreated$1(viewLifecycleOwner, this, null),
                3);
        TetheredRepository tetheredRepository = this.tetheredRepository;
        tetheredRepository.getClass();
        Flow buffer$default =
                FlowKt.buffer$default(
                        FlowKt.callbackFlow(
                                new TetheredRepository$tetheredInterfacesFlow$1(
                                        tetheredRepository, null)),
                        -1);
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        FlowsKt.collectLatestWithLifecycle(
                FlowKt.flowOn(
                        FlowKt.buffer$default(
                                FlowKt.combine(
                                        FlowKt.flowOn(buffer$default, defaultScheduler),
                                        FlowKt.flowOn(
                                                FlowKt.buffer$default(
                                                        FlowKt.transformLatest(
                                                                FlowKt.merge(
                                                                        new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(
                                                                                null),
                                                                        BroadcastReceiverFlowKt
                                                                                .broadcastReceiverFlow(
                                                                                        tetheredRepository
                                                                                                .context,
                                                                                        new IntentFilter(
                                                                                                "android.bluetooth.adapter.action.STATE_CHANGED"))),
                                                                new TetheredRepository$isBluetoothTetheringOnFlow$$inlined$flatMapLatest$1(
                                                                        tetheredRepository, null)),
                                                        -1),
                                                defaultScheduler),
                                        new TetheredRepository$tetheredTypesFlow$1(3, null)),
                                -1),
                        defaultScheduler),
                viewLifecycleOwner,
                Lifecycle.State.STARTED,
                new TetherPreferenceController$onViewCreated$2(this, null));
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
