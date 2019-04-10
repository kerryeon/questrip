package com.levelup.Questrip.data;

import android.app.Activity;
import android.widget.ImageView;

import com.levelup.Questrip.common.ImageManager;

import java.util.Comparator;

/**
 * 퀘스트 제출물에 대한 자세한 정보를 담고 있는 클래스입니다.
 *
 * 담당자: 김호
 *
 * 역할: 퀘스트 제출물에 대한 자세한 정보를 관리합니다.
 */
public final class Submission {

    private long id;
    private long user_id;
    private String nickname;
    private String imagePath;
    private long date;
    private long rating;

    /**
     * 제출물의 고유번호를 불러옵니다.
     * @return 제출물의 고유번호
     */
    public final long getID() {
        return id;
    }

    /**
     * 제출한 자의 고유번호를 불러옵니다.
     * @return 제출한 자의 고유번호
     */
    public final long getUserID() {
        return user_id;
    }

    /**
     * 제출한 자의 닉네임을 불러옵니다.
     * @return 제출한 자의 닉네임
     */
    public final String getNickname() {
        return nickname;
    }

    /**
     * 제출물의 이미지 경로를 불러옵니다.
     * @return 제출물의 이미지 경로
     */
    public final String getImagePath() {
        return imagePath;
    }

    /**
     * 제출일자를 불러옵니다.
     * @return 제출일자
     */
    public final long getDate() {
        return date;
    }

    /**
     * 추천수를 불러옵니다.
     * @return 추천수
     */
    public final long getRating() {
        return rating;
    }

    /**
     * 입축된 이미지를 불러옵니다.
     * 다운로드에 실패한 경우는 무시합니다.
     * @param activity 현재 액티비티
     * @param imageView 이미지 레이아웃
     */
    public final void loadImage(Activity activity, ImageView imageView) {
        ImageManager.load(activity, imageView, getImagePath());
    }

    /**
     * Builder 를 위해, 새로운 퀘스트 제출물 정보 객체를 생성합니다.
     */
    private Submission() {
        nickname = "";
        date = 0;
        rating = 0;
    }

    /**
     * 퀘스트 제출물 정보에 필요한 양식을 만들어주는 Builder 입니다.
     */
    public static class Builder {

        private Submission submission;

        /**
         * Builder 를 초기화합니다.
         */
        public Builder() {
            submission = new Submission();
        }

        /**
         * 제출물의 고유번호를 설정합니다.
         * @param value: 제출물의 고유번호
         * @return Builder
         */
        public Builder setID(long value) {
            submission.id = value;
            return this;
        }

        /**
         * 제출한 자의 고유번호를 설정합니다.
         * @param value: 제출한 자의 고유번호
         * @return Builder
         */
        public Builder setUserID(long value) {
            submission.user_id = value;
            return this;
        }

        /**
         * 제출한 자의 닉네임을 설정합니다.
         * @param value: 제출한 자의 닉네임
         * @return Builder
         */
        public Builder setNickname(String value) {
            submission.nickname = value;
            return this;
        }

        /**
         * 제출물의 이미지 경로를 설정합니다.
         * @param value: 제출물의 이미지 경로
         * @return Builder
         */
        public Builder setImagePath(String value) {
            submission.imagePath = value;
            return this;
        }

        /**
         * 제출물의 제출일자를 설정합니다.
         * @param value: 제출일자
         * @return Builder
         */
        public Builder setDate(long value) {
            submission.date = value;
            return this;
        }

        /**
         * 제출물의 추천수를 설정합니다.
         * @param value: 추천수
         * @return Builder
         */
        public Builder setRating(long value) {
            submission.rating = value;
            return this;
        }

        /**
         * 완성된 퀘스트 제출물 정보를 반환합니다.
         * @return 퀘스트 제출물 정보
         */
        public final Submission create() {
            return submission;
        }

    }

    /**
     * 퀘스트 제출물을 추천순으로 정렬하는 연산을 담당하는 클래스입니다.
     *
     * 담당자: 김호, 정홍기
     */
    public static class SubmissionRatingComparator implements Comparator<Submission> {

        /**
         * 새로운 비교 연산 객체를 생성합니다.
         */
        public SubmissionRatingComparator() {

        }

        /**
         * 서로 다른 두 퀘스트 제출물의 추천수를 비교합니다.
         * 동점인 경우, 최신순이 우선됩니다.
         * @param o1 퀘스트 제출물
         * @param o2 다른 퀘스트 제출물
         * @return o1 이 추천수가 더 낮다면 true 를 반환합니다.
         */
        @Override
        public int compare(Submission o1, Submission o2) {
            return o2.getRating() != o1.getRating()
                    ? (int) (o2.getRating() - o1.getRating())
                    : (int) (o2.getDate() - o1.getDate());
        }

    }

    /**
     * 퀘스트 제출물을 최신순으로 정렬하는 연산을 담당하는 클래스입니다.
     *
     * 담당자: 김호, 정홍기
     */
    public static class SubmissionDateComparator implements Comparator<Submission> {

        /**
         * 새로운 비교 연산 객체를 생성합니다.
         */
        public SubmissionDateComparator() {

        }

        /**
         * 서로 다른 두 퀘스트 제출물의 제출일자를 비교합니다.
         * 동시간대인 경우, 추천순이 우선됩니다.
         * @param o1 퀘스트 제출물
         * @param o2 다른 퀘스트 제출물
         * @return o1 이 더 나중에 제출되었다면 true 를 반환합니다.
         */
        @Override
        public int compare(Submission o1, Submission o2) {
            return o2.getDate() != o1.getDate()
                    ? (int) (o2.getDate() - o1.getDate())
                    : (int) (o2.getRating() - o1.getRating());
        }

    }

}
