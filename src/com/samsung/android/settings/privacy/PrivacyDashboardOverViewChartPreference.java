package com.samsung.android.settings.privacy;

import android.content.ActivityNotFoundException;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.privacydashboard.PermissionAccessInformationRequester;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.ArrayList;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PrivacyDashboardOverViewChartPreference extends LayoutPreference {
    public static final Uri PROVIDER_URI =
            Uri.parse("content://com.samsung.android.privacydashboard.provider");
    public ArrayList mCameraAppUids;
    public ArrayList mCameraApps;
    public int mCameraUsageCount;
    public final Context mContext;
    public int mDuration;
    public ArrayList mLocationAppUids;
    public ArrayList mLocationApps;
    public int mLocationUsageCount;
    public ArrayList mMicrophoneAppUids;
    public ArrayList mMicrophoneApps;
    public int mMicrophoneUsageCount;
    public PermissionInfoTask mPermissionInfoTask;
    public final int[] mPrivacyOverViewPermission;
    public final String[] mPrivacyOverViewPermissionId;
    public boolean mUpdateFlag;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PermissionInfoTask extends AsyncTask {
        public PermissionInfoTask() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            try {
                PermissionAccessInformationRequester.request(
                        PrivacyDashboardOverViewChartPreference.this.mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
            PrivacyDashboardOverViewChartPreference privacyDashboardOverViewChartPreference =
                    PrivacyDashboardOverViewChartPreference.this;
            ContentProviderClient acquireUnstableContentProviderClient =
                    privacyDashboardOverViewChartPreference
                            .mContext
                            .getContentResolver()
                            .acquireUnstableContentProviderClient(
                                    PrivacyDashboardOverViewChartPreference.PROVIDER_URI);
            if (acquireUnstableContentProviderClient != null) {
                try {
                    try {
                        Bundle call =
                                acquireUnstableContentProviderClient.call("get_count", null, null);
                        privacyDashboardOverViewChartPreference.mCameraUsageCount =
                                call.getInt("CAMERA_CNT", 0);
                        privacyDashboardOverViewChartPreference.mLocationUsageCount =
                                call.getInt("LOCATION_CNT", 0);
                        privacyDashboardOverViewChartPreference.mMicrophoneUsageCount =
                                call.getInt("MICROPHONE_CNT", 0);
                        privacyDashboardOverViewChartPreference.mCameraApps =
                                call.getStringArrayList("CAMERA_PACKAGES");
                        privacyDashboardOverViewChartPreference.mLocationApps =
                                call.getStringArrayList("LOCATION_PACKAGES");
                        privacyDashboardOverViewChartPreference.mMicrophoneApps =
                                call.getStringArrayList("MICROPHONE_PACKAGES");
                        privacyDashboardOverViewChartPreference.mCameraAppUids =
                                call.getIntegerArrayList("CAMERA_PACKAGE_UIDS");
                        privacyDashboardOverViewChartPreference.mLocationAppUids =
                                call.getIntegerArrayList("LOCATION_PACKAGE_UIDS");
                        privacyDashboardOverViewChartPreference.mMicrophoneAppUids =
                                call.getIntegerArrayList("MICROPHONE_PACKAGE_UIDS");
                        privacyDashboardOverViewChartPreference.mDuration =
                                call.getInt("DURATION", 86400000);
                        Math.max(
                                privacyDashboardOverViewChartPreference.mMicrophoneUsageCount,
                                Math.max(
                                        privacyDashboardOverViewChartPreference.mCameraUsageCount,
                                        privacyDashboardOverViewChartPreference
                                                .mLocationUsageCount));
                    } finally {
                        acquireUnstableContentProviderClient.close();
                    }
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
            }
            return null;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            PrivacyDashboardOverViewChartPreference privacyDashboardOverViewChartPreference =
                    PrivacyDashboardOverViewChartPreference.this;
            privacyDashboardOverViewChartPreference.mUpdateFlag = true;
            ((TextView)
                            privacyDashboardOverViewChartPreference.mRootView.findViewById(
                                    R.id.used_duration))
                    .setText(
                            privacyDashboardOverViewChartPreference.mDuration == 86400000
                                    ? R.string.permission_usage_history_category_24hours
                                    : R.string.permission_usage_history_category_7days);
            PrivacyDashboardOverViewChartPreference.this.updateAppIcons();
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            PrivacyDashboardOverViewChartPreference.this.mUpdateFlag = false;
        }
    }

    /* renamed from: -$$Nest$mcallPermissionUsageDetail, reason: not valid java name */
    public static void m1266$$Nest$mcallPermissionUsageDetail(
            PrivacyDashboardOverViewChartPreference privacyDashboardOverViewChartPreference,
            int i) {
        privacyDashboardOverViewChartPreference.getClass();
        try {
            Intent intent =
                    new Intent("com.samsung.android.intent.action.REVIEW_PERMISSION_USAGE_DETAIL");
            intent.putExtra(
                    "PERMISSION_GROUP_ID",
                    privacyDashboardOverViewChartPreference.mPrivacyOverViewPermissionId[i]);
            intent.putExtra(
                    "START_TIME",
                    System.currentTimeMillis() - privacyDashboardOverViewChartPreference.mDuration);
            intent.putExtra("END_TIME", System.currentTimeMillis());
            intent.putExtra("INCLUDE_SYSTEM_APP", false);
            privacyDashboardOverViewChartPreference.mContext.startActivity(intent);
            String str = privacyDashboardOverViewChartPreference.mPrivacyOverViewPermissionId[i];
            if (TextUtils.equals(str, "android.permission-group.CAMERA")) {
                LoggingHelper.insertEventLogging(9032, 59100);
            } else if (TextUtils.equals(str, "android.permission-group.MICROPHONE")) {
                LoggingHelper.insertEventLogging(9032, 59101);
            } else if (TextUtils.equals(str, "android.permission-group.LOCATION")) {
                LoggingHelper.insertEventLogging(9032, 59102);
            }
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public PrivacyDashboardOverViewChartPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCameraApps = new ArrayList();
        this.mLocationApps = new ArrayList();
        this.mMicrophoneApps = new ArrayList();
        this.mCameraAppUids = new ArrayList();
        this.mLocationAppUids = new ArrayList();
        this.mMicrophoneAppUids = new ArrayList();
        this.mPrivacyOverViewPermission =
                new int[] {
                    R.string.permission_camera,
                    R.string.permission_microphone,
                    R.string.permission_location
                };
        int[] iArr = {
            R.string.permission_camera_china,
            R.string.permission_microphone_china,
            R.string.permission_location_china
        };
        this.mPrivacyOverViewPermissionId =
                new String[] {
                    "android.permission-group.CAMERA",
                    "android.permission-group.MICROPHONE",
                    "android.permission-group.LOCATION"
                };
        this.mContext = context;
        if (SecurityDashboardUtils.isChinaModel()) {
            this.mPrivacyOverViewPermission = iArr;
        }
        setSelectable(false);
    }

    public final Drawable getBadgeIcon(int i, String str) {
        try {
            return this.mContext
                    .getPackageManager()
                    .getUserBadgedIcon(
                            this.mContext.getPackageManager().getApplicationIcon(str),
                            UserHandle.getUserHandleForUid(i));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("PrivacyDashboardOverViewChartPreference", e.getMessage(), e);
            return null;
        }
    }

    @Override // com.android.settingslib.widget.LayoutPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        final int i = 0;
        this.mRootView
                .findViewById(R.id.more_icon)
                .setOnClickListener(
                        new View.OnClickListener(this) { // from class:
                            // com.samsung.android.settings.privacy.PrivacyDashboardOverViewChartPreference.1
                            public final /* synthetic */ PrivacyDashboardOverViewChartPreference
                                    this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i) {
                                    case 0:
                                        try {
                                            this.this$0.mContext.startActivity(
                                                    new Intent(
                                                            "com.samsung.android.intent.action.REVIEW_PERMISSION_USAGE"));
                                            LoggingHelper.insertEventLogging(9032, 59103);
                                            break;
                                        } catch (ActivityNotFoundException e) {
                                            e.printStackTrace();
                                            return;
                                        }
                                    case 1:
                                        PrivacyDashboardOverViewChartPreference
                                                .m1266$$Nest$mcallPermissionUsageDetail(
                                                        this.this$0, 0);
                                        break;
                                    case 2:
                                        PrivacyDashboardOverViewChartPreference
                                                .m1266$$Nest$mcallPermissionUsageDetail(
                                                        this.this$0, 1);
                                        break;
                                    default:
                                        PrivacyDashboardOverViewChartPreference
                                                .m1266$$Nest$mcallPermissionUsageDetail(
                                                        this.this$0, 2);
                                        break;
                                }
                            }
                        });
        if (Utils.isRTL(this.mContext)) {
            ((ImageView) this.mRootView.findViewById(R.id.more_image_view)).setRotation(180.0f);
        }
        final int i2 = 1;
        this.mRootView
                .findViewById(R.id.camera_icon_area)
                .setOnClickListener(
                        new View.OnClickListener(this) { // from class:
                            // com.samsung.android.settings.privacy.PrivacyDashboardOverViewChartPreference.1
                            public final /* synthetic */ PrivacyDashboardOverViewChartPreference
                                    this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i2) {
                                    case 0:
                                        try {
                                            this.this$0.mContext.startActivity(
                                                    new Intent(
                                                            "com.samsung.android.intent.action.REVIEW_PERMISSION_USAGE"));
                                            LoggingHelper.insertEventLogging(9032, 59103);
                                            break;
                                        } catch (ActivityNotFoundException e) {
                                            e.printStackTrace();
                                            return;
                                        }
                                    case 1:
                                        PrivacyDashboardOverViewChartPreference
                                                .m1266$$Nest$mcallPermissionUsageDetail(
                                                        this.this$0, 0);
                                        break;
                                    case 2:
                                        PrivacyDashboardOverViewChartPreference
                                                .m1266$$Nest$mcallPermissionUsageDetail(
                                                        this.this$0, 1);
                                        break;
                                    default:
                                        PrivacyDashboardOverViewChartPreference
                                                .m1266$$Nest$mcallPermissionUsageDetail(
                                                        this.this$0, 2);
                                        break;
                                }
                            }
                        });
        final int i3 = 2;
        this.mRootView
                .findViewById(R.id.microphone_icon_area)
                .setOnClickListener(
                        new View.OnClickListener(this) { // from class:
                            // com.samsung.android.settings.privacy.PrivacyDashboardOverViewChartPreference.1
                            public final /* synthetic */ PrivacyDashboardOverViewChartPreference
                                    this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i3) {
                                    case 0:
                                        try {
                                            this.this$0.mContext.startActivity(
                                                    new Intent(
                                                            "com.samsung.android.intent.action.REVIEW_PERMISSION_USAGE"));
                                            LoggingHelper.insertEventLogging(9032, 59103);
                                            break;
                                        } catch (ActivityNotFoundException e) {
                                            e.printStackTrace();
                                            return;
                                        }
                                    case 1:
                                        PrivacyDashboardOverViewChartPreference
                                                .m1266$$Nest$mcallPermissionUsageDetail(
                                                        this.this$0, 0);
                                        break;
                                    case 2:
                                        PrivacyDashboardOverViewChartPreference
                                                .m1266$$Nest$mcallPermissionUsageDetail(
                                                        this.this$0, 1);
                                        break;
                                    default:
                                        PrivacyDashboardOverViewChartPreference
                                                .m1266$$Nest$mcallPermissionUsageDetail(
                                                        this.this$0, 2);
                                        break;
                                }
                            }
                        });
        final int i4 = 3;
        this.mRootView
                .findViewById(R.id.location_icon_area)
                .setOnClickListener(
                        new View.OnClickListener(this) { // from class:
                            // com.samsung.android.settings.privacy.PrivacyDashboardOverViewChartPreference.1
                            public final /* synthetic */ PrivacyDashboardOverViewChartPreference
                                    this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i4) {
                                    case 0:
                                        try {
                                            this.this$0.mContext.startActivity(
                                                    new Intent(
                                                            "com.samsung.android.intent.action.REVIEW_PERMISSION_USAGE"));
                                            LoggingHelper.insertEventLogging(9032, 59103);
                                            break;
                                        } catch (ActivityNotFoundException e) {
                                            e.printStackTrace();
                                            return;
                                        }
                                    case 1:
                                        PrivacyDashboardOverViewChartPreference
                                                .m1266$$Nest$mcallPermissionUsageDetail(
                                                        this.this$0, 0);
                                        break;
                                    case 2:
                                        PrivacyDashboardOverViewChartPreference
                                                .m1266$$Nest$mcallPermissionUsageDetail(
                                                        this.this$0, 1);
                                        break;
                                    default:
                                        PrivacyDashboardOverViewChartPreference
                                                .m1266$$Nest$mcallPermissionUsageDetail(
                                                        this.this$0, 2);
                                        break;
                                }
                            }
                        });
        ((TextView) this.mRootView.findViewById(R.id.camera_label))
                .setText(this.mPrivacyOverViewPermission[0]);
        ((TextView) this.mRootView.findViewById(R.id.microphone_label))
                .setText(this.mPrivacyOverViewPermission[1]);
        ((TextView) this.mRootView.findViewById(R.id.location_label))
                .setText(this.mPrivacyOverViewPermission[2]);
        updateAppIcons();
    }

    public final void setNumberContentDesciption(View view, int i, int i2) {
        view.setContentDescription(
                this.mContext
                        .getResources()
                        .getQuantityString(
                                R.plurals.ps_apps,
                                i2,
                                this.mContext.getResources().getString(i),
                                Integer.valueOf(i2)));
    }

    public final void setUsedAppIcons(ArrayList arrayList, ArrayList arrayList2, View view) {
        try {
            int size = arrayList.size();
            if (size == 0) {
                view.findViewById(R.id.first_app_icon).setVisibility(8);
                view.findViewById(R.id.number_area).setVisibility(8);
                view.findViewById(R.id.second_icon_area).setVisibility(8);
                view.findViewById(R.id.third_icon_area).setVisibility(8);
                view.findViewById(R.id.fourth_icon_area).setVisibility(8);
                view.findViewById(R.id.empty_text).setVisibility(0);
            } else if (size == 1) {
                view.findViewById(R.id.number_area).setVisibility(8);
                view.findViewById(R.id.first_app_icon).setVisibility(0);
                view.findViewById(R.id.second_icon_area).setVisibility(8);
                view.findViewById(R.id.third_icon_area).setVisibility(8);
                view.findViewById(R.id.fourth_icon_area).setVisibility(8);
                ((ImageView) view.findViewById(R.id.first_app_icon))
                        .setImageDrawable(
                                getBadgeIcon(
                                        ((Integer) arrayList2.get(0)).intValue(),
                                        (String) arrayList.get(0)));
                view.findViewById(R.id.empty_text).setVisibility(8);
            } else if (size == 2) {
                view.findViewById(R.id.number_area).setVisibility(8);
                view.findViewById(R.id.first_app_icon).setVisibility(0);
                view.findViewById(R.id.second_icon_area).setVisibility(0);
                view.findViewById(R.id.third_icon_area).setVisibility(8);
                view.findViewById(R.id.fourth_icon_area).setVisibility(8);
                ((ImageView) view.findViewById(R.id.first_app_icon))
                        .setImageDrawable(
                                getBadgeIcon(
                                        ((Integer) arrayList2.get(1)).intValue(),
                                        (String) arrayList.get(1)));
                ((ImageView) view.findViewById(R.id.second_app_icon))
                        .setImageDrawable(
                                getBadgeIcon(
                                        ((Integer) arrayList2.get(0)).intValue(),
                                        (String) arrayList.get(0)));
                view.findViewById(R.id.empty_text).setVisibility(8);
            } else if (size == 3) {
                view.findViewById(R.id.number_area).setVisibility(8);
                view.findViewById(R.id.first_app_icon).setVisibility(0);
                view.findViewById(R.id.second_icon_area).setVisibility(0);
                view.findViewById(R.id.third_icon_area).setVisibility(0);
                view.findViewById(R.id.fourth_icon_area).setVisibility(8);
                ((ImageView) view.findViewById(R.id.first_app_icon))
                        .setImageDrawable(
                                getBadgeIcon(
                                        ((Integer) arrayList2.get(2)).intValue(),
                                        (String) arrayList.get(2)));
                ((ImageView) view.findViewById(R.id.second_app_icon))
                        .setImageDrawable(
                                getBadgeIcon(
                                        ((Integer) arrayList2.get(1)).intValue(),
                                        (String) arrayList.get(1)));
                ((ImageView) view.findViewById(R.id.third_app_icon))
                        .setImageDrawable(
                                getBadgeIcon(
                                        ((Integer) arrayList2.get(0)).intValue(),
                                        (String) arrayList.get(0)));
                view.findViewById(R.id.empty_text).setVisibility(8);
            } else if (size != 4) {
                view.findViewById(R.id.first_app_icon).setVisibility(8);
                view.findViewById(R.id.number_area).setVisibility(0);
                view.findViewById(R.id.second_icon_area).setVisibility(0);
                view.findViewById(R.id.third_icon_area).setVisibility(0);
                view.findViewById(R.id.fourth_icon_area).setVisibility(0);
                ((TextView) view.findViewById(R.id.app_number))
                        .setText(
                                "+"
                                        .concat(
                                                String.format(
                                                        Locale.getDefault(),
                                                        "%d",
                                                        Integer.valueOf(arrayList.size() - 3))));
                ((ImageView) view.findViewById(R.id.second_app_icon))
                        .setImageDrawable(
                                getBadgeIcon(
                                        ((Integer) arrayList2.get(2)).intValue(),
                                        (String) arrayList.get(2)));
                ((ImageView) view.findViewById(R.id.third_app_icon))
                        .setImageDrawable(
                                getBadgeIcon(
                                        ((Integer) arrayList2.get(1)).intValue(),
                                        (String) arrayList.get(1)));
                ((ImageView) view.findViewById(R.id.fourth_app_icon))
                        .setImageDrawable(
                                getBadgeIcon(
                                        ((Integer) arrayList2.get(0)).intValue(),
                                        (String) arrayList.get(0)));
                view.findViewById(R.id.empty_text).setVisibility(8);
            } else {
                view.findViewById(R.id.number_area).setVisibility(8);
                view.findViewById(R.id.first_app_icon).setVisibility(0);
                view.findViewById(R.id.second_icon_area).setVisibility(0);
                view.findViewById(R.id.third_icon_area).setVisibility(0);
                view.findViewById(R.id.fourth_icon_area).setVisibility(0);
                ((ImageView) view.findViewById(R.id.first_app_icon))
                        .setImageDrawable(
                                getBadgeIcon(
                                        ((Integer) arrayList2.get(3)).intValue(),
                                        (String) arrayList.get(3)));
                ((ImageView) view.findViewById(R.id.second_app_icon))
                        .setImageDrawable(
                                getBadgeIcon(
                                        ((Integer) arrayList2.get(2)).intValue(),
                                        (String) arrayList.get(2)));
                ((ImageView) view.findViewById(R.id.third_app_icon))
                        .setImageDrawable(
                                getBadgeIcon(
                                        ((Integer) arrayList2.get(1)).intValue(),
                                        (String) arrayList.get(1)));
                ((ImageView) view.findViewById(R.id.fourth_app_icon))
                        .setImageDrawable(
                                getBadgeIcon(
                                        ((Integer) arrayList2.get(0)).intValue(),
                                        (String) arrayList.get(0)));
                view.findViewById(R.id.empty_text).setVisibility(8);
            }
        } catch (IndexOutOfBoundsException unused) {
            Log.w("PrivacyDashboardOverViewChartPreference", "indexOutOfBoundsException");
        }
    }

    public final void updateAppIcons() {
        setUsedAppIcons(
                this.mCameraApps,
                this.mCameraAppUids,
                this.mRootView.findViewById(R.id.camera_permission_apps));
        setUsedAppIcons(
                this.mLocationApps,
                this.mLocationAppUids,
                this.mRootView.findViewById(R.id.location_permission_apps));
        setUsedAppIcons(
                this.mMicrophoneApps,
                this.mMicrophoneAppUids,
                this.mRootView.findViewById(R.id.microphone_permission_apps));
        setNumberContentDesciption(
                this.mRootView.findViewById(R.id.camera_icon_area),
                this.mPrivacyOverViewPermission[0],
                this.mCameraAppUids.size());
        setNumberContentDesciption(
                this.mRootView.findViewById(R.id.microphone_icon_area),
                this.mPrivacyOverViewPermission[1],
                this.mMicrophoneApps.size());
        setNumberContentDesciption(
                this.mRootView.findViewById(R.id.location_icon_area),
                this.mPrivacyOverViewPermission[2],
                this.mLocationApps.size());
    }
}
