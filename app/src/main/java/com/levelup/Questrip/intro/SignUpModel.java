package com.levelup.Questrip.intro;

/**
 * 회원가입에 필요한 양식을 담은 클래스입니다.
 * 내장된 Builder 를 사용하여 양식을 안전하게 채울 수 있습니다.
 *
 * 담당자: 김호
 *
 * 역할: 회원가입에 필요한 양식을 안전하게 채울 수 있습니다.
 */
final class SignUpModel {

    private String nickname;
    private long birthday;
    private String address;
    private String addressDetail;
    private boolean terms;

    /**
     * 양식을 초기화합니다.
     */
    private SignUpModel() {
        this.nickname = "";
        this.birthday = 0;
        this.address = "";
        this.addressDetail = "";
        this.terms = false;
    }

    /**
     * 필드에서 닉네임을 반환합니다.
     * @return 닉네임
     */
    String getNickname() {
        return this.nickname;
    }

    /**
     * 필드에서 생년월일을 반환합니다.
     * @return 생년월일
     */
    long getBirthday() {
        return this.birthday;
    }

    /**
     * 필드에서 주소를 반환합니다.
     * @return 주소
     */
    String getAddress() {
        return this.address;
    }

    /**
     * 필드에서 세부주소를 반환합니다.
     * @return 세부주소
     */
    String getAddressDetail() {
        return this.addressDetail;
    }

    /**
     * 필드에서 약관 동의 여부를 반환합니다.
     * @return 약관 동의 여부
     */
    boolean getTerms() {
        return this.terms;
    }

    /**
     * 회원가입에 필요한 양식을 만들어주는 Builder 입니다.
     */
    static class Builder {

        SignUpModel model;

        /**
         * Builder 를 초기화합니다.
         */
        Builder() {
            this.model = new SignUpModel();
        }

        /**
         * 닉네임을 입력합니다.
         * @param value: 닉네임
         * @return 닉네임의 길이가 잘못된 경우 true 를 반환합니다.
         */
        boolean setNickname(String value) {
            this.model.nickname = value;
            return value.length() < 4 || value.length() > 12;
        }

        /**
         * 생년월일을 입력합니다.
         * @param value: 생년월일 yyyyMMdd. [예] 19980904
         * @return 생년월일이 잘못된 경우 true 를 반환합니다.
         */
        boolean setBirthday(long value) {
            this.model.birthday = value;
            return value < 19000101 || value > 99991231;
        }

        /**
         * 주소를 입력합니다.
         * @param value: 주소
         * @return 주소가 잘못된 경우 true 를 반환합니다.
         */
        boolean setAddress(String value) {
            this.model.address = value;
            return value.length() == 0;
        }

        /**
         * 상세한 주소를 입력합니다.
         * @param value: 주소. [예] 107동 706호
         * @return 주소가 잘못된 경우 true 를 반환합니다.
         */
        boolean setAddressDetail(String value) {
            this.model.addressDetail = value;
            return value.length() == 0;
        }

        /**
         * 약관에 동의하였는 지 입력합니다.
         * @param value: 약관에 동의하였으면 true 를 반환합니다.
         * @return 약관에 동의하지 않은 경우 true 를 반환합니다.
         */
        boolean setTerms(boolean value) {
            this.model.terms = value;
            return ! value;
        }

        /**
         * 완성된 양식을 반환합니다.
         * @return 완성된 양식
         */
        final SignUpModel create() {
            return this.model;
        }

    }

}
