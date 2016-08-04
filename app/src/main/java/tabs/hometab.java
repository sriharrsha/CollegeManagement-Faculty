package tabs;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.management.college.com.collegemanagement.R;
import app.management.college.com.collegemanagement.adapters.HomeMenuAdapter;
import app.management.college.com.collegemanagement.adapters.MenuGridAdapter;
import app.management.college.com.collegemanagement.model.MenuItem;


public class hometab extends Fragment {

    View currentFragment;
    RecyclerView recyclerView;
    public hometab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentFragment = inflater.inflate(R.layout.fragment_home, container, false);

        // setting the required menus
        recyclerView = (RecyclerView) currentFragment.findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        MenuGridAdapter mAdapter = new MenuGridAdapter(getActivity(), getMenuData(getActivity()));
        recyclerView.setAdapter(mAdapter);

        return currentFragment;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



    public static List<MenuItem> getMenuData(Context ctx)  {
        List<MenuItem> items = new ArrayList<>();
        String s_name[] = ctx.getResources().getStringArray(R.array.menu_name);
        String s_date[] = ctx.getResources().getStringArray(R.array.groups_date);
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.menu_photos);

        items.add(new MenuItem(0, s_date[0], s_name[0], "", drw_arr.getResourceId(0, -1)));
        items.add(new MenuItem(1, s_date[1], s_name[1], "", drw_arr.getResourceId(1, -1)));
        items.add(new MenuItem(2, s_date[2], s_name[2], "", drw_arr.getResourceId(2, -1)));
        items.add(new MenuItem(3, s_date[3], s_name[3], "", drw_arr.getResourceId(3, -1)));
        items.add(new MenuItem(3, s_date[3], s_name[4], "", drw_arr.getResourceId(4, -1)));
        items.add(new MenuItem(3, s_date[3], s_name[5], "", drw_arr.getResourceId(5, -1)));
        items.add(new MenuItem(3, s_date[3], s_name[6], "", drw_arr.getResourceId(6, -1)));
        items.add(new MenuItem(3, s_date[3], s_name[7], "", drw_arr.getResourceId(7, -1)));

        return items;
    }

    public void clicked(String clickOn){
        Log.d("yyy", "clicked: " + clickOn);
    }
}
