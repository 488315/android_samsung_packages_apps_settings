package com.android.settings.media;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.session.MediaController;
import android.net.Uri;

import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;

import com.android.settings.Utils;
import com.android.settings.slices.CustomSliceRegistry;
import com.android.settings.slices.CustomSliceable;
import com.android.settings.slices.SliceBackgroundWorker;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MediaOutputIndicatorSlice implements CustomSliceable {
    public final Context mContext;
    public MediaOutputIndicatorWorker mWorker;

    public MediaOutputIndicatorSlice(Context context) {
        this.mContext = context;
    }

    @Override // com.android.settings.slices.Sliceable
    public final Class getBackgroundWorkerClass() {
        return MediaOutputIndicatorWorker.class;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Intent getIntent() {
        return null;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Slice getSlice() {
        if (!isVisible()) {
            ListBuilder listBuilder =
                    new ListBuilder(
                            this.mContext, CustomSliceRegistry.MEDIA_OUTPUT_INDICATOR_SLICE_URI);
            listBuilder.mImpl.setIsError();
            return listBuilder.build();
        }
        IconCompat createWithResource =
                IconCompat.createWithResource(this.mContext, R.drawable.ic_visibility);
        int i =
                getWorker().getActiveLocalMediaController() != null
                        ? com.android.settings.R.string.media_output_label_title
                        : com.android.settings.R.string.media_output_title_without_playing;
        Context context = this.mContext;
        String string =
                context.getString(i, Utils.getApplicationLabel(context, getWorker().mPackageName));
        SliceAction sliceAction =
                new SliceAction(getBroadcastIntent(this.mContext), createWithResource, 0, string);
        int colorAttrDefaultColor =
                com.android.settingslib.Utils.getColorAttrDefaultColor(
                        this.mContext, R.attr.colorAccent);
        ListBuilder listBuilder2 =
                new ListBuilder(
                        this.mContext, CustomSliceRegistry.MEDIA_OUTPUT_INDICATOR_SLICE_URI);
        listBuilder2.mImpl.setColor(colorAttrDefaultColor);
        ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
        rowBuilder.mTitle = string;
        rowBuilder.mTitleLoading = false;
        rowBuilder.setTitleItem(
                IconCompat.createWithBitmap(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)));
        rowBuilder.mSubtitle = getWorker().getCurrentConnectedMediaDevice().getName();
        rowBuilder.mSubtitleLoading = false;
        rowBuilder.mPrimaryAction = sliceAction;
        listBuilder2.mImpl.addRow(rowBuilder);
        return listBuilder2.build();
    }

    @Override // com.android.settings.slices.Sliceable
    public final int getSliceHighlightMenuRes() {
        return com.android.settings.R.string.menu_key_connected_devices;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Uri getUri$1() {
        return CustomSliceRegistry.MEDIA_OUTPUT_INDICATOR_SLICE_URI;
    }

    public final MediaOutputIndicatorWorker getWorker() {
        if (this.mWorker == null) {
            this.mWorker =
                    (MediaOutputIndicatorWorker)
                            SliceBackgroundWorker.getInstance(
                                    CustomSliceRegistry.MEDIA_OUTPUT_INDICATOR_SLICE_URI);
        }
        return this.mWorker;
    }

    public boolean isVisible() {
        return (getWorker() == null
                        || com.android.settingslib.Utils.isAudioModeOngoingCall(this.mContext)
                        || ((CopyOnWriteArrayList) getWorker().mMediaDevices).size() <= 0)
                ? false
                : true;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final void onNotifyChange(Intent intent) {
        MediaController activeLocalMediaController = getWorker().getActiveLocalMediaController();
        if (activeLocalMediaController == null) {
            this.mContext.sendBroadcast(
                    new Intent()
                            .setPackage("com.android.systemui")
                            .setAction(
                                    "com.android.systemui.action.LAUNCH_SYSTEM_MEDIA_OUTPUT_DIALOG"));
        } else {
            this.mContext.sendBroadcast(
                    new Intent()
                            .setPackage("com.android.systemui")
                            .setAction("com.android.systemui.action.LAUNCH_MEDIA_OUTPUT_DIALOG")
                            .putExtra(
                                    "key_media_session_token",
                                    activeLocalMediaController.getSessionToken())
                            .putExtra("package_name", activeLocalMediaController.getPackageName()));
        }
        this.mContext.sendBroadcast(
                new Intent()
                        .setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG)
                        .setAction("com.android.settings.panel.action.CLOSE_PANEL"));
    }
}
