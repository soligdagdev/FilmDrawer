package com.soligdag.filmdrawer.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soligdag.filmdrawer.R
import com.soligdag.filmdrawer.ui.components.LoadingDialog
import com.soligdag.filmdrawer.ui.theme.FilmDrawerTheme
import com.soligdag.filmdrawer.ui.theme.Typography
import com.soligdag.filmdrawer.ui.viewmodels.EmailVerificationViewModel
import com.soligdag.filmdrawer.ui.viewmodels.SignupViewModel

@Composable
fun EmailVerificationScreen(viewModel: EmailVerificationViewModel = hiltViewModel(), onEmailVerified: () -> Unit = {}) {

    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        LoadingDialog()
    }
    if(uiState.emailVerified) { // Email is now verified
        LaunchedEffect(1) {
            onEmailVerified()
            viewModel.resetState()
        }
    }
    if (uiState.emailSent) {
        Toast.makeText(
            LocalContext.current,
            "Email sent, please check your inbox",
            Toast.LENGTH_SHORT
        ).show()
        viewModel.resetState()
    }
    emailVerificationScreenContent(maskedEmail = viewModel.maskedEmailAddress, onResendEmailClicked = { viewModel.sendVerificationEmail() }, onLoginBtnClicked = {})
}

@Composable
fun emailVerificationScreenContent(
    maskedEmail: String,
    onResendEmailClicked: () -> Unit,
    onLoginBtnClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.VerifiedUser,
            contentDescription = "Email verification icon",
            Modifier.size(60.dp)
        )
        Text(
            text = "Please verify your email address",
            style = Typography.headlineSmall.copy(textAlign = TextAlign.Center),

            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "To be able to login the app, you need to verify your email address. You can do that by following the instructions we have sent to you at \n $maskedEmail",
            style = Typography.bodyMedium,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )

        Row(modifier = Modifier.padding(16.dp, 42.dp)) {
            Text(text = "Haven't received any email? ", style = Typography.bodySmall)
            val emailLinkStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.montserrat_regular, FontWeight.Normal)),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onBackground
            )
            ClickableText(
                text = AnnotatedString(" Resend email"),
                onClick = { onResendEmailClicked() },
                style = emailLinkStyle,
            )
        }



    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun emailVerificationScreenPreview() {
    FilmDrawerTheme(darkTheme = true) {
        emailVerificationScreenContent("ham**************.com", {}, {})
    }
}