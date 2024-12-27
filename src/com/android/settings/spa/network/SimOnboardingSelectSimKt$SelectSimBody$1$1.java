package com.android.settings.spa.network;

import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.State;

import com.android.settings.network.SimOnboardingService;
import com.android.settingslib.spa.widget.preference.CheckboxPreferenceModel;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SimOnboardingSelectSimKt$SelectSimBody$1$1 implements CheckboxPreferenceModel {
    public final /* synthetic */ State $phoneNumber;
    public final Function0 changeable;
    public final Function0 checked;
    public final Function1 onCheckedChange;
    public final String title;

    /* JADX WARN: Multi-variable type inference failed */
    public SimOnboardingSelectSimKt$SelectSimBody$1$1(
            Ref$ObjectRef ref$ObjectRef,
            MutableState mutableState,
            final Ref$ObjectRef ref$ObjectRef2,
            final SimOnboardingService simOnboardingService,
            final SubscriptionInfo subscriptionInfo,
            final MutableState mutableState2) {
        this.$phoneNumber = mutableState;
        this.title = (String) ref$ObjectRef.element;
        this.checked =
                new Function0() { // from class:
                                  // com.android.settings.spa.network.SimOnboardingSelectSimKt$SelectSimBody$1$1$checked$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return (Boolean) Ref$ObjectRef.this.element.getValue();
                    }
                };
        this.onCheckedChange =
                new Function1() { // from class:
                                  // com.android.settings.spa.network.SimOnboardingSelectSimKt$SelectSimBody$1$1$onCheckedChange$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Boolean bool = (Boolean) obj;
                        boolean booleanValue = bool.booleanValue();
                        Ref$ObjectRef.this.element.setValue(bool);
                        if (booleanValue) {
                            SimOnboardingService simOnboardingService2 = simOnboardingService;
                            SubscriptionInfo selectedSubInfo = subscriptionInfo;
                            simOnboardingService2.getClass();
                            Intrinsics.checkNotNullParameter(selectedSubInfo, "selectedSubInfo");
                            if (!((ArrayList) simOnboardingService2.userSelectedSubInfoList)
                                    .contains(selectedSubInfo)) {
                                ((ArrayList) simOnboardingService2.userSelectedSubInfoList)
                                        .add(selectedSubInfo);
                            }
                        } else {
                            SimOnboardingService simOnboardingService3 = simOnboardingService;
                            SubscriptionInfo selectedSubInfo2 = subscriptionInfo;
                            simOnboardingService3.getClass();
                            Intrinsics.checkNotNullParameter(selectedSubInfo2, "selectedSubInfo");
                            if (((ArrayList) simOnboardingService3.userSelectedSubInfoList)
                                    .contains(selectedSubInfo2)) {
                                ((ArrayList) simOnboardingService3.userSelectedSubInfoList)
                                        .remove(selectedSubInfo2);
                            }
                        }
                        MutableState mutableState3 = mutableState2;
                        SimOnboardingService simOnboardingService4 = simOnboardingService;
                        TelephonyManager telephonyManager = simOnboardingService4.telephonyManager;
                        boolean z = false;
                        int activeModemCount =
                                telephonyManager != null
                                        ? telephonyManager.getActiveModemCount()
                                        : 0;
                        if (activeModemCount != 0
                                && ((ArrayList) simOnboardingService4.userSelectedSubInfoList)
                                                .size()
                                        == activeModemCount) {
                            z = true;
                        }
                        mutableState3.setValue(Boolean.valueOf(z));
                        return Unit.INSTANCE;
                    }
                };
        this.changeable =
                new Function0() { // from class:
                                  // com.android.settings.spa.network.SimOnboardingSelectSimKt$SelectSimBody$1$1$changeable$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return Boolean.valueOf(
                                subscriptionInfo.isActive()
                                        && (!((Boolean) mutableState2.getValue()).booleanValue()
                                                || (((Boolean) mutableState2.getValue())
                                                                .booleanValue()
                                                        && ((Boolean)
                                                                        ref$ObjectRef2.element
                                                                                .getValue())
                                                                .booleanValue())));
                    }
                };
    }

    public final Function0 getSummary() {
        final State state = this.$phoneNumber;
        return new Function0() { // from class:
                                 // com.android.settings.spa.network.SimOnboardingSelectSimKt$SelectSimBody$1$1$summary$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                String str = (String) State.this.getValue();
                return str == null ? ApnSettings.MVNO_NONE : str;
            }
        };
    }
}
