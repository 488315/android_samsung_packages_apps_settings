package com.samsung.android.settings.mde;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ShortcutInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.samsung.android.gtscell.data.FieldName;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class MDEServiceBindingManager {
    public final WeakReference mContext;
    public boolean mBound = false;
    public Messenger mServiceCallbackMessenger = null;
    public Messenger mClientCallbackMessenger = null;
    public CallbackHandler mCallbackHandler = null;
    public final ArrayList mCallbacks = new ArrayList();
    public final AnonymousClass1 mServiceConnection = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.mde.MDEServiceBindingManager$1, reason: invalid class name */
    public final class AnonymousClass1 implements ServiceConnection {
        public AnonymousClass1() {}

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("MDEServiceBindingManager", "onServiceConnected");
            MDEServiceBindingManager.this.mCallbackHandler =
                    MDEServiceBindingManager.this.new CallbackHandler(Looper.myLooper());
            MDEServiceBindingManager.this.mClientCallbackMessenger =
                    new Messenger(MDEServiceBindingManager.this.mCallbackHandler);
            MDEServiceBindingManager.this.mServiceCallbackMessenger = new Messenger(iBinder);
            MDEServiceBindingManager.this.sendMessage(6, null);
            MDEServiceBindingManager.this.sendMessage(1, null);
            new Handler(Looper.myLooper())
                    .postDelayed(
                            new Runnable() { // from class:
                                             // com.samsung.android.settings.mde.MDEServiceBindingManager$1$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    MDEServiceBindingManager.this.sendMessage(0, null);
                                }
                            },
                            1500L);
            MDEServiceBindingManager.this.mBound = true;
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            Log.d("MDEServiceBindingManager", "onServiceDisconnected");
            Iterator it = MDEServiceBindingManager.this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onSuggestionsUpdated(new ArrayList());
            }
            MDEServiceBindingManager mDEServiceBindingManager = MDEServiceBindingManager.this;
            mDEServiceBindingManager.mCallbackHandler = null;
            mDEServiceBindingManager.mClientCallbackMessenger = null;
            mDEServiceBindingManager.mServiceCallbackMessenger = null;
            mDEServiceBindingManager.mBound = false;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Callback {
        void onRequestUpdated();

        void onSuggestionsUpdated(List list);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CallbackHandler extends Handler {
        public CallbackHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Preference$$ExternalSyntheticOutline0.m(
                    new StringBuilder("handleMessage : "),
                    message.what,
                    "MDEServiceBindingManager");
            int i = message.what;
            MDEServiceBindingManager mDEServiceBindingManager = MDEServiceBindingManager.this;
            if (i == 4) {
                Iterator it = mDEServiceBindingManager.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((Callback) it.next()).onRequestUpdated();
                }
                return;
            }
            if (i != 5) {
                return;
            }
            Bundle bundle = (Bundle) message.obj;
            Log.d(
                    "MDEServiceBindingManager",
                    "handleMessage : version is " + bundle.getInt(FieldName.VERSION));
            ArrayList arrayList = new ArrayList();
            mDEServiceBindingManager.getClass();
            ArrayList<ShortcutInfo> parcelableArrayList =
                    bundle.getParcelableArrayList("shortcutInfo_list");
            if (parcelableArrayList == null) {
                Log.i(
                        "MDEServiceBindingManager",
                        "updateSuggestionInfoList: shortcutInfoList is null");
                return;
            }
            Log.d(
                    "MDEServiceBindingManager",
                    "updateSuggestionInfoList : shortcutInfoList is " + parcelableArrayList);
            for (ShortcutInfo shortcutInfo : parcelableArrayList) {
                SuggestionInfo suggestionInfo = new SuggestionInfo();
                suggestionInfo.mUiType = "depth_in";
                suggestionInfo.mId = shortcutInfo.getId();
                suggestionInfo.mTitle = shortcutInfo.getShortLabel();
                suggestionInfo.mSummary = shortcutInfo.getLongLabel();
                shortcutInfo.getPackage();
                suggestionInfo.mIntent = shortcutInfo.getIntent();
                suggestionInfo.mExtras = shortcutInfo.getExtras();
                suggestionInfo.mIcon = shortcutInfo.getIcon();
                arrayList.add(suggestionInfo);
            }
            mDEServiceBindingManager.getClass();
            ArrayList<String> stringArrayList = bundle.getStringArrayList("id_list");
            if (stringArrayList == null) {
                Log.i("MDEServiceBindingManager", "updateSuggestionUiType: idList is null");
            } else {
                Log.d(
                        "MDEServiceBindingManager",
                        "updateSuggestionUiType : idList is " + stringArrayList);
                for (String str : stringArrayList) {
                    if (!TextUtils.isEmpty(str)) {
                        String[] split = str.split("/");
                        if (split.length > 2) {
                            String str2 = split[1];
                            String str3 = split[2];
                            Iterator it2 = arrayList.iterator();
                            while (it2.hasNext()) {
                                SuggestionInfo suggestionInfo2 = (SuggestionInfo) it2.next();
                                if (TextUtils.equals(str2, suggestionInfo2.mId)) {
                                    suggestionInfo2.mUiType = str3;
                                }
                            }
                        }
                    }
                }
            }
            Iterator it3 = mDEServiceBindingManager.mCallbacks.iterator();
            while (it3.hasNext()) {
                ((Callback) it3.next()).onSuggestionsUpdated(arrayList);
            }
        }
    }

    public MDEServiceBindingManager(Context context) {
        Log.d("MDEServiceBindingManager", "MDEServiceBindingManager()");
        this.mContext = new WeakReference(context);
    }

    public final void sendMessage(int i, Bundle bundle) {
        Message obtain = Message.obtain((Handler) null, i);
        obtain.replyTo = this.mClientCallbackMessenger;
        if (bundle != null) {
            obtain.obj = bundle;
        }
        try {
            Messenger messenger = this.mServiceCallbackMessenger;
            if (messenger != null) {
                messenger.send(obtain);
                Log.i(
                        "MDEServiceBindingManager",
                        "sendMessage : Send message to Service. what = " + i);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final void startServiceBind() {
        ComponentName componentName =
                new ComponentName(
                        "com.samsung.android.smartsuggestions",
                        "com.samsung.android.smartsuggestions.mde.MDEService");
        Context context = (Context) this.mContext.get();
        if (context == null
                || context.bindService(
                        new Intent().setComponent(componentName), this.mServiceConnection, 1)) {
            return;
        }
        Log.i("MDEServiceBindingManager", "startServiceBind: binding is failed.");
        Iterator it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onSuggestionsUpdated(new ArrayList());
        }
    }

    public final void stopServiceBind() {
        if (this.mBound) {
            Context context = (Context) this.mContext.get();
            if (context != null) {
                context.unbindService(this.mServiceConnection);
            }
            sendMessage(7, null);
            this.mCallbackHandler = null;
            this.mClientCallbackMessenger = null;
            this.mServiceCallbackMessenger = null;
            this.mBound = false;
        }
    }
}
