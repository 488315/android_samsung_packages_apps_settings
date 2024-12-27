package com.android.settings.deviceinfo;

import android.content.Context;
import android.os.Bundle;
import android.os.storage.DiskInfo;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.core.InstrumentedFragment;
import com.android.settings.deviceinfo.storage.StorageUtils;
import com.android.settings.search.actionbar.SearchMenuController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrivateVolumeUnmount extends InstrumentedFragment {
    public final AnonymousClass1 mConfirmListener =
            new View
                    .OnClickListener() { // from class:
                                         // com.android.settings.deviceinfo.PrivateVolumeUnmount.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    new StorageUtils.MountTask(
                                    (Context) PrivateVolumeUnmount.this.getActivity(),
                                    PrivateVolumeUnmount.this.mVolume)
                            .execute(new Void[0]);
                    PrivateVolumeUnmount.this.getActivity().finish();
                }
            };
    public DiskInfo mDisk;
    public VolumeInfo mVolume;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1982;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        this.mLifecycle.addObserver(new SearchMenuController(this, 1982));
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        StorageManager storageManager =
                (StorageManager) getActivity().getSystemService(StorageManager.class);
        VolumeInfo findVolumeById =
                storageManager.findVolumeById(
                        getArguments().getString("android.os.storage.extra.VOLUME_ID"));
        this.mVolume = findVolumeById;
        this.mDisk = storageManager.findDiskById(findVolumeById.getDiskId());
        View inflate = layoutInflater.inflate(R.layout.storage_internal_unmount, viewGroup, false);
        TextView textView = (TextView) inflate.findViewById(R.id.body);
        Button button = (Button) inflate.findViewById(R.id.confirm);
        textView.setText(
                TextUtils.expandTemplate(
                        getText(R.string.storage_internal_unmount_details),
                        this.mDisk.getDescription()));
        button.setOnClickListener(this.mConfirmListener);
        return inflate;
    }
}
