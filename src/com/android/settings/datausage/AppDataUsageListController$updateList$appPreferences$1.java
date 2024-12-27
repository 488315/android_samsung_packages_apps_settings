package com.android.settings.datausage;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.UserHandle;

import androidx.preference.Preference;

import com.android.settings.datausage.lib.AppPreferenceRepository;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Landroidx/preference/Preference;",
            "Lkotlinx/coroutines/CoroutineScope;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class AppDataUsageListController$updateList$appPreferences$1 extends SuspendLambda
        implements Function2 {
    int label;
    final /* synthetic */ AppDataUsageListController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppDataUsageListController$updateList$appPreferences$1(
            AppDataUsageListController appDataUsageListController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = appDataUsageListController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AppDataUsageListController$updateList$appPreferences$1(
                this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppDataUsageListController$updateList$appPreferences$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        AppPreferenceRepository appPreferenceRepository;
        List uids;
        Preference preference;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        appPreferenceRepository = this.this$0.repository;
        uids = this.this$0.uids;
        appPreferenceRepository.getClass();
        Intrinsics.checkNotNullParameter(uids, "uids");
        ArrayList arrayList = new ArrayList();
        Iterator it = uids.iterator();
        while (it.hasNext()) {
            int intValue = ((Number) it.next()).intValue();
            int userId = UserHandle.getUserId(intValue);
            String[] packagesForUid =
                    appPreferenceRepository.packageManager.getPackagesForUid(intValue);
            if (packagesForUid == null) {
                packagesForUid = new String[0];
            }
            ArrayList arrayList2 = new ArrayList();
            for (String str : packagesForUid) {
                try {
                    ApplicationInfo applicationInfoAsUser =
                            appPreferenceRepository.packageManager.getApplicationInfoAsUser(
                                    str, 0, userId);
                    Intrinsics.checkNotNullExpressionValue(
                            applicationInfoAsUser, "getApplicationInfoAsUser(...)");
                    preference = new Preference(appPreferenceRepository.context);
                    preference.setIcon(
                            appPreferenceRepository.iconDrawableFactory.getBadgedIcon(
                                    applicationInfoAsUser));
                    preference.setTitle(
                            applicationInfoAsUser.loadLabel(
                                    appPreferenceRepository.packageManager));
                    preference.setSelectable(false);
                } catch (PackageManager.NameNotFoundException unused) {
                    preference = null;
                }
                if (preference != null) {
                    arrayList2.add(preference);
                }
            }
            CollectionsKt___CollectionsKt.addAll(arrayList2, arrayList);
        }
        return arrayList;
    }
}
