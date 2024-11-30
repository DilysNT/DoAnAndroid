package com.example.login.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.R;
import com.example.login.Tour;
import com.example.login.TourImage;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddandchangetourFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddandchangetourFragment extends Fragment {
    private static final int REQUEST_CODE_SELECT_IMAGE = 1001;
    private EditText editTourName, editDiemDen, editPrice, editStartDate, editEndDate, editDescription;
    private Button btnLuu,btnchonanh, btnThoat;
    private ArrayList<Uri> selectImages = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addandchangetour,container,false);
        editTourName = view.findViewById(R.id.edit_tourname);
        editDiemDen = view.findViewById(R.id.editdiemden);
        editPrice = view.findViewById(R.id.editprice);
        editStartDate = view.findViewById(R.id.editstartdata);
        editEndDate = view.findViewById(R.id.editend);
        editDescription = view.findViewById(R.id.edit_description);
        btnLuu = view.findViewById(R.id.btnluu);
        btnchonanh = view.findViewById(R.id.btnselectimage);
        btnThoat = view.findViewById(R.id.btnthoat);

        editStartDate.setOnClickListener(v -> showDatePickerDialog(editStartDate));

        // Gắn sự kiện chọn ngày cho editEndDate
        editEndDate.setOnClickListener(v -> showDatePickerDialog(editEndDate));

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });


        btnchonanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(intent,REQUEST_CODE_SELECT_IMAGE);
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Lấy dữ liệu từ các EditText
                    String tourName = editTourName.getText().toString();
                    String destination = editDiemDen.getText().toString();
                    BigDecimal price = new BigDecimal(editPrice.getText().toString());
                    Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(editStartDate.getText().toString());
                    Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(editEndDate.getText().toString());
                    String description = editDescription.getText().toString();


                    List<TourImage> images = new ArrayList<>();
                    for (Uri uri : selectImages) {

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                        byte[] imageBytes = bos.toByteArray();
                        String base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                        images.add(new TourImage(base64Image));
                    }


                    Tour tour = new Tour(null, tourName, destination, price, startDate, endDate, description, images);


                    Bundle bundle = new Bundle();
                    bundle.putSerializable("tour", tour);

                    getParentFragmentManager().setFragmentResult("requestKey", bundle);


                    getParentFragmentManager().popBackStack();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Dữ liệu không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;

    }

    private void showDatePickerDialog(EditText editStartDate) {
        Calendar calendar = Calendar.getInstance();
        int year=calendar.get(calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year1, month1, dayOfMonth) -> {
                    // Định dạng ngày tháng thành chuỗi
                    String selectedDate = year1 + "-" + (month1 + 1) + "-" + dayOfMonth;
                    editStartDate.setText(selectedDate);
                },
                year, month, day);

        datePickerDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == Activity.RESULT_OK && data != null){
            int count = data.getClipData().getItemCount();
            for (int i =0;i<count;i++){
                Uri selectimage = data.getClipData().getItemAt(i).getUri();
                if (selectimage !=null){
                    selectImages.add(selectimage);
                }
            }
            Toast.makeText(getContext(), "Chọn " + count + " ảnh thành công", Toast.LENGTH_SHORT).show();
        }else if (data.getData() != null){
            Uri selectimage = data.getData();
            selectImages.add(selectimage);
            Toast.makeText(getContext(), "Chọn ảnh thành công", Toast.LENGTH_SHORT).show();

        }
    }


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddandchangetourFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddandchangetourFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddandchangetourFragment newInstance(String param1, String param2) {
        AddandchangetourFragment fragment = new AddandchangetourFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


}