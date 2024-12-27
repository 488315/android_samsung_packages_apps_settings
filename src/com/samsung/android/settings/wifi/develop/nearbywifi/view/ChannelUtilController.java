package com.samsung.android.settings.wifi.develop.nearbywifi.view;

import android.net.wifi.ScanResult;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.samsung.android.settings.wifi.develop.nearbywifi.model.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ChannelUtilController {
    public Preference mBestChannel;
    public PreferenceCategory[] mCategory;
    public CuBarChart mCuBarChart24G;
    public CuBarChart mCuBarChart5G;
    public CuBarChart mCuBarChart6G;
    public Map mCuMap;
    public Repository mRepo;

    public final void setChannelRecommendation() {
        Repository repository = this.mRepo;
        repository.getClass();
        int[] iArr = {2417, 2422};
        int[] iArr2 = {2427, 2432, 2442, 2447};
        int[] iArr3 = {2452, 2457, 2467, 2472};
        HashMap hashMap = new HashMap();
        hashMap.put(1, new ArrayList());
        hashMap.put(2, new ArrayList());
        hashMap.put(8, new ArrayList());
        ArrayList arrayList = new ArrayList(repository.supportedFreqs);
        Collections.sort(arrayList);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            int band = Repository.toBand(intValue);
            int convertFrequencyMhzToChannelIfSupported =
                    ScanResult.convertFrequencyMhzToChannelIfSupported(intValue);
            if (band != 1) {
                if (band == 2 && repository.isRecommendable(intValue)) {
                    ((List) hashMap.get(Integer.valueOf(band)))
                            .add(Integer.valueOf(convertFrequencyMhzToChannelIfSupported));
                }
            } else if (convertFrequencyMhzToChannelIfSupported == 1) {
                if (repository.isRecommendable(intValue)
                        && repository.isRecommendableBasedOnAdjacentFreqs(iArr)) {
                    ((List) hashMap.get(Integer.valueOf(band)))
                            .add(Integer.valueOf(convertFrequencyMhzToChannelIfSupported));
                }
            } else if (convertFrequencyMhzToChannelIfSupported == 6) {
                if (repository.isRecommendable(intValue)
                        && repository.isRecommendableBasedOnAdjacentFreqs(iArr2)) {
                    ((List) hashMap.get(Integer.valueOf(band)))
                            .add(Integer.valueOf(convertFrequencyMhzToChannelIfSupported));
                }
            } else if (convertFrequencyMhzToChannelIfSupported == 11
                    && repository.isRecommendable(intValue)
                    && repository.isRecommendableBasedOnAdjacentFreqs(iArr3)) {
                ((List) hashMap.get(Integer.valueOf(band)))
                        .add(Integer.valueOf(convertFrequencyMhzToChannelIfSupported));
            }
        }
        StringBuilder sb = new StringBuilder();
        List list = (List) hashMap.get(1);
        if (list != null && list.size() > 0) {
            sb.append("  - 2.4GHz band: ");
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                sb.append(((Integer) it2.next()).intValue());
                sb.append(" ");
            }
            sb.append("\n");
        }
        List list2 = (List) hashMap.get(2);
        if (list2 != null && list2.size() > 0) {
            sb.append("  - 5GHz band: ");
            Iterator it3 = list2.iterator();
            while (it3.hasNext()) {
                sb.append(((Integer) it3.next()).intValue());
                sb.append(" ");
            }
        }
        if (sb.length() == 0) {
            sb.append("  None");
        }
        Preference preference = this.mBestChannel;
        if (preference != null) {
            preference.setSummary(sb.toString());
        }
    }

    public final void setVisible(boolean z) {
        PreferenceCategory[] preferenceCategoryArr = this.mCategory;
        PreferenceCategory preferenceCategory = preferenceCategoryArr[0];
        if (preferenceCategory != null) {
            preferenceCategory.setVisible(z);
        }
        PreferenceCategory preferenceCategory2 = preferenceCategoryArr[1];
        if (preferenceCategory2 != null) {
            preferenceCategory2.setVisible(z);
        }
        PreferenceCategory preferenceCategory3 = preferenceCategoryArr[2];
        if (preferenceCategory3 != null) {
            preferenceCategory3.setVisible(z);
        }
        Preference preference = this.mBestChannel;
        if (preference != null) {
            preference.setVisible(z);
        }
    }
}
