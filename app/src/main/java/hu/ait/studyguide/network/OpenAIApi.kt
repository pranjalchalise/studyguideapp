package hu.ait.studyguide.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAIApi {

    @POST("v1/chat/completions") // ✅ correct endpoint
    @Headers("Content-Type: application/json")
    suspend fun generateResponse(@Body request: OpenAIRequest): OpenAIResponse

    companion object {
        fun create(apiKey: String): OpenAIApi {
            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $apiKey")
                        .build()
                    chain.proceed(request)
                }
                .build()

            return Retrofit.Builder()
                .baseUrl("https://api.openai.com/") // ✅ MUST end with `/`
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenAIApi::class.java)
        }
    }
}
