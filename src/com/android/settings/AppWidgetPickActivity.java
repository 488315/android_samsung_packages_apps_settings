package com.android.settings;

import android.appwidget.AppWidgetManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class AppWidgetPickActivity extends ActivityPicker
        implements AppWidgetLoader.ItemConstructor {
    public int mAppWidgetId;
    public AppWidgetLoader mAppWidgetLoader;
    public AppWidgetManager mAppWidgetManager;
    public List mItems;
    public PackageManager mPackageManager;

    @Override // com.android.settings.ActivityPicker
    public final List getItems() {
        AppWidgetLoader appWidgetLoader = this.mAppWidgetLoader;
        Intent intent = getIntent();
        appWidgetLoader.getClass();
        boolean booleanExtra = intent.getBooleanExtra("customSort", true);
        ArrayList arrayList = new ArrayList();
        int intExtra = intent.getIntExtra("categoryFilter", 1);
        appWidgetLoader.putAppWidgetItems(
                appWidgetLoader.mAppWidgetManager.getInstalledProviders(intExtra),
                null,
                arrayList,
                intExtra,
                false);
        if (booleanExtra) {
            appWidgetLoader.putCustomAppWidgets(intent, arrayList);
        }
        AppWidgetLoader.AnonymousClass1 anonymousClass1 = new AppWidgetLoader.AnonymousClass1();
        anonymousClass1.mCollator = Collator.getInstance();
        Collections.sort(arrayList, anonymousClass1);
        if (!booleanExtra) {
            ArrayList arrayList2 = new ArrayList();
            appWidgetLoader.putCustomAppWidgets(intent, arrayList2);
            arrayList.addAll(arrayList2);
        }
        this.mItems = arrayList;
        return arrayList;
    }

    @Override // com.android.settings.ActivityPicker,
              // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        Intent intentForPosition = getIntentForPosition(i);
        int i2 = -1;
        if (((ActivityPicker.PickAdapter.Item) ((ArrayList) this.mItems).get(i)).extras != null) {
            setResultData(-1, intentForPosition);
        } else {
            try {
                this.mAppWidgetManager.bindAppWidgetId(
                        this.mAppWidgetId,
                        intentForPosition.getComponent(),
                        intentForPosition.getExtras() != null
                                ? intentForPosition.getExtras().getBundle("appWidgetOptions")
                                : null);
            } catch (IllegalArgumentException unused) {
                i2 = 0;
            }
            setResultData(i2, null);
        }
        finish();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.settings.ActivityPicker
    public final void onCreate(Bundle bundle) {
        this.mPackageManager = getPackageManager();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        this.mAppWidgetManager = appWidgetManager;
        AppWidgetLoader appWidgetLoader = new AppWidgetLoader();
        appWidgetLoader.mContext = this;
        appWidgetLoader.mAppWidgetManager = appWidgetManager;
        appWidgetLoader.mItemConstructor = this;
        this.mAppWidgetLoader = appWidgetLoader;
        super.onCreate(bundle);
        setResultData(0, null);
        Intent intent = getIntent();
        if (intent.hasExtra("appWidgetId")) {
            this.mAppWidgetId = intent.getIntExtra("appWidgetId", 0);
        } else {
            finish();
        }
    }

    public final void setResultData(int i, Intent intent) {
        if (intent == null) {
            intent = new Intent();
        }
        intent.putExtra("appWidgetId", this.mAppWidgetId);
        setResult(i, intent);
    }
}
