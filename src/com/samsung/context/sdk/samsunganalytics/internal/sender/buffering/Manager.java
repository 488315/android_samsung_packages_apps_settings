package com.samsung.context.sdk.samsunganalytics.internal.sender.buffering;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.viewpager2.widget.ViewPager2$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.database.DbManager;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.database.DefaultDBOpenHelper;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.queue.QueueManager;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Manager {
    public static Manager instance;
    public DbManager dbManager;
    public final QueueManager queueManager;
    public boolean useDatabase;

    public Manager(Context context, boolean z) {
        if (z) {
            this.dbManager = new DbManager(context);
        }
        this.queueManager = new QueueManager();
        this.useDatabase = z;
    }

    public static Manager getInstance(Context context, Configuration configuration) {
        if (instance == null) {
            synchronized (Manager.class) {
                try {
                    configuration.getClass();
                    if (PolicyUtils.senderType != 0) {
                        instance = new Manager(context, false);
                    } else if (Preferences.getPreferences(context)
                            .getString("lgt", ApnSettings.MVNO_NONE)
                            .equals("rtb")) {
                        instance = new Manager(context, true);
                    } else {
                        instance = new Manager(context, false);
                    }
                } finally {
                }
            }
        }
        return instance;
    }

    public final Queue get(int i) {
        Queue queue;
        boolean z = this.useDatabase;
        if (z) {
            if (z) {
                ((DefaultDBOpenHelper) this.dbManager.dbOpenHelper)
                        .getWritableDatabase()
                        .delete(
                                "logs_v2",
                                ViewPager2$$ExternalSyntheticOutline0.m(
                                        System.currentTimeMillis() - (5 * 86400000),
                                        "timestamp <= "),
                                null);
            }
            if (i <= 0) {
                queue = this.dbManager.select("select * from logs_v2");
            } else {
                DbManager dbManager = this.dbManager;
                dbManager.getClass();
                queue = dbManager.select("select * from logs_v2 LIMIT " + i);
            }
        } else {
            queue = this.queueManager.logQueue;
        }
        if (!queue.isEmpty()) {
            StringBuilder sb = new StringBuilder("get log from ");
            sb.append(this.useDatabase ? "Database " : "Queue ");
            sb.append("(");
            sb.append(queue.size());
            sb.append(")");
            Debug.LogENG(sb.toString());
        }
        return queue;
    }

    public final void insert(SimpleLog simpleLog) {
        if (this.useDatabase) {
            this.dbManager.insert(simpleLog);
            return;
        }
        QueueManager queueManager = this.queueManager;
        if (queueManager.logQueue.offer(simpleLog)) {
            return;
        }
        Debug.LogD("QueueManager", "queue size over. remove oldest log");
        queueManager.logQueue.poll();
        queueManager.logQueue.offer(simpleLog);
    }

    public final void remove(List list) {
        ArrayList arrayList = (ArrayList) list;
        if (!arrayList.isEmpty() && this.useDatabase) {
            SQLiteDatabase writableDatabase =
                    ((DefaultDBOpenHelper) this.dbManager.dbOpenHelper).getWritableDatabase();
            writableDatabase.beginTransaction();
            try {
                try {
                    int size = arrayList.size();
                    int i = 0;
                    while (size > 0) {
                        int i2 = 900;
                        if (size < 900) {
                            i2 = size;
                        }
                        int i3 = i + i2;
                        List subList = arrayList.subList(i, i3);
                        writableDatabase.delete(
                                "logs_v2",
                                ("_id IN("
                                                + new String(new char[subList.size() - 1])
                                                        .replaceAll("\u0000", "?,"))
                                        + "?)",
                                (String[]) subList.toArray(new String[0]));
                        size -= i2;
                        i = i3;
                    }
                    arrayList.clear();
                    writableDatabase.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                writableDatabase.endTransaction();
            } catch (Throwable th) {
                writableDatabase.endTransaction();
                throw th;
            }
        }
    }
}
