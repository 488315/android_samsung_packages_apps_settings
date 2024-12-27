package com.android.settings.notification.zen;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.icu.text.MessageFormat;
import android.provider.ContactsContract;
import android.service.notification.ConversationChannelWrapper;
import android.view.View;

import androidx.preference.PreferenceCategory;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.notification.app.ConversationListSettings;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenPrioritySendersHelper {
    public final Context mContext;
    public final boolean mIsMessages;
    public final NotificationBackend mNotificationBackend;
    public final PackageManager mPackageManager;
    public PreferenceCategory mPreferenceCategory;
    public final SelectorWithWidgetPreference.OnClickListener mSelectorClickListener;
    public final ZenModeBackend mZenModeBackend;
    public static final Intent ALL_CONTACTS_INTENT =
            new Intent("com.android.contacts.action.LIST_DEFAULT").setFlags(268468224);
    public static final Intent STARRED_CONTACTS_INTENT =
            new Intent("com.android.contacts.action.LIST_STARRED").setFlags(268468224);
    public static final Intent FALLBACK_INTENT =
            new Intent("android.intent.action.MAIN").setFlags(268468224);
    public int mNumImportantConversations = -10;
    public final List mSelectorPreferences = new ArrayList();

    public ZenPrioritySendersHelper(
            Context context,
            boolean z,
            ZenModeBackend zenModeBackend,
            NotificationBackend notificationBackend,
            SelectorWithWidgetPreference.OnClickListener onClickListener) {
        this.mContext = context;
        this.mIsMessages = z;
        this.mZenModeBackend = zenModeBackend;
        this.mNotificationBackend = notificationBackend;
        this.mSelectorClickListener = onClickListener;
        String string = context.getString(R.string.config_contacts_package_name);
        ALL_CONTACTS_INTENT.setPackage(string);
        STARRED_CONTACTS_INTENT.setPackage(string);
        Intent intent = FALLBACK_INTENT;
        intent.setPackage(string);
        this.mPackageManager = context.getPackageManager();
        if (intent.hasCategory("android.intent.category.APP_CONTACTS")) {
            return;
        }
        intent.addCategory("android.intent.category.APP_CONTACTS");
    }

    public final void displayPreference(PreferenceCategory preferenceCategory) {
        this.mPreferenceCategory = preferenceCategory;
        if (preferenceCategory.getPreferenceCount() == 0) {
            boolean z = this.mIsMessages;
            makeSelectorPreference(R.string.zen_mode_from_starred, "senders_starred_contacts", z);
            makeSelectorPreference(R.string.zen_mode_from_contacts, "senders_contacts", z);
            if (z) {
                makeSelectorPreference(
                        R.string.zen_mode_from_important_conversations,
                        "conversations_important",
                        true);
                updateChannelCounts();
            }
            makeSelectorPreference(R.string.zen_mode_from_anyone, "senders_anyone", z);
            makeSelectorPreference(R.string.zen_mode_none_messages, "senders_none", z);
            updateSummaries();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x005f, code lost:

       if (r13.equals("senders_none") == false) goto L29;
    */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int[] keyToSettingEndState(java.lang.String r13, boolean r14) {
        /*
            Method dump skipped, instructions count: 364
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.notification.zen.ZenPrioritySendersHelper.keyToSettingEndState(java.lang.String,"
                    + " boolean):int[]");
    }

    public final void makeSelectorPreference(int i, final String str, boolean z) {
        SelectorWithWidgetPreference selectorWithWidgetPreference =
                new SelectorWithWidgetPreference(this.mPreferenceCategory.getContext(), z);
        selectorWithWidgetPreference.setKey(str);
        selectorWithWidgetPreference.setTitle(i);
        selectorWithWidgetPreference.mListener = this.mSelectorClickListener;
        View.OnClickListener onClickListener = null;
        if (("senders_contacts".equals(str)
                        || "senders_starred_contacts".equals(str)
                        || "conversations_important".equals(str))
                && ((!"senders_starred_contacts".equals(str)
                                || STARRED_CONTACTS_INTENT.resolveActivity(this.mPackageManager)
                                        != null
                                || FALLBACK_INTENT.resolveActivity(this.mPackageManager) != null)
                        && (!"senders_contacts".equals(str)
                                || ALL_CONTACTS_INTENT.resolveActivity(this.mPackageManager) != null
                                || FALLBACK_INTENT.resolveActivity(this.mPackageManager)
                                        != null))) {
            onClickListener =
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.notification.zen.ZenPrioritySendersHelper.1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            if ("senders_starred_contacts".equals(str)) {
                                Intent intent = ZenPrioritySendersHelper.STARRED_CONTACTS_INTENT;
                                if (intent.resolveActivity(
                                                ZenPrioritySendersHelper.this.mPackageManager)
                                        != null) {
                                    ZenPrioritySendersHelper.this.mContext.startActivity(intent);
                                    return;
                                }
                            }
                            if ("senders_contacts".equals(str)) {
                                Intent intent2 = ZenPrioritySendersHelper.ALL_CONTACTS_INTENT;
                                if (intent2.resolveActivity(
                                                ZenPrioritySendersHelper.this.mPackageManager)
                                        != null) {
                                    ZenPrioritySendersHelper.this.mContext.startActivity(intent2);
                                    return;
                                }
                            }
                            if (!"conversations_important".equals(str)) {
                                ZenPrioritySendersHelper.this.mContext.startActivity(
                                        ZenPrioritySendersHelper.FALLBACK_INTENT);
                                return;
                            }
                            SubSettingLauncher subSettingLauncher =
                                    new SubSettingLauncher(ZenPrioritySendersHelper.this.mContext);
                            String name = ConversationListSettings.class.getName();
                            SubSettingLauncher.LaunchRequest launchRequest =
                                    subSettingLauncher.mLaunchRequest;
                            launchRequest.mDestinationName = name;
                            launchRequest.mSourceMetricsCategory = 1837;
                            subSettingLauncher.launch();
                        }
                    };
        }
        if (onClickListener != null) {
            selectorWithWidgetPreference.setExtraWidgetOnClickListener(onClickListener);
        }
        this.mPreferenceCategory.addPreference(selectorWithWidgetPreference);
        ((ArrayList) this.mSelectorPreferences).add(selectorWithWidgetPreference);
    }

    public final int[] settingsToSaveOnClick(
            SelectorWithWidgetPreference selectorWithWidgetPreference, int i, int i2) {
        int[] iArr = {-10, -10};
        int[] keyToSettingEndState =
                keyToSettingEndState(
                        selectorWithWidgetPreference.getKey(),
                        (selectorWithWidgetPreference.mIsCheckBox
                                        && selectorWithWidgetPreference.mChecked)
                                ? false
                                : true);
        int i3 = keyToSettingEndState[0];
        int i4 = keyToSettingEndState[1];
        if (i3 != -10 && i3 != i) {
            iArr[0] = i3;
        }
        if (this.mIsMessages) {
            if (i4 != -10 && i4 != i2) {
                iArr[1] = i4;
            }
            if (selectorWithWidgetPreference.getKey() == "conversations_important" && i == 0) {
                iArr[0] = -1;
            }
            if ((selectorWithWidgetPreference.getKey() == "senders_starred_contacts"
                            || selectorWithWidgetPreference.getKey() == "senders_contacts")
                    && i2 == 1) {
                iArr[1] = 3;
            }
        }
        return iArr;
    }

    public final void updateChannelCounts() {
        this.mNotificationBackend.getClass();
        ParceledListSlice conversations = NotificationBackend.getConversations(true);
        int i = 0;
        if (conversations != null) {
            Iterator it = conversations.getList().iterator();
            while (it.hasNext()) {
                if (!((ConversationChannelWrapper) it.next())
                        .getNotificationChannel()
                        .isDemoted()) {
                    i++;
                }
            }
        }
        this.mNumImportantConversations = i;
    }

    public final void updateState(int i, int i2) {
        Iterator it = ((ArrayList) this.mSelectorPreferences).iterator();
        while (it.hasNext()) {
            SelectorWithWidgetPreference selectorWithWidgetPreference =
                    (SelectorWithWidgetPreference) it.next();
            int[] keyToSettingEndState =
                    keyToSettingEndState(selectorWithWidgetPreference.getKey(), true);
            int i3 = keyToSettingEndState[0];
            int i4 = keyToSettingEndState[1];
            boolean z = i3 == i;
            if (this.mIsMessages && i4 != -10) {
                z = (z || i3 == -10) && i4 == i2;
            }
            selectorWithWidgetPreference.setChecked(z);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final void updateSummaries() {
        char c;
        String starredContactsSummary;
        Iterator it = ((ArrayList) this.mSelectorPreferences).iterator();
        while (it.hasNext()) {
            SelectorWithWidgetPreference selectorWithWidgetPreference =
                    (SelectorWithWidgetPreference) it.next();
            String key = selectorWithWidgetPreference.getKey();
            switch (key.hashCode()) {
                case -1145842476:
                    if (key.equals("senders_starred_contacts")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -133103980:
                    if (key.equals("senders_contacts")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 660058867:
                    if (key.equals("conversations_important")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1725241211:
                    if (key.equals("senders_anyone")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1767544313:
                    if (key.equals("senders_none")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            ZenModeBackend zenModeBackend = this.mZenModeBackend;
            if (c == 0) {
                starredContactsSummary = zenModeBackend.getStarredContactsSummary();
            } else if (c != 1) {
                starredContactsSummary = null;
                if (c == 2) {
                    int i = this.mNumImportantConversations;
                    if (i != -10) {
                        MessageFormat messageFormat =
                                new MessageFormat(
                                        this.mContext.getString(
                                                R.string.zen_mode_conversations_count),
                                        Locale.getDefault());
                        HashMap hashMap = new HashMap();
                        hashMap.put("count", Integer.valueOf(i));
                        starredContactsSummary = messageFormat.format(hashMap);
                    }
                } else if (c == 3) {
                    starredContactsSummary =
                            this.mContext
                                    .getResources()
                                    .getString(
                                            this.mIsMessages
                                                    ? R.string.zen_mode_all_messages_summary
                                                    : R.string.zen_mode_all_calls_summary);
                }
            } else {
                zenModeBackend.getClass();
                MessageFormat messageFormat2 =
                        new MessageFormat(
                                zenModeBackend.mContext.getString(R.string.zen_mode_contacts_count),
                                Locale.getDefault());
                HashMap hashMap2 = new HashMap();
                hashMap2.put(
                        "count",
                        Integer.valueOf(
                                zenModeBackend
                                        .mContext
                                        .getContentResolver()
                                        .query(
                                                ContactsContract.Contacts.CONTENT_URI,
                                                new String[] {"display_name"},
                                                null,
                                                null,
                                                null)
                                        .getCount()));
                starredContactsSummary = messageFormat2.format(hashMap2);
            }
            selectorWithWidgetPreference.setSummary(starredContactsSummary);
        }
    }
}
