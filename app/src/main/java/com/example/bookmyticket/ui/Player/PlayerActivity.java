package com.example.bookmyticket.ui.Player;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bookmyticket.R;
import com.example.bookmyticket.util.Constants;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayerActivity extends YouTubeBaseActivity {

    YouTubePlayerView playerView;
    Button doneButton;
    YouTubePlayerFragment youtubeFragment;
    private int RECOVERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        init();

        playVideo(getIntent().getExtras().getString("videoKey"),youtubeFragment);
        doneButton.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });
    }

    private void init() {
        doneButton = findViewById(R.id.done_button);
        youtubeFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtubeFragment);
    }

    public void playVideo(final String videoId, YouTubePlayerFragment youTubePlayerFragment) {
        //initialize youtube player view
        youTubePlayerFragment.initialize(Constants.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(videoId);

                youTubePlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
                    @Override
                    public void onPlaying() {
                        doneButton.setVisibility(View.GONE);
                    }

                    @Override
                    public void onPaused() {
                        doneButton.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onStopped() {
                        doneButton.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onBuffering(boolean b) {
                        doneButton.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onSeekTo(int i) {
                        doneButton.setVisibility(View.GONE);

                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult errorReason) {
                if (errorReason.isUserRecoverableError()) {
                    errorReason.getErrorDialog(PlayerActivity.this, RECOVERY_REQUEST).show();
                } else {
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
