package com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Job;

import java.util.NoSuchElementException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AnalyzeStorageSubList {
    public final Lazy asSubList$delegate;
    public final Context context;
    public AbsPageController controller;
    public Job delayJob;
    public boolean duringLoading;
    public TextView headerSize;
    public ImageView icon;
    public final LifecycleOwner lifecycleOwner;
    public String logTag;
    public TextView mainText;
    public ProgressBar progressBar;
    public View rootView;
    public final SizeInfo sizeInfo;
    public TextView subText;
    public final int type;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SizeInfo {
        public final MutableLiveData totalSize = new MutableLiveData(ApnSettings.MVNO_NONE);
        public final MutableLiveData supportSize = new MutableLiveData(Boolean.TRUE);
    }

    public AnalyzeStorageSubList(Context context, LifecycleOwner lifecycleOwner, int i) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "lifecycleOwner");
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
        this.type = i;
        this.sizeInfo = new SizeInfo();
        this.asSubList$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.AnalyzeStorageSubList$asSubList$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                AsSubListFactory.Companion companion = AsSubListFactory.Companion;
                                int i2 = AnalyzeStorageSubList.this.type;
                                companion.getClass();
                                for (AsSubListFactory asSubListFactory :
                                        AsSubListFactory.values()) {
                                    if (asSubListFactory.getSaType() == i2) {
                                        return asSubListFactory.createASList();
                                    }
                                }
                                throw new NoSuchElementException(
                                        "Array contains no element matching the predicate.");
                            }
                        });
        this.logTag = "AnalyzeStorageSubList";
    }

    public final AsSubListInterface getAsSubList() {
        return (AsSubListInterface) this.asSubList$delegate.getValue();
    }

    public abstract MutableLiveData getListItemData();

    public abstract MutableLiveData getListItemTotalSizeData();

    public abstract MutableLiveData getListLoading();

    public void initLayout() {
        View view = this.rootView;
        if (view != null) {
            TextView textView = (TextView) view.findViewById(R.id.main_text);
            this.mainText = textView;
            if (textView != null) {
                textView.setText(getAsSubList().getListTitleResId());
            }
            TextView textView2 = (TextView) view.findViewById(R.id.sub_text);
            this.subText = textView2;
            if (textView2 != null) {
                textView2.setText(getAsSubList().getListSubTitle(this.context));
            }
            ImageView imageView = (ImageView) view.findViewById(R.id.icon);
            this.icon = imageView;
            if (imageView != null) {
                imageView.setImageResource(getAsSubList().getIconResId());
            }
            MutableLiveData listItemData = getListItemData();
            if (listItemData != null) {
                listItemData.observe(
                        this.lifecycleOwner, new AnalyzeStorageSubList$observeList$1(this, 0));
            }
            if (this.type != 3) {
                refresh();
            }
            View view2 = this.rootView;
            ImageView imageView2 =
                    view2 != null ? (ImageView) view2.findViewById(R.id.error_icon) : null;
            if (imageView2 == null) {
                return;
            }
            imageView2.setVisibility(8);
        }
    }

    public void observeTotalSize() {
        MutableLiveData listItemTotalSizeData = getListItemTotalSizeData();
        if (listItemTotalSizeData != null) {
            listItemTotalSizeData.observe(
                    this.lifecycleOwner, new AnalyzeStorageSubList$observeList$1(this, 3));
        }
    }

    public abstract void refresh();

    public void onResume() {}
}
