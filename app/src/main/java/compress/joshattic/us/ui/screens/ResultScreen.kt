package compress.joshattic.us.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compress.joshattic.us.R
import compress.joshattic.us.model.CompressorUiState
import compress.joshattic.us.ui.components.VideoPlayerPreview
import compress.joshattic.us.utils.scaleOnPress

@Composable
fun ResultScreen(
    state: CompressorUiState, 
    onShare: () -> Unit,
    onSave: () -> Unit,
    onCompressAnother: () -> Unit,
    onBack: () -> Unit,
    onPlayExternal: () -> Unit = {},
    onDeleteOriginal: () -> Unit = {},
    onToggleSaveToOriginal: (Boolean) -> Unit = {},
    onToggleAutoDeleteOriginal: (Boolean) -> Unit = {}
) {
    val uriHandler = LocalUriHandler.current
    val haptics = LocalHapticFeedback.current
    LaunchedEffect(Unit) {
        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var visible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) { visible = true }
            
            AnimatedVisibility(
                visible = visible,
                enter = scaleIn(initialScale = 0.5f, animationSpec = spring(dampingRatio = 0.6f)) + fadeIn()
            ) {
                Icon(
                    Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                stringResource(R.string.compression_complete),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "${state.formattedOriginalSize} → ${state.formattedCompressedSize}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            
            val reduction = if (state.originalSize > 0) ((state.originalSize - state.compressedSize).toFloat() / state.originalSize * 100).toInt() else 0
            if (reduction > 0) {
                Text(
                    "(-$reduction%)",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            if (state.warnings.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                var showWarningDialog by remember { mutableStateOf(false) }
                
                OutlinedButton(
                    onClick = { showWarningDialog = true },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
                    shape = RoundedCornerShape(24.dp)
                ) {
                     Icon(Icons.Outlined.Warning, contentDescription = null)
                     Spacer(modifier = Modifier.width(8.dp))
                     Text("${state.warnings.size} Warning${if (state.warnings.size > 1) "s" else ""} - Tap for Details", fontWeight = FontWeight.Bold)
                }
                
                if (showWarningDialog) {
                    AlertDialog(
                        onDismissRequest = { showWarningDialog = false },
                        icon = { Icon(Icons.Outlined.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error) },
                        title = { Text(stringResource(R.string.warning_details), fontWeight = FontWeight.Bold) },
                        text = {
                            Column {
                                state.warnings.forEach { warning ->
                                    Text("• $warning", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                    showWarningDialog = false
                                }
                            ) {
                                Text(stringResource(R.string.dismiss), fontWeight = FontWeight.Bold)
                            }
                        }
                    )
                }
            }

            // 1. 压缩后视频播放器卡片
            if (state.compressedUri != null) {
                Spacer(modifier = Modifier.height(20.dp))
                VideoPlayerPreview(
                    videoUri = state.compressedUri,
                    onOpenExternal = onPlayExternal
                )
            }

            // 2. 保存到原路径选项
            if (!state.originalRelativePath.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                stringResource(R.string.save_to_original_path),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                state.originalRelativePath,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = state.saveToOriginalDirectory,
                            onCheckedChange = { onToggleSaveToOriginal(it) }
                        )
                    }
                }
            }

            // 3. 删除原视频管理卡片
            if (state.selectedUri != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (state.isOriginalDeleted) {
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        }
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    if (state.isOriginalDeleted) {
                                        stringResource(R.string.original_deleted)
                                    } else {
                                        stringResource(R.string.delete_original)
                                    },
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (state.isOriginalDeleted) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onSurface
                                    }
                                )
                                if (!state.isOriginalDeleted && state.formattedOriginalSize.isNotBlank()) {
                                    Text(
                                        state.formattedOriginalSize,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            if (state.isOriginalDeleted) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(22.dp)
                                )
                            } else {
                                var showDeleteDialog by remember { mutableStateOf(false) }
                                OutlinedButton(
                                    onClick = { showDeleteDialog = true },
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = MaterialTheme.colorScheme.error
                                    ),
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        stringResource(R.string.delete_original),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }

                                if (showDeleteDialog) {
                                    AlertDialog(
                                        onDismissRequest = { showDeleteDialog = false },
                                        icon = {
                                            Icon(
                                                Icons.Default.Delete,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.error
                                            )
                                        },
                                        title = {
                                            Text(
                                                stringResource(R.string.delete_confirm_title),
                                                fontWeight = FontWeight.Bold
                                            )
                                        },
                                        text = { Text(stringResource(R.string.delete_confirm_message)) },
                                        confirmButton = {
                                            TextButton(
                                                onClick = {
                                                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                                    showDeleteDialog = false
                                                    onDeleteOriginal()
                                                },
                                                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                                            ) {
                                                Text(
                                                    stringResource(R.string.delete_original),
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        },
                                        dismissButton = {
                                            TextButton(onClick = { showDeleteDialog = false }) {
                                                Text(
                                                    stringResource(R.string.cancel),
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        }

                        if (!state.isOriginalDeleted) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Checkbox(
                                    checked = state.autoDeleteOriginal,
                                    onCheckedChange = { onToggleAutoDeleteOriginal(it) }
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    stringResource(R.string.auto_delete_original),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = onShare,
                    modifier = Modifier.weight(1f).heightIn(min = 56.dp).scaleOnPress(onShare),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        stringResource(R.string.share),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        maxLines = 1
                    )
                }
                
                FilledTonalButton(
                    onClick = onSave,
                    modifier = Modifier.weight(1f).heightIn(min = 56.dp).scaleOnPress(onSave),
                    enabled = !state.saveSuccess && !state.isSaving,
                    colors = if (state.isSaving) {
                        ButtonDefaults.filledTonalButtonColors(
                            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    } else {
                        ButtonDefaults.filledTonalButtonColors()
                    },
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    if (state.isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            stringResource(R.string.saving),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.5.sp,
                            maxLines = 1
                        )
                    } else if (state.saveSuccess) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            stringResource(R.string.saved),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            maxLines = 1
                        )
                    } else {
                        Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            stringResource(
                                if (state.saveToOriginalDirectory && !state.originalRelativePath.isNullOrBlank()) {
                                    R.string.save_to_original_path
                                } else if (state.customOutputTreeUri.isNullOrBlank()) {
                                    R.string.save_to_photos
                                } else {
                                    R.string.save_to_folder
                                }
                            ),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            maxLines = 1
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    onCompressAnother()
                }
            ) {
                Text(stringResource(R.string.compress_another_video), fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            TextButton(
                onClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    uriHandler.openUri("https://buymeacoffee.com/joshatticus")
                }
            ) {
                 Text(
                    stringResource(R.string.buy_coffee),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}
