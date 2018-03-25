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
public class NumberFragment extends Fragment {


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


        words.add(new Words("one","lutti", R.drawable.number_one,"number", R.raw.number_one));
        words.add(new Words("two","otiiko", R.drawable.number_two,"number", R.raw.number_two));
        words.add(new Words("three","tolookosu", R.drawable.number_three,"number", R.raw.number_three));
        words.add(new Words("four","oyyisa", R.drawable.number_four,"number", R.raw.number_four));
        words.add(new Words("five","massokka", R.drawable.number_five,"number", R.raw.number_five));
        words.add(new Words("six","temmokka", R.drawable.number_six,"number", R.raw.number_six));
        words.add(new Words("seven","kenekaku", R.drawable.number_seven,"number", R.raw.number_seven));
        words.add(new Words("eight","kawinta", R.drawable.number_eight,"number", R.raw.number_eight));
        words.add(new Words("nine","wo'e", R.drawable.number_nine,"number", R.raw.number_nine));
        words.add(new Words("ten","na'aacha", R.drawable.number_ten,"number", R.raw.number_ten));



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
