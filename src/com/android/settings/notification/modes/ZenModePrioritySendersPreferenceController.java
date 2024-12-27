package com.android.settings.notification.modes;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.database.Cursor;
import android.icu.text.MessageFormat;
import android.provider.ContactsContract;
import android.service.notification.ConversationChannelWrapper;
import android.service.notification.ZenPolicy;
import android.util.Log;
import android.view.View;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.notification.app.ConversationListSettings;
import com.android.settings.notification.modes.ZenModePrioritySendersPreferenceController;
import com.android.settingslib.widget.SelectorWithWidgetPreference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModePrioritySendersPreferenceController extends AbstractZenModePreferenceController {
    public final boolean mIsMessages;
    public int mNumImportantConversations;
    public final PackageManager mPackageManager;
    public PreferenceCategory mPreferenceCategory;
    SelectorWithWidgetPreference.OnClickListener mSelectorClickListener;
    public final List mSelectorPreferences;
    public final ZenModeSummaryHelper mZenModeSummaryHelper;
    public static final Intent ALL_CONTACTS_INTENT = new Intent("com.android.contacts.action.LIST_DEFAULT").setFlags(268468224);
    public static final Intent STARRED_CONTACTS_INTENT = new Intent("com.android.contacts.action.LIST_STARRED").setFlags(268468224);
    public static final Intent FALLBACK_INTENT = new Intent("android.intent.action.MAIN").setFlags(268468224);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.modes.ZenModePrioritySendersPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 implements SelectorWithWidgetPreference.OnClickListener {
        public AnonymousClass1() {
        }

        @Override // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
        public final void onRadioButtonClicked(final SelectorWithWidgetPreference selectorWithWidgetPreference) {
            ZenModePrioritySendersPreferenceController.this.savePolicy(new Function() { // from class: com.android.settings.notification.modes.ZenModePrioritySendersPreferenceController$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    ZenModePrioritySendersPreferenceController.AnonymousClass1 anonymousClass1 = ZenModePrioritySendersPreferenceController.AnonymousClass1.this;
                    SelectorWithWidgetPreference selectorWithWidgetPreference2 = selectorWithWidgetPreference;
                    ZenPolicy.Builder builder = (ZenPolicy.Builder) obj;
                    anonymousClass1.getClass();
                    ZenPolicy build = builder.build();
                    String key = selectorWithWidgetPreference2.getKey();
                    boolean z = (selectorWithWidgetPreference2.mIsCheckBox && selectorWithWidgetPreference2.mChecked) ? false : true;
                    ZenModePrioritySendersPreferenceController zenModePrioritySendersPreferenceController = ZenModePrioritySendersPreferenceController.this;
                    int priorityMessageSenders = zenModePrioritySendersPreferenceController.mIsMessages ? build.getPriorityMessageSenders() : build.getPriorityCallSenders();
                    boolean z2 = zenModePrioritySendersPreferenceController.mIsMessages;
                    int priorityConversationSenders = z2 ? build.getPriorityConversationSenders() : 0;
                    int[] iArr = {0, 0};
                    int[] keyToSettingEndState = zenModePrioritySendersPreferenceController.keyToSettingEndState(key, z);
                    int i = keyToSettingEndState[0];
                    int i2 = keyToSettingEndState[1];
                    if (i != 0 && i != priorityMessageSenders) {
                        iArr[0] = i;
                    }
                    if (z2) {
                        if (i2 != 0 && i2 != priorityConversationSenders) {
                            iArr[1] = i2;
                        }
                        if (key.equals("conversations_important") && priorityMessageSenders == 1) {
                            iArr[0] = 4;
                        }
                        if ((key.equals("senders_starred_contacts") || key.equals("senders_contacts")) && priorityConversationSenders == 1) {
                            iArr[1] = 3;
                        }
                    }
                    int i3 = iArr[0];
                    int i4 = iArr[1];
                    if (i3 != 0) {
                        if (z2) {
                            builder.allowMessages(i3);
                        } else {
                            builder.allowCalls(i3);
                        }
                    }
                    if (z2 && i4 != 0) {
                        builder.allowConversations(i4);
                    }
                    return builder;
                }
            });
        }
    }

    /* renamed from: $r8$lambda$fC6F5aGNVjds-U5UJDBFUyMBJgg, reason: not valid java name */
    public static void m989$r8$lambda$fC6F5aGNVjdsU5UJDBFUyMBJgg(ZenModePrioritySendersPreferenceController zenModePrioritySendersPreferenceController, String str) {
        zenModePrioritySendersPreferenceController.getClass();
        if ("senders_starred_contacts".equals(str)) {
            Intent intent = STARRED_CONTACTS_INTENT;
            if (intent.resolveActivity(zenModePrioritySendersPreferenceController.mPackageManager) != null) {
                zenModePrioritySendersPreferenceController.mContext.startActivity(intent);
                return;
            }
        }
        if ("senders_contacts".equals(str)) {
            Intent intent2 = ALL_CONTACTS_INTENT;
            if (intent2.resolveActivity(zenModePrioritySendersPreferenceController.mPackageManager) != null) {
                zenModePrioritySendersPreferenceController.mContext.startActivity(intent2);
                return;
            }
        }
        if (!"conversations_important".equals(str)) {
            zenModePrioritySendersPreferenceController.mContext.startActivity(FALLBACK_INTENT);
            return;
        }
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(zenModePrioritySendersPreferenceController.mContext);
        String name = ConversationListSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 1837;
        subSettingLauncher.launch();
    }

    public ZenModePrioritySendersPreferenceController(Context context, String str, boolean z, ZenModesBackend zenModesBackend) {
        super(context, str, zenModesBackend);
        this.mNumImportantConversations = 0;
        this.mSelectorPreferences = new ArrayList();
        this.mSelectorClickListener = new AnonymousClass1();
        this.mIsMessages = z;
        String string = context.getString(R.string.config_contacts_package_name);
        ALL_CONTACTS_INTENT.setPackage(string);
        STARRED_CONTACTS_INTENT.setPackage(string);
        Intent intent = FALLBACK_INTENT;
        intent.setPackage(string);
        this.mPackageManager = this.mContext.getPackageManager();
        if (!intent.hasCategory("android.intent.category.APP_CONTACTS")) {
            intent.addCategory("android.intent.category.APP_CONTACTS");
        }
        this.mZenModeSummaryHelper = new ZenModeSummaryHelper(this.mContext, this.mBackend);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        PreferenceCategory preferenceCategory = (PreferenceCategory) preferenceScreen.findPreference(this.mKey);
        this.mPreferenceCategory = preferenceCategory;
        if (preferenceCategory.getPreferenceCount() == 0) {
            boolean z = this.mIsMessages;
            makeSelectorPreference(R.string.zen_mode_from_starred, "senders_starred_contacts", z);
            makeSelectorPreference(R.string.zen_mode_from_contacts, "senders_contacts", z);
            if (z) {
                makeSelectorPreference(R.string.zen_mode_from_important_conversations, "conversations_important", true);
            }
            makeSelectorPreference(R.string.zen_mode_from_anyone, "senders_anyone", z);
            makeSelectorPreference(R.string.zen_mode_none_messages, "senders_none", z);
        }
        super.displayPreference(preferenceScreen);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    public final int[] keyToSettingEndState(String str, boolean z) {
        int[] iArr = {0, 0};
        boolean z2 = this.mIsMessages;
        if (!z) {
            str.getClass();
            switch (str) {
                case "senders_starred_contacts":
                case "senders_contacts":
                case "senders_anyone":
                case "senders_none":
                    iArr[0] = 4;
                    break;
            }
            if (z2) {
                switch (str) {
                    case "conversations_important":
                    case "senders_anyone":
                    case "senders_none":
                        iArr[1] = 3;
                        break;
                }
            }
        } else {
            str.getClass();
            switch (str) {
                case "senders_starred_contacts":
                    iArr[0] = 3;
                    break;
                case "senders_contacts":
                    iArr[0] = 2;
                    break;
                case "senders_anyone":
                    iArr[0] = 1;
                    break;
                case "senders_none":
                    iArr[0] = 4;
                    break;
            }
            if (z2) {
                switch (str.hashCode()) {
                    case 660058867:
                        if (str.equals("conversations_important")) {
                            break;
                        }
                        break;
                    case 1725241211:
                        if (str.equals("senders_anyone")) {
                            break;
                        }
                        break;
                    case 1767544313:
                        if (str.equals("senders_none")) {
                            break;
                        }
                        break;
                }
                /*  JADX ERROR: Method code generation error
                    java.lang.NullPointerException: Switch insn not found in header
                    	at java.base/java.util.Objects.requireNonNull(Objects.java:235)
                    	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:246)
                    	at jadx.core.dex.regions.SwitchRegion.generate(SwitchRegion.java:84)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:140)
                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                    	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                    */
                /*
                    Method dump skipped, instructions count: 364
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.settings.notification.modes.ZenModePrioritySendersPreferenceController.keyToSettingEndState(java.lang.String, boolean):int[]");
            }

            public final void makeSelectorPreference(int i, final String str, boolean z) {
                SelectorWithWidgetPreference selectorWithWidgetPreference = new SelectorWithWidgetPreference(this.mPreferenceCategory.getContext(), z);
                selectorWithWidgetPreference.setKey(str);
                selectorWithWidgetPreference.setTitle(i);
                selectorWithWidgetPreference.mListener = this.mSelectorClickListener;
                View.OnClickListener onClickListener = null;
                if (("senders_contacts".equals(str) || "senders_starred_contacts".equals(str) || "conversations_important".equals(str)) && ((!"senders_starred_contacts".equals(str) || STARRED_CONTACTS_INTENT.resolveActivity(this.mPackageManager) != null || FALLBACK_INTENT.resolveActivity(this.mPackageManager) != null) && (!"senders_contacts".equals(str) || ALL_CONTACTS_INTENT.resolveActivity(this.mPackageManager) != null || FALLBACK_INTENT.resolveActivity(this.mPackageManager) != null))) {
                    onClickListener = new View.OnClickListener() { // from class: com.android.settings.notification.modes.ZenModePrioritySendersPreferenceController$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            ZenModePrioritySendersPreferenceController.m989$r8$lambda$fC6F5aGNVjdsU5UJDBFUyMBJgg(ZenModePrioritySendersPreferenceController.this, str);
                        }
                    };
                }
                if (onClickListener != null) {
                    selectorWithWidgetPreference.setExtraWidgetOnClickListener(onClickListener);
                }
                this.mPreferenceCategory.addPreference(selectorWithWidgetPreference);
                ((ArrayList) this.mSelectorPreferences).add(selectorWithWidgetPreference);
            }

            public final void updateChannelCounts$2() {
                ParceledListSlice emptyList;
                this.mBackend.getClass();
                try {
                    emptyList = ZenModesBackend.sINM.getConversations(true);
                } catch (Exception e) {
                    Log.w("ZenModeBackend", "Error calling NoMan", e);
                    emptyList = ParceledListSlice.emptyList();
                }
                int i = 0;
                if (emptyList != null) {
                    Iterator it = emptyList.getList().iterator();
                    while (it.hasNext()) {
                        if (!((ConversationChannelWrapper) it.next()).getNotificationChannel().isDemoted()) {
                            i++;
                        }
                    }
                }
                this.mNumImportantConversations = i;
            }

            @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
            public final void updateState(Preference preference, ZenMode zenMode) {
                boolean z = this.mIsMessages;
                if (z) {
                    updateChannelCounts$2();
                }
                ZenPolicy policy = zenMode.getPolicy();
                int priorityMessageSenders = z ? policy.getPriorityMessageSenders() : policy.getPriorityCallSenders();
                int priorityConversationSenders = z ? zenMode.getPolicy().getPriorityConversationSenders() : 0;
                Iterator it = ((ArrayList) this.mSelectorPreferences).iterator();
                while (it.hasNext()) {
                    SelectorWithWidgetPreference selectorWithWidgetPreference = (SelectorWithWidgetPreference) it.next();
                    int[] keyToSettingEndState = keyToSettingEndState(selectorWithWidgetPreference.getKey(), true);
                    int i = keyToSettingEndState[0];
                    int i2 = keyToSettingEndState[1];
                    boolean z2 = i == priorityMessageSenders;
                    if (z && i2 != 0) {
                        z2 = (z2 || i == 0) && i2 == priorityConversationSenders;
                    }
                    selectorWithWidgetPreference.setChecked(z2);
                }
                updateSummaries$1();
            }

            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            public final void updateSummaries$1() {
                char c;
                Iterator it = ((ArrayList) this.mSelectorPreferences).iterator();
                while (it.hasNext()) {
                    SelectorWithWidgetPreference selectorWithWidgetPreference = (SelectorWithWidgetPreference) it.next();
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
                    ZenModeSummaryHelper zenModeSummaryHelper = this.mZenModeSummaryHelper;
                    Cursor cursor = null;
                    r11 = null;
                    String format = null;
                    if (c == 0) {
                        ZenModesBackend zenModesBackend = zenModeSummaryHelper.mBackend;
                        zenModesBackend.getClass();
                        try {
                            cursor = zenModesBackend.mContext.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, new String[]{"display_name"}, "starred=1", null, "times_contacted");
                            List<String> starredContacts = zenModesBackend.getStarredContacts(cursor);
                            int size = starredContacts.size();
                            MessageFormat messageFormat = new MessageFormat(zenModeSummaryHelper.mContext.getString(R.string.zen_mode_starred_contacts_summary_contacts), Locale.getDefault());
                            HashMap hashMap = new HashMap();
                            hashMap.put("count", Integer.valueOf(size));
                            if (size >= 1) {
                                hashMap.put("contact_1", starredContacts.get(0));
                                if (size >= 2) {
                                    hashMap.put("contact_2", starredContacts.get(1));
                                    if (size == 3) {
                                        hashMap.put("contact_3", starredContacts.get(2));
                                    }
                                }
                            }
                            format = messageFormat.format(hashMap);
                        } finally {
                            if (cursor != null) {
                                cursor.close();
                            }
                        }
                    } else if (c == 1) {
                        zenModeSummaryHelper.getClass();
                        MessageFormat messageFormat2 = new MessageFormat(zenModeSummaryHelper.mContext.getString(R.string.zen_mode_contacts_count), Locale.getDefault());
                        HashMap hashMap2 = new HashMap();
                        hashMap2.put("count", Integer.valueOf(zenModeSummaryHelper.mBackend.mContext.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, new String[]{"display_name"}, null, null, null).getCount()));
                        format = messageFormat2.format(hashMap2);
                    } else if (c == 2) {
                        int i = this.mNumImportantConversations;
                        if (i != 0) {
                            MessageFormat messageFormat3 = new MessageFormat(this.mContext.getString(R.string.zen_mode_conversations_count), Locale.getDefault());
                            HashMap hashMap3 = new HashMap();
                            hashMap3.put("count", Integer.valueOf(i));
                            format = messageFormat3.format(hashMap3);
                        }
                    } else if (c == 3) {
                        format = this.mContext.getResources().getString(this.mIsMessages ? R.string.zen_mode_all_messages_summary : R.string.zen_mode_all_calls_summary);
                    }
                    selectorWithWidgetPreference.setSummary(format);
                }
            }
        }
