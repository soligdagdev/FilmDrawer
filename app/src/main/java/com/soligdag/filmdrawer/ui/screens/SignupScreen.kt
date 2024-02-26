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
import androidx.compose.material.OutlinedButton
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import com.soligdag.filmdrawer.ui.viewmodels.SignupViewModel

@Composable
fun SignupScreen(viewModel: SignupViewModel = hiltViewModel(), onLoginClicked: () -> Unit = {}, onSignedUp : ()-> Unit = {}) {
    val uiState by viewModel.uiState.collectAsState()
    val email by viewModel.email.observeAsState()
    val password by viewModel.password.observeAsState()
    val confirmPassword by viewModel.confirmPassword.observeAsState()
    if (uiState.isLoading) {
        LoadingDialog()
    }
    if (uiState.signupErrorMessage.isNotEmpty()) {
        Toast.makeText(LocalContext.current, uiState.signupErrorMessage, Toast.LENGTH_SHORT).show()
        viewModel.resetState()
    }
    if(uiState.hasSignedUp) {
        LaunchedEffect(Unit) {
            onSignedUp()
            viewModel.resetState()
        }
    }

    SignupScreenContent(
        email = email ?: "",
        password = password ?: "",
        confirmPassword = confirmPassword ?: "",
        onEmailChanged = { viewModel.onEmailChange(it) },
        onPasswordChanged = { viewModel.onPasswordChange(it) },
        onConfirmPasswordChanged = { viewModel.onConfirmPasswordChange(it) },
        onSignupClicked = { viewModel.signupUser() },
        onLoginClicked = { onLoginClicked() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreenContent(
    email: String = "",
    password: String = "",
    confirmPassword: String = "",
    onEmailChanged: (newText: String) -> Unit = {},
    onPasswordChanged: (newText: String) -> Unit = {},
    onConfirmPasswordChanged: (newText: String) -> Unit = {},
    onSignupClicked: () -> Unit = {},
    onLoginClicked: () -> Unit = {},
) {

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Box(
            Modifier
                .width(1700.dp)
                .height(450.dp)
                .background(Color.Blue)
        ) {

            Image(
                painterResource(id = R.drawable.gameofthronestwo),
                contentDescription = "backdrop",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(1700.dp)
                    .height(450.dp)
                    .scale(2f),

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
                endY = LocalDensity.current.run { 450.dp.toPx() }
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .scale(1.05f)
                    .background(gradient)
            )


        }


        Column(
            Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "SIGN UP",
                    style = Typography.headlineLarge,
                    modifier = Modifier.padding(32.dp)
                )
            }
            var passwordVisibility: Boolean by remember { mutableStateOf(false) }
            var confirmPasswordVisibility: Boolean by remember { mutableStateOf(false) }
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

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { onConfirmPasswordChanged(it) },
                label = { Text(text = "Confirm Password") },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(0.dp, 12.dp),
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    PasswordTrailingIcon(passwordVisibility = confirmPasswordVisibility) {
                        passwordVisibility = !passwordVisibility
                    }
                }
            )

            Button(
                onClick = { onSignupClicked() }, shape = RoundedCornerShape(12.dp), modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(0.dp, 32.dp)
                    .height(50.dp)
            ) {
                Text(text = "Sign up", style = Typography.bodyMedium)
            }

            DividerWithText(text = "Or continue with", modifier = Modifier.padding(0.dp, 16.dp))

            OutlinedButton(onClick = { }, shape = RoundedCornerShape(12.dp), modifier = Modifier.padding(top = 16.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "",
                    Modifier.size(40.dp, 40.dp)
                )
            }

            Row(Modifier.padding(32.dp)) {
                Text("Already have an account?", style = Typography.bodyMedium)
                var signupLinkStyle = TextStyle(
                    fontFamily = FontFamily(Font(R.font.montserrat_regular, FontWeight.Normal)),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onBackground
                )
                ClickableText(
                    text = AnnotatedString(" Login"),
                    onClick = { onLoginClicked ()},
                    style = signupLinkStyle,
                )
            }
        }
    }





}

@Preview (showBackground = true, showSystemUi = true)
@Composable
fun SignupPreview() {
    FilmDrawerTheme(darkTheme = true) {
        SignupScreenContent()
    }

}