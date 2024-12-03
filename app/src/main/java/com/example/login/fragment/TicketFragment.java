package com.example.login.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.login.R;
import com.example.login.RetrofitClinet;
import com.example.login.Tour;
import com.example.login.TourAdapter;
import com.example.login.TourService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TicketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TicketFragment extends Fragment {
    private ListView listView;
    private TourAdapter tourAdapter;
    private List<Tour> tours;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        listView = view.findViewById(R.id.listViewtickettour);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Tour selectedTour = (Tour) parent.getItemAtPosition(position);
                Long tourId = selectedTour.getTourId();

                InSideticketFragment newFragment = new InSideticketFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("tickettour", selectedTour); // Đảm bảo sử dụng cùng khóa
                newFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.ticket, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                return true;
            }
        });

        loadTours();

        return view;
    }

    private void loadTours() {
        TourService tourApi = RetrofitClinet.getRetrofitInstance().create(TourService.class);
        Call<List<Tour>> call = tourApi.getAllTours();
        call.enqueue(new Callback<List<Tour>>() {
            @Override
            public void onResponse(Call<List<Tour>> call, Response<List<Tour>> response) {
                if (response.isSuccessful()) {
                    tours = response.body(); // Lưu trữ danh sách tours
                    tourAdapter = new TourAdapter(getContext(), tours);
                    listView.setAdapter(tourAdapter);
                } else {
                    Log.e("API Error", "co loi: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Tour>> call, Throwable t) {
                Log.e("APi bi loi", t.getMessage());
            }
        });
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TicketFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TicketFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TicketFragment newInstance(String param1, String param2) {
        TicketFragment fragment = new TicketFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


}