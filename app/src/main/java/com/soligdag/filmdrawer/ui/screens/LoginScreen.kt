package com.soligdag.filmdrawer.ui.screens


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soligdag.filmdrawer.R
import com.soligdag.filmdrawer.ui.components.DividerWithText
import com.soligdag.filmdrawer.ui.components.LoadingDialog
import com.soligdag.filmdrawer.ui.components.PasswordTrailingIcon
import com.soligdag.filmdrawer.ui.theme.FilmDrawerTheme
import com.soligdag.filmdrawer.ui.theme.Typography
import com.soligdag.filmdrawer.ui.viewmodels.LoginViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel(), onSignupClicked: () -> Unit = {}, onLoginSuccessfully : (isEmailVerified : Boolean) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val email by viewModel.email.observeAsState()
    val password by viewModel.password.observeAsState()
    if (uiState.isLoading) {
        LoadingDialog()
    }
    if (uiState.loginErrorMessage.isNotEmpty()) {
        Toast.makeText(LocalContext.current, uiState.loginErrorMessage, Toast.LENGTH_SHORT).show()
        viewModel.resetErrorState()
    }
    if(uiState.hasLoggedIn) {
        LaunchedEffect(Unit) {
            onLoginSuccessfully(uiState.isEmailVerified)
        }

    }
    LoginScreenContent(
        email = email ?: "",
        password = password ?: "",
        onEmailChanged = { viewModel.updateEmail(newText = it) },
        onPasswordChanged = { viewModel.updatePassword(newText = it) },
        onLoginBtnClicked = { viewModel.loginUser() },
        onSignupClicked = {onSignupClicked()})

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginScreenContent(
    email: String = "",
    password: String = "",
    onEmailChanged: (newEmail: String) -> Unit = {},
    onPasswordChanged: (newPassword: String) -> Unit = {},
    onLoginBtnClicked: () -> Unit = {},
    onSignupClicked: () -> Unit = {}
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Box(
            Modifier
                .fillMaxWidth(1f)
                .height(420.dp)
                .background(Color.Yellow)
        ) {

            Image(
                painterResource(id = R.drawable.dunecropped),
                contentDescription = "backdrop",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(420.dp)

                )
            val gradient = Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.background.copy(alpha = 0.2f),
                    MaterialTheme.colorScheme.background.copy(alpha = 0.4f),
                    MaterialTheme.colorScheme.background.copy(alpha = 0.6f),
                    MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                    MaterialTheme.colorScheme.background.copy(alpha = 0.9f),
                    MaterialTheme.colorScheme.background,
                ),
                startY = 0f,  // 1/3
                endY = LocalDensity.current.run { 420.dp.toPx() }
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(gradient)
            )


        }


        Column(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            // HEADING PART
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "LOGIN",
                    style = Typography.headlineLarge,
                    modifier = Modifier.padding(top = 32.dp)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp)
            ) {
                var passwordVisibility: Boolean by remember { mutableStateOf(false) }
                OutlinedTextField(
                    value = email,
                    onValueChange = { onEmailChanged(it) },
                    label = { Text(text = "Email") },
                    modifier = Modifier.fillMaxWidth(0.8f),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { onPasswordChanged(it) },
                    label = { Text(text = "Password") },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(0.dp, 12.dp),
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        PasswordTrailingIcon(passwordVisibility = passwordVisibility) {
                            passwordVisibility = !passwordVisibility
                        }
                    }
                )

                Button(
                    onClick = { onLoginBtnClicked() },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(0.dp, 32.dp)
                        .height(50.dp)
                ) {
                    Text(text = "Log in", style = Typography.bodyMedium)
                }

                DividerWithText(text = "Or continue with", modifier = Modifier.padding(0.dp, 16.dp))

                OutlinedButton(
                    onClick = { },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "",
                        Modifier.size(40.dp, 40.dp)
                    )
                }

                Row(Modifier.padding(32.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("Do not have an account with us?", style = Typography.bodyMedium)
                    val signupLinkStyle = TextStyle(
                        fontFamily = FontFamily(Font(R.font.montserrat_regular, FontWeight.Normal)),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    ClickableText(
                        text = AnnotatedString(" Sign up"),
                        onClick = { onSignupClicked() },
                        style = signupLinkStyle,
                    )
                }

            }


        }


    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    FilmDrawerTheme(darkTheme = true) {
        LoginScreenContent()
    }
}
