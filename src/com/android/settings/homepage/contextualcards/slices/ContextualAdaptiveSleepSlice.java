package com.android.settings.homepage.contextualcards.slices;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;

import com.android.settings.R;
import com.android.settings.SubSettings;
import com.android.settings.display.AdaptiveSleepPreferenceController;
import com.android.settings.display.ScreenTimeoutPreferenceController;
import com.android.settings.display.ScreenTimeoutSettings;
import com.android.settings.slices.CustomSliceRegistry;
import com.android.settings.slices.CustomSliceable;
import com.android.settings.slices.SliceBuilderUtils;

import com.sec.ims.volte2.data.VolteConstants;

import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ContextualAdaptiveSleepSlice implements CustomSliceable {
    static final long DEFERRED_TIME_DAYS = TimeUnit.DAYS.toMillis(14);
    static final String PREF_KEY_SETUP_TIME = "adaptive_sleep_setup_time";
    public final Context mContext;

    public ContextualAdaptiveSleepSlice(Context context) {
        this.mContext = context;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Intent getIntent() {
        CharSequence text = this.mContext.getText(R.string.adaptive_sleep_title);
        return SliceBuilderUtils.buildSearchResultPageIntent(
                        VolteConstants.ErrorCode.RTP_TIME_OUT,
                        R.string.menu_key_display,
                        this.mContext,
                        ScreenTimeoutSettings.class.getName(),
                        ScreenTimeoutPreferenceController.PREF_NAME,
                        text.toString())
                .setClassName(this.mContext.getPackageName(), SubSettings.class.getName())
                .setData(
                        new Uri.Builder()
                                .appendPath(ScreenTimeoutPreferenceController.PREF_NAME)
                                .build());
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Slice getSlice() {
        if (this.mContext
                        .getSharedPreferences("adaptive_sleep_slice", 0)
                        .getLong(PREF_KEY_SETUP_TIME, 0L)
                == 0) {
            this.mContext
                    .getSharedPreferences("adaptive_sleep_slice", 0)
                    .edit()
                    .putLong(PREF_KEY_SETUP_TIME, System.currentTimeMillis())
                    .apply();
            return null;
        }
        if (isSettingsAvailable()
                && !this.mContext
                        .getSharedPreferences("adaptive_sleep_slice", 0)
                        .getBoolean("adaptive_sleep_interacted", false)) {
            if (this.mContext
                                    .getSharedPreferences("adaptive_sleep_slice", 0)
                                    .getLong(PREF_KEY_SETUP_TIME, 0L)
                            <= System.currentTimeMillis() - DEFERRED_TIME_DAYS
                    && Settings.Secure.getInt(
                                    this.mContext.getContentResolver(), "adaptive_sleep", 0)
                            == 0) {
                IconCompat createWithResource =
                        IconCompat.createWithResource(
                                this.mContext, R.drawable.ic_settings_adaptive_sleep);
                CharSequence text =
                        this.mContext.getText(R.string.adaptive_sleep_contextual_slice_title);
                CharSequence text2 =
                        this.mContext.getText(R.string.adaptive_sleep_contextual_slice_summary);
                SliceAction createDeeplink =
                        SliceAction.createDeeplink(
                                PendingIntent.getActivity(this.mContext, 0, getIntent(), 67108864),
                                createWithResource,
                                0,
                                text);
                ListBuilder listBuilder =
                        new ListBuilder(
                                this.mContext, CustomSliceRegistry.CONTEXTUAL_ADAPTIVE_SLEEP_URI);
                listBuilder.mImpl.setColor(-1);
                ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
                rowBuilder.setTitleItem(createWithResource);
                rowBuilder.mTitle = text;
                rowBuilder.mTitleLoading = false;
                rowBuilder.mSubtitle = text2;
                rowBuilder.mSubtitleLoading = false;
                rowBuilder.mPrimaryAction = createDeeplink;
                listBuilder.mImpl.addRow(rowBuilder);
                return listBuilder.build();
            }
        }
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public final int getSliceHighlightMenuRes() {
        return R.string.menu_key_display;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Uri getUri$1() {
        return CustomSliceRegistry.CONTEXTUAL_ADAPTIVE_SLEEP_URI;
    }

    public boolean isSettingsAvailable() {
        return AdaptiveSleepPreferenceController.isAdaptiveSleepSupported(this.mContext);
    }
}
