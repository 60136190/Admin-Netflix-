package com.example.adminnetflix.fragments;
import android.content.Intent;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminnetflix.R;
//import com.example.adminnetflix.activities.ListCategoriesActivity;
import com.example.adminnetflix.activities.ListDetailActivity;
import com.example.adminnetflix.adapters.ListImageUserAdapter;
import com.example.adminnetflix.adapters.ListManagerAdapter;
import com.example.adminnetflix.api.ApiClient;
import com.example.adminnetflix.models.ItemManagerModel;
import com.example.adminnetflix.models.response.ListUserResponse;
import com.example.adminnetflix.utils.Contants;
import com.example.adminnetflix.utils.StoreUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment {

    private TextView tvRating;
    private TextView tvFeedback;
    private TextView tvModeOfpayment;
    private TextView tvCategories;

    private ConstraintLayout ctListUser;
    private ConstraintLayout ctListAdmin;
    private ConstraintLayout ctListDirector;
    private RecyclerView rcvListUser;
    private RecyclerView rcvManager;
    ListManagerAdapter listManagerAdapter;
    List<ItemManagerModel> itemManagerModel;
    private ListImageUserAdapter listImageUserAdapter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_user, container, false);
        initUi();
        getListUser();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvListUser.setLayoutManager(linearLayoutManager);

        setDataRcvManager();

        ctListUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itOpenListUser = new Intent(getContext(), ListDetailActivity.class);
                itOpenListUser.putExtra("list","List User");
                startActivity(itOpenListUser);
            }
        });

        ctListAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itOpenListAdmin = new Intent(getContext(), ListDetailActivity.class);
                itOpenListAdmin.putExtra("list","List Admin");
                startActivity(itOpenListAdmin);
            }
        });

        tvRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListDetailActivity.class);
                intent.putExtra("list","List Rating");
                startActivity(intent);
            }
        });

        tvFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListDetailActivity.class);
                intent.putExtra("list","List Feedback");
                startActivity(intent);
            }
        });

        tvModeOfpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListDetailActivity.class);
                intent.putExtra("list","List Mode of Payment");
                startActivity(intent);
            }
        });

        ctListDirector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListDetailActivity.class);
                intent.putExtra("list","List Director");
                startActivity(intent);
            }
        });

        tvCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListDetailActivity.class);
                intent.putExtra("list","List Category");
                startActivity(intent);
            }
        });

        return view;
    }

    private void initUi() {
        ctListUser = view.findViewById(R.id.ct_user);
        ctListAdmin = view.findViewById(R.id.ct_admin);
        ctListDirector = view.findViewById(R.id.ct_director);
        rcvListUser = view.findViewById(R.id.rcv_img_user);
        tvRating = view.findViewById(R.id.tv_rating);
        tvFeedback = view.findViewById(R.id.tv_feedback);
        tvModeOfpayment = view.findViewById(R.id.tv_mode_of_payment);
        tvCategories = view.findViewById(R.id.tv_category);
        rcvManager = view.findViewById(R.id.rcv_manager);
    }
    private void getListUser() {
        Call<ListUserResponse> listFavoriteFilmResponseCall = ApiClient.getUserService().getListUser(
                StoreUtil.get(getContext(), Contants.accessToken));
        listFavoriteFilmResponseCall.enqueue(new Callback<ListUserResponse>() {
            @Override
            public void onResponse(Call<ListUserResponse> call, Response<ListUserResponse> response) {
                listImageUserAdapter = new ListImageUserAdapter(getContext(),response.body().getData());
                rcvListUser.setAdapter(listImageUserAdapter);
                listImageUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListUserResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Maybe is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setDataRcvManager(){
        itemManagerModel = new ArrayList<>();
        itemManagerModel.add(new ItemManagerModel("User","12",R.drawable.backgroundslider,R.color.dark_green));
        itemManagerModel.add(new ItemManagerModel("Admin","12",R.drawable.backgroundslider,R.color.yellow));
        itemManagerModel.add(new ItemManagerModel("Director","12",R.drawable.backgroundslider,R.color.black));
        itemManagerModel.add(new ItemManagerModel("Rating","12",R.drawable.backgroundslider,R.color.blue));
        itemManagerModel.add(new ItemManagerModel("Feedback","12",R.drawable.backgroundslider,R.color.dark_pink));
        itemManagerModel.add(new ItemManagerModel("Mode of payment","12",R.drawable.backgroundslider,R.color.purple_500));
        itemManagerModel.add(new ItemManagerModel("Category","12",R.drawable.backgroundslider,R.color.gray));
        listManagerAdapter = new ListManagerAdapter(getContext(),itemManagerModel);
        rcvManager.setAdapter(listManagerAdapter);
        rcvManager.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
    }
}