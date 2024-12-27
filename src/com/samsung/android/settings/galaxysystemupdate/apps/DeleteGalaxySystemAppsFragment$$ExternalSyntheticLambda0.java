package com.samsung.android.settings.galaxysystemupdate.apps;

import android.util.Log;

import com.samsung.android.settings.galaxysystemupdate.apps.data.ApexPackageVo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class DeleteGalaxySystemAppsFragment$$ExternalSyntheticLambda0 {
    public final /* synthetic */ DeleteGalaxySystemAppsFragment f$0;

    public void onClickResult(boolean z) {
        int i = DeleteGalaxySystemAppsFragment.$r8$clinit;
        DeleteGalaxySystemAppsFragment deleteGalaxySystemAppsFragment = this.f$0;
        deleteGalaxySystemAppsFragment.getClass();
        Log.d("DeleteGalaxySystemAppsFragment", "onClickResult : " + z);
        if (z) {
            DeleteGalaxySystemAppsAdapter deleteGalaxySystemAppsAdapter =
                    deleteGalaxySystemAppsFragment.adapter;
            deleteGalaxySystemAppsAdapter.getClass();
            ArrayList arrayList = new ArrayList();
            Iterator it = deleteGalaxySystemAppsAdapter.searchData.iterator();
            while (it.hasNext()) {
                ApexPackageVo apexPackageVo = (ApexPackageVo) it.next();
                if (apexPackageVo.isSelected) {
                    arrayList.add(apexPackageVo.packageName);
                }
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ((LinkedList) deleteGalaxySystemAppsFragment.seletedPackageQueue)
                        .add((String) it2.next());
            }
            deleteGalaxySystemAppsFragment.downgradeGalaxySystemApps();
        }
    }
}
