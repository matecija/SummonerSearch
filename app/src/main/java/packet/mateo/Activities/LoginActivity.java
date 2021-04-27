package packet.mateo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

import packet.mateo.R;

public class LoginActivity extends AppCompatActivity implements Serializable {

    private ImageView img;
    private EditText txtEmail, contraseña;
    private Button btn_login;
    private TextView btn_crear_cuenta;

    private FirebaseAuth auth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_layout);

        img = findViewById(R.id.imagenlogin);
        Picasso.with(this.getApplicationContext()).load(R.drawable.lol_icon).into(img);

        // Login ----------


        txtEmail = findViewById(R.id.txtEmailLogin);
        contraseña = findViewById(R.id.txtContraseñaLogin);
        btn_login = findViewById(R.id.button_login);
        btn_crear_cuenta = findViewById(R.id.txtBotonCrearCuenta);

        auth = FirebaseAuth.getInstance() ;


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkEmpty(txtEmail.getText().toString(),contraseña.getText().toString())){
                    Task task = auth.signInWithEmailAndPassword(txtEmail.getText().toString(),contraseña.getText().toString());
                    task.addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("IsLogin", true);

                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "No se ha podido loguear este usuario", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });

    }

    public void crearCuenta(View view){
        startActivity(new Intent(this,RegisterActivity.class));
        finish();

    }

    private boolean checkEmpty(String eml, String password) {

        return !eml.isEmpty() && !password.isEmpty();
    }

    // Efectos bonitos de transicion de imagen
    public void irMain(View v) {

        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, img, "imageMain2");
        Intent in = new Intent(this, MainActivity.class);
        startActivity(in, activityOptionsCompat.toBundle());

    }

}
