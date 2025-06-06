package com.teamnotfound.airise.auth.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.teamnotfound.airise.AppScreen
import com.teamnotfound.airise.data.serializable.UserDataUiState
import com.teamnotfound.airise.util.BgBlack
import com.teamnotfound.airise.util.Orange
import com.teamnotfound.airise.util.White
import kotlinx.coroutines.delay

@Composable
fun ThankYouScreen(
    appNavController: NavController,
    viewModel: OnboardingViewModel,
    newUser: UserDataUiState
) {
    LaunchedEffect(Unit) {
        viewModel.saveUserData(newUser)
        delay(2000)
        appNavController.navigate(AppScreen.HOMESCREEN.name)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgBlack),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Thank you!",
                fontSize = 36.sp,
                color = White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            CircularProgressIndicator(
                color = Orange,
                strokeWidth = 4.dp,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}
