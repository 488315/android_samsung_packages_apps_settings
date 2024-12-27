package com.android.settings.homepage.contextualcards.slices;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.storage.StorageManager;
import android.text.format.Formatter;

import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;

import com.android.settings.R;
import com.android.settings.SubSettings;
import com.android.settings.deviceinfo.StorageDashboardFragment;
import com.android.settings.slices.CustomSliceRegistry;
import com.android.settings.slices.CustomSliceable;
import com.android.settings.slices.SliceBuilderUtils;
import com.android.settingslib.Utils;
import com.android.settingslib.deviceinfo.PrivateStorageInfo;
import com.android.settingslib.deviceinfo.StorageManagerVolumeProvider;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.volte2.data.VolteConstants;

import java.text.NumberFormat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LowStorageSlice implements CustomSliceable {
    public final Context mContext;

    public LowStorageSlice(Context context) {
        this.mContext = context;
    }

    public final ListBuilder.RowBuilder buildRowBuilder(
            CharSequence charSequence, String str, IconCompat iconCompat) {
        SliceAction createDeeplink =
                SliceAction.createDeeplink(
                        PendingIntent.getActivity(this.mContext, 0, getIntent(), 67108864),
                        iconCompat,
                        0,
                        charSequence);
        ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
        rowBuilder.setTitleItem(iconCompat);
        rowBuilder.mTitle = charSequence;
        rowBuilder.mTitleLoading = false;
        rowBuilder.mSubtitle = str;
        rowBuilder.mSubtitleLoading = false;
        rowBuilder.mPrimaryAction = createDeeplink;
        return rowBuilder;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Intent getIntent() {
        return SliceBuilderUtils.buildSearchResultPageIntent(
                        VolteConstants.ErrorCode.RTP_TIME_OUT,
                        R.string.menu_key_storage,
                        this.mContext,
                        StorageDashboardFragment.class.getName(),
                        ApnSettings.MVNO_NONE,
                        this.mContext.getText(R.string.storage_label).toString())
                .setClassName(this.mContext.getPackageName(), SubSettings.class.getName())
                .setData(CustomSliceRegistry.LOW_STORAGE_SLICE_URI);
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Slice getSlice() {
        PrivateStorageInfo privateStorageInfo =
                PrivateStorageInfo.getPrivateStorageInfo(
                        new StorageManagerVolumeProvider(
                                (StorageManager)
                                        this.mContext.getSystemService(StorageManager.class)));
        long j = privateStorageInfo.totalBytes;
        long j2 = privateStorageInfo.freeBytes;
        double d = (j - j2) / j;
        String format = NumberFormat.getPercentInstance().format(d);
        String formatFileSize = Formatter.formatFileSize(this.mContext, j2);
        ListBuilder listBuilder =
                new ListBuilder(this.mContext, CustomSliceRegistry.LOW_STORAGE_SLICE_URI);
        listBuilder.mImpl.setColor(
                Utils.getColorAttrDefaultColor(this.mContext, android.R.attr.colorAccent));
        IconCompat createWithResource =
                IconCompat.createWithResource(this.mContext, R.drawable.ic_storage);
        if (d >= 0.85d) {
            listBuilder.mImpl.addRow(
                    buildRowBuilder(
                            this.mContext.getText(R.string.storage_menu_free),
                            this.mContext.getString(
                                    R.string.low_storage_summary, format, formatFileSize),
                            createWithResource));
            return listBuilder.build();
        }
        listBuilder.mImpl.addRow(
                buildRowBuilder(
                        this.mContext.getText(R.string.storage_settings),
                        this.mContext.getString(R.string.storage_summary, format, formatFileSize),
                        createWithResource));
        listBuilder.mImpl.setIsError();
        return listBuilder.build();
    }

    @Override // com.android.settings.slices.Sliceable
    public final int getSliceHighlightMenuRes() {
        return R.string.menu_key_storage;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Uri getUri$1() {
        return CustomSliceRegistry.LOW_STORAGE_SLICE_URI;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final void onNotifyChange(Intent intent) {}
}
