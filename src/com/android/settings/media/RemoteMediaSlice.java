package com.android.settings.media;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RoutingSessionInfo;
import android.net.Uri;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;

import com.android.settings.R;
import com.android.settings.SubSettings;
import com.android.settings.Utils;
import com.android.settings.notification.SoundSettings;
import com.android.settings.slices.CustomSliceRegistry;
import com.android.settings.slices.CustomSliceable;
import com.android.settings.slices.SliceBackgroundWorker;
import com.android.settings.slices.SliceBroadcastReceiver;
import com.android.settings.slices.SliceBuilderUtils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RemoteMediaSlice implements CustomSliceable {
    public final Context mContext;
    public MediaDeviceUpdateWorker mWorker;

    public RemoteMediaSlice(Context context) {
        this.mContext = context;
    }

    @Override // com.android.settings.slices.Sliceable
    public final Class getBackgroundWorkerClass() {
        return MediaDeviceUpdateWorker.class;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Intent getIntent() {
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.settings.slices.CustomSliceable
    public final Slice getSlice() {
        ListBuilder listBuilder =
                new ListBuilder(this.mContext, CustomSliceRegistry.REMOTE_MEDIA_SLICE_URI);
        listBuilder.mImpl.setColor(-1);
        String str = "RemoteMediaSlice";
        if (getWorker() == null) {
            Log.e("RemoteMediaSlice", "Unable to get the slice worker.");
            return listBuilder.build();
        }
        List remoteSessions = getWorker().mLocalMediaManager.mInfoMediaManager.getRemoteSessions();
        if (remoteSessions.isEmpty()) {
            Log.d("RemoteMediaSlice", "No active remote media device");
            return listBuilder.build();
        }
        CharSequence text = this.mContext.getText(R.string.remote_media_volume_option_title);
        IconCompat createWithResource =
                IconCompat.createWithResource(this.mContext, R.drawable.ic_volume_remote);
        IconCompat createWithBitmap =
                IconCompat.createWithBitmap(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888));
        Iterator it = remoteSessions.iterator();
        while (it.hasNext()) {
            RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) it.next();
            int volumeMax = routingSessionInfo.getVolumeMax();
            if (volumeMax <= 0) {
                Log.d(
                        str,
                        "Unable to add Slice. "
                                + ((Object) routingSessionInfo.getName())
                                + ": max volume is "
                                + volumeMax);
            } else if (getWorker()
                    .mLocalMediaManager
                    .shouldEnableVolumeSeekBar(routingSessionInfo)) {
                CharSequence applicationLabel =
                        Utils.getApplicationLabel(
                                this.mContext, routingSessionInfo.getClientPackageName());
                String string =
                        this.mContext.getString(
                                R.string.media_output_label_title, applicationLabel);
                ListBuilder.InputRangeBuilder inputRangeBuilder =
                        new ListBuilder.InputRangeBuilder();
                inputRangeBuilder.mTitleIcon = createWithResource;
                inputRangeBuilder.mTitle = text;
                int hashCode = routingSessionInfo.getId().hashCode();
                String id = routingSessionInfo.getId();
                Uri uri = CustomSliceRegistry.REMOTE_MEDIA_SLICE_URI;
                Iterator it2 = it;
                inputRangeBuilder.mInputAction =
                        PendingIntent.getBroadcast(
                                this.mContext,
                                hashCode,
                                new Intent(uri.toString())
                                        .setData(uri)
                                        .putExtra("media_id", id)
                                        .setClass(this.mContext, SliceBroadcastReceiver.class),
                                33554432);
                String id2 = routingSessionInfo.getId();
                Uri build = new Uri.Builder().appendPath(id2).build();
                String str2 = str;
                Intent buildSearchResultPageIntent =
                        SliceBuilderUtils.buildSearchResultPageIntent(
                                0,
                                R.string.menu_key_sound,
                                this.mContext,
                                SoundSettings.class.getName(),
                                id2,
                                this.mContext.getText(R.string.sound_settings).toString());
                buildSearchResultPageIntent.setClassName(
                        this.mContext.getPackageName(), SubSettings.class.getName());
                buildSearchResultPageIntent.setData(build);
                inputRangeBuilder.mPrimaryAction =
                        SliceAction.createDeeplink(
                                PendingIntent.getActivity(
                                        this.mContext, 0, buildSearchResultPageIntent, 67108864),
                                createWithResource,
                                0,
                                text);
                inputRangeBuilder.mMax = volumeMax;
                int volume = routingSessionInfo.getVolume();
                inputRangeBuilder.mValueSet = true;
                inputRangeBuilder.mValue = volume;
                listBuilder.mImpl.addInputRange(inputRangeBuilder);
                boolean isEmpty =
                        getWorker()
                                .mManager
                                .getTransferableRoutes(routingSessionInfo.getClientPackageName())
                                .isEmpty();
                boolean isEmpty2 = TextUtils.isEmpty(applicationLabel);
                String str3 = ApnSettings.MVNO_NONE;
                if (isEmpty2) {
                    applicationLabel = ApnSettings.MVNO_NONE;
                }
                SpannableString spannableString = new SpannableString(applicationLabel);
                spannableString.setSpan(
                        new ForegroundColorSpan(
                                com.android.settingslib.Utils.getColorAttrDefaultColor(
                                        this.mContext, android.R.attr.textColorSecondary)),
                        0,
                        spannableString.length(),
                        33);
                ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
                if (isEmpty) {
                    string = spannableString;
                }
                rowBuilder.mTitle = string;
                rowBuilder.mTitleLoading = false;
                rowBuilder.mSubtitle = routingSessionInfo.getName();
                rowBuilder.mSubtitleLoading = false;
                rowBuilder.setTitleItem(createWithBitmap);
                Intent intent =
                        new Intent(uri.toString())
                                .setData(uri)
                                .setClass(this.mContext, SliceBroadcastReceiver.class);
                if (!isEmpty) {
                    str3 = "action_launch_dialog";
                }
                PendingIntent broadcast =
                        PendingIntent.getBroadcast(
                                this.mContext,
                                routingSessionInfo.hashCode(),
                                intent.putExtra("customized_action", str3)
                                        .putExtra("RoutingSessionInfo", routingSessionInfo)
                                        .addFlags(268435456),
                                201326592);
                IconCompat createWithResource2 =
                        IconCompat.createWithResource(this.mContext, R.drawable.ic_volume_remote);
                Context context = this.mContext;
                rowBuilder.mPrimaryAction =
                        SliceAction.createDeeplink(
                                broadcast,
                                createWithResource2,
                                0,
                                context.getString(
                                        R.string.media_output_label_title,
                                        Utils.getApplicationLabel(
                                                context,
                                                routingSessionInfo.getClientPackageName())));
                listBuilder.addRow(rowBuilder);
                it = it2;
                str = str2;
            } else {
                Log.d(
                        str,
                        "Unable to add Slice. "
                                + ((Object) routingSessionInfo.getName())
                                + ": This is a group session");
            }
        }
        return listBuilder.build();
    }

    @Override // com.android.settings.slices.Sliceable
    public final int getSliceHighlightMenuRes() {
        return R.string.menu_key_connected_devices;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Uri getUri$1() {
        return CustomSliceRegistry.REMOTE_MEDIA_SLICE_URI;
    }

    public final MediaDeviceUpdateWorker getWorker() {
        if (this.mWorker == null) {
            this.mWorker =
                    (MediaDeviceUpdateWorker)
                            SliceBackgroundWorker.getInstance(
                                    CustomSliceRegistry.REMOTE_MEDIA_SLICE_URI);
        }
        return this.mWorker;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final void onNotifyChange(Intent intent) {
        int intExtra = intent.getIntExtra("android.app.slice.extra.RANGE_VALUE", -1);
        String stringExtra = intent.getStringExtra("media_id");
        if (!TextUtils.isEmpty(stringExtra)) {
            getWorker().mLocalMediaManager.adjustSessionVolume(intExtra, stringExtra);
        } else if (TextUtils.equals(
                "action_launch_dialog", intent.getStringExtra("customized_action"))) {
            this.mContext.sendBroadcast(
                    new Intent()
                            .setPackage("com.android.systemui")
                            .setAction("com.android.systemui.action.LAUNCH_MEDIA_OUTPUT_DIALOG")
                            .putExtra(
                                    "package_name",
                                    ((RoutingSessionInfo)
                                                    intent.getParcelableExtra("RoutingSessionInfo"))
                                            .getClientPackageName()));
            this.mContext.sendBroadcast(
                    new Intent()
                            .setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG)
                            .setAction("com.android.settings.panel.action.CLOSE_PANEL"));
        }
    }
}
