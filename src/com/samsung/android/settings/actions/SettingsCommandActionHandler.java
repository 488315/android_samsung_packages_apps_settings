package com.samsung.android.settings.actions;

import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;

import com.android.settings.MainClear$$ExternalSyntheticOutline0;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.sdk.command.Command;
import com.samsung.android.sdk.command.action.CommandAction;
import com.samsung.android.sdk.command.action.DefaultAction;
import com.samsung.android.sdk.command.provider.CommandProvider;
import com.samsung.android.sdk.command.template.CommandTemplate;
import com.samsung.android.settings.actions.templateresolver.CommandTemplateResolver;
import com.samsung.android.settings.actions.templateresolver.NoTemplateResolver;
import com.samsung.android.settings.actions.templateresolver.SingleChoiceTemplateResolver;
import com.samsung.android.settings.actions.templateresolver.SliderTemplateRsolver;
import com.samsung.android.settings.actions.templateresolver.ToggleTemplateResolver;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.samsung.android.util.SemLog;
import com.sec.ims.ImsManager;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SettingsCommandActionHandler {
    public Map mCachedCommandMap;
    public Context mContext;
    public ActionsDatabaseAccessor mDatabaseAccessor;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.actions.SettingsCommandActionHandler$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public final /* synthetic */ CommandProvider.AnonymousClass1 val$actionCallback;
        public final /* synthetic */ boolean val$hasAsyncUpdate;

        public AnonymousClass1(boolean z, CommandProvider.AnonymousClass1 anonymousClass1) {
            this.val$hasAsyncUpdate = z;
            this.val$actionCallback = anonymousClass1;
        }

        public final void onResolved(int i, String str) {
            StringBuilder sb = new StringBuilder("performCommandAction() responseCode : ");
            sb.append(i);
            sb.append(" / responseMessage : ");
            sb.append(str);
            sb.append(" / hasAsyncUpdate : ");
            boolean z = this.val$hasAsyncUpdate;
            sb.append(z);
            sb.append(" / actionCallback ");
            CommandProvider.AnonymousClass1 anonymousClass1 = this.val$actionCallback;
            Utils$$ExternalSyntheticOutline0.m(
                    sb,
                    anonymousClass1 == null ? "null" : "valid",
                    "Command/SettingsCommandActionHandler");
            if (i == 1 && z) {
                i = 3;
            }
            if (anonymousClass1 != null) {
                anonymousClass1.onActionFinished(i, str);
            }
        }
    }

    public final Map getAvailableCommands() {
        int i;
        Map map;
        if (this.mDatabaseAccessor == null) {
            this.mDatabaseAccessor = new ActionsDatabaseAccessor(this.mContext);
        }
        String str = null;
        if ((!this.mDatabaseAccessor.mActionsIndexer.mHelper.isActionDataIndexed())
                && (map = this.mCachedCommandMap) != null) {
            ((HashMap) map).clear();
            this.mCachedCommandMap = null;
        }
        Map map2 = this.mCachedCommandMap;
        if (map2 == null && map2 == null) {
            this.mCachedCommandMap = new HashMap();
            if (this.mDatabaseAccessor == null) {
                this.mDatabaseAccessor = new ActionsDatabaseAccessor(this.mContext);
            }
            ActionsDatabaseAccessor actionsDatabaseAccessor = this.mDatabaseAccessor;
            actionsDatabaseAccessor.getClass();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                actionsDatabaseAccessor.mActionsIndexer.indexActionData();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                Cursor query =
                        actionsDatabaseAccessor
                                .mHelper
                                .getReadableDatabase()
                                .query(
                                        "actions_index",
                                        new String[] {
                                            GoodSettingsContract.EXTRA_NAME.POLICY_KEY,
                                            UniversalCredentialUtil.AGENT_TITLE,
                                            UniversalCredentialUtil.AGENT_SUMMARY,
                                            "fragment",
                                            "icon",
                                            ImsManager.INTENT_PARAM_RCS_ENABLE_TYPE,
                                            "recoverable",
                                            "restore_key",
                                            "target_fragment",
                                            "payload",
                                            "screentitle",
                                            "keywords",
                                            "controller"
                                        },
                                        null,
                                        null,
                                        null,
                                        null,
                                        null);
                try {
                    if (query.moveToFirst()) {
                        int columnIndex =
                                query.getColumnIndex(GoodSettingsContract.EXTRA_NAME.POLICY_KEY);
                        int columnIndex2 =
                                query.getColumnIndex(UniversalCredentialUtil.AGENT_TITLE);
                        int columnIndex3 =
                                query.getColumnIndex(UniversalCredentialUtil.AGENT_SUMMARY);
                        int columnIndex4 = query.getColumnIndex("icon");
                        while (true) {
                            String string = query.getString(columnIndex);
                            String string2 = query.getString(columnIndex2);
                            String string3 = query.getString(columnIndex3);
                            int i2 = columnIndex4 > 0 ? query.getInt(columnIndex4) : 0;
                            Uri build =
                                    new Uri.Builder()
                                            .scheme("command")
                                            .authority(this.mContext.getPackageName() + ".command")
                                            .appendPath(string)
                                            .build();
                            if (build != null) {
                                String uri = build.toString();
                                Map map3 = RoutinesCommandRegistry.ROUTINES;
                                if (map3.containsKey(string)
                                        && (i =
                                                        ((RoutinesCommandRegistry.Routine)
                                                                        ((LinkedHashMap) map3)
                                                                                .get(string))
                                                                .mTitleResId)
                                                > 0) {
                                    string2 = this.mContext.getString(i);
                                }
                                ((HashMap) this.mCachedCommandMap)
                                        .put(
                                                uri,
                                                new Command(
                                                        uri,
                                                        string2,
                                                        string3,
                                                        null,
                                                        map3.containsKey(string) ? "routines" : str,
                                                        null,
                                                        map3.containsKey(string)
                                                                ? ((RoutinesCommandRegistry.Routine)
                                                                                ((LinkedHashMap)
                                                                                                map3)
                                                                                        .get(
                                                                                                string))
                                                                        .mCategory
                                                                : str,
                                                        null,
                                                        null,
                                                        null,
                                                        CommandTemplate.NO_TEMPLATE,
                                                        0,
                                                        null,
                                                        null,
                                                        map3.containsKey(string)
                                                                ? ((RoutinesCommandRegistry.Routine)
                                                                                ((LinkedHashMap)
                                                                                                map3)
                                                                                        .get(
                                                                                                string))
                                                                        .mIconResId
                                                                : i2));
                            }
                            if (!query.moveToNext()) {
                                break;
                            }
                            str = null;
                        }
                    }
                    query.close();
                } finally {
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        Log.i(
                "Command/SettingsCommandActionHandler",
                "getAvailableCommands() " + ((HashMap) this.mCachedCommandMap).size());
        return this.mCachedCommandMap;
    }

    public final BasePreferenceController getPreferenceController(Context context, String str) {
        ActionData actionData;
        BasePreferenceController createInstance;
        if (this.mDatabaseAccessor == null) {
            this.mDatabaseAccessor = new ActionsDatabaseAccessor(this.mContext);
        }
        ActionsDatabaseAccessor actionsDatabaseAccessor = this.mDatabaseAccessor;
        actionsDatabaseAccessor.getClass();
        try {
            Cursor indexedActionData = actionsDatabaseAccessor.getIndexedActionData(str);
            try {
                actionData = ActionsDatabaseAccessor.buildActionData(indexedActionData);
                indexedActionData.close();
            } finally {
            }
        } catch (IllegalStateException e) {
            Log.e("Command/ActionsDatabaseAccessor", e.getMessage());
            actionData = null;
        }
        if (actionData == null) {
            return null;
        }
        String str2 = actionData.mPreferenceController;
        try {
            createInstance = BasePreferenceController.createInstance(context, str2);
        } catch (IllegalStateException unused) {
            createInstance =
                    BasePreferenceController.createInstance(context, str2, actionData.mKey);
        }
        return createInstance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00b1  */
    /* JADX WARN: Type inference failed for: r12v11, types: [com.samsung.android.sdk.command.template.CommandTemplate] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.sdk.command.Command loadStateFullCommandInternal(
            java.lang.String r30, com.samsung.android.sdk.command.action.CommandAction r31) {
        /*
            Method dump skipped, instructions count: 381
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.actions.SettingsCommandActionHandler.loadStateFullCommandInternal(java.lang.String,"
                    + " com.samsung.android.sdk.command.action.CommandAction):com.samsung.android.sdk.command.Command");
    }

    public final Command loadStatefulCommand(String str) {
        Log.i("Command/SettingsCommandActionHandler", "loadStatefulCommand() " + str);
        return loadStateFullCommandInternal(str, new DefaultAction());
    }

    public final void performCommandAction(
            String str,
            CommandAction commandAction,
            CommandProvider.AnonymousClass1 anonymousClass1) {
        int availabilityStatus;
        IntentFilter intentFilter;
        Command loadStatefulCommand = loadStatefulCommand(str, commandAction);
        String lastPathSegment = Uri.parse(str).getLastPathSegment();
        Configuration configuration =
                new Configuration(this.mContext.getResources().getConfiguration());
        configuration.semDesktopModeEnabled = commandAction.mCommandParam.mDexMode ? 1 : 0;
        BasePreferenceController preferenceController =
                getPreferenceController(
                        this.mContext.createConfigurationContext(configuration), lastPathSegment);
        CommandTemplateResolver commandTemplateResolver = null;
        if (loadStatefulCommand == null
                || preferenceController == null
                || !((availabilityStatus = preferenceController.getAvailabilityStatus()) == 0
                        || availabilityStatus == 1)) {
            MainClear$$ExternalSyntheticOutline0.m(
                    ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                            "performCommandAction() controller is not available ", str, ", "),
                    preferenceController == null
                            ? "controller is null"
                            : preferenceController.getClass().getSimpleName(),
                    "Command/SettingsCommandActionHandler");
            anonymousClass1.onActionFinished(2, null);
            return;
        }
        boolean hasAsyncUpdate = preferenceController.hasAsyncUpdate();
        if (hasAsyncUpdate && (intentFilter = preferenceController.getIntentFilter()) != null) {
            if (ActionBroadcastRelayHandler.sInstance == null) {
                ActionBroadcastRelayHandler.sInstance = new ActionBroadcastRelayHandler();
            }
            ActionBroadcastRelayHandler actionBroadcastRelayHandler =
                    ActionBroadcastRelayHandler.sInstance;
            Context context = this.mContext;
            Uri parse = Uri.parse(str);
            if (actionBroadcastRelayHandler.mHandler == null) {
                actionBroadcastRelayHandler.mHandler =
                        new ActionBroadcastRelayHandler.AnonymousClass2(Looper.getMainLooper());
            }
            actionBroadcastRelayHandler.mHandler.removeMessages(0);
            Message message = new Message();
            message.what = 0;
            message.obj =
                    new Runnable() { // from class:
                                     // com.samsung.android.settings.actions.ActionBroadcastRelayHandler.1
                        public final /* synthetic */ Context val$context;

                        public AnonymousClass1(Context context2) {
                            r2 = context2;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            if (ActionBroadcastRelayHandler.this.mRelays.isEmpty()) {
                                return;
                            }
                            for (BroadcastRelay broadcastRelay :
                                    ActionBroadcastRelayHandler.this.mRelays.values()) {
                                Context context2 = r2;
                                broadcastRelay.getClass();
                                String str2 = BroadcastRelay.TAG;
                                try {
                                    context2.unregisterReceiver(broadcastRelay);
                                } catch (IllegalArgumentException e) {
                                    SemLog.e(
                                            str2,
                                            "failed to unregister receiver : " + e.getMessage());
                                }
                                SemLog.d(
                                        str2,
                                        "unreigster receiver : " + broadcastRelay.mUri.toString());
                            }
                            ActionBroadcastRelayHandler.this.mRelays.clear();
                        }
                    };
            if (actionBroadcastRelayHandler.mHandler == null) {
                actionBroadcastRelayHandler.mHandler =
                        new ActionBroadcastRelayHandler.AnonymousClass2(Looper.getMainLooper());
            }
            actionBroadcastRelayHandler.mHandler.sendMessageDelayed(message, 10000L);
            ActionBroadcastRelayHandler.BroadcastRelay broadcastRelay =
                    (ActionBroadcastRelayHandler.BroadcastRelay)
                            actionBroadcastRelayHandler.mRelays.get(parse);
            if (broadcastRelay == null) {
                broadcastRelay = new ActionBroadcastRelayHandler.BroadcastRelay(parse);
                actionBroadcastRelayHandler.mRelays.put(parse, broadcastRelay);
            }
            String str2 = ActionBroadcastRelayHandler.BroadcastRelay.TAG;
            try {
                context2.registerReceiver(broadcastRelay, intentFilter, 2);
            } catch (IllegalArgumentException e) {
                SemLog.e(str2, "failed to register receiver : " + e.getMessage());
            }
            SemLog.d(str2, "reigster receiver : " + broadcastRelay.mUri.toString());
        }
        int templateType = loadStatefulCommand.mTemplate.getTemplateType();
        if (templateType == 1) {
            commandTemplateResolver = new NoTemplateResolver();
        } else if (templateType == 2) {
            commandTemplateResolver = new ToggleTemplateResolver();
        } else if (templateType == 3) {
            commandTemplateResolver = new SliderTemplateRsolver();
        } else if (templateType == 5) {
            commandTemplateResolver = new SingleChoiceTemplateResolver();
        }
        commandTemplateResolver.resolve(
                commandAction,
                preferenceController,
                new AnonymousClass1(hasAsyncUpdate, anonymousClass1));
    }

    public final Command loadStatefulCommand(String str, CommandAction commandAction) {
        StringBuilder m =
                ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                        "loadStatefulCommand() ", str, " / ");
        m.append(commandAction.mCommandParam.toString());
        Log.i("Command/SettingsCommandActionHandler", m.toString());
        return loadStateFullCommandInternal(str, commandAction);
    }
}
