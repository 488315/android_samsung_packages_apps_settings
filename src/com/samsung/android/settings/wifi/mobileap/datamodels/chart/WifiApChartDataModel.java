package com.samsung.android.settings.wifi.mobileap.datamodels.chart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApChartDataModel {
    public final List mXaxisValueList;
    public final List mYaxisValueList;

    public WifiApChartDataModel(List list, List list2) {
        this.mYaxisValueList = new ArrayList();
        this.mXaxisValueList = new ArrayList();
        if (list != null && list.size() == list2.size()) {
            this.mXaxisValueList = list;
            this.mYaxisValueList = list2;
        } else if (list2 != null) {
            Iterator it = list2.iterator();
            int i = 0;
            while (it.hasNext()) {
                ((Float) it.next()).getClass();
                this.mXaxisValueList.add(String.valueOf(i));
                i++;
            }
        }
    }
}
