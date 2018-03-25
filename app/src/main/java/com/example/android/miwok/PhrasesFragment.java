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
public class PhrasesFragment extends Fragment {

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

        View rootview = inflater.inflate(R.layout.word_list, container, false);

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


        words.add(new Words("Are_you_coming?","әәnәs'aa?","phrase", R.raw.phrase_are_you_coming));
        words.add(new Words("Come here.","әnni'nem","phrase", R.raw.phrase_come_here));
        words.add(new Words("How are you feeling?","michәksәs?","phrase", R.raw.phrase_how_are_you_feeling));
        words.add(new Words("I m coming.","әәnәm","phrase", R.raw.phrase_im_coming));
        words.add(new Words("Yes I m coming.","hәә’ әәnәm","phrase", R.raw.phrase_yes_im_coming));
        words.add(new Words("I m feeing good.","kuchi achit","phrase", R.raw.phrase_im_feeling_good));
        words.add(new Words("Lets go!","yoowutis!","phrase", R.raw.phrase_lets_go));
        words.add(new Words("My name is...","oyaaset...","phrase", R.raw.phrase_my_name_is));
        words.add(new Words("What is your name?","tinnә oyaase'nә?","phrase", R.raw.phrase_what_is_your_name));
        words.add(new Words("Where are you going?","minto wuksus?","phrase", R.raw.phrase_where_are_you_going));

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
