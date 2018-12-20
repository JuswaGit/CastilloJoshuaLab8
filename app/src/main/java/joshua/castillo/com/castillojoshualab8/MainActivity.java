package joshua.castillo.com.castillojoshualab8;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    DatabaseReference db;

    EditText editName, editAge, editGender;
    TextView textName, textAge, textGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseDatabase.getInstance().getReference("student");

        editName = findViewById(R.id.FullName);
        editAge = findViewById(R.id.Age);
        editGender = findViewById(R.id.Gender);

        textName = findViewById(R.id.resfullname);
        textAge = findViewById(R.id.resage);
        textGender = findViewById(R.id.resgender);

    }

    public void search(View v) {
        final String name = beautifyTextField(editName).toLowerCase();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ss : dataSnapshot.getChildren()) {
                    Person p = ss.getValue(Person.class);
                    String person_name = p.getName().toLowerCase();

                    if(!person_name.equals(name))
                        continue;

                    else {
                        textName.setText(p.getName());
                        textAge.setText(p.getAge());
                        textGender.setText(p.getGender());
                        Toast("Record found!");
                        return;
                    }
                }

                textName.setText("");
                textAge.setText("");
                textGender.setText("");
                Toast("Record not found!");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };

        db.addValueEventListener(listener);
    }

    public void save(View v) {
        String name, age, gender, key;

        name = beautifyTextField(editName);
        age = beautifyTextField(editAge);
        gender = beautifyTextField(editGender);

        key = db.push().getKey();

        db.child(key).setValue(new Person(name, age, gender));

        Toast("Record added!");
    }

    protected String beautifyTextField(EditText et) {
        return et.getText().toString().trim();
    }
    protected void Toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
