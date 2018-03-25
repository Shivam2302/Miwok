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
public class FamilyFragment extends Fragment {

    ArrayList<Words> words = new ArrayList<Words>();

    MediaPlayer mp;
    AudioManager am;
    AudioManager.OnAudioFocusChangeListener onfocusChange = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {

            if (focusChange == AUDIOFOCUS_GAIN || focusChange == AUDIOFOCUS_GAIN_TRANSIENT) {
                Log.e("check", "gain");
                if (mp != null)
                    mp.start();
            } else if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                Log.e("check", "loss");
                if (mp != null)
                    mp.pause();
            } else if (focusChange == AUDIOFOCUS_LOSS) {
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
                int result = am.requestAudioFocus(onfocusChange, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AUDIOFOCUS_REQUEST_GRANTED) {

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

        words.add(new Words("Daughter", "tune", R.drawable.family_daughter, "family", R.raw.family_daughter));
        words.add(new Words("Father", "әpә", R.drawable.family_father, "family", R.raw.family_father));
        words.add(new Words("Grandfather", "paapa", R.drawable.family_grandfather, "family", R.raw.family_grandfather));
        words.add(new Words("Grandmother", "ama", R.drawable.family_grandmother, "family", R.raw.family_grandmother));
        words.add(new Words("Mother", "әṭa", R.drawable.family_mother, "family", R.raw.family_mother));
        words.add(new Words("Old Brother", "taachi", R.drawable.family_older_brother, "family", R.raw.family_older_brother));
        words.add(new Words("Old Sister", "teṭe", R.drawable.family_older_sister, "family", R.raw.family_older_sister));
        words.add(new Words("Son", "angsi", R.drawable.family_son, "family", R.raw.family_son));
        words.add(new Words("Younger Sister", "kolliti", R.drawable.family_younger_sister, "family", R.raw.family_younger_sister));
        words.add(new Words("Younger Brother", "chalitti", R.drawable.family_younger_brother, "family", R.raw.family_younger_brother));

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
