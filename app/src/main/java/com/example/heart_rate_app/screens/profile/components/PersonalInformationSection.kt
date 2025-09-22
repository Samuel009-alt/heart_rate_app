package com.example.heart_rate_app.screens.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Wc
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heart_rate_app.data.models.UserData
import com.example.heart_rate_app.ui.theme.GrayText
import com.example.heart_rate_app.ui.theme.SoftBlack


@Composable
fun PersonalInformationSection(
    user: UserData?,
    onEditClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 20.dp)
        ) {
            Icon(
                Icons.Outlined.Person,
                contentDescription = null,
                tint = SoftBlack,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "PERSONAL INFORMATION",
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold,
                color = SoftBlack,
            )

            Spacer(modifier = Modifier.width(65.dp))

            Box(
                modifier = Modifier
                    .clickable { onEditClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.Edit,
                    contentDescription = "Edit",
                    tint = Color(0xFF2C3E50),
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Phone Number Field
        if (!user?.phoneNumber.isNullOrEmpty()) {
            PersonalInfoItem(
                label = "Phone Number",
                value = user?.phoneNumber ?: "",
                icon = Icons.Outlined.Phone
            )
        }

        // Row for Gender and Age
        if (!user?.gender.isNullOrEmpty() || user?.age != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Gender Field
                if (!user?.gender.isNullOrEmpty()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Outlined.Wc,
                                contentDescription = null,
                                tint = SoftBlack.copy(alpha = 0.7f),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Gender",
                                    color = Color.Gray,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                Text(
                                    text = user?.gender ?: "",
                                    color = SoftBlack,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        if (user?.address.isNullOrEmpty()) {
                            Spacer(modifier = Modifier.height(16.dp))
                        } else {
                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider(
                                color = Color.LightGray.copy(alpha = 0.5f),
                                thickness = 1.dp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }

                // Age Field
                if (user?.age != null) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Outlined.Cake,
                                contentDescription = null,
                                tint = SoftBlack.copy(alpha = 0.7f),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Age",
                                    color = Color.Gray,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                Text(
                                    text = user.age.toString(),
                                    color = SoftBlack,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        if (user?.address.isNullOrEmpty()) {
                            Spacer(modifier = Modifier.height(16.dp))
                        } else {
                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider(
                                color = Color.LightGray.copy(alpha = 0.5f),
                                thickness = 1.dp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        // Address Field
        if (!user?.address.isNullOrEmpty()) {
            PersonalInfoItem(
                label = "Address",
                value = user?.address ?: "",
                icon = Icons.Outlined.LocationOn,
                isLast = true
            )
        }

        // Show message if no personal info is available
        if (user?.phoneNumber.isNullOrEmpty() &&
            user?.gender.isNullOrEmpty() &&
            user?.age == null &&
            user?.address.isNullOrEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Outlined.PersonAdd,
                    contentDescription = null,
                    tint = GrayText,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "No additional personal information",
                    fontSize = 16.sp,
                    color = GrayText,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Tap edit to add your details",
                    fontSize = 14.sp,
                    color = GrayText.copy(alpha = 0.7f),
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}


@Composable
private fun PersonalInfoItem(
    label: String,
    value: String,
    icon: ImageVector,
    isLast: Boolean = false
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = SoftBlack.copy(alpha = 0.7f),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    color = Color.Gray,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = value,
                    color = SoftBlack,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        if (!isLast) {
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                color = Color.LightGray.copy(alpha = 0.5f),
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
