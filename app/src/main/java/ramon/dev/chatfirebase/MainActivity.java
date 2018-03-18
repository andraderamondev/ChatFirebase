package ramon.dev.chatfirebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ChildEventListener {
    private RecyclerView rvConversa;
    private EditText edMsg;
    private Button btnEnviar;

    DatabaseReference msgReference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edMsg = findViewById(R.id.edMsg);
        btnEnviar = findViewById(R.id.btnEnviar);

        edMsg.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(edMsg.getText().toString().trim().length()>0) {
                    btnEnviar.setEnabled(true);
                }else{
                    btnEnviar.setEnabled(false);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edMsg.getText().toString().trim().length() != 0) {
                    Mensagem msg = new Mensagem(rvConversa.getAdapter().getItemCount(), edMsg.getText().toString());

                    msgReference.child("mensagens").child(msg.getId()).setValue(msg);
                    edMsg.setText(null);
                }
            }
        });


        rvConversa = findViewById(R.id.rvConversa);
        rvConversa.setHasFixedSize(true);
        rvConversa.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        rvConversa.setAdapter(new MessageRvAdapter());

        msgReference.child("mensagens").addChildEventListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recuperarMensagens();
    }

    private void recuperarMensagens() {
        Query query = msgReference.getRoot().child("mensagens").orderByChild("id");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Mensagem> lista = new ArrayList<>();
                for(DataSnapshot data: dataSnapshot.getChildren()) {
                    Mensagem msg = data.getValue(Mensagem.class);

                    lista.add(msg);
                }

                ((MessageRvAdapter) rvConversa.getAdapter()).atualizarLista(lista);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        ((MessageRvAdapter) rvConversa.getAdapter()).addMsg(dataSnapshot.getValue(Mensagem.class));
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
