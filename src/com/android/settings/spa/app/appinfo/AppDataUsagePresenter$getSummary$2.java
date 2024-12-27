package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.net.NetworkTemplate;
import android.text.format.DateUtils;

import com.android.settings.R;
import com.android.settings.datausage.lib.AppDataUsageSummaryRepository;
import com.android.settings.datausage.lib.DataUsageFormatter;
import com.android.settings.datausage.lib.NetworkUsageData;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppDataUsagePresenter$getSummary$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ NetworkTemplate $template;
    int label;
    final /* synthetic */ AppDataUsagePresenter this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppDataUsagePresenter$getSummary$2(
            AppDataUsagePresenter appDataUsagePresenter,
            NetworkTemplate networkTemplate,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = appDataUsagePresenter;
        this.$template = networkTemplate;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AppDataUsagePresenter$getSummary$2(this.this$0, this.$template, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppDataUsagePresenter$getSummary$2)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AppDataUsagePresenter appDataUsagePresenter = this.this$0;
            AppDataUsageSummaryRepository appDataUsageSummaryRepository =
                    (AppDataUsageSummaryRepository)
                            appDataUsagePresenter.repositoryFactory.invoke(
                                    appDataUsagePresenter.context, this.$template);
            int i2 = this.this$0.app.uid;
            this.label = 1;
            obj = appDataUsageSummaryRepository.querySummary(i2, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        NetworkUsageData networkUsageData = (NetworkUsageData) obj;
        if (networkUsageData == null || networkUsageData.usage == 0) {
            return this.this$0.context.getString(R.string.no_data_usage);
        }
        Context context = this.this$0.context;
        DataUsageFormatter.FormattedDataUsage formatUsage = networkUsageData.formatUsage(context);
        Context context2 = this.this$0.context;
        Intrinsics.checkNotNullParameter(context2, "context");
        String formatDateTime =
                DateUtils.formatDateTime(context2, networkUsageData.startTime, 65552);
        Intrinsics.checkNotNullExpressionValue(formatDateTime, "formatDateTime(...)");
        return context.getString(
                R.string.data_summary_format, formatUsage.displayText, formatDateTime);
    }
}
