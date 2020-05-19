package com.dev.luna.bungee.signin

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.dev.luna.bungee.api.ApiResponse
import com.dev.luna.bungee.api.BungeeApi
import com.dev.luna.bungee.api.request.SigninRequest
import com.dev.luna.bungee.api.response.SigninResponse
import com.dev.luna.bungee.common.Prefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.codephobia.ankomvvm.lifecycle.BaseViewModel

class SigninViewModel (app: Application) : BaseViewModel(app) {

    val email = MutableLiveData("")
    val password = MutableLiveData("")

    suspend fun signin() {
        val request = SigninRequest(email.value, password.value)

    }

    private fun isNotValidSignin(request: SigninRequest) =
            when {
                request.isNotValidEmail() -> {
                    toast("이메일 형식이 정확하지 않습니다.")
                    true
                }
                request.isNotValidPassword() -> {
                    toast("비밀번호는 8자 이상 20자 이하로 이력해주세요.")
                    true
                }
                else -> false
            }

    private suspend fun requestSignin(request: SigninRequest) =
            withContext(Dispatchers.IO) {
                BungeeApi.instance.signin(request)
            }

    private fun onSigninResponse(response: ApiResponse<SigninResponse>) {
        if(response.success && response.data != null) {

            Prefs.token = response.data.token
            Prefs.refreshToken = response.data.refreshToken
            Prefs.userName = response.data.userName
            Prefs.userId = response.data.userId

            toast("로그인되었습니다.")
            //todo 상품리스트 화면으로 이동
        }
        else {
            toast(response.message ?: "알 수 없는 오류가 발생했습니다.")
        }
    }



}