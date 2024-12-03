package com.example.login.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.login.R;
import com.example.login.RetrofitClinet;
import com.example.login.Ticket;
import com.example.login.TicketAdapter;
import com.example.login.TicketService;
import com.example.login.Tour;
import com.example.login.TourAdapter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InSideticketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InSideticketFragment extends Fragment {
    private ListView listView;
    private TicketAdapter ticketAdapter;
    private int pos =-1;
    private List<Ticket> tickets;
    private Tour touracb;
    Button btnthemticket ;

    private Button btnadd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in_sideticket,container,false);
        listView = view.findViewById(R.id.listViewTicket);

        Bundle bundle = getArguments();
        if (bundle !=null){
            Tour tour = (Tour) bundle.getSerializable("tickettour");
            touracb= (Tour) bundle.getSerializable("tickettour");
            if (tour != null) {
                Long tourId = tour.getTourId();
                loadticket(tourId);
            }
            else {Toast.makeText(getContext(), "ko có tour", Toast.LENGTH_SHORT).show();}
        }else {
            Toast.makeText(getContext(), "bundle ko có gì", Toast.LENGTH_SHORT).show();
        }




        return view;
    }

    private void loadticket(Long tourId) {
        TicketService  ticketService = RetrofitClinet.getRetrofitInstance().create(TicketService.class);

        Call<List<Ticket>> call = ticketService.getTicketbyTour(tourId);
        call.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                if (response.isSuccessful()){
                    tickets = response.body();
                    if (tickets != null && !tickets.isEmpty()) {

                        for (Ticket ticket : tickets) {
                            ticket.setTour(touracb); // tour đã được lấy từ bundle
                        }
                    }
                    ticketAdapter = new TicketAdapter(getContext(), tickets);
                    listView.setAdapter(ticketAdapter);
                    Toast.makeText(getContext(), "loadticket thanh cong", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "loadticket thất bại ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {

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

    public InSideticketFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InSideticketFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InSideticketFragment newInstance(String param1, String param2) {
        InSideticketFragment fragment = new InSideticketFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


}