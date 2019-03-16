package com.levelup.Questrip.intro;

/**
 * 사용자의 집주소를 불러오는 프로세스를 담당하는 클래스입니다.
 *
 * 담당자: 정홍기
 *
 * 역할: 사용자의 집주소를 불러오니다.
 *
 * 예제는 다음과 같습니다.
 * @see <a href="http://dailyddubby.blogspot.com/2018/02/2-api.html" />
 */
final class AddressManager {

    /**
     * 집주소를 불러오는 데에 성공한 경우의 이벤트입니다.
     *
     * 담당자: 김호
     */
    @FunctionalInterface
    interface OnSuccess {
        /**
         * 집주소를 이용한 이벤트를 구현합니다.
         * @param address: 집주소
         */
        void run(String address);
    }

    static void tryGetAddress(OnSuccess success, Runnable fail) {
        // TODO to be implemented.
        fail.run();
    }
}
