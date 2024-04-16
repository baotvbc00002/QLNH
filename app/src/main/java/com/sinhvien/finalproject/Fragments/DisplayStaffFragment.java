package com.sinhvien.finalproject.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.sinhvien.finalproject.Activities.AddStaffActivity;
import com.sinhvien.finalproject.Activities.HomeActivity;
import com.sinhvien.finalproject.CustomAdapter.AdapterDisplayStaff;
import com.sinhvien.finalproject.DAO.StaffDAO;
import com.sinhvien.finalproject.DTO.StaffDTO;
import com.sinhvien.finalproject.R;

import java.util.List;

public class DisplayStaffFragment extends Fragment {

    GridView gvStaff;
    StaffDAO staffDAO;
    List<StaffDTO> staffDTOS;
    AdapterDisplayStaff adapterDisplayStaff;

    ActivityResultLauncher<Intent> resultLauncherAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        long check = intent.getLongExtra("check",0);
                        String function = intent.getStringExtra("function");
                        if(function.equals("addstaff"))
                        {
                            if(check != 0){
                                ShowStaffList();
                                Toast.makeText(getActivity(),"Add success",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Add failure",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if(check != 0){
                                ShowStaffList();
                                Toast.makeText(getActivity(),"Edit success",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Edit failure",Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.displaystaff_layout,container,false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Employee manager</font>"));
        setHasOptionsMenu(true);

        gvStaff = (GridView)view.findViewById(R.id.gvStaff) ;

        staffDAO = new StaffDAO(getActivity());
        ShowStaffList();

        registerForContextMenu(gvStaff);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = menuInfo.position;
        int staffid = staffDTOS.get(position).getSTAFFID();

        switch (id){
            case R.id.itEdit:
                Intent iEdit = new Intent(getActivity(),AddStaffActivity.class);
                iEdit.putExtra("staffid",staffid);
                resultLauncherAdd.launch(iEdit);
                break;

            case R.id.itDelete:
                boolean check = staffDAO.XoaNV(staffid);
                if(check){
                    ShowStaffList();
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_sucessful)
                            ,Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_failed)
                            ,Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddStaff = menu.add(1,R.id.itAddStaff,1,"Add Staff");
        itAddStaff.setIcon(R.drawable.ic_baseline_add_24);
        itAddStaff.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itAddStaff:
                Intent iRegister= new Intent(getActivity(), AddStaffActivity.class);
                resultLauncherAdd.launch(iRegister);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ShowStaffList(){
        staffDTOS = staffDAO.LayDSNV();
        adapterDisplayStaff = new AdapterDisplayStaff(getActivity(),R.layout.custom_layout_displaystaff,staffDTOS);
        gvStaff.setAdapter(adapterDisplayStaff);
        adapterDisplayStaff.notifyDataSetChanged();
    }
}
