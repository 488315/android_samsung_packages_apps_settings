package com.android.settings.notification.zen.lifestyle.apppicker;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DndAppSelectDataViewModel extends AndroidViewModel {
    public final MutableLiveData mSelectAppLiveData;
    public final ConcurrentHashMap mSelectMap;
    public final CopyOnWriteArrayList mSupportAppInfoList;
    public List mSupportAppList;
    public final MutableLiveData mSupportAppListLiveData;
    public final MutableLiveData misAllSelected;

    public DndAppSelectDataViewModel(Application application) {
        super(application);
        this.mSupportAppList = new ArrayList();
        this.mSupportAppListLiveData = new MutableLiveData();
        this.mSelectAppLiveData = new MutableLiveData();
        this.misAllSelected = new MutableLiveData();
        this.mSupportAppInfoList = new CopyOnWriteArrayList();
        this.mSelectMap = new ConcurrentHashMap();
    }

    public final int getCheckedCount$1() {
        if (isAllSelected$1()) {
            this.misAllSelected.postValue(Boolean.TRUE);
            return this.mSupportAppList.size();
        }
        this.misAllSelected.postValue(Boolean.FALSE);
        Iterator it = this.mSelectMap.keySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (((Boolean) this.mSelectMap.get((String) it.next())).booleanValue()) {
                i++;
            }
        }
        return i;
    }

    public final boolean isAllSelected$1() {
        boolean z;
        Iterator it = this.mSupportAppList.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = true;
                break;
            }
            if (!((Boolean) this.mSelectMap.get((String) it.next())).booleanValue()) {
                z = false;
                break;
            }
        }
        return this.mSelectMap.size() != 0 && z;
    }
}
