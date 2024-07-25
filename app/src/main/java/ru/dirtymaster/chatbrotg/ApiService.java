package ru.dirtymaster.chatbrotg;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.dirtymaster.chatbrotg.dto.AuthRequest;

public interface ApiService {
    @POST("api/users/auth")
    Call<UserResponse> signUpOrIn(@Body AuthRequest request);
}