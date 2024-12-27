package com.samsung.android.settings.deviceinfo.aboutphone;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.deviceinfo.aboutphone.deviceimage.DeviceImageFileUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DeviceInfoHeader {
    public static final String sSalesCode = Utils.getSalesCode();
    public final Context mContext;
    public TextView mDeviceName;
    public Button mDeviceNameEdit;
    public final LayoutInflater mInflater;
    public final ContentResolver mResolver;
    public final View mView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    enum PhoneNumberSource {
        /* JADX INFO: Fake field, exist only in values array */
        CARRIER(2),
        /* JADX INFO: Fake field, exist only in values array */
        UICC(1),
        /* JADX INFO: Fake field, exist only in values array */
        IMS(3);

        private final int value;

        PhoneNumberSource(int i) {
            this.value = i;
        }

        public final int getValue() {
            return this.value;
        }
    }

    public DeviceInfoHeader(Context context) {
        this.mContext = context;
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService("layout_inflater");
        this.mInflater = layoutInflater;
        this.mView =
                layoutInflater.inflate(R.layout.sec_device_info_settings_header, (ViewGroup) null);
        this.mResolver = context.getContentResolver();
    }

    public final String getDeviceName() {
        String string =
                Settings.Global.getString(this.mContext.getContentResolver(), "device_name");
        if (!Utils.isGuestMode(this.mContext) || string != null) {
            return string;
        }
        String string2 =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_SETTINGS_CONFIG_BRAND_NAME");
        if (TextUtils.isEmpty(string2)) {
            return string;
        }
        Settings.System.putString(this.mContext.getContentResolver(), "device_name", string2);
        return string2;
    }

    public final void initDeviceImage() {
        View findViewById = this.mView.findViewById(R.id.device_name_space);
        ImageView imageView = (ImageView) this.mView.findViewById(R.id.device_image);
        if (DeviceImageFileUtils.isImageFileExist(this.mContext)) {
            Log.d("DeviceInfoHeader", "set DeviceImage");
            imageView.setImageBitmap(
                    BitmapFactory.decodeFile(DeviceImageFileUtils.getImageFilePath(this.mContext)));
            findViewById.setVisibility(8);
            imageView.setVisibility(0);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:169:0x036f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:204:0x05e5  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x05fc  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x061f  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x05fe  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x05ee A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0138  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0202  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initInfoChart() {
        /*
            Method dump skipped, instructions count: 1794
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.deviceinfo.aboutphone.DeviceInfoHeader.initInfoChart():void");
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DeviceInfo {
        public final String mContent;
        public final boolean mIsCopyable;
        public final boolean mIsSIMSlotCategory;
        public final int mSIMIconResId;
        public final String mTitle;

        public DeviceInfo(String str, String str2) {
            this.mIsCopyable = false;
            this.mIsSIMSlotCategory = false;
            this.mTitle = str;
            this.mContent = str2;
        }

        public DeviceInfo(int i, String str, String str2) {
            this.mIsSIMSlotCategory = false;
            this.mTitle = str;
            this.mContent = str2;
            this.mIsCopyable = true;
        }

        public DeviceInfo(String str, int i) {
            this.mIsCopyable = false;
            this.mTitle = str;
            this.mContent = null;
            this.mIsSIMSlotCategory = true;
            this.mSIMIconResId = i;
        }
    }
}
