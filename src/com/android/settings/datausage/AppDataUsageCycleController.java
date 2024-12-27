package com.android.settings.datausage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.datausage.lib.IAppDataUsageDetailsRepository;
import com.android.settings.datausage.lib.NetworkUsageDetailsData;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u007f\n"
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
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0000\n"
                + "\u0002\u0010!\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0004\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\t\n"
                + "\u0002\b\u0007\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0004*\u0001\f\b\u0007\u0018\u0000"
                + " 42\u00020\u0001:\u00014B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0014\u0010\u001e\u001a\u00020\u00122\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011J\u0010\u0010\u001f\u001a\u00020\u00122\u0006\u0010"
                + " \u001a\u00020!H\u0016J\b\u0010\"\u001a\u00020\u000fH\u0016J\"\u0010#\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00192\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00120\u0014J\u0010\u0010$\u001a\u00020\u00122\u0006\u0010%\u001a\u00020&H\u0016J\u001c\u0010'\u001a\u00020\u00122\f\u0010(\u001a\b\u0012\u0004\u0012\u00020)0\u001b2\u0006\u0010*\u001a\u00020)J\u000e\u0010+\u001a\u00020\u0012H\u0082@¢\u0006\u0002\u0010,J&\u0010-\u001a\u00020\u00122\u0006\u0010.\u001a\u00020)2\u0006\u0010/\u001a\u00020)2\u0006\u00100\u001a\u000201H\u0082@¢\u0006\u0002\u00102J\u001e\u00103\u001a\u00020\u00122\u0006\u0010.\u001a\u00020)2\u0006\u0010/\u001a\u00020)2\u0006\u00100\u001a\u000201R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\t\u001a\u00020\n"
                + "X\u0082.¢\u0006\u0002\n"
                + "\u0000R\u0010\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0004\n"
                + "\u0002\u0010\r"
                + "R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u001a\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00120\u0014X\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082.¢\u0006\u0002\n"
                + "\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00150\u001bX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00150\u001dX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000¨\u00065"
        },
        d2 = {
            "Lcom/android/settings/datausage/AppDataUsageCycleController;",
            "Lcom/android/settings/core/BasePreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "coroutineScope",
            "Lkotlinx/coroutines/CoroutineScope;",
            "cycleAdapter",
            "Lcom/android/settings/datausage/CycleAdapter;",
            "cycleListener",
            "com/android/settings/datausage/AppDataUsageCycleController$cycleListener$1",
            "Lcom/android/settings/datausage/AppDataUsageCycleController$cycleListener$1;",
            "mCyclePosition",
            ApnSettings.MVNO_NONE,
            "onDateSetByUser",
            "Lkotlin/Function0;",
            ApnSettings.MVNO_NONE,
            "onUsageDataUpdated",
            "Lkotlin/Function1;",
            "Lcom/android/settings/datausage/lib/NetworkUsageDetailsData;",
            "preference",
            "Lcom/android/settings/datausage/SpinnerPreference;",
            "repository",
            "Lcom/android/settings/datausage/lib/IAppDataUsageDetailsRepository;",
            "usageDetailsDataList",
            ApnSettings.MVNO_NONE,
            "usageDetailsDataListCHN",
            ApnSettings.MVNO_NONE,
            "dateSetByUser",
            "displayPreference",
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getAvailabilityStatus",
            "init",
            "onViewCreated",
            "viewLifecycleOwner",
            "Landroidx/lifecycle/LifecycleOwner;",
            "setInitialCycles",
            "initialCycles",
            ApnSettings.MVNO_NONE,
            "initialSelectedEndTime",
            "update",
            "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;",
            "updateAdapter",
            "startTime",
            "endTime",
            "needAddItem",
            ApnSettings.MVNO_NONE,
            "(JJZLkotlin/coroutines/Continuation;)Ljava/lang/Object;",
            "updateView",
            "Companion",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class AppDataUsageCycleController extends BasePreferenceController {
    public static final int $stable = 8;
    private static final String TAG =
            Reflection.factory
                    .getOrCreateKotlinClass(AppDataUsageCycleController.class)
                    .getSimpleName();
    private CoroutineScope coroutineScope;
    private CycleAdapter cycleAdapter;
    private final AppDataUsageCycleController$cycleListener$1 cycleListener;
    private int mCyclePosition;
    private Function0 onDateSetByUser;
    private Function1 onUsageDataUpdated;
    private SpinnerPreference preference;
    private IAppDataUsageDetailsRepository repository;
    private List<NetworkUsageDetailsData> usageDetailsDataList;
    private List<NetworkUsageDetailsData> usageDetailsDataListCHN;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.settings.datausage.AppDataUsageCycleController$cycleListener$1] */
    public AppDataUsageCycleController(Context context, String preferenceKey) {
        super(context, preferenceKey);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(preferenceKey, "preferenceKey");
        this.onUsageDataUpdated =
                new Function1() { // from class:
                                  // com.android.settings.datausage.AppDataUsageCycleController$onUsageDataUpdated$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        NetworkUsageDetailsData it = (NetworkUsageDetailsData) obj;
                        Intrinsics.checkNotNullParameter(it, "it");
                        return Unit.INSTANCE;
                    }
                };
        this.usageDetailsDataList = EmptyList.INSTANCE;
        this.usageDetailsDataListCHN = new ArrayList();
        this.onDateSetByUser =
                new Function0() { // from class:
                                  // com.android.settings.datausage.AppDataUsageCycleController$onDateSetByUser$1
                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                        return Unit.INSTANCE;
                    }
                };
        this.cycleListener =
                new AdapterView.OnItemSelectedListener() { // from class:
                    // com.android.settings.datausage.AppDataUsageCycleController$cycleListener$1
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public final void onItemSelected(
                            AdapterView adapterView, View view, int i, long j) {
                        List list;
                        Function1 function1;
                        SpinnerPreference spinnerPreference;
                        Function0 function0;
                        String str;
                        int i2;
                        int i3;
                        CycleAdapter cycleAdapter;
                        SpinnerPreference spinnerPreference2;
                        int i4;
                        CycleAdapter cycleAdapter2;
                        if (!Rune.SUPPORT_SMARTMANAGER_CN) {
                            list = AppDataUsageCycleController.this.usageDetailsDataList;
                            NetworkUsageDetailsData networkUsageDetailsData =
                                    (NetworkUsageDetailsData)
                                            CollectionsKt___CollectionsKt.getOrNull(i, list);
                            if (networkUsageDetailsData != null) {
                                function1 = AppDataUsageCycleController.this.onUsageDataUpdated;
                                function1.invoke(networkUsageDetailsData);
                                return;
                            }
                            return;
                        }
                        spinnerPreference = AppDataUsageCycleController.this.preference;
                        if (spinnerPreference == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("preference");
                            throw null;
                        }
                        Object obj = spinnerPreference.mCurrentObject;
                        Intrinsics.checkNotNull(
                                obj,
                                "null cannot be cast to non-null type"
                                    + " com.android.settings.datausage.CycleAdapter.CycleItem");
                        CycleAdapter.CycleItem cycleItem = (CycleAdapter.CycleItem) obj;
                        if (!cycleItem.dateSetByUser) {
                            AppDataUsageCycleController.this.mCyclePosition = i;
                            AppDataUsageCycleController.this.updateView(
                                    cycleItem.start, cycleItem.end, false);
                            return;
                        }
                        function0 = AppDataUsageCycleController.this.onDateSetByUser;
                        function0.mo1068invoke();
                        str = AppDataUsageCycleController.TAG;
                        i2 = AppDataUsageCycleController.this.mCyclePosition;
                        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                                i2, "the mCycleSpinnerPosition:", str);
                        i3 = AppDataUsageCycleController.this.mCyclePosition;
                        cycleAdapter = AppDataUsageCycleController.this.cycleAdapter;
                        if (cycleAdapter == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("cycleAdapter");
                            throw null;
                        }
                        if (i3 >= cycleAdapter.getCount()) {
                            AppDataUsageCycleController appDataUsageCycleController =
                                    AppDataUsageCycleController.this;
                            cycleAdapter2 = appDataUsageCycleController.cycleAdapter;
                            if (cycleAdapter2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException(
                                        "cycleAdapter");
                                throw null;
                            }
                            appDataUsageCycleController.mCyclePosition =
                                    cycleAdapter2.getCount() - 1;
                        }
                        spinnerPreference2 = AppDataUsageCycleController.this.preference;
                        if (spinnerPreference2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("preference");
                            throw null;
                        }
                        i4 = AppDataUsageCycleController.this.mCyclePosition;
                        spinnerPreference2.setSelection(i4);
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public final void onNothingSelected(AdapterView adapterView) {}
                };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object update(kotlin.coroutines.Continuation r7) {
        /*
            Method dump skipped, instructions count: 267
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.datausage.AppDataUsageCycleController.update(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object updateAdapter(
            long r16, long r18, boolean r20, kotlin.coroutines.Continuation r21) {
        /*
            r15 = this;
            r7 = r15
            r0 = r21
            boolean r1 = r0 instanceof com.android.settings.datausage.AppDataUsageCycleController$updateAdapter$1
            if (r1 == 0) goto L17
            r1 = r0
            com.android.settings.datausage.AppDataUsageCycleController$updateAdapter$1 r1 = (com.android.settings.datausage.AppDataUsageCycleController$updateAdapter$1) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L17
            int r2 = r2 - r3
            r1.label = r2
        L15:
            r8 = r1
            goto L1d
        L17:
            com.android.settings.datausage.AppDataUsageCycleController$updateAdapter$1 r1 = new com.android.settings.datausage.AppDataUsageCycleController$updateAdapter$1
            r1.<init>(r15, r0)
            goto L15
        L1d:
            java.lang.Object r0 = r8.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r9 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r10 = 1
            if (r1 == 0) goto L42
            if (r1 != r10) goto L3a
            boolean r1 = r8.Z$0
            long r2 = r8.J$1
            long r4 = r8.J$0
            java.lang.Object r6 = r8.L$0
            com.android.settings.datausage.AppDataUsageCycleController r6 = (com.android.settings.datausage.AppDataUsageCycleController) r6
            kotlin.ResultKt.throwOnFailure(r0)
            r13 = r4
            r5 = r0
            r4 = r1
            r0 = r13
            goto L6b
        L3a:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L42:
            kotlin.ResultKt.throwOnFailure(r0)
            kotlinx.coroutines.scheduling.DefaultScheduler r11 = kotlinx.coroutines.Dispatchers.Default
            com.android.settings.datausage.AppDataUsageCycleController$updateAdapter$data$1 r12 = new com.android.settings.datausage.AppDataUsageCycleController$updateAdapter$data$1
            r6 = 0
            r0 = r12
            r1 = r15
            r2 = r16
            r4 = r18
            r0.<init>(r1, r2, r4, r6)
            r8.L$0 = r7
            r0 = r16
            r8.J$0 = r0
            r2 = r18
            r8.J$1 = r2
            r4 = r20
            r8.Z$0 = r4
            r8.label = r10
            java.lang.Object r5 = kotlinx.coroutines.BuildersKt.withContext(r11, r12, r8)
            if (r5 != r9) goto L6a
            return r9
        L6a:
            r6 = r7
        L6b:
            com.android.settings.datausage.lib.NetworkUsageDetailsData r5 = (com.android.settings.datausage.lib.NetworkUsageDetailsData) r5
            kotlin.jvm.functions.Function1 r7 = r6.onUsageDataUpdated
            r7.invoke(r5)
            if (r4 == 0) goto Lb9
            java.util.List<com.android.settings.datausage.lib.NetworkUsageDetailsData> r4 = r6.usageDetailsDataListCHN
            r4.add(r5)
            com.android.settings.datausage.CycleAdapter r4 = r6.cycleAdapter
            r5 = 0
            java.lang.String r7 = "cycleAdapter"
            if (r4 == 0) goto Lb5
            r4.mIsSettedDate = r10
            r4.mSettedStartTime = r0
            r4.mSettedEndTime = r2
            com.android.settings.datausage.CycleAdapter$CycleItem r8 = new com.android.settings.datausage.CycleAdapter$CycleItem
            android.content.Context r9 = r6.mContext
            r11 = 0
            r15 = r8
            r16 = r9
            r17 = r0
            r19 = r11
            r20 = r2
            r15.<init>(r16, r17, r19, r20)
            r4.add(r8)
            com.android.settings.datausage.SpinnerPreference r0 = r6.preference
            if (r0 == 0) goto Laf
            com.android.settings.datausage.CycleAdapter r1 = r6.cycleAdapter
            if (r1 == 0) goto Lab
            int r1 = r1.getCount()
            int r1 = r1 - r10
            r0.setSelection(r1)
            goto Lb9
        Lab:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r7)
            throw r5
        Laf:
            java.lang.String r0 = "preference"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r0)
            throw r5
        Lb5:
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r7)
            throw r5
        Lb9:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.datausage.AppDataUsageCycleController.updateAdapter(long,"
                    + " long, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void dateSetByUser(Function0 onDateSetByUser) {
        Intrinsics.checkNotNullParameter(onDateSetByUser, "onDateSetByUser");
        this.onDateSetByUser = onDateSetByUser;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        Preference findPreference = screen.findPreference(getPreferenceKey());
        Intrinsics.checkNotNull(findPreference);
        this.preference = (SpinnerPreference) findPreference;
        if (this.cycleAdapter == null) {
            if (!Rune.SUPPORT_SMARTMANAGER_CN) {
                Context context = this.mContext;
                SpinnerPreference spinnerPreference = this.preference;
                if (spinnerPreference != null) {
                    this.cycleAdapter = new CycleAdapter(context, spinnerPreference);
                    return;
                } else {
                    Intrinsics.throwUninitializedPropertyAccessException("preference");
                    throw null;
                }
            }
            Context context2 = this.mContext;
            SpinnerPreference spinnerPreference2 = this.preference;
            if (spinnerPreference2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("preference");
                throw null;
            }
            AppDataUsageCycleController$cycleListener$1
                    appDataUsageCycleController$cycleListener$1 = this.cycleListener;
            CycleAdapter cycleAdapter = new CycleAdapter(context2, spinnerPreference2);
            cycleAdapter.mListener = appDataUsageCycleController$cycleListener$1;
            this.cycleAdapter = cycleAdapter;
        }
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

    public final void init(
            IAppDataUsageDetailsRepository repository, Function1 onUsageDataUpdated) {
        Intrinsics.checkNotNullParameter(repository, "repository");
        Intrinsics.checkNotNullParameter(onUsageDataUpdated, "onUsageDataUpdated");
        this.repository = repository;
        this.onUsageDataUpdated = onUsageDataUpdated;
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
        this.coroutineScope = LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner);
        BuildersKt.launch$default(
                LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner),
                null,
                null,
                new AppDataUsageCycleController$onViewCreated$1(viewLifecycleOwner, this, null),
                3);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public final void setInitialCycles(List<Long> initialCycles, long initialSelectedEndTime) {
        Intrinsics.checkNotNullParameter(initialCycles, "initialCycles");
        if (!initialCycles.isEmpty()) {
            CycleAdapter cycleAdapter = this.cycleAdapter;
            if (cycleAdapter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("cycleAdapter");
                throw null;
            }
            cycleAdapter.clear();
            int i = 0;
            while (i < initialCycles.size() - 1) {
                int i2 = i + 1;
                cycleAdapter.add(
                        new CycleAdapter.CycleItem(
                                cycleAdapter.getContext(),
                                initialCycles.get(i2).longValue(),
                                initialCycles.get(i).longValue()));
                if (initialCycles.get(i).longValue() == initialSelectedEndTime) {
                    cycleAdapter.mSpinner.setSelection(i);
                }
                i = i2;
            }
            SpinnerPreference spinnerPreference = this.preference;
            if (spinnerPreference == null) {
                Intrinsics.throwUninitializedPropertyAccessException("preference");
                throw null;
            }
            spinnerPreference.setVisible(true);
            spinnerPreference.mItemViewVisible = true;
            View view = spinnerPreference.mItemView;
            if (view != null) {
                view.setVisibility(0);
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public final void updateView(long startTime, long endTime, boolean needAddItem) {
        CoroutineScope coroutineScope = this.coroutineScope;
        if (coroutineScope != null) {
            BuildersKt.launch$default(
                    coroutineScope,
                    null,
                    null,
                    new AppDataUsageCycleController$updateView$1(
                            this, startTime, endTime, needAddItem, null),
                    3);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
