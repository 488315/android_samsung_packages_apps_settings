package com.android.settings.datausage;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkTemplate;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.lifecycle.LifecycleCoroutineScope;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.datausage.lib.AppDataUsageRepository;
import com.android.settings.datausage.lib.NetworkUsageData;
import com.android.settingslib.AppItem;
import com.android.settingslib.net.UidDetailProvider;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Job;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000r\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
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
                + "\u0000\n"
                + "\u0002\u0010\t\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\b\u0017\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u0006\u0010\u001a\u001a\u00020\u001bJ\u0010\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0018\u0010"
                + " \u001a\u00020\u00152\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001bH\u0007J%\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010\u00192\u0006\u0010'\u001a\u00020\u001b2\u0006\u0010#\u001a\u00020\u001b¢\u0006\u0002\u0010(J\u0014\u0010)\u001a\u00020\u00152\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020"
                + "\t0\bR\u0016\u0010\u0007\u001a\n"
                + "\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\n"
                + "\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\f\u001a\u00020\r"
                + "X\u0082.¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000¨\u0006*"
        },
        d2 = {
            "Lcom/android/settings/datausage/DataUsageListAppsController;",
            "Lcom/android/settings/core/BasePreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "cycleData",
            ApnSettings.MVNO_NONE,
            "Lcom/android/settings/datausage/lib/NetworkUsageData;",
            "lifecycleScope",
            "Landroidx/lifecycle/LifecycleCoroutineScope;",
            "preference",
            "Landroidx/preference/PreferenceGroup;",
            "repository",
            "Lcom/android/settings/datausage/lib/AppDataUsageRepository;",
            "template",
            "Landroid/net/NetworkTemplate;",
            "uidDetailProvider",
            "Lcom/android/settingslib/net/UidDetailProvider;",
            "displayPreference",
            ApnSettings.MVNO_NONE,
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "getSecureFolderUsage",
            ApnSettings.MVNO_NONE,
            "init",
            "onViewCreated",
            "viewLifecycleOwner",
            "Landroidx/lifecycle/LifecycleOwner;",
            "startAppDataUsage",
            "item",
            "Lcom/android/settingslib/AppItem;",
            "endTime",
            "update",
            "Lkotlinx/coroutines/Job;",
            "carrierId",
            "startTime",
            "(Ljava/lang/Integer;JJ)Lkotlinx/coroutines/Job;",
            "updateCycles",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public class DataUsageListAppsController extends BasePreferenceController {
    public static final int $stable = 8;
    private List<NetworkUsageData> cycleData;
    private LifecycleCoroutineScope lifecycleScope;
    private PreferenceGroup preference;
    private AppDataUsageRepository repository;
    private NetworkTemplate template;
    private final UidDetailProvider uidDetailProvider;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataUsageListAppsController(Context context, String preferenceKey) {
        super(context, preferenceKey);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preferenceKey, "preferenceKey");
        this.uidDetailProvider = new UidDetailProvider(context);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        Preference findPreference = screen.findPreference(getPreferenceKey());
        Intrinsics.checkNotNull(findPreference);
        this.preference = (PreferenceGroup) findPreference;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    public final long getSecureFolderUsage() {
        AppDataUsageRepository appDataUsageRepository = this.repository;
        if (appDataUsageRepository != null) {
            return appDataUsageRepository.secureusage;
        }
        Intrinsics.throwUninitializedPropertyAccessException("repository");
        throw null;
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

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void init(NetworkTemplate template) {
        Intrinsics.checkNotNullParameter(template, "template");
        this.template = template;
        Context mContext = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        this.repository =
                new AppDataUsageRepository(
                        mContext,
                        ActivityManager.getCurrentUser(),
                        template,
                        new Function1() { // from class:
                                          // com.android.settings.datausage.DataUsageListAppsController$init$1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                UidDetailProvider uidDetailProvider;
                                AppItem appItem = (AppItem) obj;
                                Intrinsics.checkNotNullParameter(appItem, "appItem");
                                uidDetailProvider =
                                        DataUsageListAppsController.this.uidDetailProvider;
                                return uidDetailProvider.getUidDetail(appItem.key, true)
                                        .packageName;
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
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void onViewCreated(LifecycleOwner viewLifecycleOwner) {
        Intrinsics.checkNotNullParameter(viewLifecycleOwner, "viewLifecycleOwner");
        this.lifecycleScope = LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public final void startAppDataUsage(AppItem item, long endTime) {
        Intrinsics.checkNotNullParameter(item, "item");
        List<NetworkUsageData> list = this.cycleData;
        if (list == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("app_item", item);
        Parcelable parcelable = this.template;
        if (parcelable == null) {
            Intrinsics.throwUninitializedPropertyAccessException("template");
            throw null;
        }
        bundle.putParcelable("network_template", parcelable);
        ArrayList arrayList = new ArrayList();
        for (NetworkUsageData networkUsageData : list) {
            if (arrayList.isEmpty()) {
                arrayList.add(Long.valueOf(networkUsageData.endTime));
            }
            arrayList.add(Long.valueOf(networkUsageData.startTime));
        }
        bundle.putSerializable("network_cycles", arrayList);
        bundle.putLong("selected_cycle", endTime);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = AppDataUsage.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        subSettingLauncher.setTitleRes(R.string.data_usage_app_summary_title, null);
        launchRequest.mArguments = bundle;
        launchRequest.mSourceMetricsCategory = getMetricsCategory();
        subSettingLauncher.launch();
    }

    public final Job update(Integer carrierId, long startTime, long endTime) {
        LifecycleCoroutineScope lifecycleCoroutineScope = this.lifecycleScope;
        if (lifecycleCoroutineScope != null) {
            return BuildersKt.launch$default(
                    lifecycleCoroutineScope,
                    null,
                    null,
                    new DataUsageListAppsController$update$1(
                            this, carrierId, startTime, endTime, null),
                    3);
        }
        Intrinsics.throwUninitializedPropertyAccessException("lifecycleScope");
        throw null;
    }

    public final void updateCycles(List<NetworkUsageData> cycleData) {
        Intrinsics.checkNotNullParameter(cycleData, "cycleData");
        this.cycleData = cycleData;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
