package com.example.sao_joao_em_arcoverde.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sao_joao_em_arcoverde.ui.theme.SurfaceDarkVariant
import com.example.sao_joao_em_arcoverde.ui.theme.GoldPrimary
import com.example.sao_joao_em_arcoverde.ui.theme.SurfaceDark
import com.example.sao_joao_em_arcoverde.ui.theme.TextSecondary

enum class BottomNavDestination {
    Home,
    Schedule,
    Map,
    More
}

@Composable
fun BottomNavBar(
    selectedDestination: BottomNavDestination,
    onHomeClick: () -> Unit,
    onScheduleClick: () -> Unit,
    onMapClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier.height(74.dp),
        containerColor = SurfaceDarkVariant,
        tonalElevation = 0.dp
    ) {
        NavigationBarItem(
            selected = selectedDestination == BottomNavDestination.Home,
            onClick = onHomeClick,
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = "Home"
                )
            },
            label = {
                Text(
                    text = "HOME",
                    fontWeight = FontWeight.Black
                )
            },
            colors = bottomNavItemColors()
        )

        NavigationBarItem(
            selected = selectedDestination == BottomNavDestination.Schedule,
            onClick = onScheduleClick,
            icon = {
                Icon(
                    imageVector = Icons.Rounded.CalendarMonth,
                    contentDescription = "Programação"
                )
            },
            label = {
                Text(
                    text = "PROG.",
                    fontWeight = FontWeight.Black
                )
            },
            colors = bottomNavItemColors()
        )

        NavigationBarItem(
            selected = selectedDestination == BottomNavDestination.Map,
            onClick = onMapClick,
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Map,
                    contentDescription = "Mapa"
                )
            },
            label = {
                Text(
                    text = "MAPA",
                    fontWeight = FontWeight.Black
                )
            },
            colors = bottomNavItemColors()
        )

        NavigationBarItem(
            selected = selectedDestination == BottomNavDestination.More,
            onClick = onMoreClick,
            icon = {
                Icon(
                    imageVector = Icons.Rounded.MoreHoriz,
                    contentDescription = "Mais opções"
                )
            },
            label = {
                Text(
                    text = "MAIS",
                    fontWeight = FontWeight.Black
                )
            },
            colors = bottomNavItemColors()
        )
    }
}

@Composable
private fun bottomNavItemColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = GoldPrimary,
    selectedTextColor = GoldPrimary,
    indicatorColor = SurfaceDark,
    unselectedIconColor = TextSecondary,
    unselectedTextColor = TextSecondary
)