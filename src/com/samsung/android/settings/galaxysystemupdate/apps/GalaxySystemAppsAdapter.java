package com.samsung.android.settings.galaxysystemupdate.apps;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.datetime.timezone.TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0;

import com.samsung.android.settings.galaxysystemupdate.apps.data.ApexPackageVo;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class GalaxySystemAppsAdapter extends RecyclerView.Adapter {
    public Context context;
    public GalaxySystemAppsFragment$$ExternalSyntheticLambda0 listener;
    public ArrayList searchData;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView installState;
        public final TextView packageName;
        public final TextView version;

        public ViewHolder(View view) {
            super(view);
            this.packageName = (TextView) view.findViewById(R.id.package_name);
            this.version = (TextView) view.findViewById(R.id.version_code);
            this.installState = (TextView) view.findViewById(R.id.install_state);
            view.setOnLongClickListener(
                    new View
                            .OnLongClickListener() { // from class:
                                                     // com.samsung.android.settings.galaxysystemupdate.apps.GalaxySystemAppsAdapter$ViewHolder$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnLongClickListener
                        public final boolean onLongClick(View view2) {
                            int bindingAdapterPosition;
                            GalaxySystemAppsAdapter.ViewHolder viewHolder =
                                    GalaxySystemAppsAdapter.ViewHolder.this;
                            GalaxySystemAppsAdapter galaxySystemAppsAdapter =
                                    GalaxySystemAppsAdapter.this;
                            if (galaxySystemAppsAdapter.listener == null
                                    || (bindingAdapterPosition =
                                                    viewHolder.getBindingAdapterPosition())
                                            == -1
                                    || ((ApexPackageVo)
                                                    galaxySystemAppsAdapter.searchData.get(
                                                            bindingAdapterPosition))
                                            .isFactory
                                    || ((ApexPackageVo)
                                                    galaxySystemAppsAdapter.searchData.get(
                                                            bindingAdapterPosition))
                                            .isStaged) {
                                return false;
                            }
                            GalaxySystemAppsFragment$$ExternalSyntheticLambda0
                                    galaxySystemAppsFragment$$ExternalSyntheticLambda0 =
                                            galaxySystemAppsAdapter.listener;
                            ArrayList arrayList =
                                    galaxySystemAppsFragment$$ExternalSyntheticLambda0.f$1;
                            GalaxySystemAppsFragment galaxySystemAppsFragment =
                                    galaxySystemAppsFragment$$ExternalSyntheticLambda0.f$0;
                            galaxySystemAppsFragment.getClass();
                            galaxySystemAppsFragment.showDeleteActivity(
                                    ((ApexPackageVo) arrayList.get(bindingAdapterPosition))
                                            .packageName);
                            return true;
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
            return;
        }
        viewHolder2.installState.setVisibility(0);
        if (apexPackageVo.lastModifiedDate == null) {
            viewHolder2.installState.setText(R.string.galaxy_system_app_update_installed);
            return;
        }
        TextView textView = viewHolder2.installState;
        StringBuilder sb = new StringBuilder();
        TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0.m(
                this.context, R.string.galaxy_system_app_update_installed, sb, " (");
        sb.append(apexPackageVo.lastModifiedDate);
        sb.append(")");
        textView.setText(sb.toString());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View m =
                MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                        viewGroup,
                        R.layout.sec_galaxy_system_apps_package_item_row,
                        viewGroup,
                        false);
        this.context = viewGroup.getContext();
        return new ViewHolder(m);
    }
}
