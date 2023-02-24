package com.example.feature_detail.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.theme.sizing
import com.example.designsystem.theme.spacing
import com.example.feature_detail.R
import com.example.feature_detail.detail.DetailAction.ShowPicture
import com.example.feature_detail.detail.DetailAction.TryAgain
import com.example.feature_detail.navigation.keyImage
import com.example.ui.HeightSpacer
import com.example.ui.WidthSpacer
import com.example.ui.base.BaseEffect.Navigate
import com.example.ui.component.AppAsyncImage
import com.example.ui.component.AppEffectObserver
import com.example.ui.dynamicShimmer

@Composable
fun DetailRoute(
    viewModel: DetailViewModel = hiltViewModel(),
    onImageSelect: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    AppEffectObserver(
        effectFlow = viewModel.effectFlow,
        onEffectReceived = { effect ->
            if (effect is Navigate) {
                effect.params?.let { bundle ->
                    bundle.getString(keyImage)?.let { url ->
                        onImageSelect(url)
                    }
                }
            }
        }
    )

    if (uiState.isObjectDetailLoading) {
        DetailScreenShimmer()
    } else {
        DetailScreen(
            uiState = uiState,
            onAction = viewModel::submitAction,
        )
    }
}

@Composable
internal fun DetailScreen(
    uiState: DetailUiState,
    onAction: (DetailAction) -> Unit,
) {
    uiState.objectDetail?.let { detail ->
        val showHeaderSection by derivedStateOf {
            detail.name.isNotBlank() || detail.imageUrl != null
        }

        val showOverallSection by derivedStateOf {
            detail.department.isNotBlank()
                    || detail.artistName.isNotBlank()
                    || detail.additionalImageUrls != null
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            if (showHeaderSection) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primary)
                        .padding(MaterialTheme.spacing.medium)
                        .testTag(stringResource(R.string.tag_header)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    detail.imageUrl?.let { url ->
                        AppAsyncImage(
                            imageUrl = url,
                            modifier = Modifier
                                .size(MaterialTheme.sizing.small)
                                .clip(CircleShape),
                            backgroundColor = MaterialTheme.colors.onPrimary,
                            onClick = {
                                onAction(ShowPicture(url))
                            }
                        )

                        WidthSpacer(value = MaterialTheme.spacing.medium)
                    }

                    Text(
                        text = detail.name,
                        color = MaterialTheme.colors.onPrimary,
                        style = MaterialTheme.typography.h5
                    )
                }
            }

            if (showOverallSection) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.surface)
                        .padding(MaterialTheme.spacing.medium)
                        .testTag(stringResource(R.string.tag_overall)),
                ) {
                    DetailItem(
                        label = stringResource(R.string.label_department),
                        content = detail.department,
                        contentColor = MaterialTheme.colors.onSurface
                    )

                    DetailItem(
                        label = stringResource(R.string.label_artist_name),
                        content = detail.artistName,
                        contentColor = MaterialTheme.colors.onSurface
                    )

                    detail.additionalImageUrls?.let { urls ->
                        HeightSpacer(value = MaterialTheme.spacing.medium)

                        LazyRow(modifier = Modifier.fillMaxWidth()) {
                            items(urls) { url ->
                                AppAsyncImage(
                                    imageUrl = url,
                                    modifier = Modifier
                                        .size(MaterialTheme.sizing.small)
                                        .clip(MaterialTheme.shapes.medium),
                                    onClick = {
                                        onAction(ShowPicture(url))
                                    }
                                )

                                WidthSpacer(value = MaterialTheme.spacing.small)
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium),
            ) {
                DetailItem(
                    label = stringResource(R.string.label_accession_number),
                    content = detail.accessionNumber
                )

                DetailItem(
                    label = stringResource(R.string.label_accession_year),
                    content = detail.accessionYear
                )

                DetailItem(
                    label = stringResource(R.string.label_artist_role),
                    content = detail.artistRole
                )

                DetailItem(
                    label = stringResource(R.string.label_classification),
                    content = detail.classification
                )

                DetailItem(
                    label = stringResource(R.string.label_title),
                    content = detail.title
                )

                DetailItem(
                    label = stringResource(R.string.label_repository),
                    content = detail.repository
                )
            }
        }
    } ?: run {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.onBackground,
                        shape = MaterialTheme.shapes.large
                    )
                    .clickable { onAction(TryAgain) }
                    .padding(
                        vertical = MaterialTheme.spacing.small,
                        horizontal = MaterialTheme.spacing.medium
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.label_internet_problem_massage),
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Composable
private fun DetailItem(
    label: String,
    content: String,
    contentColor: Color = MaterialTheme.colors.primary
) {
    if (content.isNotBlank()) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )

            HeightSpacer(value = MaterialTheme.spacing.extraSmall)

            Text(
                text = content,
                style = MaterialTheme.typography.body1,
                color = contentColor
            )

            HeightSpacer(value = MaterialTheme.spacing.medium)
        }
    }
}

@Composable
private fun DetailScreenShimmer() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
                .padding(MaterialTheme.spacing.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(MaterialTheme.sizing.small)
                    .clip(CircleShape)
                    .dynamicShimmer(),
            )

            WidthSpacer(value = MaterialTheme.spacing.medium)

            Box(
                modifier = Modifier
                    .height(MaterialTheme.spacing.medium)
                    .width(MaterialTheme.sizing.medium)
                    .clip(MaterialTheme.shapes.medium)
                    .dynamicShimmer(),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
                .padding(MaterialTheme.spacing.medium),
        ) {
            DetailItemShimmer()

            HeightSpacer(value = MaterialTheme.spacing.medium)

            DetailItemShimmer()

            HeightSpacer(value = MaterialTheme.spacing.medium)

            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .size(MaterialTheme.sizing.small)
                        .clip(MaterialTheme.shapes.medium)
                        .dynamicShimmer(),
                )

                WidthSpacer(value = MaterialTheme.spacing.small)

                Box(
                    modifier = Modifier
                        .size(MaterialTheme.sizing.small)
                        .clip(MaterialTheme.shapes.medium)
                        .dynamicShimmer(),
                )

                WidthSpacer(value = MaterialTheme.spacing.small)

                Box(
                    modifier = Modifier
                        .size(MaterialTheme.sizing.small)
                        .clip(MaterialTheme.shapes.medium)
                        .dynamicShimmer(),
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
        ) {
            DetailItemShimmer()

            HeightSpacer(value = MaterialTheme.spacing.medium)

            DetailItemShimmer()

            HeightSpacer(value = MaterialTheme.spacing.medium)

            DetailItemShimmer()

            HeightSpacer(value = MaterialTheme.spacing.medium)

            DetailItemShimmer()

            HeightSpacer(value = MaterialTheme.spacing.medium)

            DetailItemShimmer()

            HeightSpacer(value = MaterialTheme.spacing.medium)
        }
    }
}

@Composable
private fun DetailItemShimmer() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .height(MaterialTheme.spacing.medium)
                .width(MaterialTheme.sizing.medium)
                .clip(MaterialTheme.shapes.medium)
                .dynamicShimmer(),
        )

        HeightSpacer(value = MaterialTheme.spacing.extraSmall)

        Box(
            modifier = Modifier
                .height(MaterialTheme.spacing.medium)
                .width(MaterialTheme.sizing.small)
                .clip(MaterialTheme.shapes.medium)
                .dynamicShimmer(),
        )
    }
}