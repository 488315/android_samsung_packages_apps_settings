package com.android.settings.development.storage;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.blob.BlobInfo;
import android.app.blob.BlobStoreManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.UserHandle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.internal.util.CollectionUtils;
import com.android.settings.R;

import java.io.IOException;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BlobInfoListView extends ListActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public BlobListAdapter mAdapter;
    public BlobStoreManager mBlobStoreManager;
    public BlobInfoListView mContext;
    public LayoutInflater mInflater;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BlobListAdapter extends ArrayAdapter {
        public BlobListAdapter(Context context) {
            super(context, 0);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            BlobInfoViewHolder blobInfoViewHolder;
            String string;
            LayoutInflater layoutInflater = BlobInfoListView.this.mInflater;
            if (view != null) {
                blobInfoViewHolder = (BlobInfoViewHolder) view.getTag();
            } else {
                View inflate =
                        layoutInflater.inflate(R.layout.blob_list_item_view, (ViewGroup) null);
                BlobInfoViewHolder blobInfoViewHolder2 = new BlobInfoViewHolder();
                blobInfoViewHolder2.rootView = inflate;
                blobInfoViewHolder2.blobLabel = (TextView) inflate.findViewById(R.id.blob_label);
                blobInfoViewHolder2.blobId = (TextView) inflate.findViewById(R.id.blob_id);
                blobInfoViewHolder2.blobExpiry = (TextView) inflate.findViewById(R.id.blob_expiry);
                blobInfoViewHolder2.blobSize = (TextView) inflate.findViewById(R.id.blob_size);
                inflate.setTag(blobInfoViewHolder2);
                blobInfoViewHolder = blobInfoViewHolder2;
            }
            View view2 = blobInfoViewHolder.rootView;
            BlobInfo blobInfo = (BlobInfo) getItem(i);
            blobInfoViewHolder.blobLabel.setText(blobInfo.getLabel());
            blobInfoViewHolder.blobId.setText(
                    BlobInfoListView.this.getString(
                            R.string.blob_id_text, new Object[] {Long.valueOf(blobInfo.getId())}));
            TextView textView = blobInfoViewHolder.blobExpiry;
            long expiryTimeMs = blobInfo.getExpiryTimeMs();
            if (expiryTimeMs == 0) {
                string = BlobInfoListView.this.getString(R.string.blob_never_expires_text);
            } else {
                BlobInfoListView blobInfoListView = BlobInfoListView.this;
                Calendar calendar = SharedDataUtils.CALENDAR;
                calendar.setTimeInMillis(expiryTimeMs);
                string =
                        blobInfoListView.getString(
                                R.string.blob_expires_text,
                                new Object[] {
                                    SharedDataUtils.FORMATTER.format(calendar.getTime())
                                });
            }
            textView.setText(string);
            TextView textView2 = blobInfoViewHolder.blobSize;
            long sizeBytes = blobInfo.getSizeBytes();
            SimpleDateFormat simpleDateFormat = SharedDataUtils.FORMATTER;
            textView2.setText(String.format("%.2f MB", Double.valueOf(sizeBytes / 1048576.0d)));
            return view2;
        }
    }

    @Override // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 8108 && i2 == -1) {
            Toast.makeText(this, R.string.shared_data_delete_failure_text, 1).show();
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = this;
        this.mBlobStoreManager = (BlobStoreManager) getSystemService(BlobStoreManager.class);
        this.mInflater = (LayoutInflater) getSystemService(LayoutInflater.class);
        BlobListAdapter blobListAdapter = new BlobListAdapter(this);
        this.mAdapter = blobListAdapter;
        setListAdapter(blobListAdapter);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override // android.app.ListActivity
    public final void onListItemClick(ListView listView, View view, int i, long j) {
        final BlobInfo blobInfo = (BlobInfo) this.mAdapter.getItem(i);
        if (!CollectionUtils.isEmpty(blobInfo.getLeases())) {
            Intent intent = new Intent(this, (Class<?>) LeaseInfoListView.class);
            intent.putExtra("BLOB_KEY", (Parcelable) blobInfo);
            startActivityForResult(intent, 8108);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
            builder.setMessage(R.string.shared_data_no_accessors_dialog_text);
            builder.setPositiveButton(
                    android.R.string.ok,
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.android.settings.development.storage.BlobInfoListView$$ExternalSyntheticLambda0
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i2) {
                            BlobInfoListView blobInfoListView = BlobInfoListView.this;
                            BlobInfo blobInfo2 = blobInfo;
                            int i3 = BlobInfoListView.$r8$clinit;
                            blobInfoListView.getClass();
                            try {
                                blobInfoListView.mBlobStoreManager.deleteBlob(blobInfo2);
                            } catch (IOException e) {
                                Log.e(
                                        "BlobInfoListView",
                                        "Unable to delete blob: " + e.getMessage());
                                Toast.makeText(
                                                blobInfoListView,
                                                R.string.shared_data_delete_failure_text,
                                                1)
                                        .show();
                            }
                            blobInfoListView.queryBlobsAndUpdateList();
                        }
                    });
            builder.setNegativeButton(
                    android.R.string.cancel, (DialogInterface.OnClickListener) null);
            builder.create().show();
        }
    }

    @Override // android.app.Activity
    public final boolean onNavigateUp() {
        finish();
        return true;
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        queryBlobsAndUpdateList();
    }

    public final void queryBlobsAndUpdateList() {
        try {
            BlobListAdapter blobListAdapter = this.mAdapter;
            List queryBlobsForUser = this.mBlobStoreManager.queryBlobsForUser(UserHandle.CURRENT);
            blobListAdapter.clear();
            if (queryBlobsForUser.isEmpty()) {
                BlobInfoListView.this.finish();
            } else {
                blobListAdapter.addAll(queryBlobsForUser);
            }
        } catch (IOException e) {
            Log.e("BlobInfoListView", "Unable to fetch blobs for current user: " + e.getMessage());
            Toast.makeText(this, R.string.shared_data_query_failure_text, 1).show();
            finish();
        }
    }
}
