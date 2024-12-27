package com.android.settings.notification.zen;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.UserHandle;

import androidx.slice.Slice;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;

import com.android.settings.R;
import com.android.settings.SubSettings;
import com.android.settings.slices.CustomSliceRegistry;
import com.android.settings.slices.SliceBroadcastReceiver;
import com.android.settings.slices.SliceBuilderUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ZenModeSliceBuilder {
    public static final IntentFilter INTENT_FILTER;

    static {
        IntentFilter intentFilter = new IntentFilter();
        INTENT_FILTER = intentFilter;
        intentFilter.addAction("android.app.action.NOTIFICATION_POLICY_CHANGED");
        intentFilter.addAction("android.app.action.INTERRUPTION_FILTER_CHANGED");
        intentFilter.addAction("android.app.action.INTERRUPTION_FILTER_CHANGED_INTERNAL");
    }

    public static Intent getIntent(Context context) {
        return SliceBuilderUtils.buildSearchResultPageIntent(
                        76,
                        R.string.menu_key_notifications,
                        context,
                        ZenModeSettings.class.getName(),
                        "zen_mode_toggle",
                        context.getText(R.string.zen_mode_settings_title).toString())
                .setClassName(context.getPackageName(), SubSettings.class.getName())
                .setData(new Uri.Builder().appendPath("zen_mode_toggle").build());
    }

    public static Slice getSlice(Context context) {
        int zenMode =
                ((NotificationManager) context.getSystemService(NotificationManager.class))
                        .getZenMode();
        boolean z = true;
        if (zenMode != 1 && zenMode != 2 && zenMode != 3) {
            z = false;
        }
        CharSequence text = context.getText(R.string.zen_mode_settings_title);
        CharSequence text2 = context.getText(R.string.zen_mode_slice_subtitle);
        int colorAttrDefaultColor =
                Utils.getColorAttrDefaultColor(context, android.R.attr.colorAccent);
        PendingIntent broadcast =
                PendingIntent.getBroadcast(
                        context,
                        0,
                        new Intent("com.android.settings.notification.ZEN_MODE_CHANGED")
                                .setClass(context, SliceBroadcastReceiver.class),
                        301989888);
        SliceAction createDeeplink =
                SliceAction.createDeeplink(
                        PendingIntent.getActivity(context, 0, getIntent(context), 67108864),
                        null,
                        0,
                        text);
        SliceAction sliceAction = new SliceAction(broadcast, null, z);
        ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
        rowBuilder.mTitle = text;
        rowBuilder.mTitleLoading = false;
        rowBuilder.mPrimaryAction = createDeeplink;
        if (RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        context, UserHandle.myUserId(), "no_adjust_volume")
                == null) {
            rowBuilder.addEndItem(sliceAction);
        }
        if (!com.android.settings.Utils.isSettingsIntelligence(context)) {
            rowBuilder.setSubtitle(text2);
        }
        ListBuilder listBuilder = new ListBuilder(context, CustomSliceRegistry.ZEN_MODE_SLICE_URI);
        listBuilder.mImpl.setColor(colorAttrDefaultColor);
        listBuilder.mImpl.addRow(rowBuilder);
        return listBuilder.build();
    }
}
