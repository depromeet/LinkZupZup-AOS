package com.depromeet.linkzupzup.view.main.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.presenter.model.User
import com.depromeet.linkzupzup.ui.theme.LinkZupZupTheme
import com.depromeet.linkzupzup.presenter.MainViewModel

class MainUI: BaseView<MainViewModel>() {

    @Composable
    override fun onCreateViewContent() {
        LinkZupZupTheme {
            Surface(color = MaterialTheme.colors.background) {
                Column(modifier = Modifier.fillMaxWidth()) {

                    vm?.let { viewModel -> UserUI(viewModel) }

                    Button(onClick = {
                        vm?.getUserInfo()
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text("클릭", textAlign = TextAlign.Center)
                    }

                }
            }
        }
    }

}

@Composable
fun UserUI(vm: MainViewModel) {
    val userInfo: MutableState<User?> = remember { mutableStateOf(null)}
    vm.userInfo.observe(vm.lifecycleOwner!!) { user ->
        userInfo.value = user
    }

    userInfo.value?.let { user ->
        UserLoginStateUI(user)
    } ?: NeedUserLoginUI()
}

@Composable
fun UserLoginStateUI(user: User) {
    val str = "name: ${user.name}, age: ${user.age}"
    Text(str, textAlign = TextAlign.Center, modifier = Modifier
        .fillMaxWidth()
        .height(60.dp))
}

@Composable
fun NeedUserLoginUI() {
    Text("로그인이 필요합니다.", textAlign = TextAlign.Center, modifier = Modifier
        .fillMaxWidth()
        .height(60.dp))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    /**
     * 참고 : Preview하려는 Composable은 ViewModel을 참조할 수 없습니다.
     */
    LinkZupZupTheme {
        Surface(color = MaterialTheme.colors.background) {
            Column(modifier = Modifier.fillMaxWidth()) {
                val isLoginState = true
                val user = User("Jackson", 31)
                if (isLoginState) UserLoginStateUI(user)
                else NeedUserLoginUI()
            }
        }
    }
}