package com.samsung.android.settings.lockscreen;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.notification.NotificationBackend;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.notification.brief.policy.BriefPopupPolicyManager;
import com.samsung.android.settings.notification.brief.policy.PolicyInfo;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ShowNotificationContentPreference extends Preference {
    public final AnonymousClass3 mAppNameComparator;
    public final NotificationBackend mBackend;
    public final Context mContext;
    public Boolean mIsAllAppChecked;
    public final AnonymousClass1 mSettingsButtonClickListener;
    public RadioButton mShowContentBtn;
    public LinearLayout mShowContentContainer;
    public View mShowContentSettingsBtn;
    public Drawable mShowContentSettingsBtnBg;
    public TextView mShowContentSummary;
    public String mSummaryText;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class GetAppListAsyncTask extends AsyncTask {
        public GetAppListAsyncTask() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            final ShowNotificationContentPreference showNotificationContentPreference =
                    ShowNotificationContentPreference.this;
            final PackageManager packageManager =
                    showNotificationContentPreference.getContext().getPackageManager();
            Context context = showNotificationContentPreference.getContext();
            BriefPopupPolicyManager briefPopupPolicyManager =
                    BriefPopupPolicyManager.getInstance(context);
            final HashMap hashMap =
                    (briefPopupPolicyManager.mPolicyType & 4) != 0
                            ? (HashMap) briefPopupPolicyManager.mPolicyInfoData.get(2)
                            : null;
            final HashMap hashMap2 = (HashMap) briefPopupPolicyManager.mPolicyInfoData.get(10);
            final ArrayList arrayList = new ArrayList();
            NotificationBackend notificationBackend = showNotificationContentPreference.mBackend;
            int userId = context.getUserId();
            notificationBackend.getClass();
            List notificationPackagesList =
                    NotificationBackend.getNotificationPackagesList(context, userId);
            ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(4);
            ArrayList arrayList2 = new ArrayList();
            Iterator it = ((ArrayList) notificationPackagesList).iterator();
            while (it.hasNext()) {
                final ResolveInfo resolveInfo = (ResolveInfo) it.next();
                arrayList2.add(
                        new Callable() { // from class:
                                         // com.samsung.android.settings.lockscreen.ShowNotificationContentPreference.2
                            @Override // java.util.concurrent.Callable
                            public final Object call() {
                                String str;
                                ActivityInfo activityInfo = resolveInfo.activityInfo;
                                String str2 = activityInfo.name;
                                String str3 = activityInfo.packageName;
                                HashMap hashMap3 = hashMap;
                                if (hashMap3 == null || !hashMap3.containsKey(str3)) {
                                    ComponentName componentName = new ComponentName(str3, str2);
                                    String flattenToString = componentName.flattenToString();
                                    if (flattenToString != null) {
                                        componentName =
                                                ComponentName.unflattenFromString(flattenToString);
                                    }
                                    boolean z = false;
                                    try {
                                        str =
                                                packageManager
                                                        .getActivityInfo(componentName, 0)
                                                        .loadLabel(packageManager)
                                                        .toString();
                                    } catch (PackageManager.NameNotFoundException e) {
                                        e.printStackTrace();
                                        str = null;
                                    }
                                    if (str == null || str3 == null) {
                                        StringBuilder sb = new StringBuilder("Can't get the ");
                                        if (str == null) {
                                            sb.append("appName ");
                                        }
                                        if (str3 == null) {
                                            sb.append("packageName ");
                                        }
                                        Log.e("ShowNotificationContentPreference", sb.toString());
                                    } else {
                                        HashMap hashMap4 = hashMap2;
                                        PolicyInfo policyInfo =
                                                hashMap4 != null
                                                        ? (PolicyInfo) hashMap4.get(str3)
                                                        : null;
                                        int i = policyInfo != null ? policyInfo.priority : 0;
                                        ShowNotificationContentPreference
                                                showNotificationContentPreference2 =
                                                        ShowNotificationContentPreference.this;
                                        try {
                                            int packageUid =
                                                    showNotificationContentPreference2
                                                            .getContext()
                                                            .getPackageManager()
                                                            .getPackageUid(str3, 0);
                                            showNotificationContentPreference2.mBackend.getClass();
                                            int lockScreenNotificationVisibilityForPackage =
                                                    NotificationBackend
                                                            .getLockScreenNotificationVisibilityForPackage(
                                                                    packageUid, str3);
                                            if (lockScreenNotificationVisibilityForPackage == 1
                                                    || lockScreenNotificationVisibilityForPackage
                                                            == -1000) {
                                                z = true;
                                            }
                                        } catch (PackageManager.NameNotFoundException unused) {
                                            Log.e(
                                                    "ShowNotificationContentPreference",
                                                    "NameNotFound - ".concat(str3));
                                        } catch (Exception e2) {
                                            Log.e(
                                                    "ShowNotificationContentPreference",
                                                    e2.getStackTrace()[0].toString());
                                        }
                                        boolean z2 = z;
                                        if (z2) {
                                            arrayList.add(
                                                    new ShowContentAppInfo(str, str3, null, i, z2));
                                        } else {
                                            showNotificationContentPreference2.mIsAllAppChecked =
                                                    Boolean.FALSE;
                                        }
                                    }
                                }
                                return null;
                            }
                        });
            }
            try {
                newFixedThreadPool.invokeAll(arrayList2);
                newFixedThreadPool.shutdown();
                newFixedThreadPool.awaitTermination(3L, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return arrayList;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            String string;
            String str;
            ArrayList arrayList = (ArrayList) obj;
            super.onPostExecute(arrayList);
            if (arrayList != null) {
                ShowNotificationContentPreference showNotificationContentPreference =
                        ShowNotificationContentPreference.this;
                Collections.sort(arrayList, showNotificationContentPreference.mAppNameComparator);
                if (showNotificationContentPreference.mIsAllAppChecked.booleanValue()) {
                    LoggingHelper.insertEventLogging(
                            4435, 36111, "All apps turned on to show content");
                    str =
                            showNotificationContentPreference.mContext.getString(
                                    R.string.sec_edge_lighting_enable_app_list_all);
                } else {
                    int size = arrayList.size();
                    if (size != 0) {
                        String str2 = ApnSettings.MVNO_NONE;
                        if (size == 1) {
                            Context context = showNotificationContentPreference.mContext;
                            if (arrayList.get(0) != null) {
                                str2 = ((ShowContentAppInfo) arrayList.get(0)).appName;
                            }
                            string =
                                    context.getString(
                                            R.string.sec_edge_lighting_enable_app_list_one, str2);
                        } else if (size != 2) {
                            Context context2 = showNotificationContentPreference.mContext;
                            String str3 =
                                    arrayList.get(0) != null
                                            ? ((ShowContentAppInfo) arrayList.get(0)).appName
                                            : ApnSettings.MVNO_NONE;
                            if (arrayList.get(1) != null) {
                                str2 = ((ShowContentAppInfo) arrayList.get(1)).appName;
                            }
                            string =
                                    context2.getString(
                                            R.string.sec_edge_lighting_enable_app_list_other,
                                            str3,
                                            str2,
                                            Integer.valueOf(size - 2));
                        } else {
                            Context context3 = showNotificationContentPreference.mContext;
                            String str4 =
                                    arrayList.get(0) != null
                                            ? ((ShowContentAppInfo) arrayList.get(0)).appName
                                            : ApnSettings.MVNO_NONE;
                            if (arrayList.get(1) != null) {
                                str2 = ((ShowContentAppInfo) arrayList.get(1)).appName;
                            }
                            string =
                                    context3.getString(
                                            R.string.sec_edge_lighting_enable_app_list_two,
                                            str4,
                                            str2);
                        }
                    } else {
                        string =
                                showNotificationContentPreference.mContext.getString(
                                        R.string.sec_edge_lighting_enable_app_list_zero);
                    }
                    LoggingHelper.insertEventLogging(
                            4435, 36111, arrayList.size() + " apps turned on to show content");
                    str = string;
                }
                String str5 = str.toString();
                showNotificationContentPreference.mSummaryText = str5;
                TextView textView = showNotificationContentPreference.mShowContentSummary;
                if (textView != null) {
                    textView.setText(str5);
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r2v4, types: [com.samsung.android.settings.lockscreen.ShowNotificationContentPreference$1] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.samsung.android.settings.lockscreen.ShowNotificationContentPreference$3] */
    public ShowNotificationContentPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mBackend = new NotificationBackend();
        this.mIsAllAppChecked = Boolean.TRUE;
        this.mSummaryText = ApnSettings.MVNO_NONE;
        this.mSettingsButtonClickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.lockscreen.ShowNotificationContentPreference.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        if (view.getId() == R.id.show_content_settings_button) {
                            LoggingHelper.insertEventLogging(4435, "L4452");
                            SubSettingLauncher subSettingLauncher =
                                    new SubSettingLauncher(
                                            ShowNotificationContentPreference.this.getContext());
                            String name =
                                    LockScreenNotificationShowContentListSettings.class.getName();
                            SubSettingLauncher.LaunchRequest launchRequest =
                                    subSettingLauncher.mLaunchRequest;
                            launchRequest.mDestinationName = name;
                            launchRequest.mSourceMetricsCategory = 4435;
                            subSettingLauncher.launch();
                        }
                    }
                };
        this.mAppNameComparator =
                new Comparator() { // from class:
                                   // com.samsung.android.settings.lockscreen.ShowNotificationContentPreference.3
                    public final Collator collator = Collator.getInstance();

                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        try {
                            return this.collator.compare(
                                    ((ShowContentAppInfo) obj).appName,
                                    ((ShowContentAppInfo) obj2).appName);
                        } catch (NullPointerException e) {
                            Log.e(
                                    "ShowNotificationContentPreference",
                                    "Failed to compare AppInfo. " + e);
                            return 0;
                        }
                    }
                };
        this.mContext = context;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        if (this.mShowContentContainer == null) {
            this.mShowContentContainer =
                    (LinearLayout) preferenceViewHolder.findViewById(R.id.show_content_container);
        }
        if (this.mShowContentBtn == null) {
            this.mShowContentBtn =
                    (RadioButton) preferenceViewHolder.findViewById(R.id.show_content_button);
        }
        if (this.mShowContentSummary == null) {
            TextView textView =
                    (TextView) preferenceViewHolder.findViewById(R.id.show_content_summary);
            this.mShowContentSummary = textView;
            textView.setVisibility(0);
            this.mShowContentSummary.setText(this.mSummaryText);
        }
        if (this.mShowContentSettingsBtn == null) {
            View findViewById =
                    preferenceViewHolder.findViewById(R.id.show_content_settings_button);
            this.mShowContentSettingsBtn = findViewById;
            findViewById.setOnClickListener(this.mSettingsButtonClickListener);
            this.mShowContentSettingsBtnBg = this.mShowContentSettingsBtn.getBackground();
            setSettingsBtnEnabled(isEnabled());
        }
        this.mShowContentBtn.setChecked(
                Settings.Secure.getIntForUser(
                                getContext().getContentResolver(),
                                "lock_screen_allow_private_notifications",
                                1,
                                -2)
                        == 1);
    }

    @Override // androidx.preference.Preference
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        setSettingsBtnEnabled(z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x0016, code lost:

       if (android.provider.Settings.Secure.getIntForUser(getContext().getContentResolver(), "lock_screen_allow_private_notifications", 1, -2) == 1) goto L9;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setSettingsBtnEnabled(boolean r4) {
        /*
            r3 = this;
            android.view.View r0 = r3.mShowContentSettingsBtn
            if (r0 == 0) goto L41
            if (r4 == 0) goto L19
            android.content.Context r4 = r3.getContext()
            android.content.ContentResolver r4 = r4.getContentResolver()
            r0 = -2
            java.lang.String r1 = "lock_screen_allow_private_notifications"
            r2 = 1
            int r4 = android.provider.Settings.Secure.getIntForUser(r4, r1, r2, r0)
            if (r4 != r2) goto L19
            goto L1a
        L19:
            r2 = 0
        L1a:
            android.view.View r4 = r3.mShowContentSettingsBtn
            r4.setEnabled(r2)
            android.view.View r4 = r3.mShowContentSettingsBtn
            if (r2 == 0) goto L26
            r0 = 1065353216(0x3f800000, float:1.0)
            goto L29
        L26:
            r0 = 1053609165(0x3ecccccd, float:0.4)
        L29:
            r4.setAlpha(r0)
            android.view.View r4 = r3.mShowContentSettingsBtn
            r0 = 0
            if (r2 == 0) goto L34
            com.samsung.android.settings.lockscreen.ShowNotificationContentPreference$1 r1 = r3.mSettingsButtonClickListener
            goto L35
        L34:
            r1 = r0
        L35:
            r4.setOnClickListener(r1)
            android.view.View r4 = r3.mShowContentSettingsBtn
            if (r2 == 0) goto L3e
            android.graphics.drawable.Drawable r0 = r3.mShowContentSettingsBtnBg
        L3e:
            r4.setBackground(r0)
        L41:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.lockscreen.ShowNotificationContentPreference.setSettingsBtnEnabled(boolean):void");
    }
}
