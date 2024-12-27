package com.samsung.android.settings.asbase.vibration;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;

import com.android.settings.Utils;

import com.sec.ims.im.ImIntent;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class VibPickerHelper {
    public final String mCaller;
    public final String mContactPattern;
    public final Uri mContactSound;
    public final Context mContext;
    public final boolean mHasDefaultItem;
    public final boolean mIsFromContact;
    public final boolean mIsSecondSim;
    public final String mSoundPickerUri;
    public final int mType;
    public final IVibPickerType mVibType;

    public VibPickerHelper(int i, Context context, Intent intent) {
        this.mContext = context;
        int intExtra = intent.getIntExtra("vibration_type", 0);
        this.mType = intExtra;
        boolean booleanExtra =
                intent.getBooleanExtra("android.intent.extra.pattern.FROM_CONTACT", false);
        this.mIsFromContact = booleanExtra;
        boolean z = i == 1;
        this.mIsSecondSim = z;
        if (z) {
            this.mSoundPickerUri = intent.getStringExtra("picked_ringtone_uri_2");
        } else {
            this.mSoundPickerUri = intent.getStringExtra("picked_ringtone_uri");
        }
        this.mHasDefaultItem = intent.getBooleanExtra("show_default", false);
        this.mContactSound =
                (Uri) intent.getParcelableExtra("android.intent.extra.ringtone.EXISTING_URI");
        this.mContactPattern = intent.getStringExtra("android.intent.extra.pattern.EXISTING_URI");
        this.mCaller = intent.getStringExtra(ImIntent.Extras.EXTRA_FROM);
        if (booleanExtra) {
            this.mCaller = "android.intent.extra.pattern.FROM_CONTACT";
        } else {
            StringBuilder sb = Utils.sBuilder;
            if (!TextUtils.isEmpty(
                    Settings.System.getString(
                            context.getContentResolver(), "current_sec_active_themepackage"))) {
                this.mCaller = null;
            }
        }
        this.mVibType =
                intExtra == 0 ? new VibPickerRingtoneType() : new VibPickerNotificationType();
    }
}
