package com.samsung.android.settings.flipfont;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.secutil.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.fontutil.FontCache;
import com.samsung.android.fontutil.TypefaceFinder;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.display.SecFontStylePreferenceFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class FontListAdapter extends RecyclerView.Adapter {
    public static FontListAdapter mFontListAdapter;
    public final String downloadFontString;
    public final Context mContext;
    public SecFontStylePreferenceFragment mOnItemClickListener;
    public final PackageManager mPackageManager;
    public final Resources mResources;
    public final TypefaceFinder mTypefaceFinder = new TypefaceFinder();
    public final ArrayList mFontPackageNames = new ArrayList();
    public final ArrayList mFontNames = new ArrayList();
    public final ArrayList mTypefaceFiles = new ArrayList();
    public final ArrayList mTypefaces = new ArrayList();
    public boolean mIsFontPreviewActivity = false;
    public final List mListItems = new ArrayList();
    public boolean mDownloadFontEnabled = true;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class FontList {
        public boolean mIsChecked;
        public String mListItemTitle;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RecyclerFooterViewHolder extends RecyclerView.ViewHolder {
        public View mFooterView;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public RadioButton mCheckBoxView;
        public TextView mTitleView;
    }

    public FontListAdapter(Context context) {
        this.mContext = context;
        Resources resources = context.getResources();
        this.mResources = resources;
        PackageManager packageManager = context.getPackageManager();
        this.mPackageManager = packageManager;
        this.downloadFontString =
                (String) resources.getText(R.string.sec_font_style_download_font_title);
        try {
            Iterator<ApplicationInfo> it = packageManager.getInstalledApplications(128).iterator();
            while (it.hasNext()) {
                String str = it.next().packageName;
                if (str.startsWith("com.monotype.android.font.")) {
                    ApplicationInfo applicationInfo =
                            this.mPackageManager.getApplicationInfo(str, 128);
                    applicationInfo.publicSourceDir = applicationInfo.sourceDir;
                    this.mTypefaceFinder.findTypefaces(
                            this.mContext,
                            this.mPackageManager
                                    .getResourcesForApplication(applicationInfo)
                                    .getAssets(),
                            str);
                }
            }
            this.mTypefaceFinder.getSansEntries(
                    this.mContext,
                    this.mPackageManager,
                    this.mFontNames,
                    this.mTypefaceFiles,
                    this.mFontPackageNames);
        } catch (Exception e) {
            Log.secV("FontListAdapter", "font package not found, just use default font, " + e);
        }
    }

    public static FontListAdapter getInstanceFontListAdapter(Context context) {
        FontListAdapter fontListAdapter = mFontListAdapter;
        if (fontListAdapter == null) {
            mFontListAdapter = new FontListAdapter(context);
        } else {
            fontListAdapter.mFontNames.remove(0);
            mFontListAdapter.mTypefaceFiles.remove(0);
            mFontListAdapter.mFontNames.add(
                    0, context.getResources().getText(R.string.sec_monotype_default));
            mFontListAdapter.mTypefaceFiles.add(0, "default");
        }
        FontListAdapter fontListAdapter2 = mFontListAdapter;
        fontListAdapter2.mIsFontPreviewActivity = false;
        return fontListAdapter2;
    }

    public final Typeface getFont(String str, String str2) {
        try {
            ApplicationInfo applicationInfo = this.mPackageManager.getApplicationInfo(str2, 128);
            applicationInfo.publicSourceDir = applicationInfo.sourceDir;
            return FontCache.get(
                    "fonts/" + str.replace(".xml", ".ttf"),
                    this.mPackageManager.getResourcesForApplication(applicationInfo).getAssets());
        } catch (Exception e) {
            Log.e(
                    "FontListAdapter",
                    "Font Package was not found. return null and this Font will be removed. " + e);
            return null;
        }
    }

    public final String getFontName(int i) {
        String str = (String) this.mFontNames.get(i);
        if (str.equals("Cool")) {
            str = (String) this.mResources.getText(R.string.sec_monotype_dialog_font_cool);
        } else if (str.equals("Rose")) {
            str = (String) this.mResources.getText(R.string.sec_monotype_dialog_font_rose);
        } else if (str.equals("Choco")) {
            str = (String) this.mResources.getText(R.string.sec_monotype_dialog_font_choco);
        } else if (str.equals("Rosemary")) {
            str = (String) this.mResources.getText(R.string.sec_monotype_dialog_font_rose);
        } else if (str.equals("Chococooky")) {
            str = (String) this.mResources.getText(R.string.sec_monotype_dialog_font_choco);
        } else if (str.equals("Cooljazz")) {
            str = (String) this.mResources.getText(R.string.sec_monotype_dialog_font_cool);
        } else if (str.equals("Samsungsans") || str.equals("SamsungSans")) {
            str = "Samsung Sans";
        }
        if (str.equals("Roboto")) {
            return "Roboto";
        }
        if (str.equals("Applemint")) {
            return (String) this.mResources.getText(R.string.sec_monotype_dialog_font_applemint);
        }
        if (str.equals("Tinkerbell")) {
            return (String) this.mResources.getText(R.string.sec_monotype_dialog_font_tinkerbell);
        }
        if (str.equals("Shaonv")) {
            return (String) this.mResources.getText(R.string.sec_monotype_dialog_font_girl);
        }
        if (str.equals("Kaiti")) {
            return (String) this.mResources.getText(R.string.sec_monotype_dialog_font_kaiti);
        }
        if (str.equals("Miao")) {
            return (String) this.mResources.getText(R.string.sec_monotype_dialog_font_miao);
        }
        if (str.equals("Foundation")) {
            return (String) this.mResources.getText(R.string.sec_monotype_dialog_font_gothicbold);
        }
        String str2 = this.downloadFontString;
        return str.equals(str2) ? str2 : str;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mFontNames.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        return (this.mDownloadFontEnabled && this.mFontNames.size() - 1 == i) ? 1 : 0;
    }

    public final void loadTypefaces() {
        Context context = this.mContext;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str =
                (String) context.getResources().getText(R.string.sec_monotype_samsung_apps_uri);
        Intent intent = new Intent();
        intent.setData(Uri.parse(str));
        intent.addFlags(335544352);
        Intent fontDownloadMarketIntent =
                SecDisplayUtils.getFontDownloadMarketIntent(this.mContext);
        boolean z = this.mIsFontPreviewActivity;
        String str2 = this.downloadFontString;
        if ((!z
                        || (!Utils.isIntentAvailable(this.mContext, intent)
                                && !Utils.isIntentAvailable(
                                        this.mContext, fontDownloadMarketIntent)))
                && this.mFontPackageNames.contains(str2)) {
            this.mFontPackageNames.remove(str2);
            this.mFontNames.remove(str2);
            this.mDownloadFontEnabled = false;
        }
        this.mTypefaces.add(Typeface.createFromFile("/system/fonts/DroidSans.ttf"));
        int i = 1;
        while (i < this.mTypefaceFiles.size()) {
            Typeface font =
                    getFont(
                            this.mTypefaceFiles.get(i).toString(),
                            this.mFontPackageNames.get(i).toString());
            if (font == null) {
                this.mFontPackageNames.remove(i);
                this.mFontNames.remove(i);
                this.mTypefaceFiles.remove(i);
            } else {
                this.mTypefaces.add(font);
                i++;
            }
        }
        if (this.mIsFontPreviewActivity) {
            this.mTypefaces.add(getFont(str2, str2));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        if (!(viewHolder instanceof RecyclerViewHolder)) {
            if (viewHolder instanceof RecyclerFooterViewHolder) {
                final int i2 = 1;
                viewHolder.itemView.setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.flipfont.FontListAdapter.1
                            public final /* synthetic */ FontListAdapter this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i2) {
                                    case 0:
                                        this.this$0.mOnItemClickListener.onItemClick(i);
                                        break;
                                    default:
                                        this.this$0.mOnItemClickListener.onItemClick(i);
                                        break;
                                }
                            }
                        });
                return;
            }
            return;
        }
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
        recyclerViewHolder.mTitleView.setText(
                ((FontList) ((ArrayList) this.mListItems).get(i)).mListItemTitle);
        recyclerViewHolder.mCheckBoxView.setChecked(
                ((FontList) ((ArrayList) this.mListItems).get(i)).mIsChecked);
        final int i3 = 0;
        viewHolder.itemView.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.flipfont.FontListAdapter.1
                    public final /* synthetic */ FontListAdapter this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i3) {
                            case 0:
                                this.this$0.mOnItemClickListener.onItemClick(i);
                                break;
                            default:
                                this.this$0.mOnItemClickListener.onItemClick(i);
                                break;
                        }
                    }
                });
        TextView textView = recyclerViewHolder.mTitleView;
        synchronized (this.mTypefaces) {
            try {
                if (this.mTypefaces.size() - 1 == i) {
                    return;
                }
                if (i == 0) {
                    textView.setTypeface(Typeface.create("sec-no-flip", 0));
                    return;
                }
                Typeface typeface = (Typeface) this.mTypefaces.get(i);
                if (typeface != null) {
                    textView.setTypeface(typeface, 0);
                }
            } finally {
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 1) {
            View m =
                    MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                            viewGroup, R.layout.sec_download_font_layout, viewGroup, false);
            RecyclerFooterViewHolder recyclerFooterViewHolder = new RecyclerFooterViewHolder(m);
            recyclerFooterViewHolder.mFooterView = m;
            return recyclerFooterViewHolder;
        }
        if (i != 0) {
            return null;
        }
        View m2 =
                MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                        viewGroup, R.layout.sec_widget_list_item_with_radiobox, viewGroup, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(m2);
        recyclerViewHolder.mTitleView = (TextView) m2.findViewById(android.R.id.text1);
        recyclerViewHolder.mCheckBoxView = (RadioButton) m2.findViewById(R.id.checkbox);
        return recyclerViewHolder;
    }

    public final void setInitDownloadFontAndThemeFont() {
        ArrayList arrayList;
        if (this.mFontNames == null
                || (arrayList = this.mFontPackageNames) == null
                || this.mContext == null) {
            return;
        }
        String str = this.downloadFontString;
        if (!arrayList.contains(str)) {
            this.mFontPackageNames.add(str);
            this.mFontNames.add(str);
        }
        if (this.mFontNames != null) {
            for (int i = 0; i < this.mFontNames.size() - 1; i++) {
                String fontName = getFontName(i);
                FontList fontList = new FontList();
                fontList.mListItemTitle = fontName;
                ((ArrayList) this.mListItems).add(fontList);
                notifyDataSetChanged();
            }
        }
    }

    public final void setItemChecked(int i) {
        int size = this.mFontNames.size() - (this.mDownloadFontEnabled ? 1 : 0);
        int i2 = 0;
        while (i2 < size) {
            ((FontList) ((ArrayList) this.mListItems).get(i2)).mIsChecked = i == i2;
            notifyItemChanged(i2);
            i2++;
        }
    }
}
