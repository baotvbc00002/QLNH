package com.sinhvien.finalproject.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.ContextMenu;
import android.view.KeyEvent;
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

import com.sinhvien.finalproject.Activities.AddMenuActivity;
import com.sinhvien.finalproject.Activities.AmountMenuActivity;
import com.sinhvien.finalproject.Activities.HomeActivity;
import com.sinhvien.finalproject.CustomAdapter.AdapterDisplayMenu;
import com.sinhvien.finalproject.DAO.FoodDAO;
import com.sinhvien.finalproject.DTO.FoodDTO;
import com.sinhvien.finalproject.R;

import java.util.List;

public class DisplayMenuFragment extends Fragment {

    int typeid,tableid;
    String typename,status;
    GridView gvDisplayMenu;
    FoodDAO foodDAO;
    List<FoodDTO> foodDTOList;
    AdapterDisplayMenu adapterDisplayMenu;

    ActivityResultLauncher<Intent> resultLauncherMenu = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        boolean check = intent.getBooleanExtra("check ",false);
                        String function = intent.getStringExtra("function");
                        if(function.equals("addfood"))
                        {
                            if(check ){
                                ShowFoodList();
                                Toast.makeText(getActivity(),"Add success",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Add failure",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if(check ){
                                ShowFoodList();
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

        View view = inflater.inflate(R.layout.displaymenu_layout,container,false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Menu management</font>"));
        foodDAO = new FoodDAO(getActivity());

        gvDisplayMenu = (GridView)view.findViewById(R.id.gvDisplayMenu);

        Bundle bundle = getArguments();
        if(bundle !=null){
            typeid = bundle.getInt("typeid");
            typename = bundle.getString("typename");
            tableid = bundle.getInt("tableid");
            ShowFoodList();

            gvDisplayMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //nếu lấy đc mã bàn mới mở
                    status = foodDTOList.get(position).getStatus();
                    if(tableid != 0){
                        if(status.equals("true")){
                            Intent iAmount = new Intent(getActivity(), AmountMenuActivity.class);
                            iAmount.putExtra("tableid",tableid);
                            iAmount.putExtra("foodid",foodDTOList.get(position).getFoodid());
                            startActivity(iAmount);
                        }else {
                            Toast.makeText(getActivity(),"The food is sold out, can't add more", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
        setHasOptionsMenu(true);
        registerForContextMenu(gvDisplayMenu);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                     getParentFragmentManager().popBackStack("showtype", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });

        return view;
    }

    //tạo 1 menu context show lựa chọn
    @Override
    public void onCreateContextMenu(ContextMenu menu,View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    //Tạo phần sửa và xóa trong menu context
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = menuInfo.position;
        int foodid = foodDTOList.get(position).getFoodid();

        switch (id){
            case R.id.itEdit:
                Intent iEdit = new Intent(getActivity(), AddMenuActivity.class);
                iEdit.putExtra("foodid",foodid);
                iEdit.putExtra("typeid",typeid);
                iEdit.putExtra("typename",typename);
                resultLauncherMenu.launch(iEdit);
                break;

            case R.id.itDelete:
                boolean check = foodDAO.XoaMon(foodid);
                if(check){
                    ShowFoodList();
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
        MenuItem itAddMenu = menu.add(1,R.id.itAddMenu,1,R.string.addMenu);
        itAddMenu.setIcon(R.drawable.ic_baseline_add_24);
        itAddMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itAddMenu:
                Intent intent = new Intent(getActivity(), AddMenuActivity.class);
                intent.putExtra("typeid",typeid);
                intent.putExtra("typename",typename);
                resultLauncherMenu.launch(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void ShowFoodList(){
        foodDTOList = foodDAO.LayDSMonTheoLoai(typeid);
        adapterDisplayMenu = new AdapterDisplayMenu(getActivity(),R.layout.custom_layout_displaymenu,foodDTOList);
        gvDisplayMenu.setAdapter(adapterDisplayMenu);
        adapterDisplayMenu.notifyDataSetChanged();
    }

}
