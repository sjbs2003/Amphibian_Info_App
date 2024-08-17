package com.example.amphibianinfoapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibianinfoapp.R
import com.example.amphibianinfoapp.model.AmphibianData


@Composable
fun HomeScreen(
    amphibianUiState: AmphibianUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (amphibianUiState) {
        is AmphibianUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())

        is AmphibianUiState.Success -> AmphibianList(amphibianUiState.data)

        is AmphibianUiState.Error -> ErrorScreen(retryAction , modifier = modifier.fillMaxSize())
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}


@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_connection_error), 
            contentDescription = "error"
        )
        Text(stringResource(R.string.failed_to_load), modifier = modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}



@Composable
fun AmphibianList(amphibians: List<AmphibianData>) {
    LazyColumn {
        items(amphibians){ item ->
            AmphibianCard(item, modifier = Modifier)
        }
    }
}

@Composable
fun AmphibianCard(
    amphibian: AmphibianData,
    modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = " ${amphibian.name} (${amphibian.type})",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = modifier.height(8.dp))
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(amphibian.image)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = stringResource(R.string.amphibian_photo),
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxWidth()
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewLoadingScreen() {
    LoadingScreen(modifier = Modifier.fillMaxSize())
}

@Preview(showBackground = true)
@Composable
fun PreviewErrorScreen() {
    ErrorScreen(retryAction = {})
}

@Preview(showBackground = true)
@Composable
fun PreviewAmphibianList() {
    AmphibianList(
        amphibians = listOf(
            AmphibianData("Great Basin Spadefoot", "Toad","blah blah blah", "https://developer.android.com/codelabs/basic-android-kotlin-compose-amphibians-app/img/great-basin-spadefoot.png"),
            AmphibianData("Roraima Bush Toad", "Toad","blah blah blah", "https://developer.android.com/codelabs/basic-android-kotlin-compose-amphibians-app/img/roraima-bush-toad.png")
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAmphibianCard() {
    AmphibianCard(
        amphibian = AmphibianData(
            name = "Pacific Chorus Frog",
            type = "Frog",
            description = "blah blah blah",
            image = "https://developer.android.com/codelabs/basic-android-kotlin-compose-amphibians-app/img/pacific-chorus-frog.png"
        )
    )
}
