package com.example.ahmet.teknasyon.UI.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.ahmet.teknasyon.Adapter.BookShelfAdapter;
import com.example.ahmet.teknasyon.Model.BookShelfModel;
import com.example.ahmet.teknasyon.MyLib.RecyclerTouchListener;
import com.example.ahmet.teknasyon.R;
import com.example.ahmet.teknasyon.Retrofit.Interface.RetInterface;
import com.example.ahmet.teknasyon.Retrofit.Util.RetUtil;
import com.example.ahmet.teknasyon.UI.Activity.CategoryDetails;
import com.example.ahmet.teknasyon.Util.Static;
import java.util.List;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class BookshelfFragment extends Fragment {
    private RecyclerView recyclerView;
    private BookShelfAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf, container, false);
        DataLoad(view);
        return view;
    }

    private void RecyclerLoad(View view,List<BookShelfModel> list){
        adapter =new BookShelfAdapter(getActivity(),list);
        recyclerView = view.findViewById(R.id.BookshelfRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, (view1, position) -> {
            Intent ıntent = new Intent(getActivity(), CategoryDetails.class);
            ıntent.putExtra("ID",adapter.getItem(position).getBookShelfID());
            getActivity().startActivity(ıntent);
        }));
    }
    private void DataLoad(final View view){
        SweetAlertDialog sweetAlertDialog = Static.Loading(getActivity());
        sweetAlertDialog.show();
        RetUtil.CallEnque(RetUtil.createServis(RetInterface.class).Call_BookShelf());
        RetUtil.setRetUtilListener((RetUtil.RetUtilListener<List< BookShelfModel>>) (response,Status) ->{
            if(Status ==1){
                RecyclerLoad(view,response.body());
            }else{
                Toast.makeText(getActivity(),"Bağlanıtızda Sorun Var !!!",Toast.LENGTH_SHORT).show();
            }
            sweetAlertDialog.cancel();
        });

    }


}
