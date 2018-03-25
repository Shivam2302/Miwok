package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_GAIN;
import static android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;
import static android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorFragment extends Fragment {


    final ArrayList<Words> words = new ArrayList<Words>();

    MediaPlayer mp;
    AudioManager am;
    AudioManager.OnAudioFocusChangeListener onfocusChange = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {

            if(focusChange==AUDIOFOCUS_GAIN||focusChange==AUDIOFOCUS_GAIN_TRANSIENT){
                Log.e("check","gain");
                if(mp!=null)
                    mp.start();
            }
            else if(focusChange==AUDIOFOCUS_LOSS_TRANSIENT||focusChange==AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                Log.e("check","loss");
                if(mp!=null)
                    mp.pause();
            }
            else if(focusChange==AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.word_list,container,false);

        ListView lv = (ListView) rootview.findViewById(R.id.list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                releaseMediaPlayer();

                am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
                int result = am.requestAudioFocus(onfocusChange,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result==AUDIOFOCUS_REQUEST_GRANTED){

                    mp = MediaPlayer.create(getActivity(), words.get(position).getAudioResourseId());
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            releaseMediaPlayer();
                        }
                    });

                    mp.start();
                }

            }
        });

        words.add(new Words("Black","kululli",R.drawable.color_black,"color", R.raw.color_black));
        words.add(new Words("Brown","ṭakaakki",R.drawable.color_brown,"color", R.raw.color_brown));
        words.add(new Words("Dusty_yellow","ṭopiisә",R.drawable.color_dusty_yellow,"color", R.raw.color_dusty_yellow));
        words.add(new Words("Gray","ṭopoppi",R.drawable.color_gray,"color", R.raw.color_gray));
        words.add(new Words("Green","chokokki",R.drawable.color_green,"color", R.raw.color_green));
        words.add(new Words("Mustard_yellow","chiwiiṭә",R.drawable.color_mustard_yellow,"color", R.raw.color_mustard_yellow));
        words.add(new Words("Red","weṭeṭṭi",R.drawable.color_red,"color", R.raw.color_red));
        words.add(new Words("White","kelelli",R.drawable.color_white,"color", R.raw.color_white));

        WordsAdapter itemsAdapter = new WordsAdapter(getActivity(), words);
        lv.setAdapter(itemsAdapter);

        return rootview;

    }


    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer(){
        if(mp!=null){
            mp.release();
            mp=null;
            am.abandonAudioFocus(onfocusChange);
        }

    }

}
