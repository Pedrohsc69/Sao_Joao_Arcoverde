package com.example.sao_joao_em_arcoverde.screens.more

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.sao_joao_em_arcoverde.data.model.EmergencyContact
import com.example.sao_joao_em_arcoverde.data.model.EmergencyContactType
import com.example.sao_joao_em_arcoverde.data.model.Sponsor
import com.example.sao_joao_em_arcoverde.data.model.TeamMember
import com.example.sao_joao_em_arcoverde.data.preferences.ThemeMode
import com.example.sao_joao_em_arcoverde.ui.components.BottomNavBar
import com.example.sao_joao_em_arcoverde.ui.components.BottomNavDestination
import com.example.sao_joao_em_arcoverde.ui.theme.LocalAppColors

private enum class MorePanel {
    NOTIFICATIONS,
    APPEARANCE,
    HISTORY,
    ARTISTS,
    SECURITY_HEALTH,
    EMERGENCY_CONTACTS,
    SPONSORS,
    ABOUT
}

private data class MoreOption(
    val title: String,
    val icon: ImageVector,
    val iconColor: Color,
    val panel: MorePanel
)

@Composable
fun MoreScreen(
    developers: List<TeamMember>,
    emergencyContacts: List<EmergencyContact>,
    sponsors: List<Sponsor>,
    notificationsEnabled: Boolean,
    themeMode: ThemeMode,
    isLoading: Boolean,
    errorMessage: String?,
    onNotificationsEnabledChange: (Boolean) -> Unit,
    onThemeModeChange: (ThemeMode) -> Unit,
    onHomeClick: () -> Unit,
    onScheduleClick: () -> Unit,
    onMapClick: () -> Unit,
    onArtistsClick: () -> Unit,
    onAboutAppClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onSponsorsClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMenuClick: () -> Unit,
    onSendTestNotification: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current
    val selectedPanel = remember {
        mutableStateOf<MorePanel?>(null)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = appColors.background,
        bottomBar = {
            BottomNavBar(
                selectedDestination = BottomNavDestination.More,
                onHomeClick = onHomeClick,
                onScheduleClick = onScheduleClick,
                onMapClick = onMapClick,
                onMoreClick = {}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(appColors.background)
                .padding(innerPadding)
                .padding(
                    top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
                )
                .padding(horizontal = 16.dp)
        ) {
            MoreHeader(
                onMenuClick = onMenuClick,
                onSearchClick = onSearchClick
            )

            Spacer(modifier = Modifier.height(18.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                DevelopersSection(
                    developers = developers
                )

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = "Mais Opções",
                    color = appColors.textPrimary,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Configurações e informações adicionais do festival.",
                    color = appColors.textSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(18.dp))

                when {
                    isLoading -> {
                        Text(
                            text = "Carregando informações...",
                            color = appColors.textSecondary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    errorMessage != null -> {
                        Text(
                            text = "Não foi possível carregar as informações adicionais.",
                            color = appColors.red,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    else -> {
                        MoreOptionsList(
                            emergencyContactsCount = emergencyContacts.size,
                            sponsorsCount = sponsors.size,
                            onOptionClick = { panel ->
                                when (panel) {
                                    MorePanel.HISTORY -> onHistoryClick()
                                    MorePanel.ARTISTS -> onArtistsClick()
                                    MorePanel.SPONSORS -> onSponsorsClick()
                                    MorePanel.ABOUT -> onAboutAppClick()
                                    else -> selectedPanel.value = panel
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                ActionButtonsRow()

                Spacer(modifier = Modifier.height(22.dp))

                FooterInfo()

                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }

    selectedPanel.value?.let { panel ->
        MorePanelBottomSheet(
            panel = panel,
            emergencyContacts = emergencyContacts,
            sponsors = sponsors,
            notificationsEnabled = notificationsEnabled,
            themeMode = themeMode,
            onNotificationsEnabledChange = onNotificationsEnabledChange,
            onThemeModeChange = onThemeModeChange,
            onSendTestNotification = onSendTestNotification,
            onDismiss = {
                selectedPanel.value = null
            }
        )
    }
}

@Composable
private fun MoreHeader(
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "SÃO JOÃO EM ARCOVERDE",
                color = appColors.primary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                letterSpacing = androidx.compose.ui.unit.TextUnit.Unspecified
            )

            Text(
                text = "MAIS OPÇÕES",
                color = appColors.textSecondary,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
private fun MoreOptionsList(
    emergencyContactsCount: Int,
    sponsorsCount: Int,
    onOptionClick: (MorePanel) -> Unit,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    val options = listOf(
        MoreOption(
            title = "Notificações",
            icon = Icons.Rounded.Notifications,
            iconColor = appColors.primary,
            panel = MorePanel.NOTIFICATIONS
        ),
        MoreOption(
            title = "Aparência",
            icon = Icons.Rounded.Palette,
            iconColor = appColors.blue,
            panel = MorePanel.APPEARANCE
        ),
        MoreOption(
            title = "História",
            icon = Icons.Rounded.MenuBook,
            iconColor = appColors.green,
            panel = MorePanel.HISTORY
        ),
        MoreOption(
            title = "Artistas",
            icon = Icons.Rounded.Star,
            iconColor = appColors.primary,
            panel = MorePanel.ARTISTS
        ),
        MoreOption(
            title = "Segurança e Saúde",
            icon = Icons.Rounded.Security,
            iconColor = appColors.red,
            panel = MorePanel.SECURITY_HEALTH
        ),
        MoreOption(
            title = "Contatos de Emergência",
            icon = Icons.Rounded.Phone,
            iconColor = appColors.blue,
            panel = MorePanel.EMERGENCY_CONTACTS
        ),
        MoreOption(
            title = "Realização e Apoio",
            icon = Icons.Rounded.Star,
            iconColor = appColors.primary,
            panel = MorePanel.SPONSORS
        ),
        MoreOption(
            title = "Sobre o App",
            icon = Icons.Rounded.Info,
            iconColor = appColors.blue,
            panel = MorePanel.ABOUT
        )
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        options.forEach { option ->
            MoreOptionCard(
                option = option,
                onClick = {
                    onOptionClick(option.panel)
                }
            )
        }
    }
}

@Composable
private fun MoreOptionCard(
    option: MoreOption,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(62.dp)
            .border(
                width = 1.dp,
                color = appColors.border,
                shape = RoundedCornerShape(18.dp)
            )
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = appColors.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(option.iconColor.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = option.icon,
                    contentDescription = option.title,
                    tint = option.iconColor,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = option.title,
                color = appColors.textPrimary,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Black,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = null,
                tint = appColors.textSecondary,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

@Composable
private fun ActionButtonsRow(
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current
    val context = LocalContext.current

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionCircleButton(
            icon = Icons.Rounded.Share,
            contentDescription = "Compartilhar aplicativo",
            color = appColors.red,
            onClick = {
                shareApp(context)
            }
        )
    }
}

private fun shareApp(context: Context) {
    val appName = "São João em Arcoverde"

    val shareMessage = """
        Conheça o app $appName!

        Veja a programação, artistas, mapa, polos, contatos de emergência, história da cidade e informações do São João de Arcoverde.

        Em breve disponível para instalação.
    """.trimIndent()

    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, appName)
        putExtra(Intent.EXTRA_TEXT, shareMessage)
    }

    val chooserIntent = Intent.createChooser(
        shareIntent,
        "Compartilhar aplicativo"
    )

    context.startActivity(chooserIntent)
}

@Composable
private fun ActionCircleButton(
    icon: ImageVector,
    contentDescription: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(appColors.surfaceVariant)
            .border(
                width = 1.dp,
                color = appColors.border,
                shape = CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun FooterInfo(
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ARCOVERDE — PERNAMBUCO",
            color = appColors.primary,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Home,
                contentDescription = null,
                tint = appColors.textSecondary,
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "VERSÃO 3.4.0 (2026)",
                color = appColors.textSecondary,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MorePanelBottomSheet(
    panel: MorePanel,
    emergencyContacts: List<EmergencyContact>,
    sponsors: List<Sponsor>,
    notificationsEnabled: Boolean,
    themeMode: ThemeMode,
    onNotificationsEnabledChange: (Boolean) -> Unit,
    onThemeModeChange: (ThemeMode) -> Unit,
    onSendTestNotification: () -> Unit,
    onDismiss: () -> Unit
) {
    val appColors = LocalAppColors.current
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = appColors.surface,
        contentColor = appColors.textPrimary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .padding(bottom = 28.dp)
        ) {
            when (panel) {
                MorePanel.NOTIFICATIONS -> NotificationsContent(
                    notificationsEnabled = notificationsEnabled,
                    onNotificationsEnabledChange = onNotificationsEnabledChange,
                    onSendTestNotification = onSendTestNotification
                )

                MorePanel.APPEARANCE -> AppearanceContent(
                    selectedThemeMode = themeMode,
                    onThemeModeChange = onThemeModeChange
                )

                MorePanel.HISTORY -> HistoryContent()

                MorePanel.ARTISTS -> {
                    Text(
                        text = "A tela de artistas é aberta em uma página própria.",
                        color = appColors.textSecondary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                MorePanel.SECURITY_HEALTH -> SecurityHealthContent(
                    emergencyContacts = emergencyContacts
                )

                MorePanel.EMERGENCY_CONTACTS -> EmergencyContactsContent(
                    emergencyContacts = emergencyContacts
                )

                MorePanel.SPONSORS -> SponsorsContent(
                    sponsors = sponsors
                )

                MorePanel.ABOUT -> AboutAppContent()
            }
        }
    }
}

@Composable
private fun NotificationsContent(
    notificationsEnabled: Boolean,
    onNotificationsEnabledChange: (Boolean) -> Unit,
    onSendTestNotification: () -> Unit
) {
    val appColors = LocalAppColors.current
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            onNotificationsEnabledChange(true)
        }
    }

    val testPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            onSendTestNotification()
        }
    }

    fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    fun requestEnableNotifications() {
        if (hasNotificationPermission()) {
            onNotificationsEnabledChange(true)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    fun sendTestNotification() {
        if (hasNotificationPermission()) {
            onSendTestNotification()
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            testPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    SheetTitle(
        title = "Notificações",
        icon = Icons.Rounded.Notifications,
        color = appColors.primary
    )

    Spacer(modifier = Modifier.height(14.dp))

    Text(
        text = "Receba um aviso 20 minutos antes das atrações do Palco Principal começarem.",
        color = appColors.textSecondary,
        style = MaterialTheme.typography.bodyMedium
    )

    Spacer(modifier = Modifier.height(18.dp))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = appColors.border,
                shape = RoundedCornerShape(18.dp)
            ),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = appColors.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Lembretes do Palco Principal",
                    color = appColors.textPrimary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = if (notificationsEnabled) {
                        "Ativado. O app notificará 20 minutos antes das atrações."
                    } else {
                        "Desativado. Nenhum lembrete será enviado."
                    },
                    color = appColors.textSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Switch(
                checked = notificationsEnabled,
                onCheckedChange = { checked ->
                    if (checked) {
                        requestEnableNotifications()
                    } else {
                        onNotificationsEnabledChange(false)
                    }
                }
            )
        }
    }

    Spacer(modifier = Modifier.height(14.dp))

    Button(
        onClick = {
            sendTestNotification()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = appColors.primary,
            contentColor = appColors.background
        )
    ) {
        Text(
            text = "ENVIAR NOTIFICAÇÃO DE TESTE",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Black
        )
    }

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = "O teste envia uma notificação imediata simulando uma atração do Palco Principal.",
        color = appColors.textSecondary,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
private fun AppearanceContent(
    selectedThemeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit
) {
    val appColors = LocalAppColors.current

    SheetTitle(
        title = "Aparência",
        icon = Icons.Rounded.Palette,
        color = appColors.blue
    )

    Spacer(modifier = Modifier.height(14.dp))

    Text(
        text = "Escolha como deseja visualizar o aplicativo. A opção Sistema acompanha automaticamente o tema configurado no aparelho.",
        color = appColors.textSecondary,
        style = MaterialTheme.typography.bodyMedium
    )

    Spacer(modifier = Modifier.height(18.dp))

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ThemeModeOptionCard(
            title = "Sistema",
            description = "Usa o tema atual do aparelho.",
            selected = selectedThemeMode == ThemeMode.SYSTEM,
            onClick = {
                onThemeModeChange(ThemeMode.SYSTEM)
            }
        )

        ThemeModeOptionCard(
            title = "Claro",
            description = "Usa o modo claro do aplicativo.",
            selected = selectedThemeMode == ThemeMode.LIGHT,
            onClick = {
                onThemeModeChange(ThemeMode.LIGHT)
            }
        )

        ThemeModeOptionCard(
            title = "Escuro",
            description = "Usa o modo escuro do aplicativo.",
            selected = selectedThemeMode == ThemeMode.DARK,
            onClick = {
                onThemeModeChange(ThemeMode.DARK)
            }
        )
    }
}

@Composable
private fun ThemeModeOptionCard(
    title: String,
    description: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (selected) appColors.primary else appColors.border,
                shape = RoundedCornerShape(18.dp)
            )
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                appColors.primary.copy(alpha = 0.16f)
            } else {
                appColors.surfaceVariant
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    color = appColors.textPrimary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    color = appColors.textSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (selected) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(appColors.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Selecionado",
                        tint = appColors.background,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun HistoryContent() {
    val appColors = LocalAppColors.current

    SheetTitle(
        title = "História do São João",
        icon = Icons.Rounded.MenuBook,
        color = appColors.green
    )

    Spacer(modifier = Modifier.height(14.dp))

    Text(
        text = "Conteúdo histórico ainda será complementado pelo grupo. Esta seção pode apresentar a tradição junina de Arcoverde, seus polos culturais, artistas locais e a importância da festa para o sertão pernambucano.",
        color = appColors.textSecondary,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
private fun SecurityHealthContent(
    emergencyContacts: List<EmergencyContact>
) {
    val appColors = LocalAppColors.current

    SheetTitle(
        title = "Segurança e Saúde",
        icon = Icons.Rounded.Security,
        color = appColors.red
    )

    Spacer(modifier = Modifier.height(14.dp))

    Text(
        text = "Em caso de emergência, procure apoio das equipes no evento ou acione os canais oficiais abaixo.",
        color = appColors.textSecondary,
        style = MaterialTheme.typography.bodyMedium
    )

    Spacer(modifier = Modifier.height(14.dp))

    val priorityContacts = emergencyContacts.filter {
        it.type == EmergencyContactType.MEDICAL ||
                it.type == EmergencyContactType.POLICE ||
                it.type == EmergencyContactType.FIRE_DEPARTMENT
    }

    ContactList(
        contacts = priorityContacts.ifEmpty { emergencyContacts }
    )
}

@Composable
private fun EmergencyContactsContent(
    emergencyContacts: List<EmergencyContact>
) {
    val appColors = LocalAppColors.current

    SheetTitle(
        title = "Contatos de Emergência",
        icon = Icons.Rounded.Phone,
        color = appColors.blue
    )

    Spacer(modifier = Modifier.height(14.dp))

    ContactList(
        contacts = emergencyContacts
    )
}

@Composable
private fun SponsorsContent(
    sponsors: List<Sponsor>
) {
    val appColors = LocalAppColors.current

    SheetTitle(
        title = "Patrocinadores",
        icon = Icons.Rounded.Star,
        color = appColors.primary
    )

    Spacer(modifier = Modifier.height(14.dp))

    if (sponsors.isEmpty()) {
        Text(
            text = "Nenhum patrocinador cadastrado até o momento. Quando o arquivo sponsors.json for preenchido, esta seção será atualizada automaticamente.",
            color = appColors.textSecondary,
            style = MaterialTheme.typography.bodyMedium
        )
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            sponsors.forEach { sponsor ->
                SponsorCard(sponsor = sponsor)
            }
        }
    }
}

@Composable
private fun AboutAppContent() {
    val appColors = LocalAppColors.current

    SheetTitle(
        title = "Sobre o App",
        icon = Icons.Rounded.Info,
        color = appColors.blue
    )

    Spacer(modifier = Modifier.height(14.dp))

    Text(
        text = "Aplicativo desenvolvido pelo Grupo 6 como guia digital do São João de Arcoverde. O app reúne programação, mapa, pontos úteis, contatos de emergência e informações adicionais do festival.",
        color = appColors.textSecondary,
        style = MaterialTheme.typography.bodyMedium
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = "Stack: Kotlin, Jetpack Compose, Material 3, Navigation, Room, DataStore, Kotlinx Serialization e OSMDroid.",
        color = appColors.textSecondary,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun ContactList(
    contacts: List<EmergencyContact>
) {
    val appColors = LocalAppColors.current

    if (contacts.isEmpty()) {
        Text(
            text = "Nenhum contato cadastrado.",
            color = appColors.textSecondary,
            style = MaterialTheme.typography.bodyMedium
        )
        return
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        contacts.forEach { contact ->
            ContactCard(contact = contact)
        }
    }
}

@Composable
private fun ContactCard(
    contact: EmergencyContact,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = appColors.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Text(
                text = "${contact.name} — ${contact.phone}",
                color = appColors.textPrimary,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = contact.description,
                color = appColors.textSecondary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun SponsorCard(
    sponsor: Sponsor,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = appColors.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Text(
                text = sponsor.name,
                color = appColors.textPrimary,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = sponsor.category,
                color = appColors.primary,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )

            sponsor.description?.let { description ->
                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = description,
                    color = appColors.textSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun SheetTitle(
    title: String,
    icon: ImageVector,
    color: Color
) {
    val appColors = LocalAppColors.current

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = appColors.background,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = title,
            color = appColors.textPrimary,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
private fun DevelopersSection(
    developers: List<TeamMember>,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Desenvolvedores",
            color = appColors.textPrimary,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Equipe responsável pelo desenvolvimento do aplicativo.",
            color = appColors.textSecondary,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(14.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = developers,
                key = { member -> member.name }
            ) { member ->
                DeveloperCard(member = member)
            }
        }
    }
}

@Composable
private fun DeveloperCard(
    member: TeamMember,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    Card(
        modifier = modifier
            .width(148.dp)
            .height(168.dp)
            .border(
                width = 1.dp,
                color = appColors.border,
                shape = RoundedCornerShape(20.dp)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = appColors.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = member.photoResId),
                contentDescription = member.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(82.dp)
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        color = appColors.primary,
                        shape = CircleShape
                    )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = member.name,
                color = appColors.textPrimary,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Black,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = member.role,
                color = appColors.textSecondary,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}