package com.android.settings.notification.app;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.notification.NotificationBackend;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.SecNotificationBlockManager;
import com.android.settingslib.notification.ConversationIconFactory;

import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class NotificationSettings extends DashboardFragment {
    public static final boolean DEBUG = Log.isLoggable("NotifiSettingsBase", 3);
    public NotificationBackend.AppRow mAppRow;
    public Bundle mArgs;
    public NotificationChannel mChannel;
    public NotificationChannelGroup mChannelGroup;
    public FragmentActivity mContext;
    public ConversationIconFactory.ConversationIconDrawable mConversationDrawable;
    public ShortcutInfo mConversationInfo;
    public Intent mIntent;
    public ViewGroup mLayoutView;
    public boolean mListeningToPackageRemove;
    public String mPkg;
    public PackageInfo mPkgInfo;
    public PackageManager mPm;
    public List mPreferenceFilter;
    public RestrictedLockUtils.EnforcedAdmin mSuspendedAppsAdmin;
    public int mUid;
    public final NotificationBackend mBackend = new NotificationBackend();
    public List mControllers = new ArrayList();
    public final DependentFieldListener mDependentFieldListener = new DependentFieldListener();
    public final List mDynamicPreferences = new ArrayList();
    public final AnonymousClass1 mOnGlobalLayoutListener =
            new ViewTreeObserver
                    .OnGlobalLayoutListener() { // from class:
                                                // com.android.settings.notification.app.NotificationSettings.1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    ViewGroup viewGroup = NotificationSettings.this.mLayoutView;
                    float height = viewGroup.getHeight();
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setDuration(
                            IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend);
                    animatorSet.setInterpolator(new DecelerateInterpolator());
                    animatorSet.playTogether(
                            ObjectAnimator.ofFloat(
                                    viewGroup,
                                    (Property<ViewGroup, Float>) View.TRANSLATION_Y,
                                    height,
                                    0.0f),
                            ObjectAnimator.ofFloat(
                                    viewGroup,
                                    (Property<ViewGroup, Float>) View.ALPHA,
                                    0.0f,
                                    1.0f));
                    ValueAnimator valueAnimator = new ValueAnimator();
                    valueAnimator.setFloatValues(0.0f, 1.0f);
                    animatorSet.play(valueAnimator);
                    animatorSet.start();
                    ViewGroup viewGroup2 = NotificationSettings.this.mLayoutView;
                    if (viewGroup2 != null) {
                        viewGroup2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            };
    public final AnonymousClass2 mPackageRemovedReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.settings.notification.app.NotificationSettings.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                    PackageInfo packageInfo = NotificationSettings.this.mPkgInfo;
                    if (packageInfo == null
                            || TextUtils.equals(packageInfo.packageName, schemeSpecificPart)) {
                        if (NotificationSettings.DEBUG) {
                            Log.d(
                                    "NotifiSettingsBase",
                                    "Package ("
                                            + schemeSpecificPart
                                            + ") removed. RemovingNotificationSettingsBase.");
                        }
                        NotificationSettings.this.getActivity().finishAndRemoveTask();
                    }
                }
            };
    public final NotificationSettings$$ExternalSyntheticLambda0 mChannelComparator =
            new NotificationSettings$$ExternalSyntheticLambda0();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DependentFieldListener {
        public DependentFieldListener() {}

        /* JADX WARN: Code restructure failed: missing block: B:18:0x0047, code lost:

           if (r0.getImportance() == 0) goto L25;
        */
        /* JADX WARN: Removed duplicated region for block: B:23:0x0064  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onFieldValueChanged() {
            /*
                r8 = this;
                com.android.settings.notification.app.NotificationSettings r8 = com.android.settings.notification.app.NotificationSettings.this
                com.android.settingslib.notification.ConversationIconFactory$ConversationIconDrawable r0 = r8.mConversationDrawable
                if (r0 == 0) goto L15
                android.app.NotificationChannel r1 = r8.mChannel
                boolean r1 = r1.isImportantConversation()
                boolean r2 = r0.mShowRing
                if (r1 == r2) goto L15
                r0.mShowRing = r1
                r0.invalidateSelf()
            L15:
                androidx.preference.PreferenceScreen r0 = r8.getPreferenceScreen()
                java.util.List r1 = r8.mControllers
                java.util.ArrayList r1 = (java.util.ArrayList) r1
                java.util.Iterator r1 = r1.iterator()
            L21:
                boolean r2 = r1.hasNext()
                if (r2 == 0) goto L31
                java.lang.Object r2 = r1.next()
                com.android.settings.notification.app.NotificationPreferenceController r2 = (com.android.settings.notification.app.NotificationPreferenceController) r2
                r2.displayPreference(r0)
                goto L21
            L31:
                r8.updatePreferenceStates()
                com.android.settings.notification.NotificationBackend$AppRow r0 = r8.mAppRow
                r1 = 0
                r2 = 1
                if (r0 == 0) goto L55
                boolean r0 = r0.banned
                if (r0 == 0) goto L3f
                goto L55
            L3f:
                android.app.NotificationChannel r0 = r8.mChannel
                if (r0 == 0) goto L4a
                int r0 = r0.getImportance()
                if (r0 != 0) goto L53
                goto L55
            L4a:
                android.app.NotificationChannelGroup r0 = r8.mChannelGroup
                if (r0 == 0) goto L53
                boolean r0 = r0.isBlocked()
                goto L56
            L53:
                r0 = r1
                goto L56
            L55:
                r0 = r2
            L56:
                java.util.List r3 = r8.mDynamicPreferences
                java.util.ArrayList r3 = (java.util.ArrayList) r3
                java.util.Iterator r3 = r3.iterator()
            L5e:
                boolean r4 = r3.hasNext()
                if (r4 == 0) goto L8a
                java.lang.Object r4 = r3.next()
                androidx.preference.Preference r4 = (androidx.preference.Preference) r4
                androidx.preference.PreferenceScreen r5 = r8.getPreferenceScreen()
                r6 = r0 ^ 1
                java.lang.String r7 = r4.getKey()
                androidx.preference.Preference r7 = r5.findPreference(r7)
                if (r7 == 0) goto L7c
                r7 = r2
                goto L7d
            L7c:
                r7 = r1
            L7d:
                if (r7 != r6) goto L80
                goto L5e
            L80:
                if (r6 == 0) goto L86
                r5.addPreference(r4)
                goto L5e
            L86:
                r5.removePreference(r4)
                goto L5e
            L8a:
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.settings.notification.app.NotificationSettings.DependentFieldListener.onFieldValueChanged():void");
        }
    }

    public final void animatePanel() {
        if (this.mPreferenceFilter != null) {
            ViewGroup viewGroup = (ViewGroup) getActivity().findViewById(R.id.main_content);
            this.mLayoutView = viewGroup;
            viewGroup.getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
        }
    }

    public final void collectConfigActivities() {
        Intent intent =
                new Intent("android.intent.action.MAIN")
                        .addCategory("android.intent.category.NOTIFICATION_PREFERENCES")
                        .setPackage(this.mAppRow.pkg);
        List<ResolveInfo> queryIntentActivities = this.mPm.queryIntentActivities(intent, 0);
        if (DEBUG) {
            StringBuilder sb = new StringBuilder("Found ");
            sb.append(queryIntentActivities.size());
            sb.append(" preference activities");
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    sb,
                    queryIntentActivities.size() == 0 ? " ;_;" : ApnSettings.MVNO_NONE,
                    "NotifiSettingsBase");
        }
        Iterator<ResolveInfo> it = queryIntentActivities.iterator();
        while (it.hasNext()) {
            ActivityInfo activityInfo = it.next().activityInfo;
            NotificationBackend.AppRow appRow = this.mAppRow;
            if (appRow.settingsIntent == null) {
                appRow.settingsIntent =
                        intent.setPackage(null)
                                .setClassName(activityInfo.packageName, activityInfo.name)
                                .addFlags(268435456);
                NotificationChannel notificationChannel = this.mChannel;
                if (notificationChannel != null) {
                    this.mAppRow.settingsIntent.putExtra(
                            "android.intent.extra.CHANNEL_ID", notificationChannel.getId());
                }
                NotificationChannelGroup notificationChannelGroup = this.mChannelGroup;
                if (notificationChannelGroup != null) {
                    this.mAppRow.settingsIntent.putExtra(
                            "android.intent.extra.CHANNEL_GROUP_ID",
                            notificationChannelGroup.getId());
                }
            } else if (DEBUG) {
                StringBuilder sb2 =
                        new StringBuilder("Ignoring duplicate notification preference activity (");
                sb2.append(activityInfo.name);
                sb2.append(") for package ");
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        sb2, activityInfo.packageName, "NotifiSettingsBase");
            }
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final boolean isPreferenceAnimationAllowed() {
        return false;
    }

    public final void loadAppRow() {
        NotificationBackend notificationBackend = this.mBackend;
        FragmentActivity fragmentActivity = this.mContext;
        PackageManager packageManager = this.mPm;
        PackageInfo packageInfo = this.mPkgInfo;
        notificationBackend.getClass();
        NotificationBackend.AppRow loadAppRow =
                NotificationBackend.loadAppRow(
                        fragmentActivity, packageManager, packageInfo.applicationInfo);
        String str = packageInfo.packageName;
        HashSet hashSet = SecNotificationBlockManager.mConfigCSCSet;
        if (hashSet.isEmpty()) {
            SecNotificationBlockManager.initConfigCSCSet(fragmentActivity);
        }
        Iterator it = hashSet.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String str2 = (String) it.next();
            if (str2 != null && !str2.contains(":") && str.equals(str2)) {
                loadAppRow.lockedImportance = true;
                loadAppRow.systemApp = true;
                break;
            }
        }
        loadAppRow.packageBlockable =
                SecNotificationBlockManager.isBlockablePackage(fragmentActivity, str);
        this.mAppRow = loadAppRow;
    }

    public final void loadChannel() {
        Intent intent = getActivity().getIntent();
        String stringExtra =
                intent != null ? intent.getStringExtra("android.provider.extra.CHANNEL_ID") : null;
        if (stringExtra == null && intent != null) {
            Bundle bundleExtra = intent.getBundleExtra(":settings:show_fragment_args");
            stringExtra =
                    bundleExtra != null
                            ? bundleExtra.getString("android.provider.extra.CHANNEL_ID")
                            : null;
        }
        String stringExtra2 =
                intent != null
                        ? intent.getStringExtra("android.provider.extra.CONVERSATION_ID")
                        : null;
        if (stringExtra == null) {
            Bundle bundle = this.mArgs;
            stringExtra =
                    bundle != null ? bundle.getString("android.provider.extra.CHANNEL_ID") : null;
        }
        if (stringExtra2 == null) {
            Bundle bundle2 = this.mArgs;
            stringExtra2 =
                    bundle2 != null
                            ? bundle2.getString("android.provider.extra.CONVERSATION_ID")
                            : null;
        }
        NotificationBackend notificationBackend = this.mBackend;
        String str = this.mPkg;
        int i = this.mUid;
        notificationBackend.getClass();
        NotificationChannel channel =
                NotificationBackend.getChannel(i, str, stringExtra, stringExtra2);
        this.mChannel = channel;
        if (channel == null) {
            NotificationBackend notificationBackend2 = this.mBackend;
            String str2 = this.mPkg;
            int i2 = this.mUid;
            notificationBackend2.getClass();
            NotificationBackend.getChannel(i2, str2, stringExtra, null);
        }
    }

    public final void loadChannelGroup() {
        NotificationChannel notificationChannel;
        NotificationBackend notificationBackend = this.mBackend;
        NotificationBackend.AppRow appRow = this.mAppRow;
        String str = appRow.pkg;
        int i = appRow.uid;
        notificationBackend.getClass();
        boolean z =
                NotificationBackend.onlyHasDefaultChannel(i, str)
                        || ((notificationChannel = this.mChannel) != null
                                && "miscellaneous".equals(notificationChannel.getId()));
        NotificationChannelGroup notificationChannelGroup = null;
        if (z) {
            NotificationBackend notificationBackend2 = this.mBackend;
            NotificationBackend.AppRow appRow2 = this.mAppRow;
            String str2 = appRow2.pkg;
            int i2 = appRow2.uid;
            notificationBackend2.getClass();
            this.mChannel = NotificationBackend.getChannel(i2, str2, "miscellaneous", null);
        }
        NotificationChannel notificationChannel2 = this.mChannel;
        if (notificationChannel2 == null || TextUtils.isEmpty(notificationChannel2.getGroup())) {
            return;
        }
        NotificationBackend notificationBackend3 = this.mBackend;
        String str3 = this.mPkg;
        int i3 = this.mUid;
        String group = this.mChannel.getGroup();
        notificationBackend3.getClass();
        if (group != null) {
            try {
                notificationChannelGroup =
                        NotificationBackend.sINM.getNotificationChannelGroupForPackage(
                                group, str3, i3);
            } catch (Exception e) {
                Log.w("NotificationBackend", "Error calling NoMan", e);
            }
        }
        if (notificationChannelGroup != null) {
            this.mChannelGroup = notificationChannelGroup;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x011e A[LOOP:1: B:43:0x0118->B:45:0x011e, LOOP_END] */
    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onAttach(android.content.Context r10) {
        /*
            Method dump skipped, instructions count: 310
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.notification.app.NotificationSettings.onAttach(android.content.Context):void");
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.mIntent == null && this.mArgs == null) {
            Log.w("NotifiSettingsBase", "No intent");
            Toast.makeText(this.mContext, R.string.app_not_found_dlg_text, 0).show();
            getActivity().finish();
        } else if (this.mUid < 0 || TextUtils.isEmpty(this.mPkg) || this.mPkgInfo == null) {
            Log.w("NotifiSettingsBase", "Missing package or uid or packageinfo");
            Toast.makeText(this.mContext, R.string.app_not_found_dlg_text, 0).show();
            getActivity().finish();
        } else {
            if (this.mListeningToPackageRemove) {
                return;
            }
            this.mListeningToPackageRemove = true;
            IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addDataScheme("package");
            getContext().registerReceiver(this.mPackageRemovedReceiver, intentFilter);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        if (this.mListeningToPackageRemove) {
            this.mListeningToPackageRemove = false;
            getContext().unregisterReceiver(this.mPackageRemovedReceiver);
        }
        super.onDestroy();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.mUid < 0
                || TextUtils.isEmpty(this.mPkg)
                || this.mPkgInfo == null
                || this.mAppRow == null) {
            Log.w("NotifiSettingsBase", "Missing package or uid or packageinfo");
            finish();
            return;
        }
        loadAppRow();
        if (this.mAppRow == null) {
            Log.w("NotifiSettingsBase", "Can't load package");
            finish();
            return;
        }
        loadChannel();
        NotificationChannel notificationChannel = this.mChannel;
        if (notificationChannel != null
                && !TextUtils.isEmpty(notificationChannel.getConversationId())
                && !this.mChannel.isDemoted()) {
            NotificationBackend notificationBackend = this.mBackend;
            FragmentActivity fragmentActivity = this.mContext;
            String str = this.mPkg;
            int i = this.mUid;
            String conversationId = this.mChannel.getConversationId();
            notificationBackend.getClass();
            List<ShortcutInfo> shortcuts =
                    ((LauncherApps) fragmentActivity.getSystemService(LauncherApps.class))
                            .getShortcuts(
                                    new LauncherApps.ShortcutQuery()
                                            .setPackage(str)
                                            .setQueryFlags(1041)
                                            .setShortcutIds(Arrays.asList(conversationId)),
                                    UserHandle.of(UserHandle.getUserId(i)));
            ShortcutInfo shortcutInfo =
                    (shortcuts == null || shortcuts.isEmpty()) ? null : shortcuts.get(0);
            this.mConversationInfo = shortcutInfo;
            if (shortcutInfo != null) {
                NotificationBackend notificationBackend2 = this.mBackend;
                FragmentActivity fragmentActivity2 = this.mContext;
                NotificationBackend.AppRow appRow = this.mAppRow;
                String str2 = appRow.pkg;
                int i2 = appRow.uid;
                boolean isImportantConversation = this.mChannel.isImportantConversation();
                notificationBackend2.getClass();
                this.mConversationDrawable =
                        NotificationBackend.getConversationDrawable(
                                fragmentActivity2, shortcutInfo, str2, i2, isImportantConversation);
            }
        }
        loadChannelGroup();
        Intent intent = getActivity().getIntent();
        this.mPreferenceFilter =
                intent != null
                        ? intent.getStringArrayListExtra(
                                "android.provider.extra.CHANNEL_FILTER_LIST")
                        : null;
        collectConfigActivities();
    }
}
