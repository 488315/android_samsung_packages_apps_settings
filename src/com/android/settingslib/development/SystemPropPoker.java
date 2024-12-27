package com.android.settingslib.development;

import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SystemPropPoker {
    public static final SystemPropPoker sInstance;
    public boolean mBlockPokes;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PokerTask extends AsyncTask {
        public IBinder checkService(String str) {
            return ServiceManager.checkService(str);
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            String[] listServices = listServices();
            if (listServices == null) {
                Log.e("SystemPropPoker", "There are no services, how odd");
            } else {
                for (String str : listServices) {
                    IBinder checkService = checkService(str);
                    if (checkService != null) {
                        Parcel obtain = Parcel.obtain();
                        try {
                            checkService.transact(1599295570, obtain, null, 0);
                        } catch (RemoteException unused) {
                        } catch (Exception e) {
                            Log.i(
                                    "SystemPropPoker",
                                    "Someone wrote a bad service '"
                                            + str
                                            + "' that doesn't like to be poked",
                                    e);
                        }
                        obtain.recycle();
                    }
                }
            }
            return null;
        }

        public String[] listServices() {
            return ServiceManager.listServices();
        }
    }

    static {
        SystemPropPoker systemPropPoker = new SystemPropPoker();
        systemPropPoker.mBlockPokes = false;
        sInstance = systemPropPoker;
    }

    public PokerTask createPokerTask() {
        return new PokerTask();
    }

    public final void poke() {
        if (this.mBlockPokes) {
            return;
        }
        createPokerTask().execute(new Void[0]);
    }
}
