package com.samsung.android.settings.wifi.develop.nearbywifi.model;

import android.net.wifi.ScanResult;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.util.SemLog;
import com.samsung.android.wifitrackerlib.SemWifiUtils;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Repository {
    public final HashMap ssidMap = new HashMap();
    public final ArrayList ssidList = new ArrayList();
    public final HashMap freqCuMap = new HashMap();
    public final HashSet supportedFreqs = new HashSet();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CuInfo {
        public int channel;
        public int cu;
        public int frequency;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class LazyHolder {
        public static final Repository INSTANCE = new Repository();
    }

    public static List createRadarUnitByBssid(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            BssidInfo bssidInfo = (BssidInfo) it.next();
            arrayList.add(new RadarUnit(bssidInfo.rssi, getStaticAngleFrom(bssidInfo.bssid)));
        }
        return arrayList;
    }

    public static String getMacAddressString(int i, ByteBuffer byteBuffer) {
        return String.format(
                Locale.US,
                "%02x:%02x:%02x:%02x:%02x:%02x",
                Byte.valueOf(byteBuffer.get(i)),
                Byte.valueOf(byteBuffer.get(i + 1)),
                Byte.valueOf(byteBuffer.get(i + 2)),
                Byte.valueOf(byteBuffer.get(i + 3)),
                Byte.valueOf(byteBuffer.get(i + 4)),
                Byte.valueOf(byteBuffer.get(i + 5)));
    }

    public static int getStaticAngleFrom(String str) {
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            i = ((i << 5) | str.charAt(i2)) % 65521;
        }
        int i3 = i % 165;
        return i3 >= 82 ? i3 + 16 : i3;
    }

    public static int toBand(int i) {
        if (i >= 2412 && i <= 2484) {
            return 1;
        }
        if (i < 5160 || i > 5885) {
            return (i < 5955 || i > 7115) ? -1 : 8;
        }
        return 2;
    }

    public final List createRadarUnitBySsid() {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.ssidMap.values().iterator();
        while (it.hasNext()) {
            Iterator it2 = ((SsidInfo) it.next()).bssids.iterator();
            BssidInfo bssidInfo = null;
            while (it2.hasNext()) {
                BssidInfo bssidInfo2 = (BssidInfo) it2.next();
                if (bssidInfo == null || bssidInfo2.rssi > bssidInfo.rssi) {
                    bssidInfo = bssidInfo2;
                }
            }
            if (bssidInfo != null) {
                arrayList.add(new RadarUnit(bssidInfo.rssi, getStaticAngleFrom(bssidInfo.bssid)));
            }
        }
        return arrayList;
    }

    public final Map getCuInfo() {
        HashMap hashMap = new HashMap();
        hashMap.put(1, new ArrayList());
        hashMap.put(2, new ArrayList());
        hashMap.put(8, new ArrayList());
        ArrayList arrayList = new ArrayList(this.freqCuMap.keySet());
        Collections.sort(arrayList);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            int band = toBand(num.intValue());
            Integer num2 = (Integer) this.freqCuMap.get(num);
            if (band == 1 || band == 2 || band == 8) {
                List list = (List) hashMap.get(Integer.valueOf(band));
                if (list != null) {
                    int intValue = num.intValue();
                    int convertFrequencyMhzToChannelIfSupported =
                            ScanResult.convertFrequencyMhzToChannelIfSupported(num.intValue());
                    int intValue2 = num2 == null ? 0 : num2.intValue();
                    CuInfo cuInfo = new CuInfo();
                    cuInfo.frequency = intValue;
                    cuInfo.channel = convertFrequencyMhzToChannelIfSupported;
                    cuInfo.cu = intValue2;
                    list.add(cuInfo);
                }
            }
        }
        return hashMap;
    }

    public final boolean isRecommendable(int i) {
        Integer num = (Integer) this.freqCuMap.get(Integer.valueOf(i));
        boolean z = false;
        if (num != null) {
            return num.intValue() <= 50;
        }
        Iterator it = this.ssidMap.values().iterator();
        loop0:
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Iterator it2 = ((SsidInfo) it.next()).bssids.iterator();
            while (it2.hasNext()) {
                BssidInfo bssidInfo = (BssidInfo) it2.next();
                if (bssidInfo.freq == i && bssidInfo.cu == -1) {
                    z = true;
                    break loop0;
                }
            }
        }
        return !z;
    }

    public final boolean isRecommendableBasedOnAdjacentFreqs(int[] iArr) {
        for (int i : iArr) {
            if (!isRecommendable(i)) {
                return false;
            }
        }
        return true;
    }

    public final void update(List list) {
        this.ssidMap.clear();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ScanResult scanResult = (ScanResult) it.next();
            String removeDoubleQuotes = SemWifiUtils.removeDoubleQuotes(scanResult.SSID);
            if (removeDoubleQuotes.length() != 0 && scanResult.level < 0) {
                SsidInfo ssidInfo = (SsidInfo) this.ssidMap.get(removeDoubleQuotes);
                if (ssidInfo == null) {
                    ssidInfo = new SsidInfo(removeDoubleQuotes);
                    this.ssidMap.put(removeDoubleQuotes, ssidInfo);
                }
                BssidInfo bssidInfo = new BssidInfo();
                bssidInfo.bssid = scanResult.BSSID;
                bssidInfo.freq = scanResult.frequency;
                bssidInfo.rssi = scanResult.level;
                bssidInfo.cu = -1;
                bssidInfo.sta = -1;
                Iterator<ScanResult.InformationElement> it2 =
                        scanResult.getInformationElements().iterator();
                while (true) {
                    boolean z = false;
                    if (!it2.hasNext()) {
                        break;
                    }
                    ScanResult.InformationElement next = it2.next();
                    ByteBuffer order = next.getBytes().order(ByteOrder.LITTLE_ENDIAN);
                    int id = next.getId();
                    if (id != 11) {
                        if (id != 48) {
                            if (id == 70) {
                                bssidInfo.extra11k = BitSet.valueOf(next.getBytes()).get(1);
                            } else if (id == 127) {
                                bssidInfo.extra11v = BitSet.valueOf(next.getBytes()).get(19);
                            } else if (id == 201) {
                                bssidInfo.rnr = new Rnr(order);
                            } else if (id != 221) {
                                if (id == 255 && next.getIdExt() == 107) {
                                    bssidInfo.multiLink =
                                            new MultiLink(order, bssidInfo.bssid, bssidInfo.freq);
                                }
                            } else if (!bssidInfo.extra11e && order.capacity() >= 4) {
                                if ((((order.get(0) & 255) << 16)
                                                        | ((order.get(1) & 255) << 8)
                                                        | (order.get(2) & 255))
                                                == 20722
                                        && order.get(3) == 2) {
                                    z = true;
                                }
                                bssidInfo.extra11e = z;
                            }
                        } else if (order.capacity() >= 2 && order.getShort() == 1) {
                            try {
                                int i = order.getShort(6) * 4;
                                short s = order.getShort((order.getShort(i + 8) * 4) + i + 10);
                                if ((s & 64) != 0) {
                                    bssidInfo.pmfRequired = true;
                                }
                                if ((s & 128) != 0) {
                                    bssidInfo.pmfCapable = true;
                                }
                            } catch (BufferUnderflowException unused) {
                                SemLog.e(
                                        "NearbyWifi.BssidInfo",
                                        "Couldn't parse RSNE, buffer underflow");
                            }
                        }
                    } else if (order.capacity() == 5) {
                        bssidInfo.sta = order.getShort() & 65535;
                        bssidInfo.cu = ((order.get(2) & 255) * 100) / 256;
                    }
                }
                bssidInfo.wifiStandard = scanResult.getWifiStandard();
                int i2 = scanResult.channelWidth;
                bssidInfo.channelWidth =
                        i2 != 0
                                ? i2 != 1
                                        ? i2 != 2
                                                ? (i2 == 3 || i2 == 4)
                                                        ? 160
                                                        : i2 != 5 ? 0 : FileType.XLSX
                                                : 80
                                        : 40
                                : 20;
                String str = scanResult.capabilities;
                StringBuilder sb = new StringBuilder();
                for (String str2 : str.split("\\[")) {
                    if (str2.length() > 3
                            && ("WPA".equals(str2.substring(0, 3))
                                    || "RSN".equals(str2.substring(0, 3))
                                    || "WEP".equals(str2.substring(0, 3)))) {
                        if (sb.length() > 0) {
                            sb.append("\n");
                        }
                        sb.append("[");
                        sb.append(str2);
                        if (str2.contains("FT")) {
                            bssidInfo.extra11r = true;
                        }
                    }
                }
                if (sb.length() == 0) {
                    sb.append("[OPEN]");
                }
                bssidInfo.security = sb.toString();
                ssidInfo.bssids.add(bssidInfo);
            }
        }
        this.ssidList.clear();
        final int i3 = 0;
        this.ssidList.addAll(
                this.ssidMap.values().stream()
                        .sorted(
                                Comparator.comparing(
                                        new Function() { // from class:
                                                         // com.samsung.android.settings.wifi.develop.nearbywifi.model.Repository$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                switch (i3) {
                                                    case 0:
                                                        return ((SsidInfo) obj).ssid.toLowerCase();
                                                    case 1:
                                                        return Integer.valueOf(
                                                                -((BssidInfo) obj).rssi);
                                                    default:
                                                        return new ArrayList();
                                                }
                                            }
                                        }))
                        .toList());
        Iterator it3 = this.ssidList.iterator();
        while (it3.hasNext()) {
            SsidInfo ssidInfo2 = (SsidInfo) it3.next();
            Iterator it4 = ssidInfo2.bssids.iterator();
            while (it4.hasNext()) {
                int band = toBand(((BssidInfo) it4.next()).freq);
                if (band == 1) {
                    ssidInfo2.existBand24 = true;
                } else if (band == 2) {
                    ssidInfo2.existBand5 = true;
                } else if (band == 8) {
                    ssidInfo2.existBand6 = true;
                }
            }
            final int i4 = 1;
            ssidInfo2.bssids.sort(
                    Comparator.comparing(
                            new Function() { // from class:
                                             // com.samsung.android.settings.wifi.develop.nearbywifi.model.Repository$$ExternalSyntheticLambda0
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    switch (i4) {
                                        case 0:
                                            return ((SsidInfo) obj).ssid.toLowerCase();
                                        case 1:
                                            return Integer.valueOf(-((BssidInfo) obj).rssi);
                                        default:
                                            return new ArrayList();
                                    }
                                }
                            }));
        }
        HashMap hashMap = new HashMap();
        Iterator it5 = this.ssidMap.values().iterator();
        while (it5.hasNext()) {
            Iterator it6 = ((SsidInfo) it5.next()).bssids.iterator();
            while (it6.hasNext()) {
                BssidInfo bssidInfo2 = (BssidInfo) it6.next();
                if (bssidInfo2.cu != -1) {
                    final int i5 = 2;
                    ((ArrayList)
                                    hashMap.computeIfAbsent(
                                            Integer.valueOf(bssidInfo2.freq),
                                            new Function() { // from class:
                                                             // com.samsung.android.settings.wifi.develop.nearbywifi.model.Repository$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Function
                                                public final Object apply(Object obj) {
                                                    switch (i5) {
                                                        case 0:
                                                            return ((SsidInfo) obj)
                                                                    .ssid.toLowerCase();
                                                        case 1:
                                                            return Integer.valueOf(
                                                                    -((BssidInfo) obj).rssi);
                                                        default:
                                                            return new ArrayList();
                                                    }
                                                }
                                            }))
                            .add(Integer.valueOf(bssidInfo2.cu));
                }
            }
        }
        this.freqCuMap.clear();
        hashMap.forEach(
                new BiConsumer() { // from class:
                                   // com.samsung.android.settings.wifi.develop.nearbywifi.model.Repository$$ExternalSyntheticLambda3
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        Repository repository = Repository.this;
                        Integer num = (Integer) obj;
                        ArrayList arrayList = (ArrayList) obj2;
                        repository.getClass();
                        if (arrayList.size() >= 3) {
                            Collections.sort(arrayList);
                            arrayList.remove(arrayList.size() - 1);
                            arrayList.remove(0);
                        }
                        repository.freqCuMap.put(
                                num,
                                Integer.valueOf(
                                        (int)
                                                arrayList.stream()
                                                        .mapToInt(
                                                                new Repository$$ExternalSyntheticLambda4())
                                                        .average()
                                                        .orElse(0.0d)));
                    }
                });
    }
}
