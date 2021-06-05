package com.depromeet.linkzupzup

object StatusConst {

    /**
     * 조회 성공 ( 로그인 성공, 데이터 조회 성공 )
     */
    const val SELECT_SUSSCESS_STATUS = 200

    /**
     * 등록 성공 ( 가입 성공, created에 대한 응답 )
     */
    const val REGIST_SUCCESS_STATUS = 201

    /**
     * 수정 성공
     */
    const val UPDATE_SUCCESS_STATUS = 203

    /**
     * 삭제 성곡 ( no Content에 대한 응답 )
     */
    const val DELETE_SUCCESS_STATUS = 204

    /**
     * Access token Expired ( 토큰 만료 )
     */
    const val ACCESS_TOKEN_EXPIRED_STATUS = 403

    /**
     * 조회 실패
     */
    const val SELECT_FAIL_STATUS = 400

    /**
     * 등록 실패
     */
    const val REGIST_FAIL_STATUS = 401

    /**
     * 수정 실패
     */
    const val UPDATE_FAIL_STATUS = 402

    /**
     * 삭제 실패
     */
    const val DELETE_FAIL_STATUS = 404

}