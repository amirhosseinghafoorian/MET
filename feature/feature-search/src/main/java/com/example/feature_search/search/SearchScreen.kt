package com.example.feature_search.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common.AppConstants.keyId
import com.example.designsystem.theme.sizing
import com.example.designsystem.theme.spacing
import com.example.feature_search.R
import com.example.feature_search.search.SearchAction.OnObjectSelect
import com.example.feature_search.search.SearchAction.OnUpdateTextField
import com.example.ui.base.BaseEffect.Navigate
import com.example.ui.base.BaseEffect.ShowSnackBar
import com.example.ui.component.AppEffectObserver
import com.example.ui.component.AppTextField
import com.example.ui.component.showAppSnackBar

@Composable
fun SearchRoute(
    scaffoldState: ScaffoldState,
    viewModel: SearchViewModel = hiltViewModel(),
    onSearchDetail: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    AppEffectObserver(
        effectFlow = viewModel.effectFlow,
        onEffectReceived = { effect ->
            if (effect is Navigate) {
                effect.params?.let { bundle ->
                    val id = bundle.getInt(keyId)

                    onSearchDetail(id)
                }
            } else if (effect is ShowSnackBar) {
                scaffoldState.showAppSnackBar(context, effect.snackBar)
            }
        }
    )

    SearchScreen(
        focusManager = focusManager,
        uiState = uiState,
        onAction = viewModel::submitAction
    )
}

@Composable
private fun SearchScreen(
    focusManager: FocusManager,
    uiState: SearchUiState,
    onAction: (SearchAction) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium),
        columns = GridCells.Adaptive(MaterialTheme.sizing.medium),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            AppTextField(
                state = uiState.searchFieldState,
                onValueChange = { text ->
                    onAction(OnUpdateTextField(text))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                placeholder = stringResource(R.string.label_search_place_holder),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                },
                showError = false
            )
        }

        if (uiState.isSearchLoading) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(MaterialTheme.sizing.medium),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        } else {
            items(
                items = uiState.objectIds,
                key = { it }
            ) { id ->
                ObjectIdItem(id) {
                    onAction(OnObjectSelect(id))
                }
            }
        }
    }
}

@Composable
private fun ObjectIdItem(
    id: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colors.surface)
            .clickable { onClick() }
            .padding(MaterialTheme.spacing.medium),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = id.toString(),
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )
    }
}