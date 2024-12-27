package com.android.settings.spa.app.specialaccess;

import android.content.pm.ApplicationInfo;
import android.nfc.NfcAdapter;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0016\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0001H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lcom/android/settings/spa/app/specialaccess/NfcTagAppsSettingsRecord;",
            "userId",
            ApnSettings.MVNO_NONE,
            "appList",
            "Landroid/content/pm/ApplicationInfo;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class NfcTagAppsSettingsListModel$transform$1 extends SuspendLambda implements Function3 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NfcTagAppsSettingsListModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NfcTagAppsSettingsListModel$transform$1(
            NfcTagAppsSettingsListModel nfcTagAppsSettingsListModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = nfcTagAppsSettingsListModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int intValue = ((Number) obj).intValue();
        NfcTagAppsSettingsListModel$transform$1 nfcTagAppsSettingsListModel$transform$1 =
                new NfcTagAppsSettingsListModel$transform$1(this.this$0, (Continuation) obj3);
        nfcTagAppsSettingsListModel$transform$1.I$0 = intValue;
        nfcTagAppsSettingsListModel$transform$1.L$0 = (List) obj2;
        return nfcTagAppsSettingsListModel$transform$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Map emptyMap;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.I$0;
        List list = (List) this.L$0;
        NfcTagAppsSettingsListModel nfcTagAppsSettingsListModel = this.this$0;
        int i2 = NfcTagAppsSettingsListModel.$r8$clinit;
        NfcAdapter defaultAdapter =
                NfcAdapter.getDefaultAdapter(nfcTagAppsSettingsListModel.context);
        if (defaultAdapter == null || !defaultAdapter.isTagIntentAppPreferenceSupported()) {
            emptyMap = MapsKt__MapsKt.emptyMap();
        } else {
            emptyMap = defaultAdapter.getTagIntentAppPreferenceForUser(i);
            Intrinsics.checkNotNullExpressionValue(
                    emptyMap, "getTagIntentAppPreferenceForUser(...)");
        }
        List<ApplicationInfo> list2 = list;
        NfcTagAppsSettingsListModel nfcTagAppsSettingsListModel2 = this.this$0;
        ArrayList arrayList =
                new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        for (ApplicationInfo applicationInfo : list2) {
            Boolean bool = (Boolean) emptyMap.get(applicationInfo.packageName);
            nfcTagAppsSettingsListModel2.getClass();
            arrayList.add(
                    new NfcTagAppsSettingsRecord(
                            applicationInfo,
                            new NfcTagAppsSettingsController(
                                    Intrinsics.areEqual(bool, Boolean.TRUE)),
                            bool != null));
        }
        return arrayList;
    }
}
