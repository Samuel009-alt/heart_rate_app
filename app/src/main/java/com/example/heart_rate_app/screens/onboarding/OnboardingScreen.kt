package com.example.heart_rate_app.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.heart_rate_app.R
import com.example.heart_rate_app.navigation.Routes
import com.example.heart_rate_app.screens.onboarding.components.FeatureItem
import com.example.heart_rate_app.ui.theme.DarkBlue
import com.example.heart_rate_app.ui.theme.OffWhite
import com.example.heart_rate_app.ui.theme.PureWhite
import com.example.heart_rate_app.ui.theme.SoftBlack

@Composable
fun OnboardingScreen(
    navController: NavController
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            // Top spacer
            Spacer(modifier = Modifier.weight(0.5f))

            // Logo and Title Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(2f)
            ) {
                // Logo and Title in Row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_heart_main_logo),
                        contentDescription = "Heart Logo",
                        modifier = Modifier
                            .size(80.dp)
                    )

                    Text(
                        text = "HR.",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = SoftBlack,
                            fontSize = 36.sp
                        )
                    )
                }

                Text(
                    text = "Welcome To HR. Monitor",
                    style = MaterialTheme.typography.bodyLarge,
                    color = SoftBlack,
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                )

                // Background Illustration Area
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(200.dp)
                        .background(
                            color = DarkBlue.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Simple heart rate visualization
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(5) { index ->
                            Box(
                                modifier = Modifier
                                    .size(
                                        width = 4.dp,
                                        height = when (index) {
                                            1 -> 40.dp
                                            2 -> 60.dp
                                            3 -> 35.dp
                                            else -> 20.dp
                                        }
                                    )
                                    .background(
                                        color = if (index % 2 == 0) Color.Red else DarkBlue,
                                        shape = RoundedCornerShape(2.dp)
                                    )
                            )
                        }
                    }
                }
            }

            // Features Section
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(1f)
            ) {
                FeatureItem("📊 Track your heart rate in real-time")
                FeatureItem("📈 Monitor historical trends")
                FeatureItem("💾 Save and analyze your data")
            }

            // Button Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Button(
                    onClick = {
                        navController.navigate(Routes.SIGN_IN) {
                            popUpTo(Routes.ONBOARDING) {
                                inclusive = true
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkBlue,
                        contentColor = PureWhite
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 4.dp
                    )
                ) {
                    Text(
                        text = "Get Started",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Text(
                    text = "Your health, your data, your control",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}