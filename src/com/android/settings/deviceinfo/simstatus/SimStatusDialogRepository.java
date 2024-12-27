package com.android.settings.deviceinfo.simstatus;

import android.content.Context;
import android.telephony.CarrierConfigManager;

import com.android.settings.network.telephony.SimSlotRepository;
import com.android.settings.network.telephony.ims.ImsMmTelRepositoryImpl;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SimStatusDialogRepository {
    public final CarrierConfigManager carrierConfigManager;
    public final Function1 imsMmTelRepositoryFactory;
    public final SignalStrengthRepository signalStrengthRepository;
    public final SimSlotRepository simSlotRepository;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SimStatusDialogInfo {
        public final Boolean imsRegistered;
        public final String signalStrength;

        public SimStatusDialogInfo(String str, Boolean bool) {
            this.signalStrength = str;
            this.imsRegistered = bool;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SimStatusDialogInfo)) {
                return false;
            }
            SimStatusDialogInfo simStatusDialogInfo = (SimStatusDialogInfo) obj;
            return Intrinsics.areEqual(this.signalStrength, simStatusDialogInfo.signalStrength)
                    && Intrinsics.areEqual(this.imsRegistered, simStatusDialogInfo.imsRegistered);
        }

        public final int hashCode() {
            String str = this.signalStrength;
            int hashCode = (str == null ? 0 : str.hashCode()) * 31;
            Boolean bool = this.imsRegistered;
            return hashCode + (bool != null ? bool.hashCode() : 0);
        }

        public final String toString() {
            return "SimStatusDialogInfo(signalStrength="
                    + this.signalStrength
                    + ", imsRegistered="
                    + this.imsRegistered
                    + ")";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SimStatusDialogVisibility {
        public final boolean imsRegisteredShowUp;
        public final boolean signalStrengthShowUp;

        public SimStatusDialogVisibility(boolean z, boolean z2) {
            this.signalStrengthShowUp = z;
            this.imsRegisteredShowUp = z2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SimStatusDialogVisibility)) {
                return false;
            }
            SimStatusDialogVisibility simStatusDialogVisibility = (SimStatusDialogVisibility) obj;
            return this.signalStrengthShowUp == simStatusDialogVisibility.signalStrengthShowUp
                    && this.imsRegisteredShowUp == simStatusDialogVisibility.imsRegisteredShowUp;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.imsRegisteredShowUp)
                    + (Boolean.hashCode(this.signalStrengthShowUp) * 31);
        }

        public final String toString() {
            return "SimStatusDialogVisibility(signalStrengthShowUp="
                    + this.signalStrengthShowUp
                    + ", imsRegisteredShowUp="
                    + this.imsRegisteredShowUp
                    + ")";
        }
    }

    public SimStatusDialogRepository(final Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        SimSlotRepository simSlotRepository = new SimSlotRepository(context);
        SignalStrengthRepository signalStrengthRepository = new SignalStrengthRepository(context);
        Function1 function1 =
                new Function1() { // from class:
                                  // com.android.settings.deviceinfo.simstatus.SimStatusDialogRepository.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return new ImsMmTelRepositoryImpl(context, ((Number) obj).intValue());
                    }
                };
        this.simSlotRepository = simSlotRepository;
        this.signalStrengthRepository = signalStrengthRepository;
        this.imsMmTelRepositoryFactory = function1;
        Object systemService = context.getSystemService((Class<Object>) CarrierConfigManager.class);
        Intrinsics.checkNotNull(systemService);
        this.carrierConfigManager = (CarrierConfigManager) systemService;
    }
}
