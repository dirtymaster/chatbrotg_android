package ru.dirtymaster.chatbrotg;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.dirtymaster.chatbrotg.adapter.MessageAdapter;
import ru.dirtymaster.chatbrotg.dto.AuthRequest;
import ru.dirtymaster.chatbrotg.dto.Message;
import ru.dirtymaster.chatbrotg.dto.NewMessageRequest;
import ru.dirtymaster.chatbrotg.dto.UserResponse;
import ru.dirtymaster.chatbrotg.service.ApiService;

public class MainActivity extends AppCompatActivity {
    private EditText editTextName, editTextEmail, editTextPassword;
    private Button signUpOrInButton;
    private Button showAllMessagesButton;
    private RecyclerView messagesView;
    private MessageAdapter messageAdapter;
    private EditText newMessageEditText;
    private Button sendMessageButton;
    private UUID authorId;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Настройка Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        signUpOrInButton = findViewById(R.id.btn_register);
        showAllMessagesButton = findViewById(R.id.btn_showAllMessages);
        newMessageEditText = findViewById(R.id.et_newMessage);
        sendMessageButton = findViewById(R.id.btn_sendMessage);

        messagesView = findViewById(R.id.rw_messages);
        messagesView.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new MessageAdapter();
        messagesView.setAdapter(messageAdapter);

        signUpOrInButton.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            signUpOrIn(name, email, password);
        });

        showAllMessagesButton.setOnClickListener(v -> {
            showAllMessages();
        });

        sendMessageButton.setOnClickListener(v -> {
            sendNewMessage();
        });
    }

    private void signUpOrIn(String name, String email, String password) {
        AuthRequest request = new AuthRequest(name, email, password);

        Call<UserResponse> call = apiService.signUpOrIn(request);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    authorId = response.body().getId();
                    Toast.makeText(MainActivity.this, "Вы вошли или зарегистрировались", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Ошибка аутентификации", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // Обработка ошибки сети
                Toast.makeText(MainActivity.this, "Ошибка сети", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAllMessages() {
        Call<List<Message>> call = apiService.getAllMessages();
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    List<Message> messages = response.body();
                    messageAdapter.setMessages(messages);
                    messageAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Сообщения показаны", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Ошибка показа", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ошибка сети", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendNewMessage() {
        NewMessageRequest request = new NewMessageRequest(authorId, newMessageEditText.getText().toString());
        Call<Void> call = apiService.sendMessage(request);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Сообщение отправлено", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Ошибка отправки", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ошибка сети", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
