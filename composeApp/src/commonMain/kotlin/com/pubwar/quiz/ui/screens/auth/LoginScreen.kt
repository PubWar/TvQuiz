
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pubwar.quiz.ui.components.EditText
import com.pubwar.quiz.ui.view_models.LoginViewModel
import com.pubwar.quiz.utills.toCyrilic
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import pubwartvquiz.composeapp.generated.resources.Res
import pubwartvquiz.composeapp.generated.resources.hello
import pubwartvquiz.composeapp.generated.resources.login_button
import pubwartvquiz.composeapp.generated.resources.login_password
import pubwartvquiz.composeapp.generated.resources.login_username

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
        EditText(
            value = state.username,
            onValueChange = viewModel::onUsernameChanged,
            placeholder = stringResource(Res.string.login_username)
        )
        Spacer(modifier = Modifier.height(8.dp))
        EditText(
            value = state.password,
            onValueChange = viewModel::onPasswordChanged,
            placeholder = stringResource(Res.string.login_password),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("sta se ovde desava opet")
        Button(
            onClick = { coroutineScope.launch { viewModel.login() } },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(16.dp))
            } else {
                Text(stringResource(Res.string.login_button))
            }
        }
        state.errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = Color.Blue)
        }
    }
}
