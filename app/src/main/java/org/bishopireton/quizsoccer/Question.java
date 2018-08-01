package org.bishopireton.quizsoccer;

public class Question {

    private int mImgFlagId;
    private int mTextResId;
    private boolean mAnswerTrue;

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    private boolean used;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    private int points;

    public int getImgFlagId() {
        return mImgFlagId;
    }

    public void setImgFlagId(int imgFlagId) {
        mImgFlagId = imgFlagId;
    }


    public Question (int textResId, boolean answerTrue, int imgFlagId) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mImgFlagId = imgFlagId;
        points = (int)(Math.random()*7)+3;
        used = false;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
