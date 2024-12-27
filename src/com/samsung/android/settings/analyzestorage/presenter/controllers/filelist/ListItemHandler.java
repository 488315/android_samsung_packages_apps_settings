package com.samsung.android.settings.analyzestorage.presenter.controllers.filelist;

import androidx.lifecycle.MutableLiveData;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.domain.entity.DataInfo;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ListItemHandler {
    public final List mAppDataList;
    public final MutableLiveData mCheckItemSize = new MutableLiveData(ApnSettings.MVNO_NONE);
    public final Set mCheckableItemList;
    public final MutableLiveData mCheckedItemCount;
    public final List mCheckedItemList;
    public final MutableLiveData mGroupInfo;
    public final MutableLiveData mIsAllChecked;
    public final MutableLiveData mIsDisplayCheckItemSize;
    public boolean mIsSelectedMousePoint;
    public final MutableLiveData mListItems;

    public ListItemHandler() {
        Boolean bool = Boolean.FALSE;
        this.mIsDisplayCheckItemSize = new MutableLiveData(bool);
        this.mIsAllChecked = new MutableLiveData(bool);
        this.mCheckedItemCount = new MutableLiveData();
        this.mCheckedItemList = new ArrayList();
        this.mCheckableItemList = new HashSet();
        this.mListItems = new MutableLiveData(null);
        this.mGroupInfo = new MutableLiveData();
        this.mAppDataList = new ArrayList();
        this.mIsSelectedMousePoint = false;
    }

    public final int getCheckedItemCount() {
        return ((ArrayList) this.mCheckedItemList).size();
    }

    public final DataInfo getItemAt(int i) {
        List list = (List) this.mListItems.getValue();
        if (list == null) {
            return null;
        }
        try {
            return (DataInfo) list.get(i);
        } catch (IndexOutOfBoundsException e) {
            Log.e("ListItemHandler", "getItemAt() ] Exception e : " + e.getMessage());
            return null;
        }
    }

    public final int getItemCount() {
        List list = (List) this.mListItems.getValue();
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        return list.size();
    }

    public final List getListItems() {
        List list = (List) this.mListItems.getValue();
        StringBuilder sb = new StringBuilder("getListItems() ] listItems count : ");
        sb.append(list != null ? list.size() : 0);
        Log.d("ListItemHandler", sb.toString());
        return list;
    }

    public final void resetCheckedItemInfo() {
        ((ArrayList) this.mCheckedItemList).clear();
        this.mCheckedItemCount.setValue(
                Integer.valueOf(((ArrayList) this.mCheckedItemList).size()));
        this.mIsAllChecked.setValue(Boolean.FALSE);
    }

    public final void setAllItemChecked(boolean z) {
        ((ArrayList) this.mCheckedItemList).clear();
        if (z) {
            List listItems = getListItems();
            ((ArrayList) this.mCheckedItemList)
                    .addAll(
                            (Collection)
                                    listItems.stream()
                                            .filter(
                                                    new ListItemHandler$$ExternalSyntheticLambda0(
                                                            0, this))
                                            .collect(Collectors.toList()));
        }
        this.mCheckedItemCount.setValue(
                Integer.valueOf(((ArrayList) this.mCheckedItemList).size()));
        this.mIsAllChecked.setValue(Boolean.valueOf(z));
    }

    public final void setItemChecked(int i, boolean z) {
        int size = ((HashSet) this.mCheckableItemList).size();
        DataInfo itemAt = getItemAt(i);
        if (z
                ? !((ArrayList) this.mCheckedItemList).contains(itemAt)
                        && ((ArrayList) this.mCheckedItemList).add(itemAt)
                : ((ArrayList) this.mCheckedItemList).remove(itemAt)) {
            this.mCheckedItemCount.setValue(Integer.valueOf(getCheckedItemCount()));
            updateCheckedAll(size);
        }
    }

    public final void setListItems(List list) {
        Log.d("ListItemHandler", "setListItems() ] data size : " + list.size());
        this.mListItems.setValue(list);
    }

    public final void updateCheckedAll(int i) {
        Boolean bool = Boolean.TRUE;
        MutableLiveData mutableLiveData = this.mIsAllChecked;
        boolean equals = bool.equals(mutableLiveData.getValue());
        boolean z = getCheckedItemCount() == i && i > 0;
        if (equals != z) {
            mutableLiveData.setValue(Boolean.valueOf(z));
        }
    }
}
