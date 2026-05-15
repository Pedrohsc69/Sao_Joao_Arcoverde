package com.example.sao_joao_em_arcoverde.screens.welcome



import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sao_joao_em_arcoverde.ui.theme.AmberSecondary
import com.example.sao_joao_em_arcoverde.ui.theme.BackgroundDark
import com.example.sao_joao_em_arcoverde.ui.theme.BlueAccent
import com.example.sao_joao_em_arcoverde.ui.theme.BorderGold
import com.example.sao_joao_em_arcoverde.ui.theme.GoldPrimary
import com.example.sao_joao_em_arcoverde.ui.theme.GreenAccent
import com.example.sao_joao_em_arcoverde.ui.theme.PinkAccent
import com.example.sao_joao_em_arcoverde.ui.theme.RedAccent
import com.example.sao_joao_em_arcoverde.ui.theme.Sao_Joao_Em_ArcoverdeTheme
import com.example.sao_joao_em_arcoverde.ui.theme.SurfaceDark
import com.example.sao_joao_em_arcoverde.ui.theme.TextPrimary
import com.example.sao_joao_em_arcoverde.ui.theme.TextSecondary

@Composable
fun WelcomeScreen(
    onStartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
            )
    ) {
        WelcomeContent(
            onStartClick = onStartClick,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun WelcomeContent(
    onStartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 20.dp)
            .border(
                width = 1.dp,
                color = BorderGold,
                shape = RoundedCornerShape(28.dp)
            )
            .padding(horizontal = 20.dp, vertical = 26.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FestivalFlags()

        Spacer(modifier = Modifier.weight(0.8f))

        FireBadge()

        Spacer(modifier = Modifier.weight(0.5f))

        WelcomeTitle()

        Spacer(modifier = Modifier.size(18.dp))

        Text(
            text = "Seu guia completo para curtir a maior festa do sertão pernambucano. Programação, mapa e muito mais — tudo na palma da sua mão.",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onStartClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GoldPrimary,
                contentColor = BackgroundDark
            )
        ) {
            Text(
                text = "INICIAR",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
}

@Composable
private fun FestivalFlags(
    modifier: Modifier = Modifier
) {
    val colors = listOf(
        RedAccent,
        GoldPrimary,
        GreenAccent,
        BlueAccent,
        PinkAccent,
        AmberSecondary
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Top
    ) {
        repeat(12) { index ->
            FlagTriangle(
                color = colors[index % colors.size]
            )
        }
    }
}

@Composable
private fun FlagTriangle(
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(width = 18.dp, height = 24.dp)
            .clip(TriangleShape)
            .background(color)
    )
}

private val TriangleShape: Shape = GenericShape { size, _ ->
    moveTo(0f, 0f)
    lineTo(size.width, 0f)
    lineTo(size.width / 2f, size.height)
    close()
}

@Composable
private fun FireBadge(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(92.dp)
            .border(
                width = 2.dp,
                color = GoldPrimary,
                shape = CircleShape
            )
            .background(
                color = SurfaceDark,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.LocalFireDepartment,
            contentDescription = "Ícone de fogueira",
            tint = AmberSecondary,
            modifier = Modifier.size(46.dp)
        )
    }
}

@Composable
private fun WelcomeTitle(
    modifier: Modifier = Modifier
) {
    Text(
        text = buildAnnotatedString {
            append("Bem-vindo\nao\n")
            withStyle(
                style = SpanStyle(color = GoldPrimary)
            ) {
                append("São João")
            }
            append("\nde\nArcoverde!")
        },
        style = MaterialTheme.typography.displayLarge,
        color = TextPrimary,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun WelcomeScreenPreview() {
    Sao_Joao_Em_ArcoverdeTheme {
        WelcomeScreen(
            onStartClick = {}
        )
    }
}