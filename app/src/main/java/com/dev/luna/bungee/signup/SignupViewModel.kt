package com.dev.luna.bungee.signup

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.dev.luna.bungee.api.ApiResponse
import com.dev.luna.bungee.api.BungeeApi
import com.dev.luna.bungee.api.request.SignupRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.codephobia.ankomvvm.lifecycle.BaseViewModel
import org.jetbrains.anko.error
import java.lang.Exception

class SignupViewModel(app: Application) : BaseViewModel(app) {
    val email = MutableLiveData("")
    val name = MutableLiveData("")
    val password = MutableLiveData("")

    suspend fun signup() {
        val request = SignupRequest(email.value, password.value, name.value)
        if(isNotValidSignup(request))
            return

        try {
            val response = requestSignup(request)
            onSignupResponse(response)
        } catch (e:Exception) {
            error("signup error", e) //ankologger를 상속받은 클래스(baseviewmodel)에서 사용할 수 있는 anko라이브러리의 함수
            toast("알 수 없는 오류가 발생했습니다.")
        }
    }

    private fun isNotValidSignup(signupRequest: SignupRequest)
    = when {
        signupRequest.isNotValidMail() -> {
            toast("이메일 형식이 정확하지 않습니다.")
            true
        }
        signupRequest.isNotValidPassword() -> {
            toast("비밀번호는 8자 이상 20자 이하로 입력해주세요.")
            true
        }
        signupRequest.isNotValidName() -> {
            toast("이름은 2자 이상 20자 이하로 입력해주세요.")
            true
        }
        else -> false
    }

    //회원가입 api 호출
    private suspend fun requestSignup(request: SignupRequest)
    = withContext(Dispatchers.IO) { //네트워크 요청이 일어나는 동안 ui가 멈춘 것처럼 보일 수 있기 때문에 네트워크 요청은 비동기로 실행
        BungeeApi.instance.signup(request)
    } //withContext 코루틴 빌더 사용 시 현제 스레드를 블러킹하지 않고 새로운 코루틴 시작 가능
    //이 블록 내의 코드는 IO 스레드에서 비동기로 실행되게 되며, 해당 함수는 코루틴 내부에서 실행되거나 suspend 함수 내부에서 실행돼야 함

    private fun onSignupResponse(response: ApiResponse<Void>) {
        if(response.success) {
            toast("회원가입이 되었습니다. 로그인 후 이용해주세요.")
            finishActivity()
        }
        else {
            toast(response.message?: "알 수 없는 오류가 발생했습니다.")
        }
    }
}