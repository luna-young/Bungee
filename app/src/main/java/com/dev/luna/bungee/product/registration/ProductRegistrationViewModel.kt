package com.dev.luna.bungee.product.registration

import android.app.Activity.RESULT_OK
import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dev.luna.bungee.api.ApiResponse
import com.dev.luna.bungee.api.request.ProductRegistrationRequest
import com.dev.luna.bungee.api.response.ProductImageUploadResponse
import com.dev.luna.bungee.product.category.categoryList
import kotlinx.coroutines.launch
import net.codephobia.ankomvvm.lifecycle.BaseViewModel
import retrofit2.Response

class ProductRegistrationViewModel (app: Application) : BaseViewModel(app) {

    val TAG = ProductRegistrationViewModel::class.java.simpleName
    //이미지 업로드 후 반환받은 이미지 주소를 저장할 변수
    //주소가 입력되면 자동으로 이미지를 표시해주도록 데이터 바인딩을 이용하기 위해 List<MutableLiveData<String?>>으로 선언
    val imageUrls: List<MutableLiveData<String?>> = listOf(
            MutableLiveData(null as String?),
            MutableLiveData(null as String?),
            MutableLiveData(null as String?),
            MutableLiveData(null as String?)
    )

    //업로드 후 반환받은 이미지의 id들을 저장할 리스트
    val imageIds: MutableList<Long?> =
            mutableListOf(null, null, null, null)


    val productName = MutableLiveData("")
    val description = MutableLiveData("")
    val price = MutableLiveData("")
    val categories = MutableLiveData(categoryList.map{it.name}) //뷰에서 스피너로 보여줄 카테고리명 리스트를 담는 변수
    var categoryIdSelected: Int? = categoryList[0].id //뷰의 스피너를 통해 선택된 카테고리의 아이디를 저장할 변수. 디폴트로 첫번째 아이디 값 사용

    val descriptionLimit = 500
    val productNameLimit = 40

    val productNameLength = MutableLiveData("0/$productNameLimit")
    val descriptionLength = MutableLiveData("0/$descriptionLimit")

    var currentImageNum = 0

    fun checkProductNameLength() {
        productName.value?.let {
            if(it.length > productNameLimit) {
                productName.value = it.take(productNameLimit)
            }
            productNameLength.value = "${productName.value?.length}/$productNameLimit"
        }
    }

    fun checkDescriptionLength() {
        description.value?.let {
            if(it.length > descriptionLimit) {
                description.value = it.take(descriptionLimit)
            }
            descriptionLength.value = "${description.value?.length}/$descriptionLimit"
        }
    }

    fun onCategorySelect(position: Int) {
        categoryIdSelected = categoryList[position].id
    }

    //Intent(Intent.ACTION_GET_CONTENT) -> 로컬 파일시스템에서 특정 타입의 파일을 선택하는 액티비티 인텐트를 생성해줌
    fun pickImage(imageNum: Int) {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }

        //intent.resolveActivity() 함수는 인텐트 데이터에 기반해 이 인텐트를 실질적으로 핸들링하는 액티비티 컴포넌트를 반환
        //이 함수를 이용하면 디바이스에 파일을 선택할 수 있는 액티비티의 존재유무 확인 가능
        //만일 존재하지 않는다면 null 반환
        intent.resolveActivity(app.packageManager)?.let {
            startActivityForResult(intent, REQUEST_PICK_IMAGES)
        }
        currentImageNum = imageNum
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode != RESULT_OK)
            return

        when(requestCode) {
            REQUEST_PICK_IMAGES -> data?.let {uploadImage(it)}
        }
    }

    private fun uploadImage(intent: Intent) =
            getContent(intent.data)?.let { //getContent() -> uri로부터 파일을 읽어오는 역할
                imageFile ->
                viewModelScope.launch { //viewModelScope -> 뷰모델이 갖고있는 코루틴 스코프. 이를 통해 실행 중인 코루틴은 뷰모델이 클리어될 때 모두 중단됨
                    val response = ProductImageUploader().upload(imageFile)
                    onImageUploadResponse(response)
                }
            }


    private fun onImageUploadResponse(
            response: ApiResponse<ProductImageUploadResponse>
    ) {
        if(response.success && response.data != null) {

            imageUrls[currentImageNum].value = response.data.filePath
            imageIds[currentImageNum] = response.data.productImageId
            Log.d(TAG, "@@response.data.filePath: ${response.data.filePath}")
            Log.d(TAG, "@@response.data.productImageId: ${response.data.productImageId}")

        } else {
            toast(response.message ?: "알 수 없는 오류가 발생했습니다.")
        }
    }

    //뷰에서 버튼을 클릭했을 때 실행될 함수
    //api 요청 객체를 만들고 api를 호출한 후 응답 결과를 처리하는 순서로 되어있음
    //suspending 함수인 상품등록 api호출이 필요하기 때문에 suspend로 선언
    //suspending함수는 또 다른 suspending함수 내에서 호출되거나 코루틴 내에서 실행돼야 하지만,
    //anko를 이용해 onClick이벤트를 사용하는 경우 이벤트 콜백이 코루틴 스코프 내에서 실행되기 떄문에 suspend로 선언해도 문제 없음
    suspend fun register() {
        val request = extractRequest()
        val response = ProductRegistrar().register(request)
        onRegistrationResponse(response)
    }

    private fun extractRequest(): ProductRegistrationRequest =
            ProductRegistrationRequest(
                    productName.value,
                    description.value,
                    price.value?.toIntOrNull(),
                    categoryIdSelected,
                    imageIds
            )

    private fun onRegistrationResponse(
            response: ApiResponse<Response<Void>>
    ) {
        if(response.success) {
            //confirm 함수는 BaseViewModel에 정의된 얼럿 다이얼로그를 띄워주는 함수로,
            //두번째 인자로 콜백을 받아 버튼을 눌렀을 때에 실행시켜줌
            confirm("상품이 등록되었습니다") {
                finishActivity()
            }
        } else {
            toast(response.message?:"알 수 없는 오류가 발생했습니다.")
        }
    }

    companion object {
        const val REQUEST_PICK_IMAGES = 0
    }

}