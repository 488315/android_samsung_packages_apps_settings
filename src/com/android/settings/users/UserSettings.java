package com.android.settings.users;

import android.R;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlendMode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.ContactsContract;
import android.provider.SearchIndexableData;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;
import android.util.secutil.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SeslSwitchBar;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.internal.util.UserIcons;
import com.android.internal.widget.LockPatternUtils;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.MainSwitchBarController;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.drawable.CircleFramedDrawable;
import com.android.settingslib.search.SearchIndexableRaw;
import com.android.settingslib.users.CreateUserDialogController;
import com.android.settingslib.users.EditUserInfoController;
import com.android.settingslib.users.EditUserPhotoController;
import com.android.settingslib.users.UserCreatingDialog;
import com.android.settingslib.utils.CustomDialogHelper;
import com.android.settingslib.utils.ThreadUtils;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.widget.SecCustomDividerItemDecorator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UserSettings extends SettingsPreferenceFragment
        implements Preference.OnPreferenceClickListener,
                MultiUserSwitchBarController.OnMultiUserSwitchChangedListener,
                DialogInterface.OnDismissListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER;
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER;
    public static final IntentFilter USER_REMOVED_INTENT_FILTER;
    public static final SparseArray sDarkDefaultUserBitmapCache;
    RestrictedPreference mAddGuest;
    RestrictedPreference mAddSupervisedUser;
    RestrictedPreference mAddUser;
    public AddUserWhenLockedPreferenceController mAddUserWhenLockedPreferenceController;
    public boolean mAddingUser;
    public String mAddingUserName;
    public String mConfigSupervisedUserCreationPackage;
    public final CreateUserDialogController mCreateUserDialogController;
    public CircleFramedDrawable mDefaultIconDrawable;
    public final UserSettings$$ExternalSyntheticLambda0 mDesktopModeListener;
    public SemDesktopModeManager mDesktopModeManager;
    public Dialog mDialog;
    public final EditUserInfoController mEditUserInfoController;
    public final ExecutorService mExecutor;
    PreferenceGroup mGuestCategory;
    public final AtomicBoolean mGuestCreationScheduled;
    Preference mGuestExitPreference;
    Preference mGuestResetPreference;
    public boolean mGuestUserAutoCreated;
    PreferenceGroup mGuestUserCategory;
    public final AnonymousClass1 mHandler;
    UserPreference mMePreference;
    public MultiUserTopIntroPreferenceController mMultiUserTopIntroPreferenceController;
    public Drawable mPendingUserIcon;
    public boolean mPendingUserIsAdmin;
    public CharSequence mPendingUserName;
    public MultiUserSwitchBarController mSwitchBarController;
    public TimeoutToDockUserPreferenceController mTimeoutToDockUserPreferenceController;
    public UserCapabilities mUserCaps;
    public final AnonymousClass2 mUserChangeReceiver;
    public UserCreatingDialog mUserCreatingDialog;
    PreferenceGroup mUserListCategory;
    public PreferenceGroup mUserListMeCategory;
    public UserManager mUserManager;
    SparseArray<Bitmap> mUserIcons = new SparseArray<>();
    public int mRemovingUserId = -1;
    public boolean mShouldUpdateUserList = true;
    public final Object mUserLock = new Object();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.users.UserSettings$11, reason: invalid class name */
    public final class AnonymousClass11 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            int size = ((UserManager) context.getSystemService("user")).getUsers().size();
            String valueOf = String.valueOf(57512);
            String valueOf2 = String.valueOf(size);
            StatusData statusData = new StatusData();
            statusData.mStatusValue = valueOf2;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.users.UserSettings$12, reason: invalid class name */
    public final class AnonymousClass12 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (UserHandle.myUserId() != 0) {
                ArrayList arrayList = (ArrayList) nonIndexableKeys;
                arrayList.add("guest_add");
                arrayList.add("user_add");
            }
            if (TextUtils.isEmpty(
                    context.getResources().getString(R.string.face_acquired_too_left))) {
                ((ArrayList) nonIndexableKeys).add("supervised_user_add");
            }
            UserCapabilities create = UserCapabilities.create(context);
            boolean anyMatch =
                    ((UserManager) context.getSystemService("user"))
                            .getAliveUsers().stream()
                                    .anyMatch(new UserSettings$$ExternalSyntheticLambda1(2));
            if ((!create.mCanAddUser && !create.mDisallowAddUserSetByAdmin)
                    || !WizardManagerHelper.isDeviceProvisioned(context)
                    || !create.mUserSwitcherEnabled) {
                ((ArrayList) nonIndexableKeys).add("user_add");
            }
            if (anyMatch
                    || !create.mCanAddGuest
                    || !WizardManagerHelper.isDeviceProvisioned(context)
                    || !create.mUserSwitcherEnabled) {
                ((ArrayList) nonIndexableKeys).add("guest_add");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List getNonIndexableKeysFromXml(Context context, int i, boolean z) {
            List<String> nonIndexableKeysFromXml = super.getNonIndexableKeysFromXml(context, i, z);
            new AddUserWhenLockedPreferenceController(
                            context, "user_settings_add_users_when_locked")
                    .updateNonIndexableKeys(nonIndexableKeysFromXml);
            new AutoSyncDataPreferenceController(context, null)
                    .updateNonIndexableKeys(nonIndexableKeysFromXml);
            new AutoSyncPersonalDataPreferenceController(context, null)
                    .updateNonIndexableKeys(nonIndexableKeysFromXml);
            new AutoSyncWorkDataPreferenceController(context, null)
                    .updateNonIndexableKeys(nonIndexableKeysFromXml);
            if (z) {
                nonIndexableKeysFromXml.add("allow_multiple_users");
            }
            return nonIndexableKeysFromXml;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            if (!UserManager.supportsMultipleUsers()) {
                return arrayList;
            }
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            ((SearchIndexableData) searchIndexableRaw).key = "user_settings_screen";
            searchIndexableRaw.title =
                    context.getString(com.android.settings.R.string.user_settings_title);
            searchIndexableRaw.keywords =
                    context.getString(com.android.settings.R.string.multiple_users_title_keywords);
            searchIndexableRaw.screenTitle =
                    context.getString(com.android.settings.R.string.user_settings_title);
            arrayList.add(searchIndexableRaw);
            SearchIndexableRaw searchIndexableRaw2 = new SearchIndexableRaw(context);
            Resources resources = context.getResources();
            if (!UserCapabilities.create(context).mCanAddRestrictedProfile) {
                ((SearchIndexableData) searchIndexableRaw2).key = "user_add";
                searchIndexableRaw2.title =
                        resources.getString(com.android.settings.R.string.user_add_user);
                searchIndexableRaw2.screenTitle =
                        resources.getString(com.android.settings.R.string.user_settings_title);
                arrayList.add(searchIndexableRaw2);
            }
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            if ((Rune.supportDesktopMode() && Rune.isSamsungDexMode(context))
                    || !Rune.supportUserSettings(context)) {
                return false;
            }
            UserCapabilities create = UserCapabilities.create(context);
            return create.mEnabled && create.mUserSwitcherEnabled;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.users.UserSettings$3, reason: invalid class name */
    public final class AnonymousClass3 extends AsyncTask {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ UserSettings this$0;

        public /* synthetic */ AnonymousClass3(UserSettings userSettings, int i) {
            this.$r8$classId = i;
            this.this$0 = userSettings;
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            switch (this.$r8$classId) {
                case 0:
                    UserInfo userInfo = this.this$0.mUserManager.getUserInfo(UserHandle.myUserId());
                    String str = userInfo.iconPath;
                    if (str == null || str.equals(ApnSettings.MVNO_NONE)) {
                        UserSettings.copyMeProfilePhoto(this.this$0.getActivity(), userInfo);
                    }
                    return userInfo.name;
                default:
                    Iterator it = ((List[]) objArr)[0].iterator();
                    while (it.hasNext()) {
                        int intValue = ((Integer) it.next()).intValue();
                        Bitmap userIcon = this.this$0.mUserManager.getUserIcon(intValue);
                        if (userIcon == null) {
                            userIcon =
                                    UserSettings.getDefaultUserIconAsBitmap(
                                            this.this$0.getContext().getResources(), intValue);
                        }
                        this.this$0.mUserIcons.append(intValue, userIcon);
                    }
                    return null;
            }
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            switch (this.$r8$classId) {
                case 0:
                    String str = (String) obj;
                    UserSettings userSettings = this.this$0;
                    IntentFilter intentFilter = UserSettings.USER_REMOVED_INTENT_FILTER;
                    if (userSettings.getActivity() != null) {
                        userSettings.mMePreference.setTitle(str);
                        int myUserId = UserHandle.myUserId();
                        Bitmap userIcon = userSettings.mUserManager.getUserIcon(myUserId);
                        if (userIcon != null) {
                            userSettings.mMePreference.setIcon(
                                    new CircleFramedDrawable(
                                            userIcon,
                                            userSettings
                                                    .getResources()
                                                    .getDimensionPixelSize(
                                                            com.android.settings.R.dimen
                                                                    .user_icon_size)));
                            userSettings.mUserIcons.put(myUserId, userIcon);
                            break;
                        }
                    }
                    break;
                default:
                    this.this$0.updateUserList();
                    break;
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$H_6GDMyHVA7xwG1CoJJq21CpTnc(
            UserSettings userSettings, Context context, UserCreatingDialog userCreatingDialog) {
        userSettings.mMetricsFeatureProvider.action(userSettings.getActivity(), 1764, new Pair[0]);
        Trace.beginSection("UserSettings.addGuest");
        UserInfo createGuest = userSettings.mUserManager.createGuest(context);
        if (createGuest != null) {
            userSettings.mUserManager.setUserIcon(
                    createGuest.id,
                    getDefaultUserIconAsBitmap(userSettings.getContext().getResources(), -10000));
        }
        Trace.endSection();
        ThreadUtils.postOnMainThread(
                new UserSettings$$ExternalSyntheticLambda9(
                        userSettings, userCreatingDialog, createGuest, context));
    }

    static {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.USER_REMOVED");
        USER_REMOVED_INTENT_FILTER = intentFilter;
        intentFilter.addAction("android.intent.action.USER_INFO_CHANGED");
        sDarkDefaultUserBitmapCache = new SparseArray();
        STATUS_LOGGING_PROVIDER = new AnonymousClass11();
        SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass12(com.android.settings.R.xml.user_settings);
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.settings.users.UserSettings$1] */
    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.settings.users.UserSettings$2] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.settings.users.UserSettings$$ExternalSyntheticLambda0] */
    public UserSettings() {
        EditUserInfoController editUserInfoController = new EditUserInfoController();
        editUserInfoController.mWaitingForActivityResult = false;
        editUserInfoController.mMaxToast = null;
        this.mEditUserInfoController = editUserInfoController;
        CreateUserDialogController createUserDialogController = new CreateUserDialogController();
        createUserDialogController.mMaxToast = null;
        this.mCreateUserDialogController = createUserDialogController;
        this.mGuestCreationScheduled = new AtomicBoolean();
        this.mExecutor = Executors.newSingleThreadExecutor();
        new ArrayList();
        this.mDesktopModeListener =
                new SemDesktopModeManager
                        .DesktopModeListener() { // from class:
                                                 // com.android.settings.users.UserSettings$$ExternalSyntheticLambda0
                    public final void onDesktopModeStateChanged(
                            SemDesktopModeState semDesktopModeState) {
                        UserSettings userSettings = UserSettings.this;
                        IntentFilter intentFilter = UserSettings.USER_REMOVED_INTENT_FILTER;
                        userSettings.getClass();
                        if (semDesktopModeState.enabled == 4) {
                            Log.i("UserSettings", "DesktopMode Enabled, Finish User settings");
                            userSettings
                                    .getActivity()
                                    .runOnUiThread(
                                            new UserSettings$$ExternalSyntheticLambda4(
                                                    userSettings, 3));
                        }
                    }
                };
        this.mHandler =
                new Handler() { // from class: com.android.settings.users.UserSettings.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        int i = message.what;
                        UserSettings userSettings = UserSettings.this;
                        if (i == 1) {
                            userSettings.updateUserList();
                        } else {
                            if (i != 4) {
                                return;
                            }
                            Toast.makeText(
                                            userSettings.getActivity(),
                                            com.android.settings.R.string
                                                    .sec_user_mode_add_toast_on_during_call,
                                            1)
                                    .show();
                        }
                    }
                };
        this.mUserChangeReceiver =
                new BroadcastReceiver() { // from class: com.android.settings.users.UserSettings.2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        int intExtra;
                        if (intent.getAction().equals("android.intent.action.USER_REMOVED")) {
                            UserSettings.this.mRemovingUserId = -1;
                        } else if (intent.getAction()
                                        .equals("android.intent.action.USER_INFO_CHANGED")
                                && (intExtra =
                                                intent.getIntExtra(
                                                        "android.intent.extra.user_handle", -1))
                                        != -1) {
                            UserSettings.this.mUserIcons.remove(intExtra);
                        }
                        sendEmptyMessage(1);
                    }
                };
    }

    public static boolean assignDefaultPhoto(Context context, int i) {
        if (context == null) {
            return false;
        }
        ((UserManager) context.getSystemService("user"))
                .setUserIcon(i, getDefaultUserIconAsBitmap(context.getResources(), i));
        return true;
    }

    public static void copyMeProfilePhoto(Context context, UserInfo userInfo) {
        Uri uri = ContactsContract.Profile.CONTENT_URI;
        int myUserId = userInfo != null ? userInfo.id : UserHandle.myUserId();
        InputStream openContactPhotoInputStream =
                ContactsContract.Contacts.openContactPhotoInputStream(
                        context.getContentResolver(), uri, true);
        if (openContactPhotoInputStream == null) {
            assignDefaultPhoto(context, myUserId);
            return;
        }
        UserManager userManager = (UserManager) context.getSystemService("user");
        Bitmap decodeStream = BitmapFactory.decodeStream(openContactPhotoInputStream);
        int i = CircleFramedDrawable.$r8$clinit;
        userManager.setUserIcon(
                myUserId,
                UserIcons.convertToBitmap(
                        new CircleFramedDrawable(
                                decodeStream,
                                context.getResources()
                                        .getDimensionPixelSize(
                                                com.android.settings.R.dimen
                                                        .update_user_photo_popup_min_width))));
        try {
            openContactPhotoInputStream.close();
        } catch (IOException unused) {
        }
    }

    public static Bitmap getDefaultUserIconAsBitmap(Resources resources, int i) {
        SparseArray sparseArray = sDarkDefaultUserBitmapCache;
        Bitmap bitmap = (Bitmap) sparseArray.get(i);
        if (bitmap != null) {
            return bitmap;
        }
        Bitmap convertToBitmapAtUserIconSize =
                UserIcons.convertToBitmapAtUserIconSize(
                        resources, UserIcons.getDefaultUserIcon(resources, i, false));
        sparseArray.put(i, convertToBitmapAtUserIconSize);
        return convertToBitmapAtUserIconSize;
    }

    public static void updateDialogAnchorView$2(Dialog dialog, Preference preference) {
        if (preference == null || dialog == null) {
            return;
        }
        Rect rect = new Rect();
        preference.seslGetPreferenceBounds(rect);
        int width = (rect.width() / 2) + rect.left;
        int i = rect.bottom;
        if (width <= 0 || i <= 0) {
            return;
        }
        dialog.semSetAnchor(width, i);
    }

    public final void addUserNow(int i) {
        String str;
        Trace.beginAsyncSection("UserSettings.addUserNow", 0);
        synchronized (this.mUserLock) {
            try {
                this.mAddingUser = true;
                if (i == 1) {
                    CharSequence charSequence = this.mPendingUserName;
                    str =
                            charSequence != null
                                    ? ((String) charSequence).toString()
                                    : getString(com.android.settings.R.string.user_new_user_name);
                } else {
                    CharSequence charSequence2 = this.mPendingUserName;
                    str =
                            charSequence2 != null
                                    ? ((String) charSequence2).toString()
                                    : getString(
                                            com.android.settings.R.string.user_new_profile_name);
                }
                this.mAddingUserName = str;
            } catch (Throwable th) {
                throw th;
            }
        }
        UserCreatingDialog userCreatingDialog = new UserCreatingDialog(getActivity(), false);
        this.mUserCreatingDialog = userCreatingDialog;
        userCreatingDialog.show();
        createUser(i, this.mAddingUserName);
    }

    public final Dialog buildAddUserDialog(final int i) {
        Dialog createDialog;
        synchronized (this.mUserLock) {
            try {
                createDialog =
                        i == 2
                                ? this.mEditUserInfoController.createDialog(
                                        getActivity(),
                                        new UserSettings$$ExternalSyntheticLambda10(this),
                                        null,
                                        ((String) this.mPendingUserName).toString(),
                                        new BiConsumer() { // from class:
                                                           // com.android.settings.users.UserSettings$$ExternalSyntheticLambda13
                                            @Override // java.util.function.BiConsumer
                                            public final void accept(Object obj, Object obj2) {
                                                UserSettings userSettings = UserSettings.this;
                                                int i2 = i;
                                                userSettings.mPendingUserIcon = (Drawable) obj2;
                                                userSettings.mPendingUserName = (String) obj;
                                                userSettings.addUserNow(i2);
                                            }
                                        },
                                        new UserSettings$$ExternalSyntheticLambda4(this, 1))
                                : this.mCreateUserDialogController.createDialog(
                                        getActivity(),
                                        new UserSettings$$ExternalSyntheticLambda10(this),
                                        UserManager.isMultipleAdminEnabled(),
                                        new UserSettings$$ExternalSyntheticLambda15(this, i),
                                        new UserSettings$$ExternalSyntheticLambda4(this, 2));
            } catch (Throwable th) {
                throw th;
            }
        }
        return createDialog;
    }

    public final boolean canSwitchUserNow$1() {
        return this.mUserManager.getUserSwitchability() == 0;
    }

    public void clearAndExitGuest() {
        if (this.mUserCaps.mIsGuest) {
            this.mMetricsFeatureProvider.action(getActivity(), 1763, new Pair[0]);
            int myUserId = UserHandle.myUserId();
            if (!this.mUserManager.markGuestForDeletion(myUserId)) {
                RecordingInputConnection$$ExternalSyntheticOutline0.m(
                        myUserId, "Couldn't mark the guest for deletion for user ", "UserSettings");
                return;
            }
            removeThisUser$1();
            if (this.mGuestUserAutoCreated) {
                scheduleGuestCreation();
            }
        }
    }

    public void createUser(final int i, final String str) {
        final Context context = getContext();
        final Resources resources = getResources();
        final Drawable drawable = this.mPendingUserIcon;
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.android.settings.users.UserSettings$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        final UserSettings userSettings = UserSettings.this;
                        int i2 = i;
                        String str2 = str;
                        final Drawable drawable2 = drawable;
                        final Resources resources2 = resources;
                        final Context context2 = context;
                        IntentFilter intentFilter = UserSettings.USER_REMOVED_INTENT_FILTER;
                        UserInfo userInfo = null;
                        if (i2 == 1) {
                            if (Utils.isOnCall(userSettings.getActivity())) {
                                userSettings.mHandler.sendEmptyMessage(4);
                            } else {
                                userInfo =
                                        userSettings.mUserManager.createUser(
                                                str2, "android.os.usertype.full.SECONDARY", 0);
                            }
                            if (userSettings.mPendingUserIsAdmin) {
                                userSettings.mUserManager.setUserAdmin(userInfo.id);
                            }
                        } else if (Utils.isOnCall(userSettings.getActivity())) {
                            userSettings.mHandler.sendEmptyMessage(4);
                        } else {
                            userInfo = userSettings.mUserManager.createRestrictedProfile(str2);
                        }
                        final UserInfo userInfo2 = userInfo;
                        ThreadUtils.postOnMainThread(
                                new Runnable() { // from class:
                                                 // com.android.settings.users.UserSettings$$ExternalSyntheticLambda17
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        UserSettings userSettings2 = UserSettings.this;
                                        UserInfo userInfo3 = userInfo2;
                                        Drawable drawable3 = drawable2;
                                        Resources resources3 = resources2;
                                        Context context3 = context2;
                                        if (userInfo3 == null) {
                                            userSettings2.mAddingUser = false;
                                            userSettings2.mPendingUserIcon = null;
                                            userSettings2.mPendingUserName = null;
                                            Toast.makeText(
                                                            userSettings2.getContext(),
                                                            com.android.settings.R.string
                                                                    .add_user_failed,
                                                            0)
                                                    .show();
                                            UserCreatingDialog userCreatingDialog =
                                                    userSettings2.mUserCreatingDialog;
                                            if (userCreatingDialog == null
                                                    || !userCreatingDialog.isShowing()) {
                                                return;
                                            }
                                            userSettings2.mUserCreatingDialog.dismiss();
                                            return;
                                        }
                                        IntentFilter intentFilter2 =
                                                UserSettings.USER_REMOVED_INTENT_FILTER;
                                        userSettings2.getClass();
                                        ThreadUtils.postOnBackgroundThread(
                                                new UserSettings$$ExternalSyntheticLambda9(
                                                        userSettings2,
                                                        drawable3,
                                                        resources3,
                                                        userInfo3));
                                        userSettings2.mPendingUserIcon = null;
                                        userSettings2.mPendingUserName = null;
                                        UserCreatingDialog userCreatingDialog2 =
                                                userSettings2.mUserCreatingDialog;
                                        if (userCreatingDialog2 != null
                                                && userCreatingDialog2.isShowing()) {
                                            userSettings2.mUserCreatingDialog.dismiss();
                                        }
                                        userSettings2.mAddingUser = false;
                                        userSettings2.openUserDetails(userInfo3, true, context3);
                                    }
                                });
                    }
                });
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final int getDialogMetricsCategory(int i) {
        if (i == 1) {
            return 591;
        }
        if (i == 2) {
            return 595;
        }
        if (i == 16) {
            return 2000;
        }
        switch (i) {
            case 5:
                return 594;
            case 6:
                return 598;
            case 7:
                return 599;
            case 8:
            case 12:
                return 600;
            case 9:
            case 10:
            case 11:
                return 601;
            default:
                return 0;
        }
    }

    public final Drawable getEncircledDefaultIcon() {
        if (this.mDefaultIconDrawable == null) {
            this.mDefaultIconDrawable =
                    new CircleFramedDrawable(
                            getDefaultUserIconAsBitmap(getContext().getResources(), -10000),
                            getResources()
                                    .getDimensionPixelSize(
                                            com.android.settings.R.dimen.user_icon_size));
        }
        return this.mDefaultIconDrawable;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 96;
    }

    public int getRealUsersCount() {
        return (int)
                this.mUserManager.getUsers().stream()
                        .filter(new UserSettings$$ExternalSyntheticLambda1(0))
                        .count();
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        SettingsMainSwitchBar settingsMainSwitchBar = settingsActivity.mMainSwitch;
        if (this.mUserCaps.mIsGuest) {
            settingsMainSwitchBar.hide();
        } else {
            settingsMainSwitchBar.show();
        }
        if (this.mSwitchBarController != null) {
            getSettingsLifecycle().removeObserver(this.mSwitchBarController);
        }
        this.mSwitchBarController =
                new MultiUserSwitchBarController(
                        settingsActivity, new MainSwitchBarController(settingsMainSwitchBar), this);
        getSettingsLifecycle().addObserver(this.mSwitchBarController);
        boolean booleanExtra =
                getIntent().getBooleanExtra("EXTRA_OPEN_DIALOG_USER_PROFILE_EDITOR", false);
        if (((SeslSwitchBar) settingsMainSwitchBar).mSwitch.isChecked() && booleanExtra) {
            showDialog(9);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 10) {
            if (i2 == 0 || !new LockPatternUtils(getActivity()).isSecure(UserHandle.myUserId())) {
                return;
            }
            addUserNow(2);
            return;
        }
        if (this.mGuestUserAutoCreated && i == 11 && i2 == 100) {
            scheduleGuestCreation();
            return;
        }
        CreateUserDialogController createUserDialogController = this.mCreateUserDialogController;
        createUserDialogController.mWaitingForActivityResult = false;
        EditUserPhotoController editUserPhotoController =
                createUserDialogController.mEditUserPhotoController;
        if (editUserPhotoController != null) {
            editUserPhotoController.onActivityResult(i, i2, intent);
        }
        EditUserInfoController editUserInfoController = this.mEditUserInfoController;
        editUserInfoController.mWaitingForActivityResult = false;
        EditUserPhotoController editUserPhotoController2 =
                editUserInfoController.mEditUserPhotoController;
        if (editUserPhotoController2 == null
                || editUserInfoController.mEditUserInfoDialog == null) {
            return;
        }
        editUserPhotoController2.onActivityResult(i, i2, intent);
    }

    public final void onAddUserClicked(int i) {
        synchronized (this.mUserLock) {
            try {
                if (this.mRemovingUserId == -1 && !this.mAddingUser) {
                    if (i == 1) {
                        showDialog(2);
                    } else if (i == 2) {
                        if (new LockPatternUtils(getActivity()).isSecure(UserHandle.myUserId())) {
                            showDialog(11);
                        } else {
                            showDialog(7);
                        }
                    }
                }
            } finally {
            }
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(com.android.settings.R.xml.user_settings);
        FragmentActivity activity = getActivity();
        if (!WizardManagerHelper.isDeviceProvisioned(activity)) {
            activity.finish();
            return;
        }
        this.mDesktopModeManager =
                (SemDesktopModeManager) getActivity().getSystemService("desktopmode");
        this.mGuestUserAutoCreated =
                getPrefContext().getResources().getBoolean(R.bool.config_imeDrawsImeNavBar);
        this.mAddUserWhenLockedPreferenceController =
                new AddUserWhenLockedPreferenceController(
                        activity, "user_settings_add_users_when_locked");
        this.mMultiUserTopIntroPreferenceController =
                new MultiUserTopIntroPreferenceController(activity, "multiuser_top_intro");
        this.mTimeoutToDockUserPreferenceController =
                new TimeoutToDockUserPreferenceController(
                        activity, "timeout_to_dock_user_preference");
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        this.mAddUserWhenLockedPreferenceController.displayPreference(preferenceScreen);
        this.mMultiUserTopIntroPreferenceController.displayPreference(preferenceScreen);
        this.mTimeoutToDockUserPreferenceController.displayPreference(preferenceScreen);
        preferenceScreen
                .findPreference(this.mAddUserWhenLockedPreferenceController.getPreferenceKey())
                .setOnPreferenceChangeListener(this.mAddUserWhenLockedPreferenceController);
        if (bundle != null) {
            if (bundle.containsKey("removing_user")) {
                this.mRemovingUserId = bundle.getInt("removing_user");
            }
            if (bundle.containsKey("create_user")) {
                CreateUserDialogController createUserDialogController =
                        this.mCreateUserDialogController;
                createUserDialogController.getClass();
                createUserDialogController.mCachedDrawablePath = bundle.getString("pending_photo");
                createUserDialogController.mCurrentState = bundle.getInt("current_state");
                if (bundle.containsKey("admin_status")) {
                    createUserDialogController.mIsAdmin =
                            Boolean.valueOf(bundle.getBoolean("admin_status"));
                }
                createUserDialogController.mSavedName = bundle.getString("saved_name");
                createUserDialogController.mWaitingForActivityResult =
                        bundle.getBoolean("awaiting_result", false);
            } else {
                EditUserInfoController editUserInfoController = this.mEditUserInfoController;
                editUserInfoController.getClass();
                String string = bundle.getString("pending_photo");
                if (string != null) {
                    editUserInfoController.mSavedPhoto =
                            BitmapFactory.decodeFile(new File(string).getAbsolutePath());
                }
                editUserInfoController.mWaitingForActivityResult =
                        bundle.getBoolean("awaiting_result", false);
            }
        }
        this.mUserCaps = UserCapabilities.create(activity);
        this.mUserManager = (UserManager) activity.getSystemService("user");
        if (this.mUserCaps.mEnabled) {
            int myUserId = UserHandle.myUserId();
            this.mUserListMeCategory = (PreferenceGroup) findPreference("user_list_me");
            this.mUserListCategory = (PreferenceGroup) findPreference("user_list");
            UserPreference userPreference = new UserPreference(getPrefContext(), null, myUserId);
            this.mMePreference = userPreference;
            userPreference.setKey("user_me");
            this.mMePreference.setOnPreferenceClickListener(this);
            RestrictedPreference restrictedPreference =
                    (RestrictedPreference) findPreference("guest_add");
            this.mAddGuest = restrictedPreference;
            restrictedPreference.setOnPreferenceClickListener(this);
            RestrictedPreference restrictedPreference2 =
                    (RestrictedPreference) findPreference("user_add");
            this.mAddUser = restrictedPreference2;
            if (!this.mUserCaps.mCanAddRestrictedProfile) {
                restrictedPreference2.setTitle(com.android.settings.R.string.user_add_user);
            }
            this.mAddUser.setOnPreferenceClickListener(this);
            setConfigSupervisedUserCreationPackage();
            RestrictedPreference restrictedPreference3 =
                    (RestrictedPreference) findPreference("supervised_user_add");
            this.mAddSupervisedUser = restrictedPreference3;
            restrictedPreference3.setOnPreferenceClickListener(this);
            activity.registerReceiverAsUser(
                    this.mUserChangeReceiver,
                    UserHandle.ALL,
                    USER_REMOVED_INTENT_FILTER,
                    null,
                    this.mHandler,
                    2);
            updateUI$4();
            this.mShouldUpdateUserList = false;
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(final int i) {
        FragmentActivity activity = getActivity();
        Dialog dialog = null;
        if (activity == null) {
            return null;
        }
        if (getView() != null) {
            getView()
                    .addOnLayoutChangeListener(
                            new View
                                    .OnLayoutChangeListener() { // from class:
                                                                // com.android.settings.users.UserSettings.4
                                @Override // android.view.View.OnLayoutChangeListener
                                public final void onLayoutChange(
                                        View view,
                                        int i2,
                                        int i3,
                                        int i4,
                                        int i5,
                                        int i6,
                                        int i7,
                                        int i8,
                                        int i9) {
                                    Dialog dialog2 = UserSettings.this.mDialog;
                                    if (dialog2 == null || !dialog2.isShowing()) {
                                        return;
                                    }
                                    int i10 = i;
                                    if (i10 == 1) {
                                        UserSettings userSettings = UserSettings.this;
                                        userSettings.mDialog.semSetAnchor(
                                                userSettings
                                                        .getActivity()
                                                        .findViewById(
                                                                com.android.settings.R.id
                                                                        .action_bar),
                                                1);
                                    }
                                    if (i10 == 2) {
                                        UserSettings userSettings2 = UserSettings.this;
                                        UserSettings.updateDialogAnchorView$2(
                                                userSettings2.mDialog, userSettings2.mAddUser);
                                        return;
                                    }
                                    if (i10 == 16) {
                                        UserSettings userSettings3 = UserSettings.this;
                                        UserSettings.updateDialogAnchorView$2(
                                                userSettings3.mDialog, userSettings3.mAddUser);
                                        return;
                                    }
                                    switch (i10) {
                                        case 6:
                                            UserSettings userSettings4 = UserSettings.this;
                                            UserSettings.updateDialogAnchorView$2(
                                                    userSettings4.mDialog, userSettings4.mAddUser);
                                            break;
                                        case 7:
                                            UserSettings userSettings5 = UserSettings.this;
                                            UserSettings.updateDialogAnchorView$2(
                                                    userSettings5.mDialog, userSettings5.mAddUser);
                                            break;
                                        case 8:
                                            UserSettings userSettings6 = UserSettings.this;
                                            UserSettings.updateDialogAnchorView$2(
                                                    userSettings6.mDialog,
                                                    userSettings6.mMePreference);
                                            break;
                                        case 9:
                                            UserSettings userSettings7 = UserSettings.this;
                                            UserSettings.updateDialogAnchorView$2(
                                                    userSettings7.mDialog,
                                                    userSettings7.mMePreference);
                                            break;
                                        case 10:
                                            UserSettings userSettings8 = UserSettings.this;
                                            UserSettings.updateDialogAnchorView$2(
                                                    userSettings8.mDialog, userSettings8.mAddUser);
                                            break;
                                        case 11:
                                            UserSettings userSettings9 = UserSettings.this;
                                            UserSettings.updateDialogAnchorView$2(
                                                    userSettings9.mDialog, userSettings9.mAddUser);
                                            break;
                                        case 12:
                                            UserSettings userSettings10 = UserSettings.this;
                                            UserSettings.updateDialogAnchorView$2(
                                                    userSettings10.mDialog,
                                                    userSettings10.mMePreference);
                                            break;
                                    }
                                }
                            });
        }
        if (i == 1) {
            final int i2 = 0;
            AlertDialog createRemoveDialog =
                    UserDialogs.createRemoveDialog(
                            getActivity(),
                            this.mRemovingUserId,
                            new DialogInterface.OnClickListener(
                                    this) { // from class: com.android.settings.users.UserSettings.5
                                public final /* synthetic */ UserSettings this$0;

                                {
                                    this.this$0 = this;
                                }

                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i3) {
                                    switch (i2) {
                                        case 0:
                                            final UserSettings userSettings = this.this$0;
                                            IntentFilter intentFilter =
                                                    UserSettings.USER_REMOVED_INTENT_FILTER;
                                            if (!Utils.isOnCall(userSettings.getActivity())) {
                                                if (userSettings.mRemovingUserId
                                                        != UserHandle.myUserId()) {
                                                    ThreadUtils.postOnBackgroundThread(
                                                            new Runnable() { // from class:
                                                                // com.android.settings.users.UserSettings.9
                                                                @Override // java.lang.Runnable
                                                                public final void run() {
                                                                    synchronized (
                                                                            UserSettings.this
                                                                                    .mUserLock) {
                                                                        UserSettings userSettings2 =
                                                                                UserSettings.this;
                                                                        userSettings2.mUserManager
                                                                                .removeUser(
                                                                                        userSettings2
                                                                                                .mRemovingUserId);
                                                                        sendEmptyMessage(1);
                                                                    }
                                                                }
                                                            });
                                                    break;
                                                } else {
                                                    userSettings.removeThisUser$1();
                                                    break;
                                                }
                                            } else {
                                                Toast.makeText(
                                                                userSettings.getActivity(),
                                                                com.android.settings.R.string
                                                                        .sec_user_mode_delete_toast_on_during_call,
                                                                1)
                                                        .show();
                                                break;
                                            }
                                        case 1:
                                            UserSettings userSettings2 = this.this$0;
                                            int i4 = i3 != 0 ? 2 : 1;
                                            IntentFilter intentFilter2 =
                                                    UserSettings.USER_REMOVED_INTENT_FILTER;
                                            userSettings2.onAddUserClicked(i4);
                                            break;
                                        case 2:
                                            UserSettings userSettings3 = this.this$0;
                                            IntentFilter intentFilter3 =
                                                    UserSettings.USER_REMOVED_INTENT_FILTER;
                                            userSettings3.getClass();
                                            Intent intent =
                                                    new Intent(
                                                                    "android.app.action.SET_NEW_PASSWORD")
                                                            .setPackage(
                                                                    userSettings3
                                                                            .getContext()
                                                                            .getPackageName());
                                            intent.putExtra(
                                                    "android.app.extra.PASSWORD_COMPLEXITY", 65536);
                                            userSettings3.startActivityForResult(intent, 10);
                                            break;
                                        default:
                                            this.this$0.clearAndExitGuest();
                                            break;
                                    }
                                }
                            });
            this.mDialog = createRemoveDialog;
            createRemoveDialog.semSetAnchor(
                    getActivity().findViewById(com.android.settings.R.id.action_bar), 1);
            return this.mDialog;
        }
        if (i == 2) {
            synchronized (this.mUserLock) {
                this.mPendingUserName = getString(com.android.settings.R.string.user_new_user_name);
                this.mPendingUserIcon = null;
            }
            Dialog buildAddUserDialog = buildAddUserDialog(1);
            this.mDialog = buildAddUserDialog;
            updateDialogAnchorView$2(buildAddUserDialog, this.mAddUser);
            return this.mDialog;
        }
        switch (i) {
            case 5:
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(com.android.settings.R.string.user_cannot_manage_message);
                builder.setPositiveButton(
                        com.android.settings.R.string.ok, (DialogInterface.OnClickListener) null);
                return builder.create();
            case 6:
                ArrayList arrayList = new ArrayList();
                HashMap hashMap = new HashMap();
                hashMap.put(
                        UniversalCredentialUtil.AGENT_TITLE,
                        getString(com.android.settings.R.string.user_add_user_item_title));
                hashMap.put(
                        UniversalCredentialUtil.AGENT_SUMMARY,
                        getString(com.android.settings.R.string.user_add_user_item_summary));
                HashMap hashMap2 = new HashMap();
                hashMap2.put(
                        UniversalCredentialUtil.AGENT_TITLE,
                        getString(com.android.settings.R.string.user_add_profile_item_title));
                hashMap2.put(
                        UniversalCredentialUtil.AGENT_SUMMARY,
                        getString(com.android.settings.R.string.user_add_profile_item_summary));
                arrayList.add(hashMap);
                arrayList.add(hashMap2);
                AlertDialog.Builder builder2 = new AlertDialog.Builder(activity);
                SimpleAdapter simpleAdapter =
                        new SimpleAdapter(
                                builder2.P.mContext,
                                arrayList,
                                com.android.settings.R.layout.two_line_list_item,
                                new String[] {
                                    UniversalCredentialUtil.AGENT_TITLE,
                                    UniversalCredentialUtil.AGENT_SUMMARY
                                },
                                new int[] {
                                    com.android.settings.R.id.title,
                                    com.android.settings.R.id.summary
                                });
                builder2.setTitle(com.android.settings.R.string.user_add_user_type_title);
                final int i3 = 1;
                builder2.setAdapter(
                        simpleAdapter,
                        new DialogInterface.OnClickListener(
                                this) { // from class: com.android.settings.users.UserSettings.5
                            public final /* synthetic */ UserSettings this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i32) {
                                switch (i3) {
                                    case 0:
                                        final UserSettings userSettings = this.this$0;
                                        IntentFilter intentFilter =
                                                UserSettings.USER_REMOVED_INTENT_FILTER;
                                        if (!Utils.isOnCall(userSettings.getActivity())) {
                                            if (userSettings.mRemovingUserId
                                                    != UserHandle.myUserId()) {
                                                ThreadUtils.postOnBackgroundThread(
                                                        new Runnable() { // from class:
                                                                         // com.android.settings.users.UserSettings.9
                                                            @Override // java.lang.Runnable
                                                            public final void run() {
                                                                synchronized (
                                                                        UserSettings.this
                                                                                .mUserLock) {
                                                                    UserSettings userSettings2 =
                                                                            UserSettings.this;
                                                                    userSettings2.mUserManager
                                                                            .removeUser(
                                                                                    userSettings2
                                                                                            .mRemovingUserId);
                                                                    sendEmptyMessage(1);
                                                                }
                                                            }
                                                        });
                                                break;
                                            } else {
                                                userSettings.removeThisUser$1();
                                                break;
                                            }
                                        } else {
                                            Toast.makeText(
                                                            userSettings.getActivity(),
                                                            com.android.settings.R.string
                                                                    .sec_user_mode_delete_toast_on_during_call,
                                                            1)
                                                    .show();
                                            break;
                                        }
                                    case 1:
                                        UserSettings userSettings2 = this.this$0;
                                        int i4 = i32 != 0 ? 2 : 1;
                                        IntentFilter intentFilter2 =
                                                UserSettings.USER_REMOVED_INTENT_FILTER;
                                        userSettings2.onAddUserClicked(i4);
                                        break;
                                    case 2:
                                        UserSettings userSettings3 = this.this$0;
                                        IntentFilter intentFilter3 =
                                                UserSettings.USER_REMOVED_INTENT_FILTER;
                                        userSettings3.getClass();
                                        Intent intent =
                                                new Intent("android.app.action.SET_NEW_PASSWORD")
                                                        .setPackage(
                                                                userSettings3
                                                                        .getContext()
                                                                        .getPackageName());
                                        intent.putExtra(
                                                "android.app.extra.PASSWORD_COMPLEXITY", 65536);
                                        userSettings3.startActivityForResult(intent, 10);
                                        break;
                                    default:
                                        this.this$0.clearAndExitGuest();
                                        break;
                                }
                            }
                        });
                AlertDialog create = builder2.create();
                this.mDialog = create;
                updateDialogAnchorView$2(create, this.mAddUser);
                return this.mDialog;
            case 7:
                AlertDialog.Builder builder3 = new AlertDialog.Builder(activity);
                builder3.setMessage(com.android.settings.R.string.user_need_lock_message);
                final int i4 = 2;
                builder3.setPositiveButton(
                        com.android.settings.R.string.user_set_lock_button,
                        new DialogInterface.OnClickListener(
                                this) { // from class: com.android.settings.users.UserSettings.5
                            public final /* synthetic */ UserSettings this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i32) {
                                switch (i4) {
                                    case 0:
                                        final UserSettings userSettings = this.this$0;
                                        IntentFilter intentFilter =
                                                UserSettings.USER_REMOVED_INTENT_FILTER;
                                        if (!Utils.isOnCall(userSettings.getActivity())) {
                                            if (userSettings.mRemovingUserId
                                                    != UserHandle.myUserId()) {
                                                ThreadUtils.postOnBackgroundThread(
                                                        new Runnable() { // from class:
                                                                         // com.android.settings.users.UserSettings.9
                                                            @Override // java.lang.Runnable
                                                            public final void run() {
                                                                synchronized (
                                                                        UserSettings.this
                                                                                .mUserLock) {
                                                                    UserSettings userSettings2 =
                                                                            UserSettings.this;
                                                                    userSettings2.mUserManager
                                                                            .removeUser(
                                                                                    userSettings2
                                                                                            .mRemovingUserId);
                                                                    sendEmptyMessage(1);
                                                                }
                                                            }
                                                        });
                                                break;
                                            } else {
                                                userSettings.removeThisUser$1();
                                                break;
                                            }
                                        } else {
                                            Toast.makeText(
                                                            userSettings.getActivity(),
                                                            com.android.settings.R.string
                                                                    .sec_user_mode_delete_toast_on_during_call,
                                                            1)
                                                    .show();
                                            break;
                                        }
                                    case 1:
                                        UserSettings userSettings2 = this.this$0;
                                        int i42 = i32 != 0 ? 2 : 1;
                                        IntentFilter intentFilter2 =
                                                UserSettings.USER_REMOVED_INTENT_FILTER;
                                        userSettings2.onAddUserClicked(i42);
                                        break;
                                    case 2:
                                        UserSettings userSettings3 = this.this$0;
                                        IntentFilter intentFilter3 =
                                                UserSettings.USER_REMOVED_INTENT_FILTER;
                                        userSettings3.getClass();
                                        Intent intent =
                                                new Intent("android.app.action.SET_NEW_PASSWORD")
                                                        .setPackage(
                                                                userSettings3
                                                                        .getContext()
                                                                        .getPackageName());
                                        intent.putExtra(
                                                "android.app.extra.PASSWORD_COMPLEXITY", 65536);
                                        userSettings3.startActivityForResult(intent, 10);
                                        break;
                                    default:
                                        this.this$0.clearAndExitGuest();
                                        break;
                                }
                            }
                        });
                builder3.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
                AlertDialog create2 = builder3.create();
                this.mDialog = create2;
                updateDialogAnchorView$2(create2, this.mAddUser);
                return this.mDialog;
            case 8:
                AlertDialog.Builder builder4 = new AlertDialog.Builder(activity);
                builder4.setTitle(com.android.settings.R.string.guest_remove_guest_dialog_title);
                builder4.setMessage(com.android.settings.R.string.user_exit_guest_confirm_message);
                final int i5 = 3;
                builder4.setPositiveButton(
                        com.android.settings.R.string.user_exit_guest_dialog_remove,
                        new DialogInterface.OnClickListener(
                                this) { // from class: com.android.settings.users.UserSettings.5
                            public final /* synthetic */ UserSettings this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i32) {
                                switch (i5) {
                                    case 0:
                                        final UserSettings userSettings = this.this$0;
                                        IntentFilter intentFilter =
                                                UserSettings.USER_REMOVED_INTENT_FILTER;
                                        if (!Utils.isOnCall(userSettings.getActivity())) {
                                            if (userSettings.mRemovingUserId
                                                    != UserHandle.myUserId()) {
                                                ThreadUtils.postOnBackgroundThread(
                                                        new Runnable() { // from class:
                                                                         // com.android.settings.users.UserSettings.9
                                                            @Override // java.lang.Runnable
                                                            public final void run() {
                                                                synchronized (
                                                                        UserSettings.this
                                                                                .mUserLock) {
                                                                    UserSettings userSettings2 =
                                                                            UserSettings.this;
                                                                    userSettings2.mUserManager
                                                                            .removeUser(
                                                                                    userSettings2
                                                                                            .mRemovingUserId);
                                                                    sendEmptyMessage(1);
                                                                }
                                                            }
                                                        });
                                                break;
                                            } else {
                                                userSettings.removeThisUser$1();
                                                break;
                                            }
                                        } else {
                                            Toast.makeText(
                                                            userSettings.getActivity(),
                                                            com.android.settings.R.string
                                                                    .sec_user_mode_delete_toast_on_during_call,
                                                            1)
                                                    .show();
                                            break;
                                        }
                                    case 1:
                                        UserSettings userSettings2 = this.this$0;
                                        int i42 = i32 != 0 ? 2 : 1;
                                        IntentFilter intentFilter2 =
                                                UserSettings.USER_REMOVED_INTENT_FILTER;
                                        userSettings2.onAddUserClicked(i42);
                                        break;
                                    case 2:
                                        UserSettings userSettings3 = this.this$0;
                                        IntentFilter intentFilter3 =
                                                UserSettings.USER_REMOVED_INTENT_FILTER;
                                        userSettings3.getClass();
                                        Intent intent =
                                                new Intent("android.app.action.SET_NEW_PASSWORD")
                                                        .setPackage(
                                                                userSettings3
                                                                        .getContext()
                                                                        .getPackageName());
                                        intent.putExtra(
                                                "android.app.extra.PASSWORD_COMPLEXITY", 65536);
                                        userSettings3.startActivityForResult(intent, 10);
                                        break;
                                    default:
                                        this.this$0.clearAndExitGuest();
                                        break;
                                }
                            }
                        });
                builder4.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
                AlertDialog create3 = builder4.create();
                this.mDialog = create3;
                updateDialogAnchorView$2(create3, this.mMePreference);
                return this.mDialog;
            case 9:
                FragmentActivity activity2 = getActivity();
                if (activity2 != null) {
                    final UserInfo userInfo =
                            this.mUserManager.getUserInfo(Process.myUserHandle().getIdentifier());
                    final Drawable secGetUserIcon =
                            com.android.settingslib.Utils.secGetUserIcon(
                                    activity2, this.mUserManager, userInfo);
                    dialog =
                            this.mEditUserInfoController.createDialog(
                                    activity2,
                                    new UserSettings$$ExternalSyntheticLambda10(this),
                                    secGetUserIcon,
                                    userInfo.name,
                                    new BiConsumer() { // from class:
                                                       // com.android.settings.users.UserSettings$$ExternalSyntheticLambda11
                                        @Override // java.util.function.BiConsumer
                                        public final void accept(Object obj, Object obj2) {
                                            UserSettings userSettings = UserSettings.this;
                                            Drawable drawable = secGetUserIcon;
                                            UserInfo userInfo2 = userInfo;
                                            String str = (String) obj;
                                            Drawable drawable2 = (Drawable) obj2;
                                            IntentFilter intentFilter =
                                                    UserSettings.USER_REMOVED_INTENT_FILTER;
                                            userSettings.getClass();
                                            if (drawable2 != drawable) {
                                                ThreadUtils.postOnBackgroundThread(
                                                        new UserSettings$$ExternalSyntheticLambda5(
                                                                userSettings,
                                                                userInfo2,
                                                                drawable2,
                                                                1));
                                                userSettings.mMePreference.setIcon(drawable2);
                                            }
                                            if (TextUtils.isEmpty(str)
                                                    || str.equals(userInfo2.name)) {
                                                return;
                                            }
                                            userSettings.mMePreference.setTitle(str);
                                            userSettings.mUserManager.setUserName(
                                                    userInfo2.id, str);
                                        }
                                    },
                                    new UserSettings$$ExternalSyntheticLambda12());
                }
                this.mDialog = dialog;
                updateDialogAnchorView$2(dialog, this.mMePreference);
                return this.mDialog;
            case 10:
                synchronized (this.mUserLock) {
                    this.mPendingUserName =
                            getString(com.android.settings.R.string.user_new_user_name);
                    this.mPendingUserIcon = null;
                }
                Dialog buildAddUserDialog2 = buildAddUserDialog(1);
                this.mDialog = buildAddUserDialog2;
                updateDialogAnchorView$2(buildAddUserDialog2, this.mAddUser);
                return this.mDialog;
            case 11:
                synchronized (this.mUserLock) {
                    this.mPendingUserName =
                            getString(com.android.settings.R.string.user_new_profile_name);
                    this.mPendingUserIcon = null;
                }
                Dialog buildAddUserDialog3 = buildAddUserDialog(2);
                this.mDialog = buildAddUserDialog3;
                updateDialogAnchorView$2(buildAddUserDialog3, this.mAddUser);
                return this.mDialog;
            case 12:
                AlertDialog createResetGuestDialog =
                        UserDialogs.createResetGuestDialog(
                                getActivity(),
                                new DialogInterface
                                        .OnClickListener() { // from class:
                                                             // com.android.settings.users.UserSettings$$ExternalSyntheticLambda6
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i6) {
                                        UserSettings userSettings = UserSettings.this;
                                        IntentFilter intentFilter =
                                                UserSettings.USER_REMOVED_INTENT_FILTER;
                                        userSettings.clearAndExitGuest();
                                    }
                                });
                this.mDialog = createResetGuestDialog;
                updateDialogAnchorView$2(createResetGuestDialog, this.mMePreference);
                return this.mDialog;
            default:
                return null;
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (!this.mUserCaps.mIsAdmin && canSwitchUserNow$1() && !this.mUserCaps.mIsGuest) {
            MenuItem add =
                    menu.add(
                            0,
                            1,
                            0,
                            getResources()
                                    .getString(
                                            com.android.settings.R.string.user_remove_user_menu,
                                            this.mUserManager.getUserName()));
            add.setShowAsAction(0);
            RestrictedLockUtilsInternal.setMenuItemAsDisabledByAdmin(
                    getContext(),
                    add,
                    RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                            getContext(), UserHandle.myUserId(), "no_remove_user"));
        }
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        UserCapabilities userCapabilities = this.mUserCaps;
        if (userCapabilities == null || !userCapabilities.mEnabled) {
            return;
        }
        getActivity().unregisterReceiver(this.mUserChangeReceiver);
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final void onDialogShowing() {
        super.onDialogShowing();
        if (getDialogFragment().mDialogId != 6) {
            setOnDismissListener(this);
        }
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        synchronized (this.mUserLock) {
            try {
                this.mRemovingUserId = -1;
                updateUserList();
                CreateUserDialogController createUserDialogController =
                        this.mCreateUserDialogController;
                CustomDialogHelper customDialogHelper =
                        createUserDialogController.mCustomDialogHelper;
                if (customDialogHelper != null && customDialogHelper.mDialog != null) {
                    createUserDialogController.finish();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 1) {
            return super.onOptionsItemSelected(menuItem);
        }
        int myUserId = UserHandle.myUserId();
        synchronized (this.mUserLock) {
            try {
                if (this.mRemovingUserId == -1 && !this.mAddingUser) {
                    this.mRemovingUserId = myUserId;
                    showDialog(1);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        this.mShouldUpdateUserList = true;
        super.onPause();
        SemDesktopModeManager semDesktopModeManager = this.mDesktopModeManager;
        if (semDesktopModeManager != null) {
            semDesktopModeManager.unregisterListener(this.mDesktopModeListener);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        this.mMetricsFeatureProvider.logSettingsTileClick(96, preference.getKey());
        if (preference == this.mMePreference) {
            if (this.mUserCaps.mIsGuest) {
                openUserDetails(this.mUserManager.findCurrentGuestUser(), false, getContext());
                return true;
            }
            showDialog(9);
            return true;
        }
        if (preference instanceof UserPreference) {
            openUserDetails(
                    this.mUserManager.getUserInfo(((UserPreference) preference).mUserId),
                    false,
                    getContext());
            return true;
        }
        if (preference == this.mAddUser) {
            this.mMetricsFeatureProvider.action(getActivity(), 1806, new Pair[0]);
            if (this.mUserCaps.mCanAddRestrictedProfile) {
                showDialog(6);
            } else {
                onAddUserClicked(1);
            }
            return true;
        }
        if (preference == this.mAddSupervisedUser) {
            this.mMetricsFeatureProvider.action(getActivity(), 1786, new Pair[0]);
            Trace.beginSection("UserSettings.addSupervisedUser");
            startActivity(
                    new Intent()
                            .setAction("android.os.action.CREATE_SUPERVISED_USER")
                            .setPackage(this.mConfigSupervisedUserCreationPackage)
                            .addFlags(268435456));
            Trace.endSection();
            return true;
        }
        RestrictedPreference restrictedPreference = this.mAddGuest;
        if (preference != restrictedPreference) {
            return false;
        }
        restrictedPreference.setEnabled(false);
        Context context = getContext();
        UserCreatingDialog userCreatingDialog = new UserCreatingDialog(getActivity(), true);
        userCreatingDialog.show();
        ThreadUtils.postOnBackgroundThread(
                new UserSettings$$ExternalSyntheticLambda5(this, context, userCreatingDialog, 0));
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        SemDesktopModeManager semDesktopModeManager = this.mDesktopModeManager;
        if (semDesktopModeManager != null) {
            semDesktopModeManager.registerListener(this.mDesktopModeListener);
        }
        if (this.mUserCaps.mEnabled) {
            PreferenceScreen preferenceScreen = getPreferenceScreen();
            AddUserWhenLockedPreferenceController addUserWhenLockedPreferenceController =
                    this.mAddUserWhenLockedPreferenceController;
            addUserWhenLockedPreferenceController.updateState(
                    preferenceScreen.findPreference(
                            addUserWhenLockedPreferenceController.getPreferenceKey()));
            TimeoutToDockUserPreferenceController timeoutToDockUserPreferenceController =
                    this.mTimeoutToDockUserPreferenceController;
            timeoutToDockUserPreferenceController.updateState(
                    preferenceScreen.findPreference(
                            timeoutToDockUserPreferenceController.getPreferenceKey()));
            if (this.mShouldUpdateUserList) {
                updateUI$4();
            }
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        EditUserPhotoController editUserPhotoController;
        File saveNewUserPhotoBitmap;
        EditUserPhotoController editUserPhotoController2;
        CustomDialogHelper customDialogHelper =
                this.mCreateUserDialogController.mCustomDialogHelper;
        boolean z = false;
        if ((customDialogHelper == null || customDialogHelper.mDialog == null) ? false : true) {
            if (customDialogHelper != null && customDialogHelper.mDialog != null) {
                z = true;
            }
            bundle.putBoolean("create_user", z);
            CreateUserDialogController createUserDialogController =
                    this.mCreateUserDialogController;
            if (createUserDialogController.mUserCreationDialog != null
                    && (editUserPhotoController2 =
                                    createUserDialogController.mEditUserPhotoController)
                            != null
                    && createUserDialogController.mCachedDrawablePath == null) {
                createUserDialogController.mCachedDrawablePath =
                        editUserPhotoController2.mCachedDrawablePath;
            }
            String str = createUserDialogController.mCachedDrawablePath;
            if (str != null) {
                bundle.putString("pending_photo", str);
            }
            Boolean bool = createUserDialogController.mIsAdmin;
            if (bool != null) {
                bundle.putBoolean("admin_status", Boolean.TRUE.equals(bool));
            }
            bundle.putString(
                    "saved_name",
                    createUserDialogController.mUserNameView.getText().toString().trim());
            bundle.putInt("current_state", createUserDialogController.mCurrentState);
            bundle.putBoolean(
                    "awaiting_result", createUserDialogController.mWaitingForActivityResult);
        } else {
            EditUserInfoController editUserInfoController = this.mEditUserInfoController;
            if (editUserInfoController.mEditUserInfoDialog != null
                    && (editUserPhotoController = editUserInfoController.mEditUserPhotoController)
                            != null
                    && (saveNewUserPhotoBitmap = editUserPhotoController.saveNewUserPhotoBitmap())
                            != null) {
                bundle.putString("pending_photo", saveNewUserPhotoBitmap.getPath());
            }
            bundle.putBoolean("awaiting_result", editUserInfoController.mWaitingForActivityResult);
        }
        bundle.putInt("removing_user", this.mRemovingUserId);
        super.onSaveInstanceState(bundle);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setDivider(null);
        getListView()
                .addItemDecoration(
                        new SecCustomDividerItemDecorator(
                                getResources()
                                        .getDrawable(
                                                com.android.settings.R.drawable
                                                        .sec_top_level_list_divider),
                                getContext(),
                                (int)
                                        (getResources()
                                                        .getDimension(
                                                                com.android.settings.R.dimen
                                                                        .sec_app_list_item_icon_min_width)
                                                + getResources()
                                                        .getDimension(
                                                                com.android.settings.R.dimen
                                                                        .sec_widget_list_item_padding_start)),
                                com.android.settings.R.id.icon_frame,
                                R.id.icon));
    }

    public final void openUserDetails(UserInfo userInfo, boolean z, Context context) {
        if (userInfo == null || context == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, userInfo.id);
        bundle.putBoolean("new_user", z);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
        String name = UserDetailsSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        launchRequest.mTitle = userInfo.name;
        launchRequest.mSourceMetricsCategory = 96;
        if (this.mGuestUserAutoCreated && userInfo.isGuest()) {
            subSettingLauncher.setResultListener(this, 11);
        }
        subSettingLauncher.launch();
    }

    public final void removeThisUser$1() {
        if (Utils.isOnCall(getActivity())) {
            Toast.makeText(
                            getActivity(),
                            com.android.settings.R.string.sec_user_mode_delete_toast_on_during_call,
                            1)
                    .show();
            return;
        }
        if (!canSwitchUserNow$1()) {
            android.util.Log.w(
                    "UserSettings", "Cannot remove current user when switching is disabled");
            return;
        }
        try {
            this.mUserManager.removeUserWhenPossible(UserHandle.of(UserHandle.myUserId()), false);
            ActivityManager.getService()
                    .switchUser(this.mUserManager.getPreviousForegroundUser().getIdentifier());
        } catch (RemoteException unused) {
            android.util.Log.e("UserSettings", "Unable to remove self user");
        }
    }

    public void scheduleGuestCreation() {
        if (this.mGuestCreationScheduled.compareAndSet(false, true)) {
            sendEmptyMessage(1);
            this.mExecutor.execute(new UserSettings$$ExternalSyntheticLambda4(this, 0));
        }
    }

    public void setConfigSupervisedUserCreationPackage() {
        this.mConfigSupervisedUserCreationPackage =
                getPrefContext().getString(R.string.face_acquired_too_left);
    }

    @Override // androidx.fragment.app.Fragment
    public final void startActivityForResult(Intent intent, int i) {
        this.mEditUserInfoController.mWaitingForActivityResult = true;
        this.mCreateUserDialogController.mWaitingForActivityResult = true;
        super.startActivityForResult(intent, i);
    }

    public final void updateAddUserCommon(
            FragmentActivity fragmentActivity,
            RestrictedPreference restrictedPreference,
            boolean z) {
        UserCapabilities userCapabilities = this.mUserCaps;
        boolean z2 = false;
        if ((!userCapabilities.mCanAddUser && !userCapabilities.mDisallowAddUserSetByAdmin)
                || !WizardManagerHelper.isDeviceProvisioned(fragmentActivity)
                || !this.mUserCaps.mUserSwitcherEnabled) {
            restrictedPreference.setVisible(false);
            return;
        }
        restrictedPreference.setVisible(true);
        restrictedPreference.setSelectable(true);
        boolean z3 =
                this.mUserManager.canAddMoreUsers("android.os.usertype.full.SECONDARY")
                        || (z
                                && this.mUserManager.canAddMoreUsers(
                                        "android.os.usertype.full.RESTRICTED"));
        if (z3 && !this.mAddingUser && canSwitchUserNow$1()) {
            z2 = true;
        }
        restrictedPreference.setEnabled(z2);
        if (z3) {
            restrictedPreference.setSummary((CharSequence) null);
        } else {
            restrictedPreference.setSummary(
                    getString(com.android.settings.R.string.user_add_max_count));
        }
        if (restrictedPreference.isEnabled()) {
            UserCapabilities userCapabilities2 = this.mUserCaps;
            restrictedPreference.setDisabledByAdmin(
                    userCapabilities2.mDisallowAddUser ? userCapabilities2.mEnforcedAdmin : null);
        }
    }

    public final void updateUI$4() {
        this.mUserCaps.updateAddUserCapabilities(getActivity());
        if (this.mUserCaps.mIsGuest) {
            this.mMePreference.setIcon(getEncircledDefaultIcon());
            this.mMePreference.setTitle(this.mUserManager.getUserInfo(UserHandle.myUserId()).name);
            this.mMePreference.setSelectable(true);
            this.mMePreference.setEnabled(canSwitchUserNow$1());
        } else {
            new AnonymousClass3(this, 0).execute(new Void[0]);
        }
        updateUserList();
    }

    public void updateUserList() {
        UserPreference userPreference;
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        List<UserInfo> of =
                this.mUserCaps.mUserSwitcherEnabled
                        ? (List)
                                this.mUserManager.getAliveUsers().stream()
                                        .filter(new UserSettings$$ExternalSyntheticLambda1(1))
                                        .collect(Collectors.toList())
                        : List.of(this.mUserManager.getUserInfo(activity.getUserId()));
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(this.mMePreference);
        boolean z =
                this.mUserCaps.mIsAdmin
                        || (canSwitchUserNow$1() && !this.mUserCaps.mDisallowSwitchUser);
        for (UserInfo userInfo : of) {
            if (userInfo.supportsSwitchToByUser()) {
                if (userInfo.id == UserHandle.myUserId()) {
                    userPreference = this.mMePreference;
                } else {
                    UserPreference userPreference2 =
                            new UserPreference(getPrefContext(), null, userInfo.id);
                    userPreference2.setTitle(userInfo.name);
                    arrayList2.add(userPreference2);
                    userPreference2.setOnPreferenceClickListener(this);
                    userPreference2.setEnabled(z);
                    userPreference2.setSelectable(true);
                    if (userInfo.isGuest()) {
                        userPreference2.setIcon(getEncircledDefaultIcon());
                        userPreference2.setKey("user_guest");
                        if (this.mUserCaps.mDisallowSwitchUser) {
                            userPreference2.setDisabledByAdmin(
                                    RestrictedLockUtilsInternal.getDeviceOwner(activity));
                        } else {
                            userPreference2.setDisabledByAdmin(null);
                        }
                    } else {
                        userPreference2.setKey("id=" + userInfo.id);
                    }
                    userPreference = userPreference2;
                }
                if (userPreference != null) {
                    if (userInfo.isMain()) {
                        userPreference.setSummary(com.android.settings.R.string.user_owner);
                    } else if (userInfo.isAdmin()) {
                        userPreference.setSummary(com.android.settings.R.string.user_admin);
                    }
                    if (userInfo.id == UserHandle.myUserId()
                            || userInfo.isGuest()
                            || userInfo.isInitialized()) {
                        if (userInfo.isRestricted()) {
                            userPreference.setSummary(
                                    com.android.settings.R.string.user_summary_restricted_profile);
                        }
                    } else if (userInfo.isRestricted()) {
                        userPreference.setSummary(
                                com.android.settings.R.string.user_summary_restricted_not_set_up);
                    } else {
                        userPreference.setSummary(
                                com.android.settings.R.string.user_summary_not_set_up);
                        userPreference.setEnabled(
                                !this.mUserCaps.mDisallowSwitchUser && canSwitchUserNow$1());
                    }
                    if (userInfo.iconPath == null) {
                        userPreference.setIcon(getEncircledDefaultIcon());
                    } else if (this.mUserIcons.get(userInfo.id) == null) {
                        arrayList.add(Integer.valueOf(userInfo.id));
                        userPreference.setIcon(getEncircledDefaultIcon());
                    } else {
                        Bitmap bitmap = this.mUserIcons.get(userInfo.id);
                        if (bitmap != null) {
                            userPreference.setIcon(
                                    new CircleFramedDrawable(
                                            bitmap,
                                            getResources()
                                                    .getDimensionPixelSize(
                                                            com.android.settings.R.dimen
                                                                    .user_icon_size)));
                        }
                    }
                }
            }
        }
        if (this.mAddingUser) {
            UserPreference userPreference3 = new UserPreference(getPrefContext(), null, -10);
            userPreference3.setEnabled(false);
            userPreference3.setTitle(this.mAddingUserName);
            userPreference3.setIcon(getEncircledDefaultIcon());
            arrayList2.add(userPreference3);
        }
        Collections.sort(arrayList2, UserPreference.SERIAL_NUMBER_COMPARATOR);
        Iterator it = arrayList2.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            UserPreference userPreference4 = (UserPreference) it.next();
            if (!TextUtils.isEmpty(userPreference4.getKey())
                    && userPreference4.getKey().equals("user_guest")
                    && arrayList2.indexOf(userPreference4) != arrayList2.size() - 1) {
                arrayList2.remove(arrayList2.indexOf(userPreference4));
                arrayList2.add(userPreference4);
                break;
            }
        }
        getActivity().invalidateOptionsMenu();
        if (arrayList.size() > 0) {
            new AnonymousClass3(this, 1).execute(arrayList);
        }
        this.mUserListMeCategory.removeAll();
        this.mUserListCategory.removeAll();
        this.mAddUserWhenLockedPreferenceController.updateState(
                getPreferenceScreen()
                        .findPreference(
                                this.mAddUserWhenLockedPreferenceController.getPreferenceKey()));
        this.mMultiUserTopIntroPreferenceController.updateState(
                getPreferenceScreen()
                        .findPreference(
                                this.mMultiUserTopIntroPreferenceController.getPreferenceKey()));
        this.mUserListMeCategory.setVisible(this.mUserCaps.mUserSwitcherEnabled);
        this.mUserListCategory.setVisible(this.mUserCaps.mUserSwitcherEnabled);
        if (!of.stream().anyMatch(new UserSettings$$ExternalSyntheticLambda1(2))
                && this.mUserCaps.mCanAddGuest
                && WizardManagerHelper.isDeviceProvisioned(activity)
                && this.mUserCaps.mUserSwitcherEnabled) {
            this.mAddGuest.setIcon(getEncircledDefaultIcon());
            this.mAddGuest.setVisible(true);
            this.mAddGuest.setSelectable(true);
            if (this.mGuestUserAutoCreated && this.mGuestCreationScheduled.get()) {
                this.mAddGuest.setTitle(R.string.mime_type_spreadsheet_ext);
                this.mAddGuest.setSummary(com.android.settings.R.string.guest_resetting);
                this.mAddGuest.setEnabled(false);
            } else {
                this.mAddGuest.setTitle(com.android.settings.R.string.guest_new_guest);
                this.mAddGuest.setEnabled(canSwitchUserNow$1());
            }
        } else {
            this.mAddGuest.setVisible(false);
        }
        updateAddUserCommon(activity, this.mAddUser, this.mUserCaps.mCanAddRestrictedProfile);
        this.mAddUser.setIcon(
                activity.getDrawable(com.android.settings.R.drawable.sec_tw_list_icon_create_mtrl));
        if (TextUtils.isEmpty(this.mConfigSupervisedUserCreationPackage)) {
            this.mAddSupervisedUser.setVisible(false);
        } else {
            updateAddUserCommon(activity, this.mAddSupervisedUser, false);
            Drawable drawable =
                    activity.getDrawable(com.android.settings.R.drawable.ic_add_supervised_user);
            RestrictedPreference restrictedPreference = this.mAddSupervisedUser;
            drawable.setTintBlendMode(BlendMode.SRC_IN);
            drawable.setTint(
                    com.android.settingslib.Utils.getColorAttrDefaultColor(
                            getContext(), R.attr.textColorPrimary));
            LayerDrawable layerDrawable =
                    new LayerDrawable(
                            new Drawable[] {
                                getContext()
                                        .getDrawable(com.android.settings.R.drawable.user_avatar_bg)
                                        .mutate(),
                                drawable
                            });
            int dimensionPixelSize =
                    getContext()
                            .getResources()
                            .getDimensionPixelSize(com.android.settings.R.dimen.user_icon_size);
            int dimensionPixelSize2 =
                    getContext()
                            .getResources()
                            .getDimensionPixelSize(
                                    com.android.settings.R.dimen.multiple_users_user_icon_size);
            layerDrawable.setLayerSize(1, dimensionPixelSize, dimensionPixelSize);
            layerDrawable.setLayerSize(0, dimensionPixelSize2, dimensionPixelSize2);
            layerDrawable.setLayerGravity(1, 17);
            restrictedPreference.setIcon(layerDrawable);
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            UserPreference userPreference5 = (UserPreference) it2.next();
            userPreference5.setOrder(Preference.DEFAULT_ORDER);
            if (this.mUserListMeCategory.getPreferenceCount() < 1) {
                this.mUserListMeCategory.addPreference(userPreference5);
            } else {
                this.mUserListCategory.addPreference(userPreference5);
            }
        }
    }
}
