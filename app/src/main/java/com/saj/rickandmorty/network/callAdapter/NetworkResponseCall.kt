package com.saj.rickandmorty.network.callAdapter

import com.saj.rickandmorty.network.responses.NetworkResponse
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response

class NetworkResponseCall<S: Any>(
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, String>
) : Call<NetworkResponse<S>> {

    companion object {
        const val ERROR_MSG = "Unknown Error"
    }

    override fun enqueue(callback: Callback<NetworkResponse<S>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()

                if (response.isSuccessful && body != null) {
                    callback.onResponse(
                        this@NetworkResponseCall,
                        Response.success(NetworkResponse.Success(body))
                    )
                } else {
                    callback.onResponse(
                        this@NetworkResponseCall,
                        Response.success(NetworkResponse.Error(ERROR_MSG))
                    )
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val networkResponse = NetworkResponse.Error(ERROR_MSG)
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = NetworkResponseCall(delegate.clone(), errorConverter)

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<NetworkResponse<S>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}