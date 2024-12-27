package com.samsung.android.settings.wifi.develop.homewifi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;

import com.android.settings.R;
import com.android.settingslib.widget.LottieColorUtils$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.wifi.WifiPolicy;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class AdviserFragment extends Fragment {
    public static final HashSet mDefaultNames =
            new HashSet(
                    Arrays.asList(
                            "iptime",
                            "kt wifi",
                            "linksys",
                            "netgear",
                            "asus",
                            "tp-link",
                            "wireless",
                            "dlink",
                            "2wire",
                            "default"));
    public static final HashMap mSecurityOrder = new HashMap();
    public ArrayList mBssids;
    public View mExcellent;
    public View mHiddenNetwork;
    public View mMultipleName;
    public View mMultipleSecurity;
    public View mNoWifi;
    public ProgressDecorator mProgressDecorator;
    public View mStrongSecurity;
    public View mUniqueName;
    public View mWMM;
    public View mWithProblemImageView;
    public View mWithoutProblemImageView;

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        HashMap hashMap;
        int i7;
        int i8;
        SemLog.d("HomeWifi.AdviserFragment", "onCreateView");
        View inflate =
                layoutInflater.inflate(
                        R.layout.sec_wifi_development_homewifi_adviser_fragment, (ViewGroup) null);
        this.mNoWifi = inflate.findViewById(R.id.no_wifi);
        this.mStrongSecurity = inflate.findViewById(R.id.strong_security);
        this.mHiddenNetwork = inflate.findViewById(R.id.hidden_network);
        this.mUniqueName = inflate.findViewById(R.id.unique_name);
        this.mMultipleName = inflate.findViewById(R.id.multiple_name);
        this.mMultipleSecurity = inflate.findViewById(R.id.multiple_security);
        this.mWMM = inflate.findViewById(R.id.wmm);
        this.mExcellent = inflate.findViewById(R.id.excellent);
        this.mWithProblemImageView = inflate.findViewById(R.id.image_view_with_problem);
        this.mWithoutProblemImageView = inflate.findViewById(R.id.image_view_without_problem);
        this.mNoWifi.setVisibility(8);
        this.mStrongSecurity.setVisibility(8);
        this.mHiddenNetwork.setVisibility(8);
        this.mUniqueName.setVisibility(8);
        this.mMultipleName.setVisibility(8);
        this.mMultipleSecurity.setVisibility(8);
        this.mWMM.setVisibility(8);
        this.mExcellent.setVisibility(8);
        HashMap hashMap2 = mSecurityOrder;
        boolean z = false;
        int i9 = 1;
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                0, hashMap2, "EAP_SUITE_B_192", 1, "EAP-FILS-SHA384");
        LottieColorUtils$$ExternalSyntheticOutline0.m(2, hashMap2, "EAP-FILS-SHA256", 3, "FT/EAP");
        LottieColorUtils$$ExternalSyntheticOutline0.m(4, hashMap2, "DPP", 5, "FT/SAE_EXT_KEY");
        LottieColorUtils$$ExternalSyntheticOutline0.m(6, hashMap2, "SAE_EXT_KEY", 7, "FT/SAE");
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                8, hashMap2, WifiPolicy.SECURITY_TYPE_SAE, 9, "FT/PSK");
        LottieColorUtils$$ExternalSyntheticOutline0.m(10, hashMap2, "EAP/SHA256", 11, "PSK-SHA256");
        LottieColorUtils$$ExternalSyntheticOutline0.m(12, hashMap2, "EAP/SHA1", 13, "PSK");
        LottieColorUtils$$ExternalSyntheticOutline0.m(14, hashMap2, "WEP", 15, "OSEN");
        LottieColorUtils$$ExternalSyntheticOutline0.m(16, hashMap2, "OWE_TRANSITION", 17, "OWE");
        hashMap2.put("OPEN", 18);
        ArrayList arrayList = this.mBssids;
        if (arrayList == null || arrayList.size() == 0) {
            this.mNoWifi.setVisibility(0);
            ProgressDecorator progressDecorator = this.mProgressDecorator;
            if (progressDecorator != null) {
                ((MainActivity) progressDecorator).setProgressAnimation(1, 5);
                ((MainActivity) this.mProgressDecorator).mTitleView.setText("No Wi-Fi selected");
            }
        } else {
            this.mBssids.forEach(new AdviserFragment$$ExternalSyntheticLambda2());
            final ArrayList arrayList2 = new ArrayList();
            final int i10 = 0;
            this.mBssids.forEach(
                    new Consumer() { // from class:
                                     // com.samsung.android.settings.wifi.develop.homewifi.AdviserFragment$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i11 = i10;
                            ArrayList arrayList3 = arrayList2;
                            ExtendedBssidInfo extendedBssidInfo = (ExtendedBssidInfo) obj;
                            switch (i11) {
                                case 0:
                                    HashSet hashSet = AdviserFragment.mDefaultNames;
                                    boolean contains =
                                            extendedBssidInfo
                                                    .security
                                                    .toUpperCase()
                                                    .contains("[OPEN]");
                                    String str = extendedBssidInfo.ssid;
                                    if (!contains) {
                                        String str2 = extendedBssidInfo.security;
                                        if (!str2.toUpperCase().contains("[WEP]")) {
                                            if (str2.toUpperCase().contains("TKIP")) {
                                                String m =
                                                        AbstractResolvableFuture$$ExternalSyntheticOutline0
                                                                .m(str, " [TKIP]");
                                                if (!arrayList3.contains(m)) {
                                                    arrayList3.add(m);
                                                    break;
                                                }
                                            }
                                        } else {
                                            String m2 =
                                                    AbstractResolvableFuture$$ExternalSyntheticOutline0
                                                            .m(str, " [WEP]");
                                            if (!arrayList3.contains(m2)) {
                                                arrayList3.add(m2);
                                                break;
                                            }
                                        }
                                    } else {
                                        String m3 =
                                                AbstractResolvableFuture$$ExternalSyntheticOutline0
                                                        .m(str, " [OPEN]");
                                        if (!arrayList3.contains(m3)) {
                                            arrayList3.add(m3);
                                            break;
                                        }
                                    }
                                    break;
                                default:
                                    if (AdviserFragment.mDefaultNames.contains(
                                            extendedBssidInfo.ssid.toLowerCase())) {
                                        String str3 = extendedBssidInfo.ssid;
                                        if (!arrayList3.contains(str3)) {
                                            arrayList3.add(str3);
                                            SemLog.d("HomeWifi.AdviserFragment", str3);
                                            break;
                                        }
                                    }
                                    break;
                            }
                        }
                    });
            if (arrayList2.size() > 0) {
                StringBuilder sb = new StringBuilder();
                arrayList2.forEach(new AdviserFragment$$ExternalSyntheticLambda1(3, sb));
                sb.deleteCharAt(sb.length() - 1);
                if (arrayList2.size() > 1) {
                    ((TextView) this.mStrongSecurity.findViewById(R.id.strong_security_desc_top))
                            .setText("Following Wi-Fi routers have a weak security.");
                }
                ((TextView) this.mStrongSecurity.findViewById(R.id.strong_security_ssid))
                        .setText(sb);
                this.mStrongSecurity.setVisibility(0);
                i = 1;
            } else {
                i = 0;
            }
            boolean z2 = i > 0;
            final ArrayList arrayList3 = new ArrayList(1);
            final int i11 = 1;
            this.mBssids.forEach(
                    new Consumer() { // from class:
                                     // com.samsung.android.settings.wifi.develop.homewifi.AdviserFragment$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i112 = i11;
                            ArrayList arrayList32 = arrayList3;
                            ExtendedBssidInfo extendedBssidInfo = (ExtendedBssidInfo) obj;
                            switch (i112) {
                                case 0:
                                    HashSet hashSet = AdviserFragment.mDefaultNames;
                                    boolean contains =
                                            extendedBssidInfo
                                                    .security
                                                    .toUpperCase()
                                                    .contains("[OPEN]");
                                    String str = extendedBssidInfo.ssid;
                                    if (!contains) {
                                        String str2 = extendedBssidInfo.security;
                                        if (!str2.toUpperCase().contains("[WEP]")) {
                                            if (str2.toUpperCase().contains("TKIP")) {
                                                String m =
                                                        AbstractResolvableFuture$$ExternalSyntheticOutline0
                                                                .m(str, " [TKIP]");
                                                if (!arrayList32.contains(m)) {
                                                    arrayList32.add(m);
                                                    break;
                                                }
                                            }
                                        } else {
                                            String m2 =
                                                    AbstractResolvableFuture$$ExternalSyntheticOutline0
                                                            .m(str, " [WEP]");
                                            if (!arrayList32.contains(m2)) {
                                                arrayList32.add(m2);
                                                break;
                                            }
                                        }
                                    } else {
                                        String m3 =
                                                AbstractResolvableFuture$$ExternalSyntheticOutline0
                                                        .m(str, " [OPEN]");
                                        if (!arrayList32.contains(m3)) {
                                            arrayList32.add(m3);
                                            break;
                                        }
                                    }
                                    break;
                                default:
                                    if (AdviserFragment.mDefaultNames.contains(
                                            extendedBssidInfo.ssid.toLowerCase())) {
                                        String str3 = extendedBssidInfo.ssid;
                                        if (!arrayList32.contains(str3)) {
                                            arrayList32.add(str3);
                                            SemLog.d("HomeWifi.AdviserFragment", str3);
                                            break;
                                        }
                                    }
                                    break;
                            }
                        }
                    });
            if (arrayList3.size() > 0) {
                StringBuilder sb2 = new StringBuilder();
                arrayList3.forEach(new AdviserFragment$$ExternalSyntheticLambda1(4, sb2));
                sb2.deleteCharAt(sb2.length() - 1);
                if (arrayList3.size() > 1) {
                    ((TextView) this.mUniqueName.findViewById(R.id.unique_name_desc_top))
                            .setText("Following Wi-Fi routers use a default name.");
                }
                ((TextView) this.mUniqueName.findViewById(R.id.unique_name_ssid)).setText(sb2);
                this.mUniqueName.setVisibility(0);
                this.mUniqueName.findViewById(R.id.unique_name_divider).setVisibility(z2 ? 0 : 8);
                i2 = 1;
            } else {
                i2 = 0;
            }
            int i12 = i + i2;
            boolean z3 = i12 > 0;
            final HashMap hashMap3 = new HashMap();
            final int i13 = 1;
            this.mBssids.forEach(
                    new Consumer() { // from class:
                                     // com.samsung.android.settings.wifi.develop.homewifi.AdviserFragment$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i14 = i13;
                            Object obj2 = hashMap3;
                            switch (i14) {
                                case 0:
                                    HashSet hashSet = (HashSet) obj2;
                                    ExtendedBssidInfo extendedBssidInfo = (ExtendedBssidInfo) obj;
                                    HashSet hashSet2 = AdviserFragment.mDefaultNames;
                                    if (!extendedBssidInfo.extra11e) {
                                        hashSet.add(extendedBssidInfo.ssid);
                                        break;
                                    }
                                    break;
                                default:
                                    ExtendedBssidInfo extendedBssidInfo2 = (ExtendedBssidInfo) obj;
                                    HashSet hashSet3 = AdviserFragment.mDefaultNames;
                                    ((ArrayList)
                                                    ((HashMap) obj2)
                                                            .computeIfAbsent(
                                                                    extendedBssidInfo2.ssid,
                                                                    new AdviserFragment$$ExternalSyntheticLambda12()))
                                            .add(extendedBssidInfo2);
                                    break;
                            }
                        }
                    });
            ArrayList arrayList4 = new ArrayList(hashMap3.keySet());
            int i14 = 0;
            while (i14 < arrayList4.size()) {
                String str = (String) arrayList4.get(i14);
                ArrayList arrayList5 = (ArrayList) hashMap3.get(str);
                if (arrayList5 != null) {
                    int i15 = i14 + 1;
                    while (i15 < arrayList4.size()) {
                        String str2 = (String) arrayList4.get(i15);
                        ArrayList arrayList6 = (ArrayList) hashMap3.get(str2);
                        if (arrayList6 != null && str2.startsWith(str)) {
                            Iterator it = arrayList5.iterator();
                            boolean z4 = z;
                            while (true) {
                                if (!it.hasNext()) {
                                    hashMap = hashMap3;
                                    break;
                                }
                                ExtendedBssidInfo extendedBssidInfo = (ExtendedBssidInfo) it.next();
                                Iterator it2 = arrayList6.iterator();
                                while (true) {
                                    if (!it2.hasNext()) {
                                        hashMap = hashMap3;
                                        break;
                                    }
                                    ExtendedBssidInfo extendedBssidInfo2 =
                                            (ExtendedBssidInfo) it2.next();
                                    String str3 = extendedBssidInfo.bssid;
                                    String str4 = extendedBssidInfo2.bssid;
                                    ExtendedBssidInfo extendedBssidInfo3 = extendedBssidInfo;
                                    if (str3.length() != str4.length()) {
                                        hashMap = hashMap3;
                                        i8 = 1;
                                        i7 = 0;
                                    } else {
                                        hashMap = hashMap3;
                                        int i16 = 0;
                                        i7 = 0;
                                        while (i16 < str3.length()) {
                                            String str5 = str3;
                                            if (str3.charAt(i16) != str4.charAt(i16)) {
                                                i7++;
                                            }
                                            i16++;
                                            str3 = str5;
                                        }
                                        i8 = 1;
                                    }
                                    if (i7 == i8) {
                                        z4 = true;
                                        break;
                                    }
                                    extendedBssidInfo = extendedBssidInfo3;
                                    hashMap3 = hashMap;
                                }
                                if (z4) {
                                    break;
                                }
                                hashMap3 = hashMap;
                            }
                            if (z4) {
                                arrayList4.remove(str2);
                                i15--;
                                SemLog.d(
                                        "HomeWifi.AdviserFragment",
                                        "checkMultipleNames: Remove ".concat(str2));
                            }
                        } else {
                            hashMap = hashMap3;
                        }
                        i15++;
                        i9 = 1;
                        hashMap3 = hashMap;
                        z = false;
                    }
                }
                i14++;
                i9 = i9;
                hashMap3 = hashMap3;
                z = false;
            }
            int i17 = i9;
            if (arrayList4.size() > i17) {
                StringBuilder sb3 = new StringBuilder();
                arrayList4.forEach(new AdviserFragment$$ExternalSyntheticLambda1(1, sb3));
                sb3.deleteCharAt(sb3.length() - i17);
                ((TextView) this.mMultipleName.findViewById(R.id.multiple_name_ssid)).setText(sb3);
                this.mMultipleName.setVisibility(0);
                this.mMultipleName
                        .findViewById(R.id.multiple_name_divider)
                        .setVisibility(z3 ? 0 : 8);
                i3 = 1;
            } else {
                i3 = 0;
            }
            int i18 = i12 + i3;
            boolean z5 = i18 > 0;
            final HashMap hashMap4 = new HashMap();
            this.mBssids.forEach(
                    new Consumer() { // from class:
                                     // com.samsung.android.settings.wifi.develop.homewifi.AdviserFragment$$ExternalSyntheticLambda7
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            HashSet hashSet;
                            Integer num;
                            AdviserFragment adviserFragment = AdviserFragment.this;
                            HashMap hashMap5 = hashMap4;
                            ExtendedBssidInfo extendedBssidInfo4 = (ExtendedBssidInfo) obj;
                            HashSet hashSet2 = AdviserFragment.mDefaultNames;
                            adviserFragment.getClass();
                            boolean containsKey = hashMap5.containsKey(extendedBssidInfo4.ssid);
                            String str6 = extendedBssidInfo4.ssid;
                            if (containsKey) {
                                hashSet = (HashSet) hashMap5.get(str6);
                            } else {
                                hashSet = new HashSet();
                                hashMap5.put(str6, hashSet);
                            }
                            HashSet hashSet3 = new HashSet();
                            for (String str7 :
                                    extendedBssidInfo4
                                            .security
                                            .replace("\n", ApnSettings.MVNO_NONE)
                                            .split("]")) {
                                String[] split = str7.split("-");
                                if (split.length >= 3) {
                                    hashSet3.addAll(Arrays.asList(split[1].split("\\+")));
                                } else if (split.length == 1) {
                                    hashSet3.addAll(
                                            Arrays.asList(
                                                    split[0].replace("]", ApnSettings.MVNO_NONE)
                                                            .split("\\+")));
                                }
                            }
                            int size = AdviserFragment.mSecurityOrder.size() - 1;
                            Iterator it3 = hashSet3.iterator();
                            String str8 = "OPEN";
                            while (it3.hasNext()) {
                                String str9 = (String) it3.next();
                                if (!"?".equals(str9)
                                        && (num =
                                                        (Integer)
                                                                AdviserFragment.mSecurityOrder.get(
                                                                        str9))
                                                != null
                                        && num.intValue() < size) {
                                    size = num.intValue();
                                    str8 = str9;
                                }
                            }
                            hashSet.add(str8);
                        }
                    });
            final StringBuilder sb4 = new StringBuilder();
            hashMap4.forEach(
                    new BiConsumer() { // from class:
                                       // com.samsung.android.settings.wifi.develop.homewifi.AdviserFragment$$ExternalSyntheticLambda8
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            StringBuilder sb5 = sb4;
                            String str6 = (String) obj;
                            HashSet hashSet = (HashSet) obj2;
                            HashSet hashSet2 = AdviserFragment.mDefaultNames;
                            SemLog.d(
                                    "HomeWifi.AdviserFragment",
                                    "checkMultipleSecurity: " + str6 + " " + hashSet);
                            if (hashSet.size() > 1) {
                                if (sb5.length() > 0) {
                                    sb5.append("\n");
                                }
                                sb5.append(str6);
                                sb5.append(" ");
                                hashSet.forEach(
                                        new AdviserFragment$$ExternalSyntheticLambda1(2, sb5));
                            }
                        }
                    });
            if (sb4.length() > 0) {
                if (sb4.toString().contains("\n")) {
                    ((TextView)
                                    this.mMultipleSecurity.findViewById(
                                            R.id.multiple_security_desc_top))
                            .setText("Following Wi-Fi routers uses multiple different securities.");
                }
                ((TextView) this.mMultipleSecurity.findViewById(R.id.multiple_security_ssid))
                        .setText(sb4);
                this.mMultipleSecurity.setVisibility(0);
                this.mMultipleSecurity
                        .findViewById(R.id.multiple_security_divider)
                        .setVisibility(z5 ? 0 : 8);
                i4 = 1;
            } else {
                i4 = 0;
            }
            int i19 = i18 + i4;
            boolean z6 = i19 > 0;
            final HashSet hashSet = new HashSet();
            final int i20 = 0;
            this.mBssids.forEach(
                    new Consumer() { // from class:
                                     // com.samsung.android.settings.wifi.develop.homewifi.AdviserFragment$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i142 = i20;
                            Object obj2 = hashSet;
                            switch (i142) {
                                case 0:
                                    HashSet hashSet2 = (HashSet) obj2;
                                    ExtendedBssidInfo extendedBssidInfo4 = (ExtendedBssidInfo) obj;
                                    HashSet hashSet22 = AdviserFragment.mDefaultNames;
                                    if (!extendedBssidInfo4.extra11e) {
                                        hashSet2.add(extendedBssidInfo4.ssid);
                                        break;
                                    }
                                    break;
                                default:
                                    ExtendedBssidInfo extendedBssidInfo22 = (ExtendedBssidInfo) obj;
                                    HashSet hashSet3 = AdviserFragment.mDefaultNames;
                                    ((ArrayList)
                                                    ((HashMap) obj2)
                                                            .computeIfAbsent(
                                                                    extendedBssidInfo22.ssid,
                                                                    new AdviserFragment$$ExternalSyntheticLambda12()))
                                            .add(extendedBssidInfo22);
                                    break;
                            }
                        }
                    });
            if (hashSet.size() > 0) {
                StringBuilder sb5 = new StringBuilder();
                hashSet.forEach(new AdviserFragment$$ExternalSyntheticLambda1(0, sb5));
                sb5.deleteCharAt(sb5.length() - 1);
                if (hashSet.size() > 1) {
                    ((TextView) this.mWMM.findViewById(R.id.wmm_desc_top))
                            .setText("WMM is disabled in the following Wi-Fi routers.");
                }
                ((TextView) this.mWMM.findViewById(R.id.wmm_ssid)).setText(sb5);
                this.mWMM.setVisibility(0);
                this.mWMM.findViewById(R.id.wmm_divider).setVisibility(z6 ? 0 : 8);
                i5 = 1;
            } else {
                i5 = 0;
            }
            int i21 = i19 + i5;
            if (i21 == 0) {
                i6 = 0;
                this.mExcellent.setVisibility(0);
            } else {
                i6 = 0;
            }
            if (i21 == 0) {
                this.mWithProblemImageView.setVisibility(8);
                this.mWithoutProblemImageView.setVisibility(i6);
            } else {
                this.mWithProblemImageView.setVisibility(i6);
                this.mWithoutProblemImageView.setVisibility(8);
            }
            ProgressDecorator progressDecorator2 = this.mProgressDecorator;
            if (progressDecorator2 != null) {
                ((MainActivity) progressDecorator2).setProgressAnimation(1, i21 == 0 ? 2 : 4);
            }
        }
        return inflate;
    }
}
