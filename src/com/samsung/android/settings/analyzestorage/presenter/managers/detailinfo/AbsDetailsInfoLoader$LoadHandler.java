package com.samsung.android.settings.analyzestorage.presenter.managers.detailinfo;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.LruCache;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.execution.ExecuteClearAppCache$1$$ExternalSyntheticLambda3;
import com.samsung.android.settings.analyzestorage.presenter.utils.StringConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AbsDetailsInfoLoader$LoadHandler extends Handler {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CheckedItemsDetailsLoader this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ AbsDetailsInfoLoader$LoadHandler(
            CheckedItemsDetailsLoader checkedItemsDetailsLoader, Looper looper, int i) {
        super(looper);
        this.$r8$classId = i;
        this.this$0 = checkedItemsDetailsLoader;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        Map map;
        switch (this.$r8$classId) {
            case 0:
                CheckedItemsDetailsLoader checkedItemsDetailsLoader = this.this$0;
                checkedItemsDetailsLoader.getClass();
                CheckedItemsDetailsLoader.DataInfoTaskItem dataInfoTaskItem =
                        (CheckedItemsDetailsLoader.DataInfoTaskItem) message.obj;
                CheckedItemsDetailsLoader.DirInfo dirInfo = new CheckedItemsDetailsLoader.DirInfo();
                dirInfo.mSize = 0L;
                String path = dataInfoTaskItem.mPageInfo.getPath();
                Object obj = ((HashMap) checkedItemsDetailsLoader.mListenerMap).get(path);
                CheckedItemsDetailsLoader.DetailsResultListener detailsResultListener =
                        dataInfoTaskItem.mListener;
                if (obj == null
                        || !((List) ((HashMap) checkedItemsDetailsLoader.mListenerMap).get(path))
                                .contains(detailsResultListener)) {
                    ((CopyOnWriteArrayList) checkedItemsDetailsLoader.mListenerList)
                            .add(detailsResultListener);
                    ((HashMap) checkedItemsDetailsLoader.mListenerMap)
                            .put(path, checkedItemsDetailsLoader.mListenerList);
                }
                List list = dataInfoTaskItem.mAppList;
                if (list != null) {
                    Log.d(
                            "CheckedItemsDetailsLoader",
                            "loadDetailInfo() ] " + list.size() + " items are selected.");
                    if (!dataInfoTaskItem.mAppList.isEmpty()) {
                        ArrayList arrayList = new ArrayList(dataInfoTaskItem.mAppList);
                        dataInfoTaskItem.mTotalSizeInfo = new HashMap();
                        dirInfo.mSize =
                                arrayList.parallelStream()
                                        .filter(
                                                new CheckedItemsDetailsLoader$$ExternalSyntheticLambda0())
                                        .mapToLong(
                                                new ExecuteClearAppCache$1$$ExternalSyntheticLambda3())
                                        .sum();
                    }
                    if (!checkedItemsDetailsLoader.mIsCancel) {
                        Context context = dataInfoTaskItem.mContext;
                        long j = dirInfo.mSize;
                        LruCache lruCache = StringConverter.sCachedSize;
                        String str = (String) lruCache.get(Long.valueOf(j));
                        if (str == null && context != null) {
                            str = StringConverter.formatFileSize(0, j, context);
                            lruCache.put(Long.valueOf(j), str);
                        }
                        if (str != null && (map = dataInfoTaskItem.mTotalSizeInfo) != null) {
                            map.put("detail_info", str);
                        }
                        AbsDetailsInfoLoader$LoadHandler absDetailsInfoLoader$LoadHandler =
                                new AbsDetailsInfoLoader$LoadHandler(
                                        checkedItemsDetailsLoader, Looper.getMainLooper(), 1);
                        absDetailsInfoLoader$LoadHandler.sendMessage(
                                absDetailsInfoLoader$LoadHandler.obtainMessage(0, dirInfo));
                        break;
                    }
                }
                break;
            default:
                CheckedItemsDetailsLoader checkedItemsDetailsLoader2 = this.this$0;
                checkedItemsDetailsLoader2.getClass();
                CheckedItemsDetailsLoader.DirInfo dirInfo2 =
                        (CheckedItemsDetailsLoader.DirInfo) message.obj;
                Iterator it =
                        ((CopyOnWriteArrayList) checkedItemsDetailsLoader2.mListenerList)
                                .iterator();
                while (it.hasNext()) {
                    ((CheckedItemsDetailsLoader.DetailsResultListener) it.next())
                            .onResult(dirInfo2);
                }
                break;
        }
    }
}
