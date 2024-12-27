package com.android.settings.network.telephony.gsm;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settings.network.telephony.TelephonyBasePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "serviceState",
            "Landroid/telephony/ServiceState;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class OpenNetworkSelectPagePreferenceController$onViewCreated$2 extends SuspendLambda
        implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ OpenNetworkSelectPagePreferenceController this$0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\f\n"
                    + "\u0000\n"
                    + "\u0002\u0010\r\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\u0010\u0000\u001a\n"
                    + " \u0002*\u0004\u0018\u00010\u00010\u0001*\u00020\u0003H\u008a@"
            },
            d2 = {
                "<anonymous>",
                ApnSettings.MVNO_NONE,
                "kotlin.jvm.PlatformType",
                "Lkotlinx/coroutines/CoroutineScope;"
            },
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settings.network.telephony.gsm.OpenNetworkSelectPagePreferenceController$onViewCreated$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ OpenNetworkSelectPagePreferenceController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                OpenNetworkSelectPagePreferenceController openNetworkSelectPagePreferenceController,
                Continuation continuation) {
            super(2, continuation);
            this.this$0 = openNetworkSelectPagePreferenceController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2))
                    .invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Context context;
            int i;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            context = ((AbstractPreferenceController) this.this$0).mContext;
            i = ((TelephonyBasePreferenceController) this.this$0).mSubId;
            Drawable drawable = MobileNetworkUtils.EMPTY_DRAWABLE;
            SubscriptionManager subscriptionManager =
                    (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
            SubscriptionInfo activeSubscriptionInfo =
                    subscriptionManager == null
                            ? null
                            : subscriptionManager
                                    .createForAllUserProfiles()
                                    .getActiveSubscriptionInfo(i);
            if (activeSubscriptionInfo != null) {
                return activeSubscriptionInfo.getCarrierName();
            }
            TelephonyManager telephonyManager =
                    (TelephonyManager) context.getSystemService(TelephonyManager.class);
            return telephonyManager != null ? telephonyManager.getNetworkOperatorName() : null;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OpenNetworkSelectPagePreferenceController$onViewCreated$2(
            OpenNetworkSelectPagePreferenceController openNetworkSelectPagePreferenceController,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = openNetworkSelectPagePreferenceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        OpenNetworkSelectPagePreferenceController$onViewCreated$2
                openNetworkSelectPagePreferenceController$onViewCreated$2 =
                        new OpenNetworkSelectPagePreferenceController$onViewCreated$2(
                                this.this$0, continuation);
        openNetworkSelectPagePreferenceController$onViewCreated$2.L$0 = obj;
        return openNetworkSelectPagePreferenceController$onViewCreated$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((OpenNetworkSelectPagePreferenceController$onViewCreated$2)
                        create((ServiceState) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Preference preference;
        Context context;
        CharSequence string;
        Preference preference2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ServiceState serviceState = (ServiceState) this.L$0;
            preference = this.this$0.preference;
            if (preference != null) {
                if (serviceState.getState() == 0) {
                    DefaultScheduler defaultScheduler = Dispatchers.Default;
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, null);
                    this.L$0 = preference;
                    this.label = 1;
                    obj = BuildersKt.withContext(defaultScheduler, anonymousClass1, this);
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    preference2 = preference;
                } else {
                    context = ((AbstractPreferenceController) this.this$0).mContext;
                    string = context.getString(R.string.network_disconnected);
                    preference.setSummary(string);
                }
            }
            return Unit.INSTANCE;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        preference2 = (Preference) this.L$0;
        ResultKt.throwOnFailure(obj);
        string = (CharSequence) obj;
        preference = preference2;
        preference.setSummary(string);
        return Unit.INSTANCE;
    }
}
