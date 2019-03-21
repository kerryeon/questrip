package com.levelup.Questrip.data;

/**
 * 퀘스트에 대한 자세한 정보를 담고 있는 클래스입니다.
 *
 * 담당자: 김호
 *
 * 역할: 퀘스트에 대한 자세한 정보를 관리합니다.
 */
public final class Quest {

    private String title;
    private String description;
    private double latitude;
    private double longitude;
    private long date_begin;
    private long date_end;
    private long rating;
    private boolean isCleared;

    /**
     * 퀘스트의 제목을 가져옵니다.
     * @return 퀘스트의 제목
     */
    public final String getTitle() {
        return title;
    }

    /**
     * 퀘스트의 설명을 가져옵니다.
     * @return 퀘스트의 설명을
     */
    public final String getDescription() {
        return description;
    }

    /**
     * 위도 정보를 가져옵니다.
     * @return 위도값
     */
    public final double getLatitude() {
        return latitude;
    }

    /**
     * 경도 정보를 가져옵니다.
     * @return 경도값
     */
    public final double getLongitude() {
        return longitude;
    }

    /**
     * 퀘스트의 시작일을 가져옵니다.
     * @return 퀘스트 시작일
     */
    public final long getDateBegin() {
        return date_begin;
    }

    /**
     * 퀘스트의 종료일을 가져옵니다.
     * @return 퀘스트 종료일
     */
    public final long getDateEnd() {
        return date_end;
    }

    /**
     * 퀘스트의 인기도를 가져옵니다.
     * @return 인기도
     */
    public final long getRating() {
        return rating;
    }

    /**
     * 퀘스트의 성취 여부를 가져옵니다.
     * @return 성취 여부
     */
    public final boolean getIsCleared() {
        return isCleared;
    }

    /**
     * Builder 를 위해, 새로운 퀘스트 정보 객체를 생성합니다.
     */
    private Quest() {
        title = "";
        description = "";
        latitude = 0.0;
        longitude = 0.0;
        date_begin = 0;
        date_end = 0;
        rating = 0;
        isCleared = false;
    }

    /**
     * 퀘스트 정보에 필요한 양식을 만들어주는 Builder 입니다.
     */
    public static class Builder {

        private Quest quest;

        /**
         * Builder 를 초기화합니다.
         */
        public Builder() {
            quest = new Quest();
        }

        /**
         * 퀘스트의 제목을 설정합니다.
         * @param value: 퀘스트의 제목
         * @return Builder
         */
        public Builder setTitle(String value) {
            quest.title = value;
            return this;
        }

        /**
         * 퀘스트의 설명을 설정합니다.
         * @param value: 퀘스트 설명
         * @return Builder
         */
        public Builder setDescription(String value) {
            quest.description = value;
            return this;
        }

        /**
         * 위도를 설정합니다.
         * @param value: 위도값
         * @return Builder
         */
        public Builder setLatitude(double value) {
            quest.latitude = value;
            return this;
        }

        /**
         * 경도를 설정합니다.
         * @param value: 경도값
         * @return Builder
         */
        public Builder setLongitude(double value) {
            quest.longitude = value;
            return this;
        }

        /**
         * 퀘스트의 시작일을 설정합니다.
         * @param value: 퀘스트 시작일
         * @return Builder
         */
        public Builder setDateBegin(long value) {
            quest.date_begin = value;
            return this;
        }

        /**
         * 퀘스트의 종료일을 설정합니다.
         * @param value: 퀘스트 종료일
         * @return Builder
         */
        public Builder setDateEnd(long value) {
            quest.date_end = value;
            return this;
        }

        /**
         * 인기도를 설정합니다.
         * @param value: 인기도
         * @return Builder
         */
        public Builder setRating(long value) {
            quest.rating = value;
            return this;
        }

        /**
         * 성취 여부를 설정합니다.
         * @param value: 성취 여부
         * @return Builder
         */
        public Builder setIsCleared(boolean value) {
            quest.isCleared = value;
            return this;
        }

        /**
         * 완성된 퀘스트 정보를 반환합니다.
         * @return 퀘스트 정보
         */
        public final Quest create() {
            return quest;
        }

    }

}
