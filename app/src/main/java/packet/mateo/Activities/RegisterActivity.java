package packet.mateo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import packet.mateo.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText email, contraseña, contraseña2;
    private Button cancelar, registrar;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        auth = FirebaseAuth.getInstance();


        email = findViewById(R.id.txtEmail_registrar);
        contraseña = findViewById(R.id.txtContraseña_registrar);
        contraseña2 = findViewById(R.id.txtContraseña_registrar2);
        cancelar = findViewById(R.id.boton_registrar_cancelar);
        registrar = findViewById(R.id.boton_registrar_ok);

        registrar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String eml = email.getText().toString();
                        String password = contraseña.getText().toString();
                        String password2 = contraseña2.getText().toString();

                        if (password.equals(password2) && checkEmpty(eml, password, password2)) {
                            register(eml, password);
                        }


                    }
                }

        );

        cancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();

                    }
                }
        );


    }

    private void register(String email, String password) {
        Task task = auth.createUserWithEmailAndPassword(email, password);
        task.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "No se ha podido registrar este usuario", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean checkEmpty(String eml, String password, String password2) {

        return !eml.isEmpty() && !password.isEmpty() && !password2.isEmpty();
    }
}
