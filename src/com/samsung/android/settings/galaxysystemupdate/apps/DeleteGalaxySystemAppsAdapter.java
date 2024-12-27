package com.samsung.android.settings.galaxysystemupdate.apps;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.datetime.timezone.TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0;

import com.samsung.android.settings.galaxysystemupdate.apps.data.ApexPackageVo;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DeleteGalaxySystemAppsAdapter extends RecyclerView.Adapter {
    public Context context;
    public DeleteGalaxySystemAppsFragment$$ExternalSyntheticLambda0 listener;
    public ArrayList searchData;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public final CheckBox checkBox;
        public final TextView installState;
        public final TextView packageName;
        public final TextView version;

        public ViewHolder(View view) {
            super(view);
            this.packageName = (TextView) view.findViewById(R.id.package_name);
            this.version = (TextView) view.findViewById(R.id.version_code);
            this.installState = (TextView) view.findViewById(R.id.install_state);
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.selected_checkbox);
            this.checkBox = checkBox;
            checkBox.setOnCheckedChangeListener(
                    new CompoundButton
                            .OnCheckedChangeListener() { // from class:
                                                         // com.samsung.android.settings.galaxysystemupdate.apps.DeleteGalaxySystemAppsAdapter$ViewHolder$$ExternalSyntheticLambda0
                        @Override // android.widget.CompoundButton.OnCheckedChangeListener
                        public final void onCheckedChanged(
                                CompoundButton compoundButton, boolean z) {
                            DeleteGalaxySystemAppsAdapter.ViewHolder viewHolder =
                                    DeleteGalaxySystemAppsAdapter.ViewHolder.this;
                            int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                            if (bindingAdapterPosition != -1) {
                                DeleteGalaxySystemAppsAdapter deleteGalaxySystemAppsAdapter =
                                        DeleteGalaxySystemAppsAdapter.this;
                                ((ApexPackageVo)
                                                        deleteGalaxySystemAppsAdapter.searchData
                                                                .get(bindingAdapterPosition))
                                                .isSelected =
                                        z;
                                int i = 0;
                                if (z) {
                                    DeleteGalaxySystemAppsFragment deleteGalaxySystemAppsFragment =
                                            deleteGalaxySystemAppsAdapter.listener.f$0;
                                    if (deleteGalaxySystemAppsFragment.removeButtonLayout
                                                    .getVisibility()
                                            == 8) {
                                        deleteGalaxySystemAppsFragment.removeButtonLayout
                                                .setVisibility(0);
                                        return;
                                    }
                                    return;
                                }
                                Iterator it = deleteGalaxySystemAppsAdapter.searchData.iterator();
                                while (it.hasNext()) {
                                    if (((ApexPackageVo) it.next()).isSelected) {
                                        i++;
                                    }
                                }
                                if (i == 0) {
                                    deleteGalaxySystemAppsAdapter.listener.f$0.removeButtonLayout
                                            .setVisibility(8);
                                }
                            }
                        }
                    });
            view.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.galaxysystemupdate.apps.DeleteGalaxySystemAppsAdapter$ViewHolder$$ExternalSyntheticLambda1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            DeleteGalaxySystemAppsAdapter.ViewHolder viewHolder =
                                    DeleteGalaxySystemAppsAdapter.ViewHolder.this;
                            if (viewHolder.checkBox.isEnabled()) {
                                viewHolder.checkBox.setChecked(!r0.isChecked());
                            }
                        }
                    });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        ArrayList arrayList = this.searchData;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        ApexPackageVo apexPackageVo = (ApexPackageVo) this.searchData.get(i);
        viewHolder2.packageName.setText(apexPackageVo.packageName);
        viewHolder2.version.setText(Long.toString(apexPackageVo.version));
        if (((ApexPackageVo) this.searchData.get(i)).isFactory) {
            viewHolder2.installState.setVisibility(8);
        } else {
            viewHolder2.installState.setVisibility(0);
            if (apexPackageVo.lastModifiedDate != null) {
                TextView textView = viewHolder2.installState;
                StringBuilder sb = new StringBuilder();
                TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0.m(
                        this.context, R.string.galaxy_system_app_update_installed, sb, " (");
                sb.append(apexPackageVo.lastModifiedDate);
                sb.append(")");
                textView.setText(sb.toString());
            } else {
                viewHolder2.installState.setText(R.string.galaxy_system_app_update_installed);
            }
        }
        if (((ApexPackageVo) this.searchData.get(i)).isFactory) {
            viewHolder2.checkBox.setEnabled(false);
        } else {
            viewHolder2.checkBox.setEnabled(true);
            viewHolder2.checkBox.setChecked(((ApexPackageVo) this.searchData.get(i)).isSelected);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View m =
                MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                        viewGroup,
                        R.layout.sec_galaxy_system_apps_package_delete_item_row,
                        viewGroup,
                        false);
        this.context = viewGroup.getContext();
        return new ViewHolder(m);
    }
}
