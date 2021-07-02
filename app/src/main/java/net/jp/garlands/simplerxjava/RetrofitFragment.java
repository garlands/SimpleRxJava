package net.jp.garlands.simplerxjava;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.jp.garlands.simplerxjava.databinding.FragmentRetrofitBinding;

import java.util.List;

import androidx.fragment.app.Fragment;
import lombok.SneakyThrows;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RetrofitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RetrofitFragment extends Fragment {

    private FragmentRetrofitBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RetrofitFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RetrofitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RetrofitFragment newInstance(String param1, String param2) {
        RetrofitFragment fragment = new RetrofitFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @SneakyThrows
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_retrofit, container, false);
        binding = FragmentRetrofitBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.button2.setOnClickListener(new View.OnClickListener() {

            @SneakyThrows
            @Override
            public void onClick(View v) {
                binding.textView3.setText("touch!");

                String userName = "eugenp";
//        List<String> topContributors = new net.jp.garlands.simplerxjava.GitHubBasicService().getTopContributors(userName);
                List<String> topContributors = new GitHubBasicService().getTopContributors(userName);
                if (topContributors != null) {
                    for (String contributor : topContributors) {
                        Log.d("TEST:", "list : " + contributor);
                    }
                }
                /*
                new GitHubRxService()
                        .getTopContributors(userName)
                        .doOnNext(result -> {
                            Log.d("TEST:", "onNext");
                        })
                        .doOnError(e -> {
                            Log.d("TEST:", "Error = " + e.getLocalizedMessage());
                        })
                        .doOnComplete(() -> {
                            Log.d("TEST:", "Completed");
                        })
                        .subscribe();
                */
            }
        });
        return view;
    }
}