package com.example.datingapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.datingapp.MatchProfile.MatchProfile
import com.example.datingapp.MatchProfile.profiles
import com.example.datingapp.Swipeable.Direction
import com.example.datingapp.Swipeable.rememberSwipeableCardState
import com.example.datingapp.Swipeable.swipableCard
import com.example.datingapp.ui.theme.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private val viewModel : MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            this.setKeepOnScreenCondition{
                viewModel.isLoading.value
            }
        }
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        setContent {
            DatingAppTheme {
                var selectedTabIndex = remember {
                    mutableStateOf(0)
                }

                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent
                )


                when(selectedTabIndex.value){
                    0 -> MainScreen(selectedTabIndex)
                    1 -> ComingSoon()
                    2 -> ComingSoon()
                }


                BackHandler(enabled = true, onBack = {
                    selectedTabIndex.value = 0
                })
            }
        }


    }
}


@Composable
fun MainScreen(selectedTabIndex: MutableState<Int>) {

    var swipeDirection = remember {
        mutableStateOf(0)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(15.dp, 55.dp, 15.dp, 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween) {
        TopAppBar(onSearchIconClick = { } , onMoreInfoIconClick = { })
        MainInfo(swipeDirection)
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
            verticalArrangement = Arrangement.Top) {
            BottomInfo(onCancelPersonClick = {swipeDirection.value = 1} , onLikePersonClick = {swipeDirection.value = 2})
        }
        BottomAppBar( onChatPersonClick = {selectedTabIndex.value = 1}, onClosePersonClick = {selectedTabIndex.value = 2})
    }
}//stringResource(R.string.logo)

@Composable
fun TopAppBar(modifier: Modifier = Modifier,onSearchIconClick : () -> Unit , onMoreInfoIconClick : () -> Unit){
    Row(modifier = modifier
        .fillMaxWidth()
        .height(50.dp)
        .padding(0.dp, 0.dp, 0.dp, 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround){
        Logo(Modifier.weight(3f))
        Row(modifier = Modifier.weight(0.7f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly){
            IconButton(modifier = Modifier
                .padding(0.dp)
                .size(28.dp), onClick = {onSearchIconClick()}) {
                Icon(modifier = Modifier.size(28.dp),
                    imageVector = Icons.Default.Search,
                    tint = Color.Black,
                    contentDescription = ""
                )
            }

            IconButton(modifier = Modifier
                .padding(0.dp)
                .size(28.dp), onClick = {onMoreInfoIconClick()}) {
                Icon(
                    modifier = Modifier.size(26.dp),
                    imageVector = Icons.Default.MoreVert,
                    tint = Color.Black,
                    contentDescription = ""
                )
            }
        }
    }
}



@Composable
fun BottomInfo(onCancelPersonClick : () -> Unit, onLikePersonClick : () -> Unit ){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(120.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically) {

        Box(modifier = Modifier.size(70.dp).weight(1f))

        Box(
            modifier = Modifier
                .shadow(
                    color = CancelButtonMainColor,
                    borderRadius = 50.dp,
                    blurRadius = 12.dp,
                    offsetY = 0.dp,
                    offsetX = 0.dp,
                    spread = 0.5f.dp,
                )
                .padding(0.dp)
                .weight(1f)
                .clip(RoundedCornerShape(50.dp))
                .background(CancelButtonMainColor)
                .size(70.dp)
                .clickable { onCancelPersonClick() },
            contentAlignment = Alignment.Center
        ) {

            Icon(
                modifier = Modifier
                    .size(42.dp),
                imageVector = Icons.Default.Close,
                tint = Color.White,
                contentDescription = ""
            )
        }

        Box(modifier = Modifier.size(70.dp).weight(1f))

        Box(
            modifier = Modifier
                .shadow(
                    color = LikeButtonMainColor,
                    borderRadius = 50.dp,
                    blurRadius = 12.dp,
                    offsetY = 0.dp,
                    offsetX = 0.dp,
                    spread = 0.5f.dp,
                )
                .weight(1f)
                .padding(0.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(LikeButtonMainColor)
                .size(70.dp)
                .clickable { onLikePersonClick() },
            contentAlignment = Alignment.Center
        ) {

            Icon(
                modifier = Modifier
                    .size(42.dp),
                imageVector = Icons.Default.Favorite,
                tint = Color.White,
                contentDescription = ""
            )
        }

        Box(modifier = Modifier.size(70.dp).weight(1f))

    }
}

@Composable
fun BottomAppBar(onClosePersonClick : () -> Unit , onChatPersonClick : () -> Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .clip(RoundedCornerShape(25.dp)),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically) {

        Box(modifier = Modifier
            .shadow(
                color = ChatButtonMainColor,
                borderRadius = 15.dp,
                blurRadius = 6.dp,
                offsetY = 0.dp,
                offsetX = 0.dp,
                spread = 0.2f.dp,
            )
            .padding(0.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(ChatButtonMainColor)
            .height(35.dp)
            .width(70.dp)
            .clickable { onClosePersonClick ()},
            contentAlignment = Alignment.Center) {
            Icon(
                modifier = Modifier
                    .size(25.dp)
                    ,
                imageVector = ImageVector.vectorResource(id = R.drawable.chat),
                tint = Color.White,
                contentDescription = ""
            )
        }


        Box(modifier = Modifier
            .shadow(
                color = CloseButtonMainColor,
                borderRadius = 15.dp,
                blurRadius = 6.dp,
                offsetY = 0.dp,
                offsetX = 0.dp,
                spread = 0.2f.dp,
            )
            .padding(0.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(CloseButtonMainColor)
            .height(35.dp)
            .width(70.dp)
            .clickable { onChatPersonClick ()},
            contentAlignment = Alignment.Center) {
            Icon(
                modifier = Modifier
                    .size(28.dp),
                imageVector = Icons.Default.Close,
                tint = Color.White,
                contentDescription = ""
            )
        }


    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainInfo(swipeDirection: MutableState<Int>) {

    val scope = rememberCoroutineScope()

    val states = profiles
        .map { it to rememberSwipeableCardState() }

    when (swipeDirection.value){
        1-> scope.launch {
            val last = states.reversed()
                .firstOrNull {
                    it.second.offset.value == Offset(0f, 0f)
                }?.second
            swipeDirection.value = 0
            last?.swipe(Direction.Left)
        }
        2-> scope.launch {
            val last = states.reversed()
                .firstOrNull {
                    it.second.offset.value == Offset(0f, 0f)
                }?.second
            swipeDirection.value = 0
            last?.swipe(Direction.Right)
        }
    }

    Card(
        modifier = Modifier
            .height(420.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .padding(0.dp, 0.dp, 0.dp, 0.dp)
            .clickable { },
        elevation = 5.dp
    ) {
        states.forEach { (matchProfile, state) ->
            if (state.swipedDirection == null) {
                PersonCard(
                    modifier = Modifier
                        .fillMaxSize()
                        .swipableCard(
                            state = state,
                            blockedDirections = listOf(Direction.Down),
                            onSwiped = {

                            },
                            onSwipeCancel = {

                            }
                        ),
                    matchProfile = matchProfile
                )
            }
        }

    }
}

@Composable
fun PersonCard(modifier : Modifier = Modifier, matchProfile : MatchProfile){
    Box(modifier = modifier.fillMaxSize()){
        Image(modifier = Modifier.fillMaxSize(),painter = painterResource(id = matchProfile.drawableResId),
            contentScale = ContentScale.Crop,
            contentDescription = "",)

        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom) {
            Text(modifier = Modifier.padding(18.dp,0.dp,0.dp,18.dp) ,text = matchProfile.name, color = Color.White, fontWeight = FontWeight.W600 , fontSize = 32.sp)
        }
    }

}

@Composable
fun Logo(modifier: Modifier){
    Text(modifier = modifier.padding(0.dp) ,text = "Cupidonius", color = BackgroundMainColor, fontWeight = FontWeight.W500 , fontSize = 24.sp)
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DatingAppTheme {
        Greeting("Android")
    }
}

