package com.levelup.Questrip.common;

/**
 * 로그인한 사용자의 회원정보를 담고 있는 클래스입니다.
 *
 * 담당자: 김호
 *
 * 역할: 사용자의 정보를 담당합니다.
 * Account.getInstance() 를 통하여 사용자의 정보를 불러올 수 있습니다.
 */
public final class Account {

    private static Account me;

    private String nickname;
    private long birthday;
    private String address;
    private String address_detail;
    private boolean terms;

    /**
     * 사용자 정보가 담겨진 객체를 불러옵니다.
     * @return 사용자 정보
     */
    public static Account getInstance() {
        return me;
    }

    /**
     * 사용자 정보를 저장합니다.
     */
    void setInstance() {
        me = this;
    }

    /**
     * 사용자의 닉네임 정보를 가져옵니다.
     * @return 사용자의 닉네임
     */
    public final String getNickname() {
        return nickname;
    }

    /**
     * 사용자의 생년월일을 가져옵니다.
     * 생년월일은 yyyyMMdd 형식으로 출력합니다.
     * @return 사용자의 생년월일
     */
    public final long getBirthday() {
        return birthday;
    }

    /**
     * 사용자의 주소 정보를 가져옵니다.
     * @return 사용자의 집주소
     */
    public final String getAddress() {
        return address;
    }

    /**
     * 사용자의 세부주소 정보를 가져옵니다.
     * @return 사용자의 세부주소
     */
    public final String getAddress_detail() {
        return address_detail;
    }

    /**
     * 사용자의 약관 동의 여부를 가져옵니다.
     * @return 사용자의 약관 동의 여부
     */
    public final boolean getTerms() {
        return terms;
    }

    /**
     * Builder 를 위해, 새로운 사용자 정보 객체를 생성합니다.
     */
    private Account() {
        nickname = "";
        address = "";
        address_detail = "";
        terms = false;
    }

    /**
     * 사용자 정보에 필요한 양식을 만들어주는 Builder 입니다.
     */
    static class Builder {

        private Account account;

        /**
         * Builder 를 초기화합니다.
         */
        Builder() {
            account = new Account();
        }

        /**
         * 닉네임을 설정합니다.
         * @param value: 닉네임
         * @return Builder
         */
        Builder setNickname(String value) {
            account.nickname = value;
            return this;
        }

        /**
         * 생년월일을 설정합니다.
         * yyyyMMdd 형식으로 입력해야 합니다.
         * @param value: 생년월일
         * @return Builder
         */
        Builder setBirthday(long value) {
            account.birthday = value;
            return this;
        }

        /**
         * 주소를 설정합니다.
         * @param value: 집주소
         * @return Builder
         */
        Builder setAddress(String value) {
            account.address = value;
            return this;
        }

        /**
         * 세부주소를 설정합니다.
         * @param value: 세부주소
         * @return Builder
         */
        Builder setAddressDetail(String value) {
            account.address_detail = value;
            return this;
        }

        /**
         * 약관 동의 여부를 설정합니다.
         * @param value: 약관 동의 여부
         * @return Builder
         */
        Builder setTerms(boolean value) {
            account.terms = value;
            return this;
        }

        /**
         * 완성된 사용자 정보를 반환합니다.
         * @return 사용자 정보
         */
        final Account create() {
            return account;
        }

    }

}
