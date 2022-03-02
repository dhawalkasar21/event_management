package com.example.eventmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EventList extends AppCompatActivity {

    Button dateButton;
    Button submit;
    TextInputEditText venue;
    EventsAdapter adapter;
    final Calendar myCalendar= Calendar.getInstance();
    RecyclerView event;

    @Override
    protected void onStart() {
        super.onStart();
        eventsList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        dateButton = findViewById(R.id.date1);
        submit = findViewById(R.id.submit1);
        venue = findViewById(R.id.venue);
        event = findViewById(R.id.allevents);

        openDatePicker();
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                submitForm(dateButton.getText().toString(),venue.getText().toString());
            }
        });
    }

    void openDatePicker(){
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH,month);
                        myCalendar.set(Calendar.DAY_OF_MONTH,day);
                        updateLabel();

                    }
                };
                new DatePickerDialog(EventList.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        dateButton.setText(dateFormat.format(myCalendar.getTime()));
    }

    void submitForm(String date,String venue){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> event = new HashMap<>();
        event.put("date",date);
        event.put("venue", venue);
        db.collection("users")
                .add(event)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("", "Error adding document", e);
                    }
                });
    }

    void eventsList(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            adapter = new EventsAdapter(task.getResult(), getApplication());
                            event.setAdapter(adapter);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("", document.getId() + " => " + document.getData());

                            }
                        } else {
                            Log.w("", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}