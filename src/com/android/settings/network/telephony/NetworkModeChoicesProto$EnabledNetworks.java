package com.android.settings.network.telephony;

import com.google.protobuf.Internal;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public enum NetworkModeChoicesProto$EnabledNetworks implements Internal.EnumLite {
    ENABLED_NETWORKS_UNSPECIFIED(0),
    ENABLED_NETWORKS_UNKNOWN(1),
    ENABLED_NETWORKS_CDMA_CHOICES(2),
    ENABLED_NETWORKS_CDMA_NO_LTE_CHOICES(3),
    ENABLED_NETWORKS_CDMA_ONLY_LTE_CHOICES(4),
    ENABLED_NETWORKS_TDSCDMA_CHOICES(5),
    ENABLED_NETWORKS_EXCEPT_GSM_LTE_CHOICES(6),
    ENABLED_NETWORKS_EXCEPT_GSM_4G_CHOICES(7),
    ENABLED_NETWORKS_EXCEPT_GSM_CHOICES(8),
    ENABLED_NETWORKS_EXCEPT_LTE_CHOICES(9),
    ENABLED_NETWORKS_4G_CHOICES(10),
    ENABLED_NETWORKS_CHOICES(11),
    PREFERRED_NETWORK_MODE_CHOICES_WORLD_MODE(12),
    ENABLED_NETWORKS_4G_CHOICES_EXCEPT_GSM_3G(13),
    ENABLED_NETWORKS_CHOICES_EXCEPT_GSM_3G(14);

    private final int value;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EnabledNetworksVerifier implements Internal.EnumVerifier {
        public static final EnabledNetworksVerifier INSTANCE = new EnabledNetworksVerifier(0);
        public static final EnabledNetworksVerifier INSTANCE$1 = new EnabledNetworksVerifier(1);
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ EnabledNetworksVerifier(int i) {
            this.$r8$classId = i;
        }

        @Override // com.google.protobuf.Internal.EnumVerifier
        public final boolean isInRange(int i) {
            switch (this.$r8$classId) {
                case 0:
                    if (NetworkModeChoicesProto$EnabledNetworks.forNumber(i) != null) {}
                    break;
                default:
                    if (NetworkModeChoicesProto$UiOptions.PresentFormat.forNumber(i) != null) {}
                    break;
            }
            return false;
        }
    }

    NetworkModeChoicesProto$EnabledNetworks(int i) {
        this.value = i;
    }

    public static NetworkModeChoicesProto$EnabledNetworks forNumber(int i) {
        switch (i) {
            case 0:
                return ENABLED_NETWORKS_UNSPECIFIED;
            case 1:
                return ENABLED_NETWORKS_UNKNOWN;
            case 2:
                return ENABLED_NETWORKS_CDMA_CHOICES;
            case 3:
                return ENABLED_NETWORKS_CDMA_NO_LTE_CHOICES;
            case 4:
                return ENABLED_NETWORKS_CDMA_ONLY_LTE_CHOICES;
            case 5:
                return ENABLED_NETWORKS_TDSCDMA_CHOICES;
            case 6:
                return ENABLED_NETWORKS_EXCEPT_GSM_LTE_CHOICES;
            case 7:
                return ENABLED_NETWORKS_EXCEPT_GSM_4G_CHOICES;
            case 8:
                return ENABLED_NETWORKS_EXCEPT_GSM_CHOICES;
            case 9:
                return ENABLED_NETWORKS_EXCEPT_LTE_CHOICES;
            case 10:
                return ENABLED_NETWORKS_4G_CHOICES;
            case 11:
                return ENABLED_NETWORKS_CHOICES;
            case 12:
                return PREFERRED_NETWORK_MODE_CHOICES_WORLD_MODE;
            case 13:
                return ENABLED_NETWORKS_4G_CHOICES_EXCEPT_GSM_3G;
            case 14:
                return ENABLED_NETWORKS_CHOICES_EXCEPT_GSM_3G;
            default:
                return null;
        }
    }

    @Override // com.google.protobuf.Internal.EnumLite
    public final int getNumber() {
        return this.value;
    }
}
