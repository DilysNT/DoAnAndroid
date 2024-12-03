package com.example.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TicketAdapter extends ArrayAdapter<Ticket> {
    private Context context;
    private List<Ticket> tickets;


    public TicketAdapter(@NonNull Context context, List<Ticket> tickets) {
        super(context, 0, tickets);
        this.context = context;
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_ticket_tour, parent, false);
        }

        Ticket ticket = getItem(position);
        TextView txtTourName = convertView.findViewById(R.id.txttournameticket);
        TextView txtLocation = convertView.findViewById(R.id.txtlocationticket);
        TextView txtDayStart = convertView.findViewById(R.id.txtdaystarticket);
        TextView txtSeatNumber = convertView.findViewById(R.id.txt_Soghe);
        TextView txtCount = convertView.findViewById(R.id.txtcount);
        TextView txtPrice = convertView.findViewById(R.id.txtpriceticekt);

        if (ticket != null) {
            Tour tour = ticket.getTour(); // Lấy đối tượng Tour từ Ticket

            if (tour != null) {
                txtTourName.setText(tour.getTourName() != null ? tour.getTourName() : "Tên tour không có");
                txtLocation.setText(tour.getDestination() != null ? tour.getDestination() : "Địa điểm không có");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                txtDayStart.setText(tour.getStartDate() != null ? dateFormat.format(tour.getStartDate()) : "Ngày khởi hành không có");
            } else {
                txtTourName.setText("Thông tin tour không có");
                txtLocation.setText("Thông tin địa điểm không có");
                txtDayStart.setText("Thông tin ngày không có");
            }

            txtSeatNumber.setText(ticket.getSeatNumber() != null ? ticket.getSeatNumber() : "Ghế không có");
            txtCount.setText("tên người đặt :" + ticket.getPassengerName());
            txtPrice.setText(ticket.getTour().getPrice() != null ? ticket.getPrice().toString() : "Giá không có");
        }

        return convertView;
    }


}
