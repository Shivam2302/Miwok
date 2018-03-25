package com.example.android.miwok;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shivam on 18/12/17.
 */

public class WordsAdapter extends ArrayAdapter<Words> {

    public WordsAdapter(@NonNull Context context, ArrayList<Words> word) {
        super(context, 0, word);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(convertView==null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_layout,parent,false);

        Words currentWord = getItem(position);

        ImageView image = (ImageView) listItemView.findViewById(R.id.image);
        if(currentWord.getCategory().equals("phrase")){
            image.setVisibility(View.GONE);
        }

        else{
            image.setImageResource(currentWord.getImageResourceId());
        }

        TextView miwok = (TextView)listItemView.findViewById(R.id.miwok);
        miwok.setText(currentWord.getMiwokTranslation());

        TextView english = (TextView)listItemView.findViewById(R.id.engish);
        english.setText(currentWord.getEnglishTranslation());

        LinearLayout colorChange = (LinearLayout) listItemView.findViewById(R.id.color_change);

        if(currentWord.getCategory().equals("number"))
            colorChange.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.category_numbers));

        else if(currentWord.getCategory().equals("color"))
            colorChange.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.category_colors));

        else if(currentWord.getCategory().equals("family"))
            colorChange.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.category_family));

        else if(currentWord.getCategory().equals("phrase"))
            colorChange.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.category_phrases));

        return listItemView;
    }
}
