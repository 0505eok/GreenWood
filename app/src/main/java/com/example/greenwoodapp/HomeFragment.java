package com.example.greenwoodapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.greenwoodapp.Call.CallActivity;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.cloud.speech.v1.SpeechSettings;

import java.io.FileInputStream;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener{
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.call_main, container, false);

        Button btn = v.findViewById(R.id.call1);
        btn.setOnClickListener(this);
        return v;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        System.out.println("asdf");
        try {
            analyzeCall(v);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void analyzeCall(View v) throws Exception {
        AssetManager am = getResources().getAssets();
        AssetFileDescriptor fileDescriptor = am.openFd("google-stt.json");
        FileInputStream credentialStream = fileDescriptor.createInputStream();
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialStream);
        FixedCredentialsProvider credentialsProvider = FixedCredentialsProvider.create(credentials);
        try (SpeechClient speechClient = SpeechClient.create(
                SpeechSettings.newBuilder()
                        .setCredentialsProvider(credentialsProvider)
                        .build()
        )) {
            String gcsUri = "gs://greenwoodapp/1.flac";
            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.FLAC)
                    .setSampleRateHertz(44100)
                    .setLanguageCode("ko-KR")
                    .build();
            RecognitionAudio audio = RecognitionAudio.newBuilder().setUri(gcsUri).build();
            RecognizeResponse response = speechClient.recognize(config, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();
            Log.d("TEST",response.toString());

            for (SpeechRecognitionResult result : results) {
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                System.out.printf("Transcription: %s%n", alternative.getTranscript());
            }
        }
    }


}