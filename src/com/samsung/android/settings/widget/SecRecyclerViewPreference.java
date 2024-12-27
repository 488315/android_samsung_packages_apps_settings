package com.samsung.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.notification.NotificationBackend;

import com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler;
import com.samsung.android.settings.notification.app.AppNotificationTypeAdapter;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecRecyclerViewPreference extends Preference {
    public static BixbyRoutineActionHandler mRSHandler;
    public int COLUMN_COUNT;
    public final AppNotificationTypeAdapter mAppNotificationTypeAdapter;
    public final ArrayList mAppNotificationTypeInfoList;
    public final Context mContext;
    public GridLayoutManager mGridLayoutManager;
    public boolean mNestedEnable;
    public RecyclerView mNotificationTypeRecyclerView;

    public SecRecyclerViewPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.COLUMN_COUNT = 3;
        this.mNestedEnable = true;
        setLayoutResource(R.layout.sec_app_notification_types);
        this.mContext = context;
        ArrayList arrayList = new ArrayList();
        this.mAppNotificationTypeInfoList = arrayList;
        AppNotificationTypeAdapter appNotificationTypeAdapter = new AppNotificationTypeAdapter();
        appNotificationTypeAdapter.mAppNotificationTypeInfoList = arrayList;
        appNotificationTypeAdapter.mBackend = new NotificationBackend();
        this.mAppNotificationTypeAdapter = appNotificationTypeAdapter;
        mRSHandler = BixbyRoutineActionHandler.getInstance();
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mNotificationTypeRecyclerView =
                (RecyclerView) preferenceViewHolder.findViewById(R.id.notification_type_recycler);
        this.mGridLayoutManager = new GridLayoutManager(this.COLUMN_COUNT);
        this.mNotificationTypeRecyclerView.setNestedScrollingEnabled(this.mNestedEnable);
        this.mNotificationTypeRecyclerView.setLayoutManager(this.mGridLayoutManager);
        this.mNotificationTypeRecyclerView.setAdapter(this.mAppNotificationTypeAdapter);
    }
}
