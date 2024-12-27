package com.android.settings.notification.zen;

import android.app.NotificationManager;
import android.content.Context;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler;
import com.samsung.android.settings.notification.app.AppNotificationTypeInfo;
import com.samsung.android.settings.widget.SecRecyclerViewPreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeContactExceptionController extends AbstractZenModePreferenceController {
    public final Context mContext;
    public final boolean mIsFromDnd;
    public SecRecyclerViewPreference mPreference;

    public ZenModeContactExceptionController(Context context, Lifecycle lifecycle, boolean z) {
        super(context, "zen_contact", lifecycle);
        this.mContext = context;
        BixbyRoutineActionHandler.getInstance();
        this.mIsFromDnd = z;
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = (SecRecyclerViewPreference) preferenceScreen.findPreference(this.KEY);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ArrayList arrayList;
        super.updateState(preference);
        SecRecyclerViewPreference secRecyclerViewPreference =
                (SecRecyclerViewPreference) preference;
        this.mPreference = secRecyclerViewPreference;
        secRecyclerViewPreference.COLUMN_COUNT = 4;
        if (this.mIsFromDnd) {
            Context context = this.mContext;
            arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            AppNotificationTypeInfo appNotificationTypeInfo = new AppNotificationTypeInfo();
            appNotificationTypeInfo.imageEntry = R.drawable.ic_dnd_add_contact;
            appNotificationTypeInfo.title =
                    context.getString(R.string.zen_mode_bypassing_contacts_add);
            arrayList.add(appNotificationTypeInfo);
            List exceptionContacts =
                    ((NotificationManager) context.getSystemService(NotificationManager.class))
                            .getNotificationPolicy()
                            .getExceptionContacts();
            ArrayList arrayList3 = new ArrayList();
            for (int i = 0; i < exceptionContacts.size(); i++) {
                String[] split = ((String) exceptionContacts.get(i)).split(";");
                String str = split[0];
                String str2 = split[1];
                long parseLong = Long.parseLong(str);
                if (ZenUtil.isContactDeleted(context, str)) {
                    arrayList3.add((String) exceptionContacts.get(i));
                } else {
                    arrayList2.add(ZenUtil.getPhotoAndTitle(context, str2, parseLong));
                }
            }
            exceptionContacts.removeAll(arrayList3);
            ZenUtil.setExceptionContact(context, exceptionContacts);
            Collections.sort(
                    arrayList2,
                    new Comparator() { // from class:
                                       // com.android.settings.notification.zen.ZenUtil.1
                        public final /* synthetic */ int $r8$classId;

                        public /* synthetic */ AnonymousClass1(int i2) {
                            r1 = i2;
                        }

                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            switch (r1) {
                            }
                            return ((AppNotificationTypeInfo) obj)
                                    .title.compareToIgnoreCase(
                                            ((AppNotificationTypeInfo) obj2).title);
                        }
                    });
            arrayList.addAll(arrayList2);
        } else {
            Context context2 = this.mContext;
            BixbyRoutineActionHandler.getInstance();
            arrayList = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            AppNotificationTypeInfo appNotificationTypeInfo2 = new AppNotificationTypeInfo();
            appNotificationTypeInfo2.imageEntry = R.drawable.ic_dnd_add_contact;
            appNotificationTypeInfo2.title =
                    context2.getString(R.string.zen_mode_bypassing_contacts_add);
            arrayList.add(appNotificationTypeInfo2);
            ArrayList arrayList5 = new ArrayList();
            if (!BixbyRoutineActionHandler.contact_exist_list.equals(ApnSettings.MVNO_NONE)) {
                for (String str3 : BixbyRoutineActionHandler.contact_exist_list.split(",")) {
                    arrayList5.add(str3);
                }
            }
            for (int i2 = 0; i2 < arrayList5.size(); i2++) {
                String[] split2 = ((String) arrayList5.get(i2)).split(";");
                arrayList4.add(
                        ZenUtil.getPhotoAndTitle(context2, split2[1], Long.parseLong(split2[0])));
            }
            Collections.sort(
                    arrayList4,
                    new Comparator() { // from class:
                                       // com.android.settings.notification.zen.ZenUtil.1
                        public final /* synthetic */ int $r8$classId;

                        public /* synthetic */ AnonymousClass1(int i22) {
                            r1 = i22;
                        }

                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            switch (r1) {
                            }
                            return ((AppNotificationTypeInfo) obj)
                                    .title.compareToIgnoreCase(
                                            ((AppNotificationTypeInfo) obj2).title);
                        }
                    });
            arrayList.addAll(arrayList4);
        }
        secRecyclerViewPreference.mAppNotificationTypeInfoList.clear();
        secRecyclerViewPreference.mAppNotificationTypeInfoList.addAll(arrayList);
        BixbyRoutineActionHandler.setPeoplesummary(this.mContext);
        this.mPreference.mAppNotificationTypeAdapter.notifyDataSetChanged();
    }
}
