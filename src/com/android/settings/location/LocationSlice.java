package com.android.settings.location;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;

import com.android.settings.R;
import com.android.settings.SubSettings;
import com.android.settings.slices.CustomSliceRegistry;
import com.android.settings.slices.CustomSliceable;
import com.android.settings.slices.SliceBuilderUtils;
import com.android.settingslib.Utils;

import com.sec.ims.gls.GlsIntent;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LocationSlice implements CustomSliceable {
    public final Context mContext;

    public LocationSlice(Context context) {
        this.mContext = context;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Intent getIntent() {
        String charSequence = this.mContext.getText(R.string.location_settings_title).toString();
        return SliceBuilderUtils.buildSearchResultPageIntent(
                        63,
                        R.string.menu_key_location,
                        this.mContext,
                        LocationSettings.class.getName(),
                        GlsIntent.Extras.EXTRA_LOCATION,
                        charSequence)
                .setClassName(this.mContext.getPackageName(), SubSettings.class.getName())
                .setData(new Uri.Builder().appendPath(GlsIntent.Extras.EXTRA_LOCATION).build());
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Slice getSlice() {
        IconCompat createWithResource =
                IconCompat.createWithResource(
                        this.mContext,
                        android.R.drawable.iconfactory_adaptive_icon_drawable_wrapper);
        CharSequence text = this.mContext.getText(R.string.location_settings_title);
        int colorAttrDefaultColor =
                Utils.getColorAttrDefaultColor(this.mContext, android.R.attr.colorAccent);
        SliceAction createDeeplink =
                SliceAction.createDeeplink(
                        PendingIntent.getActivity(this.mContext, 0, getIntent(), 67108864),
                        createWithResource,
                        0,
                        text);
        ListBuilder listBuilder =
                new ListBuilder(this.mContext, CustomSliceRegistry.LOCATION_SLICE_URI);
        listBuilder.mImpl.setColor(colorAttrDefaultColor);
        ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
        rowBuilder.mTitle = text;
        rowBuilder.mTitleLoading = false;
        rowBuilder.setTitleItem(createWithResource);
        rowBuilder.mPrimaryAction = createDeeplink;
        listBuilder.mImpl.addRow(rowBuilder);
        return listBuilder.build();
    }

    @Override // com.android.settings.slices.Sliceable
    public final int getSliceHighlightMenuRes() {
        return R.string.menu_key_location;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Uri getUri$1() {
        return CustomSliceRegistry.LOCATION_SLICE_URI;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final void onNotifyChange(Intent intent) {}
}
