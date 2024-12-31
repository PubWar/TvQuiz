
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pubwar.quiz.ui.components.EditText
import com.pubwar.quiz.ui.components.OtpTextField
import com.pubwar.quiz.ui.components.PhoneNumberInput
import com.pubwar.quiz.ui.screens.auth.PhoneAuthState
import com.pubwar.quiz.ui.view_models.LoginViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import pubwartvquiz.composeapp.generated.resources.Res
import pubwartvquiz.composeapp.generated.resources.login_button

@Composable
fun LoginScreen(viewModel: LoginViewModel, successLogin: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    if(state.success)
    {
        successLogin()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        when (state.phoneAuthState)
        {
            PhoneAuthState.EnterNumber ->
            {

                PhoneNumberInput(
                    phoneNumber = state.phoneNumber,
                    onPhoneNumberChange = viewModel::onPhoneNumberChanged,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { coroutineScope.launch { viewModel.verifyPhoneNumber() } },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoading
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp))
                    } else {
                        Text(stringResource(Res.string.login_button))
                    }
                }
            }

            PhoneAuthState.EnterCode -> {
                OtpTextField(
                    otpText = state.smsCode,
                    onOtpTextChange  = { value, otpInputFilled ->
                        viewModel.onSmsCodeChanged(smsCode = value)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { coroutineScope.launch { viewModel.verifyCode() } },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoading
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp))
                    } else {
                        Text("Send Code")
                    }
                }
            }

            PhoneAuthState.PhoneIsVerified ->  Text(state.verifiedPhoneNumber)
            PhoneAuthState.Unauthorized -> {
                EditText(
                    value = state.firstName,
                    onValueChange = viewModel::onFirstChanged,
                    placeholder = "Enter firstname"
                )
                Spacer(modifier = Modifier.height(16.dp))
                EditText(
                    value = state.lastName,
                    onValueChange = viewModel::onLastnameChanged,
                    placeholder = "Enter lastname"
                )

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { coroutineScope.launch { viewModel.register(state.firstName, state.lastName) } },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoading
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp))
                    } else {
                        Text("Register")
                    }
                }
            }
        }



        state.errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = Color.Blue)
        }
    }
}
