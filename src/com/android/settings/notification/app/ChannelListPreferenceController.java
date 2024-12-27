package com.android.settings.notification.app;

import android.app.NotificationChannelGroup;
import android.content.pm.ParceledListSlice;
import android.os.AsyncTask;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceGroup;

import com.android.settings.R;
import com.android.settings.notification.NotificationBackend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ChannelListPreferenceController extends NotificationPreferenceController {
    public List mChannelGroupList;
    public PreferenceCategory mPreference;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "channels";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        NotificationBackend.AppRow appRow = this.mAppRow;
        if (appRow == null || appRow.banned) {
            return false;
        }
        if (this.mChannel == null) {
            return true;
        }
        String str = appRow.pkg;
        int i = appRow.uid;
        this.mBackend.getClass();
        return (NotificationBackend.onlyHasDefaultChannel(i, str)
                        || "miscellaneous".equals(this.mChannel.getId()))
                ? false
                : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01de A[LOOP:2: B:73:0x01de->B:75:0x01e5, LOOP_START] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01f6 A[LOOP:3: B:78:0x01f0->B:80:0x01f6, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01ed  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateGroupPreferences(
            final android.app.NotificationChannelGroup r17,
            androidx.preference.PreferenceGroup r18) {
        /*
            Method dump skipped, instructions count: 515
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.notification.app.ChannelListPreferenceController.updateGroupPreferences(android.app.NotificationChannelGroup,"
                    + " androidx.preference.PreferenceGroup):void");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        this.mPreference = (PreferenceCategory) preference;
        new AsyncTask() { // from class:
                          // com.android.settings.notification.app.ChannelListPreferenceController.1
            @Override // android.os.AsyncTask
            public final Object doInBackground(Object[] objArr) {
                ParceledListSlice emptyList;
                ChannelListPreferenceController channelListPreferenceController =
                        ChannelListPreferenceController.this;
                NotificationBackend notificationBackend = channelListPreferenceController.mBackend;
                NotificationBackend.AppRow appRow = channelListPreferenceController.mAppRow;
                String str = appRow.pkg;
                int i = appRow.uid;
                notificationBackend.getClass();
                try {
                    emptyList =
                            NotificationBackend.sINM.getNotificationChannelGroupsForPackage(
                                    str, i, false);
                } catch (Exception e) {
                    Log.w("NotificationBackend", "Error calling NoMan", e);
                    emptyList = ParceledListSlice.emptyList();
                }
                channelListPreferenceController.mChannelGroupList = emptyList.getList();
                Collections.sort(
                        ChannelListPreferenceController.this.mChannelGroupList,
                        NotificationPreferenceController.CHANNEL_GROUP_COMPARATOR);
                return null;
            }

            @Override // android.os.AsyncTask
            public final void onPostExecute(Object obj) {
                PreferenceCategory preferenceCategory;
                ChannelListPreferenceController channelListPreferenceController =
                        ChannelListPreferenceController.this;
                if (((NotificationPreferenceController) channelListPreferenceController).mContext
                        == null) {
                    return;
                }
                PreferenceCategory preferenceCategory2 =
                        channelListPreferenceController.mPreference;
                List list = channelListPreferenceController.mChannelGroupList;
                if (list.isEmpty()) {
                    if (preferenceCategory2.getPreferenceCount() == 1
                            && "zeroCategories"
                                    .equals(preferenceCategory2.getPreference(0).getKey())) {
                        PreferenceGroup preferenceGroup =
                                (PreferenceGroup) preferenceCategory2.getPreference(0);
                        preferenceGroup.setTitle(R.string.notification_channels);
                        preferenceGroup.getPreference(0).setTitle(R.string.no_channels);
                        return;
                    }
                    preferenceCategory2.removeAll();
                    PreferenceCategory preferenceCategory3 =
                            new PreferenceCategory(
                                    ((NotificationPreferenceController)
                                                    channelListPreferenceController)
                                            .mContext);
                    preferenceCategory3.setTitle(R.string.notification_channels);
                    preferenceCategory3.setKey("zeroCategories");
                    preferenceCategory2.addPreference(preferenceCategory3);
                    Preference preference2 =
                            new Preference(
                                    ((NotificationPreferenceController)
                                                    channelListPreferenceController)
                                            .mContext);
                    preference2.setTitle(R.string.no_channels);
                    preference2.setEnabled(false);
                    preferenceCategory3.addPreference(preference2);
                    return;
                }
                int size = list.size();
                int preferenceCount = preferenceCategory2.getPreferenceCount();
                ArrayList arrayList = new ArrayList(size);
                for (int i = 0; i < size; i++) {
                    NotificationChannelGroup notificationChannelGroup =
                            (NotificationChannelGroup) list.get(i);
                    String id = notificationChannelGroup.getId();
                    if (id == null) {
                        id = "categories";
                    }
                    int preferenceCount2 = preferenceCategory2.getPreferenceCount();
                    if (i < preferenceCount2) {
                        Preference preference3 = preferenceCategory2.getPreference(i);
                        if (id.equals(preference3.getKey())) {
                            preferenceCategory = (PreferenceCategory) preference3;
                            arrayList.add(preferenceCategory);
                            channelListPreferenceController.updateGroupPreferences(
                                    notificationChannelGroup, preferenceCategory);
                        }
                    }
                    int i2 = 0;
                    while (true) {
                        if (i2 >= preferenceCount2) {
                            preferenceCategory =
                                    new PreferenceCategory(
                                            ((NotificationPreferenceController)
                                                            channelListPreferenceController)
                                                    .mContext);
                            preferenceCategory.setOrder(i);
                            preferenceCategory.setKey(id);
                            preferenceCategory2.addPreference(preferenceCategory);
                            break;
                        }
                        Preference preference4 = preferenceCategory2.getPreference(i2);
                        if (id.equals(preference4.getKey())) {
                            preference4.setOrder(i);
                            preferenceCategory = (PreferenceCategory) preference4;
                            break;
                        }
                        i2++;
                    }
                    arrayList.add(preferenceCategory);
                    channelListPreferenceController.updateGroupPreferences(
                            notificationChannelGroup, preferenceCategory);
                }
                int preferenceCount3 = preferenceCategory2.getPreferenceCount();
                boolean z = (preferenceCount == 0 || preferenceCount == size) ? false : true;
                boolean z2 = preferenceCount3 != size;
                if (z || z2) {
                    preferenceCategory2.removeAll();
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        preferenceCategory2.addPreference((PreferenceCategory) it.next());
                    }
                }
            }
        }.execute(new Void[0]);
    }
}
