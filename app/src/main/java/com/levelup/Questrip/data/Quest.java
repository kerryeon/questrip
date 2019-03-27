package com.levelup.Questrip.data;

import com.google.android.gms.maps.model.LatLng;

import java.util.Comparator;

/**
 * 퀘스트에 대한 자세한 정보를 담고 있는 클래스입니다.
 *
 * 담당자: 김호
 *
 * 역할: 퀘스트에 대한 자세한 정보를 관리합니다.
 */
public final class Quest implements Comparable<Quest> {

    private String title;
    private String description;
    private String location;
    private double latitude;
    private double longitude;
    private long date_begin;
    private long date_end;
    private long rating;
    private boolean isCleared;

    private static Quest[] list;

    /**
     * 인기 순으로 정렬되어있는 퀘스트 목록을 불러옵니다.
     * @return 퀘스트 목록
     */
    public static Quest[] getList() {
        return list;
    }

    /**
     * 서버로부터 수신받은, 인기 순으로 정렬된 퀘스트 목록을 저장합니다.
     * @param list 퀘스트 목록
     */
    public static void setList(Quest[] list) {
        Quest.list = list;
    }

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
     * 퀘스트의 위치 지명을 가져옵니다.
     * @return 퀘스트의 위치 지명
     */
    public final String getLocation() {
        return location;
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
     * 퀘스트의 위경도 값을 가져옵니다.
     * @return 위경도
     */
    public LatLng getLatLng() {
        return new LatLng(getLatitude(), getLongitude());
    }

    /**
     * 퀘스트를 비교할 수 있습니다.
     * 둘 중 어느 퀘스트가 더 인기있는지 비교합니다.
     * @param o 다른 퀘스트
     * @return this 퀘스트가 더 인기있다면 양수(+)를 반환합니다.
     */
    @Override
    public int compareTo(Quest o) {
        return (int) (this.getRating() - o.getRating());
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
         * 퀘스트의 위치 지명을 설정합니다.
         * @param value: 퀘스트의 위치 지명
         * @return Builder
         */
        public Builder setLocation(String value) {
            quest.location = value;
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

    /**
     * 퀘스트의 정렬 비교 연산을 담당하는 클래스입니다.
     *
     * 담당자: 김호
     */
    public static class QuestComparator implements Comparator<Quest> {

        /**
         * 새로운 비교 연산 객체를 생성합니다.
         */
        public QuestComparator() {

        }

        /**
         * 서로 다른 두 퀘스트의 인기도를 비교합니다.
         * @param o1 퀘스트
         * @param o2 다른 퀘스트
         * @return o1 이 인기도가 더 낮다면 true 를 반환합니다.
         */
        @Override
        public int compare(Quest o1, Quest o2) {
            return o2.compareTo(o1);
        }

    }

}
