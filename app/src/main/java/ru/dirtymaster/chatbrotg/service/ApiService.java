package ru.dirtymaster.chatbrotg.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import ru.dirtymaster.chatbrotg.dto.Message;
import ru.dirtymaster.chatbrotg.dto.NewMessageRequest;
import ru.dirtymaster.chatbrotg.dto.UserResponse;
import ru.dirtymaster.chatbrotg.dto.AuthRequest;

public interface ApiService {
    @POST("api/users/auth")
    Call<UserResponse> signUpOrIn(@Body AuthRequest request);

    @GET("api/messages")
    Call<List<Message>> getAllMessages();

    @POST("api/messages/send")
    Call<Void> sendMessage(@Body NewMessageRequest request);
}