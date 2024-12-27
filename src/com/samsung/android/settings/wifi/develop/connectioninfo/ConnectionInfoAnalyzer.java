package com.samsung.android.settings.wifi.develop.connectioninfo;

import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ConnectionInfoAnalyzer {
    public int channelWidth;
    public int mGeneration;
    public String mModulation;
    public String mbgn;

    public final void getNetworkInfofromLinkSpeed(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        if (i2 == 6 && this.channelWidth == 0) {
            switch (i) {
                case 7:
                case 8:
                    this.mModulation = "BPSK";
                    this.mbgn = "802.11ax";
                    break;
                case 14:
                case 16:
                case 17:
                    this.mModulation = "BPSK";
                    this.mbgn = "802.11ax";
                    break;
                case 21:
                case 24:
                case 25:
                    this.mModulation = "QPSK";
                    this.mbgn = "802.11ax";
                    break;
                case 29:
                case 32:
                case 34:
                    this.mModulation = "QPSK";
                    this.mbgn = "802.11ax";
                    break;
                case 43:
                case 48:
                case 51:
                    this.mModulation = "QPSK";
                    this.mbgn = "802.11ax";
                    break;
                case 58:
                case 68:
                    this.mModulation = "16-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 65:
                    this.mModulation = "16-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 73:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 77:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 81:
                case 86:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 87:
                case 103:
                    this.mModulation = "16-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 97:
                    this.mModulation = "16-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 108:
                case 114:
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 109:
                case 129:
                    this.mModulation = "1024-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 117:
                case 130:
                case 137:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 121:
                    this.mModulation = "1024-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 131:
                case 154:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 135:
                case 143:
                    this.mModulation = "1024-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 146:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 162:
                case 172:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 175:
                case 206:
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 195:
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 216:
                case IKnoxCustomManager.Stub.TRANSACTION_setBrightness /* 229 */:
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 219:
                case 258:
                    this.mModulation = "1024-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentMode /* 243 */:
                    this.mModulation = "1024-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 270:
                case IKnoxCustomManager.Stub.TRANSACTION_stopProKioskMode /* 286 */:
                    this.mModulation = "1024-QAM";
                    this.mbgn = "802.11ax";
                    break;
                default:
                    this.mModulation = PeripheralConstants.Result.NOT_AVAILABLE;
                    this.mbgn = PeripheralConstants.Result.NOT_AVAILABLE;
                    break;
            }
        }
        if (i2 == 6 && this.channelWidth == 1) {
            switch (i) {
                case 14:
                case 16:
                case 17:
                    this.mModulation = "BPSK";
                    this.mbgn = "802.11ax";
                    break;
                case 29:
                case 32:
                case 34:
                    this.mModulation = "BPSK";
                    this.mbgn = "802.11ax";
                    break;
                case 43:
                case 48:
                case 51:
                    this.mModulation = "QPSK";
                    this.mbgn = "802.11ax";
                    break;
                case 58:
                case 65:
                case 68:
                    this.mModulation = "QPSK";
                    this.mbgn = "802.11ax";
                    break;
                case 87:
                case 97:
                case 103:
                    this.mModulation = "QPSK";
                    this.mbgn = "802.11ax";
                    break;
                case 117:
                case 130:
                case 137:
                    this.mModulation = "16-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 131:
                case 154:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 146:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 162:
                case 172:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 175:
                case 206:
                    this.mModulation = "16-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 195:
                    this.mModulation = "16-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 216:
                case IKnoxCustomManager.Stub.TRANSACTION_setBrightness /* 229 */:
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 219:
                case 258:
                    this.mModulation = "1024-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case IKnoxCustomManager.Stub.TRANSACTION_deleteHomeScreenPage /* 234 */:
                case 260:
                case IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentBroadcast /* 275 */:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentMode /* 243 */:
                    this.mModulation = "1024-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 263:
                case FileType.ASC /* 309 */:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 270:
                case IKnoxCustomManager.Stub.TRANSACTION_stopProKioskMode /* 286 */:
                    this.mModulation = "1024-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case IKnoxCustomManager.Stub.TRANSACTION_startTcpDump /* 292 */:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case FileType.PPTX /* 325 */:
                case FileType.XDW /* 344 */:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 351:
                case FileType.SEVEN_Z /* 412 */:
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 390:
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case FileType.DER /* 433 */:
                case 458:
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 438:
                case FileType.SCC_SCRAP /* 516 */:
                    this.mModulation = "1024-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case VolteConstants.ErrorCode.REQUEST_TERMINATED /* 487 */:
                    this.mModulation = "1024-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 541:
                case 573:
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ax";
                    break;
                default:
                    this.mModulation = PeripheralConstants.Result.NOT_AVAILABLE;
                    this.mbgn = PeripheralConstants.Result.NOT_AVAILABLE;
                    break;
            }
        }
        if (i2 == 6 && this.channelWidth == 2) {
            switch (i) {
                case 30:
                case 34:
                case 36:
                    this.mModulation = "BPSK";
                    this.mbgn = "802.11ax";
                    break;
                case 61:
                case 68:
                case 72:
                    this.mModulation = "BPSK";
                    this.mbgn = "802.11ax";
                    break;
                case 91:
                case 102:
                case 108:
                    this.mModulation = "QPSK";
                    this.mbgn = "802.11ax";
                    break;
                case 122:
                case 136:
                case 144:
                    this.mModulation = "QPSK";
                    this.mbgn = "802.11ax";
                    break;
                case 183:
                case 204:
                case 216:
                    this.mModulation = "QPSK";
                    this.mbgn = "802.11ax";
                    break;
                case IKnoxCustomManager.Stub.TRANSACTION_setHomeScreenMode /* 245 */:
                case 272:
                case IKnoxCustomManager.Stub.TRANSACTION_getAsoc /* 288 */:
                    this.mModulation = "16-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentBroadcast /* 275 */:
                case FileType.PPT /* 324 */:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 306:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case FileType.CELL /* 340 */:
                case 360:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 367:
                case FileType.CRT /* 432 */:
                    this.mModulation = "16-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case VolteConstants.ErrorCode.REQUEST_TIMEOUT /* 408 */:
                    this.mModulation = "16-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 453:
                case VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE /* 480 */:
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 459:
                case 540:
                    this.mModulation = "1024-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 490:
                case 544:
                case 576:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case FileType.EML /* 510 */:
                    this.mModulation = "1024-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 551:
                case 648:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 567:
                case 600:
                    this.mModulation = "1024-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 612:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 680:
                case 720:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 735:
                case 864:
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 816:
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 907:
                case 960:
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 918:
                case 1080:
                    this.mModulation = "1024-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 1020:
                    this.mModulation = "1024-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 1134:
                case 1200:
                    this.mModulation = "1024-QAM";
                    this.mbgn = "802.11ax";
                    break;
                default:
                    this.mModulation = PeripheralConstants.Result.NOT_AVAILABLE;
                    this.mbgn = PeripheralConstants.Result.NOT_AVAILABLE;
                    break;
            }
        }
        if (i2 == 6 && this.channelWidth == 3) {
            switch (i) {
                case 61:
                case 68:
                case 72:
                case 122:
                case 136:
                case 144:
                    this.mModulation = "BPSK";
                    this.mbgn = "802.11ax";
                    break;
                case 183:
                case 204:
                case 216:
                case 367:
                case VolteConstants.ErrorCode.REQUEST_TIMEOUT /* 408 */:
                case FileType.CRT /* 432 */:
                    this.mModulation = "QPSK";
                    this.mbgn = "802.11ax";
                    break;
                case IKnoxCustomManager.Stub.TRANSACTION_setHomeScreenMode /* 245 */:
                case IKnoxCustomManager.Stub
                        .TRANSACTION_setHardKeyIntentBroadcastExternal /* 273 */:
                case IKnoxCustomManager.Stub.TRANSACTION_getAsoc /* 288 */:
                    this.mModulation = "QPSK";
                    this.mbgn = "802.11ax";
                    break;
                case 272:
                case 490:
                case 544:
                case 576:
                    this.mModulation = "16-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 551:
                case 612:
                case 648:
                case VolteConstants.ErrorCode.CALL_SESSION_TERMINATED /* 1102 */:
                case 1225:
                case 1297:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 680:
                case 720:
                case 1361:
                case 1441:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 735:
                case 816:
                case 864:
                    this.mModulation = "16-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 907:
                case 960:
                case 1814:
                case 1921:
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 918:
                case 1020:
                case 1080:
                case 1837:
                case 2041:
                case 2161:
                    this.mModulation = "1024-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 980:
                case 1088:
                case 1152:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 1134:
                case VolteConstants.ErrorCode.QOS_FAILURE /* 1201 */:
                case 2268:
                case VolteConstants.ErrorCode.REG_SSL_CERTIFICATE_FAILURE /* 2401 */:
                    this.mModulation = "1024-QAM";
                    this.mbgn = "802.11ax";
                    break;
                case 1470:
                case 1633:
                case 1729:
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ax";
                    break;
            }
        }
        if (i2 == 5 && this.channelWidth == 3) {
            switch (i) {
                case 58:
                case 65:
                case 117:
                case 130:
                    this.mModulation = "BPSK";
                    this.mbgn = "802.11ac";
                    break;
                case 175:
                case 195:
                case 351:
                case 390:
                    this.mModulation = "QPSK";
                    this.mbgn = "802.11ac";
                    break;
                case IKnoxCustomManager.Stub.TRANSACTION_deleteHomeScreenPage /* 234 */:
                case 260:
                    this.mModulation = "QPSK";
                    this.mbgn = "802.11ac";
                    break;
                case 468:
                case FileType.LOC /* 520 */:
                    this.mModulation = "16-QAM";
                    this.mbgn = "802.11ac";
                    break;
                case FileType.PASSBOOK /* 526 */:
                case 585:
                case 1053:
                case 1170:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ac";
                    break;
                case 650:
                case 1300:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ac";
                    break;
                case 702:
                case 780:
                    this.mModulation = "16-QAM";
                    this.mbgn = "802.11ac";
                    break;
                case 866:
                case 1733:
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ac";
                    break;
                case 936:
                case 1040:
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ac";
                    break;
                case 1404:
                case 1560:
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ac";
                    break;
            }
        }
        if (i == 1) {
            this.mModulation = "DBPSK";
            this.mbgn = "802.11b";
        }
        if (i == 2) {
            this.mModulation = "DQPSK";
            this.mbgn = "802.11b";
            return;
        }
        if (i == 5) {
            this.mModulation = "CCK";
            this.mbgn = "802.11b";
            return;
        }
        if (i == 6) {
            if (i2 == 1) {
                this.mModulation = "BPSK";
                this.mbgn = "802.11g";
                return;
            } else {
                if (i2 == 4) {
                    this.mModulation = "BPSK";
                    this.mbgn = "802.11n";
                    return;
                }
                return;
            }
        }
        if (i == 7) {
            this.mModulation = "BPSK";
            this.mbgn = "802.11n";
            return;
        }
        if (i == 18) {
            this.mModulation = "QPSK";
            this.mbgn = "802.11g";
            return;
        }
        if (i == 19) {
            this.mModulation = "QPSK";
            this.mbgn = "802.11n";
            return;
        }
        if (i == 39) {
            if (i2 == 4 && this.channelWidth == 0) {
                this.mModulation = "QPSK";
                this.mbgn = "802.11n";
                return;
            } else {
                if (i2 == 4 && this.channelWidth == 0) {
                    this.mModulation = "16-QAM";
                    this.mbgn = "802.11n";
                    return;
                }
                return;
            }
        }
        if (i == 40) {
            this.mModulation = "QPSK";
            this.mbgn = "802.11n";
            return;
        }
        if (i == 57) {
            if (i2 == 4 && this.channelWidth == 0) {
                this.mModulation = "16-QAM";
                this.mbgn = "802.11n";
                return;
            } else {
                if (i2 == 4 && this.channelWidth == 0) {
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11n";
                    return;
                }
                return;
            }
        }
        if (i == 58) {
            if (i2 == 4 && this.channelWidth == 0) {
                this.mModulation = "64-QAM";
                this.mbgn = "802.11n";
                return;
            }
            if (i2 == 5) {
                i3 = 2;
                if (this.channelWidth == 2) {
                    this.mModulation = "BPSK";
                    this.mbgn = "802.11ac";
                    return;
                }
            } else {
                i3 = 2;
            }
            if (i2 == 5 && this.channelWidth == i3) {
                this.mModulation = "QPSK";
                this.mbgn = "802.11ac";
                return;
            }
            return;
        }
        if (i == 86) {
            if (i2 == 4 && this.channelWidth == 0) {
                this.mModulation = "16-QAM";
                this.mbgn = "802.11n";
                return;
            } else {
                if (i2 == 4 && this.channelWidth == 0) {
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ac";
                    return;
                }
                return;
            }
        }
        if (i == 87) {
            this.mModulation = "QPSK";
            this.mbgn = "802.11ac";
            return;
        }
        if (i == 120) {
            if (i2 == 4) {
                i4 = 1;
                if (this.channelWidth == 1) {
                    this.mModulation = "16-QAM";
                    this.mbgn = "802.11n";
                    return;
                }
            } else {
                i4 = 1;
            }
            if (i2 == 4 && this.channelWidth == i4) {
                this.mModulation = "64-QAM";
                this.mbgn = "802.11n";
                return;
            }
            return;
        }
        if (i == 121) {
            this.mModulation = "64-QAM";
            this.mbgn = "802.11n";
            return;
        }
        if (i == 324) {
            this.mModulation = "256-QAM";
            this.mbgn = "802.11ac";
            return;
        }
        if (i == 325) {
            this.mModulation = "64-QAM";
            this.mbgn = "802.11ac";
            return;
        }
        switch (i) {
            case 9:
                this.mModulation = "BPSK";
                this.mbgn = "802.11g";
                break;
            case 11:
                this.mModulation = "CCK";
                this.mbgn = "802.11b";
                break;
            case 12:
                this.mModulation = "QPSK";
                this.mbgn = "802.11g";
                break;
            case 13:
                if (i2 != 4 || this.channelWidth != 0) {
                    if (i2 != 4 || this.channelWidth != 1) {
                        if (i2 != 5 || this.channelWidth != 0) {
                            if (i2 != 5 || this.channelWidth != 0) {
                                if (i2 == 5 && this.channelWidth == 1) {
                                    this.mModulation = "BPSK";
                                    this.mbgn = "802.11ac";
                                    break;
                                }
                            } else {
                                this.mModulation = "QPSK";
                                this.mbgn = "802.11ac";
                                break;
                            }
                        } else {
                            this.mModulation = "BPSK";
                            this.mbgn = "802.11ac";
                            break;
                        }
                    } else {
                        this.mModulation = "BPSK";
                        this.mbgn = "802.11n";
                        break;
                    }
                } else {
                    this.mModulation = "QPSK";
                    this.mbgn = "802.11n";
                    break;
                }
                break;
            case 14:
                if (i2 != 4 || this.channelWidth != 0) {
                    if (i2 == 4 && this.channelWidth == 0) {
                        this.mModulation = "QPSK";
                        this.mbgn = "802.11n";
                        break;
                    }
                } else {
                    this.mModulation = "BPSK";
                    this.mbgn = "802.11n";
                    break;
                }
                break;
            case 15:
                this.mModulation = "BPSK";
                this.mbgn = "802.11n";
                break;
            case 21:
                this.mModulation = "QPSK";
                this.mbgn = "802.11n";
                break;
            case 24:
                this.mModulation = "16-QAM";
                this.mbgn = "802.11g";
                break;
            case 32:
                this.mModulation = "BPSK";
                this.mbgn = "802.11ac";
                break;
            case 36:
                this.mModulation = "16-QAM";
                this.mbgn = "802.11g";
                break;
            case 43:
                if (i2 != 4 || this.channelWidth != 0) {
                    if (i2 == 4 && this.channelWidth == 0) {
                        this.mModulation = "16-QAM";
                        this.mbgn = "802.11n";
                        break;
                    }
                } else {
                    this.mModulation = "QPSK";
                    this.mbgn = "802.11n";
                    break;
                }
                break;
            case 45:
                this.mModulation = "QPSK";
                this.mbgn = "802.11n";
                break;
            case 48:
                this.mModulation = "64-QAM";
                this.mbgn = "802.11g";
                break;
            case 52:
                if (i2 != 4 || this.channelWidth != 0) {
                    if (i2 == 4 && this.channelWidth == 0) {
                        this.mModulation = "64-QAM";
                        this.mbgn = "802.11n";
                        break;
                    }
                } else {
                    this.mModulation = "16-QAM";
                    this.mbgn = "802.11n";
                    break;
                }
                break;
            case 54:
                if (i2 != 1) {
                    if (i2 != 4 || this.channelWidth != 1) {
                        if (i2 != 4 || this.channelWidth != 1) {
                            this.mModulation = "64-QAM";
                            this.mbgn = "802.11g";
                            break;
                        } else {
                            this.mModulation = "16-QAM";
                            this.mbgn = "802.11n";
                            break;
                        }
                    } else {
                        this.mModulation = "QPSK";
                        this.mbgn = "802.11n";
                        break;
                    }
                } else {
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11g";
                    break;
                }
            case 60:
                if (i2 == 4) {
                    i5 = 1;
                    if (this.channelWidth == 1) {
                        this.mModulation = "QPSK";
                        this.mbgn = "802.11n";
                        break;
                    }
                } else {
                    i5 = 1;
                }
                if (i2 == 4 && this.channelWidth == i5) {
                    this.mModulation = "16-QAM";
                    this.mbgn = "802.11n";
                    break;
                }
                break;
            case 65:
                if (i2 != 4 || this.channelWidth != 0) {
                    if (i2 != 4 || this.channelWidth != 0) {
                        if (i2 == 5) {
                            i6 = 2;
                            if (this.channelWidth == 2) {
                                this.mModulation = "BPSK";
                                this.mbgn = "802.11ac";
                                break;
                            }
                        } else {
                            i6 = 2;
                        }
                        if (i2 == 5 && this.channelWidth == i6) {
                            this.mModulation = "QPSK";
                            this.mbgn = "802.11ac";
                            break;
                        }
                    } else {
                        this.mModulation = "64-QAM";
                        this.mbgn = "802.11n";
                        break;
                    }
                } else {
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11n";
                    break;
                }
                break;
            case 72:
                this.mModulation = "64-QAM";
                this.mbgn = "802.11n";
                break;
            case 78:
                if (i2 != 4 || this.channelWidth != 0) {
                    if (i2 == 4 && this.channelWidth == 0) {
                        this.mModulation = "256-QAM";
                        this.mbgn = "802.11ac";
                        break;
                    }
                } else {
                    this.mModulation = "16-QAM";
                    this.mbgn = "802.11n";
                    break;
                }
                break;
            case 81:
                if (i2 != 4 || this.channelWidth != 1) {
                    if (i2 == 4 && this.channelWidth == 1) {
                        this.mModulation = "16-QAM";
                        this.mbgn = "802.11n";
                        break;
                    }
                } else {
                    this.mModulation = "QPSK";
                    this.mbgn = "802.11n";
                    break;
                }
                break;
            case 90:
                if (i2 == 4) {
                    i7 = 1;
                    if (this.channelWidth == 1) {
                        this.mModulation = "QPSK";
                        this.mbgn = "802.11n";
                        break;
                    }
                } else {
                    i7 = 1;
                }
                if (i2 == 4 && this.channelWidth == i7) {
                    this.mModulation = "16-QAM";
                    this.mbgn = "802.11n";
                    break;
                }
                break;
            case 97:
                this.mModulation = "QPSK";
                this.mbgn = "802.11ac";
                break;
            case 104:
                this.mModulation = "64-QAM";
                this.mbgn = "802.11n";
                break;
            case 108:
                if (i2 == 4) {
                    i8 = 1;
                    if (this.channelWidth == 1) {
                        this.mModulation = "16-QAM";
                        this.mbgn = "802.11n";
                        break;
                    }
                } else {
                    i8 = 1;
                }
                if (i2 == 4 && this.channelWidth == i8) {
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11n";
                    break;
                }
                break;
            case 115:
                this.mModulation = "64-QAM";
                this.mbgn = "802.11n";
                break;
            case 117:
                if (i2 != 4 || this.channelWidth != 0) {
                    if (i2 == 5) {
                        i9 = 2;
                        if (this.channelWidth == 2) {
                            this.mModulation = "QPSK";
                            this.mbgn = "802.11ac";
                            break;
                        }
                    } else {
                        i9 = 2;
                    }
                    if (i2 == 5 && this.channelWidth == i9) {
                        this.mModulation = "16-QAM";
                        this.mbgn = "802.11ac";
                        break;
                    }
                } else {
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11n";
                    break;
                }
                break;
            case 130:
                if (i2 != 4 || this.channelWidth != 0) {
                    if (i2 == 5) {
                        i10 = 2;
                        if (this.channelWidth == 2) {
                            this.mModulation = "QPSK";
                            this.mbgn = "802.11ac";
                            break;
                        }
                    } else {
                        i10 = 2;
                    }
                    if (i2 == 5 && this.channelWidth == i10) {
                        this.mModulation = "16-QAM";
                        this.mbgn = "802.11ac";
                        break;
                    }
                } else {
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11n";
                    break;
                }
                break;
            case 135:
                if (i2 == 4 && this.channelWidth == 1) {
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11n";
                    break;
                }
                break;
            case 144:
                this.mModulation = "64-QAM";
                this.mbgn = "802.11n";
                break;
            case 150:
                this.mModulation = "64-QAM";
                this.mbgn = "802.11n";
                break;
            case 156:
                this.mModulation = "256-QAM";
                this.mbgn = "802.11ac";
                break;
            case 162:
                if (i2 == 4) {
                    i11 = 1;
                    if (this.channelWidth == 1) {
                        this.mModulation = "16-QAM";
                        this.mbgn = "802.11n";
                        break;
                    }
                } else {
                    i11 = 1;
                }
                if (i2 == 5 && this.channelWidth == i11) {
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ac";
                    break;
                }
                break;
            case 173:
                this.mModulation = "256-QAM";
                this.mbgn = "802.11ac";
                break;
            case 175:
                if (i2 == 5) {
                    i12 = 2;
                    if (this.channelWidth == 2) {
                        this.mModulation = "QPSK";
                        this.mbgn = "802.11ac";
                        break;
                    }
                } else {
                    i12 = 2;
                }
                if (i2 == 5 && this.channelWidth == i12) {
                    this.mModulation = "16-QAM";
                    this.mbgn = "802.11ac";
                    break;
                }
                break;
            case 180:
                if (i2 == 4) {
                    i13 = 1;
                    if (this.channelWidth == 1) {
                        this.mModulation = "16-QAM";
                        this.mbgn = "802.11n";
                        break;
                    }
                } else {
                    i13 = 1;
                }
                if (i2 == 5 && this.channelWidth == i13) {
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ac";
                    break;
                }
                break;
            case 195:
                if (i2 == 5) {
                    i14 = 2;
                    if (this.channelWidth == 2) {
                        this.mModulation = "QPSK";
                        this.mbgn = "802.11ac";
                        break;
                    }
                } else {
                    i14 = 2;
                }
                if (i2 == 5 && this.channelWidth == i14) {
                    this.mModulation = "16-QAM";
                    this.mbgn = "802.11ac";
                    break;
                }
                break;
            case 200:
                this.mModulation = "256-QAM";
                this.mbgn = "802.11ac";
                break;
            case 216:
                this.mModulation = "64-QAM";
                this.mbgn = "802.11n";
                break;
            case IKnoxCustomManager.Stub.TRANSACTION_deleteHomeScreenPage /* 234 */:
                if (i2 == 5) {
                    i15 = 2;
                    if (this.channelWidth == 2) {
                        this.mModulation = "16-QAM";
                        this.mbgn = "802.11ac";
                        break;
                    }
                } else {
                    i15 = 2;
                }
                if (i2 == 5 && this.channelWidth == i15) {
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ac";
                    break;
                }
                break;
            case IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp /* 240 */:
                this.mModulation = "64-QAM";
                this.mbgn = "802.11n";
                break;
            case IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentMode /* 243 */:
                this.mModulation = "64-QAM";
                this.mbgn = "802.11n";
                break;
            case 260:
                if (i2 == 5) {
                    i16 = 2;
                    if (this.channelWidth == 2) {
                        this.mModulation = "16-QAM";
                        this.mbgn = "802.11ac";
                        break;
                    }
                } else {
                    i16 = 2;
                }
                if (i2 == 5 && this.channelWidth == i16) {
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ac";
                    break;
                }
                break;
            case 263:
                this.mModulation = "64-QAM";
                this.mbgn = "802.11ac";
                break;
            case 270:
                if (i2 == 4 && this.channelWidth == 1) {
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11n";
                    break;
                }
                break;
            case IKnoxCustomManager.Stub.TRANSACTION_startTcpDump /* 292 */:
                if (i2 == 5 && this.channelWidth == 2) {
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ac";
                    break;
                }
                break;
            case 300:
                this.mModulation = "64-QAM";
                this.mbgn = "802.11n";
                break;
            case 351:
                if (i2 == 5) {
                    i17 = 2;
                    if (this.channelWidth == 2) {
                        this.mModulation = "16-QAM";
                        this.mbgn = "802.11ac";
                        break;
                    }
                } else {
                    i17 = 2;
                }
                if (i2 == 5 && this.channelWidth == i17) {
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ac";
                    break;
                }
                break;
            case 360:
                if (i2 == 5 && this.channelWidth == 1) {
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ac";
                    break;
                }
                break;
            case 390:
                if (i2 == 5) {
                    i18 = 2;
                    if (this.channelWidth == 2) {
                        this.mbgn = "802.11ac";
                        break;
                    }
                } else {
                    i18 = 2;
                }
                if (i2 == 5 && this.channelWidth == i18) {
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ac";
                    break;
                }
                break;
            case 400:
                this.mModulation = "256-QAM";
                this.mbgn = "802.11ac";
                break;
            case FileType.DER /* 433 */:
                this.mModulation = "256-QAM";
                this.mbgn = "802.11ac";
                break;
            case 468:
                this.mModulation = "64-QAM";
                this.mbgn = "802.11ac";
                break;
            case FileType.LOC /* 520 */:
                this.mModulation = "64-QAM";
                this.mbgn = "802.11ac";
                break;
            case FileType.PASSBOOK /* 526 */:
                this.mModulation = "64-QAM";
                this.mbgn = "802.11ac";
                break;
            case 585:
                if (i2 == 5 && this.channelWidth == 2) {
                    this.mModulation = "64-QAM";
                    this.mbgn = "802.11ac";
                    break;
                }
                break;
            case 650:
                this.mModulation = "64-QAM";
                this.mbgn = "802.11ac";
                break;
            case 702:
                this.mModulation = "256-QAM";
                this.mbgn = "802.11ac";
                break;
            case 780:
                if (i2 == 5 && this.channelWidth == 2) {
                    this.mModulation = "256-QAM";
                    this.mbgn = "802.11ac";
                    break;
                }
                break;
            case 866:
                this.mModulation = "256-QAM";
                this.mbgn = "802.11ac";
                break;
            default:
                switch (i) {
                    case 26:
                        if (i2 != 4 || this.channelWidth != 0) {
                            if (i2 == 4 && this.channelWidth == 0) {
                                this.mModulation = "16-QAM";
                                this.mbgn = "802.11n";
                                break;
                            }
                        } else {
                            this.mModulation = "QPSK";
                            this.mbgn = "802.11n";
                            break;
                        }
                        break;
                    case 27:
                        if (i2 == 4) {
                            i19 = 1;
                            if (this.channelWidth == 1) {
                                this.mModulation = "BPSK";
                                this.mbgn = "802.11n";
                                break;
                            }
                        } else {
                            i19 = 1;
                        }
                        if (i2 == 4 && this.channelWidth == i19) {
                            this.mModulation = "QPSK";
                            this.mbgn = "802.11n";
                            break;
                        }
                        break;
                    case 28:
                        if (i2 != 4 || this.channelWidth != 0) {
                            if (i2 == 4 && this.channelWidth == 0) {
                                this.mModulation = "16-QAM";
                                this.mbgn = "802.11n";
                                break;
                            }
                        } else {
                            this.mModulation = "QPSK";
                            this.mbgn = "802.11n";
                            break;
                        }
                        break;
                    case 29:
                        this.mModulation = "BPSK";
                        this.mbgn = "802.11ac";
                        break;
                    case 30:
                        if (i2 == 4) {
                            i20 = 1;
                            if (this.channelWidth == 1) {
                                this.mModulation = "BPSK";
                                this.mbgn = "802.11n";
                                break;
                            }
                        } else {
                            i20 = 1;
                        }
                        if (i2 == 4 && this.channelWidth == i20) {
                            this.mModulation = "QPSK";
                            this.mbgn = "802.11n";
                            break;
                        }
                        break;
                    default:
                        this.mModulation = PeripheralConstants.Result.NOT_AVAILABLE;
                        this.mbgn = PeripheralConstants.Result.NOT_AVAILABLE;
                        break;
                }
        }
    }
}
