package com.android.settings.development.storage;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.blob.BlobInfo;
import android.app.blob.BlobStoreManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.internal.util.CollectionUtils;
import com.android.settings.R;

import java.io.IOException;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LeaseInfoListView extends ListActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public LeaseListAdapter mAdapter;
    public BlobInfo mBlobInfo;
    public BlobStoreManager mBlobStoreManager;
    public LeaseInfoListView mContext;
    public LayoutInflater mInflater;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LeaseListAdapter extends ArrayAdapter {
        public final Context mContext;

        public LeaseListAdapter(Context context) {
            super(context, 0);
            this.mContext = context;
            List leases = LeaseInfoListView.this.mBlobInfo.getLeases();
            if (CollectionUtils.isEmpty(leases)) {
                return;
            }
            addAll(leases);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x00b1, code lost:

           r1.setText(r0);
           r7 = r7.leaseExpiry;
           r0 = r6.getExpiryTimeMillis();
        */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x00be, code lost:

           if (r0 != 0) goto L26;
        */
        /* JADX WARN: Code restructure failed: missing block: B:15:0x00c0, code lost:

           r5 = r5.this$0.getString(com.android.settings.R.string.accessor_never_expires_text);
        */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x00e6, code lost:

           r7.setText(r5);
        */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x00e9, code lost:

           return r8;
        */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x00ca, code lost:

           r5 = r5.this$0;
           r6 = com.android.settings.development.storage.SharedDataUtils.CALENDAR;
           r6.setTimeInMillis(r0);
           r5 = r5.getString(com.android.settings.R.string.accessor_expires_text, new java.lang.Object[]{com.android.settings.development.storage.SharedDataUtils.FORMATTER.format(r6.getTime())});
        */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x00ae, code lost:

           if (android.text.TextUtils.isEmpty(r0) == false) goto L23;
        */
        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final android.view.View getView(
                int r6, android.view.View r7, android.view.ViewGroup r8) {
            /*
                r5 = this;
                com.android.settings.development.storage.LeaseInfoListView r8 = com.android.settings.development.storage.LeaseInfoListView.this
                android.view.LayoutInflater r8 = r8.mInflater
                r0 = 0
                if (r7 == 0) goto Le
                java.lang.Object r7 = r7.getTag()
                com.android.settings.development.storage.LeaseInfoViewHolder r7 = (com.android.settings.development.storage.LeaseInfoViewHolder) r7
                goto L4c
            Le:
                r7 = 2131559511(0x7f0d0457, float:1.8744368E38)
                android.view.View r7 = r8.inflate(r7, r0)
                com.android.settings.development.storage.LeaseInfoViewHolder r8 = new com.android.settings.development.storage.LeaseInfoViewHolder
                r8.<init>()
                r8.rootView = r7
                r1 = 2131362062(0x7f0a010e, float:1.8343894E38)
                android.view.View r1 = r7.findViewById(r1)
                android.widget.ImageView r1 = (android.widget.ImageView) r1
                r8.appIcon = r1
                r1 = 2131363983(0x7f0a088f, float:1.834779E38)
                android.view.View r1 = r7.findViewById(r1)
                android.widget.TextView r1 = (android.widget.TextView) r1
                r8.leasePackageName = r1
                r1 = 2131363981(0x7f0a088d, float:1.8347786E38)
                android.view.View r1 = r7.findViewById(r1)
                android.widget.TextView r1 = (android.widget.TextView) r1
                r8.leaseDescription = r1
                r1 = 2131363982(0x7f0a088e, float:1.8347788E38)
                android.view.View r1 = r7.findViewById(r1)
                android.widget.TextView r1 = (android.widget.TextView) r1
                r8.leaseExpiry = r1
                r7.setTag(r8)
                r7 = r8
            L4c:
                android.view.View r8 = r7.rootView
                r1 = 0
                r8.setEnabled(r1)
                java.lang.Object r6 = r5.getItem(r6)
                android.app.blob.LeaseInfo r6 = (android.app.blob.LeaseInfo) r6
                android.content.Context r1 = r5.mContext     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L67
                android.content.pm.PackageManager r1 = r1.getPackageManager()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L67
                java.lang.String r2 = r6.getPackageName()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L67
                android.graphics.drawable.Drawable r1 = r1.getApplicationIcon(r2)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L67
                goto L70
            L67:
                android.content.Context r1 = r5.mContext
                r2 = 17301651(0x1080093, float:2.4979667E-38)
                android.graphics.drawable.Drawable r1 = r1.getDrawable(r2)
            L70:
                android.widget.ImageView r2 = r7.appIcon
                r2.setImageDrawable(r1)
                android.widget.TextView r1 = r7.leasePackageName
                java.lang.String r2 = r6.getPackageName()
                r1.setText(r2)
                android.widget.TextView r1 = r7.leaseDescription
                r2 = 2132017712(0x7f140230, float:1.967371E38)
                com.android.settings.development.storage.LeaseInfoListView r3 = com.android.settings.development.storage.LeaseInfoListView.this     // Catch: java.lang.Throwable -> L9a android.content.res.Resources.NotFoundException -> L9c
                int r4 = r6.getDescriptionResId()     // Catch: java.lang.Throwable -> L9a android.content.res.Resources.NotFoundException -> L9c
                java.lang.String r0 = r3.getString(r4)     // Catch: java.lang.Throwable -> L9a android.content.res.Resources.NotFoundException -> L9c
                boolean r3 = android.text.TextUtils.isEmpty(r0)
                if (r3 == 0) goto Lb1
            L93:
                com.android.settings.development.storage.LeaseInfoListView r0 = com.android.settings.development.storage.LeaseInfoListView.this
                java.lang.String r0 = r0.getString(r2)
                goto Lb1
            L9a:
                r6 = move-exception
                goto Lea
            L9c:
                java.lang.CharSequence r3 = r6.getDescription()     // Catch: java.lang.Throwable -> L9a
                if (r3 == 0) goto Laa
                java.lang.CharSequence r3 = r6.getDescription()     // Catch: java.lang.Throwable -> L9a
                java.lang.String r0 = r3.toString()     // Catch: java.lang.Throwable -> L9a
            Laa:
                boolean r3 = android.text.TextUtils.isEmpty(r0)
                if (r3 == 0) goto Lb1
                goto L93
            Lb1:
                r1.setText(r0)
                android.widget.TextView r7 = r7.leaseExpiry
                long r0 = r6.getExpiryTimeMillis()
                r2 = 0
                int r6 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r6 != 0) goto Lca
                com.android.settings.development.storage.LeaseInfoListView r5 = com.android.settings.development.storage.LeaseInfoListView.this
                r6 = 2132017711(0x7f14022f, float:1.9673708E38)
                java.lang.String r5 = r5.getString(r6)
                goto Le6
            Lca:
                com.android.settings.development.storage.LeaseInfoListView r5 = com.android.settings.development.storage.LeaseInfoListView.this
                android.icu.util.Calendar r6 = com.android.settings.development.storage.SharedDataUtils.CALENDAR
                r6.setTimeInMillis(r0)
                android.icu.text.SimpleDateFormat r0 = com.android.settings.development.storage.SharedDataUtils.FORMATTER
                java.util.Date r6 = r6.getTime()
                java.lang.String r6 = r0.format(r6)
                java.lang.Object[] r6 = new java.lang.Object[]{r6}
                r0 = 2132017709(0x7f14022d, float:1.9673704E38)
                java.lang.String r5 = r5.getString(r0, r6)
            Le6:
                r7.setText(r5)
                return r8
            Lea:
                boolean r7 = android.text.TextUtils.isEmpty(r0)
                if (r7 == 0) goto Lf5
                com.android.settings.development.storage.LeaseInfoListView r5 = com.android.settings.development.storage.LeaseInfoListView.this
                r5.getString(r2)
            Lf5:
                throw r6
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.settings.development.storage.LeaseInfoListView.LeaseListAdapter.getView(int,"
                        + " android.view.View, android.view.ViewGroup):android.view.View");
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = this;
        this.mBlobStoreManager = (BlobStoreManager) getSystemService(BlobStoreManager.class);
        this.mInflater = (LayoutInflater) getSystemService(LayoutInflater.class);
        BlobInfo parcelableExtra = getIntent().getParcelableExtra("BLOB_KEY");
        this.mBlobInfo = parcelableExtra;
        if (parcelableExtra == null) {
            finish();
            return;
        }
        LeaseListAdapter leaseListAdapter = new LeaseListAdapter(this);
        this.mAdapter = leaseListAdapter;
        if (leaseListAdapter.isEmpty()) {
            Log.e(
                    "LeaseInfoListView",
                    "Error fetching leases for shared data: " + this.mBlobInfo.toString());
            finish();
        }
        setListAdapter(this.mAdapter);
        ListView listView = getListView();
        LinearLayout linearLayout =
                (LinearLayout)
                        this.mInflater.inflate(R.layout.blob_list_item_view, (ViewGroup) null);
        linearLayout.setEnabled(false);
        TextView textView = (TextView) linearLayout.findViewById(R.id.blob_label);
        TextView textView2 = (TextView) linearLayout.findViewById(R.id.blob_id);
        TextView textView3 = (TextView) linearLayout.findViewById(R.id.blob_expiry);
        TextView textView4 = (TextView) linearLayout.findViewById(R.id.blob_size);
        textView.setText(this.mBlobInfo.getLabel());
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView2.setText(
                getString(
                        R.string.blob_id_text,
                        new Object[] {Long.valueOf(this.mBlobInfo.getId())}));
        textView3.setVisibility(8);
        long sizeBytes = this.mBlobInfo.getSizeBytes();
        SimpleDateFormat simpleDateFormat = SharedDataUtils.FORMATTER;
        textView4.setText(String.format("%.2f MB", Double.valueOf(sizeBytes / 1048576.0d)));
        listView.addHeaderView(linearLayout);
        ListView listView2 = getListView();
        Button button = new Button(this);
        button.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        button.setText(R.string.delete_blob_text);
        button.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.development.storage.LeaseInfoListView$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        final LeaseInfoListView leaseInfoListView = LeaseInfoListView.this;
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(leaseInfoListView.mContext);
                        builder.setMessage(R.string.delete_blob_confirmation_text);
                        builder.setPositiveButton(
                                android.R.string.ok,
                                new DialogInterface
                                        .OnClickListener() { // from class:
                                                             // com.android.settings.development.storage.LeaseInfoListView$$ExternalSyntheticLambda1
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i) {
                                        LeaseInfoListView leaseInfoListView2 =
                                                LeaseInfoListView.this;
                                        int i2 = LeaseInfoListView.$r8$clinit;
                                        leaseInfoListView2.getClass();
                                        try {
                                            leaseInfoListView2.mBlobStoreManager.deleteBlob(
                                                    leaseInfoListView2.mBlobInfo);
                                            leaseInfoListView2.setResult(1);
                                        } catch (IOException e) {
                                            Log.e(
                                                    "LeaseInfoListView",
                                                    "Unable to delete blob: " + e.getMessage());
                                            leaseInfoListView2.setResult(-1);
                                        }
                                        leaseInfoListView2.finish();
                                    }
                                });
                        builder.setNegativeButton(
                                android.R.string.cancel, (DialogInterface.OnClickListener) null);
                        builder.create().show();
                    }
                });
        listView2.addFooterView(button);
        getListView().setClickable(false);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override // android.app.Activity
    public final boolean onNavigateUp() {
        finish();
        return true;
    }
}
