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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sinhvien.finalproject.Activities.AddCategoryActivity;
import com.sinhvien.finalproject.Activities.HomeActivity;
import com.sinhvien.finalproject.CustomAdapter.AdapterDisplayCategory;
import com.sinhvien.finalproject.DAO.TypefoodDAO;
import com.sinhvien.finalproject.DTO.TypefoodDTO;
import com.sinhvien.finalproject.R;

import java.util.List;

public class DisplayCategoryFragment extends Fragment {

    GridView gvCategory;
    List<TypefoodDTO> typefoodDTOList;
    TypefoodDAO typefoodDAO;
    AdapterDisplayCategory adapter;
    FragmentManager fragmentManager;
    int tableid;

    ActivityResultLauncher<Intent> resultLauncherCategory = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        boolean check = intent.getBooleanExtra("check",false);
                        String function = intent.getStringExtra("function");
                        if(function.equals("addtype"))
                        {
                            if(check){
                                ShowTypeList();
                                    Toast.makeText(getActivity(),"Add success",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Add failure",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if(check){
                                ShowTypeList();
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

        View view = inflater.inflate(R.layout.displaycategory_layout,container,false);
        setHasOptionsMenu(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Menu management</font>"));

        gvCategory = (GridView)view.findViewById(R.id.gvCategory);

        fragmentManager = getActivity().getSupportFragmentManager();

        typefoodDAO = new TypefoodDAO(getActivity());
        ShowTypeList();

        Bundle bDataCategory = getArguments();
        if(bDataCategory != null){
            tableid = bDataCategory.getInt("tableid");
        }

        gvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int typeid = typefoodDTOList.get(position).getTypeid();
                String typename = typefoodDTOList.get(position).getTypename();
                DisplayMenuFragment displayMenuFragment = new DisplayMenuFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("typeid",typeid);
                bundle.putString("typename",typename);
                bundle.putInt("tableid",tableid);
                displayMenuFragment.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.contentView,displayMenuFragment).addToBackStack("showtype");
                transaction.commit();
            }
        });

        registerForContextMenu(gvCategory);

        return view;
    }

    //hiển thị contextmenu
    @Override
    public void onCreateContextMenu(ContextMenu menu,View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    //xử lí context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = menuInfo.position;
        int typeid = typefoodDTOList.get(position).getTypeid();

        switch (id){
            case R.id.itEdit:
                Intent iEdit = new Intent(getActivity(), AddCategoryActivity.class);
                iEdit.putExtra("typeid",typeid);
                resultLauncherCategory.launch(iEdit);
                break;

            case R.id.itDelete:
                boolean check = typefoodDAO.XoaLoaiMon(typeid);
                if(check){
                    ShowTypeList();
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

    //khởi tạo nút thêm loại
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddCategory = menu.add(1,R.id.itAddCategory,1,R.string.addCategory);
        itAddCategory.setIcon(R.drawable.ic_baseline_add_24);
        itAddCategory.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    //xử lý nút thêm loại
    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itAddCategory:
                Intent intent = new Intent(getActivity(), AddCategoryActivity.class);
                resultLauncherCategory.launch(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //hiển thị dữ liệu trên gridview
    private void ShowTypeList(){
        typefoodDTOList = typefoodDAO.LayDSLoaiMon();
        adapter = new AdapterDisplayCategory(getActivity(),R.layout.custom_layout_displaycategory,typefoodDTOList);
        gvCategory.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
