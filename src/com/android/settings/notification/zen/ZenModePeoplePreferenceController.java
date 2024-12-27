package com.android.settings.notification.zen;

import android.app.NotificationManager;
import android.content.Context;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModePeoplePreferenceController extends AbstractZenModePreferenceController {
    public final String KEY;
    public final ZenModeSettings.SummaryBuilder mSummaryBuilder;
    public final UserManager mUm;

    public ZenModePeoplePreferenceController(Context context, Lifecycle lifecycle) {
        super(context, "zen_mode_behavior_people", lifecycle);
        this.KEY = "zen_mode_behavior_people";
        this.mSummaryBuilder = new ZenModeSettings.SummaryBuilder(context);
        this.mUm = (UserManager) context.getSystemService("user");
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return this.KEY;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return !this.mUm.isRestrictedProfile();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        int zenMode = getZenMode();
        boolean z = false;
        if (zenMode == 2 || zenMode == 3) {
            preference.setEnabled(false);
            this.mBackend.getClass();
            preference.setSummary(R.string.zen_mode_none_messages);
            return;
        }
        preference.setEnabled(true);
        SecPreferenceUtils.applySummaryColor((SecPreference) preference, true);
        NotificationManager.Policy notificationPolicy =
                this.mNotificationManager.getNotificationPolicy();
        ZenModeSettings.SummaryBuilder summaryBuilder = this.mSummaryBuilder;
        summaryBuilder.getClass();
        List exceptionContacts = notificationPolicy.getExceptionContacts();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < exceptionContacts.size(); i++) {
            if (ZenUtil.isContactDeleted(
                    summaryBuilder.mContext, ((String) exceptionContacts.get(i)).split(";")[0])) {
                arrayList.add((String) exceptionContacts.get(i));
            }
        }
        exceptionContacts.removeAll(arrayList);
        ZenUtil.setExceptionContact(summaryBuilder.mContext, exceptionContacts);
        int size = exceptionContacts.size();
        boolean z2 =
                (ZenModeSettings.SummaryBuilder.isCategoryEnabled(notificationPolicy, 8)
                                || ZenModeSettings.SummaryBuilder.isCategoryEnabled(
                                        notificationPolicy, 4)
                                || ZenModeSettings.SummaryBuilder.isCategoryEnabled(
                                        notificationPolicy, 256)
                                || ZenModeSettings.SummaryBuilder.isCategoryEnabled(
                                        notificationPolicy, 16)
                                || size != 0)
                        ? false
                        : true;
        boolean z3 =
                ZenModeSettings.SummaryBuilder.isCategoryEnabled(notificationPolicy, 8)
                        && notificationPolicy.priorityCallSenders == 0
                        && ZenModeSettings.SummaryBuilder.isCategoryEnabled(notificationPolicy, 4)
                        && notificationPolicy.priorityMessageSenders == 0
                        && size > 0;
        int i2 =
                Settings.Global.getInt(
                        summaryBuilder.mContext.getContentResolver(),
                        "zen_selected_exception_contacts_allowed",
                        0);
        Log.d("ZenModeSettings", " mCurrentAllowState: " + i2);
        if (!ZenModeSettings.SummaryBuilder.isCategoryEnabled(notificationPolicy, 8)
                && !ZenModeSettings.SummaryBuilder.isCategoryEnabled(notificationPolicy, 4)
                && !ZenModeSettings.SummaryBuilder.isCategoryEnabled(notificationPolicy, 16)) {
            z = true;
        }
        preference.setSummary(
                ((z2 && i2 == 0) || (z3 && i2 == 1))
                        ? summaryBuilder.mContext.getString(
                                R.string.sec_zen_people_summery_allow_none)
                        : ((z3 && i2 == 0) || (z2 && i2 == 1))
                                ? summaryBuilder.mContext.getString(
                                        R.string.sec_zen_people_summery_allow_all)
                                : (!z || size <= 0)
                                        ? summaryBuilder.mContext.getString(
                                                R.string.sec_zen_people_summery_allow_some)
                                        : i2 == 0
                                                ? summaryBuilder
                                                        .mContext
                                                        .getResources()
                                                        .getQuantityString(
                                                                R.plurals.sec_dnd_select_contact,
                                                                size,
                                                                Integer.valueOf(size))
                                                : summaryBuilder
                                                        .mContext
                                                        .getResources()
                                                        .getQuantityString(
                                                                R.plurals
                                                                        .sec_dnd_allow_all_except_select_contact,
                                                                size,
                                                                Integer.valueOf(size)));
    }
}
