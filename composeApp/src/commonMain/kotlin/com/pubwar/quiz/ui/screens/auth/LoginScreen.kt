import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pubwar.quiz.getCurrentTime
import com.pubwar.quiz.ui.components.EditText
import com.pubwar.quiz.ui.components.GradientButton
import com.pubwar.quiz.ui.components.OtpTextField
import com.pubwar.quiz.ui.components.PhoneNumberInput
import com.pubwar.quiz.ui.screens.auth.PhoneAuthState
import com.pubwar.quiz.ui.theme.AppGradients
import com.pubwar.quiz.ui.view_models.LoginViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pubwartvquiz.composeapp.generated.resources.Res
import pubwartvquiz.composeapp.generated.resources.login_button
import pubwartvquiz.composeapp.generated.resources.main_icon
import pubwartvquiz.composeapp.generated.resources.open_qr_scanner
import pubwartvquiz.composeapp.generated.resources.rtv_icon

@Composable
fun LoginScreen(viewModel: LoginViewModel, successLogin: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    if (state.success) {
        successLogin()
    }

    if (state.showLoginPage)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .imePadding(),
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(Res.drawable.main_icon),
                contentDescription = "main_icon",
                modifier = Modifier
                    .width(200.dp)
                    .height(149.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))

            when (state.phoneAuthState) {
                PhoneAuthState.EnterNumber -> {

                    PhoneNumberInput(
                        phoneNumber = state.phoneNumber,
                        onPhoneNumberChange = viewModel::onPhoneNumberChanged,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    GradientButton(
                        text = stringResource(Res.string.login_button),
                        gradient = AppGradients.lightBlueGradient,
                        onClick = {
                            coroutineScope.launch { viewModel.verifyPhoneNumber() }
                        },
                    )
                }

                PhoneAuthState.EnterCode -> {
                    OtpTextField(
                        otpText = state.smsCode,
                        onOtpTextChange = { value, otpInputFilled ->
                            viewModel.onSmsCodeChanged(smsCode = value)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    GradientButton(
                        text = "Send Code",
                        gradient = AppGradients.lightBlueGradient,
                        onClick = {
                            coroutineScope.launch { viewModel.verifyCode() }
                        },
                    )
                }

                PhoneAuthState.PhoneIsVerified -> Text(state.verifiedPhoneNumber)
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
                    EditText(
                        value = state.email,
                        onValueChange = viewModel::onEmailChanged,
                        placeholder = "Enter email",
                        keyboardType = KeyboardType.Email
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    EditText(
                        value = state.city,
                        onValueChange = viewModel::onCityChanged,
                        placeholder = "Enter city"
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    EditText(
                        value = state.age,
                        onValueChange = viewModel::onAgeChanged,
                        placeholder = "Enter age",
                        keyboardType = KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    GradientButton(
                        text = "Register",
                        gradient = AppGradients.lightBlueGradient,
                        onClick = {
                            coroutineScope.launch {
                                viewModel.register()
                            }
                        },
                    )
                }
            }



            state.errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, color = Color.Blue)
            }
        }
}
