package com.android.settings.display;

import android.R;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.AmbientDisplayConfiguration;
import android.net.Uri;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.slice.Slice;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;

import com.android.settings.slices.CustomSliceRegistry;
import com.android.settings.slices.CustomSliceable;
import com.android.settingslib.Utils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AlwaysOnDisplaySlice implements CustomSliceable {
    public static final int MY_USER = UserHandle.myUserId();
    public final AmbientDisplayConfiguration mConfig;
    public final Context mContext;

    public AlwaysOnDisplaySlice(Context context) {
        this.mContext = context;
        this.mConfig = new AmbientDisplayConfiguration(context);
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Intent getIntent() {
        return null;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Slice getSlice() {
        AmbientDisplayConfiguration ambientDisplayConfiguration = this.mConfig;
        int i = MY_USER;
        if (!ambientDisplayConfiguration.alwaysOnAvailableForUser(i)) {
            return null;
        }
        PendingIntent broadcastIntent = getBroadcastIntent(this.mContext);
        int colorAttrDefaultColor =
                Utils.getColorAttrDefaultColor(this.mContext, R.attr.colorAccent);
        boolean alwaysOnEnabled = this.mConfig.alwaysOnEnabled(i);
        ListBuilder listBuilder =
                new ListBuilder(this.mContext, CustomSliceRegistry.ALWAYS_ON_SLICE_URI);
        listBuilder.mImpl.setColor(colorAttrDefaultColor);
        listBuilder.mImpl.setKeywords(
                (Set)
                        Arrays.stream(
                                        TextUtils.split(
                                                this.mContext.getString(
                                                        com.android.settings.R.string
                                                                .keywords_always_show_time_info),
                                                ","))
                                .map(new AlwaysOnDisplaySlice$$ExternalSyntheticLambda0())
                                .collect(Collectors.toSet()));
        ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
        rowBuilder.mTitle =
                this.mContext.getText(com.android.settings.R.string.doze_always_on_title);
        rowBuilder.mTitleLoading = false;
        rowBuilder.mSubtitle =
                this.mContext.getText(com.android.settings.R.string.doze_always_on_summary);
        rowBuilder.mSubtitleLoading = false;
        rowBuilder.mPrimaryAction = new SliceAction(broadcastIntent, null, alwaysOnEnabled);
        listBuilder.mImpl.addRow(rowBuilder);
        return listBuilder.build();
    }

    @Override // com.android.settings.slices.Sliceable
    public final int getSliceHighlightMenuRes() {
        return com.android.settings.R.string.menu_key_display;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Uri getUri$1() {
        return CustomSliceRegistry.ALWAYS_ON_SLICE_URI;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final void onNotifyChange(Intent intent) {
        boolean booleanExtra =
                intent.getBooleanExtra("android.app.slice.extra.TOGGLE_STATE", false);
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Settings.Secure.putInt(contentResolver, "doze_always_on", booleanExtra ? 1 : 0);
        Settings.Secure.putInt(contentResolver, "doze_wake_display_gesture", 0);
    }
}
