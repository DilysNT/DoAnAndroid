package com.example.login.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.login.R;
import com.example.login.RetrofitClinet;
import com.example.login.Tour;
import com.example.login.TourAdapter;
import com.example.login.TourImage;
import com.example.login.TourService;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HouseFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
    public class HouseFragment extends Fragment {
        private ListView listView;
        private TourAdapter tourAdapter;
        private int pos =-1;
        private Long Tourupdate ;
       private List<Tour> tours;
        private Button btnadd;


    @Override
    public void onResume() {
        super.onResume();
        Button btnadd = getView().findViewById(R.id.btnthem);
        if (btnadd != null) {
            btnadd.setVisibility(View.VISIBLE); // Hiển thị button
        }
    }

    @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getParentFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, result) -> {
                Tour newTour = (Tour) result.getSerializable("tour");

                if (newTour != null) {
                    if (pos == -1){
                    tourAdapter.add(newTour);
                    uploadtour(newTour);}
                    else {

                        tourAdapter.remove(tourAdapter.getItem(pos));
                        tourAdapter.insert(newTour,pos);
                        updatetour(newTour);

                        pos = -1;
                    }
                }
            });
        }

    private void updatetour(Tour newTour) {
        Gson gson = new Gson();
        String tourJson = gson.toJson(newTour);
        RequestBody tourRequestBody = RequestBody.create(MediaType.parse("application/json"), tourJson);


        List<MultipartBody.Part> imageParts = new ArrayList<>();
        for (TourImage tourImage : newTour.getImages()) {

            byte[] imageBytes = Base64.decode(tourImage.getImageData(), Base64.DEFAULT);


            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);

            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("images", "image.jpg", imageRequestBody);
            imageParts.add(imagePart);
        }


        TourService tourService = RetrofitClinet.getRetrofitInstance().create(TourService.class);
        Call<Tour> call = tourService.updateTour(newTour.getTourId(),tourRequestBody,imageParts);
        call.enqueue(new Callback<Tour>() {
            @Override
            public void onResponse(Call<Tour> call, Response<Tour> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Cập nhật tour thành công", Toast.LENGTH_SHORT).show();
                } else {
                    // In thêm thông tin lỗi để phân tích
                    Log.e("UpdateTourError", "Code: " + response.code() + " Message: " + response.message());
                    Toast.makeText(getContext(), "Cập nhật tour không thành công: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Tour> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void uploadtour(Tour tour) {

        Gson gson = new Gson();
        String tourJson = gson.toJson(tour);
        RequestBody tourRequestBody = RequestBody.create(MediaType.parse("application/json"), tourJson);


        List<MultipartBody.Part> imageParts = new ArrayList<>();
        for (TourImage tourImage : tour.getImages()) {

            byte[] imageBytes = Base64.decode(tourImage.getImageData(), Base64.DEFAULT);


            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);

            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("images", "image.jpg", imageRequestBody);
            imageParts.add(imagePart);
        }

        Log.d("TourJson", tourJson);
        for (MultipartBody.Part part : imageParts) {
            Log.d("ImagePart", part.toString());
        }
        TourService tourService = RetrofitClinet.getRetrofitInstance().create(TourService.class);
        Call<Tour> call = tourService.createTour(tourRequestBody, imageParts);


        call.enqueue(new Callback<Tour>() {
            @Override
            public void onResponse(Call<Tour> call, Response<Tour> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "tao tour thanh cong", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("Error Response", errorBody); // Ghi log chi tiết lỗi
                        Toast.makeText(getContext(), "Tạo tour không thành công: " + errorBody, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Tour> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_house, container, false);
            listView = view.findViewById(R.id.listView);

            btnadd= view.findViewById(R.id.btnthem);
            btnadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnadd.setVisibility(View.GONE);
                    AddandchangetourFragment fragment = new AddandchangetourFragment();
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.house,fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                Tour selectedTour = (Tour) parent.getItemAtPosition(position);
                Long tourId = selectedTour.getTourId();
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Chọn hành động");
                builder.setItems(new CharSequence[]{"Sửa", "Xóa"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:

                                editTour(selectedTour);
                                break;

                            case 1:
                                deleteTour(tourId, position);
                                break;
                        }
                    }
                });
                builder.show();
                return true;
            }
        });

          loadTours();

            return view;
        }

    private void deleteTour(Long tourId, int position) {
        TourService tourService = RetrofitClinet.getRetrofitInstance().create(TourService.class);
        Call<Void> call = tourService.deleteTour(tourId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(getContext(), "Tour đã được xóa", Toast.LENGTH_SHORT).show();
                    // Cập nhật danh sách và notify adapter
                    tours.remove(position);
                    tourAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Xóa tour thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void editTour(Tour selectedTour) {
        AddandchangetourFragment fragment = new AddandchangetourFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("tourupdate", selectedTour);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.house,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HouseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HouseFragment newInstance(String param1, String param2) {
        HouseFragment fragment = new HouseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HouseFragment() {
        // Required empty public constructor
    }




}