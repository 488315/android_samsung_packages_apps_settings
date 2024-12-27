package com.samsung.android.settings.analyzestorage.presenter.managers.detailinfo;

import android.content.Context;
import android.os.HandlerThread;

import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;
import com.samsung.android.settings.analyzestorage.ui.SubAppListActivity$observeCheckedItemCount$listener$1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class CheckedItemsDetailsLoader {
    public boolean mIsCancel;
    public AbsDetailsInfoLoader$LoadHandler mLoadHandler;
    public final List mListenerList = new CopyOnWriteArrayList();
    public final Map mListenerMap = new HashMap();
    public final HandlerThread mHandlerThread = new HandlerThread("details_thread");

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DataInfoTaskItem {
        public final List mAppList;
        public final Context mContext;
        public final DetailsResultListener mListener;
        public final PageInfo mPageInfo;
        public Map mTotalSizeInfo;

        public DataInfoTaskItem(
                Context context,
                PageInfo pageInfo,
                List list,
                SubAppListActivity$observeCheckedItemCount$listener$1
                        subAppListActivity$observeCheckedItemCount$listener$1) {
            this.mContext = context;
            this.mPageInfo = pageInfo;
            this.mAppList = list;
            this.mListener = subAppListActivity$observeCheckedItemCount$listener$1;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface DetailsResultListener {
        void onResult(DirInfo dirInfo);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DirInfo {
        public long mSize;
    }

    static {
        Runtime.getRuntime().availableProcessors();
    }
}
