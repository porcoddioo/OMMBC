package com.example.alessandro.testbottom;

import android.util.Log;

public class LaTex {

    private String text;

    public LaTex(String text) {
        this.text = text;
    }

    public String get() {
        return convert();
    }

    public void set(String text) {
        this.text = text;
    }

    private String convert() {
        String converted = text;
        int flag = 0;
        for(int i = 0 ; i < converted.length() ; i++) {
            if((converted.charAt(i)=='$')) {
                if(flag==0) {
                    if(converted.charAt(i+1)!='$') {
                        // sostituisci con \(
                        converted = new String(converted.substring(0, i) + "\\(" + converted.substring(i + 1, converted.length()));
                        flag = 1;
                    } else i=i+1;
                }
                else if (flag==1) {
                    //sostituisci con \)
                    converted = new String(converted.substring(0,i) + "\\)"+ converted.substring(i+1,converted.length()));
                    flag = 0;
                }
            }
        }
        return converted;
    }
}
