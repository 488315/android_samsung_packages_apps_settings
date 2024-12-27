package com.android.settings.spa.app.specialaccess;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.util.Log;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;

import com.android.settings.R;
import com.android.settingslib.spa.livedata.LiveDataExtKt;
import com.android.settingslib.spaprivileged.model.app.AppRecord;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;
import com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel;
import com.android.settingslib.spaprivileged.template.app.TogglePermissionInternalAppListModel$filter$$inlined$filterItem$1;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NfcTagAppsSettingsListModel implements TogglePermissionAppListModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;

    static {
        Intrinsics.checkNotNullExpressionValue(PackageManager.PackageInfoFlags.of(1L), "of(...)");
    }

    public NfcTagAppsSettingsListModel(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        context.getPackageManager();
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final Flow filter(
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            final TogglePermissionInternalAppListModel$filter$$inlined$filterItem$1
                    togglePermissionInternalAppListModel$filter$$inlined$filterItem$1) {
        return new Flow() { // from class:
            // com.android.settings.spa.app.specialaccess.NfcTagAppsSettingsListModel$filter$$inlined$map$1

            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
            /* renamed from: com.android.settings.spa.app.specialaccess.NfcTagAppsSettingsListModel$filter$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                @Metadata(
                        k = 3,
                        mv = {1, 9, 0},
                        xi = 48)
                /* renamed from: com.android.settings.spa.app.specialaccess.NfcTagAppsSettingsListModel$filter$$inlined$map$1$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(
                        java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.settings.spa.app.specialaccess.NfcTagAppsSettingsListModel$filter$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.settings.spa.app.specialaccess.NfcTagAppsSettingsListModel$filter$$inlined$map$1$2$1 r0 = (com.android.settings.spa.app.specialaccess.NfcTagAppsSettingsListModel$filter$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.settings.spa.app.specialaccess.NfcTagAppsSettingsListModel$filter$$inlined$map$1$2$1 r0 = new com.android.settings.spa.app.specialaccess.NfcTagAppsSettingsListModel$filter$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L5f
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        java.util.List r6 = (java.util.List) r6
                        java.lang.Iterable r6 = (java.lang.Iterable) r6
                        java.util.ArrayList r7 = new java.util.ArrayList
                        r7.<init>()
                        java.util.Iterator r6 = r6.iterator()
                    L3f:
                        boolean r2 = r6.hasNext()
                        if (r2 == 0) goto L54
                        java.lang.Object r2 = r6.next()
                        r4 = r2
                        com.android.settings.spa.app.specialaccess.NfcTagAppsSettingsRecord r4 = (com.android.settings.spa.app.specialaccess.NfcTagAppsSettingsRecord) r4
                        boolean r4 = r4.isSupported
                        if (r4 == 0) goto L3f
                        r7.add(r2)
                        goto L3f
                    L54:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r7, r0)
                        if (r5 != r1) goto L5f
                        return r1
                    L5f:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException(
                            "Method not decompiled:"
                                + " com.android.settings.spa.app.specialaccess.NfcTagAppsSettingsListModel$filter$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                + " kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect =
                        togglePermissionInternalAppListModel$filter$$inlined$filterItem$1.collect(
                                new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final int getFooterResId() {
        return R.string.change_nfc_tag_apps_detail_summary;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final int getPageTitleResId() {
        return R.string.change_nfc_tag_apps_title;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final int getSwitchTitleResId() {
        return R.string.change_nfc_tag_apps_detail_switch;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final Function0 isAllowed(AppRecord appRecord, Composer composer) {
        NfcTagAppsSettingsRecord record = (NfcTagAppsSettingsRecord) appRecord;
        Intrinsics.checkNotNullParameter(record, "record");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(617553305);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Function0 observeAsCallback =
                LiveDataExtKt.observeAsCallback(record.controller._allowed, composerImpl);
        composerImpl.end(false);
        return observeAsCallback;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final boolean isChangeable(AppRecord appRecord) {
        NfcTagAppsSettingsRecord record = (NfcTagAppsSettingsRecord) appRecord;
        Intrinsics.checkNotNullParameter(record, "record");
        return true;
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final void setAllowed(AppRecord appRecord, boolean z) {
        NfcTagAppsSettingsRecord record = (NfcTagAppsSettingsRecord) appRecord;
        Intrinsics.checkNotNullParameter(record, "record");
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(this.context);
        if (defaultAdapter != null) {
            if (defaultAdapter.setTagIntentAppPreferenceForUser(
                            ApplicationInfosKt.getUserId(record.app), record.app.packageName, z)
                    == 0) {
                record.controller._allowed.postValue(Boolean.valueOf(z));
            } else {
                Log.e("NfcTagAppsSettingsListModel", "Error updating TagIntentAppPreference");
            }
        }
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final Flow transform(
            FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
                    flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
            FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1
                    flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1) {
        return FlowKt.flowCombine(
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2,
                flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1,
                new NfcTagAppsSettingsListModel$transform$1(this, null));
    }

    @Override // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
    public final AppRecord transformItem(ApplicationInfo app) {
        Map emptyMap;
        Intrinsics.checkNotNullParameter(app, "app");
        int userId = ApplicationInfosKt.getUserId(app);
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(this.context);
        if (defaultAdapter == null || !defaultAdapter.isTagIntentAppPreferenceSupported()) {
            emptyMap = MapsKt__MapsKt.emptyMap();
        } else {
            emptyMap = defaultAdapter.getTagIntentAppPreferenceForUser(userId);
            Intrinsics.checkNotNullExpressionValue(
                    emptyMap, "getTagIntentAppPreferenceForUser(...)");
        }
        Boolean bool = (Boolean) emptyMap.get(app.packageName);
        return new NfcTagAppsSettingsRecord(
                app,
                new NfcTagAppsSettingsController(Intrinsics.areEqual(bool, Boolean.TRUE)),
                bool != null);
    }
}
