package com.fitlog.app.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fitlog.app.ui.theme.FitLogGreen
import com.fitlog.app.ui.theme.FitLogGreenDark
import com.fitlog.app.ui.theme.FitLogGreenLight

@Composable
fun ProfileScreen() {
    Column(Modifier.fillMaxSize()) {
        Box(
            Modifier.fillMaxWidth().height(64.dp)
                .background(Brush.horizontalGradient(listOf(FitLogGreen, FitLogGreenDark))),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text("Profile", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 20.dp))
        }
        Column(Modifier.fillMaxWidth().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                Modifier.size(80.dp).background(FitLogGreenLight, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text("AN", color = FitLogGreenDark, fontWeight = FontWeight.Bold, fontSize = androidx.compose.ui.unit.TextUnit(22f, androidx.compose.ui.unit.TextUnitType.Sp))
            }
            Spacer(Modifier.height(12.dp))
            Text("Signed in via FitLog account", color = MaterialTheme.colorScheme.outline)
            Spacer(Modifier.height(4.dp))
            Text(
                "Google Sign-In (SSO) becomes available in the final POE build.",
                color = MaterialTheme.colorScheme.outline,
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}
