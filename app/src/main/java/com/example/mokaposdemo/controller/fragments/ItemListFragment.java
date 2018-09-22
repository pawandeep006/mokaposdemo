package com.example.mokaposdemo.controller.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mokaposdemo.R;
import com.example.mokaposdemo.adapter.ArticleAdapter;
import com.example.mokaposdemo.database.OrmHelper;
import com.example.mokaposdemo.database.tables.ArticleTable;
import com.example.mokaposdemo.models.Article;
import com.example.mokaposdemo.network.RestClient;
import com.example.mokaposdemo.utils.AddToCartDialog;
import com.example.mokaposdemo.utils.RecyclerItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mokaposdemo.utils.Utils.toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemListFragment extends BaseFragment {


    private RecyclerView list;

    public ItemListFragment() {
        // Required empty public constructor
    }

    public static ItemListFragment get() {
        return new ItemListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_list, container, false);
    }

    @Override
    protected boolean isBackEnabled() {
        return true;
    }

    @Override
    protected int getScreenTitle() {
        return R.string.all_items;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 500);
    }

    private void getData() {

        List<ArticleTable> articles = OrmHelper.get().getAllItems();
        if (!articles.isEmpty()) {
            setupList(articles);
            return;
        }

        showLoader();
        RestClient.with().getClient().getItems().enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if (getActivity() == null)
                    return;

                if (response.code() != 200) {
                    toast("Something went wrong. Please try again later!");
                    return;
                }
                if (response.body() == null) {
                    toast("Something went wrong. Please try again later!");
                    return;
                }

                hideLoader();
                OrmHelper.get().saveArticle(response.body());
                setupList(OrmHelper.get().getAllItems());
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                hideLoader();
                toast("Something went wrong. Please try again later!");
            }
        });
    }

    private void setupList(List<ArticleTable> articles) {
        list.setLayoutManager(new GridLayoutManager(getContext().getApplicationContext(), 3));
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(new ArticleAdapter(articles));
        list.setHasFixedSize(true);
        list.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(),
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                showPopup(((ArticleAdapter) list.getAdapter()).getItem(position));
                            }
                        })
        );
    }

    private void showPopup(ArticleTable item) {
        if (item == null) return;

        new AddToCartDialog(getContext(), item).show();
    }

    private void initView(View view) {
        list = view.findViewById(R.id.list);
    }
}
