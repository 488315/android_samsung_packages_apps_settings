package com.android.settings.notification;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageItemInfo;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.applications.defaultapps.DefaultAppPickerFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.applications.DefaultAppInfo;
import com.android.settingslib.applications.ServiceListing;
import com.android.settingslib.widget.CandidateInfo;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NotificationAssistantPicker extends DefaultAppPickerFragment
        implements ServiceListing.Callback {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.notification_assistant_settings);
    public List mCandidateInfos = new ArrayList();

    @VisibleForTesting protected Context mContext;

    @VisibleForTesting protected NotificationBackend mNotificationBackend;
    public ServiceListing mServiceListing;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CandidateNone extends CandidateInfo {
        public Context mContext;

        @Override // com.android.settingslib.widget.CandidateInfo
        public final String getKey() {
            return ApnSettings.MVNO_NONE;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final Drawable loadIcon() {
            return null;
        }

        @Override // com.android.settingslib.widget.CandidateInfo
        public final CharSequence loadLabel() {
            return this.mContext.getString(R.string.no_notification_assistant);
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final List getCandidates() {
        return this.mCandidateInfos;
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPickerFragment
    public final CharSequence getConfirmationMessage(CandidateInfo candidateInfo) {
        if (TextUtils.isEmpty(candidateInfo.getKey())) {
            return null;
        }
        return this.mContext.getString(
                R.string.notification_assistant_security_warning_summary,
                candidateInfo.loadLabel());
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final String getDefaultKey() {
        this.mNotificationBackend.getClass();
        ComponentName allowedNotificationAssistant =
                NotificationBackend.getAllowedNotificationAssistant();
        return allowedNotificationAssistant != null
                ? allowedNotificationAssistant.flattenToString()
                : ApnSettings.MVNO_NONE;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 790;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.notification_assistant_settings;
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPickerFragment,
              // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mNotificationBackend = new NotificationBackend();
        ServiceListing serviceListing =
                new ServiceListing(
                        context,
                        "NotiAssistantPicker",
                        "enabled_notification_assistant",
                        "android.service.notification.NotificationAssistantService",
                        "android.permission.BIND_NOTIFICATION_ASSISTANT_SERVICE",
                        "notification assistant");
        this.mServiceListing = serviceListing;
        ((ArrayList) serviceListing.mCallbacks).add(this);
        this.mServiceListing.reload();
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onDetach() {
        super.onDetach();
        ((ArrayList) this.mServiceListing.mCallbacks).remove(this);
    }

    @Override // com.android.settingslib.applications.ServiceListing.Callback
    public final void onServicesReloaded(List list) {
        ArrayList arrayList = new ArrayList();
        list.sort(new PackageItemInfo.DisplayNameComparator(this.mPm));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ServiceInfo serviceInfo = (ServiceInfo) it.next();
            if (this.mContext
                            .getPackageManager()
                            .checkPermission(
                                    "android.permission.REQUEST_NOTIFICATION_ASSISTANT_SERVICE",
                                    serviceInfo.packageName)
                    == 0) {
                arrayList.add(
                        new DefaultAppInfo(
                                this.mContext,
                                this.mPm,
                                this.mUserId,
                                new ComponentName(serviceInfo.packageName, serviceInfo.name)));
            }
        }
        Context context = this.mContext;
        CandidateNone candidateNone = new CandidateNone(true);
        candidateNone.mContext = context;
        arrayList.add(candidateNone);
        this.mCandidateInfos = arrayList;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean setDefaultKey(String str) {
        NotificationBackend notificationBackend = this.mNotificationBackend;
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        notificationBackend.getClass();
        return NotificationBackend.setNotificationAssistantGranted(unflattenFromString);
    }
}
