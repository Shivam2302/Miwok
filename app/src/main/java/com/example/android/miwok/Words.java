package com.example.android.miwok;

/**
 * Created by shivam on 18/12/17.
 */

public class Words {

    private String englishTranslation;
    private String miwokTranslation;
    private int imageResourceId;
    private int audioResourceId;
    private String catogory;

    Words(String e, String m, String c, int a){
        englishTranslation = e;
        miwokTranslation = m;
        catogory = c;
        audioResourceId=a;
    }


    Words(String e, String m, int r , String c ,int a){
        englishTranslation = e;
        miwokTranslation = m;
        imageResourceId = r;
        catogory = c;
        audioResourceId = a;
    }


    public String getEnglishTranslation(){
        return this.englishTranslation;
    }

    public String getMiwokTranslation(){
        return this.miwokTranslation;
    }

    public int getImageResourceId(){ return imageResourceId; }

    public String getCategory(){ return catogory; }

    public int getAudioResourseId(){ return audioResourceId ; }



}
