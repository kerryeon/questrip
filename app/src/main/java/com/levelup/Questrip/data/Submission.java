package com.levelup.Questrip.data;

import java.util.Comparator;

/**
 * 퀘스트 제출물에 대한 자세한 정보를 담고 있는 클래스입니다.
 *
 * 담당자: 김호
 *
 * 역할: 퀘스트 제출물에 대한 자세한 정보를 관리합니다.
 */
public final class Submission {

    private String nickname;
    private byte[] image;
    private long date;
    private long rating;

    /**
     * 제출한 자의 닉네임을 불러옵니다.
     * @return 제출한 자의 닉네임
     */
    public final String getNickname() {
        return nickname;
    }

    /**
     * 입축된 이미지를 불러옵니다.
     * @return 압축된 이미지
     */
    public final byte[] getImage() {
        return image;
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
     * Builder 를 위해, 새로운 퀘스트 제출물 정보 객체를 생성합니다.
     */
    private Submission() {
        nickname = "";
        image = null;
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
         * 제출한 자의 닉네임을 설정합니다.
         * @param value: 제출한 자의 닉네임
         * @return Builder
         */
        public Builder setNickname(String value) {
            submission.nickname = value;
            return this;
        }

        /**
         * 제출물의 입축된 이미지를 설정합니다.
         * @param value: 입축된 이미지
         * @return Builder
         */
        public Builder setImage(byte[] value) {
            submission.image = value;
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
         * @param o1 퀘스트 제출물
         * @param o2 다른 퀘스트 제출물
         * @return o1 이 추천수가 더 낮다면 true 를 반환합니다.
         */
        @Override
        public int compare(Submission o1, Submission o2) {
            return (int) (o2.getRating() - o1.getRating());
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
         * @param o1 퀘스트 제출물
         * @param o2 다른 퀘스트 제출물
         * @return o1 이 더 나중에 제출되었다면 true 를 반환합니다.
         */
        @Override
        public int compare(Submission o1, Submission o2) {
            return (int) (o2.getDate() - o1.getDate());
        }

    }

}
